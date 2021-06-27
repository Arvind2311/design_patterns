package solid_principles;


/*
* DIP:- Dependency Inversion Principle.
* Not to be confused with DI -> Dependency Injection.
* Both are close but not same.
* */

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DIP {

    public static void main(String[] args) {
        Person john = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(john, child1);
        relationships.addParentAndChild(john, child2);

        new Research(relationships);

        Relationships1 relationships1 = new Relationships1();
        relationships1.addParentAndChild(john, child1);
        relationships1.addParentAndChild(john, child2);

        new Research(relationships1);
    }

}

enum Relationship{
    PARENT,
    CHILD,
    SIBLING
}

class Person{
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

// Low Level Module (Client doesn't care)
class Relationships{
    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child){
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    // We are exposing this method (internal storage method) to everyone (public access modifier).
    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }
}

// High Level Module (Exposed to Client, they want to do operations, don't care of implementation of relationships)
class Research{
    //Research is based on low-level module Relationships. They should be dependent on abstractions.
    public Research(Relationships relationships){
        System.out.println("Before Dependency Inversion");
        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
        relations.stream()
                .filter(x -> x.getValue0().name.equals("John")
                        && x.getValue1() == Relationship.PARENT)
                .forEach(ch -> System.out.println(
                        "John has a child called " + ch.getValue2().name
                ));
    }

    // After Implementing RelationshipBrowser (Abstractions)
    public Research(RelationshipBrowser browser){
        System.out.println("After Dependency Inversion");
        List<Person> children = browser.findAllChildrenOf("John");
        for (Person child: children) {
            System.out.println("John has child called " + child.name);
        }
    }
}

interface RelationshipBrowser{
    List<Person> findAllChildrenOf(String name);
}

class Relationships1 implements RelationshipBrowser{

    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child){
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream()
                .filter(x -> Objects.equals(x.getValue0().name, name)
                        && x.getValue1() == Relationship.PARENT)
                .map(Triplet::getValue2)
                .collect(Collectors.toList());
    }
}