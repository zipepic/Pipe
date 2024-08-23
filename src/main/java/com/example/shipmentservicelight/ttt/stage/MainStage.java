package com.example.shipmentservicelight.ttt.stage;

import com.example.shipmentservicelight.ttt.annotations.StageInject;
import com.example.shipmentservicelight.ttt.args.HandlerArgumentWithValue;
import com.example.shipmentservicelight.ttt.args.HandlerArgumentsValidator;
import com.example.shipmentservicelight.ttt.enums.StagePhases;
import com.example.shipmentservicelight.ttt.ex.NoBeanOfType;
import com.example.shipmentservicelight.ttt.ex.StageException;
import com.example.shipmentservicelight.ttt.handlers.AbstractHandler;
import com.example.shipmentservicelight.ttt.handlers.IHandlerExecutor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * Класс {@code MainStage} это один из возможных видов упровляющей стадии,
 * отличается от своего родителя тем, что может с конфигурироваться
 * и дает возможность после этого выполниться
 * <p>
 * Этот класс расширяет функционал класса {@link Stage} и реализует интерфейсы {@link InvocationStage} и {@link ConfigurationStage}.
 * </p>
 */
public class MainStage extends Stage implements InvocationStage, ConfigurationStage{
    @Setter()
    @Accessors(chain = true)
    private StageContext stageContext;
    /**
     * Ебаная заглушка
     *
     * @param currentHandler основной обработчик, который будет ассоциирован с этой стадией.
     */
    protected MainStage(AbstractHandler currentHandler) {
        super(currentHandler);
    }

    /**
     * Инициализирует новый объект {@code MainStage} с указанным обработчиком.
     *
     * @param currentHandler основной обработчик, который будет ассоциирован с этой стадией.
     * @return новый экземпляр {@code MainStage}.
     */
    public static MainStage init(AbstractHandler currentHandler){
        return new MainStage(currentHandler);
    }


    /**
     * Короч эта залупа нужна когда уже накидан шаблон стадий и новых добовляться не планируется
     * Она устанавливает всем своим дочерним стадиям всяки настройки, может прокинуть контекст и т.д
     * После вызова этого метода можно выполнять {@code invoke}
     * Этот метод также выполняет маппинг(ниже опишу в классе  {@code Stage}) классов и переводит стадию в фазу "CONFIGURED".
     *
     *
     * @return текущий экземпляр {@code MainStage} после завершения конфигурации.
     * @throws StageException если стадия находится не в фазе "BUILDING".
     */


    @Override
    public MainStage configurate() {
        if(this.stagePhase != StagePhases.BUILDING)
            throw new StageException("Ты конфигурируещь какую то залупу", this.stagePhase);
        //TODO выполнить подготовку обработчиков, внедрить зависимости и т.д
        applyToAllStages(stage -> stage.setStagePhase(StagePhases.CONFIGURATION));
        this.inject();
        mappingClasses(this.mainOutPutClasses);
        applyToAllStages(stage -> stage.setStagePhase(StagePhases.CONFIGURED));
        return this;
    }
    private void inject(){
        this.applyToAllStages(stage->{
                    stage.mainHandlerExecutor.getFieldsByAnnotation(StageInject.class)
                            .forEach(field->{
                                try {
                                    field.setAccessible(true);
                                    field.set(stage.mainHandler, this.stageContext.getBean(field.getType()));
                                    field.setAccessible(false);
                                }catch (IllegalAccessException e) {
                                    throw new StageException(stage.stagePhase);
                                }
                            });
                }
        );
    }

    /**
     * Добавляет новый этап в текущую стадию. Этап добавляется только в том случае, если стадия находится в фазе "BUILDING" или "CONFIGURED".
     * Просто ебаная заглушка своего родителя
     *
     * @param s массив стадий, которые будут добавлены как этапы.
     * @return текущий экземпляр {@code MainStage} с добавленным этапом.
     * @throws StageException если стадия находится не в фазе "BUILDING" или "CONFIGURED".
     */
    @Override
    public MainStage attachStageStep(Stage... s){
        if(this.stagePhase != StagePhases.BUILDING && this.stagePhase != StagePhases.CONFIGURED)
            throw new StageException("Нельзя приатачить стадию из за фазы работы", this.stagePhase);

        StageStep stageStep = new StageStep(s);
        return (MainStage) attachStageStepSystem(stageStep);
    }

    /**
     * Выполняет вызов основной стадии с заданными аргументами.
     * Стадия должна быть предварительно сконфигурирована перед вызовом.
     *
     * @param args аргументы, необходимые для выполнения стадии.
     * @return массив объектов, представляющих результат выполнения стадии.
     * @throws StageException если стадия не была сконфигурирована перед вызовом.
     */
    @Override
    public Object[] invoke(Object... args) {
        if(this.stagePhase != StagePhases.CONFIGURED)
            throw new StageException("Нельзя запустить не сконфигурировав до конца", StagePhases.USER_INVOCATION);

        List<HandlerArgumentWithValue> mappedValue = HandlerArgumentWithValue.ofArgs(args);

        HandlerArgumentsValidator.validate(this.mainInputClasses, mappedValue, StagePhases.USER_INVOCATION);

        applyToAllStages(stage -> stage.setStagePhase(StagePhases.PROCESSING));

        List<HandlerArgumentWithValue> result = this.process(mappedValue);

        applyToAllStages(stage -> stage.setStagePhase(StagePhases.CONFIGURED));

        // Возвращаем результат в виде массива объектов
        return result.stream()
                .map(HandlerArgumentWithValue::getValue)
                .toArray();
    }
}
