/*
 * This file is part of the GWTUML project and was written by Raphael Brugier <raphael-dot-brugier.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.helpers;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;


/**
 * Concrete listener to add on the canvas widget.
 * It allows to interact directly with the canvas by simulating mouse events, for example to use in the animated demo. 
 * @author Rapahel Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class CanvasListener implements GfxObjectListener {

	private UMLCanvas canvas;
	
	public CanvasListener(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void mouseDoubleClicked(final GfxObject graphicObject, final Event event) {
		if (canvas.isMouseEnabled()) {
			doubleClick(
				graphicObject,
				new Point(event.getClientX(), event.getClientY())
			);
		}
	}
	
	/**
	 * This method represent a double click with the mouse. <br />
	 * It's automatically called on double click but can also be called manually for testing purpose
	 * 
	 * @param gfxObject
	 *            The object on which this event has occurred
	 * @param location
	 *            The location of the event
	 */
	public void doubleClick(final GfxObject gfxObject, final Point location) {
		Log.trace("Mouse double clicked on " + gfxObject + " at " + location);
		canvas.mouseDoubleClicked(gfxObject, location);
	}

	@Override
	public void mouseMoved(final Event event) {
		if (canvas.isMouseEnabled()) {
			move(
				new Point(event.getClientX(), event.getClientY()),
				event.getButton(),
				event.getCtrlKey(),
				event.getShiftKey()
			);
		}
	}
	
	/**
	 * This method represent a movement with the mouse. <br />
	 * It's automatically called on mouse move but can also be called manually for testing purpose
	 * 
	 * @param location
	 *            The location of the event
	 * @param triggerButton
	 *            A number representing which button has triggered the event
	 * @param isCtrlDown
	 *            True if ctrl key was down during the event
	 * @param isShiftDown
	 *            True if shift key was down during the event
	 */
	public void move(final Point location, int triggerButton, boolean isCtrlDown, boolean isShiftDown) {
		Log.trace("Mouse moved to " + location + " with button " + triggerButton + " ctrl " + isCtrlDown + " shift " + isShiftDown);
		canvas.mouseMoved(location, isCtrlDown, isShiftDown);
	}

	@Override
	public void mousePressed(final GfxObject graphicObject, final Event event) {
		if (canvas.isMouseEnabled()) {
			press(graphicObject, 
				new Point(event.getClientX(), event.getClientY()), 
				event.getButton(), 
				event.getCtrlKey(),
				event.getShiftKey()
			);
		}
	}
	
	/**
	 * This method represent a mouse press with the mouse. <br />
	 * It's automatically called on mouse press but can also be called manually for testing purpose
	 * 
	 * @param gfxObject
	 *            The object on which this event has occurred
	 * @param location
	 *            The location of the event
	 * @param triggerButton
	 *            A number representing which button has triggered the event
	 * @param isCtrlDown
	 *            True if ctrl key was down during the event
	 * @param isShiftDown
	 *            True if shift key was down during the event
	 */
	public void press(final GfxObject gfxObject, final Point location, int triggerButton, boolean isCtrlDown, boolean isShiftDown) {
		Log.trace("Mouse pressed on " + gfxObject + " at " + location + " with button " + triggerButton + " ctrl " + isCtrlDown + " alt " + isShiftDown);
		if (triggerButton == NativeEvent.BUTTON_LEFT) {
			canvas.mouseLeftPressed(gfxObject, location, isCtrlDown, isShiftDown);
		} else if (triggerButton == NativeEvent.BUTTON_RIGHT) {
			canvas.mouseRightPressed(gfxObject, location);
		}
	}

	@Override
	public void mouseReleased(final GfxObject graphicObject,final Event event) {
		if (canvas.isMouseEnabled()) {
			release(graphicObject, 
				new Point(event.getClientX(), event.getClientY()), 
				event.getButton(), 
				event.getCtrlKey(),
				event.getShiftKey()
			);
		}
	}
	
	/**
	 * This method represent a mouse release with the mouse. <br />
	 * It's automatically called on release but can also be called manually for testing purpose
	 * 
	 * @param gfxObject
	 *            The object on which this event has occurred
	 * @param location
	 *            The location of the event
	 * @param triggerButton
	 *            A number representing which button has triggered the event
	 * @param isCtrlDown
	 *            True if ctrl key was down during the event
	 * @param isShiftDown
	 *            True if shift key was down during the event
	 */
	public void release(final GfxObject gfxObject, final Point location, int triggerButton, boolean isCtrlDown, boolean isShiftDown) {
		Log.trace("Mouse released on " + gfxObject + " at " + location + " with button " + triggerButton + " ctrl " + isCtrlDown + " shift " + isShiftDown);
		if (triggerButton == NativeEvent.BUTTON_LEFT) {
			canvas.mouseReleased(gfxObject, location, isCtrlDown, isShiftDown);
		}
	}
}
