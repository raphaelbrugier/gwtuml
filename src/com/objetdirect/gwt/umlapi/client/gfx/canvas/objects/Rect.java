package com.objetdirect.gwt.umlapi.client.gfx.canvas.objects;
import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.CanvasBridge;
public class Rect extends IncubatorGfxObject {
	private int h = 0;
	private int w = 0;
	public Rect(int w, int h) {
		this.w = w;
		this.h = h;
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
		canvas.fillRect(getX(), getY(), w, h);
		canvas.strokeRect(getX(), getY(), w, h);
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
		return (x > getX()) && (x < getX() + w) && (y > getY())
				&& (y < getY() + h);
	}
}
