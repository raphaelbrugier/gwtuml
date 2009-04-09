package com.objetdirect.gwt.umlapi.client;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;

/**
 * This class is a static helper for entire application
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class UMLDrawerHelper {
    private static EventPreview eventPreview = null;

    /**
     * Disable various browser events like image drag and drop
     */
    public static void disableBrowserEvents() {
	if (eventPreview == null) {
	    makeEventPreview();
	}
	DOM.addEventPreview(eventPreview);
    }

    /**
     * Enable browser events if it was disabled before
     */
    public static void enableBrowserEvents() {
	if (eventPreview == null) {
	    makeEventPreview();
	} else {
	    DOM.removeEventPreview(eventPreview);
	}
    }

    /**
     * Useful function that return the maximum of an integer list
     * 
     * @param list
     * @return The max of all integer in list
     */
    public static int getMaxOf(final List<Integer> list) {
	int max = 0;
	for (final int i : list) {
	    max = i > max ? i : max;
	}
	return max;
    }

    /**
     * Make a pretty object name (<code>name[hash]</code>) from an object
     * 
     * @param o
     *            The object
     * @return The pretty name of the object
     */
    public static String getShortName(final Object o) {
	if (o == null) {
	    return "null";
	}
	final String objectName = o.getClass().getName() + "[" + o.hashCode()
		+ "]";
	final String[] stringParts = objectName.split("\\.");
	final int lastIndex = stringParts.length;
	if (lastIndex > 1) {
	    return stringParts[lastIndex - 1];
	}

	Log.warn("Cannot split " + objectName + " <=" + lastIndex);
	return "" + o;

    }

    private static void makeEventPreview() {
	eventPreview = new EventPreview() {
	    public boolean onEventPreview(final Event event) {

		switch (DOM.eventGetType(event)) {
		case Event.ONMOUSEDOWN:
		case Event.ONMOUSEMOVE:
		case Event.ONMOUSEUP: // Tell the browser not to act on the
		    // event.
		    DOM.eventPreventDefault(event);

		}
		return true; // But DO allow the event to fire.
	    }
	};
    }
}
