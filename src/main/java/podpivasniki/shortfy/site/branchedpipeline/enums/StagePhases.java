package podpivasniki.shortfy.site.branchedpipeline.enums;

public enum StagePhases {
    BUILDING("Building"),
    BUILT("Built"),
    CONFIGURATION("Configuration"),
    CONFIGURED("Configured"),
    USER_INVOCATION("User Invocation"),
    PROCESSING("Processing"),
    COMPLETED("Completed");

    private final String phaseName;

    StagePhases(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getPhaseName() {
        return phaseName;
    }
}

