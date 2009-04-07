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
 * @author  florian
 */
public abstract class UMLArtifact  {
    /**
     * 
     * @uml.associationEnd  
     */
    protected UMLCanvas canvas;
    /**
     * 
     * @uml.associationEnd  
     */
    protected GfxObject gfxObject;
    private HashMap<LinkArtifact, UMLArtifact> dependentUMLArtifacts = new HashMap<LinkArtifact, UMLArtifact>();
    private boolean isBuilt = false;
    public void addDependency(LinkArtifact dependentUMLArtifact, UMLArtifact linkedUMLArtifact) {
        Log.trace(this + "adding depency with" + dependentUMLArtifact + " - " + linkedUMLArtifact);
        getDependentUMLArtifacts().put(dependentUMLArtifact, linkedUMLArtifact);
    }
    public void removeDependency(LinkArtifact dependentUMLArtifact) {
        Log.trace(this + "removing depency with" + dependentUMLArtifact);
        dependentUMLArtifacts.remove(dependentUMLArtifact);
    }
    /**
     * @return
     * 
     */
    public UMLCanvas getCanvas() {
        return canvas;
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
    public GfxObject initializeGfxObject() {
        gfxObject = GfxManager.getPlatform().buildVirtualGroup();
        isBuilt = false;
        return gfxObject;
    }
    public void buildGfxObjectWithAnimation() {
        if(OptionsManager.qualityLevelIsAlmost(QualityLevel.VERY_HIGH)) ThemeManager.setForegroundOpacityTo(0);
        buildGfxObject();
        if(OptionsManager.qualityLevelIsAlmost(QualityLevel.VERY_HIGH)) {
            for(int i = 25 ; i < 256 ; i+=25) {
                final int j = i;
                new Scheduler.Task() {
                    @Override
                    public void process() {
                        GfxManager.getPlatform().setOpacity(gfxObject, j, false);
                    }
                };
            }
            ThemeManager.setForegroundOpacityTo(255);
        }
    }
    /**
     * @return
     * 
     */
    public GfxObject getGfxObject() {
        if (gfxObject == null) {
            throw new UMLDrawerException("Must Initialize before getting gfxObjects");	
        }
        if(!isBuilt) {
            long t = System.currentTimeMillis();
            buildGfxObjectWithAnimation();
            Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to build " + this);
            isBuilt = true;
        }
        return gfxObject;
    }
    
    public abstract int getHeight();
    public abstract int[] getOpaque();
    public abstract int getWidth();
    public abstract int getX();
    public abstract int getY();
    /**
     * @param canvas
     * 
     */
    public void setCanvas(UMLCanvas canvas) {
        this.canvas = canvas;
    }
    public void toFront() {
        GfxManager.getPlatform().moveToFront(gfxObject);
    }
    public void toBack() {
        GfxManager.getPlatform().moveToBack(gfxObject);
    }
    @Override
    public String toString() {
        return UMLDrawerHelper.getShortName(this);
    }
    protected abstract void buildGfxObject();

    public void rebuildGfxObject() {
        long t = System.currentTimeMillis();
        GfxManager.getPlatform().clearVirtualGroup(gfxObject);
        buildGfxObjectWithAnimation();

        Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to build " + this);
        for(final Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : getDependentUMLArtifacts().entrySet()) {
            Log.trace("Rebuilding : " + dependentUMLArtifact);
            new Scheduler.Task(dependentUMLArtifact) {
                @Override
                public void process() {
                    long t = System.currentTimeMillis();
                    dependentUMLArtifact.getKey().rebuildGfxObject();
                    Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to arrow " + this);
                }
            };
        }
        Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to rebuild " + this + " with dependency");

    }
    public ArrayList<Point> getOutlineForDependencies() {

        ArrayList<Point> points = new ArrayList<Point>();
        if(OptionsManager.qualityLevelIsAlmost(QualityLevel.HIGH)) {
            for(final Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : getDependentUMLArtifacts().entrySet()) {
                if (dependentUMLArtifact.getValue() != null) points.add(dependentUMLArtifact.getValue().getCenter());
            }
        }
        return points;
    }
    public void destructGfxObjectWhithDependencies() {
        GfxManager.getPlatform().clearVirtualGroup(gfxObject);
        for(final Entry<LinkArtifact, UMLArtifact> dependentUMLArtifact : getDependentUMLArtifacts().entrySet()) {
            GfxManager.getPlatform().clearVirtualGroup(dependentUMLArtifact.getKey().getGfxObject());
        }
    }
    public HashMap<LinkArtifact, UMLArtifact> getDependentUMLArtifacts() {
        return dependentUMLArtifacts;
    }
    public abstract boolean isDraggable();
    public abstract void edit(GfxObject gfxObject);
    public abstract GfxObject getOutline();
    public abstract MenuBarAndTitle getRightMenu();
    public abstract void moveTo(int fx, int fy);
    public abstract void select();
    public abstract void unselect();
    public abstract boolean isALink();


}
