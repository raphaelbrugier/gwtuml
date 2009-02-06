package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author fmounier
 *
 */
public class ClassMethodsArtifact extends ClassPartArtifact {
	
	private List<Method> methods;
	
	public ClassMethodsArtifact() {
		methods = new ArrayList<Method>();
		List<Parameter> methodParameters = new ArrayList<Parameter>();
		methodParameters.add(new Parameter("String", "parameter1"));
		methods.add(new Method("void","method", methodParameters));
		height = 0;
		width = 0;
	}
	
	public void add(Method method) {
		this.methods.add(method);
	}
	
	public void remove(int index) {
		methods.remove(index);
	}
	
	public List<Method> getList() {
		return methods;
	}
	
	
	@Override
	public void buildGfxObject() {
		if(textVirtualGroup == null) computeBounds();	
		GfxObject methodRect = GfxManager.getPlatform().buildRect(classWidth, height);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, methodRect);
		GfxManager.getPlatform().setFillColor(methodRect,	ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(methodRect, ThemeManager.getForegroundColor(), 1);	
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
		for (Method method : methods) {
			GfxObject methodText = GfxManager.getPlatform().buildText(method.toString());
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, methodText);	
			GfxManager.getPlatform().setFont(methodText, font);
			GfxManager.getPlatform().setFillColor(methodText, ThemeManager.getForegroundColor());
			int thisMethodWidth = (int) (OptionsManager.getXPadding() + GfxManager.getPlatform().getWidthFor(methodText) + OptionsManager.getXPadding());
			int thisMethodHeight = (int) (OptionsManager.getYPadding() + GfxManager.getPlatform().getHeightFor(methodText) + OptionsManager.getYPadding());
			GfxManager.getPlatform().translate(methodText, OptionsManager.getXPadding(), OptionsManager.getYPadding() + height + (thisMethodHeight / 2));
			width  = thisMethodWidth > width ? thisMethodWidth : width;
			height += thisMethodHeight;
					
		}
		Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}

	@Override
	public void setClassWidth(int width) {
		this.classWidth = width;
	}



}
