package com.example.shipmentservicelight.ttt.stage;

import com.example.shipmentservicelight.ttt.ex.NoBeanOfType;
import com.example.shipmentservicelight.ttt.ex.StageException;

import java.util.HashMap;
import java.util.Map;

public class StageContext implements IStageContext{
    private final Map<Class<?>, Object> beanMap = new HashMap<>();

    // Регистрация объекта в контейнере
    public <T> StageContext registerBean(Class<T> clazz, T instance) {
        beanMap.put(clazz, instance);
        return this;
    }

    // Получение объекта из контейнера
    public <T> T getBean(Class<T> clazz) throws NoBeanOfType {
        T ans = clazz.cast(beanMap.get(clazz));
        if(ans !=null){
            return ans;
        }else{
            throw new NoBeanOfType("No bean of type " + clazz);
        }
    }

    // Автоматическое создание и регистрация объекта
    public <T> void registerBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = clazz.newInstance();
        beanMap.put(clazz, instance);
    }
}
