package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 10/05/16.
 */
public final class UnitExpressionThis extends UnitExpression {

    @Override
    Unit evaluate(Unit unit) {
        return unit;
    }

}
