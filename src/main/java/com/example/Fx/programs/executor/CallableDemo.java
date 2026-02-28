package com.example.Fx.programs.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableDemo {

    public static void main(String[] args) {

        System.out.println("program starting..");

        //=========Callable Void========
        //But Callable<Void> expects:
        //
        //return <some Void value>;
        //
        //So Java requires:
        //
        //return null;
        Callable<Void> voidCallable = () -> {
            System.out.println("inside Callable : " + Thread.currentThread().getName());
            return null;
        };
        try {
            Void call = voidCallable.call();
            System.out.println("call = " + call);//null
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //========Callable Integer========
        Callable<Integer> integerCallable = () -> 11;
        try {
            Integer call = integerCallable.call();
            System.out.println("call = " + call);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //Callable<Void> voidCallable = () -> {
    //    System.out.println("inside Callable " + Thread.currentThread().getName());
    //    return;
    //};
    //
    //❌ This is wrong because Callable must return a value.
    //
    //🔥 Rule
    //
    //Callable<T> requires:
    //
    //T call() throws Exception;
    //
    //So if you write:
    //
    //Callable<Void>
    //
    //Then call() must return a Void.
    //
    //✅ How to return for Callable<Void>
    //
    //You must return null:
    //
    //Callable<Void> voidCallable = () -> {
    //    System.out.println("inside Callable " + Thread.currentThread().getName());
    //    return null;   // ✔ required
    //};
    //
    //Why?
    //
    //Because:
    //
    //Void is a reference type
    //
    //It has no instances
    //
    //The only valid value is null
    //
    //⚠ Why plain return; doesn’t work?
    //
    //Because:
    //
    //return;
    //
    //Means method returns void.
    //
    //But Callable<Void> expects:
    //
    //return <some Void value>;
    //
    //So Java requires:
    //
    //return null;
}
