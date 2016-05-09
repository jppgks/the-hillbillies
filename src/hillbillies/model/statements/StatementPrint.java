package hillbillies.model.statements;

import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class StatementPrint extends Statement {

    private final Expression<?> value;

    public StatementPrint(Expression<?> value) {
        this.value = value;
    }

    @Override
    public void execute() {

    }
}
