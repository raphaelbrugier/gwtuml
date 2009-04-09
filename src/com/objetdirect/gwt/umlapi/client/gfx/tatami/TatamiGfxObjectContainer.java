package com.objetdirect.gwt.umlapi.client.gfx.tatami;

import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.tatami.client.gfx.GraphicObject;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class TatamiGfxObjectContainer extends GfxObject {
    private static Map<GraphicObject, TatamiGfxObjectContainer> tatamiGfxObjectContainerMap = new HashMap<GraphicObject, TatamiGfxObjectContainer>();

    public static TatamiGfxObjectContainer getContainerOf(
	    final GraphicObject graphicObject) {
	return tatamiGfxObjectContainerMap.get(graphicObject);
    }

    private final GraphicObject graphicObject;

    public TatamiGfxObjectContainer(final GraphicObject graphicObject) {
	if (graphicObject == null) {
	    Log.error("Creating a Tcontainer of a null object");
	}
	this.graphicObject = graphicObject;
	tatamiGfxObjectContainerMap.put(graphicObject, this);
	Log.trace("Added Tcontainer " + this);
    }

    public GraphicObject getGraphicObject() {
	return graphicObject;
    }

    @Override
    public String toString() {
	return UMLDrawerHelper.getShortName(this) + " containing "
		+ UMLDrawerHelper.getShortName(graphicObject);
    }
}
