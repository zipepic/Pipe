package podpivasniki.shortfy.site.branchedpipeline.stage;

import org.junit.jupiter.api.Test;
import podpivasniki.shortfy.site.branchedpipeline.annotations.HandlerProcess;
import podpivasniki.shortfy.site.branchedpipeline.args.HandlerArgument;
import podpivasniki.shortfy.site.branchedpipeline.args.HandlerArgumentWithValue;
import podpivasniki.shortfy.site.branchedpipeline.handlers.AbstractHandler;
import podpivasniki.shortfy.site.branchedpipeline.args.TypeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GenericTest {

    AbstractHandler a = new AbstractHandler() {
        @HandlerProcess
        public List<List<Map<List<String>,Triple<Integer, List<Long>, Object>>>> process(List<List<Map<List<String>,Triple<Integer, List<Long>, Object>>>> listList){
            return null;
        }
    };
    @Test
    public void test(){
        TypeNode node = TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Long.class, Float.class));
        TypeNode obj = TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Long.class, Float.class));
        assertTrue(node.equals(obj));
    }
    @Test
    public void test2(){
        TypeNode node = TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Long.class, Float.class));
        TypeNode obj = TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Object.class, Float.class));
        assertTrue(node.equals(obj));
    }
    @Test
    public void test3(){
        TypeNode node = TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Long.class, Float.class));
        TypeNode obj = TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Object.class));
        assertTrue(node.equals(obj));
    }
    @Test
    public void test4(){
        TypeNode node = TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Long.class, Float.class));
        TypeNode obj = TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Float.class, Float.class));
        assertFalse(node.equals(obj));
    }

    @Test
    public void test5(){
        assertThrows(RuntimeException.class, ()->{
            TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Float.class));
        });
    }
    @Test
    public void test6(){
        assertThrows(RuntimeException.class, ()->{
            TypeNode.buildTree(List.of(Triple.class, String.class, Integer.class, List.class, Map.class, Integer.class, Map.class, Float.class, Float.class, Float.class));
        });
    }
    @Test
    public void test7(){
        TypeNode node = TypeNode.buildTree(List.of(String.class));
        TypeNode obj = TypeNode.buildTree(List.of(Object.class));
        assertTrue(node.equals(obj));
    }
    @Test
    public void test8(){
        TypeNode node = TypeNode.buildTree(List.of(Object.class));
        TypeNode obj = TypeNode.buildTree(List.of(String.class));
        assertTrue(node.equals(obj));
    }
    @Test
    public void test9(){
        List<TypeNode> nodes = TypeNode.buildTrees(List.of(Object.class, String.class, String.class, Integer.class));
        assertEquals(4, nodes.size());
    }
    @Test
    public void test10(){
        HandlerArgument argument1 = HandlerArgument.init(List.class);
        HandlerArgumentWithValue argument2 = HandlerArgumentWithValue.of(new ArrayList<>());
        assertTrue(argument1.equals(argument2));
    }
//    HandlerArgumentWithValue argument2 = HandlerArgumentWithValue.of(list);
//        System.out.println(argument2);
    @Test
    public void test11(){
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("A");

        TypeNode node = TypeNode.buildTreeWithValue(list);
        assertEquals(TypeNode.buildTree(List.of(List.class, String.class)), node);
    }

    @Test
    public void test12(){
        List<List<String>> list = new ArrayList<>();

        TypeNode node = TypeNode.buildTreeWithValue(list);
        assertEquals(TypeNode.buildTree(List.of(List.class,List.class, String.class)), node);
    }

    @Test
    public void test13(){
        Map<String, List<String>> map = new HashMap<>();
        map.put("adfaf", List.of("a"));
        TypeNode node = TypeNode.buildTreeWithValue(map);
        assertEquals(TypeNode.buildTree(List.of(Map.class, String.class, List.class, String.class)), node);
    }


}
