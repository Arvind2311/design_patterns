package behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();
        Person john = new Person("John");
        Person jane = new Person("Jane");
        room.join(john); room.join(jane);
        john.say("hi room");
        jane.say("hi john");
        Person simon = new Person("Simon");
        room.join(simon);
        simon.say("Hi Everyone!");
        jane.privateMessage("Simon", "Glad you could join us");
    }
}

class Person{
    public String name;
    public ChatRoom room;
    private List<String> chatLog = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public void receive(String sender, String message){
        String s = sender + " : '" + message + "'";
        System.out.println("["+name+"'s chat session] "+ s);
        chatLog.add(s);
    }

    public void say(String message){
        room.broadcast(name, message);
    }

    public void privateMessage(String who, String message){
        room.message(name, who, message);
    }
}

class ChatRoom{
    private List<Person> people = new ArrayList<>();

    public void join(Person p){
        String joinMsg = p.name + " joins the room";
        broadcast("room", joinMsg);
        p.room = this;
        people.add(p);
    }

    public void broadcast(String source, String message){
        for (Person person: people)
            if(person.name != source)
                person.receive(source, message);
    }

    public void message(String source, String destination, String message){
        people.stream()
                .filter(p -> p.name.equals(destination))
                .findFirst()
                .ifPresent(person -> person.receive(source, message));
    }
}