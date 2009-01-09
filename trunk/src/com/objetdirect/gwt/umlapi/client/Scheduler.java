package com.objetdirect.gwt.umlapi.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Timer;

public class Scheduler {

	public static abstract class Task extends Timer {
		public Task() {
			this(null);
		}
		public Task(Object subject) {
			this.subject = subject;
			Scheduler.register(this);
		}
		
		public abstract void process();
		public void run() {
			process();
			Scheduler.done(this);
		}
		boolean done = false;
		Task next;
		Object subject;
	}
	
	static public void register(Task t) {
		if (t.subject!=null) {
			Task old = objects.get(t.subject);
			if (old!=null)
				return;
			objects.put(t.subject, t);
		}
		if (last==null) {
			first = t;
			last = t;
			execute(t);
		}
		else {
			last.next = t;
			last = t;
		}
	}

	private static void execute(Task t) {
		if (t.done)
			done(t);
		else
			t.schedule(5);
	}
	
	static public void done(Task t) {
		if (t!=first)
			throw new UMLDrawerException("Wrong task");
		else {
			if (t.subject!=null)
				objects.remove(t.subject);
			first = t.next;
			if (first==null)
				last=null;
			else 
				execute(first);
		}
	}
	
	static Task first = null;
	static Task last = null;
	static Map<Object, Task> objects = new HashMap<Object, Task>();
}
