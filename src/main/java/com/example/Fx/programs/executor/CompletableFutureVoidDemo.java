package com.example.Fx.programs.executor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureVoidDemo {

    //Real-World Example
    //▶ runAsync() — no result
    //CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
    //    System.out.println("Doing background work...");
    //});
    //future.join(); // wait until it's done
    //
    //Use when the task only performs side-effects like saving to DB, sending email, logging, etc.
    //
    //▶ supplyAsync() — returns result
    //CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    //    return "Data from background";
    //});
    //String result = future.join(); // returns the string
    //
    //Use when the task produces a result — like fetching data, computing something, etc.

    //uses the common ForkJoinPool if ExecutorService service = Executors.newFixedThreadPool(2); not used .
    //if ExecutorService service = Executors.newFixedThreadPool(2); is used then those are not daemon threads(uses thread pool)
    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(2);
        System.out.println("program starting..");

        //✔️ Creates the task (a Runnable)
        //✔️ Submits it immediately to be executed asynchronously
        //✔️ Returns a CompletableFuture<Void> that you can use to track the result
        //✅ Equivalent to defining a Callable and submitting it using ExecutorService.submit(...), but in one
        // line and more modern-style.
        //ForkJoinPool.commonPool used here
        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
            System.out.println("task starting : " + Thread.currentThread().getName());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task ending : " + Thread.currentThread().getName());
        }, service);
        //task.get();//get() throws checked exceptions (ExecutionException, InterruptedException)
//        Void join = task.join();//to block main thread form ending before task ends
//        System.out.println("join = " + join);//null
        //join() wraps exceptions in an unchecked CompletionException.
        //This is similar to future.get() — but join() doesn't require a try-catch.

        System.out.println("main ending..");
        service.shutdown();
    }
}
