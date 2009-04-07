/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.AttributePartEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Visibility;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  fmounier
 */
public class ClassAttributesArtifact extends ClassPartArtifact {
	private List<Attribute> attributes;
	private Map<GfxObject, Attribute> attributeGfxObjects;
private GfxObject lastGfxObject;
	private GfxObject attributeRect;
	public ClassAttributesArtifact() {
		attributes = new ArrayList<Attribute>();
		attributeGfxObjects = new LinkedHashMap<GfxObject, Attribute>();
		height = 0;
		width = 0;
	}
	
	public void add(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public void remove(Attribute attributeToChange) {
		attributes.remove(attributeToChange);
	}
	
	@Override
	public void buildGfxObject() {
		if(textVirtualGroup == null) computeBounds();		
		attributeRect = GfxManager.getPlatform().buildRect(classWidth, height);
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
	    attributeGfxObjects.clear();
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);
		
		for (Attribute attribute : attributes) {
			GfxObject attributeText = GfxManager.getPlatform().buildText(attribute.toString());
			GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, attributeText);
			GfxManager.getPlatform().setFont(attributeText, OptionsManager.getFont());
			GfxManager.getPlatform().setStroke(attributeText, ThemeManager.getBackgroundColor(), 0);
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
		Attribute attributeToCreate = new Attribute(Visibility.PROTECTED, "String", "attribute");
		attributes.add(attributeToCreate);
		classArtifact.rebuildGfxObject();
		attributeGfxObjects.put(lastGfxObject, attributeToCreate);
		edit(lastGfxObject);
	}
	@Override
	public void edit(GfxObject gfxObject) {
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
    private Command editCommand(final GfxObject gfxo) {       
        return new Command() {
            public void execute() {
                edit(gfxo);
            }
        };
    }
    private Command deleteCommand(final Attribute attribute) {       
        return new Command() {
            public void execute() {
                remove(attribute);
                classArtifact.rebuildGfxObject();
            }
        };
    }
    private Command editCommand() {       
        return new Command() {
            public void execute() {
                edit();
            }
        };
    }
    @Override
    public MenuBarAndTitle getRightMenu() {
        MenuBarAndTitle rightMenu = new MenuBarAndTitle();
        rightMenu.setName("Attributes");

        for(Entry<GfxObject, Attribute> attribute : attributeGfxObjects.entrySet()) {
            MenuBar subsubMenu = new MenuBar(true);
            subsubMenu.addItem("Edit ", editCommand(attribute.getKey()));
            subsubMenu.addItem("Delete ", deleteCommand(attribute.getValue()));
            rightMenu.addItem(attribute.getValue().toString(), subsubMenu);
        }
        rightMenu.addItem("Add new", editCommand());
        return rightMenu;
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
		GfxManager.getPlatform().setStroke(attributeRect, ThemeManager.getHighlightedForegroundColor(), 2);
	}

	@Override
	public void unselect() {
		GfxManager.getPlatform().setStroke(attributeRect, ThemeManager.getForegroundColor(), 1);	
	}
	
}
