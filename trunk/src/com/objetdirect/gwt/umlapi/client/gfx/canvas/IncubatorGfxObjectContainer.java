package com.objetdirect.gwt.umlapi.client.gfx.canvas;
import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.IncubatorGfxObject;
/**
 * @author  florian
 */
public class IncubatorGfxObjectContainer extends GfxObject {
	private static Map<IncubatorGfxObject, IncubatorGfxObjectContainer> incubatorGfxObjectContainerMap = new HashMap<IncubatorGfxObject, IncubatorGfxObjectContainer>();
	public static IncubatorGfxObjectContainer getContainerOf(IncubatorGfxObject incubatorGfxObject) {
		return incubatorGfxObjectContainerMap.get(incubatorGfxObject);
	}
	
	public static IncubatorGfxObjectContainer getPointedObject(int x, int y) {
		long t = System.currentTimeMillis();
		for (Map.Entry<IncubatorGfxObject, IncubatorGfxObjectContainer> pair : incubatorGfxObjectContainerMap.entrySet()) {
			if (pair.getKey().isPointed(x, y)) {
				Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to find " + pair.getValue());
				return pair.getValue();
			}
		}
		Log.trace("No Icontainer found at " + x + ", " + y);
		return null;
	}
	/*
	 * public static void redrawAll(GWTCanvas canvas) { canvas.clear(); for
	 * (IncubatorGfxObjectContainer incubatorGfxObjectContainer :
	 * incubatorGfxObjectContainerSet)
	 * incubatorGfxObjectContainer.getGraphicObject().draw(canvas); }
	 */
private IncubatorGfxObject incubatorGfxObject;
	public IncubatorGfxObjectContainer(IncubatorGfxObject incubatorGfxObject) {
		if (incubatorGfxObject == null)
			Log.error("Creating a Icontainer of a null object");
		this.incubatorGfxObject = incubatorGfxObject;
		incubatorGfxObjectContainerMap.put(incubatorGfxObject, this);
		Log.trace("Added Icontainer " + this);
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
