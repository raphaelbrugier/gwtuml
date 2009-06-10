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

import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
class VirtualGroup extends IncubatorGfxObject {
	private final Set<IncubatorGfxObject>	incubatorGfxObjectSet	= new HashSet<IncubatorGfxObject>();

	/**
	 * @param incubatorGfxObject
	 */
	public void add(final IncubatorGfxObject incubatorGfxObject) {
		this.incubatorGfxObjectSet.add(incubatorGfxObject);
		incubatorGfxObject.setParentGroup(this);
		if (this.isVisible) {
			incubatorGfxObject.addOnCanvasAt(this.canvas, 0, 0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.IncubatorGfxObject #addOnCanvasAt(com.objetdirect.gwt.umlapi.client.gfx.canvas.CanvasBridge,
	 * int, int)
	 */
	@Override
	public void addOnCanvasAt(final CanvasBridge canvasBridge, final int dx, final int dy) {
		super.addOnCanvasAt(canvasBridge, dx, dy);
		for (final IncubatorGfxObject incubatorGfxObject : this.incubatorGfxObjectSet) {
			incubatorGfxObject.addOnCanvasAt(canvasBridge, dx, dy);
		}
	}

	public void clear() {
		this.incubatorGfxObjectSet.clear();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.IncubatorGfxObject #draw()
	 */
	@Override
	public void draw() {
		if (!this.isVisible) {
			Log.trace(this + " is not visible");
			return;
		}
		if (this.canvas == null) {
			Log.fatal("canvas is null for " + this);
		}

		Log.trace("Starting drawing " + this);
		for (final IncubatorGfxObject incubatorGfxObject : this.incubatorGfxObjectSet) {
			incubatorGfxObject.draw();
		}
		Log.trace("Ending drawing " + this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.IncubatorGfxObject #getHeight()
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.IncubatorGfxObject #getWidth()
	 */
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.IncubatorGfxObject #isPointed(int, int)
	 */
	@Override
	public boolean isPointed(final int xp, final int yp) {
		return false;
	}

	/**
	 * @param incubatorGfxObject
	 */
	public void remove(final IncubatorGfxObject incubatorGfxObject) {
		this.incubatorGfxObjectSet.remove(incubatorGfxObject);
	}
}
