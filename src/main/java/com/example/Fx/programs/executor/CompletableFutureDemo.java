package com.example.Fx.programs.executor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureDemo {

    public static void main(String[] args) {

        Supplier<String> supplier = () -> "demo";
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(supplier);
        //this method throws an (unchecked) CompletionException with the underlying exception as its cause.
        String result = stringCompletableFuture.join();
        System.out.println("result = " + result);
        try {
            String s = stringCompletableFuture.get();
            System.out.println("s = " + s);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        //apply
        Function<String, Integer> integerFunction = (str) -> str.length();
        Function<String, Integer> integerFunction1 = String::length;
        System.out.println("integerFunction.apply() = " + integerFunction1.apply("dhoni"));//5

        //thenApply
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> thenApply = integerCompletableFuture.thenApply(num -> num * 2);
        System.out.println("thenApply.join() = " + thenApply.join());//20
        
        //thenApply returns another CompletableFuture
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<CompletableFuture<Integer>> thenApplyC = 
                future.thenApply(x -> CompletableFuture.supplyAsync(() -> x * 2));
        CompletableFuture<Integer> join = thenApplyC.join();//1st join
        Integer doubleResult = join.join();//join called twice due to nested structure
        System.out.println("doubleResult = " + doubleResult);//40

        //thenCompose
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<Integer> future2 = future1.thenCompose(number -> CompletableFuture.supplyAsync(() -> number * 2));
        System.out.println("future2.join() = " + future2.join());//40
    }
}
