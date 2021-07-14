package creational.singleton;

import java.util.HashMap;
import java.util.Map;

public class Multiton {
    public static void main(String[] args) {
        Printer main = Printer.get(Subsystem.PRIMARY);
        Printer aux = Printer.get(Subsystem.AUXILIARY);
        Printer aux1 = Printer.get(Subsystem.AUXILIARY);
    }
}

enum Subsystem{
    PRIMARY,
    AUXILIARY,
    FALLBACK
}

class Printer{

    private static int instanceCount = 0;

    private Printer(){
        instanceCount++;
        System.out.println("A total of "+instanceCount+" instances created so far");
    }

    private static Map<Subsystem, Printer> instances = new HashMap<>();

    public static Printer get(Subsystem ss){
        if(instances.containsKey(ss))
            return instances.get(ss);
        Printer instance = new Printer();
        instances.put(ss, instance);
        return instance;
    }
}
