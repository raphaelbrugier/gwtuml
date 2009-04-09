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
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkClassRelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
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
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLCanvas extends AbsolutePanel {

    private static long classCount = 1;
    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    private final static int FAR_AWAY = 9000;
    private static long noteCount = 0;
    // selected
    private RelationKind activeLinking = null;
    private boolean dragOn = false; // Represent the dragging state
    private final Widget drawingCanvas; // Drawing canvas
    private Point offset; // Represent the offset between object coordinates and mouse
    
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
	    final Point realPoint = convertToRealPoint(x, y);
	    if (outline == null) {
		select(gfxObject);
		if (selected != null && selected.isDraggable()) {
		    take(realPoint);
		    dragOn = true;
		}
	    }
	}

	public void mouseMoved(final int x, final int y) {
	    final Point realPoint = convertToRealPoint(x, y);
	    if (dragOn) {
		drag(realPoint);
	    }
	    if (activeLinking != null && selected != null) {
		animateLinking(realPoint);

	    }
	}

	public void mouseReleased(final GfxObject gfxObject, final int x,
		final int y) {
	    final Point realPoint = convertToRealPoint(x, y);
	    if (dragOn) {
		drop(realPoint);
	    }
	    dragOn = false;
	}

	public void mouseRightClickPressed(final GfxObject gfxObject,
		final int x, final int y) {
	    final Point realPoint = convertToRealPoint(x, y);
	    dropRightMenu(gfxObject, realPoint);
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
	drawingCanvas = GfxManager.getPlatform().makeCanvas();
	setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH,
		GfxPlatform.DEFAULT_CANVAS_HEIGHT);
	initCanvas();
    }

    public UMLCanvas(final int width, final int height) {
	Log.trace("Making " + width + " x " + height + " Canvas");
	drawingCanvas = GfxManager.getPlatform().makeCanvas(width, height,
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
	    GfxManager.getPlatform().addToCanvas(drawingCanvas,
		    element.initializeGfxObject(),
		    element.getLocation());
	    objects.put(element.getGfxObject(), element);
	    Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to add "
		    + element);
	} else {
	    Log.trace("Canvas not attached, queuing " + element);
	    objectsToBeAddedWhenAttached.add(element);
	}
    }

    public void addNewClass() {
	addNewClass(new Point(FAR_AWAY, FAR_AWAY));

    }

    public void addNewClass(final Point initPoint) {
	if (dragOn) {
	    return;
	}
	final ClassArtifact newClass = new ClassArtifact("Class "
		+ ++classCount);
	if (fireNewArtifactEvent(newClass)) {
	    add(newClass);
	    newClass.moveTo(initPoint);
	    if (selected != null) {
		selected.unselect();
	    }
	    select(newClass.getGfxObject());
	    take(initPoint);
	    drag(initPoint);
	    dragOn = true;
	}
    }

    public void addNewLink(final UMLArtifact newSelected) {
	LinkArtifact newLink;
	Log.info("selected " + selected.getClass() + " newSelected "
		+ newSelected.getClass());
	if (activeLinking == RelationKind.OTHER) {
	    if (newSelected.getClass() == NoteArtifact.class) {
		newLink = new LinkNoteArtifact((NoteArtifact) newSelected,
			selected);
	    } else if (selected.getClass() == NoteArtifact.class) {
		newLink = new LinkNoteArtifact((NoteArtifact) selected,
			newSelected);
	    } else if (newSelected.getClass().getSuperclass() == RelationLinkArtifact.class
		    && selected.getClass() == ClassArtifact.class) {
		newLink = new LinkClassRelationArtifact(
			(ClassArtifact) selected,
			(RelationLinkArtifact) newSelected);
	    } else if (selected.getClass().getSuperclass() == RelationLinkArtifact.class
		    && newSelected.getClass() == ClassArtifact.class) {
		newLink = new LinkClassRelationArtifact(
			(ClassArtifact) newSelected,
			(RelationLinkArtifact) selected);
	    } else {
		newLink = null;
	    }
	} else if (selected.getClass() == ClassArtifact.class
		&& newSelected.getClass() == ClassArtifact.class) {
	    newLink = RelationLinkArtifact.makeLinkArtifact(
		    (ClassArtifact) newSelected, (ClassArtifact) selected,
		    activeLinking);
	} else {
	    newLink = null;
	}

	if (newLink != null && fireNewLinkEvent(newLink)) {
	    add(newLink);
	}
    }

    public void addNewNote() {
	addNewNote(new Point(FAR_AWAY, FAR_AWAY));

    }

    public void addNewNote(final Point initPoint) {
	if (dragOn) {
	    return;
	}
	final NoteArtifact newNote = new NoteArtifact("Note " + ++noteCount);
	if (fireNewArtifactEvent(newNote)) {
	    add(newNote);
	    newNote.moveTo(initPoint);
	    if (selected != null) {
		selected.unselect();
	    }
	    select(newNote.getGfxObject());
	    take(initPoint);
	    drag(initPoint);
	    dragOn = true;
	}
    }

    public void addUMLEventListener(final UMLEventListener uMLEventListener) {
	uMLEventListenerList.add(uMLEventListener);
    }

    public boolean fireNewArtifactEvent(final UMLArtifact umlArtifact) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : uMLEventListenerList) {
	    // If one is not ok then it's not ok !
	    isThisOk = listener.onNewUMLArtifact(umlArtifact) && isThisOk;
	}
	Log.trace("New Artifact event fired. Status : " + isThisOk);
	return isThisOk;

    }

    public boolean fireNewLinkEvent(final LinkArtifact newLink) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : uMLEventListenerList) {
	    isThisOk = listener.onNewLink(newLink) && isThisOk;
	}
	Log.trace("New Link event fired. Status : " + isThisOk);
	return isThisOk;
    }

    public void moveSelected(final Direction direction) {
	if (selected != null) {
	    selected.moveTo(new Point(selected.getLocation().getX()
		    + OptionsManager.getMovingStep()
		    * direction.getXDirection(), selected.getLocation().getY()
		    + OptionsManager.getMovingStep()
		    * direction.getYDirection()));
	}
    }

    public void remove(final UMLArtifact element) {
	removeRecursive(element);
	if (element.isALink()) {
	    ((LinkArtifact) element).removeCreatedDependency();
	}
    }

    public void removeSelected() {
	if (selected != null) {
	    remove(selected);
	}
    }

    public void removeUMLEventListener(final UMLEventListener uMLEventListener) {
	uMLEventListenerList.remove(uMLEventListener);
    }

    public void setDeleteMode() {
	isDeleting = true;
	CursorIconManager.setCursorIcon(PointerStyle.NOT_ALLOWED);
    }

    public void toLinkMode(final RelationKind linkType) {
	activeLinking = linkType;
	CursorIconManager.setCursorIcon(PointerStyle.CROSSHAIR);
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
	for (final UMLArtifact elementNotAdded : objectsToBeAddedWhenAttached) {
	    elementNotAdded.setCanvas(this);
	    final long t = System.currentTimeMillis();
	    GfxManager.getPlatform().addToCanvas(drawingCanvas,
		    elementNotAdded.initializeGfxObject(),
		    elementNotAdded.getLocation());
	    objects.put(elementNotAdded.getGfxObject(), elementNotAdded);
	    Log.debug("([" + (System.currentTimeMillis() - t)
		    + "ms]) to add queued " + elementNotAdded);
	}
	objectsToBeAddedWhenAttached.clear();
    }

    private void animateLinking(final Point location) {
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.HIGH)) {
	    if (movingLine != null) {
		GfxManager.getPlatform().removeFromCanvas(drawingCanvas, movingLine);
	    }
	    movingLine = GfxManager.getPlatform().buildLine(selected.getCenter(), location);
	    GfxManager.getPlatform().setStroke(movingLine,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStrokeStyle(movingLine, GfxStyle.DASH);
	    GfxManager.getPlatform().addToCanvas(drawingCanvas, movingLine, Point.getOrigin());
	    GfxManager.getPlatform().moveToBack(movingLine);
	}
    }

    private Point convertToRealPoint(final int x, final int y) {
	return new Point(x + RootPanel.getBodyElement().getScrollLeft()
		- getAbsoluteLeft(), y + RootPanel.getBodyElement().getScrollTop() - getAbsoluteTop());
    }

    private void drag(final Point location) {
	if (selected != null && selected.isDraggable()) {
	    if (outline == null) {
		outline = selected.getOutline();
		GfxManager.getPlatform().addToCanvas(drawingCanvas, outline, Point.getOrigin());
		selected.destroyGfxObjectWhithDependencies();
		Log.trace("Adding outline for " + selected);
		CursorIconManager.setCursorIcon(PointerStyle.MOVE);
	    }
	    final Point position = Point.substract(location, offset);
	    final Point translation = Point.substract(position, GfxManager.getPlatform().getLocationFor(outline));
	    position.translate(selected.getWidth() / 2, selected.getHeight()/ 2);
	    GfxManager.getPlatform().translate(outline, translation);
	    outlineDependencies(selected.getOutlineForDependencies(), position);

	}
    }

    private void drop(final Point location) {
	if (selected != null && selected.isDraggable()) {
	    if (outline != null) {
		GfxManager.getPlatform().removeFromCanvas(drawingCanvas,
			outline);
		outline = null;
		for (final Entry<Point, GfxObject> entry : outlineDependencies
			.entrySet()) {
		    GfxManager.getPlatform().removeFromCanvas(drawingCanvas,
			    entry.getValue());
		}
		outlineDependencies.clear();
	    }
	    
	    final Point f = Point.substract(location, offset);
	    
	    if (!f.equals(selected.getLocation())) {
		Log.trace("Dropping at " + f + " for " + selected);
		CursorIconManager.setCursorIcon(PointerStyle.AUTO);
		selected.moveTo(f);
		selected.rebuildGfxObject();
	    }

	}
    }

    private void dropRightMenu(final GfxObject gfxObject, final Point location) {
	select(gfxObject);
	final UMLArtifact elem = getUMLArtifact(gfxObject);
	ContextMenu contextMenu;
	if (elem != null) {
	    contextMenu = new ContextMenu(location, this, elem.getRightMenu());
	} else {
	    contextMenu = new ContextMenu(location, this);
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
	final UMLArtifact UMLArtifact = objects.get(currentGfxObject);
	if (UMLArtifact == null) {
	    Log.trace("Artifact not found");
	}
	return UMLArtifact;
    }

    private void initCanvas() {
	Log.trace("Adding Canvas");
	add(drawingCanvas, 0, 0);
	Log.trace("Adding object listener");
	GfxManager.getPlatform().addObjectListenerToCanvas(drawingCanvas,
		gfxObjectListener);
	Log.trace("Init canvas done");
    }

    private void outlineDependencies(
	    final ArrayList<Point> dependentArtifactLocations, final Point location) {

	if (!outlineDependencies.isEmpty()) {
	    for (final Entry<Point, GfxObject> entry : outlineDependencies
		    .entrySet()) {
		GfxManager.getPlatform().removeFromCanvas(drawingCanvas,
			entry.getValue());
	    }
	    outlineDependencies.clear();
	}

	for (final Point point : dependentArtifactLocations) {
	    final GfxObject line = GfxManager.getPlatform().buildLine(point, location);
	    GfxManager.getPlatform().setStroke(line,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
	    GfxManager.getPlatform().addToCanvas(drawingCanvas, line, Point.getOrigin());
	    GfxManager.getPlatform().moveToBack(line);
	    outlineDependencies.put(point, line);
	}

    }

    private void removeRecursive(final UMLArtifact element) {
	GfxManager.getPlatform().removeFromCanvas(drawingCanvas,
		element.getGfxObject());
	objects.remove(element.getGfxObject());
	element.setCanvas(null);
	if (element == selected) {
	    selected = null;
	}

	for (final Entry<LinkArtifact, UMLArtifact> entry : element
		.getDependentUMLArtifacts().entrySet()) {
	    if (entry.getValue().isALink()
		    && entry.getKey().getClass() != LinkNoteArtifact.class) {
		remove(entry.getValue());
	    }
	    entry.getValue().removeDependency(entry.getKey());
	    removeRecursive(entry.getKey());
	}

    }

    private void select(final GfxObject gfxObject) {
	final UMLArtifact newSelected = getUMLArtifact(gfxObject);
	Log.trace("Selecting : " + newSelected + " (" + gfxObject + ")");
	if (isDeleting && newSelected != null) {
	    remove(newSelected);
	    isDeleting = false;
	    CursorIconManager.setCursorIcon(PointerStyle.AUTO);
	    return;
	}

	if (newSelected == null) {
	    activeLinking = null;
	    if (movingLine != null) {
		GfxManager.getPlatform().removeFromCanvas(drawingCanvas,
			movingLine);
	    }
	    isDeleting = false;
	    CursorIconManager.setCursorIcon(PointerStyle.AUTO);
	}
	if (selected != null) {
	    if (activeLinking != null) {
		addNewLink(newSelected);
		activeLinking = null;
		if (movingLine != null) {
		    GfxManager.getPlatform().removeFromCanvas(drawingCanvas,
			    movingLine);
		}
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

    private void take(final Point point) {
	offset = Point.substract(point, selected.getLocation());
	Log.trace("Take at " + point + " with " + offset + " for "
		+ selected);
    }
}
