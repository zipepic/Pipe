package podpivasniki.shortfy.site.branchedpipeline.json;

import podpivasniki.shortfy.site.branchedpipeline.observers.Observer;
import podpivasniki.shortfy.site.branchedpipeline.observers.StageSubject;
import podpivasniki.shortfy.site.branchedpipeline.stage.IStageContext;
import podpivasniki.shortfy.site.branchedpipeline.stage.configchain.StageContextConfigHandler;

import java.util.List;

public class SubjectContextMapper implements StageContextConfigHandler {
    private final List<Observer> observers;
    private final boolean allowedSubject;

    public SubjectContextMapper(List<Observer> observers, boolean allowedSubject) {
        this.observers = observers;
        this.allowedSubject = allowedSubject;
    }

    @Override
    public void handle(IStageContext context) {
        if(allowedSubject)
        {
            StageSubject subject = new StageSubject(observers);
            for(Observer o: observers){
                o.setSubject(subject);
            }
            context.registerBean(subject);
        }else {
            context.registerBean(new StageSubject(null));
        }
    }
}
