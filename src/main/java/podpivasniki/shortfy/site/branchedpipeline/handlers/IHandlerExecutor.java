package podpivasniki.shortfy.site.branchedpipeline.handlers;

import podpivasniki.shortfy.site.branchedpipeline.args.HandlerArgument;
import podpivasniki.shortfy.site.branchedpipeline.args.HandlerArgumentWithValue;
import podpivasniki.shortfy.site.branchedpipeline.stage.IStageContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public interface IHandlerExecutor {
    List<Field>  getFieldsByAnnotation(Class<? extends Annotation> annotationClazz);
    List<HandlerArgument> getInputArguments();
    List<HandlerArgument> getOutPutArguments();
    List<HandlerArgumentWithValue> invokeHandlerProcess(List<HandlerArgumentWithValue> values);
    List<HandlerArgument> initSystemHandlersOutPuts(List<HandlerArgument> handlerArguments);
    void injectDependencies(IStageContext context);
}
