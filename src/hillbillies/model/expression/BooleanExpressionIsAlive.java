package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public class BooleanExpressionIsAlive extends BooleanExpression<Unit> {
    public BooleanExpressionIsAlive(Expression<Unit> unitExpression) {
        super(unitExpression.evaluate());
    }

    @Override
    boolean evaluate(Unit unit) {
        return this.evaluate().isAlive();
    }
}
