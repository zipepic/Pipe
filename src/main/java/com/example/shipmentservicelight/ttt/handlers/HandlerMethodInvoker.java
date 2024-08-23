package com.example.shipmentservicelight.ttt.handlers;

import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcessAfter;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcessBefore;
import com.example.shipmentservicelight.ttt.args.HandlerArgument;
import com.example.shipmentservicelight.ttt.args.HandlerArgumentWithValue;
import com.example.shipmentservicelight.ttt.args.MultiType;
import com.example.shipmentservicelight.ttt.ex.ProcessMethodNotFound;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class HandlerMethodInvoker implements IHandlerExecutor {
    private final AbstractHandler handler;
    private final Method processMethod;
    private final List<Method> beforeMethods;
    private final List<Method> afterMethods;

    private HandlerMethodInvoker(AbstractHandler handler) {
        this.handler = handler;
        this.processMethod = getHandlerProcessMethod();
        this.beforeMethods = getMethodsByAnnotation(HandlerProcessBefore.class);
        this.afterMethods = getMethodsByAnnotation(HandlerProcessAfter.class);
    }

    public static HandlerMethodInvoker of(AbstractHandler handler){
        return new HandlerMethodInvoker(handler);
    }

    private  <T extends Annotation> List<Method> getMethodsByAnnotation(Class<T> annotationClazz) {
        List<Method> result = new ArrayList<>();
        Class<?> clazz = handler.getClass();

        while (clazz != null) {
            // Поиск методов с аннотацией в текущем классе
            Optional<Method> method = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(annotationClazz))
                    .findFirst();

            if (method.isPresent()) {
                 result.add(method.get());
            }

            // Переход к суперклассу
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    private Method getHandlerProcessMethod(){
        return Arrays.stream(handler.getClass().getMethods())
                .filter(x -> x.isAnnotationPresent(HandlerProcess.class))
                .findFirst().orElseThrow(() -> new ProcessMethodNotFound("Пизда, не нашелся ни один норм метод"));
    }
    public List<Field>  getFieldsByAnnotation(Class<? extends Annotation> annotationClazz) {
        return Arrays.stream(handler.getClass().getDeclaredFields())
                .filter(field->
                        field.isAnnotationPresent(annotationClazz))
                .collect(Collectors.toList());
    }

    @Override
    public List<HandlerArgument> getInputArguments() {
        // Получаем типы параметров метода
        Type[] parameterTypes = processMethod.getGenericParameterTypes();
        return HandlerArgument.init(parameterTypes);
    }
    @Override
    public List<HandlerArgument> getOutPutArguments() {
        Type returnType = this.processMethod.getGenericReturnType();

        // Проверка на тип MultiType
        if (this.processMethod.getReturnType().equals(MultiType.class)) {
            HandlerProcess annotation = this.processMethod.getAnnotation(HandlerProcess.class);
            return HandlerArgument.initByMultiply(annotation);
        }

        return Arrays.asList(HandlerArgument.init(returnType));
    }

    private void invokeBeforeMethods(Object[] inputs){
        try {
            for (Method beforeMethod : this.beforeMethods) {
                beforeMethod.setAccessible(true);
                if (beforeMethod.isVarArgs()) {
                    // Если метод принимает varargs, нужно передать массив как единственный аргумент
                    beforeMethod.invoke(handler, (Object) inputs);
                } else {
                    // В обычном случае просто передаем аргументы
                    beforeMethod.invoke(handler, inputs);
                }
                beforeMethod.setAccessible(false);
            }

        }catch (Exception e) {
            throw new RuntimeException("Чет нихуя не получилось вызвать методы перед главным", e);
        }
    }
    private void invokeAfterMethods(Object[] output){
        try {
            for(Method afterMethod: this.afterMethods){
                afterMethod.setAccessible(true);
                if (afterMethod.isVarArgs()) {
                    // Если метод принимает varargs, нужно передать массив как единственный аргумент
                    afterMethod.invoke(handler, (Object) output);
                } else {
                    // В обычном случае просто передаем аргументы
                    afterMethod.invoke(handler, output);
                }
                afterMethod.setAccessible(false);
            }

        }catch (Exception e) {
            throw new RuntimeException("Чет нихуя не получилось вызвать методы после главного", e);
        }
    }


    private Object invokeHandlerProcess(Object[] inputs) {
        try {
            //For tests
            this.processMethod.setAccessible(true);
            Object returnValue = this.processMethod.invoke(handler, inputs);
            this.processMethod.setAccessible(false);
            return returnValue;
        } catch (Exception e) {
            throw new RuntimeException("Чет нихуя не получилось вызвать эту залупень", e);
        }

    }

    @Override
    public List<HandlerArgumentWithValue> invokeHandlerProcess(List<HandlerArgumentWithValue> values){
        Object[] o = values.stream().map(HandlerArgumentWithValue::getValue).toArray();

        invokeBeforeMethods(o);
        Object res = invokeHandlerProcess(o);
        List<HandlerArgumentWithValue> resList;
        if(res.getClass().equals(MultiType.class)){
            resList = new ArrayList<>();
            Object[] args = ((MultiType) res).getArgs();
            for(Object a:args){
                resList.add(HandlerArgumentWithValue.of(a));
            }

        }else {
            resList  = Arrays.asList(HandlerArgumentWithValue.of(res));
        }
        Object[] resObjects = resList.stream().map(x-> x.getValue()).toArray();
        invokeAfterMethods(resObjects);

        return resList;
    }

    @Override
    public List<HandlerArgument> initSystemHandlersOutPuts(List<HandlerArgument> handlerArguments){
        if(handler.getClass().equals(Dubler.class)){
            if(handlerArguments.get(0).isObject())
                throw new RuntimeException("Мапинг объекта object не имеет смысла");
            return Arrays.asList(handlerArguments.get(0), handlerArguments.get(0));
        }
        if(handler.getClass().equals(Swapper.class)){
            if(handlerArguments.get(0).isObject()|| handlerArguments.get(1).isObject())
                throw new RuntimeException("Мапинг объекта object не имеет смысла");
            return Arrays.asList(handlerArguments.get(1), handlerArguments.get(0));
        }
        if(handler.getClass().equals(Brigde.class))
        {
            if(handlerArguments.get(0).isObject())
                throw new RuntimeException("Мапинг объекта object не имеет смысла");
            return Arrays.asList(handlerArguments.get(0));
        }


        return getOutPutArguments();
    }
}
