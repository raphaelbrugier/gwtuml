package com.objetdirect.gwt.umlapi.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLElement;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.GraphicObjectListener;

public class UMLCanvas extends AbsolutePanel {

	public UMLCanvas() {
		canvas = new GraphicCanvas();
		add(canvas, 0, 0);
		canvas.setSize("100%", "100%");
		canvas.addGraphicObjectListener(goListener);
	}
	
	public void add(UMLElement element) {
		canvas.add(element.getGraphicObject(), 0, 0);
		objects.put(element.getGraphicObject(), element);
		List<GraphicObject> elems = element.getComponents();
		for (int i = 0; i<elems.size(); i++) {
			objects.put(elems.get(i), element);
		}
		element.setCanvas(this);
	}
	
	public void remove(UMLElement element) {
		canvas.remove(element.getGraphicObject());
		objects.remove(element.getGraphicObject());
		List<GraphicObject> elems = element.getComponents();
		for (int i = 0; i<elems.size(); i++) {
			objects.remove(elems.get(i));
		}
		element.setCanvas(null);
		if (element==selected)
			selected = null;
	}

	public void register(GraphicObject go, UMLElement element) {
		objects.put(go, element);
	}

	public void unregister(GraphicObject go) {
		objects.remove(go);
	}

	public void addUMLElementListener(UMLElementListener lst) {
		listeners.add(lst);
	}
	
	public void removeUMLElementListener(UMLElementListener lst) {
		listeners.remove(lst);
	}

	void select(GraphicObject graphicObject, int x, int y) {
		UMLElement newSelected = getUMLElement(graphicObject);
		if (newSelected != selected) {
			if (selected!=null)
				selected.unselect();
			selected = newSelected;
			if (selected!=null)
				selected.select();
		}
	}
	
	UMLElement getUMLElement(GraphicObject obj) {
		return objects.get(obj);
	}
	
	void take(int x, int y) {
		if (selected!=null && selected.isDraggable()) {
			dx = x-selected.getX();
			dy = y-selected.getY();
		}		
	}
	
	void drag(int x, int y) {
		if (selected!=null && selected.isDraggable()) {
			if (outline==null) {
				outline = selected.getOutline();
				canvas.add(outline, 0, 0);
			}
			outline.translate(x-dx-(int)outline.getX(), y-dy-(int)outline.getY());
		}
	}
	
	void drop(int x, int y) {
		if (selected!=null && selected.isDraggable()) {
			if (outline!=null) { 
				canvas.remove(outline);
				outline=null;
			}
			if (x-dx!=selected.getX() || y-dy!=selected.getY()) {
				System.out.println("dropped");
				selected.setLocation(x-dx, y-dy);
				selected.adjusted();
			}
		}
		
	}
	
	void editItem(GraphicObject o, int x, int y) {
		UMLElement elem = getUMLElement(o);
		if (elem!=null) {
			Iterator<UMLElementListener> it = listeners.iterator();
			while (it.hasNext()) {
				UMLElementListener lst = it.next();
				lst.itemEdited(elem, o);
			}
		}
	}
	
	GraphicObjectListener goListener = new GraphicObjectListener() 
	{
		public void mouseClicked(GraphicObject graphicObject, Event e) {
		}

		public void mouseDblClicked(GraphicObject graphicObject, Event e) {
			editItem(graphicObject, DOM.eventGetClientX(e), DOM.eventGetClientY(e));
		}
		
		public void mouseMoved(GraphicObject graphicObject, Event e) {
			if (dragOn)
				drag(DOM.eventGetClientX(e), DOM.eventGetClientY(e));
		}

		public void mousePressed(GraphicObject graphicObject, Event e) {
			if (outline==null) {
				select(graphicObject, DOM.eventGetClientX(e), DOM.eventGetClientY(e));
				if (selected!=null && selected.isDraggable()) {
					take(DOM.eventGetClientX(e), DOM.eventGetClientY(e));
					dragOn = true;
				}
			}
		}

		public void mouseReleased(GraphicObject graphicObject, Event e) {
			if (dragOn)
				drop(DOM.eventGetClientX(e), DOM.eventGetClientY(e));
			dragOn = false;
		}		
	};
	
	GraphicObject outline=null;
	int dx, dy;
	UMLElement selected = null;
	boolean dragOn = false;
	GraphicCanvas canvas;
	Map<GraphicObject, UMLElement> objects = new HashMap<GraphicObject, UMLElement>();
	Set<UMLElementListener> listeners = new HashSet<UMLElementListener>();
}
