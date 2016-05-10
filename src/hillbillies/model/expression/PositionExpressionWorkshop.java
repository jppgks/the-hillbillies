package hillbillies.model.expression;

import hillbillies.model.Cube;
import hillbillies.model.Position;

/**
 * Created by joppegeluykens on 10/05/16.
 */
public class PositionExpressionWorkshop extends PositionExpression<Cube> {

    @Override
    Position evaluate(Cube cube) {
        return cube.getPosition();
    }

}
