package hillbillies.model.expression;

import hillbillies.model.Log;
import hillbillies.model.Position;

/**
 * Created by joppegeluykens on 10/05/16.
 */
public class PositionExpressionLog extends PositionExpression<Log> {

    @Override
    Position evaluate(Log log) {
        return log.getPosition();
    }

}
