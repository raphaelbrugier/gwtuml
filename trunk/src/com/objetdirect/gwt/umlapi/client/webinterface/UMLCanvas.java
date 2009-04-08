package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLEventListener;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.CursorIconManager.PointerStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager.QualityLevel;

/**
 * @author florian
 */
public class UMLCanvas extends AbsolutePanel {

    private static long classCount = 1;
    /**
     * @author florian
     */
    private final static int FAR_AWAY = 9000;
    private static long noteCount = 0;
    // selected
    private RelationKind activeLinking = null;
    private boolean dragOn = false; // Represent the dragging state
    private final Widget drawingCanvas; // Drawing canvas
    private int dx; // Represent the offset between object coordinates and mouse
    private int dy;
    private final GfxObjectListener gfxObjectListener = new GfxObjectListener() {
	public void mouseClicked() {
	    // Unused
	}

	public void mouseDblClicked(final GfxObject gfxObject, final int x,
		final int y) {
	    editItem(gfxObject);
	}

	public void mouseLeftClickPressed(final GfxObject gfxObject,
		final int x, final int y) {
	    final int realX = convertToRealX(x);
	    final int realY = convertToRealY(y);
	    if (UMLCanvas.this.outline == null) {
		select(gfxObject);
		if (UMLCanvas.this.selected != null && UMLCanvas.this.selected.isDraggable()) {
		    take(realX, realY);
		    UMLCanvas.this.dragOn = true;
		}
	    }
	}

	public void mouseMoved(final int x, final int y) {
	    final int realX = convertToRealX(x);
	    final int realY = convertToRealY(y);
	    if (UMLCanvas.this.dragOn) {
		drag(realX, realY);
	    }
	    if (UMLCanvas.this.activeLinking != null && UMLCanvas.this.selected != null) {
		animateLinking(realX, realY);

	    }
	}

	public void mouseReleased(final GfxObject gfxObject, final int x,
		final int y) {
	    final int realX = convertToRealX(x);
	    final int realY = convertToRealY(y);
	    if (UMLCanvas.this.dragOn) {
		drop(realX, realY);
	    }
	    UMLCanvas.this.dragOn = false;
	}

	public void mouseRightClickPressed(final GfxObject gfxObject,
		final int x, final int y) {
	    final int realX = convertToRealX(x);
	    final int realY = convertToRealY(y);
	    dropRightMenu(gfxObject, realX, realY);
	}
    };
    private boolean isDeleting = false;

    private GfxObject movingLine;
    private final Map<GfxObject, UMLArtifact> objects = new HashMap<GfxObject, UMLArtifact>();
    // Map of UMLArtifact with corresponding Graphical objects (group)
    private final Set<UMLArtifact> objectsToBeAddedWhenAttached = new HashSet<UMLArtifact>();
    private GfxObject outline = null; // Outline is used for drawing while drag
    private final HashMap<Point, GfxObject> outlineDependencies = new HashMap<Point, GfxObject>();
    // and drop
    private UMLArtifact selected = null; // Represent the current UMLArtifact

    private final ArrayList<UMLEventListener> uMLEventListenerList = new ArrayList<UMLEventListener>();

    public UMLCanvas() {
	Log.trace("Making Canvas");
	this.drawingCanvas = GfxManager.getPlatform().makeCanvas();
	setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH,
		GfxPlatform.DEFAULT_CANVAS_HEIGHT);
	initCanvas();
    }

    public UMLCanvas(final int width, final int height) {
	Log.trace("Making " + width + " x " + height + " Canvas");
	this.drawingCanvas = GfxManager.getPlatform().makeCanvas(width, height,
		ThemeManager.getBackgroundColor());
	setPixelSize(width, height);
	initCanvas();
    }

    public void add(final UMLArtifact element) {
	if (element == null) {
	    Log.error("Adding null element to canvas");
	    return;
	}
	if (isAttached()) {
	    element.setCanvas(this);
	    final long t = System.currentTimeMillis();
	    GfxManager.getPlatform().addToCanvas(this.drawingCanvas,
		    element.initializeGfxObject(), element.getX(),
		    element.getY());
	    this.objects.put(element.getGfxObject(), element);
	    Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to add "
		    + element);
	} else {
	    Log.trace("Canvas not attached, queuing " + element);
	    this.objectsToBeAddedWhenAttached.add(element);
	}
    }

    public void addNewClass() {
	addNewClass(FAR_AWAY, FAR_AWAY);

    }

    public void addNewClass(final int xInit, final int yInit) {
	if (this.dragOn) {
	    return;
	}
	final ClassArtifact newClass = new ClassArtifact("Class "
		+ ++classCount);
	if (fireNewArtifactEvent(newClass)) {
	    add(newClass);
	    newClass.moveTo(xInit, yInit);
	    if (this.selected != null) {
		this.selected.unselect();
	    }
	    select(newClass.getGfxObject());
	    take(xInit, yInit);
	    drag(xInit, yInit);
	    this.dragOn = true;
	}
    }

    public void addNewLink(final UMLArtifact newSelected) {
	LinkArtifact newLink;
	Log.info("selected " + this.selected.getClass() + " newSelected "
		+ newSelected.getClass());
	if (this.activeLinking == RelationKind.OTHER) {
	    if (newSelected.getClass() == NoteArtifact.class) {
		newLink = new NoteLinkArtifact((NoteArtifact) newSelected,
			this.selected);
	    } else if (this.selected.getClass() == NoteArtifact.class) {
		newLink = new NoteLinkArtifact((NoteArtifact) this.selected,
			newSelected);
	    } else if (newSelected.getClass().getSuperclass() == RelationLinkArtifact.class
		    && this.selected.getClass() == ClassArtifact.class) {
		newLink = new ClassRelationLinkArtifact(
			(ClassArtifact) this.selected,
			(RelationLinkArtifact) newSelected);
	    } else if (this.selected.getClass().getSuperclass() == RelationLinkArtifact.class
		    && newSelected.getClass() == ClassArtifact.class) {
		newLink = new ClassRelationLinkArtifact(
			(ClassArtifact) newSelected,
			(RelationLinkArtifact) this.selected);
	    } else {
		newLink = null;
	    }
	} else if (this.selected.getClass() == ClassArtifact.class
		&& newSelected.getClass() == ClassArtifact.class) {
	    newLink = RelationLinkArtifact.makeLinkArtifact(
		    (ClassArtifact) newSelected, (ClassArtifact) this.selected,
		    this.activeLinking);
	} else {
	    newLink = null;
	}

	if (newLink != null && fireNewLinkEvent(newLink)) {
	    add(newLink);
	}
    }

    public void addNewNote() {
	addNewNote(FAR_AWAY, FAR_AWAY);

    }

    public void addNewNote(final int xInit, final int yInit) {
	if (this.dragOn) {
	    return;
	}
	final NoteArtifact newNote = new NoteArtifact("Note " + ++noteCount);
	if (fireNewArtifactEvent(newNote)) {
	    add(newNote);
	    newNote.moveTo(xInit, yInit);
	    if (this.selected != null) {
		this.selected.unselect();
	    }
	    select(newNote.getGfxObject());
	    take(xInit, yInit);
	    drag(xInit, yInit);
	    this.dragOn = true;
	}
    }

    public void addUMLEventListener(final UMLEventListener uMLEventListener) {
	this.uMLEventListenerList.add(uMLEventListener);
    }

    private void animateLinking(final int x, final int y) {
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.HIGH)) {
	    if (this.movingLine != null) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
			this.movingLine);
	    }
	    this.movingLine = GfxManager.getPlatform().buildLine(
		    this.selected.getCenterX(), this.selected.getCenterY(), x, y);
	    GfxManager.getPlatform().setStroke(this.movingLine,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStrokeStyle(this.movingLine, GfxStyle.DASH);
	    GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.movingLine, 0,
		    0);
	    GfxManager.getPlatform().moveToBack(this.movingLine);
	}
    }

    private int convertToRealX(final int x) {
	return x + RootPanel.getBodyElement().getScrollLeft()
		- getAbsoluteLeft();
    }

    private int convertToRealY(final int y) {
	return y + RootPanel.getBodyElement().getScrollTop() - getAbsoluteTop();
    }

    private void drag(final int x, final int y) {
	if (this.selected != null && this.selected.isDraggable()) {
	    if (this.outline == null) {
		this.outline = this.selected.getOutline();
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.outline, 0,
			0);
		this.selected.destructGfxObjectWhithDependencies();
		Log.trace("Adding outline for " + this.selected);
		CursorIconManager.setCursorIcon(PointerStyle.MOVE);
	    }
	    final int tx = x - this.dx - GfxManager.getPlatform().getXFor(this.outline);
	    final int ty = y - this.dy - GfxManager.getPlatform().getYFor(this.outline);
	    Log.trace("Translating " + tx + "," + ty);
	    GfxManager.getPlatform().translate(this.outline, tx, ty);
	    outlineDependencies(this.selected.getOutlineForDependencies(), x - this.dx
		    + this.selected.getWidth() / 2, y - this.dy + this.selected.getHeight()
		    / 2);

	}
    }

    private void drop(final int x, final int y) {
	if (this.selected != null && this.selected.isDraggable()) {
	    if (this.outline != null) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
			this.outline);
		this.outline = null;
		for (final Entry<Point, GfxObject> entry : this.outlineDependencies
			.entrySet()) {
		    GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
			    entry.getValue());
		}
		this.outlineDependencies.clear();
	    }
	    final int fx = x - this.dx;
	    final int fy = y - this.dy;
	    if (fx != this.selected.getX() || fy != this.selected.getY()) {

		Log.trace("Dropping at " + fx + "," + fy + " for " + this.selected);
		CursorIconManager.setCursorIcon(PointerStyle.AUTO);
		this.selected.moveTo(fx, fy);
		this.selected.rebuildGfxObject();
	    }

	}
    }

    private void dropRightMenu(final GfxObject gfxObject, final int x,
	    final int y) {
	select(gfxObject);
	final UMLArtifact elem = getUMLArtifact(gfxObject);
	ContextMenu contextMenu;
	if (elem != null) {
	    contextMenu = new ContextMenu(x, y, this, elem.getRightMenu());
	} else {
	    contextMenu = new ContextMenu(x, y, this);
	}
	contextMenu.show();
    }

    private void editItem(final GfxObject gfxObject) {
	Log.trace("Edit request on " + gfxObject);
	final UMLArtifact elem = getUMLArtifact(gfxObject);
	if (elem != null) {
	    Log.trace("Edit started on " + elem);
	    elem.edit(gfxObject);
	}
    }

    public boolean fireNewArtifactEvent(final UMLArtifact umlArtifact) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : this.uMLEventListenerList) {
	    // If one is not ok then it's not ok !
	    isThisOk = listener.onNewUMLArtifact(umlArtifact) && isThisOk;
	}
	Log.trace("New Artifact event fired. Status : " + isThisOk);
	return isThisOk;

    }

    public boolean fireNewLinkEvent(final LinkArtifact newLink) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : this.uMLEventListenerList) {
	    isThisOk = listener.onNewLink(newLink) && isThisOk;
	}
	Log.trace("New Link event fired. Status : " + isThisOk);
	return isThisOk;
    }

    private UMLArtifact getUMLArtifact(final GfxObject gfxObject) {
	if (gfxObject == null) {
	    Log.trace("No Object");
	    return null;
	}
	GfxObject currentGfxObject = gfxObject;
	GfxObject gfxOParentGroup = GfxManager.getPlatform().getGroup(
		currentGfxObject);
	while (gfxOParentGroup != null) {
	    currentGfxObject = gfxOParentGroup;
	    gfxOParentGroup = GfxManager.getPlatform().getGroup(
		    currentGfxObject);
	}
	final UMLArtifact UMLArtifact = this.objects.get(currentGfxObject);
	if (UMLArtifact == null) {
	    Log.trace("Artifact not found");
	}
	return UMLArtifact;
    }

    private void initCanvas() {
	Log.trace("Adding Canvas");
	add(this.drawingCanvas, 0, 0);
	Log.trace("Adding object listener");
	GfxManager.getPlatform().addObjectListenerToCanvas(this.drawingCanvas,
		this.gfxObjectListener);
	Log.trace("Init canvas done");
    }

    public void moveSelected(final Direction direction) {
	if (this.selected != null) {
	    this.selected.moveTo(this.selected.getX() + OptionsManager.getMovingStep()
		    * direction.getXDirection(), this.selected.getY()
		    + OptionsManager.getMovingStep()
		    * direction.getYDirection());
	}
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
	for (final UMLArtifact elementNotAdded : this.objectsToBeAddedWhenAttached) {
	    elementNotAdded.setCanvas(this);
	    final long t = System.currentTimeMillis();
	    GfxManager.getPlatform().addToCanvas(this.drawingCanvas,
		    elementNotAdded.initializeGfxObject(),
		    elementNotAdded.getX(), elementNotAdded.getY());
	    this.objects.put(elementNotAdded.getGfxObject(), elementNotAdded);
	    Log.debug("([" + (System.currentTimeMillis() - t)
		    + "ms]) to add queued " + elementNotAdded);
	}
	this.objectsToBeAddedWhenAttached.clear();
    }

    private void outlineDependencies(
	    final ArrayList<Point> dependentArtifactLocations, final int x,
	    final int y) {

	if (!this.outlineDependencies.isEmpty()) {
	    for (final Entry<Point, GfxObject> entry : this.outlineDependencies
		    .entrySet()) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
			entry.getValue());
	    }
	    this.outlineDependencies.clear();
	}

	for (final Point point : dependentArtifactLocations) {
	    final GfxObject line = GfxManager.getPlatform().buildLine(
		    point.getX(), point.getY(), x, y);
	    GfxManager.getPlatform().setStroke(line,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
	    GfxManager.getPlatform().addToCanvas(this.drawingCanvas, line, 0, 0);
	    GfxManager.getPlatform().moveToBack(line);
	    this.outlineDependencies.put(point, line);
	}

    }

    public void remove(final UMLArtifact element) {
	removeRecursive(element);
	if (element.isALink()) {
	    ((LinkArtifact) element).removeCreatedDependency();
	}
    }

    private void removeRecursive(final UMLArtifact element) {
	GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
		element.getGfxObject());
	this.objects.remove(element.getGfxObject());
	element.setCanvas(null);
	if (element == this.selected) {
	    this.selected = null;
	}

	for (final Entry<LinkArtifact, UMLArtifact> entry : element
		.getDependentUMLArtifacts().entrySet()) {
	    if (entry.getValue().isALink()
		    && entry.getKey().getClass() != NoteLinkArtifact.class) {
		remove(entry.getValue());
	    }
	    entry.getValue().removeDependency(entry.getKey());
	    removeRecursive(entry.getKey());
	}

    }

    public void removeSelected() {
	if (this.selected != null) {
	    remove(this.selected);
	}
    }

    public void removeUMLEventListener(final UMLEventListener uMLEventListener) {
	this.uMLEventListenerList.remove(uMLEventListener);
    }

    private void select(final GfxObject gfxObject) {
	final UMLArtifact newSelected = getUMLArtifact(gfxObject);
	Log.trace("Selecting : " + newSelected + " (" + gfxObject + ")");
	if (this.isDeleting && newSelected != null) {
	    remove(newSelected);
	    this.isDeleting = false;
	    CursorIconManager.setCursorIcon(PointerStyle.AUTO);
	    return;
	}

	if (newSelected == null) {
	    this.activeLinking = null;
	    if (this.movingLine != null) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
			this.movingLine);
	    }
	    this.isDeleting = false;
	    CursorIconManager.setCursorIcon(PointerStyle.AUTO);
	}
	if (this.selected != null) {
	    if (this.activeLinking != null) {
		addNewLink(newSelected);
		this.activeLinking = null;
		if (this.movingLine != null) {
		    GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
			    this.movingLine);
		}
		CursorIconManager.setCursorIcon(PointerStyle.AUTO);
	    }
	    Log.trace("UnSelecting : " + this.selected);
	    this.selected.unselect();
	}
	this.selected = newSelected;
	if (this.selected != null) {
	    this.selected.select();
	    Log.trace("Selecting really : " + this.selected);
	}

    }

    public void setDeleteMode() {
	this.isDeleting = true;
	CursorIconManager.setCursorIcon(PointerStyle.NOT_ALLOWED);
    }

    private void take(final int x, final int y) {
	this.dx = x - this.selected.getX();
	this.dy = y - this.selected.getY();
	Log.trace("Take at " + x + "," + y + " with " + this.dx + "," + this.dy + " for "
		+ this.selected);
    }

    public void toLinkMode(final RelationKind linkType) {
	this.activeLinking = linkType;
	CursorIconManager.setCursorIcon(PointerStyle.CROSSHAIR);
    }
}
