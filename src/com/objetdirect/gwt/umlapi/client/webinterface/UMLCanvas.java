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
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationArtifact;
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
 * @author  florian
 */
public class UMLCanvas extends AbsolutePanel {

    private static long classCount = 1;
    private static long noteCount = 0;
    /**
     * @author   florian
     */
    private final static int FAR_AWAY = 9000;
    private Widget drawingCanvas; // Drawing canvas
    private boolean dragOn = false; // Represent the dragging state
    private int dx; // Represent the offset between object coordinates and mouse
    private int dy;
    private GfxObject movingLine;
    private ArrayList<UMLEventListener> uMLEventListenerList = new ArrayList<UMLEventListener>();
    private final GfxObjectListener gfxObjectListener = new GfxObjectListener() {
        public void mouseClicked() {
        }
        public void mouseDblClicked(GfxObject gfxObject, int x, int y) {
            editItem(gfxObject);
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
            if(activeLinking != null && selected != null) {
                animateLinking(x, y);

            }
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
    private GfxObject outline = null; // Outline is used for drawing while drag and drop	
    private UMLArtifact selected = null; // Represent the current UMLArtifact selected
    private RelationKind activeLinking = null;
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
        if (dragOn) return;
        ClassArtifact newClass = new ClassArtifact("Class " + ++classCount);
        if(fireNewArtifactEvent(newClass)) {
            add(newClass);
            newClass.moveTo(xInit, yInit);
            if (selected != null) selected.unselect();
            select(newClass.getGfxObject());
            take(xInit, yInit);
            drag(xInit, yInit);
            dragOn = true;
        }
    }
    public void addNewNote() {
        addNewNote(FAR_AWAY, FAR_AWAY);

    }
    public void addNewNote(int xInit, int yInit) {
        if (dragOn)
            return;
        NoteArtifact newNote = new NoteArtifact("Note " + ++noteCount);
        if(fireNewArtifactEvent(newNote)) {
            add(newNote);
            newNote.moveTo(xInit, yInit);
            if (selected != null)
                selected.unselect();
            select(newNote.getGfxObject());
            take(xInit, yInit);
            drag(xInit, yInit);		
            dragOn = true;
        }
    }
    public void toLinkMode(RelationKind linkType) {
        activeLinking = linkType;
        CursorIconManager.setCursorIcon(PointerStyle.CROSSHAIR);
    }
    public void addNewLink(UMLArtifact selected, UMLArtifact newSelected) {
        LinkArtifact newLink;
        Log.info("selected " + selected.getClass() + " newSelected " + newSelected.getClass());
        if(activeLinking == RelationKind.OTHER) {
                if (newSelected.getClass() == NoteArtifact.class) {
                    newLink = new NoteLinkArtifact((NoteArtifact) newSelected, selected);
                } else if (selected.getClass() == NoteArtifact.class) {
                    newLink = new NoteLinkArtifact((NoteArtifact) selected, newSelected);
                } else if (newSelected.getClass().getSuperclass() == RelationArtifact.class && selected.getClass() == ClassArtifact.class) {
                    newLink = new ClassRelationLinkArtifact((ClassArtifact) selected, (RelationArtifact) newSelected);
                } else if (selected.getClass().getSuperclass() == RelationArtifact.class && newSelected.getClass() == ClassArtifact.class) {
                    newLink = new ClassRelationLinkArtifact((ClassArtifact) newSelected, (RelationArtifact) selected);
                } else newLink = null;
        }
        else if (selected.getClass() == ClassArtifact.class && newSelected.getClass() == ClassArtifact.class) {
            newLink = RelationArtifact.makeLinkArtifact((ClassArtifact) newSelected, (ClassArtifact) selected, activeLinking);
        }
        else newLink = null;
        
        if(newLink != null && fireNewLinkEvent(newLink)) {
            add(newLink);
        }
    }
    public void remove(UMLArtifact element) {
        GfxManager.getPlatform().removeFromCanvas(drawingCanvas, element.getGfxObject());
        objects.remove(element.getGfxObject());       
        element.setCanvas(null);
        if (element == selected) selected = null;
        for(Entry<LinkArtifact, UMLArtifact> entry : element.getDependentUMLArtifacts().entrySet()) {
            if(entry.getValue().isALink()) remove(entry.getValue());
            entry.getValue().removeDependency(entry.getKey());     
            remove(entry.getKey());
        }
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
                selected.destructGfxObjectWhithDependencies();
                Log.trace("Adding outline for " + selected);
                CursorIconManager.setCursorIcon(PointerStyle.MOVE);
            }
            int tx = x - dx -  GfxManager.getPlatform().getXFor(outline);
            int ty = y - dy -  GfxManager.getPlatform().getYFor(outline);
            Log.trace("Translating " + tx + "," + ty);
            GfxManager.getPlatform().translate(outline, tx, ty);
            outlineDependencies(selected.getOutlineForDependencies(), x, y);


        }
    }
    private void drop(int x, int y) {
        if (selected != null && selected.isDraggable()) {
            if (outline != null) {
                GfxManager.getPlatform().removeFromCanvas(drawingCanvas, outline);
                outline = null;
                for(Entry<Point, GfxObject> entry : outlineDependencies.entrySet()) {
                    GfxManager.getPlatform().removeFromCanvas(drawingCanvas, entry.getValue());
                }
                outlineDependencies.clear();
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
    private void editItem(GfxObject gfxObject) {
        Log.trace("Edit request on " + gfxObject);
        UMLArtifact elem = getUMLArtifact(gfxObject);
        if (elem != null) {
            Log.trace("Edit started on " + elem);
            elem.edit(gfxObject);
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

        if (newSelected == null) {
            activeLinking = null;
            if(movingLine != null)  GfxManager.getPlatform().removeFromCanvas(drawingCanvas, movingLine);
            isDeleting = false;
            CursorIconManager.setCursorIcon(PointerStyle.AUTO);
        }
        if (selected != null) {
            if (activeLinking != null) {
                addNewLink(selected, newSelected);
                activeLinking = null;
                if(movingLine != null)  GfxManager.getPlatform().removeFromCanvas(drawingCanvas, movingLine);
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
    private HashMap<Point, GfxObject> outlineDependencies = new HashMap<Point, GfxObject>();
    private void outlineDependencies(ArrayList<Point> dependentArtifactLocations, int x, int y) {

        if(!outlineDependencies.isEmpty()) {
            for(Entry<Point, GfxObject> entry : outlineDependencies.entrySet()) {
                GfxManager.getPlatform().removeFromCanvas(drawingCanvas, entry.getValue());
            }
            outlineDependencies.clear();
        }

        for(Point point : dependentArtifactLocations) {
            GfxObject line = GfxManager.getPlatform().buildLine(point.getX(), point.getY(), x, y);
            GfxManager.getPlatform().setStroke(line, ThemeManager.getHighlightedForegroundColor(), 1);
            GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
            GfxManager.getPlatform().addToCanvas(drawingCanvas, line, 0, 0);
            GfxManager.getPlatform().moveToBack(line);   
            outlineDependencies.put(point, line);
        }

    }
    private void animateLinking(int x, int y) {
        if(OptionsManager.qualityLevelIsAlmost(QualityLevel.HIGH)) {
            if(movingLine != null)  GfxManager.getPlatform().removeFromCanvas(drawingCanvas, movingLine);
            movingLine = GfxManager.getPlatform().buildLine(selected.getCenterX(), selected.getCenterY(), x, y);
            GfxManager.getPlatform().setStroke(movingLine, ThemeManager.getHighlightedForegroundColor(), 1);
            GfxManager.getPlatform().setStrokeStyle(movingLine, GfxStyle.DASH);
            GfxManager.getPlatform().addToCanvas(drawingCanvas, movingLine, 0, 0);
            GfxManager.getPlatform().moveToBack(movingLine);
        }
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
    public void moveSelected(Direction direction) {
        if(selected != null) {
            selected.moveTo(selected.getX() + OptionsManager.getMovingStep() * direction.getXDirection(),
                    selected.getY() + OptionsManager.getMovingStep() * direction.getYDirection());
        }
    }
    public void addUMLEventListener(UMLEventListener uMLEventListener) {
        uMLEventListenerList.add(uMLEventListener);
    }
    public void removeUMLEventListener(UMLEventListener uMLEventListener) {
        uMLEventListenerList.remove(uMLEventListener);
    }
    public boolean fireNewArtifactEvent(UMLArtifact umlArtifact) {
        boolean isThisOk = true;
        for(UMLEventListener listener : uMLEventListenerList) {
            //If one is not ok then it's not ok !
            isThisOk = listener.onNewUMLArtifact(umlArtifact) && isThisOk;
        }
        Log.trace("New Artifact event fired. Status : " + isThisOk);
        return isThisOk;

    }
    public boolean fireNewLinkEvent(LinkArtifact newLink) {
        boolean isThisOk = true;
        for(UMLEventListener listener : uMLEventListenerList) {
            isThisOk = listener.onNewLink(newLink) && isThisOk;
        }
        Log.trace("New Link event fired. Status : " + isThisOk);
        return isThisOk;
    }
}
