package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.List;

import com.objetdirect.gwt.umlapi.client.UMLCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;

public interface UMLElement {

	GraphicObject getGraphicObject();
	List<GraphicObject> getComponents();
	void select();
	void unselect();
	boolean isDraggable();
	GraphicObject getOutline();
	void setLocation(int x, int y);
	int getX();
	int getY();
	UMLCanvas getCanvas();
	void setCanvas(UMLCanvas canvas);
	void adjusted();
}
