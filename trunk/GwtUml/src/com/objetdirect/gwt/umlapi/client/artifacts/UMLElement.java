package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.List;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

public interface UMLElement {

	GfxObject getGfxObject();
	List<GfxObject> getComponents();
	void select();
	void unselect();
	boolean isDraggable();
	GfxObject getOutline();
	void setLocation(int x, int y);
	int getX();
	int getY();
	UMLCanvas getCanvas();
	void setCanvas(UMLCanvas canvas);
	void adjusted();
}
