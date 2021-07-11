package creational.builder;

public class FacetedBuilder {
    public static void main(String[] args) {
        PersonBuilder1 pb = new PersonBuilder1();
        Person1 person = pb
                .lives()
                    .at("123 London Road")
                    .in("London")
                    .withPostCode("123456")
                .works()
                    .at("DB")
                    .as("Developer")
                    .earning(123000)
                .build();
        System.out.println(person);
    }
}

class Person1{
    // address
    public String streetAddress, city, postcode;

    // employment
    public String companyName, position;
    public int annualIncome;

    @Override
    public String toString() {
        return "Person1{" +
                "streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}

// builder facade
class PersonBuilder1{
    protected Person1 person = new Person1();

    public PersonAddressBuilder lives(){
        return new PersonAddressBuilder(person);
    }

    public PersonJobBuilder works(){
        return new PersonJobBuilder(person);
    }

    public Person1 build(){
        return person;
    }
}

/* We extend from PersonBuilder 1 because they can use both the "works" and "lives" methods.
* This lets us switch between one subbuilder to another in a single fluent API call.
* */
class PersonAddressBuilder extends PersonBuilder1{
    public PersonAddressBuilder(Person1 person) {
        this.person = person;
    }

    public PersonAddressBuilder at(String streetAddress){
        person.streetAddress = streetAddress;
        return this;
    }

    public PersonAddressBuilder withPostCode(String postCode){
        person.postcode = postCode;
        return this;
    }

    public PersonAddressBuilder in(String city){
        person.city = city;
        return this;
    }
}

class PersonJobBuilder extends PersonBuilder1{
    public PersonJobBuilder(Person1 person) {
        this.person = person;
    }

    public PersonJobBuilder at(String companyName){
        person.companyName = companyName;
        return this;
    }

    public PersonJobBuilder as(String position){
        person.position = position;
        return this;
    }

    public PersonJobBuilder earning(int annualIncome){
        person.annualIncome = annualIncome;
        return this;
    }
}

