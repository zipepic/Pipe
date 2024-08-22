package com.example.shipmentservicelight.ttt.args;

import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import lombok.Data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class HandlerArgument {
    protected Class<?> collectionClass;
    protected Class<?> argClass;
    protected boolean isParametrized;

    public HandlerArgument(Class<?> collectionClass, Class<?> argClass) {
        this.collectionClass = collectionClass;
        this.argClass = argClass;
        isParametrized = !(collectionClass == null);
    }

    public HandlerArgument(Class<?> argClass) {
        this.argClass = argClass;
        this.isParametrized = false;
    }

    public static List<HandlerArgument> init(Type[] types){
        return Arrays.stream(types).map(HandlerArgument::init).collect(Collectors.toList());
    }
    public static HandlerArgument init(Type type){
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // Проверяем, является ли тип параметризованным List
            if (Collection.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                return new HandlerArgument((Class<?>) parameterizedType.getRawType(), (Class<?>) actualTypeArguments[0]);
            }
        }
        // Если тип является обычным классом
        if (type instanceof Class<?>) {
            Class<?> cls = (Class<?>) type;

            // Если это массив
            if (cls.isArray()) {
                return new HandlerArgument(cls, cls.getComponentType());
            }
            return new HandlerArgument(cls);
        }
        throw new RuntimeException("Хуйня");
    }
    public static List<HandlerArgument> initByMultiply(HandlerProcess annotation){
        List<HandlerArgument> res = new ArrayList<>();
        // Извлекаем массив классов из аннотации
        Class<?>[] classes = annotation.classes();
        Class<?>[] genericTypes = annotation.genericTypes();

        // Печатаем классы
        int index =0;
        for (Class<?> cls : classes) {
            if(cls.isArray()){
                res.add(new HandlerArgument(cls, cls.getComponentType()));
            }else if( cls.equals(List.class)){
                if(index >= genericTypes.length)
                    throw new RuntimeException("Хуйня, мало указал в genericTypes");

                res.add(new HandlerArgument(cls, genericTypes[index++]));
            }else {
                res.add(new HandlerArgument(cls));
            }

        }
        if(index != genericTypes.length)
            throw new RuntimeException("Хуйня, много указал в genericTypes");
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        Class<?> anyClazz = Object.class;
        if( this == obj)
            return true;
        if(obj == null)
            return false;
        try {
            HandlerArgument argument2 = (HandlerArgument) obj;

            if(this.argClass.equals(anyClazz)|| argument2.argClass.equals(anyClazz)){
                return true;
            }else {
                if(this.isParametrized != argument2.isParametrized)
                    return false;
                if(!this.argClass.equals(argument2.argClass))
                    return false;

                if(this.isParametrized && !this.collectionClass.isAssignableFrom(argument2.collectionClass))
                    return false;
            }



            return true;
        }catch (ClassCastException e){
            return false;
        }

    }

    public boolean isObject(){
        if(this.isParametrized)
            return false;
        else
            return this.argClass.equals(Object.class);
    }

    @Override
    public String toString() {
        return isParametrized? "HandlerArgument{" +
                "collectionClass=" + collectionClass +
                ", argClass=" + argClass +
                ", isParametrized=" + isParametrized +
                '}':
                "HandlerArgument{" +
                        "argClass=" + argClass +
                        ", isParametrized=" + isParametrized +
                        '}'
                ;
    }
}
