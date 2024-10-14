package podpivasniki.shortfy.site.branchedpipeline;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Comparing {

    public static void main(String[] args) {
        List<Rec> list = List.of(new Rec(1,"O"), new Rec(2000,"A"),new Rec(200,"A"));

        List<Rec> out = list.stream().sorted(Comparator.comparing(Rec::getName).thenComparing(Rec::getIncr)).collect(Collectors.toList());

        out.forEach(System.out::println);
    }
}
class Rec{
    private int incr;
    private String name;

    public Rec(int incr, String name) {
        this.incr = incr;
        this.name = name;
    }

    public int getIncr() {
        return incr;
    }

    public void setIncr(int incr) {
        this.incr = incr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Rec{" +
                "incr=" + incr +
                ", name='" + name + '\'' +
                '}';
    }
}