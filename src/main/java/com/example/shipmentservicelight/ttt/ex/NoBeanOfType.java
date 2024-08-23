package com.example.shipmentservicelight.ttt.ex;

public class NoBeanOfType extends RuntimeException{
    public NoBeanOfType() {
        super();
    }

    public NoBeanOfType(String message) {
        super(message);
    }

    public NoBeanOfType(String message, Throwable cause) {
        super(message, cause);
    }
}
