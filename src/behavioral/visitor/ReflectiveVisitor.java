package behavioral.visitor;

//Code is slow (reflection).
public class ReflectiveVisitor {
    public static void main(String[] args) {
        AdditionExpression1 e = new AdditionExpression1(
                new DoubleExpression1(1), new AdditionExpression1(
                new DoubleExpression1(2), new DoubleExpression1(3)));
        StringBuilder sb = new StringBuilder();
        ExpressionPrinter.print(e, sb);
        System.out.println(sb);
    }
}

abstract class Expression1{

}

class DoubleExpression1 extends Expression1{
    public double value;

    public DoubleExpression1(double value) {
        this.value = value;
    }

}

class AdditionExpression1 extends Expression1{
    public Expression1 left,right;

    public AdditionExpression1(Expression1 left, Expression1 right) {
        this.left = left;
        this.right = right;
    }
}

class ExpressionPrinter{

    public ExpressionPrinter() {
    }

    public static void print(Expression1 e, StringBuilder sb){
        if(e.getClass()==DoubleExpression1.class){
            sb.append(((DoubleExpression1)e).value);
        } else if(e.getClass()==AdditionExpression1.class){
            AdditionExpression1 ae = (AdditionExpression1)e;
            sb.append("(");
            print(ae.left, sb);
            sb.append("+");
            print(ae.right, sb);
            sb.append(")");
        }
    }
}