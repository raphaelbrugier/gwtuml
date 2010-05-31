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

import com.google.gwt.user.client.Event;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;


/**
 * Listener to add on the canvas widget.
 * 
 * @author Rapahel Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class CanvasListener implements GfxObjectListener {

	private UMLCanvas canvas;
	
	public CanvasListener(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void mouseDoubleClicked(final GfxObject graphicObject,
			final Event event) {
		if (canvas.isMouseEnabled()) {
			Mouse.doubleClick(graphicObject, new Point(event
					.getClientX(), event.getClientY()), event
					.getButton(), event.getCtrlKey(), event
					.getAltKey(), event.getShiftKey(), event
					.getMetaKey());
		}
	}

	@Override
	public void mouseMoved(final Event event) {
		if (canvas.isMouseEnabled()) {
			Mouse.move(new Point(event.getClientX(), event
					.getClientY()), event.getButton(), event
					.getCtrlKey(), event.getAltKey(), event
					.getShiftKey(), event.getMetaKey());
		}
	}

	@Override
	public void mousePressed(final GfxObject graphicObject,
			final Event event) {
		if (canvas.isMouseEnabled()) {
			Mouse.press(graphicObject, new Point(
					event.getClientX(), event.getClientY()), event
					.getButton(), event.getCtrlKey(), event
					.getAltKey(), event.getShiftKey(), event
					.getMetaKey());
		}
	}

	@Override
	public void mouseReleased(final GfxObject graphicObject,
			final Event event) {
		if (canvas.isMouseEnabled()) {
			Mouse.release(graphicObject, new Point(event
					.getClientX(), event.getClientY()), event
					.getButton(), event.getCtrlKey(), event
					.getAltKey(), event.getShiftKey(), event
					.getMetaKey());
		}
	}
}
