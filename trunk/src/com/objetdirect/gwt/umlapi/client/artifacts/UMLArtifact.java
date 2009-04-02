package com.objetdirect.gwt.umlapi.client.artifacts;
import java.util.HashSet;
import java.util.LinkedHashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
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
    protected HashSet<UMLArtifact> dependentUMLArtifacts = new HashSet<UMLArtifact>();
    private boolean isBuilt = false;
    public void addDependency(UMLArtifact dependentUMLArtifact) {
        Log.trace(this + "adding depency with" + dependentUMLArtifact);
        dependentUMLArtifacts.add(dependentUMLArtifact);
    }
    public void removeDependency(UMLArtifact dependentUMLArtifact) {
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
        if(OptionsManager.isAnimated()) ThemeManager.setForegroundOpacityTo(0);
        buildGfxObject();
        if(OptionsManager.isAnimated()) {
            for(int i = 25 ; i < 256 ; i+=50) {
                final int j = i;
                new Scheduler.Task() {
                    @Override
                    public void process() {
                        GfxManager.getPlatform().setOpacity(gfxObject, j);
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
        for(final UMLArtifact dependentUMLArtifact : dependentUMLArtifacts) {
            Log.trace("Rebuilding : " + dependentUMLArtifact);
            new Scheduler.Task(dependentUMLArtifact) {
                @Override
                public void process() {
                    long t = System.currentTimeMillis();
                    dependentUMLArtifact.rebuildGfxObject();
                    Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to arrow " + this);
                }
            };
        }
        Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to rebuild " + this + " with dependency");

    }
    public abstract boolean isDraggable();
    public abstract void edit(GfxObject gfxObject, int x, int y);
    public abstract GfxObject getOutline();
    public abstract LinkedHashMap<String, Command> getRightMenu();
    public abstract void moveTo(int fx, int fy);
    public abstract void select();
    public abstract void unselect();
}
