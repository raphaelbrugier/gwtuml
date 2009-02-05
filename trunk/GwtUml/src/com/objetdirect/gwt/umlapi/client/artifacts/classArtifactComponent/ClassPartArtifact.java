package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.List;

import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

public abstract class ClassPartArtifact {
	
	protected int height;
	protected int width;
	protected GfxObject gfxObject;
	protected GfxFont font = new GfxFont("monospace", 10, GfxFont.NORMAL, GfxFont.NORMAL,	GfxFont.LIGHTER);
	
	public abstract int getHeight();
	public abstract void setHeight(int height);
	public abstract int getWidth();
	public abstract void setWidth(int width);	
	
	public abstract GfxObject getGfxObject();
	public abstract GfxObject buildGfxObject();
}
