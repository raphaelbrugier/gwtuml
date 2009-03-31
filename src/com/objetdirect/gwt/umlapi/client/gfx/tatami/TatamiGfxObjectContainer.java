package com.objetdirect.gwt.umlapi.client.gfx.tatami;
import java.util.HashMap;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.tatami.client.gfx.GraphicObject;
/**
 * @author  florian
 */
public class TatamiGfxObjectContainer extends GfxObject {
	private static Map<GraphicObject, TatamiGfxObjectContainer> tatamiGfxObjectContainerMap = new HashMap<GraphicObject, TatamiGfxObjectContainer>();
	
	public static TatamiGfxObjectContainer getContainerOf(GraphicObject graphicObject) {
		return tatamiGfxObjectContainerMap.get(graphicObject);
	}
	/**
	 * @uml.property  name="graphicObject"
	 */
	private GraphicObject graphicObject;
	public TatamiGfxObjectContainer(GraphicObject graphicObject) {
		if (graphicObject == null)
			Log.error("Creating a Tcontainer of a null object");
		this.graphicObject = graphicObject;
		tatamiGfxObjectContainerMap.put(graphicObject, this);
		Log.trace("Added Tcontainer " + this);
	}
	/**
	 * @return
	 * @uml.property  name="graphicObject"
	 */
	public GraphicObject getGraphicObject() {
		return graphicObject;
	}
	@Override
	public String toString() {
		return UMLDrawerHelper.getShortName(this) + " containing "
				+ UMLDrawerHelper.getShortName(graphicObject);
	}
}
