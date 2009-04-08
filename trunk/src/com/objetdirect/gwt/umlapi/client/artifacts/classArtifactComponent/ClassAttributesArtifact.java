/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.ArrayList;
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
 * @author fmounier
 */
public class ClassAttributesArtifact extends ClassPartArtifact {
    private final Map<GfxObject, Attribute> attributeGfxObjects;
    private GfxObject attributeRect;
    private final List<Attribute> attributes;
    private GfxObject lastGfxObject;

    public ClassAttributesArtifact() {
	this.attributes = new ArrayList<Attribute>();
	this.attributeGfxObjects = new LinkedHashMap<GfxObject, Attribute>();
	this.height = 0;
	this.width = 0;
    }

    public void add(final Attribute attribute) {
	this.attributes.add(attribute);
    }

    @Override
    public void buildGfxObject() {
	if (this.textVirtualGroup == null) {
	    computeBounds();
	}
	this.attributeRect = GfxManager.getPlatform().buildRect(this.classWidth, this.height);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.attributeRect);
	GfxManager.getPlatform().setFillColor(this.attributeRect,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(this.attributeRect,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().translate(this.textVirtualGroup,
		OptionsManager.getRectangleLeftPadding(),
		OptionsManager.getRectangleTopPadding());
	GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	this.attributeGfxObjects.clear();
	this.height = 0;
	this.width = 0;
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);

	for (final Attribute attribute : this.attributes) {
	    final GfxObject attributeText = GfxManager.getPlatform().buildText(
		    attribute.toString());
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    attributeText);
	    GfxManager.getPlatform().setFont(attributeText,
		    OptionsManager.getFont());
	    GfxManager.getPlatform().setStroke(attributeText,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(attributeText,
		    ThemeManager.getForegroundColor());
	    int thisAttributeWidth = GfxManager.getPlatform().getWidthFor(
		    attributeText);
	    int thisAttributeHeight = GfxManager.getPlatform().getHeightFor(
		    attributeText);
	    GfxManager.getPlatform().translate(
		    attributeText,
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + this.height
			    + thisAttributeHeight);
	    thisAttributeWidth += OptionsManager.getTextXTotalPadding();
	    thisAttributeHeight += OptionsManager.getTextYTotalPadding();
	    this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
	    this.height += thisAttributeHeight;

	    this.attributeGfxObjects.put(attributeText, attribute);
	    this.lastGfxObject = attributeText;
	}
	this.width += OptionsManager.getRectangleXTotalPadding();
	this.height += OptionsManager.getRectangleYTotalPadding();

	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ this.width + "x" + this.height);
    }

    private Command deleteCommand(final Attribute attribute) {
	return new Command() {
	    public void execute() {
		remove(attribute);
		ClassAttributesArtifact.this.classArtifact.rebuildGfxObject();
	    }
	};
    }

    @Override
    public void edit() {
	final Attribute attributeToCreate = new Attribute(Visibility.PROTECTED,
		"String", "attribute");
	this.attributes.add(attributeToCreate);
	this.classArtifact.rebuildGfxObject();
	this.attributeGfxObjects.put(this.lastGfxObject, attributeToCreate);
	edit(this.lastGfxObject);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final Attribute attributeToChange = this.attributeGfxObjects
		.get(editedGfxObject);
	if (attributeToChange == null) {
	    edit();
	} else {
	    final AttributePartEditor editor = new AttributePartEditor(this.canvas,
		    this, attributeToChange);
	    editor
		    .startEdition(
			    attributeToChange.toString(),
			    (this.classArtifact.getX()
				    + OptionsManager.getTextLeftPadding() + OptionsManager
				    .getRectangleLeftPadding()), (this.classArtifact
				    .getY()
				    + this.classArtifact.className.getHeight()
				    + GfxManager.getPlatform().getYFor(
					    editedGfxObject)
				    - GfxManager.getPlatform().getHeightFor(
					    editedGfxObject) + OptionsManager
				    .getRectangleTopPadding()), this.classWidth
				    - OptionsManager.getTextXTotalPadding()
				    - OptionsManager
					    .getRectangleXTotalPadding(), false);
	}
    }

    private Command editCommand() {
	return new Command() {
	    public void execute() {
		edit();
	    }
	};
    }

    private Command editCommand(final GfxObject gfxo) {
	return new Command() {
	    public void execute() {
		edit(gfxo);
	    }
	};
    }

    @Override
    public int getHeight() {
	return this.height;
    }

    public List<Attribute> getList() {
	return this.attributes;
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
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Attributes");

	for (final Entry<GfxObject, Attribute> attribute : this.attributeGfxObjects
		.entrySet()) {
	    final MenuBar subsubMenu = new MenuBar(true);
	    subsubMenu.addItem("Edit ", editCommand(attribute.getKey()));
	    subsubMenu.addItem("Delete ", deleteCommand(attribute.getValue()));
	    rightMenu.addItem(attribute.getValue().toString(), subsubMenu);
	}
	rightMenu.addItem("Add new", editCommand());
	return rightMenu;
    }

    @Override
    public int getWidth() {
	return this.width;
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
    public void moveTo(final int fx, final int fy) {
	// TODO Auto-generated method stub

    }

    public void remove(final Attribute attributeToChange) {
	this.attributes.remove(attributeToChange);
    }

    @Override
    public void select() {
	GfxManager.getPlatform().setStroke(this.attributeRect,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    @Override
    public void setClassWidth(final int width) {
	this.classWidth = width;
    }

    @Override
    public void setHeight(final int height) {
	this.height = height;
    }

    @Override
    public void setWidth(final int width) {
	this.width = width;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.attributeRect,
		ThemeManager.getForegroundColor(), 1);
    }

}