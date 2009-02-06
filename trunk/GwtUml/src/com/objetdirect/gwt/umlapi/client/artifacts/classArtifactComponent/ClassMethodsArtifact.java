package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
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
		height = 50;
		width = 50;
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
	public GfxObject buildGfxObject() {		
		gfxObject = GfxManager.getPlatform().buildVirtualGroup();
		GfxObject methodRect = GfxManager.getPlatform().buildRect(width, height);
		GfxManager.getPlatform().setFillColor(methodRect,	ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(methodRect, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, methodRect);

		for (Method method : methods) {
			GfxObject methodText = GfxManager.getPlatform().buildText(method.toString());
			GfxManager.getPlatform().setFont(methodText, font);
			GfxManager.getPlatform().setFillColor(methodText, ThemeManager.getForegroundColor());
			GfxManager.getPlatform().translate(methodText, 10, 10);//TEXT_MARGIN,	height + getLineHeight());
			GfxManager.getPlatform().addToVirtualGroup(gfxObject, methodText);
		}
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
