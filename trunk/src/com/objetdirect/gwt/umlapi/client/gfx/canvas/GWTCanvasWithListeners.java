package com.objetdirect.gwt.umlapi.client.gfx.canvas;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.ClickListenerCollection;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

@SuppressWarnings("deprecation")
public class GWTCanvasWithListeners extends GWTCanvas {

    private ClickListenerCollection clickListeners;
    private MouseListenerCollection mouseListeners;

    public GWTCanvasWithListeners() {
	super();
    }

    public GWTCanvasWithListeners(final int coordX, final int coordY) {
	super(coordX, coordY);
    }

    public GWTCanvasWithListeners(final int coordX, final int coordY,
	    final int pixelX, final int pixelY) {
	super(coordX, coordY, pixelX, pixelY);
    }

    public void addClickListener(final ClickListener listener) {
	if (this.clickListeners == null) {
	    this.clickListeners = new ClickListenerCollection();
	    sinkEvents(Event.ONCLICK + Event.ONDBLCLICK);
	}
	this.clickListeners.add(listener);
    }

    public void addMouseListener(final MouseListener listener) {
	if (this.mouseListeners == null) {
	    this.mouseListeners = new MouseListenerCollection();
	    sinkEvents(Event.MOUSEEVENTS);
	}
	this.mouseListeners.add(listener);
    }

    @Override
    public void onBrowserEvent(final Event event) {
	super.onBrowserEvent(event);
	if (this.clickListeners != null && this.clickListeners.size() > 0
		|| this.mouseListeners != null && this.mouseListeners.size() > 0) {
	    final int x = event.getClientX();
	    final int y = event.getClientY();
	    switch (DOM.eventGetType(event)) {
	    case Event.ONCLICK:
		if (this.clickListeners != null) {
		    this.clickListeners.fireClick(this);
		}
		break;
	    // TODO ; Fix this hacky hack
	    case Event.ONDBLCLICK:
		if (this.mouseListeners != null) {
		    this.mouseListeners.fireMouseUp(this, -x, -y);
		}
		break;
	    case Event.ONMOUSEDOWN:
		if (this.mouseListeners != null) {
		    if (event.getButton() == NativeEvent.BUTTON_RIGHT) {
			this.mouseListeners.fireMouseDown(this, -x, -y); // TODO
			// ;
			// Fix this
			// hacky
			// hack
		    } else {
			this.mouseListeners.fireMouseDown(this, x, y);
		    }
		}
		break;
	    case Event.ONMOUSEMOVE:
		if (this.mouseListeners != null) {
		    this.mouseListeners.fireMouseMove(this, x, y);
		}
		break;
	    case Event.ONMOUSEUP:
		if (this.mouseListeners != null) {
		    this.mouseListeners.fireMouseUp(this, x, y);
		}
		break;
	    }
	}
    }

    public void removeClickListener(final ClickListener listener) {
	if (this.clickListeners != null) {
	    this.clickListeners.remove(listener);
	}
    }

    public void removeMouseListener(final MouseListener listener) {
	if (this.mouseListeners != null) {
	    this.mouseListeners.remove(listener);
	}
    }
}