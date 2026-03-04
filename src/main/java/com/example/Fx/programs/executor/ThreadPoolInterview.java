package com.example.Fx.programs.executor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolInterview {

    //create ThreadPool of 3 threads and print 1 to 100 utilising all the three threads

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(3);
//        for (int i = 1; i <= 100; i++) {
//            int val = i;
//            CompletableFuture.runAsync(() -> {
//                System.out.println("val = " + val + " -> "+Thread.currentThread().getName());
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }, service);
//        }

        for (int i = 1; i <= 100; i++) {
            int num = i;
            service.submit(() -> {
                System.out.println("num = " + num + " -> "+Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        service.shutdown();
    }
}
