package com.example.Fx.programs.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounter {

    //int counter = 0;
    AtomicInteger atomicInteger = new AtomicInteger(0);

    //no synchronized block needed
    void increment() {
        System.out.println(Thread.currentThread().getName() + " counter : " + atomicInteger.incrementAndGet());
    }
}

public class AtomicNumbersCounterDemo {

    public static void main(String[] args) throws InterruptedException {

        AtomicCounter atomicCounter = new AtomicCounter();

        Runnable r1 = () -> {
            for (int i = 0; i < 10; i++) {
                atomicCounter.increment();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable r2 = () -> {
            for (int i =0;i<10;i++) {
                atomicCounter.increment();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final counter value : " + atomicCounter.atomicInteger);
        System.out.println("end of main...");
    }
}
