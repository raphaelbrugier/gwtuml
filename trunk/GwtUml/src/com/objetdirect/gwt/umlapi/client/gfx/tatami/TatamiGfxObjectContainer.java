package com.objetdirect.gwt.umlapi.client.gfx.tatami;
import java.util.HashSet;
import java.util.Set;
import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.tatami.client.gfx.GraphicObject;
/**
 * @author  florian
 */
public class TatamiGfxObjectContainer extends GfxObject {
	private static Set<TatamiGfxObjectContainer> tatamiGfxObjectContainerSet = new HashSet<TatamiGfxObjectContainer>();
	public static TatamiGfxObjectContainer getContainerOf(
			GraphicObject graphicObject) {
		if (graphicObject == null) {
			Log.debug("Looking for a Tcontainer of a null object");
			return null; // Avoid a complete search if null
		}
		for (TatamiGfxObjectContainer tatamiGfxObjectContainer : tatamiGfxObjectContainerSet) {
			if (tatamiGfxObjectContainer.getGraphicObject().equals(
					graphicObject))
				return tatamiGfxObjectContainer;
		}
		Log.warn("No Tcontainer found for "
				+ UMLDrawerHelper.getShortName(graphicObject));
		return null;
	}
	/**
	 * @uml.property  name="graphicObject"
	 */
	private GraphicObject graphicObject;
	public TatamiGfxObjectContainer(GraphicObject graphicObject) {
		if (graphicObject == null)
			Log.error("Creating a Tcontainer of a null object");
		this.graphicObject = graphicObject;
		tatamiGfxObjectContainerSet.add(this);
		Log.info("Added Tcontainer " + this);
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
