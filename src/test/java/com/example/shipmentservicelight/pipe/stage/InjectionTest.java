package com.example.shipmentservicelight.pipe.stage;

import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcessAfter;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcessBefore;
import com.example.shipmentservicelight.ttt.annotations.StageInject;
import com.example.shipmentservicelight.ttt.handlers.AbstractHandler;
import com.example.shipmentservicelight.ttt.handlers.Brigde;
import com.example.shipmentservicelight.ttt.handlers.Dubler;
import com.example.shipmentservicelight.ttt.handlers.Swapper;
import com.example.shipmentservicelight.ttt.stage.MainStage;
import com.example.shipmentservicelight.ttt.stage.Stage;
import com.example.shipmentservicelight.ttt.stage.StageContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        MainStage s = MainStage.init(a1)
                .setStageContext(new StageContext().registerBean(Integer.class,2))
                .configurate();
        Object[] result = s.invoke(5);
        assertEquals(1, result.length);
        assertEquals(10, result[0]);
    }
}
