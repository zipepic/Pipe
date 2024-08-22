package com.example.shipmentservicelight;

import com.example.shipmentservicelight.ttt.stage.MainStage;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.handlers.AbstractHandler;

public class Test {
    public static void main(String[] args) {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };
        MainStage m = MainStage.init(a1);
        System.out.println(m);
//        Stage s = Stage.init(a1);
//        System.out.println(s);
//        s.attachStageStep(Stage.init(a1));
//        System.out.println(s);
//        s.configurate();
//        System.out.println(s);
//        s.attachStageStep(Stage.init(a1));
//        System.out.println(s);
//        s.configurate();
//        System.out.println(s);

    }
}
