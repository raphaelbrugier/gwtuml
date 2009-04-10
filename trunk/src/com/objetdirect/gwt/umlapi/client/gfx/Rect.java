package com.objetdirect.gwt.umlapi.client.gfx;

import com.allen_sauer.gwt.log.client.Log;

class Rect extends IncubatorGfxObject {
    private int h = 0;
    private int w = 0;

    public Rect(final int w, final int h) {
	this.w = w;
	this.h = h;
    }

    @Override
    public void draw() {
	if (!this.isVisible) {
	    Log.trace(this + " is not visible");
	    return;
	}
	if (this.canvas == null) {
	    Log.fatal("canvas is null for " + this);
	}

	Log.trace("Drawing " + this);
	this.canvas.saveContext();
	if (this.fillColor != null) {
	    this.canvas.setFillStyle(this.fillColor);
	}
	if (this.strokeColor != null) {
	    this.canvas.setStrokeStyle(this.strokeColor);
	}
	if (this.strokeWidth != 0) {
	    this.canvas.setLineWidth(this.strokeWidth);
	}
	this.canvas.fillRect(getX(), getY(), this.w, this.h);
	this.canvas.strokeRect(getX(), getY(), this.w, this.h);
	this.canvas.restoreContext();
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
	return xp > getX() && xp < getX() + this.w && yp > getY() && yp < getY() + this.h;
    }
}
