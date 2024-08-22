package com.example.shipmentservicelight.ttt.stage;

import java.util.HashMap;
import java.util.Map;

public class StageContext implements IStageContext{
    private final Map<Class<?>, Object> beanMap = new HashMap<>();

    // Регистрация объекта в контейнере
    public <T> void registerBean(Class<T> clazz, T instance) {
        beanMap.put(clazz, instance);
    }

    // Получение объекта из контейнера
    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beanMap.get(clazz));
    }

    // Автоматическое создание и регистрация объекта
    public <T> void registerBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = clazz.newInstance();
        beanMap.put(clazz, instance);
    }
}
