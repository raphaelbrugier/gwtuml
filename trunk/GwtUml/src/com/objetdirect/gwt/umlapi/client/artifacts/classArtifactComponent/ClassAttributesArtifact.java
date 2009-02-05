/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author fmounier
 *
 */
public class ClassAttributesArtifact extends ClassPartArtifact {

	private List<Attribute> attributes;
	
	
	public ClassAttributesArtifact() {
		attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("String", "Attribute"));
		
	}
	
	public void add(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public void remove(int index) {
		attributes.remove(index);
	}
	
	@Override
	public GfxObject buildGfxObject() {
		
		gfxObject = GfxManager.getPlatform().buildVirtualGroup();
		GfxObject attributeRect = GfxManager.getPlatform().buildRect(width, height);
		GfxManager.getPlatform().setFillColor(attributeRect,	ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(attributeRect, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, attributeRect);

		for (Attribute attribute : attributes) {
			GfxObject attributeText = GfxManager.getPlatform().buildText(attribute.toString());
			GfxManager.getPlatform().setFont(attributeText, font);
			GfxManager.getPlatform().setFillColor(attributeText, ThemeManager.getForegroundColor());
			GfxManager.getPlatform().translate(attributeText, 0, 0);
			GfxManager.getPlatform().addToVirtualGroup(gfxObject, attributeText);
		}
		return gfxObject;
	}

	public List<Attribute> getList() {
		return attributes;
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
