package com.objetdirect.gwt.umlapi.client.gfx.incubator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.IncubatorGfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.tatami.TatamiGfxObjectContainer;
import com.objetdirect.tatami.client.gfx.GraphicObject;
/**
 * @author  florian
 */
public class IncubatorGfxObjectContainer extends GfxObject {
	private static Map<IncubatorGfxObject, IncubatorGfxObjectContainer> incubatorGfxObjectContainerMap = new HashMap<IncubatorGfxObject, IncubatorGfxObjectContainer>();
	public static IncubatorGfxObjectContainer getContainerOf(IncubatorGfxObject incubatorGfxObject) {
		return incubatorGfxObjectContainerMap.get(incubatorGfxObject);
	}
	
	public static IncubatorGfxObjectContainer getPointedObject(int x, int y) {
		for (Map.Entry<IncubatorGfxObject, IncubatorGfxObjectContainer> pair : incubatorGfxObjectContainerMap.entrySet()) {
			if (pair.getKey().isPointed(x, y))
				return pair.getValue();
		}
		Log.debug("No Icontainer found at " + x + ", " + y);
		return null;
	}
	/*
	 * public static void redrawAll(GWTCanvas canvas) { canvas.clear(); for
	 * (IncubatorGfxObjectContainer incubatorGfxObjectContainer :
	 * incubatorGfxObjectContainerSet)
	 * incubatorGfxObjectContainer.getGraphicObject().draw(canvas); }
	 */
	/**
	 * @uml.property  name="incubatorGfxObject"
	 * @uml.associationEnd  
	 */
	private IncubatorGfxObject incubatorGfxObject;
	public IncubatorGfxObjectContainer(IncubatorGfxObject incubatorGfxObject) {
		if (incubatorGfxObject == null)
			Log.error("Creating a Icontainer of a null object");
		this.incubatorGfxObject = incubatorGfxObject;
		incubatorGfxObjectContainerMap.put(incubatorGfxObject, this);
		Log.debug("Added Icontainer " + this);
	}
	public IncubatorGfxObject getGraphicObject() {
		return incubatorGfxObject;
	}
	@Override
	public String toString() {
		return UMLDrawerHelper.getShortName(this) + " containing "
				+ UMLDrawerHelper.getShortName(incubatorGfxObject);
	}
}
