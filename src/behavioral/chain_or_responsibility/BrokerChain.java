package behavioral.chain_or_responsibility;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/*
* From the example of Method Chain, 1 limitation is that, you are applying the modifier directly
* to the creature(immediately). Now we want to track the modifier objects and apply the modifier
* only when the modifier is actually there.
* */

/*
* This example is going to cover other design patterns as well namely
* Observer, Mediator and some degree of Memento(will be using some tokens).
* */

public class BrokerChain {
    public static void main(String[] args){
        Game game = new Game();
        Creature1 goblin = new Creature1(game, "Strong Goblin", 2, 2);
        System.out.println(goblin);

        IncreaseDefenseModifier1 idm = new IncreaseDefenseModifier1(game, goblin);
        DoubleAttackModifier1 dam = new DoubleAttackModifier1(game, goblin);
        try(dam){
            System.out.println(goblin);
        }
        System.out.println(goblin);
    }
}

// Observer Pattern
// Command Query Separation (CQS)
class Event<Args>{

    private int index = 0;
    private Map<Integer, Consumer<Args>> handlers = new HashMap<>();

    public int subscribe(Consumer<Args> handler){
        int i = index;
        handlers.put(index++, handler);
        return i;
    }

    public void unsubscribe(int key){
        handlers.remove(key);
    }

    public void fire(Args args){
        for (Consumer<Args> handler: handlers.values()){
            handler.accept(args);
        }
    }

}

class Query{
    public String creatureName;
    enum Argument{
        ATTACK, DEFENSE
    }
    public Argument argument;
    public int result;

    public Query(String creatureName,
                 Argument argument, int result) {
        this.creatureName = creatureName;
        this.argument = argument;
        this.result = result;
    }

}

// Mediator Pattern
class Game{
    public Event<Query> queries = new Event<>();

}

class Creature1{
    private Game game;
    public String name;
    public int baseAttack, baseDefense;

    public Creature1(Game game, String name, int baseAttack, int baseDefense) {
        this.game = game;
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    int getAttack(){
        Query q = new Query(name, Query.Argument.ATTACK, baseAttack);
        game.queries.fire(q);
        return q.result;
    }

    int getDefense(){
        Query q = new Query(name, Query.Argument.DEFENSE, baseDefense);
        game.queries.fire(q);
        return q.result;
    }

    @Override
    public String toString() {
        return "Creature1{" +
                "name='" + name + '\'' +
                ", attack=" + getAttack() +
                ", defense=" + getDefense() +
                '}';
    }
}

class CreatureModifier1{
    protected Game game;
    protected Creature1 creature;

    public CreatureModifier1(Game game, Creature1 creature) {
        this.game = game;
        this.creature = creature;
    }
}

class DoubleAttackModifier1
        extends CreatureModifier1
        implements AutoCloseable {
    private final int token;

    public DoubleAttackModifier1(Game game, Creature1 creature) {
        super(game, creature);

        token = game.queries.subscribe(query -> {
           if(query.creatureName.equals(creature.name) && query.argument== Query.Argument.ATTACK){
               query.result *=2;
           }
        });
    }

    @Override
    public void close() {
        game.queries.unsubscribe(token);
    }
}

class IncreaseDefenseModifier1
        extends CreatureModifier1{

    private final int token;

    public IncreaseDefenseModifier1(Game game, Creature1 creature) {
        super(game, creature);

        token = game.queries.subscribe(query -> {
            if(query.creatureName.equals(creature.name) && query.argument== Query.Argument.DEFENSE){
                query.result +=3;
            }
        });
    }

//    @Override
//    public void close() throws Exception {
//        game.queries.unsubscribe(token);
//    }
}