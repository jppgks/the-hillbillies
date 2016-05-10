package hillbillies.part3.programs;

import hillbillies.model.*;
import hillbillies.model.expression.*;
import hillbillies.model.statements.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class of ...
 *
 * @author  ...
 * @version 1.0
 */
public class TaskFactory implements ITaskFactory<Expression<?>, Statement, Task> {

    private HashMap<String, Expression<?>> context;

    /* TASKS */

    /**
     * Create a list of tasks from the given arguments.
     *
     * @param name
     *            The name of the task
     * @param priority
     *            The initial priority of the task
     * @param activity
     *            The activity of the task. Most likely this is a sequence
     *            statement.
     * @param selectedCubes
     *            A list of cube coordinates (each represented as an array {x,
     *            y, z}) that were selected by the player in the GUI.
     * @return A list of new task instances. One task instance should be created
     *         for each selectedCube coordinate. If selectedCubes is empty and
     *         the 'selected' expression does not occur in the activity, a list
     *         with exactly one Task instance should be returned.
     */
	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List<int[]> selectedCubes) {
        List<Task> taskList = new ArrayList<>();
        Task task = new Task(name, priority, activity);
        taskList.add(task);
        // Returning multiple tasks???
        return taskList;
	}

    /* STATEMENTS */

	@Override
	public Statement createAssignment(String variableName, Expression value, SourceLocation sourceLocation) {
        context.put(variableName, value);
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
		return context.get(variableName);
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
	public PositionExpression<Unit> createHerePosition(SourceLocation sourceLocation) {
		return new PositionExpressionUnit();
	}

	@Override
	public PositionExpression<Unit> createLogPosition(SourceLocation sourceLocation) {
        return new PositionExpressionLog();
	}

	@Override
	public PositionExpression<Unit> createBoulderPosition(SourceLocation sourceLocation) {
        return new PositionExpressionBoulder();
	}

	@Override
	public PositionExpression<Unit> createWorkshopPosition(SourceLocation sourceLocation) {
        return new PositionExpressionWorkshop();
	}

	@Override
	public PositionExpression<int[]> createSelectedPosition(SourceLocation sourceLocation) {
		return new PositionExpressionSelected();
	}

	@Override
	public PositionExpression<World> createNextToPosition(Expression position, SourceLocation sourceLocation) {
		return new PositionExpressionNextTo(position);
	}

	@Override
	public Expression<Position> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		return new LiteralPositionExpression(new int[] {x, y, z});
	}

	@Override
	public UnitExpression createThis(SourceLocation sourceLocation) {
        return new UnitExpressionThis();
	}

	@Override
	public UnitExpression createFriend(SourceLocation sourceLocation) {
        return new UnitExpressionFriend();
	}

	@Override
	public UnitExpression createEnemy(SourceLocation sourceLocation) {
        return new UnitExpressionEnemy();
	}

	@Override
	public UnitExpression createAny(SourceLocation sourceLocation) {
        return new UnitExpressionAny();
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
