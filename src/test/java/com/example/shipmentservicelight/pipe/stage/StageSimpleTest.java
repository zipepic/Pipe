package com.example.shipmentservicelight.pipe.stage;

import com.example.shipmentservicelight.ttt.stage.MainStage;
import com.example.shipmentservicelight.ttt.annotations.HandlerProcess;
import com.example.shipmentservicelight.ttt.args.MultiType;
import com.example.shipmentservicelight.ttt.handlers.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StageSimpleTest {

    @Test
    public void simple_test2() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String proc(String s){
                return s;
            }
        };

        MainStage s = MainStage.init(a1).configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(1, result.length);
        assertEquals("Hello", result[0]);
    }
    @Test
    public void simple_test3() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(String s){
                return s.length();
            }
        };

        MainStage s = MainStage.init(a1).configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(1, result.length);
        assertEquals(5, result[0]);
    }
    @Test
    public void simple_test4() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public Integer proc(String s1, String s2){
                return s1.length() + s2.length();
            }
        };

        MainStage s = MainStage.init(a1).configurate();

        Object[] result = s.invoke("Hello", "Hello");

        assertEquals(1, result.length);
        assertEquals(10, result[0]);
    }
    @Test
    public void simple_test5() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public List<String> proc(String s1, String s2){
                return Arrays.asList(s1,s2);
            }
        };

        MainStage s = MainStage.init(a1).configurate();

        Object[] result = s.invoke("Hello", "Hello");

        assertEquals(1, result.length);
        assertEquals(Arrays.asList("Hello","Hello"), result[0]);
    }
    @Test
    public void simple_test6() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess
            public String[] proc(String s1, String s2){
                return new String[]{s1,s2};
            }
        };

        MainStage s = MainStage.init(a1).configurate();

        Object[] result = s.invoke("Hello", "Hello");

        assertEquals(1, result.length);
        assertArrayEquals(new String[]{"Hello", "Hello"}, (Object[]) result[0]);
    }

    @Test
    public void simple_test7() {
        AbstractHandler a1 = new AbstractHandler() {
            @HandlerProcess(classes = {String.class, String.class})
            public MultiType proc(String s1){
                return MultiType.of(s1,s1+"R");
            }
        };

        MainStage s = MainStage.init(a1).configurate();

        Object[] result = s.invoke("Hello");

        assertEquals(2, result.length);
        assertEquals("Hello", result[0]);
        assertEquals("HelloR", result[1]);
    }


}
