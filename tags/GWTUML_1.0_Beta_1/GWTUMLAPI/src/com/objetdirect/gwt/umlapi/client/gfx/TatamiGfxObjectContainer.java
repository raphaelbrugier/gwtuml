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
import com.objetdirect.tatami.client.gfx.GraphicObject;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
class TatamiGfxObjectContainer extends GfxObject {
	private static Map<GraphicObject, TatamiGfxObjectContainer>	tatamiGfxObjectContainerMap	= new HashMap<GraphicObject, TatamiGfxObjectContainer>();

	public static TatamiGfxObjectContainer getContainerOf(final GraphicObject graphicObject) {
		return TatamiGfxObjectContainer.tatamiGfxObjectContainerMap.get(graphicObject);
	}

	private final GraphicObject	graphicObject;

	public TatamiGfxObjectContainer(final GraphicObject graphicObject) {
		super();
		if (graphicObject == null) {
			Log.error("Creating a Tcontainer of a null object");
		}
		this.graphicObject = graphicObject;
		TatamiGfxObjectContainer.tatamiGfxObjectContainerMap.put(graphicObject, this);
		Log.trace("Added Tcontainer " + this);
	}

	public GraphicObject getGraphicObject() {
		return this.graphicObject;
	}

	@Override
	public String toString() {
		return GWTUMLDrawerHelper.getShortName(this) + " containing " + GWTUMLDrawerHelper.getShortName(this.graphicObject);
	}
}
