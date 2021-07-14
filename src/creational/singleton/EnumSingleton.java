package creational.singleton;

import java.io.*;

public class EnumSingleton {

    static void saveToFile(EnumBasedSingleton singleton, String filename) throws Exception{
        try(FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)){
            out.writeObject(singleton);
        }
    }

    static EnumBasedSingleton readFromFile(String filename) throws Exception{
        try(FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn)){
            return (EnumBasedSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        String filename = "myfile.bin";
//        EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
//        singleton.setValue(111);
//        saveToFile(singleton, filename);
        EnumBasedSingleton singleton1 = readFromFile(filename);
        System.out.println(singleton1.getValue());
    }
}

/*
* Pros:-
* 1. Reflection can't call constructor.
* 2. Though enums are serializable, they don't let you preserve the state of the singleton.
*
* Cons:-
* 1. You cannot inherit from this singleton.
*
* You can use this approach, provided you don't have any state to be persisted.
* */

enum EnumBasedSingleton{
    INSTANCE;

    private int value;

    // Enum based constructors are always private.
    EnumBasedSingleton(){
        value = 42;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

