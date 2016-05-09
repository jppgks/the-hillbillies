package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class UnitExpression extends Expression<Unit> {

    UnitExpression(Expression<Unit> unitExpression) {
        super(unitExpression.getValue());
    }

}
