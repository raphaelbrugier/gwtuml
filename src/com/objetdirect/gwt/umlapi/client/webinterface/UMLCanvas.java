package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.UMLEventListener;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.CursorIconManager.PointerStyle;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLCanvas extends AbsolutePanel {

    private static long classCount = 1;
    private static long objectCount = 1;
    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    private long noteCount;
    private RelationKind activeLinking;
    private Point selectBoxStartPoint; 
    private GfxObject selectBox;
    private final Widget drawingCanvas; // Drawing canvas
    private enum DragAndDropState {
	TAKING, DRAGGING, NONE, PREPARING_SELECT_BOX, SELECT_BOX;
    }
    private DragAndDropState dragAndDropState = DragAndDropState.NONE;

    private Point currentMousePosition = Point.getOrigin();
    private Point canvasOffset = Point.getOrigin();
    /**
     * Getter for the canvasOffset
     *
     * @return the canvasOffset
     */
    public Point getCanvasOffset() {
	return this.canvasOffset;
    }


    private HashMap<UMLArtifact, ArrayList<Point>> previouslySelectedArtifacts;
    private UMLDiagram uMLDiagram;
    private boolean isMouseEnabled = true;

    void setMouseEnabled(boolean isMouseEnabled) {
	this.isMouseEnabled = isMouseEnabled;
    }


    private final GfxObjectListener gfxObjectListener = new GfxObjectListener() {

	@Override
	public void mouseDoubleClicked(GfxObject graphicObject, Event event) {
	    if(UMLCanvas.this.isMouseEnabled) {
		Mouse.doubleClick(graphicObject, new Point(event.getClientX(), event.getClientY()), event.getButton(), event.getCtrlKey(), event.getAltKey(), event.getShiftKey(), event.getMetaKey());
	    }
	}

	@Override
	public void mouseMoved(Event event) {
	    if(UMLCanvas.this.isMouseEnabled) {
		Mouse.move(new Point(event.getClientX(), event.getClientY()), event.getButton(), event.getCtrlKey(), event.getAltKey(), event.getShiftKey(), event.getMetaKey());
	    }
	}

	@Override
	public void mousePressed(GfxObject graphicObject, Event event) {
	    if(UMLCanvas.this.isMouseEnabled) {
		Mouse.press(graphicObject, new Point(event.getClientX(), event.getClientY()), event.getButton(), event.getCtrlKey(), event.getAltKey(), event.getShiftKey(), event.getMetaKey());
	    }
	}

	@Override
	public void mouseReleased(GfxObject graphicObject, Event event) {
	    if(UMLCanvas.this.isMouseEnabled) {
		Mouse.release(graphicObject, new Point(event.getClientX(), event.getClientY()), event.getButton(), event.getCtrlKey(), event.getAltKey(), event.getShiftKey(), event.getMetaKey());
	    }
	}

    };

    private boolean mouseIsPressed = false; // Manage mouse state when releasing outside the listener


    void mouseDoubleClicked(final GfxObject gfxObject, final Point location) {
	editItem(gfxObject);
    }

    void mouseLeftPressed(final GfxObject gfxObject, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
	if(this.mouseIsPressed) return;
	final Point realPoint = convertToRealPoint(location);
	this.mouseIsPressed = true;
	if (UMLCanvas.this.dragAndDropState == DragAndDropState.DRAGGING) {
	    return;
	}
	if(gfxObject != null) {
	    UMLCanvas.this.dragAndDropState = DragAndDropState.TAKING;
	    UMLCanvas.this.dragOffset = realPoint.clonePoint();
	    CursorIconManager.setCursorIcon(PointerStyle.MOVE);		
	} else {
	    UMLCanvas.this.selectBoxStartPoint = realPoint.clonePoint();
	    UMLCanvas.this.dragAndDropState = DragAndDropState.PREPARING_SELECT_BOX;
	}
	doSelection(gfxObject, isCtrlDown, isShiftDown);
    }

    @SuppressWarnings("fallthrough")
    void mouseMoved(final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
	final Point realPoint = convertToRealPoint(location);
	UMLCanvas.this.currentMousePosition = realPoint;
	switch(UMLCanvas.this.dragAndDropState) {
	case TAKING:
	    take();
	    UMLCanvas.this.dragAndDropState = DragAndDropState.DRAGGING;
	case DRAGGING:
	    drag(realPoint);
	    break;
	case PREPARING_SELECT_BOX:
	    UMLCanvas.this.dragAndDropState = DragAndDropState.SELECT_BOX;
	    UMLCanvas.this.previouslySelectedArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(UMLCanvas.this.selectedArtifacts);
	case SELECT_BOX:
	    boxSelector(UMLCanvas.this.selectBoxStartPoint, realPoint, isCtrlDown, isShiftDown);
	}

	if (UMLCanvas.this.activeLinking != null && !UMLCanvas.this.selectedArtifacts.isEmpty()) {
	    animateLinking(realPoint);

	}
    }


    @SuppressWarnings("fallthrough")
    void mouseReleased(final GfxObject gfxObject, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
	if(!this.mouseIsPressed) return;
	final Point realPoint = convertToRealPoint(location);
	this.mouseIsPressed = false;	    
	if(UMLCanvas.this.dragAndDropState == DragAndDropState.TAKING) {
	    unselectOnRelease(gfxObject, isCtrlDown, isShiftDown);
	}
	switch(UMLCanvas.this.dragAndDropState) {
	case SELECT_BOX:
	    if(UMLCanvas.this.selectBox != null) {
		GfxManager.getPlatform().removeFromCanvas(UMLCanvas.this.drawingCanvas, UMLCanvas.this.selectBox);
	    }
	    UMLCanvas.this.dragAndDropState = DragAndDropState.NONE;
	    break;
	case DRAGGING:
	    drop(realPoint);
	case TAKING:
	    CursorIconManager.setCursorIcon(PointerStyle.AUTO);
	default :
	    UMLCanvas.this.dragAndDropState = DragAndDropState.NONE;
	}

    }

    void mouseRightPressed(final GfxObject gfxObject, final Point location) {
	final Point realPoint = convertToRealPoint(location);
	dropRightMenu(gfxObject, realPoint);
    }


    private final GfxObject movingLines = GfxManager.getPlatform().buildVirtualGroup();
    private final GfxObject movingOutlineDependencies = GfxManager.getPlatform().buildVirtualGroup();
    private final GfxObject outlines = GfxManager.getPlatform().buildVirtualGroup();
    private final GfxObject allObjects = GfxManager.getPlatform().buildVirtualGroup();
    private final Map<GfxObject, UMLArtifact> objects = new HashMap<GfxObject, UMLArtifact>();	// Map of UMLArtifact with corresponding Graphical objects (group)
    private final Set<UMLArtifact> objectsToBeAddedWhenAttached = new LinkedHashSet<UMLArtifact>();

    private final HashMap<UMLArtifact, ArrayList<Point>> selectedArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(); // Represent the selected UMLArtifacts and his moving dependencies points 
    private final ArrayList<UMLEventListener> uMLEventListenerList = new ArrayList<UMLEventListener>();
    private Point dragOffset;
    private Point totalDragShift = Point.getOrigin();

    /**
     * Constructor of an {@link UMLCanvas} with default size 
     * 
     * @param uMLDiagram The {@link UMLDiagram} this {@link UMLCanvas} is drawing 
     *
     */
    public UMLCanvas(UMLDiagram uMLDiagram) {
	Log.trace("Making Canvas");
	this.drawingCanvas = GfxManager.getPlatform().makeCanvas();
	setPixelSize(GfxPlatform.DEFAULT_CANVAS_WIDTH,
		GfxPlatform.DEFAULT_CANVAS_HEIGHT);
	initCanvas();
	this.uMLDiagram = uMLDiagram;
    }

    /**
     * Constructor of an {@link UMLCanvas} with the specified size 
     * 
     * @param uMLDiagram The {@link UMLDiagram} this {@link UMLCanvas} is drawing 
     * @param width The uml canvas width
     * @param height The uml canvas height
     */
    public UMLCanvas(UMLDiagram uMLDiagram, final int width, final int height) {
	Log.trace("Making " + width + " x " + height + " Canvas");
	this.drawingCanvas = GfxManager.getPlatform().makeCanvas(width, height,
		ThemeManager.getTheme().getCanvasColor());
	this.drawingCanvas.getElement().setAttribute("oncontextmenu", "return false");

	setPixelSize(width, height);
	initCanvas();
	this.uMLDiagram = uMLDiagram;
    }

    /**
     * Add an {@link UMLArtifact} to this canvas
     * 
     * @param artifact The {@link UMLArtifact} to add
     */
    public void add(final UMLArtifact artifact) {
	if (artifact == null) {
	    Log.info("Adding null element to canvas");
	    return;
	}
	if (isAttached()) {
	    artifact.setCanvas(this);
	    final long t = System.currentTimeMillis();
	    GfxManager.getPlatform().addToVirtualGroup(this.allObjects, artifact.initializeGfxObject());

	    GfxManager.getPlatform().translate(artifact.getGfxObject(), artifact.getLocation());
	    this.objects.put(artifact.getGfxObject(), artifact);
	    Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to add "
		    + artifact);

	} else {
	    Log.debug("Canvas not attached, queuing " + artifact);
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
     * @param umlArtifact
     */
    public void remove(final UMLArtifact umlArtifact) {
	if(fireDeleteArtifactEvent(umlArtifact)) {
	    removeRecursive(umlArtifact);
	    if (umlArtifact.isALink()) {
		((LinkArtifact) umlArtifact).removeCreatedDependency();
	    }
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
    public void addNewClass() {
	addNewClass(this.currentMousePosition);

    }

    /**
     * Add a new class with default values to this canvas at the specified location
     * 
     * @param location The initial class location
     * 
     */
    void addNewClass(final Point location) {
	if (this.dragAndDropState != DragAndDropState.NONE) {
	    return;
	}
	final ClassArtifact newClass = new ClassArtifact("Class "
		+ ++classCount);
	if (fireNewArtifactEvent(newClass)) {
	    add(newClass);
	    newClass.moveTo(Point.substract(location, this.canvasOffset));
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		selectedArtifact.unselect();
	    }
	    this.selectedArtifacts.clear();
	    doSelection(newClass.getGfxObject(), false, false);
	    this.selectedArtifacts.put(newClass, new ArrayList<Point>());
	    this.dragOffset = location;
	    CursorIconManager.setCursorIcon(PointerStyle.MOVE);
	    this.dragAndDropState = DragAndDropState.TAKING;
	}
    }

    /**
     * Add a new object with default values to this canvas to an invisible location (to hide it)
     */
    public void addNewObject() {
	addNewObject(this.currentMousePosition);

    }

    /**
     * Add a new object with default values to this canvas at the specified location
     * 
     * @param location The initial class location
     * 
     */
    void addNewObject(final Point location) {
	if (this.dragAndDropState != DragAndDropState.NONE) {
	    return;
	}
	final ObjectArtifact newObject = new ObjectArtifact("obj"+ ++objectCount +":Object "
		+ objectCount);
	if (fireNewArtifactEvent(newObject)) {
	    add(newObject);
	    newObject.moveTo(Point.substract(location, this.canvasOffset));
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		selectedArtifact.unselect();
	    }
	    this.selectedArtifacts.clear();
	    doSelection(newObject.getGfxObject(), false, false);
	    this.selectedArtifacts.put(newObject, new ArrayList<Point>());
	    this.dragOffset = location;
	    CursorIconManager.setCursorIcon(PointerStyle.MOVE);
	    this.dragAndDropState = DragAndDropState.TAKING;
	}
    }

    void addNewLink(final UMLArtifact newSelected) {
	boolean isOneLinkOk = false;
	for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
	    LinkArtifact newLink = LinkArtifact.makeLinkBetween(selectedArtifact, newSelected, this.activeLinking);
	    isOneLinkOk = isOneLinkOk || (newLink != null);
	    add(newLink);
	}
	if(isOneLinkOk) linkingModeOff();
    }

    void addNewNote() {
	addNewNote(this.currentMousePosition);

    }

    void addNewNote(final Point location) {
	if (this.dragAndDropState != DragAndDropState.NONE) {
	    return;
	}
	final NoteArtifact newNote = new NoteArtifact("Note " + ++this.noteCount);
	if (fireNewArtifactEvent(newNote)) {
	    add(newNote);
	    newNote.moveTo(Point.substract(location, this.canvasOffset));
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		selectedArtifact.unselect();
	    }
	    this.selectedArtifacts.clear();
	    doSelection(newNote.getGfxObject(), false, false);
	    this.selectedArtifacts.put(newNote, new ArrayList<Point>());
	    this.dragOffset = location; 
	    this.dragAndDropState = DragAndDropState.TAKING;
	    CursorIconManager.setCursorIcon(PointerStyle.MOVE);
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

    boolean fireEditArtifactEvent(final UMLArtifact umlArtifact) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : this.uMLEventListenerList) {
	    // If one is not ok then it's not ok !
	    isThisOk = listener.onEditUMLArtifact(umlArtifact) && isThisOk;
	}
	Log.trace("New Artifact event fired. Status : " + isThisOk);
	return isThisOk;
    }	

    boolean fireDeleteArtifactEvent(final UMLArtifact umlArtifact) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : this.uMLEventListenerList) {
	    // If one is not ok then it's not ok !
	    isThisOk = listener.onDeleteUMLArtifact(umlArtifact) && isThisOk;
	}
	Log.trace("Delete artifact event fired. Status : " + isThisOk);
	return isThisOk;
    }

    boolean fireLinkKindChange(final LinkArtifact newLink, final RelationKind oldKind, final RelationKind newKind) {
	boolean isThisOk = true;
	for (final UMLEventListener listener : this.uMLEventListenerList) {
	    isThisOk = listener.onLinkKindChange(newLink, oldKind, newKind) && isThisOk;
	}
	Log.trace("Link kind chage event fired. Status : " + isThisOk);
	return isThisOk;
    }
    Widget getDrawingCanvas() {
	return this.drawingCanvas;
    }

    void moveSelected(final Direction direction) {
	if (!this.selectedArtifacts.isEmpty()) {
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		if(selectedArtifact.isDraggable()) {		
		    selectedArtifact.moveTo(new Point(selectedArtifact.getLocation().getX()
			    + OptionsManager.get("MovingStep")
			    * direction.getXDirection(), selectedArtifact.getLocation().getY()
			    + OptionsManager.get("MovingStep")
			    * direction.getYDirection()));
		    selectedArtifact.rebuildGfxObject();
		    selectedArtifact.select(true);
		}
	    }
	}
    }

    void removeSelected() {
	if (!this.selectedArtifacts.isEmpty()) {
	    final HashMap<UMLArtifact, ArrayList<Point>> selectedBeforeRemovalArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(this.selectedArtifacts);
	    for(final UMLArtifact selectedArtifact : selectedBeforeRemovalArtifacts.keySet()) {
		remove(selectedArtifact);
	    }
	}
    }

    /**
     * Getter for the uMLDiagram
     *
     * @return the uMLDiagram
     */
    public UMLDiagram getUMLDiagram() {
	return this.uMLDiagram;
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
	    Log.debug("Adding queued " + elementNotAdded);
	    elementNotAdded.setCanvas(this);
	    final long t = System.currentTimeMillis();
	    GfxManager.getPlatform().addToVirtualGroup(this.allObjects, elementNotAdded.initializeGfxObject());
	    GfxManager.getPlatform().translate(elementNotAdded.getGfxObject(), elementNotAdded.getLocation());
	    this.objects.put(elementNotAdded.getGfxObject(), elementNotAdded);
	    Log.debug("([" + (System.currentTimeMillis() - t)
		    + "ms]) to add queued " + elementNotAdded);
	}
	this.objectsToBeAddedWhenAttached.clear();
    }

    private void animateLinking(final Point location) {
	if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
	    GfxManager.getPlatform().clearVirtualGroup(this.movingLines);
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		GfxObject movingLine = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(), Point.substract(location, this.canvasOffset));
		GfxManager.getPlatform().addToVirtualGroup(this.movingLines, movingLine);
		GfxManager.getPlatform().setStrokeStyle(movingLine, GfxStyle.LONGDASHDOTDOT);
	    }
	    GfxManager.getPlatform().setStroke(this.movingLines, ThemeManager.getTheme().getHighlightedForegroundColor(), 1);	    
	    GfxManager.getPlatform().moveToBack(this.movingLines);
	}
    }

    private Point convertToRealPoint(final Point location) {
	return Point.add(location, new Point( RootPanel.getBodyElement().getScrollLeft()
		- getAbsoluteLeft(), RootPanel.getBodyElement().getScrollTop() - getAbsoluteTop()));
    }


    private void take() {
	GfxManager.getPlatform().translate(this.outlines, Point.substract(this.canvasOffset,GfxManager.getPlatform().getLocationFor(this.outlines)));
	final HashMap<UMLArtifact, UMLArtifact> alreadyAdded = new HashMap<UMLArtifact, UMLArtifact>();
	for(final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : this.selectedArtifacts.entrySet()) {
	    final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
	    Scheduler.cancel(selectedArtifact);
	    if (selectedArtifact.isDraggable()) {
		GfxObject outline = selectedArtifact.getOutline();
		GfxManager.getPlatform().addToVirtualGroup(this.outlines, outline);
		GfxManager.getPlatform().translate(outline, selectedArtifact.getLocation());
		selectedArtifact.destroyGfxObjectWhithDependencies();
		Log.trace("Adding outline for " + selectedArtifact);
		//Drawing lines only translating during with drag
		if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
		    selectedArtifactEntry.getValue().clear();	

		    for (UMLArtifact dependantArtifact : selectedArtifact.getDependentUMLArtifacts().values()) {
			if(this.selectedArtifacts.containsKey(dependantArtifact)) {
			    if(alreadyAdded.get(selectedArtifact) == null 
				    || !alreadyAdded.get(selectedArtifact).equals(dependantArtifact)) {
				GfxObject outlineDependency  = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(), dependantArtifact.getCenter());
				GfxManager.getPlatform().addToVirtualGroup(this.outlines, outlineDependency);
				GfxManager.getPlatform().setStrokeStyle(outlineDependency, GfxStyle.DASH);
				GfxManager.getPlatform().moveToBack(outlineDependency);
				alreadyAdded.put(dependantArtifact, selectedArtifact);
			    }
			} else {
			    selectedArtifactEntry.getValue().add(dependantArtifact.getCenter());
			}
		    }		
		}	
	    }
	}
    }
    private void drag(final Point location) {
	Point shift = Point.substract(location, this.dragOffset);
	this.totalDragShift.translate(shift);

	if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
	    GfxManager.getPlatform().clearVirtualGroup(this.movingOutlineDependencies);
	}
	for(final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : this.selectedArtifacts.entrySet()) {
	    final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
	    if (selectedArtifact.isDraggable()) {
		Point outlineOfSelectedCenter = Point.add(selectedArtifact.getCenter(), this.totalDragShift);
		if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
		    for (Point dependantArtifactCenter : selectedArtifactEntry.getValue()) {
			GfxObject outlineDependency  = GfxManager.getPlatform().buildLine(outlineOfSelectedCenter, dependantArtifactCenter);
			GfxManager.getPlatform().addToVirtualGroup(this.movingOutlineDependencies, outlineDependency);
			GfxManager.getPlatform().setStrokeStyle(outlineDependency, GfxStyle.DASH);
		    }
		    GfxManager.getPlatform().setStroke(this.movingOutlineDependencies, ThemeManager.getTheme().getHighlightedForegroundColor(), 1);
		    GfxManager.getPlatform().moveToBack(this.movingOutlineDependencies);
		}
		GfxManager.getPlatform().setStroke(this.outlines, ThemeManager.getTheme().getHighlightedForegroundColor(), 1);
	    }
	}
	GfxManager.getPlatform().translate(this.outlines, shift);
	this.dragOffset = location.clonePoint();
    }

    private void drop(final Point location) {
	for(UMLArtifact selectedArtifact : UMLCanvas.this.selectedArtifacts.keySet()) {
	    if (selectedArtifact.isDraggable()) {
		selectedArtifact.moveTo(Point.add(selectedArtifact.getLocation(), this.totalDragShift));
		selectedArtifact.rebuildGfxObject();
		selectedArtifact.select(true);
	    }
	}
	this.totalDragShift = Point.getOrigin();
	GfxManager.getPlatform().clearVirtualGroup(this.outlines);
	GfxManager.getPlatform().clearVirtualGroup(this.movingOutlineDependencies);
    }

    private void dropRightMenu(final GfxObject gfxObject, final Point location) {
	doSelection(gfxObject, false, false);
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
	final UMLArtifact uMLArtifact = getUMLArtifact(gfxObject);
	if (uMLArtifact != null) {
	    Log.trace("Edit started on " + uMLArtifact);
	    if(fireEditArtifactEvent(uMLArtifact)) {
		uMLArtifact.edit(gfxObject);
	    }
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
	while (gfxOParentGroup != null && !gfxOParentGroup.equals(this.allObjects)) {
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
	this.noteCount = 0;
	GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.allObjects, Point.getOrigin());
	GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.movingLines, Point.getOrigin());
	GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.outlines, Point.getOrigin());
	GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.movingOutlineDependencies, Point.getOrigin());
	Log.trace("Init canvas done");
    }

    private void removeRecursive(final UMLArtifact element) {
	GfxManager.getPlatform().removeFromVirtualGroup(this.allObjects,
		element.getGfxObject(), false);
	this.objects.remove(element.getGfxObject());
	element.setCanvas(null);
	this.selectedArtifacts.remove(element);

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

    private void doSelection(final GfxObject gfxObject, boolean isCtrlKeyDown, boolean isShiftKeyDown) {
	final UMLArtifact newSelected = getUMLArtifact(gfxObject);
	Log.trace("Selecting : " + newSelected + " (" + gfxObject + ")");
	//New selection is null -> deselecting all selected artifacts and disabling linking mode
	if(newSelected == null) {
	    linkingModeOff();
	    deselectAllArtifacts();   
	} else { // New selection is not null
	    if(this.selectedArtifacts.containsKey(newSelected)) { //New selection is already selected -> deselecting it if shift is not down
		if(this.selectedArtifacts.size() != 1) { //New selection isn't the only one selected
		    if(isCtrlKeyDown) { //If ctrl down deselecting only this one
			deselectArtifact(newSelected);
		    } 
		}
	    } else { //New selection is not selected
		if (this.selectedArtifacts.isEmpty()) { //If nothing is selected -> selecting
		    selectArtifact(newSelected);
		} else { //Other artifacts are selected
		    if (this.activeLinking != null) {//if linking mode on
			addNewLink(newSelected);
		    }
		    if(!isCtrlKeyDown && !isShiftKeyDown) { //If selection is not greedy ->deselect all
			deselectAllArtifacts();
		    }
		    selectArtifact(newSelected);
		}
	    }
	}
    }
    private void selectArtifact(UMLArtifact toSelect) {
	this.selectedArtifacts.put(toSelect, new ArrayList<Point>());
	toSelect.select(true);

    }
    private void deselectArtifact(UMLArtifact toDeselect) {
	this.selectedArtifacts.remove(toDeselect);
	toDeselect.unselect();	
    }

    private void deselectAllArtifacts() {

	for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
	    selectedArtifact.unselect();
	}
	this.selectedArtifacts.clear();	
    }

    private void unselectOnRelease(final GfxObject gfxObject, boolean isCtrlKeyDown, boolean isShiftKeyDown) {	

	final UMLArtifact newSelected = getUMLArtifact(gfxObject);
	if(newSelected != null) {
	    if(this.selectedArtifacts.containsKey(newSelected)) {
		if(this.selectedArtifacts.size() != 1) {
		    if(!isShiftKeyDown && !isCtrlKeyDown) { //Doing what have not been done on mouse press to allow multiple dragging
			deselectAllArtifacts();
			selectArtifact(newSelected);
		    }
		}
	    }
	}
    }
    private void boxSelector(final Point startPoint, final Point location, boolean isCtrlDown, boolean isShiftDown) {
	if(this.selectBox != null) {
	    GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, this.selectBox);
	}

	this.selectBox = GfxManager.getPlatform().buildPath();
	GfxManager.getPlatform().moveTo(this.selectBox, startPoint);
	GfxManager.getPlatform().lineTo(this.selectBox, new Point(location.getX(), startPoint.getY()));
	GfxManager.getPlatform().lineTo(this.selectBox, location);
	GfxManager.getPlatform().lineTo(this.selectBox, new Point(startPoint.getX(), location.getY()));
	GfxManager.getPlatform().lineTo(this.selectBox, startPoint);
	GfxManager.getPlatform().addToCanvas(this.drawingCanvas, this.selectBox, Point.getOrigin());
	GfxManager.getPlatform().setStroke(this.selectBox, ThemeManager.getTheme().getSelectBoxForegroundColor(), 2);
	GfxManager.getPlatform().setFillColor(this.selectBox, ThemeManager.getTheme().getSelectBoxBackgroundColor());
	GfxManager.getPlatform().setOpacity(this.selectBox, ThemeManager.getTheme().getSelectBoxBackgroundColor().getAlpha(), true);
	Point min = Point.substract(Point.min(startPoint, location), this.canvasOffset);
	Point max = Point.substract(Point.max(startPoint, location), this.canvasOffset);

	for (UMLArtifact artifact : this.objects.values()) {
	    if(artifact.isDraggable()) {
		if(isIn(artifact.getLocation(), Point.add(artifact.getLocation(), new Point(artifact.getWidth(), artifact.getHeight())), min, max)) {	    
		    boxSelect(artifact, !(this.previouslySelectedArtifacts.containsKey(artifact) && isCtrlDown));
		} else {
		    boxSelect(artifact, (isShiftDown || isCtrlDown) && this.previouslySelectedArtifacts.containsKey(artifact));
		}
	    }
	}
    }
    private void boxSelect(UMLArtifact artifact, boolean isSelecting) {
	if(isSelecting) {
	    this.selectedArtifacts.put(artifact, new ArrayList<Point>());
	    artifact.select(false);
	} else {
	    this.selectedArtifacts.remove(artifact);
	    artifact.unselect();
	}
    }
    private boolean isIn(Point artifactMin, Point artifactMax, Point selectMin, Point selectMax) {
	return (selectMax.isSuperiorTo(artifactMin) && artifactMax.isSuperiorTo(selectMin));
    }
    private void linkingModeOff() {
	this.activeLinking = null;
	GfxManager.getPlatform().clearVirtualGroup(this.movingLines);
    }
    GfxObject getArtifactAt(Point location) {
	for (UMLArtifact artifact : this.objects.values()) {
	    if(isIn(artifact.getLocation(), Point.add(artifact.getLocation(), new Point(artifact.getWidth(), artifact.getHeight())), location, location)) {
		Log.info("Artifact : " + artifact + " found"); 
		return artifact.getGfxObject();
	    }
	}
	Log.info("No Artifact Found !"); 
	return null;
    }

    /**
     * Create a diagram from the URL {@link String} previously generated by {@link UMLCanvas#toUrl()}
     * 
     * @param url The {@link String} previously generated by {@link UMLCanvas#toUrl()}
     */
    public void fromURL(String url) {
	String diagram = UMLDrawerHelper.decodeBase64(url);
	diagram = diagram.substring(0, diagram.lastIndexOf(";"));
	String[] diagramArtifacts = diagram.split(";");
	for (final String artifactWithParameters : diagramArtifacts) {
	    if(!artifactWithParameters.equals("")) {
		String[] artifactParameters = artifactWithParameters.split("§");
		if(artifactParameters.length > 1) {
		    String artifact = artifactParameters[0];
		    String[] parameters = artifactParameters[1].split("!");

		    if(artifact.equals("Class")) {
			ClassArtifact classArtifact = new ClassArtifact(parameters[0], parameters[1].equals("null") ? "" : parameters[1]);
			classArtifact.setLocation(Point.parse(parameters[2]));
			add(classArtifact);
		    } else if(artifact.equals("Object")) {
			ObjectArtifact objectArtifact = new ObjectArtifact(parameters[0], parameters[1].equals("null") ? "" : parameters[1]);
			objectArtifact.setLocation(Point.parse(parameters[2]));
			add(objectArtifact);
		    } else if(artifact.equals("Note")) {
			NoteArtifact noteArtifact = new NoteArtifact(parameters[0]);
			noteArtifact.setLocation(Point.parse(parameters[1]));
			add(noteArtifact);
		    }
		}
	    }
	}
    }
    /**
     * This method calls {@link UMLArtifact#toURL()} on all artifacts of this canvas and concatenate it in a String separated by a semicolon
     * 
     * @return The concatenated String from all {@link UMLArtifact#toURL()}
     */
    public String toUrl() {	
	StringBuilder url = new StringBuilder();
	for(UMLArtifact uMLArtifact : this.objects.values()) {
	    String artifactString = uMLArtifact.toURL();
	    if(artifactString != null && !artifactString.equals("")) {
		url.append(artifactString);
		url.append(";");
	    }
	}	

	return UMLDrawerHelper.encodeBase64(url.toString());
    }

    void moveAll(final Direction direction) {
	final int step = 10;
	final Point totalTranslation = new Point((this.drawingCanvas.getOffsetWidth() / 2) * direction.getXDirection(),
		(this.drawingCanvas.getOffsetHeight() / 2) * direction.getYDirection());
	if(QualityLevel.IsAlmost(QualityLevel.HIGH)) {
	    final Point totalTranslated = Point.getOrigin();
	    if(direction.getXDirection() != 0) {
		for(int i = 0 ; i < (this.drawingCanvas.getOffsetWidth() / 2) - step ; i += step) {
		    new Scheduler.Task(this, this.allObjects) {@Override public void process() {
			Point translation =  new Point(step * direction.getXDirection(), step * direction.getYDirection());
			GfxManager.getPlatform().translate(UMLCanvas.this.allObjects, translation);
			UMLCanvas.this.canvasOffset.translate(translation);
			totalTranslated.translate(translation);
		    }
		    };
		}
	    } else {
		for(int i = 0 ; i < (this.drawingCanvas.getOffsetHeight() / 2) - step; i += step) {
		    new Scheduler.Task(this, this.allObjects) {@Override public void process() {
			Point translation =  new Point(step * direction.getXDirection(), step * direction.getYDirection());
			GfxManager.getPlatform().translate(UMLCanvas.this.allObjects, translation);
			UMLCanvas.this.canvasOffset.translate(translation);
			totalTranslated.translate(translation);
		    }
		    };		    
		}
	    }
	    new Scheduler.Task(this, this.allObjects) {@Override public void process() {
		Point translation =  Point.substract(totalTranslation, totalTranslated);
		GfxManager.getPlatform().translate(UMLCanvas.this.allObjects, translation);
		UMLCanvas.this.canvasOffset.translate(translation);
		totalTranslated.translate(translation);
	    }
	    };
	} else {
	    GfxManager.getPlatform().translate(UMLCanvas.this.allObjects, totalTranslation);
	    UMLCanvas.this.canvasOffset.translate(totalTranslation);
	}
	    GfxManager.getPlatform().translate(UMLCanvas.this.outlines, totalTranslation);
	    GfxManager.getPlatform().translate(UMLCanvas.this.movingLines, totalTranslation);
	    GfxManager.getPlatform().translate(UMLCanvas.this.movingOutlineDependencies, totalTranslation);
    }
}
