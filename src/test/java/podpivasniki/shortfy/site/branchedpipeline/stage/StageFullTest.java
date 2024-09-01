package podpivasniki.shortfy.site.branchedpipeline.stage;

import podpivasniki.shortfy.site.branchedpipeline.annotations.StageInject;
import podpivasniki.shortfy.site.branchedpipeline.args.MultiType;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcessAfter;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcessBefore;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Bridge;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Dubler;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Swapper;
import org.junit.jupiter.api.Test;
import podpivasniki.shortfy.site.branchedpipeline.stage.configchain.StageContextConfigHandler;
import podpivasniki.shortfy.site.branchedpipeline.stats.BillingStats;
import podpivasniki.shortfy.site.branchedpipeline.testhandlers.ApiClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StageFullTest {
    @Test
    public void fullTest() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public List<String> proc(String s){
                List<String> r = new ArrayList<>();
                r.add(s.toString());
                r.add(s.toString());
                return r;
            }
            @HandlerProcessBefore
            public void fullLog(String args){
                System.out.println("До уже переопределенный");
                System.out.println(args);
            }

            @HandlerProcessAfter
            public void fullLogOutputs(List<String> args){
                System.out.println("После уже переопределенный");
                System.out.println(args);
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(List<String> s1, List<String> s2){
                return s1.size()+s2.size();
            }
        };

        InvocationStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Dubler()).attachStageStep(Stage.init(a2)), Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Swapper()))
                .build()
                .configure();

        Object[] result = s.invoke("a");


        assertEquals(2, result.length);
        assertEquals(Arrays.asList("a","a"), result[0]);
        assertEquals(4, result[1]);
    }

    @Test
    public void test2(){
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess(classes = {
                    List.class, String.class,
                    String.class,
                    Integer.class
            })
            public MultiType proc(String s){
                List<String> r = new ArrayList<>();
                r.add(s.toString());
                r.add(s.toString());
                return MultiType.of(r, "TEST_STRING", 10);
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(List<String> s1){
                return s1.size();
            }
        };
        AbstractHandler a3 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(Integer i1 , Integer i2){
                return i1+i2;
            }
        };

        InvocationStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(a2), Stage.init(new Swapper()))
                .attachStageStep(Stage.init(a3), Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Dubler()), Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()), Stage.init(new Swapper()))
                .build()
                .configure();

        Object[] res = s.invoke("a");
        assertEquals(12, res[0]);
        assertEquals("TEST_STRING", res[1]);
        assertEquals(12, res[2]);
    }
    @Test
    public void loaderTest(){
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(String s1){
                return s1.length();
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(){
                return 5;
            }
        };

        InvocationStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Bridge()),Stage.init(a2))
                .build()
                .configure();

        Object[] res = s.invoke("a");
        assertEquals(2,res.length);
        assertEquals(5, res[1]);
    }
    @Test
    public void terminatorTest(){
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(String s1){
                return s1.length();
            }
        };
        AbstractHandler a2 = new AbstractHandler() {
            @HandlerProcess
            public void proc(Integer i){
            }
        };

        InvocationStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(a2))
                .build()
                .configure();

        Object[] res = s.invoke("a");
        assertEquals(0,res.length);
    }
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
