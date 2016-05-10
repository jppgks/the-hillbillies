package hillbillies.model.expression;

/*
 * Not abstract because True and False expressions are instantiated through this class.
 */
public class LogicalExpression extends Expression<Boolean> {

    public LogicalExpression(Boolean booleanValue) {
        this.value = booleanValue;
    }

}
