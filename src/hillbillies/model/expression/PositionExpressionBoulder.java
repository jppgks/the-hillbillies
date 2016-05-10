package hillbillies.model.expression;

import hillbillies.model.Boulder;
import hillbillies.model.Position;

/**
 * Created by joppegeluykens on 10/05/16.
 */
public class PositionExpressionBoulder extends PositionExpression<Boulder> {

    @Override
    Position evaluate(Boulder boulder) {
        return boulder.getPosition();
    }

}
