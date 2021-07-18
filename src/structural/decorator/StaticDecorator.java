package structural.decorator;

import java.util.function.Supplier;

public class StaticDecorator {
    public static void main(String[] args) {
        ColoredShape1<Sqaure1> blueSquare = new ColoredShape1<>( () -> new Sqaure1(20), "blue");
        System.out.println(blueSquare.info());

        TransparentShape1<ColoredShape1<Circle1>> myCircle =
                new TransparentShape1<>( () -> new ColoredShape1<>(
                        () -> new Circle1(5), "green"
                ), 50);
        System.out.println(myCircle.info());
    }
}

interface Shape1{
    String info();
}

class Circle1 implements Shape1{

    private float radius;

    public Circle1() {
    }

    public Circle1(float radius) {
        this.radius = radius;
    }

    void resize(float factor){
        radius *= factor;
    }

    @Override
    public String info() {
        return "A circle of radius " + radius;
    }
}

class Sqaure1 implements Shape1{

    private float side;

    public Sqaure1() {
    }

    public Sqaure1(float side) {
        this.side = side;
    }

    @Override
    public String info() {
        return "A square of side " + side;
    }
}

class ColoredShape1<T extends Shape1> implements Shape1{
    private Shape1 shape;
    private String color;

    /*
    * We don't want to create a new object for shape (circle, square) and pass through the constructor.
    * Instead we want to pass it as an argument or generic so that, we let the code figure out which
    * shape to construct, based on the value passed. Compare the example of dynamic decorator with this example.
    * The key difference is, you can create new decorators at runtime, but not true with static decorators.
    * */
    public ColoredShape1(Supplier<? extends T> ctor, String color){
        this.color = color;
        shape = ctor.get();
    }

    @Override
    public String info() {
        return shape.info() + " has the color " + color;
    }
}

class TransparentShape1<T extends Shape1> implements Shape1{
    private Shape1 shape;
    private int transparency;

    /*
     * What we want to achieve here is that we don't want to specify the constructor explicitly
     * while creating an object. Instead we are providing the constructor of Shape to ColoredShape and
     * through lambda functions and creating a new ColoredShape.
     * */
    public TransparentShape1(Supplier<? extends T> ctor, int transparency){
        this.transparency = transparency;
        shape = ctor.get();
    }

    @Override
    public String info() {
        return shape.info() + " has " + transparency + "% transparency";
    }
}