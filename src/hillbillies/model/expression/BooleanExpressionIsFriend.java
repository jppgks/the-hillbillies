package hillbillies.model.expression;

import hillbillies.model.Unit;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public class BooleanExpressionIsFriend extends BooleanExpression<Unit> {

    public BooleanExpressionIsFriend(Expression<Unit> unitExpression) {
        super(unitExpression.getValue());
    }


    @Override
    boolean evaluate(Unit unit) {
        return unit.getFaction().equals(this.getValue().getFaction());
    }
}
