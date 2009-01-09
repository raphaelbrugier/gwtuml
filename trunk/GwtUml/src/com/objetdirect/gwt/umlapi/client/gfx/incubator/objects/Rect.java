package com.objetdirect.gwt.umlapi.client.gfx.incubator.objects;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class Rect extends IncubatorGfxObject {
	private int w = 0;
	private int h = 0;

	public Rect(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void draw(GWTCanvas canvas) {
		if(!isVisible) return;
		Log.trace("{Incubator} Drawing " + this);
		canvas.saveContext();
		if(fillColor != null) canvas.setFillStyle(fillColor);
		if(strokeColor != null) canvas.setStrokeStyle(strokeColor);
		if(strokeWidth != 0) canvas.setLineWidth(strokeWidth);
		canvas.strokeRect(x, y, w, h);
		canvas.restoreContext();
	}


	public boolean isPointed(int x, int y) {	
		return (x > this.x) && (x < this.x + w) && (y > this.y) && (y < this.y + h);
	}

	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
