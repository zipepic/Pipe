package com.example.shipmentservicelight.ttt.ex;

public class UserInputsAreNotCorrect extends RuntimeException{
    public UserInputsAreNotCorrect() {
        super();
    }

    public UserInputsAreNotCorrect(String message) {
        super(message);
    }

    public UserInputsAreNotCorrect(String message, Throwable cause) {
        super(message, cause);
    }
}
