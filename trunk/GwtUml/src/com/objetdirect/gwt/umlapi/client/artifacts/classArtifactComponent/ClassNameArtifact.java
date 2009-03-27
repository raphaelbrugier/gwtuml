package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;
import java.util.LinkedHashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.NamePartFieldEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  fmounier
 */
public class ClassNameArtifact extends ClassPartArtifact {
	/**
	 * @uml.property  name="className"
	 */
	private String className;
	/**
	 * @uml.property  name="nameText"
	 * @uml.associationEnd  
	 */
	private GfxObject nameText;
	
	public ClassNameArtifact(String className) {
		this.className = className;
		height = 0;
		width = 0;
	}
	/**
	 * @return
	 * @uml.property  name="className"
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className
	 * @uml.property  name="className"
	 */
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
		GfxManager.getPlatform().translate(textVirtualGroup, OptionsManager.getRectangleLeftPadding() + (classWidth-width)/2, OptionsManager.getRectangleTopPadding());
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
		nameText = GfxManager.getPlatform().buildText(className);
		GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, nameText);
		
		GfxManager.getPlatform().setFont(nameText, OptionsManager.getSmallCapsFont());
		GfxManager.getPlatform().setFillColor(nameText,	ThemeManager.getForegroundColor());
		width  =  GfxManager.getPlatform().getWidthFor(nameText);
		height =  GfxManager.getPlatform().getHeightFor(nameText);
		
		GfxManager.getPlatform().translate(nameText, OptionsManager.getTextLeftPadding(), OptionsManager.getTextTopPadding() + height);
			
		width += OptionsManager.getTextXTotalPadding();
		height += OptionsManager.getTextYTotalPadding();		
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
		NamePartFieldEditor editor = new NamePartFieldEditor(canvas, this);
		editor.startEdition(className,  (classArtifact.getX() + OptionsManager.getTextLeftPadding() + OptionsManager.getRectangleLeftPadding()),
				 (classArtifact.getY() + OptionsManager.getTextTopPadding() + OptionsManager.getRectangleTopPadding()), 
				classWidth - OptionsManager.getTextXTotalPadding() - OptionsManager.getRectangleXTotalPadding(), false);
	}	
	
	@Override
	public void edit(GfxObject gfxObject, int x, int y) {
		edit();
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
