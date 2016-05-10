package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public abstract class BooleanExpression<T> extends Expression<T> {

    abstract boolean evaluate(Unit unit);

}
