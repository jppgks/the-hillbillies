package hillbillies.model.statements;

import hillbillies.model.Unit;

import java.util.List;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class StatementSequence extends Statement {

    private List<Statement> statements;

    public StatementSequence(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public boolean hasNext() {
        return ! statements.isEmpty();
    }

    @Override
    public Statement next() {
        return statements.remove(0);
    }

    @Override
    public void execute(Unit unit) {
        if (hasNext()) {
            next().execute(unit);
        }
    }
}
