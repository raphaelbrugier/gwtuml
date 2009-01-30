package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public interface UMLElement {

	void adjusted();

	void edit(GfxObject gfxObject, int x, int y);

	UMLCanvas getCanvas();

	List<GfxObject> getComponents();

	GfxObject getGfxObject();

	GfxObject getOutline();

	LinkedHashMap<String, Command> getRightMenu();

	int getX();

	int getY();

	boolean isDraggable();

	void select();

	void setCanvas(UMLCanvas canvas);

	void setLocation(int x, int y);

	void unselect();

}
