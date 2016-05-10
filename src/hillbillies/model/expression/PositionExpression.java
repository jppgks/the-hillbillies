package hillbillies.model.expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public abstract class PositionExpression<T> extends Expression<T> {

    PositionExpression(Expression<T> positionExpression) {
        super(positionExpression.evaluate());
    }

}
