package hillbillies.model.statements;

import hillbillies.model.Position;
import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class WorkStatement extends Statement {

    private final Expression<Position> position;

    public WorkStatement(Expression<Position> position) {
        this.position = position;
    }

    @Override
    public void execute() {

    }
}
