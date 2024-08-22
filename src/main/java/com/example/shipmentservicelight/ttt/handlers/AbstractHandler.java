package com.example.shipmentservicelight.ttt.handlers;

import com.example.shipmentservicelight.ttt.annotations.HandlerProcessAfter;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcessBefore;

public abstract class AbstractHandler {
    @HandlerProcessBefore
    public void fullLogInput(Object... args){
        System.out.println("До");
        for(Object o: args)
            System.out.println(o);
    }
    @HandlerProcessAfter
    public void fullLogOutputs(Object... args){
        System.out.println("После");
        for(Object o: args)
            System.out.println(o);
    }
}
