package com.objetdirect.gwt.umlapi.client.helpers;

import com.google.gwt.user.client.Event;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;

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
