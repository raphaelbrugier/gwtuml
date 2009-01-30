package com.objetdirect.gwt.umlapi.client.gfx.incubator.objects;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class Path extends IncubatorGfxObject {

	private class Point {
		double x;
		double y;

		public Point(double x, double y) {
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
	public void translate(int dx, int dy) {
		super.translate(dx, dy);
		for (Point point : pathPoints) {
			point.x += dx;
			point.y += dy;
		}
	}

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
		for (Point point : pathPoints) {
			canvas.lineTo(point.x, point.y);
		}
		canvas.closePath();
		canvas.stroke();
		canvas.restoreContext();
	}

	public void lineTo(double x, double y) {
		pathPoints.add(new Point(x, y));
	}

	public void moveTo(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;

	}

	public boolean isPointed(int x, int y) {
		double minx = Double.MAX_VALUE, miny = Double.MAX_VALUE, maxx = Double.MIN_VALUE, maxy = Double.MIN_VALUE;
		for (Point point : pathPoints) {
			minx = minx > point.x ? point.x : minx;
			miny = miny > point.y ? point.y : miny;
			maxx = maxx < point.x ? point.x : maxx;
			maxy = maxy < point.y ? point.y : maxy;
		}

		return (x > minx) && (x < maxx) && (y > miny) && (y < maxy);
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
