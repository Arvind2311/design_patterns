package behavioral.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EventClass {
    public static void main(String[] args) {
        Person1 person = new Person1();
        Event<PropertyChangedEventArgs1>.Subscription
                sub = person.propertyChanged.addHandler(x -> {
            System.out.println("Person's " + x.propertyName +
                    " has changed to " + x.newValue);
        });
        person.setAge(17);
        person.setAge(18);
        sub.close();
        person.setAge(19);
    }
}

class Event<TArgs>{
    private int count=0;
    private Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();

    public Subscription addHandler(Consumer<TArgs> handler){
        int i = count;
        handlers.put(count++, handler);
        return new Subscription(this, i);
    }

    public void fire(TArgs args){
        for (Consumer<TArgs> handler: handlers.values())
            handler.accept(args);
    }

    public class Subscription implements AutoCloseable{

        private Event<TArgs> event;
        private int id;

        public Subscription(Event<TArgs> event, int id) {
            this.event = event;
            this.id = id;
        }

        @Override
        public void close() /*throws Exception*/ {
            event.handlers.remove(id);
        }
    }
}

class PropertyChangedEventArgs1{
    public Object source;
    public String propertyName;
    public Object newValue;

    public PropertyChangedEventArgs1(Object source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }
}

class Person1{
    public Event<PropertyChangedEventArgs1> propertyChanged = new Event<>();
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(this.age==age) return;
        this.age = age;
        propertyChanged.fire(
                new PropertyChangedEventArgs1(this, "age", age));
    }

    public boolean getCanVote(){
        return age>=18;
    }
}