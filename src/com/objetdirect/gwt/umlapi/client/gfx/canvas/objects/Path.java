package com.objetdirect.gwt.umlapi.client.gfx.canvas.objects;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;

public class Path extends IncubatorGfxObject {
    private class Point {
	private final Path path;
	private final int xp;
	private final int yp;

	public Point(final int x, final int y, final Path path) {
	    xp = x;
	    yp = y;
	    this.path = path;
	}

	/**
	 * @return the x
	 */
	public int getX() {
	    return xp + path.getX();
	}

	/**
	 * @return the y
	 */
	public int getY() {
	    return yp + path.getY();
	}

    }

    private final ArrayList<Point> pathPoints = new ArrayList<Point>();

    public Path() {
	x = 0;
	y = 0;
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
	for (final Point point : pathPoints) {
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
    public boolean isPointed(final int xp, final int yp) {
	int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;
	for (final Point point : pathPoints) {
	    minx = minx > point.getX() ? point.getX() : minx;
	    miny = miny > point.getY() ? point.getY() : miny;
	    maxx = maxx < point.getX() ? point.getX() : maxx;
	    maxy = maxy < point.getY() ? point.getY() : maxy;
	}
	return xp > minx && xp < maxx && yp > miny && yp < maxy;
    }

    public void lineTo(final int xp, final int yp) {
	pathPoints.add(new Point(xp, yp, this));
    }

    public void moveTo(final int xp, final int yp) {
	pathPoints.add(new Point(xp, yp, this));
    }
}
