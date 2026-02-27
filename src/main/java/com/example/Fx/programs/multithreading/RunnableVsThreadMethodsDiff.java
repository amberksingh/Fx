package com.example.Fx.programs.multithreading;

class RunnableImpl implements Runnable {

    int counter = 0;

    @Override
    public void run() {
        counter++;
        System.out.println("runnable counter : "+counter);
    }
}

class ThreadImpl extends Thread {

    int counter = 0;

    @Override
    public void run() {
        counter++;
        System.out.println("ThreadImpl counter : "+counter);
    }
}

public class RunnableVsThreadMethodsDiff {

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = new RunnableImpl();

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(1000);
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println("===========");

        Thread t4 = new ThreadImpl();
        Thread t5 = new ThreadImpl();
        Thread t6 = new ThreadImpl();
        t4.start();
        Thread.sleep(1000);
        t5.start();
        Thread.sleep(1000);
        t6.start();

        t4.join();
        t5.join();
        t6.join();

        System.out.println("end of main..");
    }
}
