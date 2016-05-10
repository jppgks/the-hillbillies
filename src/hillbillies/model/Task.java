package hillbillies.model;

import hillbillies.model.statements.*;

/**
 * Created by joppegeluykens on 14/04/16.
 */
public class Task{
	
	/**
	 * 
	 *variable that's hold the name if this task
	 * 
	 */
	private String taskName;
	
	
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
	 * Return the taskName of this Task.
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Set the taskName of this Task to the given taskName.
	 *
	 * @param  taskName
	 *         The taskName to set.
	 * @post   The taskName of this of this Task is equal to the given taskName.
	 *       | new.gettaskName() == taskName
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Task)) {
			return false;
		}
		return  (this.getTaskName().equals(((Task)o).getTaskName())&&
				(this.getPriority() ==((Task)o).getPriority())&&
				(this.getActivities().equals(((Task)o).getActivities())));
		
	}
	@Override
	public int hashCode(){
		int result = hashCode();
		if (result == 0) {
			result = 17;
			result = 31 * result + this.getTaskName().hashCode();
			result = 31 * result + this.getPriority();
			result = 31 * result + this.getActivities().hashCode();
		}
		return result;
	}
}
