package hillbillies.model.expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public abstract class LogicalExpression extends Expression<Boolean> {

    LogicalExpression(Boolean booleanValue) {
        super(booleanValue);
    }

    boolean evaluate() {
        return this.getValue();
    };
}
