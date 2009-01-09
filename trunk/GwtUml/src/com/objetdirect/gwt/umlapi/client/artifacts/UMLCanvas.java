package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.ThemeManager;
import com.objetdirect.gwt.umlapi.client.engine.UMLElementListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;


public class UMLCanvas extends AbsolutePanel {
	
	private final static int FAR_AWAY = -1000;
	
	public UMLCanvas() {
		canvas = GfxManager.getInstance().makeCanvas();
		this.setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH, GfxPlatform.DEFAULT_CANVAS_HEIGHT);
		GfxManager.getInstance().addObjectListenerToCanvas(canvas, gfxObjectListener);
		add(canvas, 0, 0);
	}
	
	public UMLCanvas(int width, int height) {
		canvas = GfxManager.getInstance().makeCanvas(width, height, ThemeManager.getBackgroundColor());
		this.setPixelSize(width, height);
		GfxManager.getInstance().addObjectListenerToCanvas(canvas, gfxObjectListener);
		add(canvas, 0, 0);
	}
	
	public void addNewClass() {		
		ClassArtifact newClass = new ClassArtifact("Class");
		newClass.setLocation(FAR_AWAY, FAR_AWAY);
		add(newClass);	
		if (selected!=null)
			selected.unselect();
		selected = newClass;
		selected.select();		
		take(FAR_AWAY+ClassArtifact.DEFAULT_WIDTH/2, FAR_AWAY);
		dragOn = true;
	}
	public void addNewNote() {
		NoteArtifact newNote = new NoteArtifact();
		newNote.setLocation(200, 200);
		add(newNote);		
		
	}
	public void addNewLink() {
		isInLinkingMode = true;
	}
	
	public void add(UMLElement element) {
		GfxManager.getInstance().addToCanvas(canvas, element.getGfxObject(), 0, 0);
		objects.put(element.getGfxObject(), element);
		List<GfxObject> elems = element.getComponents();
		for (int i = 0; i<elems.size(); i++) {
			objects.put(elems.get(i), element);
		}
		element.setCanvas(this);
	}
	
	public void remove(UMLElement element) {
		GfxManager.getInstance().removeFromCanvas(canvas, element.getGfxObject());
		objects.remove(element.getGfxObject());
		List<GfxObject> elems = element.getComponents();
		for (int i = 0; i<elems.size(); i++) {
			objects.remove(elems.get(i));
		}
		element.setCanvas(null);
		if (element==selected)
			selected = null;
	}

	public void register(GfxObject go, UMLElement element) {
		objects.put(go, element);
	}

	public void unregister(GfxObject go) {
		objects.remove(go);
	}

	public void addUMLElementListener(UMLElementListener lst) {
		listeners.add(lst);
	}
	
	public void removeUMLElementListener(UMLElementListener lst) {
		listeners.remove(lst);
	}

	void select(GfxObject gfxObject) {
		UMLElement newSelected = getUMLElement(gfxObject);
		
		if (newSelected != selected) {
			if(newSelected == null) 
				isInLinkingMode = false;
			
			if (selected!=null) {
				if(isInLinkingMode)
					add(new ClassDependencyArtifact.Simple((ClassArtifact) selected,(ClassArtifact) newSelected));
					selected.unselect();
				}
			
			selected = newSelected;
			
			if (selected!=null)
				selected.select();
		}
	}
	
	UMLElement getUMLElement(GfxObject obj) {
		UMLElement umlElement = objects.get(obj);
		if(umlElement == null) Log.info("Object not found");
		return umlElement;
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
				GfxManager.getInstance().addToCanvas(canvas, outline, 0, 0);
			}
			GfxManager.getInstance().translate(outline, x-dx-(int)GfxManager.getInstance().getXFor(outline), y-dy-(int)GfxManager.getInstance().getYFor(outline));
		}
	}
	
	void drop(int x, int y) {
		if (selected!=null && selected.isDraggable()) {
			if (outline!=null) { 
				GfxManager.getInstance().removeFromCanvas(canvas, outline);
				outline=null;
			}
			if (x-dx!=selected.getX() || y-dy!=selected.getY()) {
				selected.setLocation(x-dx, y-dy);
				selected.adjusted();
			}
		}
		
	}
	
	void editItem(GfxObject o, int x, int y) {
		UMLElement elem = getUMLElement(o);
		if (elem!=null) {
			Iterator<UMLElementListener> it = listeners.iterator();
			while (it.hasNext()) {
				UMLElementListener lst = it.next();
				lst.itemEdited(elem, o);
			}
		}
	}
	
	final GfxObjectListener gfxObjectListener = new GfxObjectListener() 
	{
		public void mouseClicked() {
		}

		public void mouseDblClicked(GfxObject gfxObject, int x, int y) {
			editItem(gfxObject, x, y);
		}
		
		public void mouseMoved(int x, int y) {
			if (dragOn)
				drag(x, y);
		}

		public void mousePressed(GfxObject gfxObject, int x, int y) {
			if (outline==null) {
				select(gfxObject);
				if (selected!=null && selected.isDraggable()) {
					take(x, y);
					dragOn = true;
				}
			}
		}

		public void mouseReleased(GfxObject gfxObject, int x, int y) {
			if (dragOn)
				drop(x, y);
			dragOn = false;
		}		

		
	};
	
	GfxObject outline=null; 	// Outline is used for drawing while drag and drop
	int dx, dy;						// Represent the offset between object coordinates and mouse hotspot  
	UMLElement selected = null;		// Represent the current UMLElement selected
	boolean dragOn = false;			// Represent the dragging state
	boolean isInLinkingMode = false;
	Widget canvas;			// Drawing canvas
	Map<GfxObject, UMLElement> objects = new HashMap<GfxObject, UMLElement>();	// Map of UMLElement with their Graphical objects 
	Set<UMLElementListener> listeners = new HashSet<UMLElementListener>();				// Set of UMLElement listeners
}
