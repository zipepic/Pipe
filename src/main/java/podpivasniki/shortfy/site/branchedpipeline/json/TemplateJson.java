package podpivasniki.shortfy.site.branchedpipeline.json;

import podpivasniki.shortfy.site.branchedpipeline.stage.AttacheableStage;
import podpivasniki.shortfy.site.branchedpipeline.stage.IStageContext;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class TemplateJson {
    @JsonProperty("settings")
    private List<Setting> settings;
    @JsonProperty("object_setting")
    private List<ObjectSetting> objectSettings;
    @JsonProperty("base_domen")
    private String baseDomen;
    @JsonProperty("services")
    private Services services;
    @JsonProperty("stage")
    private List<Stage> stages;

    @SneakyThrows
    public static AttacheableStage attachStages(ILoader loader, List<Stage> stages, String baseDomen){
        AttacheableStage firstStage = null;
        for(Stage stage:stages){
            if(firstStage == null)
                firstStage = stage.createInstanceMainStage(loader, baseDomen);
            else
                firstStage = stage.createInstanceStage(loader,baseDomen);
            if(stage.getSteps()!= null && stage.getSteps().size() > 0){

                for(StageStep stageStep: stage.getSteps()){
                    List<podpivasniki.shortfy.site.branchedpipeline.stage.Stage> newStages = new ArrayList<>();
                    for(Stage s:stageStep.getAddStages()){
                        podpivasniki.shortfy.site.branchedpipeline.stage.Stage o = (podpivasniki.shortfy.site.branchedpipeline.stage.Stage) attachStages(loader, List.of(s), baseDomen);
                        newStages.add(o);
                    }
                    firstStage.attachStageStep(newStages.toArray(new podpivasniki.shortfy.site.branchedpipeline.stage.Stage[0]));
                }
            }
        }
        return firstStage;
    }
}
