package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.MethodPartEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  fmounier
 */
public class ClassMethodsArtifact extends ClassPartArtifact {
	
	private List<Method> methods;
	private Map<GfxObject, Method> methodGfxObjects;
	/**
	 * @uml.property  name="lastGfxObject"
	 * @uml.associationEnd  
	 */
	private GfxObject lastGfxObject;
	private GfxObject methodRect;
	public ClassMethodsArtifact() {
		methods = new ArrayList<Method>();
		methodGfxObjects = new HashMap<GfxObject, Method>();
		//List<Parameter> methodParameters = new ArrayList<Parameter>();
		//methodParameters.add(new Parameter("String", "parameter1"));
		//methods.add(new Method("void","method", methodParameters));
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
		methodRect = GfxManager.getPlatform().buildRect(classWidth, height);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, methodRect);
		GfxManager.getPlatform().setFillColor(methodRect,	ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(methodRect, ThemeManager.getForegroundColor(), 1);	
		GfxManager.getPlatform().translate(textVirtualGroup, OptionsManager.getRectangleLeftPadding(), OptionsManager.getRectangleTopPadding());
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
			GfxManager.getPlatform().setFont(methodText, OptionsManager.getFont());

			GfxManager.getPlatform().setStroke(methodText, ThemeManager.getBackgroundColor(), 0);
			GfxManager.getPlatform().setFillColor(methodText, ThemeManager.getForegroundColor());
			int thisMethodWidth =  GfxManager.getPlatform().getWidthFor(methodText);
			int thisMethodHeight =  GfxManager.getPlatform().getHeightFor(methodText);
			
			GfxManager.getPlatform().translate(methodText, OptionsManager.getTextLeftPadding(), OptionsManager.getTextTopPadding() + height + thisMethodHeight);
			thisMethodWidth += OptionsManager.getTextXTotalPadding();
			thisMethodHeight += OptionsManager.getTextYTotalPadding();
			width  = thisMethodWidth > width ? thisMethodWidth : width;
			height += thisMethodHeight;
			
			methodGfxObjects.put(methodText, method);
			lastGfxObject = methodText;
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
		List<Parameter> methodToCreateParameters = new ArrayList<Parameter>();
		methodToCreateParameters.add(new Parameter("String", "parameter1"));
		methods.add(new Method("void","method", methodToCreateParameters));
		classArtifact.rebuildGfxObject();
		edit(lastGfxObject, 0, 0);
	}
	@Override
	public void edit(GfxObject gfxObject, int x, int y) {
		Method methodToChange = methodGfxObjects.get(gfxObject);
		if(methodToChange == null) edit();
		else {
		MethodPartEditor editor = new MethodPartEditor(canvas, this, methodToChange);
		editor.startEdition(methodToChange.toString(),  (classArtifact.getX() + OptionsManager.getTextLeftPadding() + OptionsManager.getRectangleLeftPadding()),
				 (classArtifact.getY() + classArtifact.className.getHeight() +  classArtifact.classAttributes.getHeight() + GfxManager.getPlatform().getYFor(gfxObject) - GfxManager.getPlatform().getHeightFor(gfxObject) + OptionsManager.getRectangleTopPadding() ), 
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
		GfxManager.getPlatform().setStroke(methodRect, ThemeManager.getHighlightedForegroundColor(), 2);
	}

	@Override
	public void unselect() {
		GfxManager.getPlatform().setStroke(methodRect, ThemeManager.getForegroundColor(), 1);	
	}
}
