package podpivasniki.shortfy.site.branchedpipeline.test;

import org.junit.jupiter.api.Test;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcessAfter;
import podpivasniki.shortfy.site.branchedpipeline.annotations.StageInject;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Bridge;
import podpivasniki.shortfy.site.branchedpipeline.stage.IStageContext;
import podpivasniki.shortfy.site.branchedpipeline.stage.InvocationStage;
import podpivasniki.shortfy.site.branchedpipeline.stage.MainStage;
import podpivasniki.shortfy.site.branchedpipeline.stage.Stage;
import podpivasniki.shortfy.site.branchedpipeline.stage.configchain.StageContextConfigHandler;
import podpivasniki.shortfy.site.branchedpipeline.stats.BillingStats;
import podpivasniki.shortfy.site.branchedpipeline.testhandlers.ApiClass;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StageTtest {
    @Test
    public void test(){
        AbstractHandler a1 = new AbstractHandler() {
            @StageInject(required = true)
            BillingStats billingStats;

            @HandlerProcess
            public Integer proc(String s){
                billingStats.increment(s.length());
                return s.length();
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @StageInject(required = true)
            BillingStats billingStats;
            @StageInject
            ApiClass apiClass;

            @HandlerProcessAfter
            public void after(Integer s){
                billingStats.increment(s*apiClass.getMultipluer());
            }

            @HandlerProcess
            public Integer proc(Integer s){
                return s;
            }
        };
        ApiClass apiClass = new ApiClass(100);
        InvocationStage stage =  MainStage.init(a1)
                .attachStageStep(Stage.init(a2))
                .attachStageStep(Stage.init(new Bridge()))
                .build(new StageContextConfigHandler() {
                    @Override
                    public void handle(IStageContext context) {
                        context.registerBean(apiClass);
                    }
                })
                .configure();
        String input = "aaaa";
        stage.invoke(input);
        IStageContext context = stage.popInvokeContext();
        assertEquals(input.length()+input.length()*apiClass.getMultipluer(), context.getBean(BillingStats.class).getBill());


    }
}
