package hillbillies.model.statements;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class AttackStatement extends Statement {

    private final Expression<Unit> unit;

    public AttackStatement(Expression<Unit> unit) {
        this.unit = unit;
    }

    @Override
    public void execute() {

    }
}
