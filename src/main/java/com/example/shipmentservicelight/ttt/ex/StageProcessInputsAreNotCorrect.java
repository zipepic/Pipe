package com.example.shipmentservicelight.ttt.ex;

public class StageProcessInputsAreNotCorrect extends RuntimeException{
    public StageProcessInputsAreNotCorrect() {
        super();
    }

    public StageProcessInputsAreNotCorrect(String message) {
        super(message);
    }

    public StageProcessInputsAreNotCorrect(String message, Throwable cause) {
        super(message, cause);
    }
}
