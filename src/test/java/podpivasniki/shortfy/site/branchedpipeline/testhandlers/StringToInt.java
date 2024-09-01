package podpivasniki.shortfy.site.branchedpipeline.testhandlers;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.annotations.StageInject;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.observers.StageSubject;

public class StringToInt extends AbstractHandler {
    @StageInject
    ApiClass apiClass;

    @StageInject
    StageSubject subject;
    ImageParams imageParams;

    public StringToInt(ImageParams imageParams) {
        this.imageParams = imageParams;
    }

    @HandlerProcess
    public Integer proc(String s){
        subject.setState("Aaa");
        return s.length();
    }
}
