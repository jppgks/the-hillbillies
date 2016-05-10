package hillbillies.model.statements;

import hillbillies.model.Unit;

import java.util.Iterator;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public abstract class Statement implements Iterator<Statement> {

    private boolean hasNext = true;

    abstract void execute(Unit unit);

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public Statement next() {
        hasNext = false;
        return this;
    }
}