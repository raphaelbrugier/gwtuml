package com.objetdirect.gwt.umlapi.client.webinterface;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.ClassDependencyLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.webinterface.CursorIconManager.PointerStyle;
/**
 * @author  florian
 */
public class UMLCanvas extends AbsolutePanel {
	private static long classCount = 1;
	private static long noteCount = 0;
	/**
	 * @author   florian
	 */
	public enum Link {
		/**
		 * @uml.property  name="eXTENSION"
		 * @uml.associationEnd  
		 */
		EXTENSION, /**
		 * @uml.property  name="iMPLEMENTATION"
		 * @uml.associationEnd  
		 */
		IMPLEMENTATION, /**
		 * @uml.property  name="nONE"
		 * @uml.associationEnd  
		 */
		NONE, /**
		 * @uml.property  name="rELATIONSHIP"
		 * @uml.associationEnd  
		 */
		RELATIONSHIP, /**
		 * @uml.property  name="sIMPLE"
		 * @uml.associationEnd  
		 */
		SIMPLE
	}	
	private final static int FAR_AWAY = 9000;
	private Widget drawingCanvas; // Drawing canvas
	private boolean dragOn = false; // Represent the dragging state
	private int dx; // Represent the offset between object coordinates and mouse
	private int dy;
	/**
	 * @uml.property  name="gfxObjectListener"
	 * @uml.associationEnd  
	 */
	private final GfxObjectListener gfxObjectListener = new GfxObjectListener() {
		public void mouseClicked() {
		}
		public void mouseDblClicked(GfxObject gfxObject, int x, int y) {
			x = convertToRealX(x);
			y = convertToRealY(y);
			editItem(gfxObject, x, y);
		}
		public void mouseLeftClickPressed(GfxObject gfxObject, int x, int y) {
			x = convertToRealX(x);
			y = convertToRealY(y);
			if (outline == null) {
				select(gfxObject);				
				if (selected != null && selected.isDraggable()) {
					take(x, y);
					dragOn = true;
				}
			}
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
		public void mouseRightClickPressed(GfxObject gfxObject, int x, int y) {
			x = convertToRealX(x);
			y = convertToRealY(y);
			dropRightMenu(gfxObject, x, y);
		}
	};
	// Map of UMLArtifact with corresponding Graphical objects (group)
	private Set<UMLArtifact> objectsToBeAddedWhenAttached = new HashSet<UMLArtifact>();
	private Map<GfxObject, UMLArtifact> objects = new HashMap<GfxObject, UMLArtifact>();
	/**
	 * @uml.property  name="outline"
	 * @uml.associationEnd  
	 */
	private GfxObject outline = null; // Outline is used for drawing while drag and drop	
	/**
	 * @uml.property  name="selected"
	 * @uml.associationEnd  
	 */
	private UMLArtifact selected = null; // Represent the current UMLArtifact selected
	/**
	 * @uml.property  name="activeLinking"
	 * @uml.associationEnd  
	 */
	private Link activeLinking = Link.NONE;
	private boolean isDeleting = false;
	public UMLCanvas() {
		Log.trace("Making Canvas");
		drawingCanvas = GfxManager.getPlatform().makeCanvas();
		this.setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH,
				GfxPlatform.DEFAULT_CANVAS_HEIGHT);
		initCanvas();
	}
	public UMLCanvas(int width, int height) {
		Log.trace("Making " + width + " x " + height  + " Canvas");
		drawingCanvas = GfxManager.getPlatform().makeCanvas(width, height,
				ThemeManager.getBackgroundColor());
		this.setPixelSize(width, height);
		initCanvas();
	}
	public void add(UMLArtifact element) {
		if(element == null) {
			Log.error("Adding null element to canvas");
			return;
		}
		if(isAttached()) {
			element.setCanvas(this);
			long t = System.currentTimeMillis();
			GfxManager.getPlatform().addToCanvas(drawingCanvas, element.initializeGfxObject(), element.getX(), element.getY());
			objects.put(element.getGfxObject(), element);
			Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to add " + element);
		} else {
			Log.trace("Canvas not attached, queuing " + element);
			objectsToBeAddedWhenAttached.add(element);
		}
	}
	public void addNewClass() {
		addNewClass(FAR_AWAY, FAR_AWAY);
	
	}
	public void addNewClass(int xInit, int yInit) {
		if (dragOn)
			return;
		ClassArtifact newClass = new ClassArtifact("Class " + ++classCount);
		add(newClass);
		newClass.moveTo(xInit, yInit);
		if (selected != null)
			selected.unselect();
		select(newClass.getGfxObject());
		take(xInit, yInit);
		drag(xInit, yInit);
		dragOn = true;
	}
	public void addNewLink(Link linkType) {
		activeLinking = linkType;
		CursorIconManager.setCursorIcon(PointerStyle.CROSSHAIR);
	}
	public void addNewNote() {
		addNewClass(FAR_AWAY, FAR_AWAY);
	
	}
	public void addNewNote(int xInit, int yInit) {
		if (dragOn)
			return;
		NoteArtifact newNote = new NoteArtifact("Note " + ++noteCount);
		add(newNote);
		newNote.moveTo(xInit, yInit);
		if (selected != null)
			selected.unselect();
		select(newNote.getGfxObject());
		take(xInit, yInit);
		drag(xInit, yInit);		
		dragOn = true;
	}
	public void remove(UMLArtifact element) {
		GfxManager.getPlatform().removeFromCanvas(drawingCanvas,
				element.getGfxObject());
		objects.remove(element.getGfxObject());
		element.setCanvas(null);
		if (element == selected)
			selected = null;
	}
	public void removeSelected() {
		if (selected != null)
			remove(selected);
	}
	public void setDeleteMode() {
		isDeleting = true;
		CursorIconManager.setCursorIcon(PointerStyle.NOT_ALLOWED);
	}
	private void drag(int x, int y) {
		if (selected != null && selected.isDraggable()) {
			if (outline == null) {
				outline = selected.getOutline();
				GfxManager.getPlatform().addToCanvas(drawingCanvas, outline, 0, 0);
				GfxManager.getPlatform().clearVirtualGroup(selected.getGfxObject());
				Log.trace("Adding outline for " + selected);
				CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			}
			int tx = x - dx -  GfxManager.getPlatform().getXFor(outline);
			int ty = y - dy -  GfxManager.getPlatform().getYFor(outline);
			Log.trace("Translating " + tx + "," + ty);
			GfxManager.getPlatform().translate(outline, tx, ty);
		}
	}
	private void drop(int x, int y) {
		if (selected != null && selected.isDraggable()) {
			if (outline != null) {
				GfxManager.getPlatform().removeFromCanvas(drawingCanvas, outline);
				outline = null;
			}
			int fx = x - dx;
			int fy = y - dy;
			if (fx != selected.getX() || fy != selected.getY()) {
				
				Log.trace("Dropping at " + fx + "," + fy + " for " + selected);
				CursorIconManager.setCursorIcon(PointerStyle.AUTO);
				selected.moveTo(fx, fy);
				selected.rebuildGfxObject();
			}
			
		}
	}
	private void editItem(GfxObject gfxObject, int x, int y) {
		Log.trace("Edit request on " + gfxObject);
		UMLArtifact elem = getUMLArtifact(gfxObject);
		if (elem != null) {
			Log.trace("Edit started on " + elem);
			elem.edit(gfxObject, x, y);
		}
	}
	private UMLArtifact getUMLArtifact(GfxObject gfxO) {
		if (gfxO == null) {
			Log.trace("No Object");
			return null;
		}
		GfxObject gfxOPrentGroup = GfxManager.getPlatform().getGroup(gfxO);
		while (gfxOPrentGroup != null) {
			gfxO = gfxOPrentGroup;
			gfxOPrentGroup = GfxManager.getPlatform().getGroup(gfxO);
		}
		UMLArtifact UMLArtifact = objects.get(gfxO);
		if (UMLArtifact == null)
			Log.trace("Artifact not found");
		return UMLArtifact;
	}
	private void select(GfxObject gfxObject) {
		UMLArtifact newSelected = getUMLArtifact(gfxObject);
		Log.trace("Selecting : " + newSelected + " (" + gfxObject + ")");
		if (isDeleting && newSelected != null) {
			remove(newSelected);
			isDeleting = false;
			CursorIconManager.setCursorIcon(PointerStyle.AUTO);
			return;
		}
		// if it's the same nothing is to be done
		if (newSelected != selected || isDeleting) {
			if (newSelected == null) {
				activeLinking = Link.NONE;
				isDeleting = false;
				CursorIconManager.setCursorIcon(PointerStyle.AUTO);
			}
			if (selected != null) {
				if (activeLinking != Link.NONE) {
					if ((selected.getClass() == NoteArtifact.class)
							|| (newSelected.getClass() == NoteArtifact.class)) {
						if (newSelected.getClass() == NoteArtifact.class)
							add(new NoteLinkArtifact(
									(NoteArtifact) newSelected,
									(UMLArtifact) selected));
						else
							add(new NoteLinkArtifact((NoteArtifact) selected,
									(UMLArtifact) newSelected));
					} else
						switch (activeLinking) {
						case SIMPLE:
							add(new ClassDependencyLinkArtifact.Simple(
									(ClassArtifact) selected,
									(ClassArtifact) newSelected));
							break;
						case IMPLEMENTATION:
							add(new ClassDependencyLinkArtifact.Implementation(
									(ClassArtifact) selected,
									(ClassArtifact) newSelected));
							break;
						case EXTENSION:
							add(new ClassDependencyLinkArtifact.Extension(
									(ClassArtifact) selected,
									(ClassArtifact) newSelected));
							break;
						case RELATIONSHIP:
							add(new RelationshipLinkArtifact(
									(ClassArtifact) selected,
									(ClassArtifact) newSelected));
							break;
						default:
							break;
						}
					activeLinking = Link.NONE;
					CursorIconManager.setCursorIcon(PointerStyle.AUTO);
				}
				Log.trace("UnSelecting : " + selected);
				selected.unselect();
			}
			selected = newSelected;
			if (selected != null) {
				selected.select();
				Log.trace("Selecting really : " + selected);
			}
		}
	}
	private void take(int x, int y) {
		dx = x - selected.getX();
		dy = y - selected.getY();
		Log.trace("Take at " + x + "," + y + " with " + dx + "," + dy + " for " + selected);
	}
	private int convertToRealX(int x) {
		return x + RootPanel.getBodyElement().getScrollLeft()
		- getAbsoluteLeft();
	}
	private int convertToRealY(int y) {
		return y + RootPanel.getBodyElement().getScrollTop() - getAbsoluteTop();
	};
	private void dropRightMenu(GfxObject gfxObject, int x, int y) {
		select(gfxObject);
		UMLArtifact elem = getUMLArtifact(gfxObject);
		ContextMenu contextMenu;
		if (elem != null)
			contextMenu = new ContextMenu(x, y, this, elem.getRightMenu());
		else
			contextMenu = new ContextMenu(x, y, this);
		contextMenu.show();
	}
	
	private void initCanvas() {
		Log.trace("Adding Canvas");
		add(drawingCanvas, 0, 0);
		Log.trace("Adding object listener");
		GfxManager.getPlatform().addObjectListenerToCanvas(drawingCanvas,
				gfxObjectListener);
		Log.trace("Init canvas done");
	}
	@Override
	protected void onAttach() {
		super.onAttach();
		Log.trace("Attaching");
	}
	@Override
	protected void onLoad() {	
		super.onLoad();
		Log.trace("Loading");
		for (UMLArtifact elementNotAdded : objectsToBeAddedWhenAttached) {
			elementNotAdded.setCanvas(this);
			long t = System.currentTimeMillis();
			GfxManager.getPlatform().addToCanvas(drawingCanvas, elementNotAdded.initializeGfxObject(), elementNotAdded.getX(), elementNotAdded.getY());
			objects.put(elementNotAdded.getGfxObject(), elementNotAdded);
			Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to add queued " + elementNotAdded);
		}
		objectsToBeAddedWhenAttached.clear();
	}
}
