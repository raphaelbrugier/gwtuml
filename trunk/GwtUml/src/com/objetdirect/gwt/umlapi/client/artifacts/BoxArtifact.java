package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.ThemeManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;


public abstract class BoxArtifact extends UMLArtifact{

	public void setLocation(int x, int y) {
		GfxManager.getInstance().translate(getGfxObject(), x-this.x, y-this.y);
		this.x = x;
		this.y = y;
	}
	
	public void adjust() {
		super.adjust();
		if (getCanvas()!=null)
			GfxManager.getInstance().translate(getGfxObject(),getX(), getY());
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public float[] getOpaque() {
		float[] opaque = new float[] {
				getX(), getY(),
				getX(), getY() + getHeight(), 
				getX() + getWidth(), getY() + getHeight(), 
				getX() + getWidth(), getY()
		};
		return opaque;
	}
		
	public boolean isDraggable() {
		return true;
	}
	
	protected boolean set(GfxObject[] slot, GfxObject text) {
		int oldWidth = getWidth();
		int oldHeight = getHeight();
		if (slot[0]!=null) {
			GfxManager.getInstance().removeFromVirtualGroup(getGfxObject(), slot[0], false);
			if (getCanvas()!=null)
				getCanvas().unregister(slot[0]);
		}
		slot[0] = text;
		if (slot[0]!=null) {
			GfxManager.getInstance().addToVirtualGroup(getGfxObject(), slot[0]);
			if (getCanvas()!=null)
				getCanvas().register(slot[0], this);
		}
		int newWidth = getWidth();
		int newHeight = getHeight();
		if (oldWidth!=newWidth || oldHeight!=newHeight) {
			adjust();
			return true;
		}
		else 
			return false;
	}
	
	public GfxObject getOutline() {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject vg = gPlatform.buildVirtualGroup();
		
		GfxObject line1 = gPlatform.buildLine(0, 0, getWidth(), 0);
		GfxObject line2 = gPlatform.buildLine(getWidth(), 0, getWidth(), getHeight());
		GfxObject line3 = gPlatform.buildLine(getWidth(), getHeight(), 0, getHeight());
		GfxObject line4 = gPlatform.buildLine(0, getHeight(), 0, 0);
			
		gPlatform.setStrokeStyle(line1, GfxStyle.DASH);
		gPlatform.setStrokeStyle(line2, GfxStyle.DASH);
		gPlatform.setStrokeStyle(line3, GfxStyle.DASH);
		gPlatform.setStrokeStyle(line4, GfxStyle.DASH);
		
		GfxManager.getInstance().setStroke(line1, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getInstance().setStroke(line2, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getInstance().setStroke(line3, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getInstance().setStroke(line4, ThemeManager.getHighlightedForegroundColor(), 1);
		
		gPlatform.addToVirtualGroup(vg, line1);
		gPlatform.addToVirtualGroup(vg, line2);
		gPlatform.addToVirtualGroup(vg, line3);
		gPlatform.addToVirtualGroup(vg, line4);
		return vg;
	}

	int x = 0;
	int y = 0;
	
}
