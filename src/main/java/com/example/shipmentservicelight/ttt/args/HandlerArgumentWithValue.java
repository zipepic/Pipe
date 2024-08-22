package com.example.shipmentservicelight.ttt.args;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerArgumentWithValue extends HandlerArgument {
    private Object value;

    public Object getValue() {
        return value;
    }

    private HandlerArgumentWithValue(Class<?> collectionClass, Class<?> argClass, Object value) {
        super(collectionClass, argClass);
        this.value = value;
    }

    public static HandlerArgumentWithValue[] ofArray(Object[] values){
        HandlerArgumentWithValue[] res = new HandlerArgumentWithValue[values.length];
        for(int i =0;i<values.length;i++){
            res[i] = HandlerArgumentWithValue.of(values[i]);
        }
        return res;
    }
    public static List<HandlerArgumentWithValue> ofArgs(Object... values){
        return Arrays.stream(values).map(HandlerArgumentWithValue::of).collect(Collectors.toList());
    }
    public static HandlerArgumentWithValue of(Object value){


        if(value instanceof Collection<?>){
            Class<?> colClass = value.getClass();
            Class<?> arggClass = null;
            Collection<?> c = (Collection<?>) value;
            if(!c.isEmpty()){
                arggClass = c.stream().findFirst().get().getClass();
            }else {
                throw new RuntimeException("По пустой  коллекции нельзя определить класс");
            }
            return new HandlerArgumentWithValue(colClass, arggClass,value);
        }else {
            return new HandlerArgumentWithValue(null, value.getClass(),value);
        }

    }

    @Override
    public String toString() {
        return super.isParametrized? "HandlerArgument{" +
                "collectionClass=" + collectionClass +
                ", argClass=" + argClass +
                ", isParametrized=" + isParametrized +
                ", value=" + value +
                '}':
                "HandlerArgument{" +
                        "argClass=" + argClass +
                        ", isParametrized=" + isParametrized +
                        ", value=" + value +
                        '}'
                ;
    }
}
