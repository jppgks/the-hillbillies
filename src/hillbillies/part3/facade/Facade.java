package hillbillies.part3.facade;

import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.TaskFactory;
import hillbillies.part3.programs.TaskParser;
import hillbillies.tests.facade.Part3TestPartial;
import ogp.framework.util.ModelException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by joppegeluykens on 14/04/16.
 */
public class Facade extends hillbillies.part2.facade.Facade implements IFacade {

    /**
     * Create a new instance of a Task factory.
     *
     * <p>
     * This factory is used by the parser ({@link TaskParser}) to construct an
     * in-memory representation of your program. For example, when reading the
     * task description
     *
     * <pre>
     * name: "test"
     * priority: 1
     * activities: moveTo here;
     * </pre>
     *
     * the parser will create a Task object by (conceptually) executing the
     * following code:
     *
     * <pre>
     * factory.createTask("test", 1, factory.createMoveTo(factory.createHerePosition()))
     * </pre>
     *
     * on the returned factory object.
     *
     * <p>
     * For testing, you may use the methods from {@link TaskParser} yourself, as
     * demonstrated in the partial test file {@link Part3TestPartial}.
     *
     * @return An instance of ITaskFactory. See the documentation of that
     *         interface for an explanation of its parameters.
     */
    @Override
    public ITaskFactory<?, ?, Task> createTaskFactory() {
        return new TaskFactory();
    }

    /**
     * Returns whether the given task is well-formed.
     *
     * A task is well-formed if
     * <ul>
     * <li>it is type-safe</li>
     * <li>there are no break statements outside loops</li>
     * <li>variables assigned before they are first used</li>
     * </ul>
     * See the assignment text for more details.
     *
     * @param task
     *            The task to check for well-formedness
     *
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public boolean isWellFormed(Task task) throws ModelException {
        return task.isWellFormed();
    }

    /**
     * Returns the scheduler associated to the given faction.
     *
     * @param faction
     *            The faction of which to return the scheduler.
     *
     * @return The scheduler associated to the given faction.
     *
     * @throws ModelException
     *             A precondition was violated or an exception was thrown.
     */
    @Override
    public Scheduler getScheduler(Faction faction) throws ModelException {
        return faction.getScheduler();
    }

    @Override
    public void schedule(Scheduler scheduler, Task task) throws ModelException {
        scheduler.schedule(task);
    }

    @Override
    public void replace(Scheduler scheduler, Task original, Task replacement) throws ModelException {
        scheduler.replace(original, replacement);
    }

    @Override
    public boolean areTasksPartOf(Scheduler scheduler, Collection<Task> tasks) throws ModelException {
        return scheduler.hasAsTasks(tasks);
    }

    @Override
    public Iterator<Task> getAllTasksIterator(Scheduler scheduler) throws ModelException {
        return scheduler.iterator();
    }

    @Override
    public Set<Scheduler> getSchedulersForTask(Task task) throws ModelException {
        return task.getSchedulers();
    }

    @Override
    public Unit getAssignedUnit(Task task) throws ModelException {
        return task.getAssignedUnit();
    }

    @Override
    public Task getAssignedTask(Unit unit) throws ModelException {
        return unit.getAssignedTask();
    }

    @Override
    public String getName(Task task) throws ModelException {
        return task.getName();
    }

    @Override
    public int getPriority(Task task) throws ModelException {
        return task.getPriority();
    }
}
