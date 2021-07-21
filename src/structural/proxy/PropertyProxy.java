package structural.proxy;

import java.util.Objects;

public class PropertyProxy {
    public static void main(String[] args) {
        Creature creature = new Creature();
        creature.setAgility(12);
    }
}

class Property<T>{
    private T value;

    public Property(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        // You can provide logging over here.
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property<?> property = (Property<?>) o;
        return getValue().equals(property.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

/* We want to log all the assignments made to agility of the creature.
* There is 1 exception in java specifically (agility = 123), we cannot overload this operation.
* So we will use a work around for setting a basic data type using Generics (Property setting/Property proxy).
* */

class Creature{
    private Property<Integer> agility = new Property<>(10);

    public void setAgility(int value){
        agility.setValue(value);
    }

    public int getAgility(){
        return agility.getValue();
    }
}