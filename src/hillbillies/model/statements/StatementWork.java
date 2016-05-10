package hillbillies.model.statements;

import hillbillies.model.Position;
import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class StatementWork extends Statement {

    private final Expression<Position> position;

    public StatementWork(Expression<Position> position) {
        this.position = position;
    }

    @Override
    public void execute(Unit unit) {
        unit.work(position.evaluate());
    }
}
