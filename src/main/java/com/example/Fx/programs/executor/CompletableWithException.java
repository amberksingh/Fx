package com.example.Fx.programs.executor;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableWithException {

    public static void main(String[] args) {

        System.out.println("program starting..");
        ExecutorService service = Executors.newFixedThreadPool(2);
        Supplier<String> supplier = () -> "answer";

        Function<Throwable, String> function = (e) -> {
            System.out.println("exception handled in exceptionally block");
            return "exception_handled_output";
        };
        BiFunction<String, Throwable, String> biFunction = (exMessage, ex) -> {
            if (!Objects.isNull(ex)) {
                System.out.println("Exception occurred .handle() :" + ex.getMessage());
                return "Error Result";
            }
            System.out.println("NO Exception occurred .handle() ");
            return exMessage;

        };

        CompletableFuture.supplyAsync(() -> {
                    System.out.println("task starting : " + Thread.currentThread().getName());
                    if (true) {
                        throw new RuntimeException("exception occurred");
                    }
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("task ending : " + Thread.currentThread().getName());
                    return supplier.get();
                }, service)
                //.exceptionally(function)
                .exceptionally(e -> {
                    System.out.println("exception handled in exceptionally block");
                    return e.getMessage();
                })
                //.handle(biFunction)
                .handle((exMessage, ex) -> {
                            if (!Objects.isNull(ex)) {
                                System.out.println("Exception occurred .handle() :" + ex.getMessage());
                                return "Error Result";
                            }
                            System.out.println("NO Exception occurred .handle() ");
                            return exMessage;
                        }
                )
                .thenApply(String::toUpperCase)
                .thenAccept(s -> System.out.println("result : " + s));

        System.out.println("main ending..");
        service.shutdown();
    }
}
