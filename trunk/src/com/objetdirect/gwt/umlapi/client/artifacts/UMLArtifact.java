package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.links.LinkArtifact;
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
 * @author florian
 */
public abstract class UMLArtifact {
    /**

     */
    protected UMLCanvas canvas;
    private final HashMap<LinkArtifact, UMLArtifact> dependentUMLArtifacts = new HashMap<LinkArtifact, UMLArtifact>();
    /**

     */
    protected GfxObject gfxObject;
    private boolean isBuilt = false;

    public void addDependency(final LinkArtifact dependentUMLArtifact,
	    final UMLArtifact linkedUMLArtifact) {
	Log.trace(this + "adding depency with" + dependentUMLArtifact + " - "
		+ linkedUMLArtifact);
	getDependentUMLArtifacts().put(dependentUMLArtifact, linkedUMLArtifact);
    }

    protected abstract void buildGfxObject();

    public void buildGfxObjectWithAnimation() {
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
				.setOpacity(UMLArtifact.this.gfxObject, j, false);
		    }
		};
	    }
	    ThemeManager.setForegroundOpacityTo(255);
	}
    }

    public void destructGfxObjectWhithDependencies() {
	GfxManager.getPlatform().clearVirtualGroup(this.gfxObject);
	for (final Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : getDependentUMLArtifacts()
		.entrySet()) {
	    GfxManager.getPlatform().clearVirtualGroup(
		    dependentUMLArtifact.getKey().getGfxObject());
	}
    }

    public abstract void edit(GfxObject editedGfxObject);

    /**
     * @return the canvas on which this object is
     * 
     */
    public UMLCanvas getCanvas() {
	return this.canvas;
    }

    public Point getCenter() {
	return new Point(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public int getCenterX() {
	return getX() + getWidth() / 2;
    }

    public int getCenterY() {
	return getY() + getHeight() / 2;
    }

    public HashMap<LinkArtifact, UMLArtifact> getDependentUMLArtifacts() {
	return this.dependentUMLArtifacts;
    }

    /**
     * @return the graphical object of this artifact
     * If it has already built this function just returns it, otherwise it builds it 
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

    public abstract int getHeight();

    public abstract int[] getOpaque();

    public abstract GfxObject getOutline();

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

    public abstract MenuBarAndTitle getRightMenu();

    public abstract int getWidth();

    public abstract int getX();

    public abstract int getY();

    public GfxObject initializeGfxObject() {
	this.gfxObject = GfxManager.getPlatform().buildVirtualGroup();
	this.isBuilt = false;
	return this.gfxObject;
    }

    public abstract boolean isALink();

    public abstract boolean isDraggable();

    public abstract void moveTo(int fx, int fy);

    public void rebuildGfxObject() {
	final long t = System.currentTimeMillis();
	GfxManager.getPlatform().clearVirtualGroup(this.gfxObject);
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

    public void removeDependency(final LinkArtifact dependentUMLArtifact) {
	Log.trace(this + "removing depency with" + dependentUMLArtifact);
	this.dependentUMLArtifacts.remove(dependentUMLArtifact);
    }

    public abstract void select();

    /**
     * @param canvas
     * 
     */
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
    }

    public void toBack() {
	GfxManager.getPlatform().moveToBack(this.gfxObject);
    }

    public void toFront() {
	GfxManager.getPlatform().moveToFront(this.gfxObject);
    }

    @Override
    public String toString() {
	return UMLDrawerHelper.getShortName(this);
    }

    public abstract void unselect();

}
