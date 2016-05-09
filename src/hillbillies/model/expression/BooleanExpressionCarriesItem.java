package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public class BooleanExpressionCarriesItem extends BooleanExpression<Unit> {
    public BooleanExpressionCarriesItem(Expression<Unit> unitExpression) {
        super(unitExpression.getValue());
    }

    @Override
    boolean evaluate(Unit unit) {
        return this.getValue().getMaterial() != null;
    }
}
