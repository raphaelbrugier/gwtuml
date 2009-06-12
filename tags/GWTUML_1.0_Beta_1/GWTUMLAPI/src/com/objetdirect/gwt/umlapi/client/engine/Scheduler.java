/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;
import java.util.LinkedList;

import com.google.gwt.user.client.Timer;

/**
 * This class is usefull to queue tasks
 * 
 * @author Henri Darmet, Florian Mounier
 * 
 */
public class Scheduler {
	/**
	 * This class represents a classic Task
	 * 
	 * @author Henri Darmet, Florian Mounier
	 */
	public static abstract class Task extends Timer {
		private final String	groupId;
		private final int		delay;

		/**
		 * Constructor of Task with a specific object
		 * 
		 * @param groupId
		 *            A {@link String} to identify a task group
		 */
		public Task(final String groupId) {
			this(groupId, 5);
		}

		/**
		 * Constructor of Task with a specific object
		 * 
		 * @param groupId
		 *            A {@link String} to identify a task group
		 * @param delay
		 *            Specifies a delay for this task (default is 5ms)
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.gwt.user.client.Timer#run()
		 */
		@Override
		public void run() {
			this.process();
			Scheduler.done(this);
		}
	}

	private static Task				currentTask;
	private static LinkedList<Task>	queuedTasks	= new LinkedList<Task>();

	/**
	 * This method cancel all the queued task (the current might still be performed)
	 * 
	 * @param groupId
	 *            The groupId of the {@link Task}s to cancel
	 */
	public static void cancel(final String groupId) {
		if ((Scheduler.currentTask != null) && Scheduler.currentTask.groupId.equals(groupId)) {
			Scheduler.currentTask.cancel();
			Scheduler.queuedTasks.remove(Scheduler.currentTask);
			Scheduler.currentTask = null;
		}
		final ArrayList<Task> taskToRemove = new ArrayList<Task>(); // Avoid concurrent modification
		for (final Task task : Scheduler.queuedTasks) {

			if ((task != null) && task.groupId.equals(groupId)) {
				taskToRemove.add(task);
			}
		}
		Scheduler.queuedTasks.removeAll(taskToRemove);
	}

	private static void done(final Task t) {
		final Task next = Scheduler.queuedTasks.poll();
		if (next != null) {
			Scheduler.execute(next);
		} else {
			Scheduler.currentTask = null;
		}

	}

	private static void execute(final Task t) {
		Scheduler.currentTask = t;
		t.schedule(Scheduler.currentTask.delay);
	}

	private static void register(final Task t) {
		if (Scheduler.currentTask == null) {
			Scheduler.execute(t);
		} else {
			Scheduler.queuedTasks.add(t);
		}

	}

}
