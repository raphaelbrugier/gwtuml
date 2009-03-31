package com.objetdirect.gwt.umlapi.client.gfx.canvas.objects;
import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.CanvasBridge;
public class Line extends IncubatorGfxObject {
	
	private int w;
	private int h;
	public Line(int x1, int y1, int x2, int y2) {
		this.x = x1;
		this.y = y1;
		this.w = x2 - x1;
		this.h = y2 - y1;
	}
	@Override
	public void draw() {
		if (!isVisible) {
			Log.trace(this + " is not visible");
			return;
		}
		if (canvas == null) Log.fatal("canvas is null for " + this);
		
		Log.trace("Drawing " + this);
		canvas.saveContext();
		if (fillColor != null)
			canvas.setFillStyle(fillColor);
		if (strokeColor != null)
			canvas.setStrokeStyle(strokeColor);
		if (strokeWidth != 0)
			canvas.setLineWidth(strokeWidth);
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
	public boolean isPointed(int x, int y) {
		return false;
	}
}
