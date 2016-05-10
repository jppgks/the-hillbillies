package hillbillies.model.expression;

import hillbillies.model.Position;
import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class BooleanExpressionIsPassable extends BooleanExpression<Position> {

    public BooleanExpressionIsPassable(Expression<Position> positionExpression) {
        this.value = positionExpression.evaluate();
    }

    @Override
    public boolean evaluate(Unit unit) {
        return ! unit.getWorld().getCube(this.evaluate()).isSolid();
    }
}
