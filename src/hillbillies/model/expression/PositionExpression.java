package hillbillies.model.expression;

import hillbillies.model.Position;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class PositionExpression extends Expression<Position> {

    PositionExpression(Expression<Position> positionExpression) {
        super(positionExpression.getValue());
    }
}
