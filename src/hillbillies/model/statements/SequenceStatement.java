package hillbillies.model.statements;

import java.util.List;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public final class SequenceStatement extends Statement {

    private final List<Statement> statements;

    public SequenceStatement(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void execute() {

    }
}
