package behavioral.visitor;

//Double Dispatch -> Real World (Most popular)
public class ClassicVisitor {
    public static void main(String[] args) {
        AdditionExpression2 e = new AdditionExpression2(
                new DoubleExpression2(1), new AdditionExpression2(
                new DoubleExpression2(2), new DoubleExpression2(3)));
        StringBuilder sb = new StringBuilder();
        ExpressionPrinter1 ep = new ExpressionPrinter1();
        ep.visit(e);
        System.out.println(ep);
        ExpressionCalculator ec = new ExpressionCalculator();
        ec.visit(e);
        System.out.println(ep + " = "+ ec.result);
    }
}

abstract class Expression2{
    public abstract void accept(ExpressionVisitor visitor);
}

class DoubleExpression2 extends Expression2{
    public double value;

    public DoubleExpression2(double value) {
        this.value = value;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

class AdditionExpression2 extends Expression2{
    public Expression2 left,right;

    public AdditionExpression2(Expression2 left, Expression2 right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

interface ExpressionVisitor{
    void visit(DoubleExpression2 e);
    void visit(AdditionExpression2 e);
}

class ExpressionPrinter1 implements ExpressionVisitor{

    private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpression2 e) {
        sb.append(e.value);
    }

    @Override
    public void visit(AdditionExpression2 e) {
        sb.append("(");
        e.left.accept(this);
        sb.append("+");
        e.right.accept(this);
        sb.append(")");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class ExpressionCalculator implements ExpressionVisitor{

    public double result;

    @Override
    public void visit(DoubleExpression2 e) {
        result=e.value;
    }

    @Override
    public void visit(AdditionExpression2 e) {
        e.left.accept(this);
        double a = result;
        e.right.accept(this);
        double b = result;
        result = a+b;
    }
}