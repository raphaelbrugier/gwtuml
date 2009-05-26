/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.artifacts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.engine.ShapeGeometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.QualityLevel;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This abstract class represent any uml artifact that can be displayed An
 * artifact is something between the graphical object and the uml component. <br> It
 * has an uml component and it build the graphical object
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class UMLArtifact {

    protected boolean isSelected = false;
    private static int idCount = 0;
    private static TreeMap<Integer, UMLArtifact> artifactById = new TreeMap<Integer, UMLArtifact>();
    /**
     * Static getter of an {@link UMLArtifact} from its id
     * 
     * @param artifactId The id of the artifact to retrieve 
     * @return the artifact corresponding to this id
     */
    public static UMLArtifact getArtifactById(int artifactId) {
	return artifactById.get(artifactId);
    }
    /**
     * Static getter of the id sorted {@link UMLArtifact} TreeMap
     *  
     * @return the artifact list ordered by id 
     */
    public static TreeMap<Integer, UMLArtifact> getArtifactList() {
	return artifactById;
    }
    /**
     * Remove an artifact from the artifact by id list
     * 
     * @param idToRemove The id of the artifact to remove
     * 
     */
    public static void removeArtifactById(Integer idToRemove) {
	artifactById.remove(idToRemove);

    }
    private int id;

    protected UMLCanvas canvas;
    protected GfxObject gfxObject;

    private final HashMap<LinkArtifact, UMLArtifact> dependentUMLArtifacts = new HashMap<LinkArtifact, UMLArtifact>();
    private boolean isBuilt = false;
    private Point location = Point.getOrigin();

    /**
     * Constructor of UMLArtifact <br>
     * This constructor must be called by super() because it's here we assign the artifact id 
     * 
     * @param toBeAdded True if the artifact can be add to the artifact list
     */
    public UMLArtifact(boolean toBeAdded) {
	super();
	if(toBeAdded) {
	    this.id = idCount++;
	    artifactById.put(this.id, this);
	}
    }
    /**
     * Setter of UMLArtifact id <br>
     * This method must only be use for recovering an old diagram
     *  
     * @param id The old id of the artifact we are recovering 
     *
     */
    public void setId(int id) {
	artifactById.remove(this.id);
	this.id = id;
	idCount = Math.max(this.id + 1, idCount);	
	artifactById.put(this.id, this);
    }
    /**
     * This method destroys this artifact's graphical object and all
     * dependencies graphical objects. <br> 
     * Useful to remove a class and all its links
     */
    public void destroyGfxObjectWhithDependencies() {
	GfxManager.getPlatform().clearVirtualGroup(this.gfxObject);
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
     * @return An {@link HashMap} of {@link LinkArtifact} and UMLArtifact which represents the
     *         links of this artifact with the linked artifacts
     */
    public HashMap<LinkArtifact, UMLArtifact> getDependentUMLArtifacts() {
	return this.dependentUMLArtifacts;
    }

    /** 
     * Getter for the graphical object of this artifact
     * 
     * @return The graphical object of this artifact. <br> If it has already built
     *         this function just returns it, otherwise it builds it.
     * 
     */
    public GfxObject getGfxObject() {
	if (this.gfxObject == null) {
	    throw new UMLDrawerException(
	    "Must Initialize before getting gfxObjects");
	}
	if (!this.isBuilt) {
	    final long t = System.currentTimeMillis();
	    buildGfxObjectWithAnimation();
	    Log.debug("([" + (System.currentTimeMillis() - t)
		    + "ms]) to build " + this);
	    this.isBuilt = true;
	}
	return this.gfxObject;
    }

    /**
     * Getter for the Height
     * 
     * @return This artifact total height
     */
    public abstract int getHeight();

    /**
     * Getter for the artifact id
     *
     * @return the unique id of this artifact
     */
    public int getId() {
	return this.id;
    }

    /**
     * Getter for the location
     * 
     * @return The Point that represents where this artifact currently is
     */
    public Point getLocation() {
	return this.location;
    }

    /**
     * Getter for an "opaque" integer table. <br> 
     * This opaque represents all the points that describes a shape. <br> 
     * This is used by the shape based engine.
     * 
     * @return The opaque of this artifact which is an integer table
     *  
     * @see ShapeGeometry 
     */
    public abstract int[] getOpaque();

    /**
     * Getter for the outline of an artifact. <br> 
     * The outline is what is been drawn during drag and drop
     * 
     * @return The graphical object of this outline
     */
    public abstract GfxObject getOutline();

    /**
     * Getter for the dependent objects points. <br> 
     * This is used to draw outline links during drag and drop.
     * 
     * @return An ArrayList of all this points
     */
    public ArrayList<Point> getOutlineForDependencies() {

	final ArrayList<Point> points = new ArrayList<Point>();
	if (QualityLevel.IsAlmost(QualityLevel.HIGH)) {
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
     * Getter for the context menu. <br> 
     * Each artifact has his own context menu which is built during right click
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
     * This is the method that initializes the graphical object. It <b>MUST</b>
     * be called before doing anything else with the graphical object.
     * 
     * @return The initialized graphical object.
     */
    public GfxObject initializeGfxObject() {
	this.gfxObject = GfxManager.getPlatform().buildVirtualGroup();
	this.isBuilt = false;
	return this.gfxObject;
    }

    /**
     * This method can be used to determine if this artifact has a type inherited from {@link LinkArtifact}
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
     * This method moves an artifact to a new location
     * It changes the current location <b>AND</b> translate the graphical object
     * @param newLocation 
     * 			The new location of the artifact
     */
    public void moveTo(final Point newLocation) {
	if(!isALink()) {
	    GfxManager.getPlatform().translate(getGfxObject(), Point.substract(newLocation, getLocation()));
	    this.location = newLocation;
	}
	else {
	    Log.error("Can't move a line ! (moveTo called on " + this + ")");
	}

    }


    /**
     * This method does a rebuild of the graphical object of this artifact. <br> 
     * It is useful to reflect changes made on an artifact / uml component
     * 
     */
    public void rebuildGfxObject() {
	final long t = System.currentTimeMillis();
	GfxManager.getPlatform().clearVirtualGroup(this.gfxObject);
	buildGfxObjectWithAnimation();
	if(this.isSelected) select();

	Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to build "
		+ this);
	for (final Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : getDependentUMLArtifacts()
		.entrySet()) {
	    Log.trace("Rebuilding : " + dependentUMLArtifact);
	    new Scheduler.Task("RebuildingDependencyFor"+this) {
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
	this.dependentUMLArtifacts.remove(dependentUMLArtifact);
    }

    /**
     * This method does the graphic changes to reflect that an artifact has been selected.
     * 
     * @param moveToFront Specifies if the graphical object must be brought to foreground (this parameter is ignored if this graphical object is a link)
     * 
     */
    public void select(boolean moveToFront) {
	select();
	if(moveToFront && !isALink()) toFront();
    }

    /**
     * This method does the graphic changes to reflect that an artifact has been selected
     */
    protected void select() {
	this.isSelected = true;
	for (Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : this.dependentUMLArtifacts.entrySet()) {
	    if(dependentUMLArtifact.getValue().isSelected) this.canvas.selectArtifact(dependentUMLArtifact.getKey());
	}
    }

    /**
     * Setter for the canvas
     * Assign an artifact to his canvas
     * @param canvas The canvas this artifact belongs
     * 
     */
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
    }

    /**
     * This method sets the current artifact location, it should not be called after the artifact is added on canvas except for very specifically uses: <br /> 
     * It doesn't translate the graphical object unlike {@link UMLArtifact#moveTo}.
     * 
     * @param location The artifact  location
     */
    public void setLocation(final Point location) {
	this.location = location;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return UMLDrawerHelper.getShortName(this);
    }

    /**
     * This method transform the parameters of the object to a string
     * 
     * @return The String containing the parameters
     */
    public abstract String toURL();


    /**
     * This method do the graphic changes to reflect that an artifact has been unselected
     */
    public void unselect() {
	this.isSelected = false;
    }


    void addDependency(final LinkArtifact dependentUMLArtifact,
	    final UMLArtifact linkedUMLArtifact) {
	Log.trace(this + "adding depency with" + dependentUMLArtifact + " - "
		+ linkedUMLArtifact);
	getDependentUMLArtifacts().put(dependentUMLArtifact, linkedUMLArtifact);
    }

    void buildGfxObjectWithAnimation() {
	if (QualityLevel.IsAlmost(QualityLevel.VERY_HIGH)) {
	    ThemeManager.setForegroundOpacityTo(0);
	}
	buildGfxObject();
	if (QualityLevel.IsAlmost(QualityLevel.VERY_HIGH)) {
	    for (int i = 25; i < 256; i += 25) {
		final int j = i;
		new Scheduler.Task("OpacityArtifactAnimation") {
		    @Override
		    public void process() {
			GfxManager.getPlatform()
			.setOpacity(UMLArtifact.this.gfxObject, j, false);
		    }
		};
	    }
	    ThemeManager.setForegroundOpacityTo(255);
	}
    }

    void toBack() {
	GfxManager.getPlatform().moveToBack(this.gfxObject);
    }

    void toFront() {
	GfxManager.getPlatform().moveToFront(this.gfxObject);
    }

    protected abstract void buildGfxObject();

}
