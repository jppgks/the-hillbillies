package hillbillies.model.expression;

import hillbillies.model.Position;
import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 10/05/16.
 */
public final class PositionExpressionUnit extends PositionExpression<Unit> {

    @Override
    Position evaluate(Unit unit) {
        return unit.getPosition();
    }

}
