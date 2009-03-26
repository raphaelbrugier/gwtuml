package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.HashSet;
import java.util.LinkedHashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author  florian
 */
public abstract class UMLArtifact  {

	/**
	 * @uml.property  name="canvas"
	 * @uml.associationEnd  
	 */
	protected UMLCanvas canvas;

	/**
	 * @uml.property  name="gfxObject"
	 * @uml.associationEnd  
	 */
	protected GfxObject gfxObject;

	protected HashSet<UMLArtifact> dependentUMLArtifacts = new HashSet<UMLArtifact>();

	private boolean isBuilt = false;

	public void addDependency(UMLArtifact dependentUMLArtifact) {
		Log.warn(this + "adding depency with" + dependentUMLArtifact);
		dependentUMLArtifacts.add(dependentUMLArtifact);
	}
	public void removeDependency(UMLArtifact dependentUMLArtifact) {
		Log.warn(this + "removing depency with" + dependentUMLArtifact);
		dependentUMLArtifacts.remove(dependentUMLArtifact);
	}

	/**
	 * @return
	 * @uml.property  name="canvas"
	 */
	public UMLCanvas getCanvas() {
		return canvas;
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

	/**
	 * @return
	 * @uml.property  name="gfxObject"
	 */
	public GfxObject getGfxObject() {
		if (gfxObject == null) {
			throw new UMLDrawerException("Must Initialize before getting gfxObjects");	
		}
		if(!isBuilt) {
			buildGfxObject();
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
	 * @uml.property  name="canvas"
	 */
	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	@Override
	public String toString() {
		return UMLDrawerHelper.getShortName(this);
	}
	protected abstract void buildGfxObject();

	public void rebuildGfxObject() {
		GfxManager.getPlatform().clearVirtualGroup(gfxObject);
		buildGfxObject();
		for(final UMLArtifact dependentUMLArtifact : dependentUMLArtifacts) {
			Log.warn("Rebuilding : " + dependentUMLArtifact);
			new Scheduler.Task(dependentUMLArtifact) {
				@Override
				public void process() {
					dependentUMLArtifact.rebuildGfxObject();
				}
			};
		}
	
	}

	public abstract boolean isDraggable();

	public abstract void edit(GfxObject gfxObject, int x, int y);

	public abstract GfxObject getOutline();

	public abstract LinkedHashMap<String, Command> getRightMenu();

	public abstract void moveTo(int fx, int fy);

	public abstract void select();

	public abstract void unselect();
}
