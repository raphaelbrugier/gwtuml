package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author fmounier
 *
 */
public class ClassNameArtifact extends ClassPartArtifact {

	private String className;
	
	public ClassNameArtifact(String className) {
		this.className = className;		
		height = 0;
		width = 0;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public void buildGfxObject() {
		if(textVirtualGroup == null) computeBounds();	
		GfxObject nameRect = GfxManager.getPlatform().buildRect(classWidth, height);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, nameRect);
		GfxManager.getPlatform().setFillColor(nameRect,	ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(nameRect, ThemeManager.getForegroundColor(), 1);
				
		//Centering name class :
		GfxManager.getPlatform().translate(textVirtualGroup, (classWidth-width)/2, 0);
		GfxManager.getPlatform().moveToFront(textVirtualGroup);
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void computeBounds() {
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);
		GfxObject nameText = GfxManager.getPlatform().buildText(className);
		GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, nameText);
		
		GfxManager.getPlatform().setFont(nameText, font);
		GfxManager.getPlatform().setFillColor(nameText,	ThemeManager.getForegroundColor());
		width  = (int) (OptionsManager.getXPadding() + GfxManager.getPlatform().getWidthFor(nameText) + OptionsManager.getXPadding());
		height = (int) (OptionsManager.getYPadding() + GfxManager.getPlatform().getHeightFor(nameText) + OptionsManager.getYPadding());
		GfxManager.getPlatform().translate(nameText, OptionsManager.getXPadding(), OptionsManager.getYPadding() + (height / 2));
		
		Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}

	@Override
	public void setClassWidth(int width) {
		this.classWidth = width;
	}


}
