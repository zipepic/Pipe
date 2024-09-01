package podpivasniki.shortfy.site.branchedpipeline.testhandlers;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcessAfter;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcessBefore;
import podpivasniki.shortfy.site.branchedpipeline.annotations.StageInject;
import podpivasniki.shortfy.site.branchedpipeline.stats.BillingStats;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.observers.StageSubject;
import podpivasniki.shortfy.site.branchedpipeline.stats.ProfilerStats;

public class StringToIntWithBilling extends AbstractHandler {
    @StageInject
    ApiClass apiClass;

    @StageInject
    StageSubject subject;
    @StageInject(required = true)
    private BillingStats billingSubject;
//
//    @StageInject(required = true)
//    private ProfilerStats profilerStats;

    ImageParams imageParams;

    public StringToIntWithBilling(ImageParams imageParams) {
        this.imageParams = imageParams;
    }
    @HandlerProcessBefore
    public void handle(Object... o){
//        profilerStats.setStart(System.currentTimeMillis());
    }
    @HandlerProcessAfter
    public void handleAfter(Integer i){
//        profilerStats.setEnd(System.currentTimeMillis());
    }

    @HandlerProcess
    public Integer proc(String s){
        billingSubject.increment(100);
        subject.setState("Aaa");
        return s.length();
    }
}
