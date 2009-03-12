package com.objetdirect.gwt.umlapi.client.gfx.incubator.objects;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class Line extends IncubatorGfxObject {

	private int w, h;

	public Line(int x1, int y1, int x2, int y2) {
		this.x = x1;
		this.y = y1;
		this.w = x2 - x1;
		this.h = y2 - y1;
	}

	@Override
	public void draw(GWTCanvas canvas) {
		if (!isVisible)
			return;
		Log.trace("{Incubator} Drawing " + this);
		canvas.saveContext();
		if (fillColor != null)
			canvas.setFillStyle(fillColor);
		if (strokeColor != null)
			canvas.setStrokeStyle(strokeColor);
		if (strokeWidth != 0)
			canvas.setLineWidth(strokeWidth);
		canvas.beginPath();
		canvas.moveTo(x, y);
		canvas.lineTo(x + w, y + h);
		canvas.closePath();
		canvas.stroke();
		canvas.restoreContext();
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isPointed(int x, int y) {
		return false;
	}
}
