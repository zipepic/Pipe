package podpivasniki.shortfy.site.branchedpipeline.args;

import lombok.Getter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Getter
public class TypeNode {
    private final Class<?> type;
    private final List<TypeNode> children;

    public TypeNode(Class<?> type) {
        this.type = type;
        this.children = new ArrayList<>();
    }

    public static List<TypeNode> buildTrees(List<Class<?>> classes) {
        List<TypeNode> res = new ArrayList<>();
        int index = 0;
        while (index < classes.size()) {
            TypeNode node = TypeNode.buildRecurs(classes, index);
            index += node.size();
            res.add(node);
        }
        return res;
    }

    // Рекурсивная функция для построения дерева
    public static TypeNode buildTree(List<Class<?>> classes) {
        TypeNode node = buildRecurs(classes, 0);
        if (node.size() != classes.size())
            throw new RuntimeException("Чет не сошлось");
        return node;
    }

    private static TypeNode buildRecurs(List<Class<?>> classes, int index) {
        if (index >= classes.size()) {
            return null; // Если индекс выходит за границы списка, завершаем рекурсию
        }


        Class<?> currentClass = classes.get(index);
        TypeNode node = new TypeNode(currentClass);

        // Получаем количество обобщённых параметров текущего класса
        int typeParamsCount = currentClass.getTypeParameters().length;

        if (index + typeParamsCount >= classes.size())
            throw new RuntimeException("Хуйня мало указал параметров");

        // Начинаем с индекса следующего элемента
        int nextIndex = index + 1;

        // Рекурсивно добавляем дочерние узлы
        for (int i = 0; i < typeParamsCount && nextIndex < classes.size(); i++) {
            TypeNode childNode = buildRecurs(classes, nextIndex);
            if (childNode != null) {
                node.addChild(childNode);
                nextIndex++; // Увеличиваем индекс после добавления дочернего узла
            }
        }
        return node;
    }

    private static TypeNode recurs(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            Type rawType = parameterizedType.getRawType();

            // Создаем узел для rawType
            TypeNode node = new TypeNode((Class<?>) rawType);

            // Рекурсивно обрабатываем аргументы типов и добавляем их как дочерние узлы
            for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
                node.addChild(recurs(actualTypeArgument));
            }

            return node;
        } else if (type instanceof Class<?> cls) {
            // Если тип не параметризован, просто возвращаем узел
            TypeNode node = new TypeNode(cls);

            // Если это массив, добавляем компонент типа как дочерний узел
            if (cls.isArray()) {
                node.addChild(new TypeNode(cls.getComponentType()));
            }

            return node;
        } else {
            throw new RuntimeException("Unsupported type");
        }
    }

    public static TypeNode buildTree(Type type) {
        return recurs(type);
    }
    

    public static TypeNode buildTreeWithValue(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }
        TypeNode node = new TypeNode(o.getClass());

        if (o instanceof Collection) {
            var opt = ((Collection) o).stream().findFirst();
            if (opt.isPresent()) {
                node.addChild(buildTreeWithValue(opt.get()));
            }else{
                node.addChild(TypeNode.recurs(Object.class));
            }
        }else if (o instanceof AbstractMap) {
            var optKey = ((AbstractMap) o).keySet().stream().findFirst();
            var optValue = ((AbstractMap) o).values().stream().findFirst();
            if (optKey.isPresent()) {
                node.addChild(buildTreeWithValue(optKey.get()));
            }else{
                node.addChild(TypeNode.recurs(Object.class));
            }
            if (optValue.isPresent()) {
                node.addChild(buildTreeWithValue(optValue.get()));
            }else{
                node.addChild(TypeNode.recurs(Object.class));
            }
        }
        return node;
    }
    public int size() {
        int totalSize = 1; // Начинаем с 1, так как текущий узел тоже считается
        for (TypeNode child : children) {
            totalSize += child.size(); // Рекурсивно добавляем размер всех потомков
        }
        return totalSize;
    }

    public void addChild(TypeNode child) {
        this.children.add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        TypeNode typeNode = (TypeNode) o;
        if (this.type.equals(Object.class) || typeNode.getType().equals(Object.class)) return true;
        // If either type is Object, consider them equal
        if (this.type.isAssignableFrom(typeNode.getType()) || typeNode.getType().isAssignableFrom(this.type)) {
            return Objects.equals(children, typeNode.children);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, children);
    }

    @Override
    public String toString() {
        return "TypeNode{" +
               "type=" + type +
               ", children=" + children +
               '}';
    }

}
