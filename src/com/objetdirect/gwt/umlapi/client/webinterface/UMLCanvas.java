package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.tools.ant.taskdefs.Sleep;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Event;
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
    private long noteCount;
    private RelationKind activeLinking;
    private boolean dragOn = false; // Represent the dragging state
    private boolean selectBoxOn = false; // Represent the select state
    private Point selectBoxStartPoint; 
    private GfxObject selectBox;
    private final Widget drawingCanvas; // Drawing canvas

    private final GfxObjectListener gfxObjectListener = new GfxObjectListener() {
	public void mouseClicked(final Event event) {
	    //Unused yet
	}

	public void mouseDblClicked(final GfxObject gfxObject, final Point location, final Event event) {
	    editItem(gfxObject);
	}

	public void mouseLeftClickPressed(final GfxObject gfxObject, final Point location, final Event event) {
	    final Point realPoint = convertToRealPoint(location);
	    if (UMLCanvas.this.dragOn == false && gfxObject != null) {
		select(gfxObject, event.getCtrlKey(), event.getShiftKey());
		UMLCanvas.this.dragOn = true;
		UMLCanvas.this.dragOffset = realPoint.clonePoint();
	    } else {
		UMLCanvas.this.selectBoxStartPoint = realPoint.clonePoint();
		UMLCanvas.this.selectBoxOn = true;
	    }
	}

	public void mouseMoved(final Point location, final Event event) {
	    final Point realPoint = convertToRealPoint(location);
	    if (UMLCanvas.this.dragOn) {
		drag(realPoint);
	    }
	    else if(UMLCanvas.this.selectBoxOn) {
		boxSelector(UMLCanvas.this.selectBoxStartPoint, realPoint);
	    }
	    if (UMLCanvas.this.activeLinking != null && !UMLCanvas.this.selectedArtifacts.isEmpty()) {
		animateLinking(realPoint);

	    }
	}


	public void mouseReleased(final GfxObject gfxObject, final Point location, final Event event) {
	    final Point realPoint = convertToRealPoint(location);
	    if (UMLCanvas.this.dragOn) {
		drop(realPoint);
	    } else if(UMLCanvas.this.selectBoxOn) {
		if(UMLCanvas.this.selectBox != null) {
		    GfxManager.getPlatform().removeFromCanvas(UMLCanvas.this.drawingCanvas, UMLCanvas.this.selectBox);
		}
		UMLCanvas.this.selectBoxOn = false;
	    }
	    unselectOnRelease(gfxObject, event.getCtrlKey(), event.getShiftKey());
	    UMLCanvas.this.dragOn = false;
	}

	public void mouseRightClickPressed(final GfxObject gfxObject, final Point location, final Event event) {
	    final Point realPoint = convertToRealPoint(location);
	    dropRightMenu(gfxObject, realPoint);
	}
    };

    private final ArrayList<GfxObject> movingLines = new ArrayList<GfxObject>();
    private final Map<GfxObject, UMLArtifact> objects = new HashMap<GfxObject, UMLArtifact>();    // Map of UMLArtifact with corresponding Graphical objects (group)
    private final Set<UMLArtifact> objectsToBeAddedWhenAttached = new LinkedHashSet<UMLArtifact>();
    private final HashMap<UMLArtifact, GfxObject> outlines = new HashMap<UMLArtifact, GfxObject>(); // Map of outlines used for drawing while drag and drop
    private final ArrayList<GfxObject> staticOutlineDependencies = new ArrayList<GfxObject>();
    private final ArrayList<GfxObject> movingOutlineDependencies = new ArrayList<GfxObject>();
    private final HashMap<UMLArtifact, ArrayList<Point>> selectedArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(); // Represent the selected UMLArtifacts and his moving dependencies points 
    private final ArrayList<UMLEventListener> uMLEventListenerList = new ArrayList<UMLEventListener>();
    private Point dragOffset; 
    private class ClassPair {
	ClassArtifact c1;
	ClassArtifact c2;

	private ClassPair(ClassArtifact c1, ClassArtifact c2) {
	    this.c1 = c1;
	    this.c2 = c2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	    ClassPair cp = (ClassPair) obj;
	    return ((this.c1 == cp.c1) && (this.c2 == cp.c2)) || ((this.c1 == cp.c2) && (this.c2 == cp.c1));
	}
    }
    private final ArrayList<ClassPair> classRelations = new ArrayList<ClassPair>();
    
    
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
		ThemeManager.getTheme().getBackgroundColor());
	this.drawingCanvas.getElement().setAttribute("oncontextmenu", "return false");
	
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
	    if(artifact.getClass() == RelationLinkArtifact.class) this.classRelations.add(new ClassPair(((RelationLinkArtifact) artifact).getLeftClassArtifact(), ((RelationLinkArtifact) artifact).getRightClassArtifact()));
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
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		selectedArtifact.unselect();
	    }
	    this.selectedArtifacts.clear();
	    select(newClass.getGfxObject());
	    this.selectedArtifacts.put(newClass, new ArrayList<Point>());
	    this.dragOffset = location;
	    drag(location);
	    this.dragOn = true;
	}
    }

    void addNewLink(final UMLArtifact newSelected) {
	LinkArtifact newLink;
	for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {

	    if (this.activeLinking == RelationKind.NOTE) {
		if (newSelected.getClass() == NoteArtifact.class) {
		    newLink = new LinkNoteArtifact((NoteArtifact) newSelected,
			    selectedArtifact);
		} else if (selectedArtifact.getClass() == NoteArtifact.class) {
		    newLink = new LinkNoteArtifact((NoteArtifact) selectedArtifact,
			    newSelected);
		} else {
		    newLink = null;
		}
	    } else if (this.activeLinking == RelationKind.CLASSRELATION) {
		if (newSelected.getClass() == RelationLinkArtifact.class
			&& selectedArtifact.getClass() == ClassArtifact.class) {
		    newLink = new LinkClassRelationArtifact(
			    (ClassArtifact) selectedArtifact,
			    (RelationLinkArtifact) newSelected);
		} else if (selectedArtifact.getClass() == RelationLinkArtifact.class
			&& newSelected.getClass() == ClassArtifact.class) {
		    newLink = new LinkClassRelationArtifact(
			    (ClassArtifact) newSelected,
			    (RelationLinkArtifact) selectedArtifact);
		} else {
		    newLink = null;
		}
	    }
	    else if (selectedArtifact.getClass() == ClassArtifact.class
		    && newSelected.getClass() == ClassArtifact.class) {
		ClassPair cp = new ClassPair((ClassArtifact) newSelected, (ClassArtifact) selectedArtifact);
		int index = Collections.frequency(this.classRelations, cp);
		newLink = new RelationLinkArtifact((ClassArtifact) newSelected, (ClassArtifact) selectedArtifact, this.activeLinking, index);
	    } else {
		newLink = null;
	    }

	    if (newLink != null && fireNewArtifactEvent(newLink)) {
		add(newLink);
	    }
	}
    }

    void addNewNote() {
	addNewNote(new Point(FAR_AWAY, FAR_AWAY));

    }

    void addNewNote(final Point initPoint) {
	if (this.dragOn) {
	    return;
	}
	final NoteArtifact newNote = new NoteArtifact("Note " + ++this.noteCount);
	if (fireNewArtifactEvent(newNote)) {
	    add(newNote);
	    newNote.moveTo(initPoint);
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		selectedArtifact.unselect();
	    }
	    this.selectedArtifacts.clear();
	    select(newNote.getGfxObject());
	    this.selectedArtifacts.put(newNote, new ArrayList<Point>());
	    this.dragOffset = initPoint;
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
			    + OptionsManager.getMovingStep()
			    * direction.getXDirection(), selectedArtifact.getLocation().getY()
			    + OptionsManager.getMovingStep()
			    * direction.getYDirection()));
		    selectedArtifact.rebuildGfxObject();
		    selectedArtifact.select();
		}
	    }
	}
    }

    void removeSelected() {
	if (!this.selectedArtifacts.isEmpty()) {
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		remove(selectedArtifact);
	    }
	}
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
	    if (!this.movingLines.isEmpty()) {
		for(GfxObject movingLine : this.movingLines) {
		    GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, movingLine);
		}
	    }
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		GfxObject movingLine = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(), location);
		GfxManager.getPlatform().setStroke(movingLine,
			ThemeManager.getTheme().getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStrokeStyle(movingLine, GfxStyle.DASH);
		GfxManager.getPlatform().addToCanvas(this.drawingCanvas, movingLine, Point.getOrigin());
		GfxManager.getPlatform().moveToBack(movingLine);
		this.movingLines.add(movingLine);
	    }
	}
    }

    private Point convertToRealPoint(final Point location) {
	return Point.add(location, new Point( RootPanel.getBodyElement().getScrollLeft()
		- getAbsoluteLeft(), RootPanel.getBodyElement().getScrollTop() - getAbsoluteTop()));
    }

    private void drag(final Point location) {
	CursorIconManager.setCursorIcon(PointerStyle.MOVE);

	Point shift = Point.subtract(location, this.dragOffset);
	if (!this.movingOutlineDependencies.isEmpty()) {
	    for (final GfxObject outlineDependency : this.movingOutlineDependencies) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, outlineDependency);
	    }
	    this.movingOutlineDependencies.clear();
	}
	final boolean isOutlineBuildNeeded = this.outlines.isEmpty();
	final HashMap<UMLArtifact, UMLArtifact> alreadyAdded = new HashMap<UMLArtifact, UMLArtifact>();
	for(final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : this.selectedArtifacts.entrySet()) {
	    final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
	    GfxObject outline = null;
	    if (selectedArtifact.isDraggable()) {
		if (isOutlineBuildNeeded) {
		    outline = selectedArtifact.getOutline();
		    GfxManager.getPlatform().addToCanvas(this.drawingCanvas, outline, selectedArtifact.getLocation());
		    selectedArtifact.destroyGfxObjectWhithDependencies();
		    Log.trace("Adding outline for " + selectedArtifact);
		    this.outlines.put(selectedArtifact, outline);
		    if (OptionsManager.qualityLevelIsAlmost(QualityLevel.HIGH)) {
			selectedArtifactEntry.getValue().clear();

			for (UMLArtifact dependantArtifact : selectedArtifact.getDependentUMLArtifacts().values()) {
			    if(this.selectedArtifacts.containsKey(dependantArtifact)) {
				if(alreadyAdded.get(selectedArtifact) == null 
					|| !alreadyAdded.get(selectedArtifact).equals(dependantArtifact)) {
				    GfxObject outlineDependency  = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(), dependantArtifact.getCenter());
				    alreadyAdded.put(dependantArtifact, selectedArtifact);
				    GfxManager.getPlatform().setStroke(outlineDependency, ThemeManager.getTheme().getHighlightedForegroundColor(), 1);
				    GfxManager.getPlatform().setStrokeStyle(outlineDependency, GfxStyle.DASH);
				    GfxManager.getPlatform().addToCanvas(this.drawingCanvas, outlineDependency, Point.getOrigin());
				    GfxManager.getPlatform().moveToBack(outlineDependency);
				    this.staticOutlineDependencies.add(outlineDependency);
				}
			    } else {
				selectedArtifactEntry.getValue().add(dependantArtifact.getCenter());
			    }
			}
		    }
		}
		else {
		    outline = this.outlines.get(selectedArtifact);
		}

		GfxManager.getPlatform().translate(outline, shift);


		Point outlineCenter = Point.add(GfxManager.getPlatform().getLocationFor(outline), new Point(selectedArtifact.getWidth()/2, selectedArtifact.getHeight()/2));
		if (OptionsManager.qualityLevelIsAlmost(QualityLevel.HIGH)) {
		    for (Point dependantArtifactCenter : selectedArtifactEntry.getValue()) {
			GfxObject outlineDependency  = GfxManager.getPlatform().buildLine(outlineCenter, dependantArtifactCenter);
			GfxManager.getPlatform().setStroke(outlineDependency, ThemeManager.getTheme().getHighlightedForegroundColor(), 1);
			GfxManager.getPlatform().setStrokeStyle(outlineDependency, GfxStyle.DASH);
			GfxManager.getPlatform().addToCanvas(this.drawingCanvas, outlineDependency, Point.getOrigin());
			GfxManager.getPlatform().moveToBack(outlineDependency);
			this.movingOutlineDependencies.add(outlineDependency);
		    }
		}
	    }
	}
	for(GfxObject staticOutlineDependency : this.staticOutlineDependencies) {
	    GfxManager.getPlatform().translate(staticOutlineDependency, shift);
	}
	this.dragOffset = location.clonePoint();
    }

    private void drop(final Point location) {
	for(UMLArtifact selectedArtifact : UMLCanvas.this.selectedArtifacts.keySet()) {
	    Point outlineLocation = GfxManager.getPlatform().getLocationFor(this.outlines.get(selectedArtifact));
	    if (selectedArtifact.isDraggable() && !outlineLocation.equals(Point.getOrigin())) {
		selectedArtifact.moveTo(outlineLocation);
		selectedArtifact.rebuildGfxObject();
		selectedArtifact.select();
	    }
	}
	if (!this.outlines.isEmpty()) {
	    for(GfxObject outline : this.outlines.values()) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, outline);
	    }
	    this.outlines.clear();
	    for (final GfxObject outlineDependency : this.staticOutlineDependencies) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, outlineDependency);
	    }
	    this.staticOutlineDependencies.clear();
	    for (final GfxObject outlineDependency : this.movingOutlineDependencies) {
		GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, outlineDependency);
	    }
	    this.movingOutlineDependencies.clear();

	}
	CursorIconManager.setCursorIcon(PointerStyle.AUTO);
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
	this.noteCount = 0;
	Log.trace("Init canvas done");
    }

    private void removeRecursive(final UMLArtifact element) {
	GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas,
		element.getGfxObject());
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

    private void select(final GfxObject gfxObject) {
	select(gfxObject, false, false);
    }

    private void select(final GfxObject gfxObject, boolean isCtrlKeyDown, boolean isShiftKeyDown) {
	final UMLArtifact newSelected = getUMLArtifact(gfxObject);
	Log.trace("Selecting : " + newSelected + " (" + gfxObject + ")");

	if (newSelected == null) {
	    this.activeLinking = null;
	    if (!this.movingLines.isEmpty()) {
		for(GfxObject movingLine : this.movingLines) {
		    GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, movingLine);
		}
		this.movingLines.clear();
	    }
	    CursorIconManager.setCursorIcon(PointerStyle.AUTO);
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		selectedArtifact.unselect();
	    }
	    this.selectedArtifacts.clear();
	} else {
	    if (!this.selectedArtifacts.isEmpty()) {
		if (this.activeLinking != null) {
		    addNewLink(newSelected);
		    this.activeLinking = null;
		    for(GfxObject movingLine : this.movingLines) {
			GfxManager.getPlatform().removeFromCanvas(this.drawingCanvas, movingLine);
		    }
		    this.movingLines.clear();
		    CursorIconManager.setCursorIcon(PointerStyle.AUTO);
		}
		if(isCtrlKeyDown || isShiftKeyDown) {
		    if(this.selectedArtifacts.containsKey(newSelected) && isCtrlKeyDown) {
			this.selectedArtifacts.remove(newSelected);
			newSelected.unselect();
		    } else {
			this.selectedArtifacts.put(newSelected, new ArrayList<Point>());
			newSelected.select();
		    }
		} else {  
		    if(!this.selectedArtifacts.containsKey(newSelected)) {
			for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
			    selectedArtifact.unselect();
			}
			this.selectedArtifacts.clear();
			this.selectedArtifacts.put(newSelected, new ArrayList<Point>());
			newSelected.select();
		    }
		}
	    } else {
		this.selectedArtifacts.put(newSelected, new ArrayList<Point>());
		newSelected.select();
	    }
	}
    }
    private void unselectOnRelease(final GfxObject gfxObject, boolean isCtrlKeyDown, boolean isShiftKeyDown) {	

	final UMLArtifact newSelected = getUMLArtifact(gfxObject);
	if(newSelected != null && !this.selectedArtifacts.isEmpty() && !(isCtrlKeyDown || isShiftKeyDown)) {
	    for(UMLArtifact selectedArtifact : this.selectedArtifacts.keySet()) {
		selectedArtifact.unselect();
	    }
	    this.selectedArtifacts.clear();
	    this.selectedArtifacts.put(newSelected, new ArrayList<Point>());
	    newSelected.select();
	}
    }
    private void boxSelector(final Point startPoint, final Point location) {
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
	
    }
}
