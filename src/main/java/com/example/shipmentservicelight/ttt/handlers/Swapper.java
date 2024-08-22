package com.example.shipmentservicelight.ttt.handlers;

import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.args.MultiType;

public class Swapper extends AbstractHandler{
    @HandlerProcess(classes = {Object.class, Object.class})
    public MultiType proc(Object o1, Object o2){
        return MultiType.of(o2,o1);
    }

}
