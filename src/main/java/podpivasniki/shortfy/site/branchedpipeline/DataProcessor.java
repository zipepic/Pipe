package podpivasniki.shortfy.site.branchedpipeline;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataProcessor {
    public static void main(String[] args) {
        List<String> dataSources = Arrays.asList("Data1", "Data2", "Data3");
        ExecutorService executorService = Executors.newFixedThreadPool(4); // Пул потоков для асинхронной обработки


        dataSources.stream().map(data -> CompletableFuture
                .supplyAsync(()-> processData(data), executorService)
                .thenAccept(res -> {
                    System.out.println(res);
                })

                .thenRun(executorService::shutdown)
        );

        System.out.println(dataSources);
    }

    public static String processData(String data) {
        // Имитация обработки данных
        return data + " processed";
    }
}
