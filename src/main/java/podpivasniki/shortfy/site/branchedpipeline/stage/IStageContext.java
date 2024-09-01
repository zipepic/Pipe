package podpivasniki.shortfy.site.branchedpipeline.stage;

import java.util.Map;
import java.util.UUID;

public interface IStageContext {
    <T> IStageContext registerBean(Class<T> clazz, T instance);
    <T> T getBean(Class<T> clazz);
    <T> T getBeanByInterface(Class<T> iface);
    <T> IStageContext registerBean(Class<T> clazz) throws IllegalAccessException, InstantiationException;
    <T> IStageContext registerBean(T instance);
    UUID getStageUUID();
    IStageContext merge(IStageContext stageContext);
    static IStageContext defaultStageContext(){
        return new StageContext();
    }
}
