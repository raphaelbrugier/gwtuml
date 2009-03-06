package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassDependencyArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationshipArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLElement;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.webinterface.CursorIconManager.PointerStyle;

public class UMLCanvas extends AbsolutePanel {
	private static long classCount = 1;
	private static long noteCount = 0;
	public enum Link {
		EXTENSION, IMPLEMENTATION, NONE, RELATIONSHIP, SIMPLE
	}	
	private final static int FAR_AWAY = 9000;
	private Widget canvas; // Drawing canvas
	private boolean dragOn = false; // Represent the dragging state
	private int dx, dy; // Represent the offset between object coordinates and mouse

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
	// Map of UMLElement with corresponding Graphical objects (group)
	private Set<UMLElement> objectsToBeAddedWhenAttached = new HashSet<UMLElement>();
	private Map<GfxObject, UMLElement> objects = new HashMap<GfxObject, UMLElement>();
	private GfxObject outline = null; // Outline is used for drawing while drag and drop	
	private UMLElement selected = null; // Represent the current UMLElement selected
	private Link activeLinking = Link.NONE;
	private boolean isDeleting = false;

	public UMLCanvas() {
		canvas = GfxManager.getPlatform().makeCanvas();
		this.setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH,
				GfxPlatform.DEFAULT_CANVAS_HEIGHT);
		initCanvas();
	}

	public UMLCanvas(int width, int height) {
		canvas = GfxManager.getPlatform().makeCanvas(width, height,
				ThemeManager.getBackgroundColor());
		this.setPixelSize(width, height);
		initCanvas();
	}

	public void add(UMLElement element) {
		if(element == null) {
			Log.error("Adding null element to canvas");
			return;
		}
		if(isAttached()) {
			element.setCanvas(this);
			GfxManager.getPlatform().addToCanvas(canvas, element.initializeGfxObject(), element.getX(), element.getY());
			objects.put(element.getGfxObject(), element);
		} else {
			Log.info("Canvas not attached, queuing " + element);
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
		dragOn = true;

	}

	public void remove(UMLElement element) {
		GfxManager.getPlatform().removeFromCanvas(canvas,
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
				GfxManager.getPlatform().addToCanvas(canvas, outline, 0, 0);
				Log.debug("Adding outline for " + selected);
				CursorIconManager.setCursorIcon(PointerStyle.MOVE);
			}
			int tx = x - dx - (int) GfxManager.getPlatform().getXFor(outline);
			int ty = y - dy - (int) GfxManager.getPlatform().getYFor(outline);
			Log.trace("Translating " + tx + "," + ty);
			GfxManager.getPlatform().translate(outline, tx, ty);
		}
	}

	private void drop(int x, int y) {
		if (selected != null && selected.isDraggable()) {

			if (outline != null) {
				GfxManager.getPlatform().removeFromCanvas(canvas, outline);
				outline = null;
			}
			if (x - dx != selected.getX() || y - dy != selected.getY()) {
				int fx = x - dx;
				int fy = y - dy;
				Log.debug("Dropping at " + fx + "," + fy + " for " + selected);
				CursorIconManager.setCursorIcon(PointerStyle.AUTO);
				selected.moveTo(fx, fy);
				selected.adjusted();
			}
		}

	}

	private void editItem(GfxObject gfxObject, int x, int y) {
		Log.debug("Edit request on " + gfxObject);
		UMLElement elem = getUMLElement(gfxObject);
		if (elem != null) {
			Log.debug("Edit started on " + elem);
			elem.edit(gfxObject, x, y);
		}
	}

	private UMLElement getUMLElement(GfxObject gfxO) {
		if (gfxO == null) {
			Log.info("No Object");
			return null;
		}
		GfxObject gfxOPrentGroup = GfxManager.getPlatform().getGroup(gfxO);
		while (gfxOPrentGroup != null) {
			gfxO = gfxOPrentGroup;
			gfxOPrentGroup = GfxManager.getPlatform().getGroup(gfxO);
		}

		UMLElement umlElement = objects.get(gfxO);
		if (umlElement == null)
			Log.info("Artifact not found");

		return umlElement;
	}

	private void select(GfxObject gfxObject) {
		UMLElement newSelected = getUMLElement(gfxObject);
		Log.debug("Selecting : " + newSelected + " (" + gfxObject + ")");
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
							add(new ClassDependencyArtifact.Simple(
									(ClassArtifact) selected,
									(ClassArtifact) newSelected));
							break;
						case IMPLEMENTATION:
							add(new ClassDependencyArtifact.Implementation(
									(ClassArtifact) selected,
									(ClassArtifact) newSelected));
							break;
						case EXTENSION:
							add(new ClassDependencyArtifact.Extension(
									(ClassArtifact) selected,
									(ClassArtifact) newSelected));
							break;
						case RELATIONSHIP:
							add(new RelationshipArtifact(
									(ClassArtifact) selected,
									(ClassArtifact) newSelected));
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

			if (selected != null) {
				selected.select();
				Log.debug("Selecting really : " + selected);
			}
		}
	}

	private void take(int x, int y) {
		Log.debug("Take at " + x + "," + y);
		if (selected != null && selected.isDraggable()) {
			dx = x - selected.getX();
			dy = y - selected.getY();
			Log.debug("... with " + dx + "," + dy + " for " + selected);

		}
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
		UMLElement elem = getUMLElement(gfxObject);
		ContextMenu contextMenu;
		if (elem != null)
			contextMenu = new ContextMenu(x, y, this, elem.getRightMenu());
		else
			contextMenu = new ContextMenu(x, y, this);
		contextMenu.show();
	}
	
	private void initCanvas() {
		add(canvas, 0, 0);
		GfxManager.getPlatform().addObjectListenerToCanvas(canvas,
				gfxObjectListener);
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
		for (UMLElement elementNotAdded : objectsToBeAddedWhenAttached) {
			elementNotAdded.setCanvas(this);
			GfxManager.getPlatform().addToCanvas(canvas, elementNotAdded.initializeGfxObject(), elementNotAdded.getX(), elementNotAdded.getY());
			objects.put(elementNotAdded.getGfxObject(), elementNotAdded);
		}
		objectsToBeAddedWhenAttached.clear();
	}
}
