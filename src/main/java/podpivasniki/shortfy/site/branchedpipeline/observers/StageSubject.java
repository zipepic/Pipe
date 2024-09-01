package podpivasniki.shortfy.site.branchedpipeline.observers;

import java.util.List;

public class StageSubject extends Subject{
    private String state;

    public StageSubject(List<Observer> observers) {
        super(observers);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        myNotify();
    }
}