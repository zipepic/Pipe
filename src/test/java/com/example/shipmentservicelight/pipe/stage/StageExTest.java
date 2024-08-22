package com.example.shipmentservicelight.pipe.stage;

import com.example.shipmentservicelight.ttt.stage.MainStage;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.stage.Stage;
import com.example.shipmentservicelight.ttt.enums.StagePhases;
import com.example.shipmentservicelight.ttt.ex.ProcessMethodNotFound;
import com.example.shipmentservicelight.ttt.ex.StageException;
import com.example.shipmentservicelight.ttt.handlers.AbstractHandler;
import com.example.shipmentservicelight.ttt.handlers.Brigde;
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
        assertEquals(StagePhases.USER_INVOCATION, exception.getStagePhase());
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

        MainStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(a2));

        StageException exception = assertThrowsExactly(StageException.class, () -> {
            s.configurate();
        });
        assertEquals(StagePhases.CONFIGURATION, exception.getStagePhase());

    }
}
