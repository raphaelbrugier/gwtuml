/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class Mouse {

    private static boolean imEnabled = true;

    public static void doubleClick(GfxObject gfxObject, Point location,
	    int triggerButton, boolean isCtrlDown, boolean isAltDown, boolean isShiftDown,
	    boolean isMetaKey) {
	if(imEnabled) {
	    Log.trace("Mouse double clicked on " + gfxObject + " at " + location + " with button " + triggerButton + " ctrl " + isCtrlDown + " alt " + isAltDown + " shift " + isShiftDown);
	    Session.getActiveCanvas().mouseDoubleClicked(gfxObject, location);
	}
    }

    public static void move(Point location,
	    int triggerButton, boolean isCtrlDown, boolean isAltDown, boolean isShiftDown,
	    boolean isMetaKey) {
	if(imEnabled) {
	    Log.trace("Mouse moved to " + location + " with button " + triggerButton + " ctrl " + isCtrlDown + " alt " + isAltDown + " shift " + isShiftDown);
	    Session.getActiveCanvas().mouseMoved(location, isCtrlDown, isShiftDown);

	}
    }

    public static void press(GfxObject gfxObject, Point location,
	    int triggerButton, boolean isCtrlDown, boolean isAltDown, boolean isShiftDown,
	    boolean isMetaKey) {
	if(imEnabled) {
	    Log.trace("Mouse pressed on " + gfxObject + " at " + location + " with button " + triggerButton + " ctrl " + isCtrlDown + " alt " + isAltDown + " shift " + isShiftDown);
	    if(triggerButton == NativeEvent.BUTTON_LEFT) {
		Session.getActiveCanvas().mouseLeftPressed(gfxObject, location, isCtrlDown, isShiftDown);
	    } else if (triggerButton == NativeEvent.BUTTON_RIGHT) {
		Session.getActiveCanvas().mouseRightPressed(gfxObject, location);
	    }

	}
    }

    public static void release(GfxObject gfxObject, Point location,
	    int triggerButton, boolean isCtrlDown, boolean isAltDown, boolean isShiftDown,
	    boolean isMetaKey) {
	if(imEnabled) {
	    Log.trace("Mouse released on " + gfxObject + " at " + location + " with button " + triggerButton + " ctrl " + isCtrlDown + " alt " + isAltDown + " shift " + isShiftDown);
	    if(triggerButton == NativeEvent.BUTTON_LEFT) {
		Session.getActiveCanvas().mouseReleased(gfxObject, location, isCtrlDown, isShiftDown);
	    }
	}
    }
    /**
     * Getter to the current state of {@link Mouse}
     * 
     * @return true if it is enabled false otherwise
     */
    public static boolean isEnabled() {
	return imEnabled;
    }

    /**
     * Set the {@link Mouse} state. This is used to disable {@link Mouse} while editing for instance
     * 
     * @param isEnabled The status : True to activate False to disable 
     */
    public static void setEnabled(final boolean isEnabled) {
	Mouse.imEnabled = isEnabled;
    }
}
