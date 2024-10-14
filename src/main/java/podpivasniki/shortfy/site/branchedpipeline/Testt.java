package podpivasniki.shortfy.site.branchedpipeline;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Testt {
    public static void main(String[] args) {
        List<Object> l = List.of("a");
        l.add(1);
        Map<Character,Map<Integer,List<String>>> a = List.of("fa","fasdf","sdf", "fdsf").stream()
                .collect(Collectors.groupingBy(
                        str -> str.charAt(0),
                        Collectors.groupingBy(String::length)
                ));
        System.out.println(a);
    }
//    public static void main(String[] args) {
//        Map<Integer, List<Long>> input = new HashMap<>();
//
//        input.put(10,List.of(1l,2l,3l));
//        input.put(100,List.of(1l));
//        Map<Long, Integer> out = input.entrySet().stream().flatMap(entry -> entry.getValue().stream().map(val -> Map.entry(val, entry.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::compareTo));
//        System.out.println(out);
//
//    }
//    public static void main(String[] args) throws ClassNotFoundException {
//        Predicate<String> isFoo = "foo"::equals;
//        System.out.println(isFoo.test("foo7"));
//
//        BiFunction<String, Integer, Long> biFunction = (a,b) -> (long) b;
//
//
//        System.out.println(biFunction.apply("test", 10));
//        Predicate<Character> isO = (Character a) -> a == 'o';
//        Supplier<Integer> intSupplier = () -> 5;
//
//
//        IntSummaryStatistics intSummaryStatistics = IntStream.range(1, 100)
//                .summaryStatistics();
//
//        Map a = Arrays.asList("a", "abb", "aafafa", "asd", "fds").stream()
//                .collect(Collectors.groupingBy(
//                        String::length,
//                        Collectors.groupingBy(x->x.charAt(0))));
//        System.out.println(a);
//    }

}
