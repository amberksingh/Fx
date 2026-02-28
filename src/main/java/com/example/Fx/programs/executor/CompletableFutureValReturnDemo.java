package com.example.Fx.programs.executor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;

public class CompletableFutureValReturnDemo {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(2);

        BiFunction<String, String, String> biFunction = (s1, s2) -> s1.concat(s2);
        BiFunction<String, String, String> biFunction1 = String::concat;
        System.out.println("biFunction : "+biFunction1.apply("arun", "vasco"));

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("task starting : " + Thread.currentThread().getName());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task ending : " + Thread.currentThread().getName());
            return "answer";
        }, service);
        String join = stringCompletableFuture.join();
        System.out.println("join = " + join);
        System.out.println("main ending..");
        service.shutdown();
    }

}
