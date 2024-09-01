package podpivasniki.shortfy.site.branchedpipeline.stage;

public interface InvocationStage{
    Object[] invoke(Object... args);
    IStageContext popInvokeContext();
}
