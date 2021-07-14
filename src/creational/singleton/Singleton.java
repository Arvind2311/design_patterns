package creational.singleton;

import java.io.*;

public class Singleton {

    static void saveToFile(BasicSingleton singleton, String filename) throws Exception{
        try(FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)){
            out.writeObject(singleton);
        }
    }

    static BasicSingleton readFromFile(String filename) throws Exception{
        try(FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn)){
            return (BasicSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws Exception{
        //Basic Singleton
        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(123);
        System.out.println(singleton.getValue());

        /* Problems with basic singleton
        * 1. Reflections
        * 2. Serialization
        *  */

        String filename = "singleton.bin";
        saveToFile(singleton, filename);
        singleton.setValue(111);
        BasicSingleton singleton1 = readFromFile(filename);
        System.out.println(singleton==singleton1);
        System.out.println(singleton.getValue());
        System.out.println(singleton1.getValue());
    }
}

class BasicSingleton implements Serializable {
    private static BasicSingleton INSTANCE = new BasicSingleton();
    private BasicSingleton() {

    }

    private int value = 0;

    public static BasicSingleton getInstance(){
        return INSTANCE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected Object readResolve(){
        return INSTANCE;
    }
}