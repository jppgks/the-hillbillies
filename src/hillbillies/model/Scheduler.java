package hillbillies.model;

import java.lang.reflect.GenericArrayType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by joppegeluykens on 14/04/16.
 */
public class Scheduler implements Iterator<Task> {
	
	private Faction faction;
	
	private List<Task> tasks;
	
	private HashMap<Task,Unit> assigned = new HashMap<Task, Unit>();

	/**
	 * Return the assigned of this Scheduler.
	 */
	public HashMap<Task, Unit> getAssigned() {
		return assigned;
	}

	/**
	 * Return the tasks of this Scheduler.
	 */
	public List<Task> getTasks() {
		return tasks;
	}

	/**
	 * Set the tasks of this Scheduler to the given tasks.
	 *
	 * @param  tasks
	 *         The tasks to set.
	 * @post   The tasks of this of this Scheduler is equal to the given tasks.
	 *       | new.gettasks() == tasks
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Return the faction of this Scheduler.
	 */
	public Faction getFaction() {
		return faction;
	}

	/**
	 * Set the faction of this Scheduler to the given faction.
	 *
	 * @param  faction
	 *         The faction to set.
	 * @post   The faction of this of this Scheduler is equal to the given faction.
	 *       | new.getfaction() == faction
	 */
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	
	public void add(Task task){
		
	}
	
	public void addAll(List<Task> task){
		
	}

	public void remove() {
		
	}
	
	public void removeCollection(Collection<Task> tasks) {
		
	}
	
	public void replace(Task original, Task replacment){
		
	}
	
	public boolean hasAsTask(Task task){
		return false;
	}
	
	public boolean hasAsTasks(Collection<Task> task){
		return false;
	}
	
	public List<Task> getAll(){
		return null;	
	}
	
	public List<Task> getPositive(){
		return null;
	}
	
	public void markAssigned(Task task, Unit unit) {
		
	}
	
	public void resetAssigned(Task task, Unit unit) {
		
	}
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Task next() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
