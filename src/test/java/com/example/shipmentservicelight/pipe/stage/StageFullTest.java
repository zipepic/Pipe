package com.example.shipmentservicelight.pipe.stage;

import com.example.shipmentservicelight.ttt.stage.MainStage;
import com.example.shipmentservicelight.ttt.args.MultiType;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.stage.Stage;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcessAfter;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcessBefore;
import com.example.shipmentservicelight.ttt.handlers.AbstractHandler;
import com.example.shipmentservicelight.ttt.handlers.Brigde;
import com.example.shipmentservicelight.ttt.handlers.Dubler;
import com.example.shipmentservicelight.ttt.handlers.Swapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StageFullTest {
    @Test
    public void fullTest() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public List<String> proc(String s){
                List<String> r = new ArrayList<>();
                r.add(s.toString());
                r.add(s.toString());
                return r;
            }
            @HandlerProcessBefore
            public void fullLog(String args){
                System.out.println("До уже переопределенный");
                System.out.println(args);
            }

            @HandlerProcessAfter
            public void fullLogOutputs(List<String> args){
                System.out.println("После уже переопределенный");
                System.out.println(args);
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(List<String> s1, List<String> s2){
                return s1.size()+s2.size();
            }
        };

        MainStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Dubler()).attachStageStep(Stage.init(a2)), Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Swapper()))
                .configurate();

        Object[] result = s.invoke("a");

        assertEquals(2, result.length);
        assertEquals(Arrays.asList("a","a"), result[0]);
        assertEquals(4, result[1]);
    }

    @Test
    public void test2(){
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess(classes = {List.class, String.class, Integer.class},
            genericTypes = {String.class})
            public MultiType proc(String s){
                List<String> r = new ArrayList<>();
                r.add(s.toString());
                r.add(s.toString());
                return MultiType.of(r, "TEST_STRING", 10);
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(List<String> s1){
                return s1.size();
            }
        };
        AbstractHandler a3 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(Integer i1 , Integer i2){
                return i1+i2;
            }
        };

        MainStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(a2), Stage.init(new Swapper()))
                .attachStageStep(Stage.init(a3), Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Dubler()), Stage.init(new Brigde()))
                .attachStageStep(Stage.init(new Brigde()), Stage.init(new Swapper()))
                .configurate();

        Object[] res = s.invoke("a");
        assertEquals(12, res[0]);
        assertEquals("TEST_STRING", res[1]);
        assertEquals(12, res[2]);
    }
}
