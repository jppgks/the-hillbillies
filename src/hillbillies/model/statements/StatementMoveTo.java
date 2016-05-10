package hillbillies.model.statements;

import hillbillies.model.Position;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class StatementMoveTo extends Statement {

    private final Expression<Position> position;

    public StatementMoveTo(Expression<Position> position) {
        this.position = position;
    }

    @Override
    public void execute(Unit unit) {
        unit.moveTo(position.evaluate().getCubeCoordinates());
    }
}
