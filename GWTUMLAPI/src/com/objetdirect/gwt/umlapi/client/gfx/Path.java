/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.gfx;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;

class Path extends IncubatorGfxObject {
	private class Point {
		private final Path	path;
		private final int	xp;
		private final int	yp;

		public Point(final int x, final int y, final Path path) {
			super();
			this.xp = x;
			this.yp = y;
			this.path = path;
		}

		/**
		 * @return the x
		 */
		public int getX() {
			return this.xp + this.path.getX();
		}

		/**
		 * @return the y
		 */
		public int getY() {
			return this.yp + this.path.getY();
		}

	}

	private final ArrayList<Point>	pathPoints	= new ArrayList<Point>();

	public Path() {
		super();
		this.x = 0;
		this.y = 0;
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
		this.canvas.beginPath();
		for (final Point point : this.pathPoints) {
			this.canvas.lineTo(point.getX(), point.getY());
		}
		this.canvas.closePath();
		this.canvas.fill();
		this.canvas.stroke();
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
		int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;
		for (final Point point : this.pathPoints) {
			minx = minx > point.getX() ? point.getX() : minx;
			miny = miny > point.getY() ? point.getY() : miny;
			maxx = maxx < point.getX() ? point.getX() : maxx;
			maxy = maxy < point.getY() ? point.getY() : maxy;
		}
		return (xp > minx) && (xp < maxx) && (yp > miny) && (yp < maxy);
	}

	public void lineTo(final int xp, final int yp) {
		this.pathPoints.add(new Point(xp, yp, this));
	}

	public void moveTo(final int xp, final int yp) {
		this.pathPoints.add(new Point(xp, yp, this));
	}
}
