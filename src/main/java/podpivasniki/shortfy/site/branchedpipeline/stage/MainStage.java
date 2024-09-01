package podpivasniki.shortfy.site.branchedpipeline.stage;

import lombok.extern.slf4j.Slf4j;
import podpivasniki.shortfy.site.branchedpipeline.args.HandlerArgumentWithValue;
import podpivasniki.shortfy.site.branchedpipeline.args.HandlerArgumentsValidator;
import podpivasniki.shortfy.site.branchedpipeline.enums.StagePhases;
import podpivasniki.shortfy.site.branchedpipeline.stage.configchain.StageContextChain;
import podpivasniki.shortfy.site.branchedpipeline.stage.configchain.StageContextConfigHandler;
import podpivasniki.shortfy.site.branchedpipeline.stats.BillingStats;
import podpivasniki.shortfy.site.branchedpipeline.stats.ProfilerStats;
import podpivasniki.shortfy.site.branchedpipeline.ex.StageException;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;

import java.util.List;


/**
 * Класс {@code MainStage} это один из возможных видов упровляющей стадии,
 * отличается от своего родителя тем, что может с конфигурироваться
 * и дает возможность после этого выполниться
 * <p>
 * Этот класс расширяет функционал класса {@link Stage} и реализует интерфейсы {@link InvocationStage} и {@link ConfigurableStage}.
 * </p>
 */
@Slf4j
public class MainStage extends Stage implements InvocationStage, ConfigurableStage, BuildableStage {

    /**
     * Ебаная заглушка
     *
     * @param currentHandler основной обработчик, который будет ассоциирован с этой стадией.
     */
    private MainStage(AbstractHandler currentHandler) {
        super(currentHandler);
        this.stageContext = IStageContext.defaultStageContext();
    }

    /**
     * Контекст стадии, включающий данные для выполнения стадии.
     */
    private final IStageContext stageContext;

    private IStageContext invokeStageContext;


    /**
     * Инициализирует новый объект {@code MainStage} с указанным обработчиком.
     *
     * @param currentHandler основной обработчик, который будет ассоциирован с этой стадией.
     * @return новый экземпляр {@code MainStage}.
     */
    public static MainStage init(AbstractHandler currentHandler){
        return new MainStage(currentHandler);
    }



    public ConfigurableStage build(StageContextConfigHandler... handlers){
        whiteListPhases(StagePhases.BUILDING);

        configContext(stageContext, List.of(handlers), defaultMainContextBeans);

        mappingClasses(mainOutPutClasses);

        updateStagePhase(StagePhases.BUILT);

        return this;
    }
    /**
     * Короч жта залупа нужна когда уже накидан шаблон стадий и новых добовляться не планируется
     * Она устанавливает всем своим дочерним стадиям всяки настройки, может прокинуть контекст и т.д
     * После вызова этого метода можно выполнять {@code invoke}
     * Этот метод также выполняет маппинг(ниже опишу в классе  {@code Stage}) классов и переводит стадию в фазу "CONFIGURED".
     *
     *
     * @return текущий экземпляр {@code MainStage} после завершения конфигурации.
     * @throws StageException если стадия находится не в фазе "BUILDING".
     */
    @Override
    public InvocationStage configure(StageContextConfigHandler... handlers) {
        whiteListPhases(StagePhases.BUILT);

        updateStagePhase(StagePhases.CONFIGURATION);

        invokeStageContext = IStageContext.defaultStageContext();
        configContext(invokeStageContext, List.of(handlers), defaultInvokeContextStats);

        inject(stageContext.merge(invokeStageContext));

        updateStagePhase(StagePhases.CONFIGURED);
        return this;
    }


    private void inject(IStageContext stageContextLocal){
        log.debug("Injecting dependencies into all stages for UUID: {}", stageContextLocal.getStageUUID());
        this.applyToAllStages(stage->{stage.mainHandlerExecutor.injectDependencies(stageContextLocal);});
    }

    /**
     * Добавляет новый этап в текущую стадию. Этап добавляется только в том случае, если стадия находится в фазе "BUILDING" или "CONFIGURED".
     * Просто ебаная заглушка своего родителя
     *
     * @param s массив стадий, которые будут добавлены как этапы.
     * @return текущий экземпляр {@code MainStage} с добавленным этапом.
     */
    @Override
    public MainStage attachStageStep(Stage... s){
        StageStep stageStep = new StageStep(s);
        MainStage mainStage = (MainStage) attachStageStepSystem(stageStep);

        return mainStage;
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
        whiteListPhases(StagePhases.CONFIGURED);


        List<HandlerArgumentWithValue> userInputs = HandlerArgumentWithValue.ofArgs(args);
        HandlerArgumentsValidator.validate(this.mainInputClasses, userInputs, StagePhases.USER_INVOCATION);

        updateStagePhase(StagePhases.PROCESSING);

        List<HandlerArgumentWithValue> result = this.process(userInputs);


        //TODO отчистка всех inject для handlers
        updateStagePhase(StagePhases.COMPLETED);
        return result.stream()
                .map(HandlerArgumentWithValue::getValue)
                .toArray();
    }

    @Override
    public IStageContext popInvokeContext(){
        whiteListPhases(StagePhases.COMPLETED);

        IStageContext temp = invokeStageContext;
        invokeStageContext = null;
        return temp;
    }


    private void configContext(IStageContext stageContext, List<StageContextConfigHandler> handlers, StageContextConfigHandler deafault){
        StageContextChain contextChain = new StageContextChain();
        contextChain.addHandler(deafault);
        handlers.forEach(contextChain::addHandler);

        contextChain.process(stageContext);
    }

    protected StageContextConfigHandler defaultInvokeContextStats = new StageContextConfigHandler() {
        @Override
        public void handle(IStageContext context) {
            context.registerBean(new BillingStats());
            context.registerBean(new ProfilerStats());
        }
    };
    private final StageContextConfigHandler defaultMainContextBeans = new StageContextConfigHandler() {
        @Override
        public void handle(IStageContext context) {

        }
    };


}
