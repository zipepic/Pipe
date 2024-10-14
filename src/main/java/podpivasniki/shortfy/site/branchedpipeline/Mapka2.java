package podpivasniki.shortfy.site.branchedpipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapka2 {
    public static void main(String[] args) {
        Map<Integer, List<Long>> map = new HashMap<>();
        map.put(1,List.of(1l,2l,3l));
        map.put(2, List.of(3l,5l,6l));

        Map map2 = map.entrySet().stream().flatMap(entry -> entry.getValue().stream().map(value -> Map.entry(value, entry.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)-> a+b));

        System.out.println(map2);

    }
}
