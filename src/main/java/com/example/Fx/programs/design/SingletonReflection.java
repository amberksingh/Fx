package com.example.Fx.programs.design;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class Perfume {

    private static Perfume perfume;

    private Perfume() {
        System.out.println("Inside constructor..");
        // Prevent reflection from breaking Singleton - for simulating exception scenario to
        // avoid creating second instance-use if condition to show exception
        if (perfume != null) {
            System.out.println("Inside null check for constructor..");
            throw new RuntimeException("Use getInstance() method to create Singleton!");
        }
    }

    public static Perfume getInstance() {
        System.out.println("Inside getInstance()..");
        if (perfume == null) {
            perfume = new Perfume();
        }
        return perfume;
    }

    //doesn't work for reflection scenario
//    protected Object readResolve() {
//        return perfume;
//    }

}

public class SingletonReflection {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Perfume perfume1 = Perfume.getInstance();
        System.out.println("perfume1.hashCode() = " + perfume1.hashCode());

        Constructor<Perfume> constructor = Perfume.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Perfume perfume2 = constructor.newInstance();
        //avoids getInstance and directly calls constructor-ultimately breaking,
        // so added null check in constructor to avoid new object creation by throwing exception
        System.out.println("perfume2.hashCode() = " + perfume2.hashCode());
    }
}
