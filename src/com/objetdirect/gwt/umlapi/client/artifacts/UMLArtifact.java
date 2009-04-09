package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager.QualityLevel;

/**
 * This abstract class represent any uml artifact that can be displayed An
 * artifact is something between the graphical object and the uml component. It
 * has an uml component and it build the graphical object
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class UMLArtifact {
    protected UMLCanvas canvas;
    protected GfxObject gfxObject;

    private final HashMap<LinkArtifact, UMLArtifact> dependentUMLArtifacts = new HashMap<LinkArtifact, UMLArtifact>();
    private boolean isBuilt = false;
    private Point location = Point.getOrigin();

    /**
     * This method destroys this artifact's graphical object and all
     * dependencies graphical objects Useful to remove a class and all its links
     */
    public void destroyGfxObjectWhithDependencies() {
	GfxManager.getPlatform().clearVirtualGroup(gfxObject);
	for (final Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : getDependentUMLArtifacts()
		.entrySet()) {
	    GfxManager.getPlatform().clearVirtualGroup(
		    dependentUMLArtifact.getKey().getGfxObject());
	}
    }

    /**
     * Request an edition on this artifact.
     * 
     * @param editedGfxObject
     *            is the graphical object on which edition should occur
     */
    public abstract void edit(GfxObject editedGfxObject);

    /**
     * Getter for the center point of the graphical object
     * 
     * @return The Point which corresponds to the center of this artifact
     */
    public Point getCenter() {
	return new Point(getLocation().getX() + getWidth() / 2, getLocation()
		.getY()
		+ getHeight() / 2);
    }

    /**
     * Getter for the dependent UMLArtifacts
     * 
     * @return An HashMap of LinkArtifact and UMLArtifact which represents the
     *         links of this artifact with the linked artifacts
     */
    public HashMap<LinkArtifact, UMLArtifact> getDependentUMLArtifacts() {
	return dependentUMLArtifacts;
    }

    /** 
     * Getter for the graphical object of this artifact
     * @return The graphical object of this artifact If it has already built
     *         this function just returns it, otherwise it builds it
     * 
     */
    public GfxObject getGfxObject() {
	if (gfxObject == null) {
	    throw new UMLDrawerException(
		    "Must Initialize before getting gfxObjects");
	}
	if (!isBuilt) {
	    final long t = System.currentTimeMillis();
	    buildGfxObjectWithAnimation();
	    Log.debug("([" + (System.currentTimeMillis() - t)
		    + "ms]) to build " + this);
	    isBuilt = true;
	}
	return gfxObject;
    }

    /**
     * Getter for the Height
     * 
     * @return This artifact total height
     */
    public abstract int getHeight();

    /**
     * Getter for the location
     * 
     * @return The Point that represents where this artifact currently is
     */
    public Point getLocation() {
	return location;
    }

    /**
     * Getter for an "opaque" integer table. This opaque represents all the
     * points that describes a shape. This is used by the shape based engine
     * 
     * @return The opaque of this artifact which is an integer table
     */
    public abstract int[] getOpaque();

    /**
     * Getter for the outline of an artifact. The outline is what is been drawn
     * during drag and drop
     * 
     * @return The graphical object of this outline
     */
    public abstract GfxObject getOutline();

    /**
     * Getter for the dependent objects points. This is used to draw outline
     * links during drag and drop.
     * 
     * @return An ArrayList of all this points
     */
    public ArrayList<Point> getOutlineForDependencies() {

	final ArrayList<Point> points = new ArrayList<Point>();
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.HIGH)) {
	    for (final Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : getDependentUMLArtifacts()
		    .entrySet()) {
		if (dependentUMLArtifact.getValue() != null) {
		    points.add(dependentUMLArtifact.getValue().getCenter());
		}
	    }
	}
	return points;
    }

    /**
     * Getter for the context menu. Each artifact has his own context menu which
     * is built during right click
     * 
     * @return The context sub menu and the title of the sub menu
     * @see MenuBarAndTitle
     */
    public abstract MenuBarAndTitle getRightMenu();

    /**
     * Getter for the Width
     * 
     * @return This artifact total width
     */
    public abstract int getWidth();

    /**
     * This is the method that initialize the graphical object. It <b>MUST</b>
     * be called before doing anything else with the graphical object.
     * 
     * @return The initialized graphical object.
     */
    public GfxObject initializeGfxObject() {
	gfxObject = GfxManager.getPlatform().buildVirtualGroup();
	isBuilt = false;
	return gfxObject;
    }

    /**
     * This method can be used to determine if this artifact is a link
     * 
     * @return <ul>
     *         <li><b>True</b> if it is a link</li>
     *         <li><b>False</b> otherwise</li>
     *         </ul>
     */
    public abstract boolean isALink();

    /**
     * This method can be used to determine if this artifact can do drag and drop
     * @return <ul>
     *         <li><b>True</b> if it can</li>
     *         <li><b>False</b> otherwise</li>
     *         </ul>
     */
    public abstract boolean isDraggable();

    /**
     * This method move
     * @param newLocation
     */
    public void moveTo(final Point newLocation) {
	if(!isALink()) {
	GfxManager.getPlatform().translate(getGfxObject(), Point.substract(newLocation, getLocation()));
	this.setLocation(newLocation);
	}
	else {
	    Log.error("Can't move a line ! (moveTo called on " + this + ")");
	}
	    
    }


    public void rebuildGfxObject() {
	final long t = System.currentTimeMillis();
	GfxManager.getPlatform().clearVirtualGroup(gfxObject);
	buildGfxObjectWithAnimation();

	Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to build "
		+ this);
	for (final Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : getDependentUMLArtifacts()
		.entrySet()) {
	    Log.trace("Rebuilding : " + dependentUMLArtifact);
	    new Scheduler.Task(dependentUMLArtifact) {
		@Override
		public void process() {
		    final long t2 = System.currentTimeMillis();
		    dependentUMLArtifact.getKey().rebuildGfxObject();
		    Log.debug("([" + (System.currentTimeMillis() - t2)
			    + "ms]) to arrow " + this);
		}
	    };
	}
	Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to rebuild "
		+ this + " with dependency");

    }

    /**
     * Remove a dependency for this artifact
     * 
     * @param dependentUMLArtifact
     *            The link which this artifact is no more dependent
     */
    public void removeDependency(final LinkArtifact dependentUMLArtifact) {
	Log.trace(this + "removing depency with" + dependentUMLArtifact);
	dependentUMLArtifacts.remove(dependentUMLArtifact);
    }

    public abstract void select();

    /**
     * @param canvas
     * 
     */
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(final Point location) {
	this.location = location;
    }

    @Override
    public String toString() {
	return UMLDrawerHelper.getShortName(this);
    }

    public abstract void unselect();

    void addDependency(final LinkArtifact dependentUMLArtifact,
	    final UMLArtifact linkedUMLArtifact) {
	Log.trace(this + "adding depency with" + dependentUMLArtifact + " - "
		+ linkedUMLArtifact);
	getDependentUMLArtifacts().put(dependentUMLArtifact, linkedUMLArtifact);
    }

    void buildGfxObjectWithAnimation() {
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.VERY_HIGH)) {
	    ThemeManager.setForegroundOpacityTo(0);
	}
	buildGfxObject();
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.VERY_HIGH)) {
	    for (int i = 25; i < 256; i += 25) {
		final int j = i;
		new Scheduler.Task() {
		    @Override
		    public void process() {
			GfxManager.getPlatform()
				.setOpacity(gfxObject, j, false);
		    }
		};
	    }
	    ThemeManager.setForegroundOpacityTo(255);
	}
    }

    void toBack() {
	GfxManager.getPlatform().moveToBack(gfxObject);
    }

    void toFront() {
	GfxManager.getPlatform().moveToFront(gfxObject);
    }

    protected abstract void buildGfxObject();

}
