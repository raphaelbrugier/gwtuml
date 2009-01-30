package com.objetdirect.gwt.umlapi.client.gfx.incubator;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class GWTCanvasWithListeners extends GWTCanvas {
	private MouseListenerCollection mouseListeners;
	private ClickListenerCollection clickListeners;

	public GWTCanvasWithListeners() {
		super();
	}

	public GWTCanvasWithListeners(int coordX, int coordY) {
		super(coordX, coordY);
	}

	public GWTCanvasWithListeners(int coordX, int coordY, int pixelX, int pixelY) {
		super(coordX, coordY, pixelX, pixelY);
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if ((clickListeners != null && clickListeners.size() > 0)
				|| (mouseListeners != null && mouseListeners.size() > 0)) {
			int x = event.getClientX();
			int y = event.getClientY();

			switch (DOM.eventGetType(event)) {
			case Event.ONCLICK:
				if (clickListeners != null) {
					clickListeners.fireClick(this);
				}
				break;
			// TODO ; Fix this hacky hack
			case Event.ONDBLCLICK:
				if (mouseListeners != null) {
					mouseListeners.fireMouseUp(this, -x, -y);
				}
				break;
			case Event.ONMOUSEDOWN:
				if (mouseListeners != null) {
					if (event.getButton() == Event.BUTTON_RIGHT)
						mouseListeners.fireMouseDown(this, -x, -y); // TODO ;
																	// Fix this
																	// hacky
																	// hack
					else
						mouseListeners.fireMouseDown(this, x, y);
				}
				break;
			case Event.ONMOUSEMOVE:
				if (mouseListeners != null) {
					mouseListeners.fireMouseMove(this, x, y);
				}
				break;
			case Event.ONMOUSEUP:
				if (mouseListeners != null) {
					mouseListeners.fireMouseUp(this, x, y);
				}
				break;
			}
		}
	}

	public void addClickListener(ClickListener listener) {
		if (clickListeners == null) {
			clickListeners = new ClickListenerCollection();
			sinkEvents(Event.ONCLICK + Event.ONDBLCLICK);
		}
		clickListeners.add(listener);
	}

	public void addMouseListener(MouseListener listener) {
		if (mouseListeners == null) {
			mouseListeners = new MouseListenerCollection();
			sinkEvents(Event.MOUSEEVENTS);
		}
		mouseListeners.add(listener);
	}

	public void removeClickListener(ClickListener listener) {
		if (clickListeners != null) {
			clickListeners.remove(listener);
		}
	}

	public void removeMouseListener(MouseListener listener) {
		if (mouseListeners != null) {
			mouseListeners.remove(listener);
		}
	}
}