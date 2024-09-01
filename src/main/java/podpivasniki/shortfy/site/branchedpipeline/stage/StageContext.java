package podpivasniki.shortfy.site.branchedpipeline.stage;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class StageContext implements IStageContext, IBeanMapGetterable {
    private final UUID uuid;
    private final Map<Class<?>, Object> beanMap = new HashMap<>();

    public StageContext(UUID uuid) {
        this.uuid = uuid;
    }

    public StageContext() {
        this.uuid = UUID.randomUUID();
    }

    // Регистрация объекта в контейнере
    public <T> IStageContext registerBean(Class<T> clazz, T instance) {
        log.debug("Registering bean for class: {}", clazz.getName());
        beanMap.put(clazz, instance);

        for (Class<?> iface : clazz.getInterfaces()) {
            log.debug("Registering bean for interface: {}", iface.getName());
            beanMap.put(iface, instance);
        }

        log.info("Bean registered successfully for class: {}", clazz.getName());
        return this;
    }

    // Получение объекта из контейнера по классу
    public <T> T getBean(Class<T> clazz) {
        T res = clazz.cast(beanMap.get(clazz));
        if (res == null) {
            log.warn("Bean for class {} not found", clazz.getName());
        } else {
            log.info("Bean for class {} retrieved successfully", clazz.getName());
        }
        return res;
    }

    // Получение объекта из контейнера по интерфейсу
    @SuppressWarnings("unchecked")
    public <T> T getBeanByInterface(Class<T> iface) {
        T res = (T) beanMap.get(iface);
        if (res == null) {
            log.warn("Bean for interface {} not found", iface.getName());
        } else {
            log.info("Bean for interface {} retrieved successfully", iface.getName());
        }
        return res;
    }

    // Автоматическое создание и регистрация объекта
    public <T> IStageContext registerBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        log.debug("Automatically creating and registering bean for class: {}", clazz.getName());
        T instance = clazz.newInstance();
        beanMap.put(clazz, instance);

        for (Class<?> iface : clazz.getInterfaces()) {
            log.debug("Registering bean for interface: {}", iface.getName());
            beanMap.put(iface, instance);
        }

        log.info("Bean automatically registered successfully for class: {}", clazz.getName());
        return this;
    }

    @Override
    public <T> IStageContext registerBean(@NonNull T instance) {
        log.debug("Registering bean for instance of class: {}", instance.getClass().getName());
        beanMap.put(instance.getClass(), instance);

        for (Class<?> iface : instance.getClass().getInterfaces()) {
            log.debug("Registering bean for interface: {}", iface.getName());
            beanMap.put(iface, instance);
        }

        log.info("Bean registered successfully for instance of class: {}", instance.getClass().getName());
        return this;
    }

    @Override
    public UUID getStageUUID() {
        return uuid;
    }

    @Override
    public IStageContext merge(IStageContext stageContext) {
        IStageContext locale = IStageContext.defaultStageContext();
        ((IBeanMapGetterable) locale).getBeanMap().putAll(((IBeanMapGetterable) this).getBeanMap());
        ((IBeanMapGetterable) locale).getBeanMap().putAll(((IBeanMapGetterable) stageContext).getBeanMap());
        return locale;
    }

    @Override
    public Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }
}
