package hillbillies.model.expression;

import hillbillies.model.Position;

/**
 * Created by joppegeluykens on 10/05/16.
 */
public class PositionExpressionSelected extends PositionExpression<int[]> {

    @Override
    Position evaluate(int[] selectedCube) {
        return new Position(selectedCube);
    }
}
