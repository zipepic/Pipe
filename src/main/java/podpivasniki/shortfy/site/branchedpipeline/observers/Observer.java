package podpivasniki.shortfy.site.branchedpipeline.observers;

public interface Observer {
    void update();
    void setSubject(StageSubject subject);
}
