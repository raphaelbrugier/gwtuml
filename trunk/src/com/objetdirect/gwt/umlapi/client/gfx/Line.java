/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.gfx;

import com.allen_sauer.gwt.log.client.Log;

class Line extends IncubatorGfxObject {

    private final int h;
    private final int w;

    public Line(final int x1, final int y1, final int x2, final int y2) {
	super();
	this.x = x1;
	this.y = y1;
	this.w = x2 - x1;
	this.h = y2 - y1;
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
	this.canvas.moveTo(getX(), getY());
	this.canvas.lineTo(getX() + this.w, getY() + this.h);
	this.canvas.closePath();
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
	return false;
    }
}
