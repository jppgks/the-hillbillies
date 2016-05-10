package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public abstract class UnitExpression extends Expression<Unit> {

    abstract Unit evaluate(Unit unit);

}
