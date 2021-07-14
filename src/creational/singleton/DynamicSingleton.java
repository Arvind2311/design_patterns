package creational.singleton;

/*
* Dynamic Singleton:- create an instance of singleton on call, not on static block.
* */
public class DynamicSingleton {
    public static void main(String[] args) {

    }
}

class LazySingleton{
    private static LazySingleton instance;

    /*
    * Though this lets us create instance on request, this fails on race condition where 2 threads try to access it
    * at the same time. To avoid this you can:-
    * 1. Use synchronized keyword in-front of getInstance. (has performance implications).
    * 2. Double checked lock. (textbook implementation, outdated).
    * */
    private LazySingleton( ){
        System.out.println("Initializing a lazy singleton");
    }

    public static LazySingleton getInstance(){
        if(instance==null){
            instance = new LazySingleton();
        }
        return instance;
    }

    // Double checked Lock
    public static LazySingleton getInstance1(){
        if(instance==null){
            synchronized (LazySingleton.class){
                if(instance==null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}

/*
* This is another way of lazy and thread safe singleton,
* but without the use of primitive keyword "synchronize"
* */

class InnerStaticSingleton{
    private InnerStaticSingleton(){ }

    private static class Impl{
        private static final InnerStaticSingleton
                INSTANCE = new InnerStaticSingleton();
    }

    public InnerStaticSingleton getInstance(){
        return Impl.INSTANCE;
    }
}