package podpivasniki.shortfy.site.branchedpipeline.json;

import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.stage.AttacheableStage;
import podpivasniki.shortfy.site.branchedpipeline.stage.IStageContext;
import podpivasniki.shortfy.site.branchedpipeline.stage.MainStage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Constructor;
import java.util.List;

@Data
@ToString
public class Stage {
    @JsonProperty("class")
    private String className;
    private List<StageStep> steps;
    @JsonProperty("constructor_params")
    private List<StageConstructorParams> constructorParams;
    @JsonProperty("inject")
    private List<StageConstructorParams> injectParams;

    @SneakyThrows
    public AttacheableStage createInstanceStage(ILoader loader, String baseDomen){
        return podpivasniki.shortfy.site.branchedpipeline.stage.Stage.init(createInstanceHandler(loader, baseDomen));
    }
    @SneakyThrows
    public MainStage createInstanceMainStage(ILoader loader, String baseDomen){

        return MainStage.init(createInstanceHandler(loader,baseDomen));
    }

    @SneakyThrows
    private AbstractHandler createInstanceHandler(ILoader loader, String baseDomen){
        if(className.startsWith("."))
            className = baseDomen+className;

        Class<?> clazz = Class.forName(className);
        List<Object> params = loader.getAllStageParam(constructorParams);

        Constructor constructor = ConstructorFinder.findConstructor(clazz,params);
        return (AbstractHandler) constructor.newInstance(params.toArray());
    }
}
