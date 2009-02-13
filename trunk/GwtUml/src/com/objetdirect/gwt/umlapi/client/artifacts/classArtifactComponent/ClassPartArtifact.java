package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.editors.FieldEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public abstract class ClassPartArtifact {
	
	private boolean isBuilt = false;
	protected int height;
	protected int width;
	protected int classWidth;
	protected UMLCanvas canvas;
	protected GfxObject gfxObject;
	protected GfxObject textVirtualGroup;
	protected ClassArtifact classArtifact;

	
	public abstract int getHeight();
	public abstract void setHeight(int height);
	public abstract int getWidth();
	public abstract void setWidth(int width);	
	public abstract void computeBounds();
	public abstract void setClassWidth(int width);	
	public abstract void buildGfxObject();
	public abstract void edit();
	public abstract void edit(GfxObject gfxobject);
	
	public ClassArtifact getClassArtifact() {
		return classArtifact;
	}
	public void setClassArtifact(ClassArtifact classArtifact) {
		this.classArtifact = classArtifact;
	}
	
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
	
	
	public GfxObject initializeGfxObject() {
		gfxObject = GfxManager.getPlatform().buildVirtualGroup();
		isBuilt = false;
		return gfxObject;
	}
	
	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}
		
}
