package hillbillies.model.expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public class LogicalExpressionAnd extends LogicalExpression {

    public LogicalExpressionAnd(Expression<Boolean> left, Expression<Boolean> right) {
        super(left.evaluate() && right.evaluate());
    }
}
