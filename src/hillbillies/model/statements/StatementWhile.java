package hillbillies.model.statements;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class StatementWhile extends Statement {


    private final Expression<Boolean> condition;
    private final Statement body;

    public StatementWhile(Expression<Boolean> condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Unit unit) {
        if (condition.evaluate()) {
            if (body.hasNext()) {
                body.next().execute(unit);
            }
        }
    }
}
