/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.AttributePartEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  fmounier
 */
public class ClassAttributesArtifact extends ClassPartArtifact {
	private List<Attribute> attributes;
	private Map<GfxObject, Attribute> attributeGfxObjects;
	/**
	 * @uml.property  name="lastGfxObject"
	 * @uml.associationEnd  
	 */
	private GfxObject lastGfxObject;
	public ClassAttributesArtifact() {
		attributes = new ArrayList<Attribute>();
		attributeGfxObjects = new HashMap<GfxObject, Attribute>();
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
		GfxManager.getPlatform().setFillColor(attributeRect, ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(attributeRect, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().translate(textVirtualGroup, OptionsManager.getRectangleLeftPadding(), OptionsManager.getRectangleTopPadding());
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
			GfxManager.getPlatform().setFont(attributeText, OptionsManager.getFont());
			GfxManager.getPlatform().setFillColor(attributeText, ThemeManager.getForegroundColor());
			int thisAttributeWidth =  GfxManager.getPlatform().getWidthFor(attributeText) ;
			int thisAttributeHeight =  GfxManager.getPlatform().getHeightFor(attributeText);
			GfxManager.getPlatform().translate(attributeText, OptionsManager.getTextLeftPadding() , OptionsManager.getTextTopPadding() + height + thisAttributeHeight);
			thisAttributeWidth += OptionsManager.getTextXTotalPadding();
			thisAttributeHeight += OptionsManager.getTextYTotalPadding();
			width  = thisAttributeWidth > width ? thisAttributeWidth : width;
			height += thisAttributeHeight;	
			
			attributeGfxObjects.put(attributeText, attribute);
			lastGfxObject = attributeText;
		}		
		width += OptionsManager.getRectangleXTotalPadding();
		height += OptionsManager.getRectangleYTotalPadding();
		
		Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}
	@Override
	public void setClassWidth(int width) {
		this.classWidth = width;
	}
	@Override
	public void edit() {
		Attribute attributeToCreate = new Attribute("String", "attribute");
		attributes.add(attributeToCreate);
		classArtifact.rebuildGfxObject();
		edit(lastGfxObject, 0, 0);
	}
	@Override
	public void edit(GfxObject gfxObject, int x, int y) {
		Attribute attributeToChange = attributeGfxObjects.get(gfxObject);
		if(attributeToChange == null) edit();
		else {
		AttributePartEditor editor = new AttributePartEditor(canvas, this, attributeToChange);
		editor.startEdition(attributeToChange.toString(),  (classArtifact.getX() + OptionsManager.getTextLeftPadding() + OptionsManager.getRectangleLeftPadding()),
				 (classArtifact.getY() + classArtifact.className.getHeight() + GfxManager.getPlatform().getYFor(gfxObject) - GfxManager.getPlatform().getHeightFor(gfxObject) + OptionsManager.getRectangleTopPadding()), 
				classWidth - OptionsManager.getTextXTotalPadding() - OptionsManager.getRectangleXTotalPadding(), false);
		}
	}
	@Override
	public int[] getOpaque() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GfxObject getOutline() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public LinkedHashMap<String, Command> getRightMenu() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void moveTo(int fx, int fy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void select() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unselect() {
		// TODO Auto-generated method stub
		
	}
}
