package com.objetdirect.gwt.umlapi.client;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;

/**
 * This class is a static helper for entire application
 * @author fmounier
 *
 */
public class UMLDrawerHelper {

	private static EventPreview eventPreview = null;
	/**
	 * Make a pretty object name (<code>name[hash]</code>) from an object
	 * @param o The object 
	 * @return The pretty name of the object
	 */
	public static String getShortName(Object o) {
		if (o == null)
			return "null";
		String objectName = o.getClass().getName() + "[" + o.hashCode() + "]";
		String[] stringParts = objectName.split("\\.");
		int lastIndex = stringParts.length;
		if (lastIndex > 1)
			return stringParts[lastIndex - 1];
		else {
			Log.warn("Cannot split " + objectName + " <=" + lastIndex);
			return "" + o;
		}
	}
	
	public static int getMaxOf(List<Integer> list) {
		int max = 0;
		for (int i : list) {
			max = i > max ? i : max;	
		}
		return max;
	}
	
	public static void disableBrowserEvents() {
		if(eventPreview == null) makeEventPreview();
		DOM.addEventPreview(eventPreview);
	}
	public static void enableBrowserEvents() {
		if(eventPreview == null) makeEventPreview();
		DOM.removeEventPreview(eventPreview);
	}
	private static void makeEventPreview()  {
		eventPreview = new EventPreview() { 
			public boolean onEventPreview(Event event) {
				
				switch (DOM.eventGetType(event)) {
				case Event.ONMOUSEDOWN:
				case Event.ONMOUSEMOVE:
				case Event.ONMOUSEUP: // Tell the browser not to act on the event. 
					DOM.eventPreventDefault(event); 
					
				}
				return true; // But DO allow the event to fire. 
			}
		};
	}
}
