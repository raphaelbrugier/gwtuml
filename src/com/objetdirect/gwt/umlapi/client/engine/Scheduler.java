package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;
import java.util.LinkedList;

import com.google.gwt.user.client.Timer;

/**
 * This class is usefull to queue tasks 
 * 
 * @author Henri Darmet
 *
 */
public class Scheduler {
    /**
     * This class represents a classic Task
     * 
     * @author Henri Darmet
     */
    public static abstract class Task extends Timer {
	private String groupId;
	private int delay;

	/**
	 * Constructor of Task with a specific object 
	 * 
	 * @param groupId A {@link String} to identify a task group
	 */
	public Task(final String groupId) {
	    this(groupId, 5);
	}
	/**
	 * Constructor of Task with a specific object 
	 * 
	 * @param groupId A {@link String} to identify a task group
	 * @param delay Specifies a delay for this task (default is 5ms)
	 */
	public Task(final String groupId, final int delay) {
	    super();
	    this.groupId = groupId;
	    this.delay = delay;
	    Scheduler.register(this);
	}

	/**
	 * This method contains the code of the Task that will be run 
	 */
	public abstract void process();

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.Timer#run()
	 */
	@Override
	public void run() {
	    process();
	    Scheduler.done(this);
	}
    }
    private static Task currentTask;
    private static LinkedList<Task> queuedTasks = new LinkedList<Task>();

    /**
     * This method cancel all the queued task (the current might still be performed)
     * 
     * @param groupId The groupId of the {@link Task}s to cancel
     */
    public static void cancel(String groupId) {
	if(currentTask != null && currentTask.groupId.equals(groupId)) {
	    currentTask.cancel();
	    queuedTasks.remove(currentTask);
	    currentTask = null;
	}
	ArrayList<Task> taskToRemove = new ArrayList<Task>(); //Avoid concurrent modification
	for (Task task : queuedTasks) {
	    
	    if(task != null && task.groupId.equals(groupId)) {
		taskToRemove.add(task);
	    }
	}
	queuedTasks.removeAll(taskToRemove);
    }
    private static void done(final Task t) {
	Task next = queuedTasks.poll(); 
	if (next != null) {
	    execute(next);
	} else {
	    currentTask = null;
	}
	    
    }

    private static void register(final Task t) {	
	if(currentTask == null) execute(t);
	else queuedTasks.add(t);

    }

    private static void execute(final Task t) {
	currentTask = t;
	t.schedule(currentTask.delay);
    }

}
