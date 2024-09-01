package podpivasniki.shortfy.site.branchedpipeline.stage;

import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.args.MultiType;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Bridge;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Dubler;
import podpivasniki.shortfy.site.branchedpipeline.handlers.Swapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StageSystemComponentsTest {
    @Test
    public void systemComponentsTest() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess(classes = {String.class, String.class})
            public MultiType proc(String s1){
                return MultiType.of(s1,s1+"R");
            }
        };

        InvocationStage s = MainStage.init(a1).attachStageStep(Stage.init(new Swapper()))
                .build()
                .configure();

        Object[] result = s.invoke("Hello");

        assertEquals(2, result.length);
        assertEquals("HelloR", result[0]);
        assertEquals("Hello", result[1]);
    }
    @Test
    public void systemComponentsTest2() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        InvocationStage s = MainStage.init(a1).attachStageStep(Stage.init(new Dubler()))
                .build()
                .configure();

        Object[] result = s.invoke("Hello");

        assertEquals(2, result.length);
        assertEquals("Hello", result[0]);
        assertEquals("Hello", result[1]);
    }
    @Test
    public void systemComponentsTest3() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        InvocationStage s = MainStage.init(a1).attachStageStep(Stage.init(new Bridge()))
                .build()
                .configure();

        Object[] result = s.invoke("Hello");

        assertEquals(1, result.length);
        assertEquals("Hello", result[0]);
    }
    @Test
    public void systemComponentsTest4() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        InvocationStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Dubler()),Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()),Stage.init(new Dubler()))
                .build()
                .configure();

        Object[] result = s.invoke("Hello");

        assertEquals(16, result.length);
        assertEquals("Hello", result[0]);
        assertEquals("Hello", result[15]);
    }
    @Test
    public void systemComponentsTest5() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        InvocationStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .attachStageStep(Stage.init(new Bridge()))
                .build()
                .configure();

        Object[] result = s.invoke("Hello");

        assertEquals(1, result.length);
        assertEquals("Hello", result[0]);
    }
    @Test
    public void systemComponentsTest6() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        InvocationStage s = MainStage.init(a1)
                .attachStageStep(Stage.init(new Dubler()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .attachStageStep(Stage.init(new Swapper()))
                .build()
                .configure();

        Object[] result = s.invoke("Hello");

        assertEquals(2, result.length);
        assertEquals("Hello", result[0]);
        assertEquals("Hello", result[1]);
    }
}
