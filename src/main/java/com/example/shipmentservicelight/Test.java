package com.example.shipmentservicelight;

import com.example.shipmentservicelight.ttt.annotations.StageInject;
import com.example.shipmentservicelight.ttt.handlers.HandlerMethodInvoker;
import com.example.shipmentservicelight.ttt.stage.MainStage;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.handlers.AbstractHandler;

public class Test {
    public static void main(String[] args) {
        AbstractHandler a1 = new AbstractHandler() {
            @StageInject
            public Integer o;
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };
        HandlerMethodInvoker handlerMethodInvoker= HandlerMethodInvoker.of(a1);
        System.out.println(handlerMethodInvoker.getFieldsByAnnotation(StageInject.class));
//
    }
}
