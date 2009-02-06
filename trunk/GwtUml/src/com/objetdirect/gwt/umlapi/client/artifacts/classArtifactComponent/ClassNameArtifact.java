package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.BoxArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author fmounier
 *
 */
public class ClassNameArtifact extends ClassPartArtifact {

	private String className;
	
	public ClassNameArtifact(String className) {
		this.className = className;		
		height = 50;
		width = 50;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public GfxObject buildGfxObject() {
		gfxObject = GfxManager.getPlatform().buildVirtualGroup();
		GfxObject nameRect = GfxManager.getPlatform().buildRect(width, height);
		GfxManager.getPlatform().setFillColor(nameRect,	ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(nameRect, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, nameRect);

		GfxObject nameText = GfxManager.getPlatform().buildText(className);
		GfxManager.getPlatform().setFont(nameText, font);
		GfxManager.getPlatform().setFillColor(nameText,	ThemeManager.getForegroundColor());
		GfxManager.getPlatform().translate(nameText, 10, 10/*TEXT_MARGIN, getNameY() + getLineHeight()*/);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, nameText);
		return gfxObject;
	}

	@Override
	public GfxObject getGfxObject() {
		if(gfxObject == null)
			return buildGfxObject();
		return gfxObject;
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


}
