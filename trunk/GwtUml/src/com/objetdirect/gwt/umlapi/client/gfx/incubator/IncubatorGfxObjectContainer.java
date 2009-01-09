package com.objetdirect.gwt.umlapi.client.gfx.incubator;

import java.util.HashSet;
import java.util.Set;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.IncubatorGfxObject;

public class IncubatorGfxObjectContainer extends GfxObject{

	private static Set<IncubatorGfxObjectContainer> incubatorGfxObjectContainerSet = new HashSet<IncubatorGfxObjectContainer>();
	public static IncubatorGfxObjectContainer getPointedObject(int x, int y)
	{
		
		for (IncubatorGfxObjectContainer incubatorGfxObjectContainer : incubatorGfxObjectContainerSet) {
			
			if (incubatorGfxObjectContainer.getGraphicObject().isPointed(x, y))
				return incubatorGfxObjectContainer;
		}
		return null;
		
	}
	/*public static void redrawAll(GWTCanvas canvas) {
		canvas.clear();
		for (IncubatorGfxObjectContainer incubatorGfxObjectContainer : incubatorGfxObjectContainerSet)
			incubatorGfxObjectContainer.getGraphicObject().draw(canvas);
	}*/
		
	private IncubatorGfxObject incubatorGfxObject;
	
	
	public IncubatorGfxObjectContainer(IncubatorGfxObject incubatorGfxObject) {
		this.incubatorGfxObject = incubatorGfxObject;
		incubatorGfxObjectContainerSet.add(this);
	}

	public IncubatorGfxObject getGraphicObject() {
		return incubatorGfxObject;
	}


}

