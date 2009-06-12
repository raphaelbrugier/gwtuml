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

import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
class IncubatorGfxObjectContainer extends GfxObject {
	private static Map<IncubatorGfxObject, IncubatorGfxObjectContainer>	incubatorGfxObjectContainerMap	= new HashMap<IncubatorGfxObject, IncubatorGfxObjectContainer>();

	public static IncubatorGfxObjectContainer getContainerOf(final IncubatorGfxObject incubatorGfxObject) {
		return IncubatorGfxObjectContainer.incubatorGfxObjectContainerMap.get(incubatorGfxObject);
	}

	public static IncubatorGfxObjectContainer getPointedObject(final int x, final int y) {
		final long t = System.currentTimeMillis();
		for (final Map.Entry<IncubatorGfxObject, IncubatorGfxObjectContainer> pair : IncubatorGfxObjectContainer.incubatorGfxObjectContainerMap.entrySet()) {
			if (pair.getKey().isPointed(x, y)) {
				Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to find " + pair.getValue());
				return pair.getValue();
			}
		}
		Log.trace("No Icontainer found at " + x + ", " + y);
		return null;
	}

	/*
	 * public static void redrawAll(GWTCanvas canvas) { canvas.clear(); for (IncubatorGfxObjectContainer incubatorGfxObjectContainer :
	 * incubatorGfxObjectContainerSet) incubatorGfxObjectContainer.getGraphicObject().draw(canvas); }
	 */
	private final IncubatorGfxObject	incubatorGfxObject;

	public IncubatorGfxObjectContainer(final IncubatorGfxObject incubatorGfxObject) {
		super();
		if (incubatorGfxObject == null) {
			Log.error("Creating a Icontainer of a null object");
		}
		this.incubatorGfxObject = incubatorGfxObject;
		IncubatorGfxObjectContainer.incubatorGfxObjectContainerMap.put(incubatorGfxObject, this);
		Log.trace("Added Icontainer " + this);
	}

	public IncubatorGfxObject getGraphicObject() {
		return this.incubatorGfxObject;
	}

	@Override
	public String toString() {
		return GWTUMLDrawerHelper.getShortName(this) + " containing " + GWTUMLDrawerHelper.getShortName(this.incubatorGfxObject);
	}
}
