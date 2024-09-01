package podpivasniki.shortfy.site.branchedpipeline.json;

import podpivasniki.shortfy.site.branchedpipeline.observers.*;
import lombok.*;
import podpivasniki.shortfy.site.branchedpipeline.stage.*;

import java.util.ArrayList;
import java.util.List;

public class TemplateParser implements ITemplateParser {
    private TemplateJson templateJson;
    private ApiLoader apiLoader;
    private List<Observer> stageObservers = new ArrayList<>();
    private ConfigurableStage configurableStage;

    private ILoader loader;

    private TemplateParser (TemplateJson templateJson, ApiLoader apiLoader){
        this.templateJson = templateJson;
        this.apiLoader = apiLoader;
    }

    @Builder
    public static TemplateParser of(TemplateJson templateJson, ApiLoader apiLoader) {
        return new TemplateParser(templateJson, apiLoader);
    }


    public TemplateParser setupStageObservers(List<Observer> stageObservers){
        this.stageObservers = stageObservers;
        return this;
    }

    private ILoader setupLoader(){
        ILoader newLoader = new Loader();
        if( templateJson.getSettings() != null && !templateJson.getSettings().isEmpty()){
            for (Setting setting: templateJson.getSettings()){
                newLoader.writeParamInRefMap(setting.getName(), setting.loadSetting());
            }
        }
        if( templateJson.getObjectSettings() != null &&!templateJson.getObjectSettings().isEmpty()) {
            for(ObjectSetting objectSetting: templateJson.getObjectSettings()){
                newLoader.writeParamInRefMap(objectSetting.getName(), objectSetting.loadObjectSetting(newLoader));
            }
        }
        return newLoader;
    }
    @Override
    public TemplateParser prepare(){
        if(this.loader != null && loader.getRefMapSize() != 0){
            throw new IllegalStateException("No double call prepare");
        }
        loader = setupLoader();
        configurableStage = loadStage();
        return this;
    }
    private ConfigurableStage loadStage(){
        return ((BuildableStage) TemplateJson.attachStages(loader,templateJson.getStages(), templateJson.getBaseDomen())).build(
                new StageApiLoaderToContextMapper(apiLoader,templateJson.getServices()),
                new SubjectContextMapper(stageObservers, templateJson.getServices().isAllowSubject()));
    }
    @Override
    public InvocationStage getInvokationStage(){
        return configurableStage.configure();
    }

    @Override
    public ConfigurableStage getConfigurableStage() {
        return configurableStage;
    }
}
