package com.example.Fx.programs.design;

import java.io.*;
import java.util.Objects;

class Subject implements Serializable {

    private static Subject instance;

    private Subject() {
        System.out.println("inside constructor");
    }

    public static Subject getInstance() {
        System.out.println("inside getInstance() method");
        if (Objects.isNull(instance)) {
            instance = new Subject();
        }
        return instance;
    }

    //works only for serialization case
    protected Object readResolve() {
        System.out.println("inside readResolve()");
        return instance;
    }
}

public class SingletonSerializationDemo {

    public static void main(String[] args) {

        Subject subject1 = Subject.getInstance();
        System.out.println("subject1.hashCode() = " + subject1.hashCode());

        try (FileOutputStream fileOutputStream = new FileOutputStream("files.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(subject1);
            System.out.println("serialization done");
        } catch (Exception e) {
            System.out.println("exception : "+e.getMessage());
        }

        Subject subject2 = null;
        try (FileInputStream fileInputStream = new FileInputStream("files.txt");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            subject2 = (Subject)objectInputStream.readObject();
            System.out.println("DeSerialization done");
        } catch (Exception e) {
            System.out.println("exception : "+e.getMessage());
        }
        System.out.println("subject2.hashCode() = " + subject2.hashCode());
    }
}
