package hillbillies.model.expression;

import hillbillies.model.Position;

/**
 * Created by joppegeluykens on 10/05/16.
 */
public class PositionExpressionLiteral extends PositionExpression {

    public PositionExpressionLiteral(int[] positionArray) {
        this.value = new Position(positionArray);
    }

    @Override
    Position evaluate(Object o) {
        return this.evaluate();
    }
}
