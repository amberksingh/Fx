package com.example.Fx.programs.executor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureThenApply {

    public static void main(String[] args) {

        System.out.println("program starting...");
        ExecutorService service = Executors.newFixedThreadPool(2);

        Supplier<String> supplier = () -> "answer";
        Function<String, String> function = String::toUpperCase;

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("task starting : " + Thread.currentThread().getName());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task starting : " + Thread.currentThread().getName());
            return supplier.get();
        }, service).thenApply(function);

        String join = stringCompletableFuture.join();
        System.out.println("join = " + join);
        System.out.println("main ending..");
        service.shutdown();
    }
}
