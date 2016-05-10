package hillbillies.model.statements;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class StatementAssignment extends Statement {

    private final String variableName;
    private final Expression<?> value;

    public StatementAssignment(String variableName, Expression<?> value) {
        this.variableName = variableName;
        this.value = value;
    }

    @Override
    public void execute(Unit unit) {

    }
}