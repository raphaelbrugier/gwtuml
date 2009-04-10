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
    private RelationKind activeLinking = null;
    private boolean dragOn = false; // Represent the dragging state
    private final Widget drawingCanvas; // Drawing canvas
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
	    if (UMLCanvas.this.outline == null) {
		select(gfxObject);
		if (UMLCanvas.this.selected != null && UMLCanvas.this.selected.isDraggable()) {
		    take(realPoint);
		    UMLCanvas.this.dragOn = true;
		}
	    }
	}

	public void mouseMoved(final int x, final int y) {
	    final Point realPoint = convertToRealPoint(x, y);
	    if (UMLCanvas.this.dragOn) {
		drag(realPoint);
	    }
	    if (UMLCanvas.this.activeLinking != null && UMLCanvas.this.selected != null) {
		animateLinking(realPoint);

	    }
	}

	public void mouseReleased(final GfxObject gfxObject, final int x,
		final int y) {
	    final Point realPoint = convertToRealPoint(x, y);
	    if (UMLCanvas.this.dragOn) {
		drop(realPoint);
	    }
	    UMLCanvas.this.dragOn = false;
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
    private Point offset; // Represent the offset between object coordinates and mouse
    private GfxObject outline = null; // Outline is used for drawing while drag and drop
    private final HashMap<Point, GfxObject> outlineDependencies = new HashMap<Point, GfxObject>();
    private UMLArtifact selected = null; // Represent the current UMLArtifact
    private final ArrayList<UMLEventListener> uMLEventListenerList = new ArrayList<UMLEventListener>();

    /**
     * Constructor of an {@link UMLCanvas} with default size 
     *
     */
    public UMLCanvas() {
	Log.trace("Making Canvas");
	this.drawingCanvas = GfxManager.getPlatform().makeCanvas();
	setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH,
		GfxPlatform.DEFAULT_CANVAS_HEIGHT);
	initCanvas();
    }

    /**
     * Constructor of an {@link UMLCanvas} with the specified size 
     *
     * @param width The uml canvas width
     * @param height The uml canvas height
     */
    public UMLCanvas(final int width, final int height) {
	Log.trace("Making " + width + " x " + height + " Canvas");
	this.drawingCanvas = GfxManager.getPlatform().makeCanvas(width, height,
		ThemeManager.getBackgroundColor());
	setPixelSize(width, height);
	initCanvas();
    }

    /**
     * Add an {@link UMLArtifact} to this canvas
     * 
     * @param artifact The {@link UMLArtifact} to add
     */
    public void add(final UMLArtifact artifact) {
	if (artifact == null) {
	    Log.error("Adding null element to canvas");
	    return;
	}
	if (isAttached()) {
	    artifact.setCanvas(this);
	    final long t = System.currentTimeMillis();
	    GfxManager.getPlatform().addToCanvas(this.drawingCanvas,
		    artifact.initializeGfxObject(),
		    artifact.getLocation());
	    this.objects.put(artifact.getGfxObject(), artifact);
	    Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to add "
		    + artifact);
	} else {
	    Log.trace("Canvas not attached, queuing " + artifact);
	    this.objectsToBeAddedWhenAttached.add(artifact);
	}
    }

    /**
     * Add an {@link UMLEventListener} to this canvas
     * 
     * @param uMLEventListener The {@link UMLEventListener} to add to this canvas
     */
    public void addUMLEventListener(final UMLEventListener uMLEventListener) {
	this.uMLEventListenerList.add(uMLEventListener);
    }
    /**
     * Remove the specified artifact from the canvas 
     * @param artifact
     */
    public void remove(final UMLArtifact artifact) {
	removeRecursive(artifact);
	if (artifact.isALink()) {
	    ((LinkArtifact) artifact).removeCreatedDependency();
	}
    }

     /**
     *Remove the specified  {@link UMLEventListener} from this canvas
     * 
     * @param uMLEventListener The {@link UMLEventListener} to remove from this canvas
     */
    public void removeUMLEventListener(final UMLEventListener uMLEventListener) {
	this.uMLEventListenerList.remove(uMLEventListener);
    }

    /**
     * Add a new class with default values to this canvas to an invisible location (to hide it)
     */
    void addNewClass() {
	addNewClass(new Point(FAR_AWAY, FAR_AWAY));

    }

    /**
     * Add a new class with default values to this canvas at the specified location
     * 
     * @param location The initial class location
     * 
     */
    void addNewClass(final Point location) {
	if (this.dragOn) {
	    return;
	}
	final ClassArtifact newClass = new ClassArtifact("Class "
		+ ++classCount);
	if (fireNewArtifactEvent(newClass)) {
	    add(newClass);
	    newClass.moveTo(location);
	    if (this.selected != null) {
		this.selected.unselect();
	    }
	    select(newClass.getGfxObject());
	    take(location);
	    drag(location);
	    this.dragOn = true;
	}
    }

    void addNewLink(final UMLArtifact newSelected) {
	LinkArtifact newLink;
	Log.info("selected " + this.selected.getClass() + " newSelected "
		+ newSelected.getClass());
	if (this.activeLinking == RelationKind.OTHER) {
	    if (newSelected.getClass() == NoteArtifact.class) {
		newLink = new LinkNoteArtifact((NoteArtifact) newSelected,
			this.selected);
	    } else if (this.selected.getClass() == NoteArtifact.class) {
		newLink = new LinkNoteArtifact((NoteArtifact) this.selected,
			newSelected);
	    } else if (newSelected.getClass().getSuperclass() == RelationLinkArtifact.class
		    && this.selected.getClass() == ClassArtifact.class) {
		newLink = new LinkClassRelationArtifact(
			(ClassArtifact) this.selected,
			(RelationLinkArtifact) newSelected);
	    } else if (this.selected.getClass().getSuperclass() == RelationLinkArtifact.class
		    && newSelected.getClass() == ClassArtifact.class) {
		newLink = new LinkClassRelationArtifact(
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

    void addNewNote() {
	addNewNote(new Point(FAR_AWAY, FAR_AWAY));

    }

    void addNewNote(final Point initPoint) {
	if (this.dragOn) {
	    return;
	}
	final NoteArtifact newNote = new NoteArtifact("Note " + ++noteCount);
	if (fireNewArtifactEvent(newNote)) {
	    add(newNote);
	    newNote.moveTo(initPoint);
	    if (this.selected != null) {
		this.selected.unselect();
	    }
	    select(newNote.getGfxObject());
	    take(initPoint);
	    drag(initPoint);
	    this.dragOn = true;
	}
    }

    boolean fireNewArtifactEvent(final UMLArtifact umlArtifact) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : this.uMLEventListenerList) {
	    // If one is not ok then it's not ok !
	    isThisOk = listener.onNewUMLArtifact(umlArtifact) && isThisOk;
	}
	Log.trace("New Artifact event fired. Status : " + isThisOk);
	return isThisOk;

    }

    boolean fireNewLinkEvent(final LinkArtifact newLink) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : this.uMLEventListenerList) {
	    isThisOk = listener.onNewLink(newLink) && isThisOk;
	}
	Log.trace("New Link event fired. Status : " + isThisOk);
	return isThisOk;
    }

    void moveSelected(final Direction direction) {
	if (this.selected != null) {
	    this.selected.moveTo(new Point(this.selected.getLocation().getX()
		    + OptionsManager.getMovingStep()
		    * direction.getXDirection(), this.selected.getLocation().getY()
		    + OptionsManager.getMovingStep()
		    * direction.getYDirection()));
	}
    }
    
    void removeSelected() {
	if (this.selected != null) {
	    remove(this.selected);
	}
    }

    void setDeleteMode() {
	this.isDeleting = true;
	CursorIconManager.setCursorIcon(PointerStyle.NOT_ALLOWED);
    }

    void toLinkMode(final RelationKind linkType) {
	this.activeLinking = linkType;
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
	for (final UMLArtifact elementNotAdded : this.objectsToBeAddedWhenAttached) {
	    elementNotAdded.setCanvas(this);
	    final long t = System.currentTimeMillis();
	    GfxManager.getPlatform().addToCanvas(this.drawingCanvas,
		    elementNotAdded.initializeGfxObject(),
		    elementNotAdded.getLocation());
	    this.objects.put(elementNotAdded.getGfxObject(), elementNotAdded);
	    Log.debug("([" + (System.currentTimeMillis() - t)
		    + "ms]) to add queued " + elementNotAdded);
	}
	this.objectsToBeAddedWhenAttached.clear();
    }

    private void animateLinking(final Point location) {
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.HIGH)) {
	    if (this.movingLine != null) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, this.movingLine);
	    }
	    this.movingLine = GfxManager.getPlatform().buildLine(this.selected.getCenter(), location);
	    GfxManager.getPlatform().setStroke(this.movingLine,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStrokeStyle(this.movingLine, GfxStyle.DASH);
	    GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.movingLine, Point.getOrigin());
	    GfxManager.getPlatform().moveToBack(this.movingLine);
	}
    }

    private Point convertToRealPoint(final int x, final int y) {
	return new Point(x + RootPanel.getBodyElement().getScrollLeft()
		- getAbsoluteLeft(), y + RootPanel.getBodyElement().getScrollTop() - getAbsoluteTop());
    }

    private void drag(final Point location) {
	if (this.selected != null && this.selected.isDraggable()) {
	    if (this.outline == null) {
		this.outline = this.selected.getOutline();
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.outline, Point.getOrigin());
		this.selected.destroyGfxObjectWhithDependencies();
		Log.trace("Adding outline for " + this.selected);
		CursorIconManager.setCursorIcon(PointerStyle.MOVE);
	    }
	    final Point position = Point.subtract(location, this.offset);
	    final Point translation = Point.subtract(position, GfxManager.getPlatform().getLocationFor(this.outline));
	    position.translate(this.selected.getWidth() / 2, this.selected.getHeight()/ 2);
	    GfxManager.getPlatform().translate(this.outline, translation);
	    outlineDependencies(this.selected.getOutlineForDependencies(), position);

	}
    }

    private void drop(final Point location) {
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
	    
	    final Point f = Point.subtract(location, this.offset);
	    
	    if (!f.equals(this.selected.getLocation())) {
		Log.trace("Dropping at " + f + " for " + this.selected);
		CursorIconManager.setCursorIcon(PointerStyle.AUTO);
		this.selected.moveTo(f);
		this.selected.rebuildGfxObject();
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

    private void outlineDependencies(
	    final ArrayList<Point> dependentArtifactLocations, final Point location) {

	if (!this.outlineDependencies.isEmpty()) {
	    for (final Entry<Point, GfxObject> entry : this.outlineDependencies
		    .entrySet()) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
			entry.getValue());
	    }
	    this.outlineDependencies.clear();
	}

	for (final Point point : dependentArtifactLocations) {
	    final GfxObject line = GfxManager.getPlatform().buildLine(point, location);
	    GfxManager.getPlatform().setStroke(line,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
	    GfxManager.getPlatform().addToCanvas(this.drawingCanvas, line, Point.getOrigin());
	    GfxManager.getPlatform().moveToBack(line);
	    this.outlineDependencies.put(point, line);
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

    private void take(final Point point) {
	this.offset = Point.subtract(point, this.selected.getLocation());
	Log.trace("Take at " + point + " with " + this.offset + " for "
		+ this.selected);
    }
}
