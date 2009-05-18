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
	Object owner;
	Object subject;
	private int delay;

	/**
	 * Constructor of Task without specific object
	 *
	 */
	public Task() {
	    this(null, null);
	}

	/**
	 * Constructor of Task with a specific object 
	 * 
	 * @param owner Specifies the caller of this task for later use
	 * @param subject Specifies a parameter which can be used in {@link Task#process()}
	 */
	public Task(final Object owner, final Object subject) {
	    this(owner, subject,  5);
	}
	/**
	 * Constructor of Task with a specific object 
	 * 
	 * @param owner Specifies the caller of this task for later use
	 * @param subject Specifies a parameter which can be used in {@link Task#process()}
	 * @param delay Specifies a delay for this task (default is 5ms)
	 */
	public Task(final Object owner, final Object subject, final int delay) {
	    super();
	    this.subject = subject;
	    this.owner = owner;
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
     * This method cancel all the queued task (the current will still be performed)
     * @param owner 
     */
    public static void cancel(Object owner) {
	if(currentTask != null && currentTask.owner != null && currentTask.owner.equals(owner)) {
	    currentTask.cancel();
	    queuedTasks.remove(currentTask);
	    currentTask = null;
	}
	ArrayList<Task> taskToRemove = new ArrayList<Task>(); //Avoid concurrent modification
	for (Task task : queuedTasks) {
	    
	    if(task != null && task.owner != null && task.owner.equals(owner)) {
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
