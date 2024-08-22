package com.example.shipmentservicelight.ttt.stage;

import com.example.shipmentservicelight.ttt.handlers.HandlerMethodInvoker;
import com.example.shipmentservicelight.ttt.handlers.IHandlerExecutor;
import com.example.shipmentservicelight.ttt.args.HandlerArgument;
import com.example.shipmentservicelight.ttt.args.HandlerArgumentWithValue;
import com.example.shipmentservicelight.ttt.args.HandlerArgumentsValidator;
import com.example.shipmentservicelight.ttt.enums.StagePhases;
import com.example.shipmentservicelight.ttt.ex.StageException;
import com.example.shipmentservicelight.ttt.handlers.AbstractHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Класс Stage представляет этап обработки, который может состоять из одного или нескольких шагов {@link StageStep}.
 * Он отвечает за составление стадии путем добавления шагов
 */
public class Stage implements IStage {
    /**
     * Текущая фаза стадии. Уровень доступа для изменения - пакетный.
     */
    @Setter(AccessLevel.PACKAGE)
    protected StagePhases stagePhase;

    /**
     * Список шагов, которые составляют текущую стадию.
     */
    protected final List<StageStep> stageSteps = new ArrayList<>();

    /**
     * Основной обработчик, который будет выполнен на данном этапе.
     */
    protected final AbstractHandler mainHandler;

    /**
     * Обертка над основным обработчиком {@link IHandlerExecutor}
     */
    protected final IHandlerExecutor mainHandlerExecutor;


    /**
     * Входные аргументы для основного обработчика.
     */
    @Getter(AccessLevel.PACKAGE)
    protected final List<HandlerArgument> mainInputClasses;

    /**
     * Выходные аргументы для основного обработчика.
     */
    protected final List<HandlerArgument> mainOutPutClasses;


    /**
     * Текущие выходные аргументы, обновляемые после каждого шага.
     */
    @Getter(AccessLevel.PACKAGE)
    private List<HandlerArgument> currentOutPutClasses;

    /**
     * Результаты выполнения текущей стадии.
     */
    private List<HandlerArgumentWithValue> currentResult = null;

    /**
     * Текущие отображаемые классы аргументов.
     */
    private List<HandlerArgument> currentMappedClasses = null;


    /**
     * Контекст стадии, включающий данные для выполнения стадии.
     */
    private StageContext stageContext;

    /**
     * Конструктор для создания стадии с заданным основным обработчиком.
     *
     * @param currentHandler основной обработчик стадии.
     */
    protected Stage(AbstractHandler currentHandler) {
        this.mainHandler = currentHandler;
        this.mainHandlerExecutor = HandlerMethodInvoker.of(currentHandler);
        this.mainInputClasses = this.mainHandlerExecutor.getInputArguments();
        this.currentOutPutClasses = this.mainHandlerExecutor.getOutPutArguments();
        this.mainOutPutClasses = this.mainHandlerExecutor.getOutPutArguments();
        this.stagePhase = StagePhases.BUILDING;
    }


    /**
     * Инициализирует стадию с заданным обработчиком.
     *
     * @param abstractHandler основной обработчик.
     * @return новый экземпляр Stage.
     */
    public static Stage init(AbstractHandler abstractHandler){
        return new Stage(abstractHandler);
    }


    /**
     * Добавляет шаг или шаги к текущей стадии.
     *
     * @param s шаги, которые необходимо добавить.
     * @return текущий экземпляр Stage.
     */
    public Stage attachStageStep(Stage... s){
        StageStep stageStep = new StageStep(s);
        return attachStageStepSystem(stageStep);
    }


    /**
     * Системный метод для добавления шага к стадии. Нихуя интересного
     *
     * @param stageStep шаг, который необходимо добавить.
     * @return текущий экземпляр Stage.
     * @throws StageException если стадия находится в фазе PROCESSING.
     */
    protected Stage attachStageStepSystem(StageStep stageStep){
        if(this.stagePhase == StagePhases.PROCESSING)
            throw new StageException("Еблан, пытаешся добавить шаг к сдадии которая запущенна или с конфигурированна", this.stagePhase);

        applyToAllStages(stage -> stage.setStagePhase(StagePhases.BUILDING));

        HandlerArgumentsValidator.validate(this.currentOutPutClasses, stageStep.getAllArgInputs(), StagePhases.BUILDING);

        this.stageSteps.add(stageStep);

        this.currentOutPutClasses = stageStep.getAllArgOutPuts();

        return this;
    }

    /**
     * Выполняет маппинг классов аргументов для текущей стадии.
     * Короч, мапптнг это когда у нас добавляется на одном из этапов какойнибудь Bridge,
     * и так как он возврящяет Object, то у нас в currentOutPutClasses копится можно
     * скзать мусорные объекты, они ни как не мешают добавлять новые шаги после себя,
     * но проверки можно скзать фективные, так вот что бы все было пучком,
     * надо перед запуском пайплайна заменить Object.class на реальные классы
     * и все перепроверить
     *
     * @param list список аргументов для маппинга.
     * @return список отображаемых аргументов.
     */
    List<HandlerArgument> mappingClasses(List<HandlerArgument> list){
        currentMappedClasses = this.mainHandlerExecutor.initSystemHandlersOutPuts(list);

        for(StageStep s: stageSteps){
            this.currentMappedClasses = s.mappedClasses(currentMappedClasses);
        }

        return currentMappedClasses;
    }


    /**
     * Запускает процесс выполнения стадии с заданными аргументами. Тут особо ничего больше не скажешь
     *
     * @param args аргументы для выполнения стадии.
     * @return результаты выполнения стадии.
     * @throws StageException если стадия находится не в фазе PROCESSING.
     */
    List<HandlerArgumentWithValue> process(List<HandlerArgumentWithValue> args){
        if(this.stagePhase != StagePhases.PROCESSING)
            throw new StageException("Нельзя запустить так как стадия не рабочая", this.stagePhase);


        HandlerArgumentsValidator.validate(this.mainInputClasses, args, StagePhases.PROCESSING);


        this.currentResult = this.mainHandlerExecutor.invokeHandlerProcess(args);

        for(StageStep s: stageSteps){
            this.currentResult = s.invokeStageStep(currentResult);
        }

        return currentResult;
    }


    /**
     * Применяет указанное действие ко всем стадиям и их шагам.
     *
     * @param action действие, которое необходимо выполнить.
     */
    protected void applyToAllStages(Consumer<Stage> action) {
        action.accept(this);

        for (StageStep step : stageSteps) {
            for (Stage stage : step.getStages()) {
                stage.applyToAllStages(action);
            }
        }
    }
}
