package com.example.Fx.programs.design;

import java.util.Objects;

class World {

    private static World world;// = new World();

    private World() {
        System.out.println("Inside constructor..");
    }

    public static World getInstance() {
        System.out.println("inside getInstance() method.");
        if (Objects.isNull(world)) {
            System.out.println("Object null..creating new");
            world = new World();
        }
        return world;
    }
}

public class SingletonLazyEager {

    public static void main(String[] args) {

        World world1 = World.getInstance();
        System.out.println("world1.hashCode() = " + world1.hashCode());
        World world2 = World.getInstance();
        System.out.println("world2.hashCode() = " + world2.hashCode());

    }
}
