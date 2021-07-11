package creational.prototype;

import java.util.Arrays;

/*
* Cloneable is not recommended because:-
* 1) Cloneable doesn't state what clone does, you need to define it your own.
* 2) Default behaviour is to shallow copy, instead of deep copy.
* */

public class Cloning {
    public static void main(String[] args) throws Exception {
        Person john = new Person(new String[]{"John", "Smith"},
                new Address("London Road", 123));
        /*
        * Initial shallow copy. Gives reference to original object. When 1 is changed, other is affected too.
        * Ex:- Person jane = john;
        * */
        Person jane = (Person) john.clone();
        jane.names[0] = "Jane";
        jane.address.houseNumber = 124;
        System.out.println(john);
        System.out.println(jane);
    }
}

class Person implements Cloneable{
    public String[] names;
    public Address address;

    public Person(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "names=" + Arrays.toString(names) +
                ", address=" + address +
                '}';
    }

    // This doesn't work
    /*
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Person(names, address);
    }
    */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Person(names.clone(), (Address) address.clone());
    }
}

class Address implements Cloneable{
    public String streetName;
    public int houseNumber;

    public Address(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }

    // This is Deep copy.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Address(streetName, houseNumber);
    }
}
