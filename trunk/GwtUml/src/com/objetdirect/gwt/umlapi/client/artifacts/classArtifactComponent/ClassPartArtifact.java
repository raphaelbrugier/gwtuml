package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

public abstract class ClassPartArtifact {
	
	private boolean isInitialized = false;
	protected int height;
	protected int width;
	protected int classWidth;
	
	protected GfxObject gfxObject;
	protected GfxObject textVirtualGroup;
	protected GfxFont font = new GfxFont("monospace", 10, GfxFont.NORMAL, GfxFont.NORMAL,	GfxFont.LIGHTER);
	
	public abstract int getHeight();
	public abstract void setHeight(int height);
	public abstract int getWidth();
	public abstract void setWidth(int width);	
	public abstract void computeBounds();
	public abstract void setClassWidth(int width);	
	public abstract void buildGfxObject();
	
	public GfxObject getGfxObject() {
		if (gfxObject == null) {
			throw new UMLDrawerException("Must Initialize before getting gfxObjects");	
		}
		if(!isInitialized) {
			buildGfxObject();
			isInitialized = true;
		}
		return gfxObject;
	}
	
	
	
	public GfxObject initializeGfxObject() {
		gfxObject = GfxManager.getPlatform().buildVirtualGroup();
		return gfxObject;
	}
}
