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

import com.allen_sauer.gwt.log.client.Log;

class Line extends IncubatorGfxObject {

	private final int h;
	private final int w;

	public Line(final int x1, final int y1, final int x2, final int y2) {
		super();
		x = x1;
		y = y1;
		w = x2 - x1;
		h = y2 - y1;
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
		canvas.moveTo(this.getX(), this.getY());
		canvas.lineTo(this.getX() + w, this.getY() + h);
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
	public boolean isPointed(final int xp, final int yp) {
		return false;
	}
}
