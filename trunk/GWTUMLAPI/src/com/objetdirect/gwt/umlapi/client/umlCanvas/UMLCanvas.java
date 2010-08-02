/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright Â© 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.umlCanvas;

import static com.objetdirect.gwt.umlapi.client.gfx.GfxStyle.LONGDASHDOTDOT;
import static com.objetdirect.gwt.umlapi.client.helpers.CursorIconManager.PointerStyle.AUTO;
import static com.objetdirect.gwt.umlapi.client.helpers.CursorIconManager.PointerStyle.CROSSHAIR;
import static com.objetdirect.gwt.umlapi.client.helpers.CursorIconManager.PointerStyle.MOVE;
import static com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas.DragAndDropState.NONE;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind.NOTE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifactPeer;
import com.objetdirect.gwt.umlapi.client.controls.CanvasListener;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.QualityLevel;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @contributor Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public abstract class UMLCanvas implements Serializable {

	public enum DragAndDropState {
		TAKING, DRAGGING, NONE, PREPARING_SELECT_BOX, SELECT_BOX;
	}

	// ======= Display fields =================//
	/** The canvas */
	private transient Widget drawingCanvas;

	private transient Widget fieldEditor;

	/** The panel containing the canvas and the arrows. */
	protected transient DecoratorCanvas wrapper;

	private transient GfxObject allObjects;

	/** Map of UMLArtifact with corresponding Graphical objects (group) */
	private transient HashMap<GfxObject, UMLArtifact> objects;

	// ==== Selection fields =====//
	private transient GfxObject selectBox;
	private transient GfxObject movingLines;
	private transient GfxObject outlines;
	private transient GfxObject movingOutlineDependencies;

	// ===== Mouse engine field ====//
	private transient CanvasListener canvasMouseListener;

	// ========== Extra ===========//
	private transient UrlConverter urlConverter;

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	// ====== Model fields =======//
	private long noteCount;

	/** Id counter */
	protected int idCount;
	/** List of all umlArtifacts by id. */
	private HashMap<Integer, UMLArtifact> artifactById;
	private Set<UMLArtifact> artifactsToBeAddedWhenAttached;
	/**
	 * Flag used when the LinksArtifact are computed to known if their dependencies have already been sorted.
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#ComputeDirectionsType()
	 */
	private boolean LinkArtifactsHaveAlreadyBeenSorted;
	/** List of all couple of UMLArtifacts linked together. */
	private ArrayList<UMLArtifactPeer> uMLArtifactRelations;

	// ===== cut copy paste fields =======//
	private String copyBuffer;
	private boolean wasACopy;
	private Point copyMousePosition;

	// ==== Selection fields =====//
	protected LinkKind activeLinking;
	private Point selectBoxStartPoint;
	protected DragAndDropState dragAndDropState;
	private HashMap<UMLArtifact, ArrayList<Point>> previouslySelectedArtifacts;
	private Point duringDragOffset;
	protected Point dragOffset;
	private Point totalDragShift;

	/** Represent the selected UMLArtifacts and his moving dependencies points */
	protected HashMap<UMLArtifact, ArrayList<Point>> selectedArtifacts;

	// ===== Mouse engine fields ====//
	private boolean isMouseEnabled;

	/** Manage mouse state when releasing outside the listener */
	protected boolean mouseIsPressed;
	private Point canvasOffset;

	/**
	 * Factory : return a new UmlCanvas depending on the diagram type given.
	 * 
	 * @param diagramType The type of diagram to create.
	 * @return The created diagram
	 */
	public static UMLCanvas createUmlCanvas(DiagramType diagramType) {
		switch (diagramType) {
			case CLASS:
				return new UMLCanvasClassDiagram(true);
			case OBJECT:
				return new UMLCanvasObjectDiagram(true);
			case SEQUENCE:
				return new UMLCanvasSequenceDiagram(true);
		}

		throw new IllegalArgumentException("Unknown diagram type : " + diagramType);
	}

	/** Default constructor ONLY for gwt-rpc serialization. */
	UMLCanvas() { }

	/**
	 * Constructor of an {@link UMLCanvas}.
	 * 
	 * @param dummy
	 *            any value is fine.
	 */
	public UMLCanvas(@SuppressWarnings("unused") boolean dummy) {
		initFieldsWithDefaultValue();

		drawingCanvas = GfxManager.getPlatform().makeCanvas(100, 100, ThemeManager.getTheme().getCanvasColor());
		drawingCanvas.getElement().setAttribute("oncontextmenu", "return false");

		this.initCanvas();
	}

	private void initFieldsWithDefaultValue() {
		idCount = 0;
		copyBuffer = "";
		dragAndDropState = DragAndDropState.NONE;
		canvasOffset = Point.getOrigin();
		duringDragOffset = Point.getOrigin();
		isMouseEnabled = true;
		mouseIsPressed = false;
		artifactsToBeAddedWhenAttached = new LinkedHashSet<UMLArtifact>();
		selectedArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>();
		uMLArtifactRelations = new ArrayList<UMLArtifactPeer>();
		artifactById = new HashMap<Integer, UMLArtifact>();
		totalDragShift = Point.getOrigin();
	}

	private void initCanvas() {
		initGfxObjects();
		urlConverter = new UrlConverter(this);
		Log.trace("Adding Canvas");
		Log.trace("Adding object listener");
		GfxManager.getPlatform().addObjectListenerToCanvas(drawingCanvas, canvasMouseListener);
		noteCount = 0;
		allObjects.addToCanvas(drawingCanvas, Point.getOrigin());
		movingLines.addToCanvas(drawingCanvas, Point.getOrigin());
		outlines.addToCanvas(drawingCanvas, Point.getOrigin());
		movingOutlineDependencies.addToCanvas(drawingCanvas, Point.getOrigin());
		Log.trace("Init canvas done");
	}

	private void initGfxObjects() {
		objects = new HashMap<GfxObject, UMLArtifact>();
		allObjects = GfxManager.getPlatform().buildVirtualGroup();
		outlines = GfxManager.getPlatform().buildVirtualGroup();
		movingOutlineDependencies = GfxManager.getPlatform().buildVirtualGroup();
		movingLines = GfxManager.getPlatform().buildVirtualGroup();
		canvasMouseListener = new CanvasListener(this);
	}

	/**
	 * Getter for the canvasOffset
	 * 
	 * @return the canvasOffset
	 */
	public Point getCanvasOffset() {
		return canvasOffset;
	}

	/**
	 * Getter for the graphic canvas
	 * 
	 * @return The graphic canvas
	 */
	public Widget getDrawingCanvas() {
		return drawingCanvas;
	}

	public DecoratorCanvas getContainer() {
		return wrapper;
	}

	/**
	 * @return the canvasMouseListener
	 */
	public CanvasListener getCanvasMouseListener() {
		return canvasMouseListener;
	}

	/**
	 * @param container
	 *            the decoratorCanvas to set
	 */
	public void setDecoratorPanel(DecoratorCanvas container) {
		wrapper = container;
	}

	/**
	 * @return the artifactById
	 */
	public Map<Integer, UMLArtifact> getArtifactById() {
		return artifactById;
	}

	public boolean getWasACopy() {
		return wasACopy;
	}

	public Point getCopyMousePosition() {
		return copyMousePosition;
	}

	/**
	 * Set the mouse state
	 * 
	 * @param isMouseEnabled
	 *            True to enable Mouse <br/>
	 *            False to disable Mouse
	 */
	public void setMouseEnabled(final boolean isMouseEnabled) {
		this.isMouseEnabled = isMouseEnabled;
	}

	/**
	 * Set if the hotKeys are enabled.
	 * 
	 * @param hotKeysEnabled
	 *            the hotKeysEnabled to set
	 */
	public void setHotKeysEnabled(boolean hotKeysEnabled) {
		wrapper.setHotKeysEnabled(hotKeysEnabled);
	}

	/**
	 * @param fieldEditor
	 *            the fieldEditor to set
	 */
	public void setFieldEditor(Widget fieldEditor) {
		this.fieldEditor = fieldEditor;
	}

	/**
	 * @return the uMLArtifactRelations
	 */
	public ArrayList<UMLArtifactPeer> getuMLArtifactRelations() {
		return uMLArtifactRelations;
	}

	/**
	 * @return the linkArtifactsHaveAlreadyBeenSorted
	 */
	public boolean isLinkArtifactsHaveAlreadyBeenSorted() {
		return LinkArtifactsHaveAlreadyBeenSorted;
	}

	public boolean isMouseEnabled() {
		return isMouseEnabled;
	}

	/**
	 * @param linkArtifactsHaveAlreadyBeenSorted
	 *            the linkArtifactsHaveAlreadyBeenSorted to set
	 */
	public void setLinkArtifactsHaveAlreadyBeenSorted(boolean linkArtifactsHaveAlreadyBeenSorted) {
		LinkArtifactsHaveAlreadyBeenSorted = linkArtifactsHaveAlreadyBeenSorted;
	}


	/**
	 * Create a diagram from the URL {@link String} previously generated by {@link UMLCanvas#toUrl()}
	 * 
	 * @param url
	 *            The {@link String} previously generated by {@link UMLCanvas#toUrl()}
	 * 
	 * @param isForPasting
	 *            True if the parsing is for copy pasting
	 * 
	 */
	public void fromURL(final String url, final boolean isForPasting) {
		urlConverter.fromURL(url, isForPasting);
	}

	/**
	 * This method calls {@link UMLArtifact#toURL()} on all artifacts of this canvas and concatenate it in a String
	 * separated by a semicolon
	 * 
	 * @return The concatenated String from all {@link UMLArtifact#toURL()}
	 */
	public String toUrl() {
		return urlConverter.toUrl();
	}

	/**
	 * Build the gfx object for the given artifact and attached it to the canvas.
	 * 
	 * @param artifact
	 *            the artifact to build in the canvas.
	 */
	private void displaydArtifactInCanvas(final UMLArtifact artifact) {
		final long t = System.currentTimeMillis();
		artifact.initializeGfxObject().addToVirtualGroup(allObjects);

		artifact.getGfxObject().translate(artifact.getLocation());
		objects.put(artifact.getGfxObject(), artifact);
		Log.trace("([" + (System.currentTimeMillis() - t) + "ms]) to add " + artifact);
	}

	/**
	 * Add an {@link UMLArtifact} to this canvas
	 * 
	 * @param artifact
	 *            The {@link UMLArtifact} to add
	 */
	public void add(final UMLArtifact artifact) {
		if (artifact == null) {
			Log.info("Adding null element to canvas");
			return;
		}

		idCount++;
		artifactById.put(artifact.getId(), artifact);

		if (wrapper!=null && wrapper.isAttached()) {
			displaydArtifactInCanvas(artifact);
		} else {
			Log.trace("Canvas not attached, queuing " + artifact);
			artifactsToBeAddedWhenAttached.add(artifact);
		}
	}

	private void addNewLink(final UMLArtifact newSelected) {
		int linkOkCount = 0;
		for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {

			LinkArtifact newLink = null;
			if (activeLinking == NOTE) {
				newLink = makeLinkNote(selectedArtifact, newSelected);
			} else {
				newLink = makeLinkBetween(selectedArtifact, newSelected);
			}

			if (newLink != null) {
				linkOkCount++;
				this.add(newLink);
			}
		}
		if (linkOkCount != 0) {
			this.linkingModeOff();
		}
	}

	public void addNewNote() {
		this.addNewNote(wrapper.getCurrentMousePosition());
	}

	private void addNewNote(final Point location) {
		if (dragAndDropState != NONE) {
			return;
		}

		final NoteArtifact newNote = new NoteArtifact(this, idCount, "Note " + ++noteCount);
		this.add(newNote);
		newNote.moveTo(Point.substract(location, canvasOffset));
		for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {
			selectedArtifact.unselect();
		}
		selectedArtifacts.clear();
		this.doSelection(newNote.getGfxObject(), false, false);
		selectedArtifacts.put(newNote, new ArrayList<Point>());
		dragOffset = location;
		dragAndDropState = DragAndDropState.TAKING;
		mouseIsPressed = true;
		wrapper.setCursorIcon(MOVE);
		wrapper.setHelpText("Adding a new note", location.clonePoint());
	}

	private LinkArtifact makeLinkNote(final UMLArtifact uMLArtifact, final UMLArtifact uMLArtifactNew) {
		try {
			return new LinkNoteArtifact(this, idCount, uMLArtifact, uMLArtifactNew);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Return the first artifact found at the specified location
	 * This is part of the public API and should not be removed.
	 * @param location
	 *            The location to look for an artifact
	 * 
	 * @return The first artifact found at the specified location or null if there is no artifact there
	 */
	public GfxObject getArtifactAt(final Point location) {
		for (final UMLArtifact artifact : objects.values()) {
			if (this.isIn(artifact.getLocation(), Point.add(artifact.getLocation(), new Point(artifact.getWidth(), artifact.getHeight())), location, location)) {
				Log.info("Artifact : " + artifact + " found");
				return artifact.getGfxObject();
			}
		}
		Log.info("No Artifact Found !");
		return null;
	}


	public void cut() {
		this.copy();
		wasACopy = false;
		this.removeSelected();
	}

	public void copy() {
		if (selectedArtifacts.isEmpty()) {
			return;
		}
		wasACopy = true;
		final StringBuilder url = new StringBuilder();
		Point lower = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point higher = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

		for (final Entry<Integer, UMLArtifact> uMLArtifactEntry : artifactById.entrySet()) {
			if (selectedArtifacts.containsKey(uMLArtifactEntry.getValue())) {
				final String artifactString = uMLArtifactEntry.getValue().toURL();
				if ((artifactString != null) && !artifactString.equals("")) {
					if (!uMLArtifactEntry.getValue().isALink()
							|| (selectedArtifacts.containsKey(((LinkArtifact) uMLArtifactEntry.getValue()).getLeftUMLArtifact()) && selectedArtifacts
									.containsKey(((LinkArtifact) uMLArtifactEntry.getValue()).getRightUMLArtifact()))) {
						url.append("<");
						url.append(uMLArtifactEntry.getKey());
						url.append(">]");
						url.append(artifactString);
						url.append(";");
						if (!uMLArtifactEntry.getValue().isALink()) {
							lower = Point.min(lower, uMLArtifactEntry.getValue().getCenter());
							higher = Point.max(higher, uMLArtifactEntry.getValue().getCenter());
						}
					}
				}
			}
		}
		copyBuffer = url.toString();
		copyMousePosition = Point.getMiddleOf(lower, higher);
	}

	public void paste() {
		if (!copyBuffer.equals("") && dragAndDropState == DragAndDropState.NONE) {
			// Getting ids :
			deselectAllArtifacts();
			LinkedList<Integer> oldIds = new LinkedList<Integer>();
			String[] slices = copyBuffer.split(">");
			for (String stringId : slices) {
				if (stringId.contains("<")) {
					oldIds.add(Integer.parseInt(stringId.split("<")[1]));
				}
			}
			Collections.sort(oldIds);
			// Creating new Ids
			for (Integer oldId : oldIds) {
				copyBuffer = copyBuffer.replaceAll("<" + oldId + ">", "<" + (idCount + oldIds.indexOf(oldId) + 1) + ">");
			}
			idCount = idCount + oldIds.size() + 1;
			fromURL(copyBuffer, true);
			dragOffset = wrapper.getCurrentMousePosition();

			wrapper.setCursorIcon(MOVE);
			dragAndDropState = DragAndDropState.TAKING;
			mouseIsPressed = true;
		}
	}

	/**
	 * Select an artifact and put it in the artifact list
	 * 
	 * @param toSelect
	 *            The artifact to be selected
	 */
	public void selectArtifact(final UMLArtifact toSelect) {
		selectedArtifacts.put(toSelect, new ArrayList<Point>());
		toSelect.select(true);
	}

	public void mouseDoubleClicked(final GfxObject gfxObject) {
		this.editItem(gfxObject);
	}

	public void mouseLeftPressed(final GfxObject gfxObject, final boolean isCtrlDown, final boolean isShiftDown) {
		if (mouseIsPressed) {
			return;
		}
		duringDragOffset = Point.getOrigin();
		final Point realPoint = wrapper.getCurrentMousePosition();
		mouseIsPressed = true;
		if (dragAndDropState == DragAndDropState.DRAGGING) {
			return;
		}
		if (activeLinking == null) {
			if (gfxObject != null) {
				dragAndDropState = DragAndDropState.TAKING;
				dragOffset = realPoint.clonePoint();
				wrapper.setCursorIcon(MOVE);
			} else {
				selectBoxStartPoint = realPoint.clonePoint();
				dragAndDropState = DragAndDropState.PREPARING_SELECT_BOX;
			}
		}
		this.doSelection(gfxObject, isCtrlDown, isShiftDown);
	}

	@SuppressWarnings("fallthrough")
	public void mouseMoved(final boolean isCtrlDown, final boolean isShiftDown) {
		Log.trace("UMLCanvas::mouseMoved()");
		final Point realPoint = wrapper.getCurrentMousePosition();
		wrapper.moveHelpText(realPoint);
		switch (dragAndDropState) {
			case TAKING:
				this.take();
				dragAndDropState = DragAndDropState.DRAGGING;
			case DRAGGING:
				this.drag(realPoint);
				break;
			case PREPARING_SELECT_BOX:
				dragAndDropState = DragAndDropState.SELECT_BOX;
				previouslySelectedArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(selectedArtifacts);
			case SELECT_BOX:
				this.boxSelector(Point.add(selectBoxStartPoint, duringDragOffset), realPoint, isCtrlDown, isShiftDown);
		}

		if ((activeLinking != null) && !selectedArtifacts.isEmpty()) {
			this.animateLinking(realPoint);
		}
	}

	public void mouseReleased(final GfxObject gfxObject, final boolean isCtrlDown, final boolean isShiftDown) {
		if (!mouseIsPressed) {
			return;
		}
		final Point realPoint = wrapper.getCurrentMousePosition();
		mouseIsPressed = false;

		if (dragAndDropState == DragAndDropState.TAKING) {
			this.unselectOnRelease(gfxObject, isCtrlDown, isShiftDown);
		}
		switch (dragAndDropState) {
			case SELECT_BOX:
				if (selectBox != null) {
					selectBox.removeFromCanvas(drawingCanvas);
				}
				dragAndDropState = DragAndDropState.NONE;
				break;
			case DRAGGING:
				this.drop();
			case TAKING:
				wrapper.setCursorIcon(AUTO);
				wrapper.setHelpText("", realPoint);

			default:
				dragAndDropState = DragAndDropState.NONE;
		}
	}

	public void mouseRightPressed(final GfxObject gfxObject, final Point location) {
		int wrapperHeight = wrapper.getOffsetHeight();
		int wrapperWidth = wrapper.getOffsetWidth();
		int locationX = location.getX();
		int locationY = location.getY();
		
		int contextMenuMaxWidth = 80;
		int contextMenuMaxHeight = 200;
		
		
		if ((locationX + contextMenuMaxWidth ) > wrapperWidth) {
			locationX  = wrapperWidth - contextMenuMaxWidth;
		}
		
		if ((locationY + contextMenuMaxHeight ) > wrapperHeight) {
			locationY  = wrapperHeight - contextMenuMaxHeight;
		}
		
		Point calculatedLocation = new Point(locationX, locationY);
		
		this.dropContextualMenu(gfxObject, calculatedLocation);
	}

	public void moveSelected(final Direction direction) {
		if (!selectedArtifacts.isEmpty()) {
			Point lower = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
			Point higher = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
			for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {
				if (!selectedArtifact.isALink()) {
					lower = Point.min(lower, Point.add(selectedArtifact.getLocation(), new Point(direction.getXShift(), direction.getYShift())));
					higher = Point.max(higher, Point.add(Point.add(selectedArtifact.getLocation(), new Point(selectedArtifact.getWidth(), selectedArtifact
							.getHeight())), new Point(direction.getXShift(), direction.getYShift())));
				}
				if (selectedArtifact.isDraggable()) {
					new Scheduler.Task("MovingSelected") {
						@Override
						public void process() {
							selectedArtifact.moveTo(new Point(selectedArtifact.getLocation().getX() + direction.getXShift(), selectedArtifact.getLocation()
									.getY()
									+ direction.getYShift()));
							selectedArtifact.rebuildGfxObject();
						}
					};
				}
			}
			boolean mustShiftCanvas = false;
			switch (direction) {
				case UP:
					mustShiftCanvas = lower.getY() < -canvasOffset.getY();
					break;
				case DOWN:
					mustShiftCanvas = higher.getY() > -canvasOffset.getY() + wrapper.getOffsetHeight();
					break;
				case LEFT:
					mustShiftCanvas = lower.getX() < -canvasOffset.getX();
					break;
				case RIGHT:
					mustShiftCanvas = higher.getX() > -canvasOffset.getX() + wrapper.getOffsetWidth();
					break;
			}
			if (mustShiftCanvas) {
				moveAll(direction, false);
			}
		}
	}

	/**
	 * Move all artifact in the specified {@link Direction}
	 * 
	 * @param direction
	 *            The {@link Direction} to move the artifact by
	 * @param isRecursive
	 *            True if the method call is recursive
	 */
	public void moveAll(final Direction direction, final boolean isRecursive) {
		Log.trace("UMLCanvas::moveAll");
		new Scheduler.Task("MovingAllArtifacts") {
			@Override
			public void process() {
				final Point translation = new Point(-direction.getXShift(), -direction.getYShift());
				allObjects.translate(translation);
				canvasOffset.translate(translation);
				duringDragOffset.translate(translation);
				UMLCanvas.this.mouseMoved(false, false);
				movingLines.translate(translation);
				movingOutlineDependencies.translate(translation);
				if (fieldEditor != null) {
					wrapper.setWidgetPosition(fieldEditor, (int) (fieldEditor.getAbsoluteLeft() - direction.getXShift()),
							(int) (fieldEditor.getAbsoluteTop() - direction.getYShift()));
				}
			}
		};

		if (isRecursive) {
			new Scheduler.Task("MovingAllArtifactsRecursive", 5) {
				@Override
				public void process() {
					UMLCanvas.this.moveAll(direction, true);
				}
			};
		}
	}

	public void rebuildAllGFXObjects() {
		for (final UMLArtifact artifact : objects.values()) {
			artifact.rebuildGfxObject();
		}
	}

	/**
	 * OnLoad should explicitly be called by the container's onLoad method
	 */
	public void onLoad() {
		Log.trace("UMLCanvas::onLoad() loading");
		rebuildAllGFXObjects();
		for (final UMLArtifact elementNotAdded : artifactsToBeAddedWhenAttached) {
			displaydArtifactInCanvas(elementNotAdded);
		}
		artifactsToBeAddedWhenAttached.clear();
	}

	public void removeSelected() {
		if (!selectedArtifacts.isEmpty()) {
			final HashMap<UMLArtifact, ArrayList<Point>> selectedBeforeRemovalArtifacts = new HashMap<UMLArtifact, ArrayList<Point>>(selectedArtifacts);
			for (final UMLArtifact selectedArtifact : selectedBeforeRemovalArtifacts.keySet()) {
				this.remove(selectedArtifact);
			}
		}
	}

	/**
	 * Remove the specified artifact from the canvas
	 * 
	 * @param umlArtifact
	 */
	public void remove(final UMLArtifact umlArtifact) {
		removeRecursive(umlArtifact);
		if (umlArtifact.isALink()) {
			LinkArtifact linkArtifact = (LinkArtifact) umlArtifact; 
			linkArtifact.removeCreatedDependency();
			uMLArtifactRelations.remove(linkArtifact.getArtifactsTargeted());
		}
	}

	private void removeRecursive(final UMLArtifact element) {
		element.getGfxObject().removeFromVirtualGroup(allObjects, false);
		objects.remove(element.getGfxObject());
		artifactById.remove(element.getId());
		element.setCanvas(null);
		selectedArtifacts.remove(element);

		for (final Entry<LinkArtifact, UMLArtifact> entry : element.getDependentUMLArtifacts().entrySet()) {
			if (entry.getValue().isALink() && (entry.getKey().getClass() != LinkNoteArtifact.class)) {
				this.remove(entry.getValue());
			}
			entry.getValue().removeDependency(entry.getKey());
			this.removeRecursive(entry.getKey());
		}
	}

	public void toLinkMode(final LinkKind linkType) {
		activeLinking = linkType;
		final int selectedToLink = selectedArtifacts.keySet().size();

		wrapper.setHelpText(("Adding " + (selectedToLink == 0 || selectedToLink == 1   ? "a" : selectedToLink) + " new " + activeLinking.getName()), new Point(0, 0));

		wrapper.setCursorIcon(CROSSHAIR);
	}

	private void animateLinking(final Point location) {
		if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
			GfxManager.getPlatform().clearVirtualGroup(movingLines);
			for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {
				final GfxObject movingLine = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(), Point.substract(location, canvasOffset));
				movingLine.addToVirtualGroup(movingLines);
				movingLine.setStrokeStyle(LONGDASHDOTDOT);
			}
			movingLines.setStroke(ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
			movingLines.moveToBack();
		}
	}

	public void selectAll() {
		for (final UMLArtifact artifact : objects.values()) {
			this.selectArtifact(artifact);
		}
	}

	private void boxSelect(final UMLArtifact artifact, final boolean isSelecting) {
		if (isSelecting) {
			selectedArtifacts.put(artifact, new ArrayList<Point>());
			artifact.select(false);
		} else {
			selectedArtifacts.remove(artifact);
			artifact.unselect();
		}
	}

	private void boxSelector(final Point startPoint, final Point location, final boolean isCtrlDown, final boolean isShiftDown) {
		if (selectBox != null) {
			selectBox.removeFromCanvas(drawingCanvas);
		}

		selectBox = GfxManager.getPlatform().buildPath();
		GfxManager.getPlatform().moveTo(selectBox, startPoint);
		GfxManager.getPlatform().lineTo(selectBox, new Point(location.getX(), startPoint.getY()));
		GfxManager.getPlatform().lineTo(selectBox, location);
		GfxManager.getPlatform().lineTo(selectBox, new Point(startPoint.getX(), location.getY()));
		GfxManager.getPlatform().lineTo(selectBox, startPoint);
		selectBox.addToCanvas(drawingCanvas, Point.getOrigin());
		selectBox.setStroke(ThemeManager.getTheme().getSelectBoxForegroundColor(), 2);
		selectBox.setFillColor(ThemeManager.getTheme().getSelectBoxBackgroundColor());
		selectBox.setOpacity(ThemeManager.getTheme().getSelectBoxBackgroundColor().getAlpha(), true);
		final Point min = Point.substract(Point.min(startPoint, location), canvasOffset);
		final Point max = Point.substract(Point.max(startPoint, location), canvasOffset);

		for (final UMLArtifact artifact : objects.values()) {
			if (artifact.isDraggable()) {
				if (this.isIn(artifact.getLocation(), Point.add(artifact.getLocation(), new Point(artifact.getWidth(), artifact.getHeight())), min, max)) {
					this.boxSelect(artifact, !(previouslySelectedArtifacts.containsKey(artifact) && isCtrlDown));
				} else {
					this.boxSelect(artifact, (isShiftDown || isCtrlDown) && previouslySelectedArtifacts.containsKey(artifact));
				}
			}
		}
	}

	private void deselectAllArtifacts() {
		for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {
			selectedArtifact.unselect();
		}
		selectedArtifacts.clear();
	}

	protected void doSelection(final GfxObject gfxObject, final boolean isCtrlKeyDown, final boolean isShiftKeyDown) {
		final UMLArtifact newSelected = this.getUMLArtifact(gfxObject);
		Log.trace("Selecting : " + newSelected + " (" + gfxObject + ")");
		// New selection is null -> deselecting all selected artifacts and disabling linking mode
		if (newSelected == null) {
			this.linkingModeOff();
			this.deselectAllArtifacts();
		} else { // New selection is not null
			if (selectedArtifacts.containsKey(newSelected)) { // New selection is already selected -> deselecting it if
				// ctrl is down
				if (activeLinking != null) {// if linking mode on
					this.addNewLink(newSelected);
				}
				if (selectedArtifacts.size() != 1) { // New selection isn't the only one selected
					if (isCtrlKeyDown) { // If ctrl down deselecting only this one
						this.deselectArtifact(newSelected);
					}
				}
			} else { // New selection is not selected
				if (selectedArtifacts.isEmpty()) { // If nothing is selected -> selecting
					this.selectArtifact(newSelected);
				} else { // Other artifacts are selected
					if (activeLinking != null) {// if linking mode on
						this.addNewLink(newSelected);
					}
					if (!isCtrlKeyDown && !isShiftKeyDown) { // If selection is not greedy ->deselect all
						this.deselectAllArtifacts();
					}
					this.selectArtifact(newSelected);
				}
			}
		}
	}

	/**
	 * Deselect an artifact and put it in the artifact list
	 * 
	 * @param toDeselect
	 *            The artifact to be deselected
	 */
	private void deselectArtifact(final UMLArtifact toDeselect) {
		selectedArtifacts.remove(toDeselect);
		toDeselect.unselect();
	}

	private void drag(final Point realPoint) {
		Log.trace("dragging : location = " + realPoint);
		final Point shift = Point.substract(realPoint, dragOffset);
		totalDragShift.translate(shift);

		if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
			GfxManager.getPlatform().clearVirtualGroup(movingOutlineDependencies);
		}
		for (final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : selectedArtifacts.entrySet()) {
			final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
			if (selectedArtifact.isDraggable()) {
				final Point outlineOfSelectedCenter = Point.substract(Point.add(selectedArtifact.getCenter(), totalDragShift), duringDragOffset);
				if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
					for (final Point dependantArtifactCenter : selectedArtifactEntry.getValue()) {
						final GfxObject outlineDependency = GfxManager.getPlatform().buildLine(outlineOfSelectedCenter, dependantArtifactCenter);
						outlineDependency.addToVirtualGroup(movingOutlineDependencies);
						outlineDependency.setStrokeStyle(GfxStyle.DASH);
					}
					movingOutlineDependencies.setStroke(ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
					movingOutlineDependencies.moveToBack();
				}
				outlines.setStroke(ThemeManager.getTheme().getDefaultHighlightedForegroundColor(), 1);
			}
		}
		outlines.translate(shift);
		dragOffset = realPoint.clonePoint();
	}

	private void drop() {
		for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {
			if (selectedArtifact.isDraggable()) {
				selectedArtifact.moveTo(Point.substract(Point.add(selectedArtifact.getLocation(), totalDragShift), duringDragOffset));
				selectedArtifact.rebuildGfxObject();
			}
		}
		totalDragShift = Point.getOrigin();
		duringDragOffset = Point.getOrigin();
		GfxManager.getPlatform().clearVirtualGroup(outlines);
		GfxManager.getPlatform().clearVirtualGroup(movingOutlineDependencies);
	}

	private void editItem(final GfxObject gfxObject) {
		final UMLArtifact uMLArtifact = this.getUMLArtifact(gfxObject);
		if (uMLArtifact != null) {
			Log.trace("Edit started on " + uMLArtifact);
			uMLArtifact.edit(gfxObject);
		}
	}

	protected UMLArtifact getUMLArtifact(final GfxObject gfxObject) {
		if (gfxObject == null) {
			Log.trace("No Object");
			return null;
		}
		GfxObject currentGfxObject = gfxObject;
		GfxObject gfxOParentGroup = currentGfxObject.getGroup();
		while ((gfxOParentGroup != null) && !gfxOParentGroup.equals(allObjects)) {
			currentGfxObject = gfxOParentGroup;
			gfxOParentGroup = currentGfxObject.getGroup();
		}
		final UMLArtifact UMLArtifact = objects.get(currentGfxObject);
		if (UMLArtifact == null) {
			Log.trace("Artifact not found");
		}
		return UMLArtifact;
	}

	private boolean isIn(final Point artifactMin, final Point artifactMax, final Point selectMin, final Point selectMax) {
		return (selectMax.isSuperiorTo(artifactMin) && artifactMax.isSuperiorTo(selectMin));
	}

	private void linkingModeOff() {
		activeLinking = null;
		GfxManager.getPlatform().clearVirtualGroup(movingLines);
		wrapper.setCursorIcon(AUTO);
		wrapper.setHelpText("", new Point(0, 0));
	}

	private void take() {
		outlines.translate(Point.substract(canvasOffset, outlines.getLocation()));
		final HashMap<UMLArtifact, UMLArtifact> alreadyAdded = new HashMap<UMLArtifact, UMLArtifact>();
		duringDragOffset = Point.getOrigin();
		for (final Entry<UMLArtifact, ArrayList<Point>> selectedArtifactEntry : selectedArtifacts.entrySet()) {
			final UMLArtifact selectedArtifact = selectedArtifactEntry.getKey();
			Scheduler.cancel("RebuildingDependencyFor" + selectedArtifact);
			if (selectedArtifact.isDraggable()) {
				final GfxObject outline = selectedArtifact.getOutline();
				outline.addToVirtualGroup(outlines);
				outline.translate(selectedArtifact.getLocation());
				selectedArtifact.destroyGfxObjectWhithDependencies();
				Log.trace("Adding outline for " + selectedArtifact);
				// Drawing lines only translating during drag
				if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
					selectedArtifactEntry.getValue().clear();

					for (final UMLArtifact dependantArtifact : selectedArtifact.getDependentUMLArtifacts().values()) {
						if (selectedArtifacts.containsKey(dependantArtifact)) {
							if ((alreadyAdded.get(selectedArtifact) == null) || !alreadyAdded.get(selectedArtifact).equals(dependantArtifact)) {
								final GfxObject outlineDependency = GfxManager.getPlatform().buildLine(selectedArtifact.getCenter(),
										dependantArtifact.getCenter());
								outlineDependency.addToVirtualGroup(outlines);
								outlineDependency.setStrokeStyle(GfxStyle.DASH);
								outlineDependency.moveToBack();
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

	private void unselectOnRelease(final GfxObject gfxObject, final boolean isCtrlKeyDown, final boolean isShiftKeyDown) {
		final UMLArtifact newSelected = this.getUMLArtifact(gfxObject);
		if (newSelected != null) {
			if (selectedArtifacts.containsKey(newSelected)) {
				if (selectedArtifacts.size() != 1) {
					if (!isShiftKeyDown && !isCtrlKeyDown) { // Doing what have not been done on mouse press to allow
						// multiple dragging
						this.deselectAllArtifacts();
						this.selectArtifact(newSelected);
					}
				}
			}
		}
	}

	public void setUpAfterDeserialization(int width, int height) {
		Log.trace("UMLCanvas::setUpAfterDeserialization => Making Canvas");
		drawingCanvas = GfxManager.getPlatform().makeCanvas(width, height, ThemeManager.getTheme().getCanvasColor());
		drawingCanvas.getElement().setAttribute("oncontextmenu", "return false");
		this.initCanvas();

		objects = new HashMap<GfxObject, UMLArtifact>();
		for (Entry<Integer, UMLArtifact> entry : artifactById.entrySet()) {
			UMLArtifact artifact = entry.getValue();
			artifact.initializeGfxObject().addToVirtualGroup(allObjects);
			artifact.setUpAfterDeserialization();
			artifact.getGfxObject().translate(artifact.getLocation());
			objects.put(artifact.getGfxObject(), artifact);
		}
		canvasOffset = Point.getOrigin();
		duringDragOffset = Point.getOrigin();
	}

	public abstract void dropContextualMenu(final GfxObject gfxObject, final Point location);


	/**
	 * Make a link between two {@link UMLArtifact}
	 * 
	 * @param uMLArtifact
	 *            The first one of the two {@link UMLArtifact} to be linked
	 * @param uMLArtifactNew
	 *            The second one of the two {@link UMLArtifact} to be linked
	 * @return The created {@link LinkArtifact} linking uMLArtifact and uMLArtifactNew
	 */
	protected abstract LinkArtifact makeLinkBetween(final UMLArtifact uMLArtifact, final UMLArtifact uMLArtifactNew);
}
