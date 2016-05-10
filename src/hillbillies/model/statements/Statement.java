package hillbillies.model.statements;

import java.util.Iterator;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public abstract class Statement implements Iterator<Statement> {

    private boolean hasNext = true;

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