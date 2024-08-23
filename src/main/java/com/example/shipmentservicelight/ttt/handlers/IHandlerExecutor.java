package com.example.shipmentservicelight.ttt.handlers;

import com.example.shipmentservicelight.ttt.args.HandlerArgument;
import com.example.shipmentservicelight.ttt.args.HandlerArgumentWithValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public interface IHandlerExecutor {
    List<Field>  getFieldsByAnnotation(Class<? extends Annotation> annotationClazz);
    List<HandlerArgument> getInputArguments();
    List<HandlerArgument> getOutPutArguments();
    List<HandlerArgumentWithValue> invokeHandlerProcess(List<HandlerArgumentWithValue> values);
    List<HandlerArgument> initSystemHandlersOutPuts(List<HandlerArgument> handlerArguments);
}
