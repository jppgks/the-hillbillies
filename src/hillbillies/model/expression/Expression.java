package hillbillies.model.expression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public abstract class Expression<T> {

    protected T value;

    public T evaluate() {
        return value;
    }
}
