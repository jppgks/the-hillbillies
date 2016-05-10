package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class BooleanExpressionIsEnemy extends BooleanExpression<Unit> {

    public BooleanExpressionIsEnemy(Expression<Unit> unitExpression) {
        this.value = unitExpression.evaluate();
    }

    @Override
    boolean evaluate(Unit unit) {
        return ! unit.getFaction().equals(this.evaluate().getFaction());
    }
}
