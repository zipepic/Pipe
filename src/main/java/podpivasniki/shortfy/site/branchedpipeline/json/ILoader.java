package podpivasniki.shortfy.site.branchedpipeline.json;

import java.util.List;

public interface ILoader {
    Object getStageParam(StageConstructorParams param);
    Object findObjectParamByNameInRefMap(String name);
    void writeParamInRefMap(String name, Object value);
    Integer getRefMapSize();
    List<Object> findAllObjectParamByObjectSettingsInRefMap(List<ObjectSettingConstructorParam> params);
    Object findObjectParamByObjectSettingInRefMap(ObjectSettingConstructorParam param);
    List<Object> getAllStageParam(List<StageConstructorParams> params);
}
