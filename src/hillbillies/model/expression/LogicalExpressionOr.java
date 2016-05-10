package hillbillies.model.expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public class LogicalExpressionOr extends LogicalExpression {
    public LogicalExpressionOr(Expression<Boolean> left, Expression<Boolean> right) {
        super(left.evaluate() || right.evaluate());
    }
}
