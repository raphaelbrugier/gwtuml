package com.objetdirect.gwt.umlapi.client.gfx.incubator;

import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.IncubatorGfxObject;

public class IncubatorGfxObjectContainer extends GfxObject {

	private static Set<IncubatorGfxObjectContainer> incubatorGfxObjectContainerSet = new HashSet<IncubatorGfxObjectContainer>();

	public static IncubatorGfxObjectContainer getPointedObject(int x, int y) {

		for (IncubatorGfxObjectContainer incubatorGfxObjectContainer : incubatorGfxObjectContainerSet) {

			if (incubatorGfxObjectContainer.getGraphicObject().isPointed(x, y))
				return incubatorGfxObjectContainer;
		}

		Log.warn("No Icontainer found at " + x + ", " + y);
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
		incubatorGfxObjectContainerSet.add(this);
		Log.info("Added Icontainer " + this);
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
