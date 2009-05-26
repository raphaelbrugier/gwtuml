/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
