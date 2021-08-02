package behavioral.visitor;

// Acyclic Visitor follow Interface Segregation Principle

// If a particular Expression class doesn't want to implement visit,
// you just don't have to implement the interface to the ExpressionPrinter2 class

// Only "Price" to pay w.r.t ClassicVisitor, you end up having lots of interfaces.
public class AcyclicVisitor {
    public static void main(String[] args) {
        AdditionExpression3 e = new AdditionExpression3(
                new DoubleExpression3(1), new AdditionExpression3(
                new DoubleExpression3(2), new DoubleExpression3(3)));
        StringBuilder sb = new StringBuilder();
        ExpressionPrinter2 ep = new ExpressionPrinter2();
        ep.visit(e);
        System.out.println(ep);
    }
}

abstract class Expression3{
    public void accept(Visitor visitor){
        if(visitor instanceof ExpressionVisitor1)
            ((ExpressionVisitor1)visitor).visit(this);
    }
}

class DoubleExpression3 extends Expression3{
    public double value;

    public DoubleExpression3(double value) {
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {
        if(visitor instanceof DoubleExpressionVisitor)
            ((DoubleExpressionVisitor)visitor).visit(this);
    }
}

class AdditionExpression3 extends Expression3{
    public Expression3 left,right;

    public AdditionExpression3(Expression3 left, Expression3 right) {
        this.left = left;
        this.right = right;
    }

    public void accept(Visitor visitor) {
        if(visitor instanceof AdditionExpressionVisitor)
            ((AdditionExpressionVisitor)visitor).visit(this);
    }
}

interface Visitor{ } //Marker Interface

interface ExpressionVisitor1 extends Visitor{
    void visit(Expression3 obj);
}

interface DoubleExpressionVisitor extends Visitor{
    void visit(DoubleExpression3 obj);
}

interface AdditionExpressionVisitor extends Visitor{
    void visit(AdditionExpression3 obj);
}

class ExpressionPrinter2 implements
        DoubleExpressionVisitor,
        AdditionExpressionVisitor{

    private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpression3 e) {
        sb.append(e.value);
    }

    @Override
    public void visit(AdditionExpression3 e) {
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