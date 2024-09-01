package podpivasniki.shortfy.site.branchedpipeline.json;


import podpivasniki.shortfy.site.branchedpipeline.stage.ConfigurableStage;
import podpivasniki.shortfy.site.branchedpipeline.stage.InvocationStage;

public interface ITemplateParser {
    ITemplateParser prepare();
    InvocationStage getInvokationStage();
    ConfigurableStage getConfigurableStage();

}
