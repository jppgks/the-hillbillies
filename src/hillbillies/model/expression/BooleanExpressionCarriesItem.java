package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class BooleanExpressionCarriesItem extends BooleanExpression<Unit> {

    public BooleanExpressionCarriesItem(Expression<Unit> unitExpression) {
        this.value = unitExpression.evaluate();
    }

    @Override
    boolean evaluate(Unit unit) {
        return this.evaluate().getMaterial() != null;
    }
}
