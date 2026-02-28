package com.example.Fx.programs.executor;

import java.util.concurrent.*;

public class ExecutorWithCallable {

    //Without get()
    //The ExecutorService’s worker threads are non-daemon by default.
    //That means the JVM will not terminate until all worker threads finish executing.
    //✅ So your Callable will still complete fully.
    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(2);//non-daemon threads

        System.out.println("program starting..");

        Callable<Integer> callable = () -> {
            System.out.println("task starting : " + Thread.currentThread().getName());
            Thread.sleep(4000);
            System.out.println("task ending : " + Thread.currentThread().getName());
            return 10;
        };

        //submit the task
        Integer i;
        Future<Integer> submit = service.submit(callable);
        try {
            i = submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("result from callable : " + i);
        System.out.println("main ending..");

        service.shutdown();
    }
}
