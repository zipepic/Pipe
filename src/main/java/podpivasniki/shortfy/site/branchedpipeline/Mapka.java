package podpivasniki.shortfy.site.branchedpipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapka {
    public static void main(String[] args) {
        List<String> f = List.of("dadf","fsadf","fsdf","fsadf");

        Map<String,Integer> map = new HashMap<>();

        f.stream().forEach(str -> map.merge(str, 1,(a,b) -> a+b ));
        System.out.println(map);
    }
}
