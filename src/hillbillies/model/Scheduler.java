package hillbillies.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by joppegeluykens on 14/04/16.
 */
public class Scheduler implements Iterable<Task> {

	private List<Task> tasks = new ArrayList<>();
	
	public void add(Task task){
		this.tasks.add(task);
	}
	
	public void addAll(List<Task> tasks){
		this.tasks.addAll(tasks);
	}

	public void remove(Task task) {
		this.tasks.remove(task);
	}
	
	public void removeCollection(Collection<Task> tasks) {
		this.tasks.removeAll(tasks);
	}
	
	public void replace(Task original, Task replacement){
		this.tasks.remove(original);
		this.tasks.add(replacement);
	}
	
	public boolean hasAsTask(Task task){
		return this.tasks.contains(task);
	}
	
	public boolean hasAsTasks(Collection<Task> tasks){
		return this.tasks.containsAll(tasks);
	}

	/**
	 * Return the tasks of this Scheduler.
	 */
	public List<Task> getAll() {
		return new ArrayList<>(tasks);
	}
	
	public List<Task> getPositive(){
		return this.tasks.stream()
				.filter(task -> task.getPriority() > 0)
				.collect(Collectors.toList());
	}
	
	public void markAssigned(Task task, Unit unit) {
		this.tasks.remove(task);
		task.assignTo(unit);
	}

	// Documentatie!
	public void resetAssigned(Task task, Unit unit) {
		
	}

	public void schedule(Task task) {
		this.add(task);
	}

	public Iterator<Task> iterator() {
		return this.tasks.iterator();
	}
	public boolean isEmpty(){
		return this.tasks.isEmpty();
	}
}
