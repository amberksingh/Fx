package com.example.Fx.programs.design;

class SingletonStuff {

    private static SingletonStuff singletonStuff;

    private SingletonStuff() {
        System.out.println("inside constructor..");
    }

    public static SingletonStuff getInstance() {
        System.out.println("inside getInstance() method " + Thread.currentThread().getName());
        if (singletonStuff == null) {
            System.out.println("inside 1st if cond " + Thread.currentThread().getName());
            synchronized (SingletonStuff.class) {
                if (singletonStuff == null) {
                    System.out.println("inside 2nd if cond" + Thread.currentThread().getName());
                    singletonStuff = new SingletonStuff();
                }
            }
        }
        return singletonStuff;
    }

}

public class SingletonMultiThread {

    public static void main(String[] args) {

        Runnable runnable = () -> {
            SingletonStuff instance = SingletonStuff.getInstance();
            System.out.println("instance.hashCode() = " + instance.hashCode());
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end of main");
    }
}
