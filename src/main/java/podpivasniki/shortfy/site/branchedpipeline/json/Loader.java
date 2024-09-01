package podpivasniki.shortfy.site.branchedpipeline.json;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Loader implements ILoader {
    private Map<String,Object> refMap = new HashMap<>();

    @SneakyThrows
    @Override
    public Object getStageParam(StageConstructorParams param) {
        Object res;
        switch (param.getType()){
            case OBJECT_REF -> res = findObjectParamByNameInRefMap(param.getObjectRef());
            default -> throw new IllegalStateException("Invalid type stage params: "+ param.getType());
        }
        if(res == null){
            throw new IllegalStateException("Result is null for parameter type: " + param.getType() +
                    ", parameter details: " + param);
        }
        return res;
    }

    @Override
    public Object findObjectParamByNameInRefMap(String name) {
        if(!refMap.containsKey(name) || refMap.get(name) == null)
            throw new IllegalStateException(String.format("Parameter in refMap %s not found by name %s", refMap,name));

        return refMap.get(name);
    }

    @Override
    public void writeParamInRefMap(String name, Object value) {
        refMap.put(name,value);
    }

    @Override
    public Integer getRefMapSize() {
        return refMap.size();
    }

    @Override
    public List<Object> findAllObjectParamByObjectSettingsInRefMap(List<ObjectSettingConstructorParam> params) {
        if(params == null || params.size() == 0)
            return new ArrayList<>();
        return params.stream().map(this::findObjectParamByObjectSettingInRefMap).toList();
    }

    @Override
    public Object findObjectParamByObjectSettingInRefMap(ObjectSettingConstructorParam param) {
        if(param.isRef())
            return findObjectParamByNameInRefMap(param.getRef());

        return param.getSettingIfParamIsNotRef().loadSetting();
    }

    @Override
    public List<Object> getAllStageParam(List<StageConstructorParams> params) {
        if(params == null || params.size() == 0)
            return new ArrayList<>();
        return params.stream().map(this::getStageParam).toList();
    }
}
