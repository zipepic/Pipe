package com.example.shipmentservicelight.pipe.stage;

import com.example.shipmentservicelight.ttt.stage.MainStage;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.args.MultiType;
import com.example.shipmentservicelight.ttt.stage.Stage;
import com.example.shipmentservicelight.ttt.handlers.AbstractHandler;
import com.example.shipmentservicelight.ttt.handlers.Brigde;
import com.example.shipmentservicelight.ttt.handlers.Dubler;
import com.example.shipmentservicelight.ttt.handlers.Swapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StageSystemComponentsTest {
    @Test
    public void systemComponentsTest() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess(classes = {String.class, String.class})
            public MultiType proc(String s1){
                return MultiType.of(s1,s1+"R");
            }
        };

        MainStage s = MainStage.init(a1).attachStageStep(Stage.init(new Swapper()))
                .configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(2, result.length);
        assertEquals("HelloR", result[0]);
        assertEquals("Hello", result[1]);
    }
    @Test
    public void systemComponentsTest2() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        MainStage s = MainStage.init(a1).attachStageStep(Stage.init(new Dubler()))
                .configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(2, result.length);
        assertEquals("Hello", result[0]);
        assertEquals("Hello", result[1]);
    }
    @Test
    public void systemComponentsTest3() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        MainStage s = MainStage.init(a1).attachStageStep(Stage.init(new Brigde()))
                .configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(1, result.length);
        assertEquals("Hello", result[0]);
    }
    @Test
    public void systemComponentsTest4() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        MainStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Dubler()),Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()))
                .configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(16, result.length);
        assertEquals("Hello", result[0]);
        assertEquals("Hello", result[15]);
    }
    @Test
    public void systemComponentsTest5() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        MainStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()))
                .configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(1, result.length);
        assertEquals("Hello", result[0]);
    }
    @Test
    public void systemComponentsTest6() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        MainStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(2, result.length);
        assertEquals("Hello", result[0]);
        assertEquals("Hello", result[1]);
    }
}
