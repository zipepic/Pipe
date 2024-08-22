package com.example.shipmentservicelight.ttt.stage;

public interface IStageContext {
    public <T> void registerBean(Class<T> clazz, T instance);
    public <T> T getBean(Class<T> clazz);
    public <T> void registerBean(Class<T> clazz) throws IllegalAccessException, InstantiationException;
}
