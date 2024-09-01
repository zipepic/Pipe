package podpivasniki.shortfy.site.branchedpipeline.json;

public enum StageConstructorParamsTypes {
    API_REF("API_REF"),
    SUBJECT("SUBJECT"),
    OBJECT_REF("OBJECT_REF");
    private final String type;

    StageConstructorParamsTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
