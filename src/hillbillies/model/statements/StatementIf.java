package hillbillies.model.statements;

import hillbillies.model.Unit;
import hillbillies.model.expression.Expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class StatementIf extends Statement {

    private final Expression<Boolean> condition;
    private final Statement ifBody;
    private final Statement elseBody;

    public StatementIf(Expression<Boolean> condition, Statement ifBody, Statement elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    @Override
    public void execute(Unit unit) {
        if (condition.evaluate()) {
            if (ifBody.hasNext()) {
                ifBody.next().execute(unit);
            }
        }
        if (elseBody != null) {
            if (elseBody.hasNext()) {
                elseBody.next().execute(unit);
            }
        }
    }
}
