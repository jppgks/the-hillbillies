package hillbillies.model;

import hillbillies.model.statements.*;

import java.util.Set;

/**
 * Created by joppegeluykens on 14/04/16.
 */
public class Task{
	
	/**
	 * 
	 *variable that's hold the name if this task
	 * 
	 */
	private String name;
	
	
	/**
	 * 
	 * variable that's hold the priority of this tasks 
	 * 
	 */
	private int priority;
	
	/**
	 * 
	 * sequens of statements or a singel statement that contains the activities a unit must execute.
	 * 
	 */
	private Statement activities;

	private Unit assignedUnit;

	private Set<Scheduler> schedulers;

	public Task(String name, int priority, Statement activities) {
		this.name = name;
		this.priority = priority;
		this.activities = activities;
	}

	/**
	 * Return the activities of this Task.
	 */
	public Statement getActivities() {
		return activities;
	}

	/**
	 * Set the activities of this Task to the given activities.
	 *
	 * @param  activities
	 *         The activities to set.
	 * @post   The activities of this of this Task is equal to the given activities.
	 *       | new.getactivities() == activities
	 */
	public void setActivities(Statement activities) {
		this.activities = activities;
	}

	/**
	 * Return the priority of this Task.
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Set the priority of this Task to the given priority.
	 *
	 * @param  priority
	 *         The priority to set.
	 * @post   The priority of this of this Task is equal to the given priority.
	 *       | new.getpriority() == priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Return the name of this Task.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this Task to the given name.
	 *
	 * @param  name
	 *         The name to set.
	 * @post   The name of this of this Task is equal to the given name.
	 *       | new.getName() == name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Task)) {
			return false;
		}
		return  (this.getName().equals(((Task)o).getName())&&
				(this.getPriority() ==((Task)o).getPriority())&&
				(this.getActivities().equals(((Task)o).getActivities())));
		
	}
	@Override
	public int hashCode(){
		int result = hashCode();
		if (result == 0) {
			result = 17;
			result = 31 * result + this.getName().hashCode();
			result = 31 * result + this.getPriority();
			result = 31 * result + this.getActivities().hashCode();
		}
		return result;
	}

	public boolean isWellFormed() {
		return false;
	}

	public Unit getAssignedUnit() {
		return this.assignedUnit;
	}	
	public  void setAssignedUnit(Unit unit) {
		this.assignedUnit = unit;
	}
	

	public void assignTo(Unit unit) {
		this.assignedUnit = unit;
	}

	public Set<Scheduler> getSchedulers() {
		return schedulers;
	}
}
