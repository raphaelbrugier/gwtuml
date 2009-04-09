package com.objetdirect.gwt.umlapi.client.gfx.canvas.objects;

import com.allen_sauer.gwt.log.client.Log;

public class Line extends IncubatorGfxObject {

    private final int h;
    private final int w;

    public Line(final int x1, final int y1, final int x2, final int y2) {
	x = x1;
	y = y1;
	w = x2 - x1;
	h = y2 - y1;
    }

    @Override
    public void draw() {
	if (!isVisible) {
	    Log.trace(this + " is not visible");
	    return;
	}
	if (canvas == null) {
	    Log.fatal("canvas is null for " + this);
	}

	Log.trace("Drawing " + this);
	canvas.saveContext();
	if (fillColor != null) {
	    canvas.setFillStyle(fillColor);
	}
	if (strokeColor != null) {
	    canvas.setStrokeStyle(strokeColor);
	}
	if (strokeWidth != 0) {
	    canvas.setLineWidth(strokeWidth);
	}
	canvas.beginPath();
	canvas.moveTo(getX(), getY());
	canvas.lineTo(getX() + w, getY() + h);
	canvas.closePath();
	canvas.stroke();
	canvas.restoreContext();
    }

    @Override
    public int getHeight() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getWidth() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public boolean isPointed(final int xp, final int yp) {
	return false;
    }
}
