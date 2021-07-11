package creational.prototype;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/*
* Correct way to copy an object is to use serialize or reflections to copy entire object.
* This way it creates an entire object graph while serializing and
* while deserializing, copies the data into new object.
* */
public class SerializePrototype {
    public static void main(String[] args) {
        Foo foo = new Foo(42, "life");
        Foo fooCopy = SerializationUtils.roundtrip(foo);
        fooCopy.whatever = "xyz";
        System.out.println(foo);
        System.out.println(fooCopy);
    }
}

class Foo implements Serializable{
    public int stuff;
    public String whatever;

    public Foo(int stuff, String whatever) {
        this.stuff = stuff;
        this.whatever = whatever;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "stuff=" + stuff +
                ", whatever='" + whatever + '\'' +
                '}';
    }
}
