package hillbillies.model.expression;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public abstract class Expression<T> {

    protected final T value;

    public Expression(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
