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

class Text extends IncubatorGfxObject {
    private final String text;

    public Text(final String text) {
	super();
	this.text = text;
    }

    @Override
    public void draw() {
	// if (!isVisible)
	return;
	/*
	 * Log.trace("{Incubator} Drawing " + this); canvas.saveContext(); if
	 * (fillColor != null) canvas.setFillStyle(fillColor); if (strokeColor
	 * != null) canvas.setStrokeStyle(strokeColor); if (strokeWidth != 0)
	 * canvas.setLineWidth(strokeWidth); for (int i = 0; i < text.length();
	 * i++) { canvas.strokeRect(getX() + 10 * i, getY() - 8, 8, 10); }
	 * canvas.restoreContext();
	 */
    }

    @Override
    public int getHeight() {
	return 8;
    }

    @Override
    public int getWidth() {
	return this.text.length() * 10;
    }

    @Override
    public boolean isPointed(final int xp, final int yp) {
	return false;
    }
}
