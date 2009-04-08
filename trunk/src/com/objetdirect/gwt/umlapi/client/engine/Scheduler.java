package com.objetdirect.gwt.umlapi.client.engine;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Timer;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;

public class Scheduler {
    /**
     * @author hdarmet
     */
    public static abstract class Task extends Timer {
	boolean done = false;

	Task next;
	Object subject;

	public Task() {
	    this(null);
	}

	public Task(final Object subject) {
	    this.subject = subject;
	    Scheduler.register(this);
	}

	public abstract void process();

	@Override
	public void run() {
	    process();
	    Scheduler.done(this);
	}
    }

    static Task first = null;
    static Task last = null;
    static Map<Object, Task> objects = new HashMap<Object, Task>();

    static public void done(final Task t) {
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

    private static void execute(final Task t) {
	if (t.done) {
	    done(t);
	} else {
	    t.schedule(5);
	}
    }

    static public void register(final Task t) {
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
}