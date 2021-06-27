package solid_principles;


/*
* LSP: Liskov Substitution Principle
* */

public class LSP {

    static void useIt(Rectangle r){
        int width = r.getWidth();
        // Bad setter, makes sense for a rectangle but falls apart for square.
        /*
        * To overcome this, there are 2 ways:-
        * 1) If separate constructors not required, then check if rectangle is square or not.
        * 2) If you want different constructors, use factory method.
        * */
        r.setHeight(10);
        //area = width*10
        System.out.println(
                "Expected area of " + (width *10) +
                        ", got " + r.getArea()
        );
    }

    public static void main(String[] args) {
        Rectangle rc = new Rectangle(2,3);
        useIt(rc);

        Rectangle sq = new Square();
        sq.setWidth(5);
        useIt(sq);

        Rectangle rc1 = RectangleFactory.newRectangle(2,3);
        useIt(rc1);

        /* Here while calling the useIt method, you are able to do what a rectangle does
        * which is to set height and width freely, without changing the other if called separately. */
        Rectangle sq1 = RectangleFactory.newSquare(5);
        useIt(sq1);

    }

}

class RectangleFactory{
    public static Rectangle newRectangle(int width, int height){
        return new Rectangle(width, height);
    }

    public static Rectangle newSquare(int side){
        return new Rectangle(side, side);
    }
}

class Rectangle{
    protected int width, height;

    public Rectangle() {
    }

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    public int getArea(){
        return width*height;
    }
}

class Square extends Rectangle{
    public Square() {
    }

    public Square(int size) {
        width=height=size;
    }

    /*
    * This violates the LSP pattern.
    * */
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}
