package hillbillies.model.expression;

import hillbillies.model.Position;

/**
 * Created by joppegeluykens on 10/05/16.
 */
public final class LiteralPositionExpression extends Expression<Position> {

    public LiteralPositionExpression(int[] positionArray) {
        this.value = new Position(positionArray);
    }

}
