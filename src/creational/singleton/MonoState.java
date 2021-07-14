package creational.singleton;

/*
* To convert already existing class to a singleton, replace all vars to static.
* If any getters/setters, change accordingly. You can create multiple instances,
* but all instances will have same values.
* */

public class MonoState {
    public static void main(String[] args) {
        ChiefExecutiveOfficer ceo = new ChiefExecutiveOfficer();
        ceo.setName("Adam");
        ceo.setAge(55);

        ChiefExecutiveOfficer ceo1 = new ChiefExecutiveOfficer();
        System.out.println(ceo1);
    }
}

class ChiefExecutiveOfficer{
    private static String name;
    private static int age;

    @Override
    public String toString() {
        return "ChiefExecutiveOfficer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ChiefExecutiveOfficer.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ChiefExecutiveOfficer.age = age;
    }
}
