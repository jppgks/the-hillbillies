package hillbillies.model.expression;

/**
 * Created by joppegeluykens on 09/05/16.
 */
public class LogicalExpression extends Expression<Boolean> {

    public LogicalExpression(Boolean booleanValue) {
        this.value = booleanValue;
    }

}
