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
package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.Event;

/**
 * This interface normalize the events for a graphic canvas for any gfx implementation
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public interface GfxObjectListener {

	/**
	 * Invoked when a mouse button has been double clicked on a gfxObject.
	 * 
	 * @param graphicObject
	 *            the <code>GraphicObject</code> which launches the event
	 * @param event
	 *            The event object from DOM
	 */
	public void mouseDoubleClicked(final GfxObject graphicObject, final Event event);

	/**
	 * Invoked when a the mouse has been moved
	 * 
	 * @param event
	 *            The event object from DOM
	 */
	public void mouseMoved(final Event event);

	/**
	 * Invoked when the left mouse button has been pressed on a gfxObject.
	 * 
	 * @param graphicObject
	 *            the <code>GraphicObject</code> which launches the event
	 * @param event
	 *            The event object from DOM
	 */
	public void mousePressed(final GfxObject graphicObject, final Event event);

	/**
	 * Invoked when a mouse button has been release on a gfxObject.
	 * 
	 * @param graphicObject
	 *            the <code>GraphicObject</code> which launches the event
	 * @param event
	 *            The event object from DOM
	 */
	public void mouseReleased(final GfxObject graphicObject, final Event event);

}
