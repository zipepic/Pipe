package podpivasniki.shortfy.site.branchedpipeline.stage;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.annotations.StageInject;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Dubler;
import org.junit.jupiter.api.Test;
import podpivasniki.shortfy.site.branchedpipeline.stage.configchain.StageContextConfigHandler;
import podpivasniki.shortfy.site.branchedpipeline.testhandlers.ApiClass;

import static org.junit.jupiter.api.Assertions.*;

public class InjectionTest {
    @Test
    public void injectTest() {
        AbstractHandler a1 = new AbstractHandler() {
            @StageInject
            private Integer multiplier;
            @HandlerProcess
            public Integer proc(Integer i) {
                return i * multiplier;
            }
        };
        StageContextConfigHandler stageContextConfigHandler = new StageContextConfigHandler() {
            @Override
            public void handle(IStageContext context) {
                context.registerBean(Integer.class, 2);
            }
        };

        InvocationStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Dubler()))
                .attachStageStep(Stage.init(a1), Stage.init(a1))
                .build(stageContextConfigHandler)
                .configure();
        Object[] result = s.invoke(5);
        assertEquals(2, result.length);
        assertEquals(20, result[0]);
        assertEquals(20, result[1]);
    }
    @Test
    public void injectTestInterface() {
        AbstractHandler a1 = new AbstractHandler() {
            @StageInject
            private Iterface multiplier;
            @HandlerProcess
            public Iterface proc() {
               return multiplier;
            }
        };

        Imp i = new Imp();
        StageContextConfigHandler stageContextConfigHandler2 = new StageContextConfigHandler() {
            @Override
            public void handle(IStageContext context) {
                context.registerBean(i);
            }
        };
        InvocationStage s = MainStage.init(a1)
                .build(stageContextConfigHandler2)
                .configure();

        Object[] result = s.invoke();
        assertEquals(1, result.length);
        assertTrue(i == result[0]);


    }
    @Test
    public void injectTestClass() {
        AbstractHandler a2 = new AbstractHandler() {
            @StageInject(required = true)
            private Imp multiplier;
            @HandlerProcess
            public Iterface proc() {
                return multiplier;
            }
        };

        Iterface i = new Imp();
        StageContextConfigHandler stageContextConfigHandler = new StageContextConfigHandler() {
            @Override
            public void handle(IStageContext context) {
                context.registerBean(i);
            }
        };

        InvocationStage s2 = MainStage.init(a2)
                .build(stageContextConfigHandler)
                .configure();
        Object[] result2 = s2.invoke();
        assertEquals(1, result2.length);
        assertTrue(i == result2[0]);
    }
    @Test
    public void injectTestClassRequeredFalse() {
        AbstractHandler a1 = new AbstractHandler() {
            @StageInject(required = true)
            private ApiClass multiplier;
            @HandlerProcess
            public Integer proc() {
                return 10;
            }
        };

        assertThrows(RuntimeException.class, ()->{
            InvocationStage s2 = MainStage.init(a1)
                    .build()
                    .configure();
        });
    }
}
interface Iterface{
    int get();
}
class Imp implements Iterface{

    @Override
    public int get() {
        return -100;
    }
}