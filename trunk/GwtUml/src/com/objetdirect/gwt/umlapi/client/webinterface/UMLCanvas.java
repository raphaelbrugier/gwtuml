package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassDependencyArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationshipArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLElement;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.webinterface.CursorIconManager.PointerStyle;

public class UMLCanvas extends AbsolutePanel {

	private final static int FAR_AWAY = 1000;
	private ContextMenuManager contextMenuManager; 
	public UMLCanvas() {
		canvas = GfxManager.getInstance().makeCanvas();
		this.setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH, GfxPlatform.DEFAULT_CANVAS_HEIGHT);
		initCanvas();

	}
	
	public UMLCanvas(int width, int height) {
		canvas = GfxManager.getInstance().makeCanvas(width, height, ThemeManager.getBackgroundColor());
		this.setPixelSize(width, height);
		initCanvas();
	}
	
	  
	private void initCanvas() {
		GfxManager.getInstance().addObjectListenerToCanvas(canvas, gfxObjectListener);
		add(canvas, 0, 0);
		contextMenuManager = new ContextMenuManager(this);
	}



	public void addNewClass() {
		if(dragOn) return;
		ClassArtifact newClass = new ClassArtifact("Class");
		newClass.setLocation(FAR_AWAY, FAR_AWAY);
		add(newClass);	
		if (selected!=null)
			selected.unselect();
		select(newClass.getGfxObject());	
		take(FAR_AWAY+ClassArtifact.DEFAULT_WIDTH/2, FAR_AWAY);
		dragOn = true;
	}
	
	public void addNewNote() {
		if(dragOn) return;
		NoteArtifact newNote = new NoteArtifact();
		newNote.setLocation(FAR_AWAY, FAR_AWAY);
		add(newNote);		
		if (selected!=null)
			selected.unselect();
		select(newNote.getGfxObject());
		take(FAR_AWAY+NoteArtifact.DEFAULT_WIDTH/2, FAR_AWAY);
		dragOn = true;

	}
	public void addNewLink(Link linkType) {
		activeLinking = linkType;
		CursorIconManager.setCursorIcon(PointerStyle.CROSSHAIR);
	}
	public void setDeleteMode() {
		isDeleting = true;
		CursorIconManager.setCursorIcon(PointerStyle.NOT_ALLOWED);
	}
	
	public void removeSelected() {
		if(selected != null) remove(selected);
	}

	public void add(UMLElement element) {
		element.setCanvas(this);
		GfxManager.getInstance().addToCanvas(canvas, element.getGfxObject(), 0, 0);
		register(element.getGfxObject(), element);
		List<GfxObject> elems = element.getComponents();
		for (int i = 0; i<elems.size(); i++) {
			register(elems.get(i), element);
		}
		
	}

	public void remove(UMLElement element) {
		GfxManager.getInstance().removeFromCanvas(canvas, element.getGfxObject());
		unregister(element.getGfxObject());
		List<GfxObject> elems = element.getComponents();
		for (int i = 0; i<elems.size(); i++) {
			unregister(elems.get(i));
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

	void select(GfxObject gfxObject) {
		UMLElement newSelected = getUMLElement(gfxObject);
		Log.debug("Selecting : " + newSelected + " (" + gfxObject + ")");
		if(isDeleting && newSelected != null) {
			remove(newSelected);
			isDeleting = false;
			CursorIconManager.setCursorIcon(PointerStyle.AUTO);
			return;
		}
		// if it's the same nothing is to be done
		if (newSelected != selected || isDeleting) {

			if(newSelected == null) {
				activeLinking = Link.NONE;
				isDeleting = false;
				CursorIconManager.setCursorIcon(PointerStyle.AUTO);
			}

			if (selected!=null) {
				if(activeLinking != Link.NONE) {
					if((selected.getClass() == NoteArtifact.class) ||
							(newSelected.getClass() == NoteArtifact.class)) {
						if(newSelected.getClass() == NoteArtifact.class)
								add(new NoteLinkArtifact((NoteArtifact) newSelected, (UMLArtifact) selected));
						else add(new NoteLinkArtifact((NoteArtifact) selected, (UMLArtifact) newSelected));
					}
					else switch (activeLinking) {
					case SIMPLE:
						add(new ClassDependencyArtifact.Simple((ClassArtifact) selected,(ClassArtifact) newSelected));
						break;
					case IMPLEMENTATION:
						add(new ClassDependencyArtifact.Implementation((ClassArtifact) selected,(ClassArtifact) newSelected));
						break;
					case EXTENSION:
						add(new ClassDependencyArtifact.Extension((ClassArtifact) selected,(ClassArtifact) newSelected));
						break;
					case RELATIONSHIP:
						add(new RelationshipArtifact((ClassArtifact) selected,(ClassArtifact) newSelected));
						break;		
					default:
						break;
					}

					activeLinking = Link.NONE;
					CursorIconManager.setCursorIcon(PointerStyle.AUTO);
				}
				Log.debug("UnSelecting : " + selected);
				selected.unselect();
			}

			selected = newSelected;

			if (selected!=null) {
				selected.select();
				Log.debug("Selecting really : " + selected);
			}
		}
	}

	UMLElement getUMLElement(GfxObject obj) {
		UMLElement umlElement = objects.get(obj);
		if(umlElement == null) Log.info("Object not found");
		return umlElement;
	}

	void take(int x, int y) {
		Log.debug("Take at " + x + "," + y);
		if (selected!=null && selected.isDraggable()) {
			dx = x-selected.getX();
			dy = y-selected.getY();
			Log.debug("... with " + dx + "," + dy + " for " + selected);
			
		}		
	}

	void drag(int x, int y) {
		if (selected!=null && selected.isDraggable()) {
			if (outline==null) {
				outline = selected.getOutline();
				GfxManager.getInstance().addToCanvas(canvas, outline, 0, 0);
				Log.debug("Adding outline for " + selected);
				CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			}
			int tx = x-dx-(int)GfxManager.getInstance().getXFor(outline);
			int ty = y-dy-(int)GfxManager.getInstance().getYFor(outline);
			Log.debug("Translating " + tx + "," + ty);
			GfxManager.getInstance().translate(outline, tx, ty);
		}
	}

	void drop(int x, int y) {
		if (selected!=null && selected.isDraggable()) {
			
			if (outline!=null) { 
				GfxManager.getInstance().removeFromCanvas(canvas, outline);
				outline=null;
			}
			if (x-dx!=selected.getX() || y-dy!=selected.getY()) {
				int fx = x-dx;
				int fy = y-dy;
				Log.debug("Dropping at " + fx + "," + fy + " for " + selected);
				CursorIconManager.setCursorIcon(PointerStyle.AUTO);
				selected.setLocation(fx, fy);
				selected.adjusted();
			}
		}

	}

	void editItem(GfxObject gfxObject, int x, int y) {
		Log.debug("Edit request on " + gfxObject);
		UMLElement elem = getUMLElement(gfxObject);
		if (elem!=null) {
			Log.debug("Edit started on " + elem);
			elem.edit(gfxObject, x, y);
		}
	}

	private void dropRightMenu(GfxObject gfxObject, int x, int y) {
		select(gfxObject);
		UMLElement elem = getUMLElement(gfxObject);
		if (elem!=null) contextMenuManager.makeMenu(elem.getRightMenu());
		else contextMenuManager.makeMenu();
		contextMenuManager.show(x, y);
	}		 
	

	final GfxObjectListener gfxObjectListener = new GfxObjectListener() 
	{
		public void mouseClicked() {
		}

		public void mouseDblClicked(GfxObject gfxObject, int x, int y) {
			x = convertToRealX(x);
			y = convertToRealY(y);
			editItem(gfxObject, x, y);
		}

		public void mouseMoved(int x, int y) {
			x = convertToRealX(x);
			y = convertToRealY(y);
			if (dragOn)
				drag(x, y);
		}

		public void mouseReleased(GfxObject gfxObject, int x, int y) {
			x = convertToRealX(x);
			y = convertToRealY(y);
			if (dragOn)
				drop(x, y);
			dragOn = false;
		}

		public void mouseLeftClickPressed(GfxObject gfxObject, int x, int y) {
			x = convertToRealX(x);
			y = convertToRealY(y);
			if (outline==null) {
				select(gfxObject);
				if (selected!=null && selected.isDraggable()) {
					take(x, y);
					dragOn = true;
				}
			}
			
		}

		public void mouseRightClickPressed(GfxObject gfxObject, int x, int y) {
			x = convertToRealX(x);
			y = convertToRealY(y);
			dropRightMenu(gfxObject, x, y);
		}


	};

	private int convertToRealX(int x) {
		return x + RootPanel.getBodyElement().getScrollLeft() - getAbsoluteLeft();
	}
	private int convertToRealY(int y) {
		return y + RootPanel.getBodyElement().getScrollTop() - getAbsoluteTop();
	}
	
	GfxObject outline=null; 	// Outline is used for drawing while drag and drop
	int dx, dy;						// Represent the offset between object coordinates and mouse hotspot  
	UMLElement selected = null;		// Represent the current UMLElement selected
	boolean dragOn = false;			// Represent the dragging state
	Widget canvas;			// Drawing canvas
	Map<GfxObject, UMLElement> objects = new HashMap<GfxObject, UMLElement>();	// Map of UMLElement with their Graphical objects 
	public enum Link {NONE, SIMPLE, IMPLEMENTATION, EXTENSION, RELATIONSHIP};	
	private Link activeLinking  = Link.NONE;
	private boolean isDeleting = false;
}
