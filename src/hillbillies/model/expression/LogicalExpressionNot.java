package hillbillies.model.expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class LogicalExpressionNot extends LogicalExpression {

    public LogicalExpressionNot(Expression<Boolean> booleanExpression) {
        super(! booleanExpression.evaluate());
    }

}
