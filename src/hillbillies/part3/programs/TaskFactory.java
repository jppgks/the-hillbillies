package hillbillies.part3.programs;

import hillbillies.model.Position;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.expression.*;
import hillbillies.model.statements.*;

import java.util.List;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class TaskFactory implements ITaskFactory<Expression<?>, Statement, Task> {

    /* TASKS */

	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
		// TODO Auto-generated method stub
		return null;
	}

    /* STATEMENTS */

	@Override
	public Statement createAssignment(String variableName, Expression value, SourceLocation sourceLocation) {
        return new StatementAssignment(variableName, value);
	}

	@Override
	public Statement createWhile(Expression condition, Statement body, SourceLocation sourceLocation) {
		return new StatementWhile(condition, body);
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		return new StatementIf(condition, ifBody, elseBody);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		return new StatementBreak();
	}

	@Override
	public Statement createPrint(Expression<?> value, SourceLocation sourceLocation) {
		return new StatementPrint(value);
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		return new StatementSequence(statements);
	}

	@Override
	public Statement createMoveTo(Expression position, SourceLocation sourceLocation) {
		return new StatementMoveTo(position);
	}

	@Override
	public Statement createWork(Expression position, SourceLocation sourceLocation) {
		return new StatementWork(position);
	}

	@Override
	public Statement createFollow(Expression unit, SourceLocation sourceLocation) {
		return new StatementFollow(unit);
	}

	@Override
	public Statement createAttack(Expression unit, SourceLocation sourceLocation) {
		return new StatementAttack(unit);
	}

    /* EXPRESSIONS */

	@Override
	public Expression createReadVariable(String variableName, SourceLocation sourceLocation) {
		// Return Expression assigned to variableName, retrieve from context hashmap.
		//return new Expression(context.evaluate(variableName));
        return null;
	}

	@Override
	public BooleanExpression<Position> createIsSolid(Expression position, SourceLocation sourceLocation) {
		return new BooleanExpressionIsSolid(position);
	}

	@Override
	public BooleanExpression<Position> createIsPassable(Expression position, SourceLocation sourceLocation) {
		return new BooleanExpressionIsPassable(position);
	}

	@Override
	public BooleanExpression<Unit> createIsFriend(Expression unit, SourceLocation sourceLocation) {
		return new BooleanExpressionIsFriend(unit);
	}

	@Override
	public BooleanExpression<Unit> createIsEnemy(Expression unit, SourceLocation sourceLocation) {
		return new BooleanExpressionIsEnemy(unit);
	}

	@Override
	public BooleanExpression<Unit> createIsAlive(Expression unit, SourceLocation sourceLocation) {
		return new BooleanExpressionIsAlive(unit);
	}

	@Override
	public BooleanExpression<Unit> createCarriesItem(Expression unit, SourceLocation sourceLocation) {
		return new BooleanExpressionCarriesItem(unit);
	}

	@Override
	public LogicalExpression createNot(Expression expression, SourceLocation sourceLocation) {
		return new LogicalExpressionNot(expression);
	}

	@Override
	public LogicalExpression createAnd(Expression left, Expression right, SourceLocation sourceLocation) {
		return new LogicalExpressionAnd(left, right);
	}

	@Override
	public LogicalExpression createOr(Expression left, Expression right, SourceLocation sourceLocation) {
		return new LogicalExpressionOr(left, right);
	}

	@Override
	public Expression<Position> createHerePosition(SourceLocation sourceLocation) {
		return null;
	}

	@Override
	public Expression<Position> createLogPosition(SourceLocation sourceLocation) {
        return null;
	}

	@Override
	public Expression<Position> createBoulderPosition(SourceLocation sourceLocation) {
        return null;
	}

	@Override
	public Expression<Position> createWorkshopPosition(SourceLocation sourceLocation) {
        return null;
	}

	@Override
	public Expression<Position> createSelectedPosition(SourceLocation sourceLocation) {
        // PositionExpression
		return null;
	}

	@Override
	public Expression<Position> createNextToPosition(Expression position, SourceLocation sourceLocation) {
        // PositionExpression
		return null;
	}

	@Override
	public Expression<Position> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
        // PositionExpression
		return null;
	}

	@Override
	public Expression<Unit> createThis(SourceLocation sourceLocation) {
        // UnitExpression
		return null;
	}

	@Override
	public Expression<Unit> createFriend(SourceLocation sourceLocation) {
        // UnitExpression
		return null;
	}

	@Override
	public Expression<Unit> createEnemy(SourceLocation sourceLocation) {
        // UnitExpression
		return null;
	}

	@Override
	public Expression<Unit> createAny(SourceLocation sourceLocation) {
        // UnitExpression
		return null;
	}

	@Override
	public LogicalExpression createTrue(SourceLocation sourceLocation) {
        return new LogicalExpression(true);
	}

	@Override
	public LogicalExpression createFalse(SourceLocation sourceLocation) {
        return new LogicalExpression(false);
	}

}
