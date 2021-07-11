package creational.prototype;

/*
* Here we are using copy constructors.
* Better than implementing cloneable interface.
* Cons:- You have to write a copy constructor for every type of constructor you have.
* */

public class Copyable {
    public static void main(String[] args) {
        Employee john = new Employee("John",
                new Address1("123 London Street", "London", "UK"));
        Employee chris = new Employee(john);
        chris.name = "Chris";
        System.out.println(john);
        System.out.println(chris);
    }
}

class Address1{
    public String streetName, city, country;

    public Address1(String streetName, String city, String country) {
        this.streetName = streetName;
        this.city = city;
        this.country = country;
    }

    public Address1 (Address1 other){
        this(other.streetName, other.city, other.country);
    }

    @Override
    public String toString() {
        return "Address1{" +
                "streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

class Employee{
    public String name;
    public Address1 address;

    public Employee(String name, Address1 address) {
        this.name = name;
        this.address = address;
    }

    public Employee(Employee other){
        name = other.name;
        address = new Address1(other.address);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}