/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author fmounier
 *
 */
public class ClassAttributesArtifact extends ClassPartArtifact {

	private List<Attribute> attributes;
	
	
	public ClassAttributesArtifact() {
		attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute("String", "attribute"));
		height = 0;
		width = 0;
	}
	
	public void add(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public void remove(int index) {
		attributes.remove(index);
	}
	
	@Override
	public void buildGfxObject() {
		if(textVirtualGroup == null) computeBounds();		
		GfxObject attributeRect = GfxManager.getPlatform().buildRect(classWidth, height);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, attributeRect);	
		GfxManager.getPlatform().setFillColor(attributeRect,	ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(attributeRect, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().moveToFront(textVirtualGroup);
	}

	public List<Attribute> getList() {
		return attributes;
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
		for (Attribute attribute : attributes) {
			GfxObject attributeText = GfxManager.getPlatform().buildText(attribute.toString());
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, attributeText);
			GfxManager.getPlatform().setFont(attributeText, font);
			GfxManager.getPlatform().setFillColor(attributeText, ThemeManager.getForegroundColor());
			int thisAttributeWidth = (int) (OptionsManager.getXPadding() + GfxManager.getPlatform().getWidthFor(attributeText) + OptionsManager.getXPadding());
			int thisAttributeHeight = (int) (OptionsManager.getYPadding() + GfxManager.getPlatform().getHeightFor(attributeText) + OptionsManager.getYPadding());
			GfxManager.getPlatform().translate(attributeText, OptionsManager.getXPadding(), OptionsManager.getYPadding() + height + (thisAttributeHeight / 2));
			width  = thisAttributeWidth > width ? thisAttributeWidth : width;
			height += thisAttributeHeight;			
		}
		
		
		Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}

	@Override
	public void setClassWidth(int width) {
		this.classWidth = width;
	}


}
