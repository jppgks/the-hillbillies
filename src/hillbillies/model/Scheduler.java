package hillbillies.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by joppegeluykens on 14/04/16.
 */
public class Scheduler implements Iterable<Task> {

	private PriorityQueue<Task> tasks = new PriorityQueue<>(new Comparator<Task>() {
		@Override
		public int compare(Task o1, Task o2) {
			return o2.getPriority()-o1.getPriority();
		}
	});
	
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
		return new Iterator<Task>() {
			@Override
			public boolean hasNext() {
				return ! tasks.isEmpty();
			}

			@Override
			public Task next() {
				return tasks.poll(); // Does the iterator only return the tasks or does it also remove them from the queue?
			}
		};
	}
	public boolean isEmpty(){
		return this.tasks.isEmpty();
	}
}
