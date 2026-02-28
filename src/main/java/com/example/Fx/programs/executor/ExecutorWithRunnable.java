package com.example.Fx.programs.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorWithRunnable {

    //Without get()
    //The ExecutorService’s worker threads are non-daemon by default.
    //That means the JVM will not terminate until all worker threads finish executing.
    //✅ So your Runnable will still complete fully.
    public static void main(String[] args) {

        //public interface ExecutorService extends Executor
        ExecutorService service = Executors.newFixedThreadPool(2);

        System.out.println("program starting..");

        //runnable
        //similar to Callable<Void> scenario
        Runnable runnable = () -> {
            System.out.println("Runnable task starting executed by: " + Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Runnable task ending executed by: " + Thread.currentThread().getName());
        };
        //? is wildcard
        Future<?> future = service.submit(runnable);
        Object o = null;
        try {
            o = future.get();//null as Runnable ,blocks below main thread stuff similar to Callable<Void> scenario
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("o = " + o);//blocked because of submit.get()
        System.out.println("main ending..");
        service.shutdown();
    }
}
