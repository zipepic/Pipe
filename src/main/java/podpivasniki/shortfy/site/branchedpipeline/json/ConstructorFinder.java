package podpivasniki.shortfy.site.branchedpipeline.json;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorFinder {
    public static Constructor findConstructor(Class<?> clazz, List<Object> params){
        Class<?>[] paramTypes = params.stream()
                .map(Object::getClass)
                .toArray(Class[]::new);

        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (matchConstructor(constructor.getParameterTypes(), paramTypes)) {
                return constructor;
            }
        }
        throw new IllegalArgumentException("No suitable constructor found for class: " + clazz.getName());
    }

    private static boolean matchConstructor(Class<?>[] constructorParamTypes, Class<?>[] paramTypes) {
        if (constructorParamTypes.length != paramTypes.length) {
            return false;
        }

        for (int i = 0; i < constructorParamTypes.length; i++) {
            if (!constructorParamTypes[i].isAssignableFrom(paramTypes[i])) {
                return false;
            }
        }
        return true;
    }
}
