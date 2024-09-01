package podpivasniki.shortfy.site.branchedpipeline.testhandlers;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.annotations.StageInject;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.observers.StageSubject;

public class IntToIntWithConstructor extends AbstractHandler {
    @StageInject
    private ApiClass apiClass;

    @StageInject
    StageSubject subject;

    private final Integer in;

    public IntToIntWithConstructor(Integer i) {
        this.in = i;
    }

    @HandlerProcess
    public Integer proc(Integer i){
        subject.setState(String.valueOf(i * in * apiClass.getMultipluer()));
        return i * in * apiClass.getMultipluer();
    }
}
