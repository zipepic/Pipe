package com.example.shipmentservicelight.ttt.handlers;

import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.args.MultiType;

public class Dubler extends AbstractHandler{
    @HandlerProcess(classes = {Object.class, Object.class})
    public MultiType proc(Object o){
        return MultiType.of(o,o);
    }
}
