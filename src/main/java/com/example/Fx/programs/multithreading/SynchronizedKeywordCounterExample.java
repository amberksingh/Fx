package com.example.Fx.programs.multithreading;

class Counter {

    int counter = 0;

//    public synchronized void increment() {
//        counter++;
//        System.out.println("counter = " + counter);
//    }

    void increment() {
        synchronized (this) {//"this" means Counter object here
            counter++;
            System.out.println("counter = " + counter);
        }
    }
    //increment() is marked as synchronized, ensuring that only one thread accesses it at a time.
//    synchronized void increment() {
//        counter++;
//        System.out.println("Thread counter : " + Thread.currentThread().getName() + " -> " + counter);
//    }
}

class ThreadCounter1 extends Thread {

    Counter counter;

    ThreadCounter1(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        System.out.println("run : " + Thread.currentThread().getName());
        for (int i = 0; i < 10; i++) {
            counter.increment();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ThreadCounter2 extends Thread {

    Counter counter;

    ThreadCounter2(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        System.out.println("run : " + Thread.currentThread().getName());
        for (int i = 0; i < 10; i++) {
            counter.increment();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class SynchronizedKeywordCounterExample {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Runnable runnable1 = () -> {
            for (int i = 0; i < 10; i++) {
                counter.increment();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Runnable runnable2 = () -> {
            for (int i = 0; i < 10; i++) {
                counter.increment();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Runnable final counter value : " + counter.counter);
        System.out.println("=======================");

        Counter threadCounter = new Counter();
        ThreadCounter1 threadCounter1 = new ThreadCounter1(threadCounter);
        ThreadCounter2 threadCounter2 = new ThreadCounter2(threadCounter);

        threadCounter1.start();
        threadCounter2.start();

        threadCounter1.join();
        threadCounter2.join();
        System.out.println("Runnable final counter value : " + threadCounter.counter);

        System.out.println("end of main..");


    }
}
