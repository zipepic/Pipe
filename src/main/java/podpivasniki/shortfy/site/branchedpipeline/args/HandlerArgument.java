package podpivasniki.shortfy.site.branchedpipeline.args;

import lombok.ToString;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
@Data
@ToString
public class HandlerArgument {
    protected TypeNode typeNode;

    public HandlerArgument(TypeNode typeNode) {
        this.typeNode = typeNode;
    }

    public static List<HandlerArgument> init(Type[] types){
        return Arrays.stream(types).map(HandlerArgument::init).collect(Collectors.toList());
    }

    public static HandlerArgument init(Type type){
        TypeNode node = TypeNode.buildTree(type);
        return new HandlerArgument(node);
    }
    public static List<HandlerArgument> initByMultiply(HandlerProcess annotation){
        List<HandlerArgument> res = new ArrayList<>();
        Class<?>[] classes = annotation.classes();
        // Извлекаем массив классов из аннотации
        List<TypeNode> nodes = TypeNode.buildTrees(Arrays.stream(classes).toList());
        for(TypeNode node: nodes){
            res.add(new HandlerArgument(node));
        }

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof HandlerArgument)) return false;
        HandlerArgument that = (HandlerArgument) o;
        return this.typeNode.equals(that.getTypeNode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(typeNode);
    }
}
