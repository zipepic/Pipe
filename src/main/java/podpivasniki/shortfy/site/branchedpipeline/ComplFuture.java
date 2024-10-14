package podpivasniki.shortfy.site.branchedpipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ComplFuture {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
//        List<String> list = List.of("da", "no");
//        List<String> res = new ArrayList<>();
//        List<CompletableFuture> c = list.stream().map(str -> CompletableFuture.supplyAsync(()-> proc(str), service).thenAccept(result -> res.add(result))).collect(Collectors.toList());
//        c.stream().forEach(col -> col.join());
//        System.out.println(res);
//
//        CompletableFuture.allOf(c.toArray(new CompletableFuture[0])).thenRun(service::shutdown);

        Callable callable = () -> "Hi";
        Future<String> stringFuture = service.submit(callable);


        try {
            System.out.println(stringFuture.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        service.shutdown();

    }

    public static String proc(String s){
        System.out.println(s);
        return "P "+s;
    }
}
