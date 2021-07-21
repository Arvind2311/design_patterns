package structural.proxy;

public class ProtectionProxy {
    public static void main(String[] args) {
        // Base class doesn't check for age.
        Car car = new Car(new Driver(10));
        car.drive();

        // But the proxy class can check for age, and throw error if driver is too young.
        Car car1 = new CarProxy(new Driver(10));
        car1.drive();
    }
}

interface Drivable{
    void drive();
}

class Car implements Drivable{

    protected Driver driver;

    public Car(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void drive() {
        System.out.println("Car being driven");
    }
}

class Driver {
    public int age;

    public Driver(int age) {
        this.age = age;
    }
}

class CarProxy extends Car{
    public CarProxy(Driver driver) {
        super(driver);
    }

    @Override
    public void drive() {
        if(driver.age >= 16){
            super.drive();
        } else {
            System.out.println("Driver too young to drive!!");
        }
    }
}