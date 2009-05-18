package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.ObjectPartAttributesEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This object represent the middle Part of a {@link NodeArtifact}
 * It can hold an attribute list
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ObjectPartAttributesArtifact extends NodePartArtifact {
    private final Map<GfxObject, UMLObjectAttribute> attributeGfxObjects;
    private GfxObject attributeRect;
    private final List<UMLObjectAttribute> attributes;
    private GfxObject lastGfxObject;
    
    /**
     * Constructor of ObjectPartAttributesArtifact
     * It initializes the attribute list
     * 
     */
    public ObjectPartAttributesArtifact() {
	super();
	this.attributes = new ArrayList<UMLObjectAttribute>();
	this.attributeGfxObjects = new LinkedHashMap<GfxObject, UMLObjectAttribute>();
	this.height = 0;
	this.width = 0;
    }

    /**
     * Add an attribute to the current attribute list.
     * The graphical object must be rebuilt to reflect the changes
     * 
     * @param attribute The new attribute to add
     */
    public void add(final UMLObjectAttribute attribute) {
	this.attributes.add(attribute);
    }

    @Override
    public void buildGfxObject() {
	if (this.textVirtualGroup == null) {
	    computeBounds();
	}
	this.attributeRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.attributeRect);
	GfxManager.getPlatform().setFillColor(this.attributeRect,
		ThemeManager.getTheme().getBackgroundColor());
	GfxManager.getPlatform().setStroke(this.attributeRect,
		ThemeManager.getTheme().getForegroundColor(), 1);
	GfxManager.getPlatform().translate(this.textVirtualGroup, new Point(
		OptionsManager.get("RectangleLeftPadding"),
		OptionsManager.get("RectangleTopPadding")));
	GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	this.attributeGfxObjects.clear();
	this.height = 0;
	this.width = 0;
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);

	for (final UMLObjectAttribute attribute : this.attributes) {
	    final GfxObject attributeText = GfxManager.getPlatform().buildText(
		    attribute.toString(), new Point(
			    OptionsManager.get("TextLeftPadding"),
			    OptionsManager.get("TextTopPadding") + this.height
				    ));
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    attributeText);
	    GfxManager.getPlatform().setFont(attributeText,
		    OptionsManager.getSmallFont());
	    GfxManager.getPlatform().setStroke(attributeText,
		    ThemeManager.getTheme().getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(attributeText,
		    ThemeManager.getTheme().getForegroundColor());
	    int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(
		    attributeText);
	    int thisAttributeHeight = GfxManager.getPlatform().getTextHeightFor(
		    attributeText);
	    thisAttributeWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
	    thisAttributeHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
	    this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
	    this.height += thisAttributeHeight;

	    this.attributeGfxObjects.put(attributeText, attribute);
	    this.lastGfxObject = attributeText;
	}
	this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
	this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ this.width + "x" + this.height);
    }

    @Override
    public void edit() {
	final UMLObjectAttribute attributeToCreate = new UMLObjectAttribute(UMLVisibility.PROTECTED,
		"String", "attribute", "\"null\"");
	this.attributes.add(attributeToCreate);
	this.nodeArtifact.rebuildGfxObject();
	this.attributeGfxObjects.put(this.lastGfxObject, attributeToCreate);
	edit(this.lastGfxObject);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final UMLObjectAttribute attributeToChange = this.attributeGfxObjects
		.get(editedGfxObject);
	if (attributeToChange == null) {
	    edit();
	} else {
	    final ObjectPartAttributesEditor editor = new ObjectPartAttributesEditor(this.canvas,
		    this, attributeToChange);
	    editor
		    .startEdition(
			    attributeToChange.toString(),
			    (this.nodeArtifact.getLocation().getX()
				    + OptionsManager.get("TextLeftPadding") + OptionsManager
				    .get("RectangleLeftPadding")), (this.nodeArtifact
				    .getLocation().getY()
				    + ((ObjectArtifact) this.nodeArtifact).objectName.getHeight()
				    + GfxManager.getPlatform().getLocationFor(
					    editedGfxObject).getY() + OptionsManager
				    .get("RectangleTopPadding")), this.nodeWidth
				    - OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding")
				    - OptionsManager
					    .get("RectangleXTotalPadding"), false, true);
	}
    }

    @Override
    public int getHeight() {
	return this.height;
    }

    /**
     * Getter for the attribute list
     * 
     * @return The current attribute list
     */
    public List<UMLObjectAttribute> getList() {
	return this.attributes;
    }

    @Override
    public int[] getOpaque() {
	return null;
    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Attributes");

	for (final Entry<GfxObject, UMLObjectAttribute> attribute : this.attributeGfxObjects
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
    
    /**
     * Remove an attribute to the current attribute list.
     * The graphical object must be rebuilt to reflect the changes
     * 
     * @param attribute The attribute to be removed
     */
    public void remove(final UMLObjectAttribute attribute) {
	this.attributes.remove(attribute);
    }

    @Override
    protected void select() {
	GfxManager.getPlatform().setStroke(this.attributeRect,
		ThemeManager.getTheme().getHighlightedForegroundColor(), 2);
    }

    @Override
    void setNodeWidth(final int width) {
	this.nodeWidth = width;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.attributeRect,
		ThemeManager.getTheme().getForegroundColor(), 1);
    }

    private Command deleteCommand(final UMLObjectAttribute attribute) {
	return new Command() {
	    public void execute() {
		remove(attribute);
		ObjectPartAttributesArtifact.this.nodeArtifact
			.rebuildGfxObject();
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

    private Command editCommand(final GfxObject gfxo) {
	return new Command() {
	    public void execute() {
		edit(gfxo);
	    }
	};
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	StringBuilder attributesURL = new StringBuilder();
	for(UMLObjectAttribute attribute : this.attributes) {
	    attributesURL.append(attribute);
	    attributesURL.append("%");
	}
	return attributesURL.toString();
    }
}
