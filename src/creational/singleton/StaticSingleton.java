package creational.singleton;

import java.io.File;

public class StaticSingleton {
    public static void main(String[] args) {
        StaticBlockSingleton sbs = StaticBlockSingleton.getInstance();
    }
}

class StaticBlockSingleton{
    private StaticBlockSingleton() throws Exception{
        System.out.println("Singleton is initializing");
        File.createTempFile(".",".");
    }

    private static StaticBlockSingleton instance;

    static {
        try{
            instance = new StaticBlockSingleton();
        } catch (Exception e){
            System.err.println("Failed to create singleton");
        }
    }

    public static StaticBlockSingleton getInstance(){
        return instance;
    }
}