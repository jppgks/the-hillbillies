package hillbillies.model.statements;

import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class WhileStatement extends Statement {


    private final Expression<Boolean> condition;
    private final Statement body;

    public WhileStatement(Expression<Boolean> condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute() {

    }
}
