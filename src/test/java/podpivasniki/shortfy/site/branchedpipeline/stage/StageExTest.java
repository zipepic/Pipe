package podpivasniki.shortfy.site.branchedpipeline.stage;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.enums.StagePhases;
import podpivasniki.shortfy.site.branchedpipeline.ex.ProcessMethodNotFound;
import podpivasniki.shortfy.site.branchedpipeline.ex.StageException;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Bridge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class StageExTest {
    @Test
    public void simple_test_ex() {
        assertThrowsExactly(ProcessMethodNotFound.class, () -> {
            MainStage s = MainStage.init(new AbstractHandler() {
            });
        });
    }
    @Test
    public void simple_test_ex2() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };
        MainStage s = MainStage.init(a1);

        StageException exception = assertThrowsExactly(StageException.class, () -> {
            s.invoke(1);
        });
        assertEquals(StagePhases.BUILDING, exception.getStagePhase());
    }
    @Test
    public void simple_test_ex3() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(Integer s){
                return s;
            }
        };


        StageException exception = assertThrowsExactly(StageException.class, () -> {
            MainStage.init(a1).attachStageStep(Stage.init(a2));
        });
        assertEquals(StagePhases.BUILDING, exception.getStagePhase());
    }
    @Test
    public void mappingTest() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(Integer i){
                return i;
            }
        };

        BuildableStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(a2));

        StageException exception = assertThrowsExactly(StageException.class, () -> {
            s.build().configure();
        });
        assertEquals(StagePhases.BUILDING, exception.getStagePhase());

    }
}
