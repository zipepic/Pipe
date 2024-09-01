package podpivasniki.shortfy.site.branchedpipeline.enums;

public enum StageMode {
    TEMPLATE_SYNTAX_VALIDATION("Template Syntax Validation"),
    PROCESSING("Processing Mode");

    private final String mode;

    StageMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
