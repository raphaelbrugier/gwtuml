package com.objetdirect.gwt.umlapi.client.gfx;

import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
class IncubatorGfxObjectContainer extends GfxObject {
    private static Map<IncubatorGfxObject, IncubatorGfxObjectContainer> incubatorGfxObjectContainerMap = new HashMap<IncubatorGfxObject, IncubatorGfxObjectContainer>();

    public static IncubatorGfxObjectContainer getContainerOf(
	    final IncubatorGfxObject incubatorGfxObject) {
	return incubatorGfxObjectContainerMap.get(incubatorGfxObject);
    }

    public static IncubatorGfxObjectContainer getPointedObject(final int x,
	    final int y) {
	final long t = System.currentTimeMillis();
	for (final Map.Entry<IncubatorGfxObject, IncubatorGfxObjectContainer> pair : incubatorGfxObjectContainerMap
		.entrySet()) {
	    if (pair.getKey().isPointed(x, y)) {
		Log.debug("([" + (System.currentTimeMillis() - t)
			+ "ms]) to find " + pair.getValue());
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
    private final IncubatorGfxObject incubatorGfxObject;

    public IncubatorGfxObjectContainer(
	    final IncubatorGfxObject incubatorGfxObject) {
	super();
	if (incubatorGfxObject == null) {
	    Log.error("Creating a Icontainer of a null object");
	}
	this.incubatorGfxObject = incubatorGfxObject;
	incubatorGfxObjectContainerMap.put(incubatorGfxObject, this);
	Log.trace("Added Icontainer " + this);
    }

    public IncubatorGfxObject getGraphicObject() {
	return this.incubatorGfxObject;
    }

    @Override
    public String toString() {
	return UMLDrawerHelper.getShortName(this) + " containing "
		+ UMLDrawerHelper.getShortName(this.incubatorGfxObject);
    }
}