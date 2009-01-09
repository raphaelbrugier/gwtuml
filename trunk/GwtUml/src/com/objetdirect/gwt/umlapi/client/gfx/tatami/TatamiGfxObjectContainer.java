package com.objetdirect.gwt.umlapi.client.gfx.tatami;

import java.util.HashSet;
import java.util.Set;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.tatami.client.gfx.GraphicObject;

public class TatamiGfxObjectContainer extends GfxObject{

	private static Set<TatamiGfxObjectContainer> tatamiGfxObjectContainerSet = new HashSet<TatamiGfxObjectContainer>();
	public static TatamiGfxObjectContainer getContainerOf(GraphicObject graphicObject)
	{
		if(graphicObject == null) return null; // Avoid a complete search if null 
		for (TatamiGfxObjectContainer tatamiGfxObjectContainer : tatamiGfxObjectContainerSet) {
			if (tatamiGfxObjectContainer.getGraphicObject().equals(graphicObject))
				return tatamiGfxObjectContainer;
		}
		return null;
		
	}
	
	private GraphicObject graphicObject;
	
	
	public TatamiGfxObjectContainer(GraphicObject graphicObject) {
		this.graphicObject = graphicObject;
		tatamiGfxObjectContainerSet.add(this);
	}

	public GraphicObject getGraphicObject() {
		return graphicObject;
	}


}
