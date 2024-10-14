package podpivasniki.shortfy.site.branchedpipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapka3 {

    public static void main(String[] args) {
        List<String> data = List.of("A","B", "Cc");
        Map map = data.stream().collect(Collectors.groupingBy(str -> str.length(), Collectors.groupingBy(str-> str.charAt(0))));
        System.out.println(map);

    }
}
