package podpivasniki.shortfy.site.branchedpipeline.observers;

import lombok.Getter;
import lombok.Setter;

public class StageObserver implements Observer {
    @Setter
    private StageSubject subject;
    @Getter
    private String observerState;
    @Override
    public void update() {
        observerState = subject.getState();
    }
}
