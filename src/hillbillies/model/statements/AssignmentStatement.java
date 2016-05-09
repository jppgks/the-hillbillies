package hillbillies.model.statements;

import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class AssignmentStatement extends Statement {

    private String variableName;
    private Expression<?> value;

    public AssignmentStatement(String variableName, Expression<?> value) {

    }

    @Override
    public void execute() {

    }
}