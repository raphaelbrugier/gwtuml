package com.objetdirect.gwt.umlapi.client.gfx.canvas.objects;
import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
public class Path extends IncubatorGfxObject {
	private class Point {
		private int x;
		private int y;
		private Path path;
		public Point(int x, int y, Path path) {
			this.x = x;
			this.y = y;
			this.path = path;
		}
		/**
		 * @return the x
		 */
		public int getX() {
			return x + path.getX();
		}
		/**
		 * @return the y
		 */
		public int getY() {
			return y + path.getY();
		}

	}
	private ArrayList<Point> pathPoints = new ArrayList<Point>();
	public Path() {
		this.x = 0;
		this.y = 0;
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
		for (Point point : pathPoints) {
			canvas.lineTo(point.getX(), point.getY());
		}
		canvas.closePath();
		canvas.fill();
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
			minx = minx > point.getX() ? point.getX() : minx;
			miny = miny > point.getY() ? point.getY() : miny;
			maxx = maxx < point.getX() ? point.getX() : maxx;
			maxy = maxy < point.getY() ? point.getY() : maxy;
		}
		return (x > minx) && (x < maxx) && (y > miny) && (y < maxy);
	}
	public void lineTo(int x, int y) {
		pathPoints.add(new Point(x, y, this));
	}
	public void moveTo(int x, int y) {
		pathPoints.add(new Point(x, y, this));
	}
}
