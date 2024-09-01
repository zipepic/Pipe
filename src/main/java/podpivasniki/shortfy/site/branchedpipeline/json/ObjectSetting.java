package podpivasniki.shortfy.site.branchedpipeline.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectSetting {
    private String name;
    @JsonProperty("class")
    private String className;
    @JsonProperty("constructor_params")
    private List<ObjectSettingConstructorParam> constructorParams;
    @JsonProperty("setter_params")
    private List<ObjectSettingConstructorParam> setterParams;

    @SneakyThrows
    public Object loadObjectSetting(ILoader loader){
        Object o = createInstance(loader);
        if(setterParams == null)
            return o;

        for(ObjectSettingConstructorParam i: setterParams){
            Object value = loader.findObjectParamByObjectSettingInRefMap(i);
            try {
                Method setMethod = o.getClass().getMethod("set"+i.getName(), value.getClass());
                setMethod.invoke(o, value);
            }catch (NoSuchMethodException e){
                throw new IllegalArgumentException(String.format("Setter method %s not found for class %s", i.getSettingIfParamIsNotRef().getName(), o.getClass()));
            }

        }
        return o;
    }
    @SneakyThrows
    private Object createInstance(ILoader loader){
        Class<?> clazz = Class.forName(className);
        if(constructorParams == null || constructorParams.size() == 0){
            Constructor<?> noArgConstructor = clazz.getDeclaredConstructor();
            return noArgConstructor.newInstance();
        }
        List<Object> constructorParamsLocal = loader.findAllObjectParamByObjectSettingsInRefMap(constructorParams);

        return ConstructorFinder.findConstructor(clazz, constructorParamsLocal)
                .newInstance(constructorParamsLocal.toArray());
    }
}
