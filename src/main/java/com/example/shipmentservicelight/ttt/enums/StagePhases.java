package com.example.shipmentservicelight.ttt.enums;

public enum StagePhases {
    BUILDING("Building"),
    CONFIGURATION("Configuration"),
    CONFIGURED("Configured"),
    USER_INVOCATION("User Invocation"),
    PROCESSING("Processing");

    private final String phaseName;

    StagePhases(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getPhaseName() {
        return phaseName;
    }
}

