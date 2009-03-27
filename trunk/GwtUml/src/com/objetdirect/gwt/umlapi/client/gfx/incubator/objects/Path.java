package com.objetdirect.gwt.umlapi.client.gfx.incubator.objects;
import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
public class Path extends IncubatorGfxObject {
	private class Point {
		int x;
		int y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	private ArrayList<Point> pathPoints = new ArrayList<Point>();
	public Path() {
		this.x = 0;
		this.y = 0;
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
		canvas.moveTo(getX(), getY());
		for (Point point : pathPoints) {
			canvas.lineTo(point.x, point.y);
		}
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
		int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;
		for (Point point : pathPoints) {
			minx = minx > point.x ? point.x : minx;
			miny = miny > point.y ? point.y : miny;
			maxx = maxx < point.x ? point.x : maxx;
			maxy = maxy < point.y ? point.y : maxy;
		}
		return (x > minx) && (x < maxx) && (y > miny) && (y < maxy);
	}
	public void lineTo(int x, int y) {
		pathPoints.add(new Point(x, y));
	}
	public void moveTo(int x, int y) {
		this.x =  x;
		this.y =  y;
	}
}
