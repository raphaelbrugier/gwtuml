package com.objetdirect.gwt.umlapi.client.engine;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Timer;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;

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
	boolean done = false;

	Task next;
	Object subject;

	/**
	 * Constructor of Task without specific object
	 *
	 */
	public Task() {
	    this(null);
	}

	/**
	 * Constructor of Task with a specific object 
	 *
	 * @param subject
	 */
	public Task(final Object subject) {
	    this.subject = subject;
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

    static Task first = null;
    static Task last = null;
    static Map<Object, Task> objects = new HashMap<Object, Task>();

    static void done(final Task t) {
	if (t != first) {
	    throw new UMLDrawerException("Wrong task");
	}

	if (t.subject != null) {
	    objects.remove(t.subject);
	}
	first = t.next;
	if (first == null) {
	    last = null;
	} else {
	    execute(first);
	}

    }

    static void register(final Task t) {
	if (t.subject != null) {
	    final Task old = objects.get(t.subject);
	    if (old != null) {
		return;
	    }
	    objects.put(t.subject, t);
	}
	if (last == null) {
	    first = t;
	    last = t;
	    execute(t);
	} else {
	    last.next = t;
	    last = t;
	}
    }

    private static void execute(final Task t) {
	if (t.done) {
	    done(t);
	} else {
	    t.schedule(5);
	}
    }
}
