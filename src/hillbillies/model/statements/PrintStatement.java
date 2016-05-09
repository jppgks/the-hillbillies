package hillbillies.model.statements;

import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class PrintStatement extends Statement {

    private final Expression<?> value;

    public PrintStatement(Expression<?> value) {
        this.value = value;
    }

    @Override
    public void execute() {

    }
}
