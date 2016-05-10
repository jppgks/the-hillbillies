package hillbillies.model.expression;

import hillbillies.model.Position;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public abstract class PositionExpression<T> extends Expression<Position> {

    abstract Position evaluate(T t);

}
