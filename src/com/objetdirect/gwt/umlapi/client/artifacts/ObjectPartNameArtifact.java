package com.objetdirect.gwt.umlapi.client.artifacts;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.ObjectPartNameFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This object represent the upper Part of a {@link NodeArtifact}
 * It can hold a name and a stereotype
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ObjectPartNameArtifact extends NodePartArtifact {

    private String objectName;
    private GfxObject nameRect;
    private GfxObject nameText;
    private String stereotype;
    private GfxObject stereotypeText;
    private GfxObject underline;
    /**
     * Constructor of ObjectPartNameArtifact with only object name
     * 
     * @param objectName The name of the object
     */
    public ObjectPartNameArtifact(final String objectName) {
	this(objectName, "");
    }
    /**
     * Constructor of ObjectPartNameArtifact with object name and stereotype
     * 
     * @param objectName The name of the object
     * @param stereotype The stereotype associated with the object
     */
    public ObjectPartNameArtifact(final String objectName, final String stereotype) {
	this.objectName = objectName;
	this.stereotype = stereotype == "" ? "" : "«" + stereotype + "»";
	this.height = 0;
	this.width = 0;
    }

    @Override
    public void buildGfxObject() {
	if (this.textVirtualGroup == null) {
	    computeBounds();
	}
	this.nameRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.nameRect);
	GfxManager.getPlatform().setFillColor(this.nameRect,
		ThemeManager.getTheme().getBackgroundColor());
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getTheme().getForegroundColor(), 1);

	// Centering name object :
	GfxManager.getPlatform().translate(
		this.nameText, new Point(
		 (this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.getTextXTotalPadding())
			/ 2, OptionsManager.getRectangleTopPadding()));
	GfxManager.getPlatform().translate(
		this.underline, new Point(
			(this.nodeWidth -  GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.getTextXTotalPadding())
			/ 2, OptionsManager.getRectangleTopPadding()));
	if(this.stereotypeText != null) {
	GfxManager.getPlatform().translate(
		this.stereotypeText, new Point(
			(this.nodeWidth -  GfxManager.getPlatform().getTextWidthFor(this.stereotypeText) - OptionsManager.getTextXTotalPadding())
			/ 2, OptionsManager.getRectangleTopPadding()));
	
	}
	GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	this.height = 0;
	this.width = 0;
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);
	if (this.stereotype != null && this.stereotype != "") {
	    this.stereotypeText = GfxManager.getPlatform().buildText(this.stereotype, new Point(
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding()));
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    this.stereotypeText);
	    GfxManager.getPlatform().setFont(this.stereotypeText,
		    OptionsManager.getFont());
	    GfxManager.getPlatform().setStroke(this.stereotypeText,
		    ThemeManager.getTheme().getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(this.stereotypeText,
		    ThemeManager.getTheme().getForegroundColor());
	    this.width = GfxManager.getPlatform().getTextWidthFor(this.stereotypeText);
	    this.height += GfxManager.getPlatform().getTextHeightFor(this.stereotypeText);
	    this.width += OptionsManager.getTextXTotalPadding();
	    this.height += OptionsManager.getTextYTotalPadding();
	}
	this.nameText = GfxManager.getPlatform().buildText(this.objectName, new Point(
		OptionsManager.getTextLeftPadding(),
		OptionsManager.getTextTopPadding() + this.height)/*, "underline" doesn't work yet in common browsers*/);
	GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, this.nameText);
	int yUnderline = this.height + GfxManager.getPlatform().getTextHeightFor(this.nameText) + OptionsManager.getTextTopPadding();
	this.underline = GfxManager.getPlatform().buildLine(new Point(OptionsManager.getTextLeftPadding(), yUnderline), new Point(OptionsManager.getTextLeftPadding() + GfxManager.getPlatform().getTextWidthFor(this.nameText), yUnderline));
	GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, this.underline);
	
	GfxManager.getPlatform().setFont(this.nameText,
		OptionsManager.getFont());
	GfxManager.getPlatform().setStroke(this.nameText,
		ThemeManager.getTheme().getBackgroundColor(), 0);
	GfxManager.getPlatform().setFillColor(this.nameText,
		ThemeManager.getTheme().getForegroundColor());
	GfxManager.getPlatform().setStroke(this.underline,
		ThemeManager.getTheme().getForegroundColor(), 1);
	GfxManager.getPlatform().setFillColor(this.underline,
		ThemeManager.getTheme().getForegroundColor());
	final int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(
		this.nameText)
		+ OptionsManager.getTextXTotalPadding();
	this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
	this.height += GfxManager.getPlatform().getTextHeightFor(this.nameText);
	this.height += OptionsManager.getTextYTotalPadding() + OptionsManager.getUnderlineShift();
	this.width += OptionsManager.getRectangleXTotalPadding();
	this.height += OptionsManager.getRectangleYTotalPadding();
	
	
	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ this.width + "x" + this.height);
    }

    @Override
    public void edit() {
	if (this.stereotype == null || this.stereotype.equals("")) {
	    this.stereotype = "«Abstract»";
	    this.nodeArtifact.rebuildGfxObject();
	    edit(this.stereotypeText);
	} else {
	    edit(this.nameText);
	}
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {	
	final boolean isTheStereotype = editedGfxObject.equals(this.stereotypeText);
	if(!isTheStereotype && !editedGfxObject.equals(this.nameText)) {
	    edit();
	    return;
	}
	final ObjectPartNameFieldEditor editor = new ObjectPartNameFieldEditor(this.canvas, this, isTheStereotype);
	String edited;
	if (isTheStereotype) {
	    edited = this.stereotype.replaceAll("»", "").replaceAll("«", "");
	} else {
	    edited = this.objectName;
	}
	editor.startEdition(edited, (this.nodeArtifact.getLocation().getX()
		+ OptionsManager.getTextLeftPadding() + OptionsManager
		.getRectangleLeftPadding()),
		(this.nodeArtifact.getLocation().getY()
			+ GfxManager.getPlatform().getLocationFor(editedGfxObject).getY() + OptionsManager
			.getRectangleTopPadding()), this.nodeWidth
			- OptionsManager.getTextXTotalPadding()
			- OptionsManager.getRectangleXTotalPadding(), false, false);
    }

    /**
     * Getter for the object name
     * 
     * @return The object name
     */
    public String getObjectName() {
	return this.objectName;
    }

    @Override
    public int getHeight() {
	return this.height;
    }

    @Override
    public int[] getOpaque() {
	return null;
    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Name");
	rightMenu.addItem("Edit Name", editCommand(this.nameText));
	if (this.stereotypeText == null) {
	    rightMenu.addItem("Add stereotype", createStereotype());
	} else {
	    rightMenu.addItem("Edit Stereotype", editCommand(this.stereotypeText));
	    rightMenu.addItem("Delete Stereotype", deleteStereotype());
	}

	return rightMenu;
    }

    /**
     * Getter for the stereotype
     * 
     * @return the stereotype
     */
    public String getStereotype() {
	return this.stereotype;
    }

    @Override
    public int getWidth() {
	return this.width;
    }

    @Override
    protected void select() {
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getTheme().getHighlightedForegroundColor(), 2);
    }

    /**
     * Setter for the object name
     * 
     * @param objectName 
     * 			The new name of the object 
     */
    public void setObjectName(final String objectName) {
	this.objectName = objectName;
    }

    @Override
    void setNodeWidth(final int width) {
	this.nodeWidth = width;
    }

    /**
     * @param stereotype
     *            the stereotype to set
     */
    public void setStereotype(final String stereotype) {
	this.stereotype = stereotype;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getTheme().getForegroundColor(), 1);
    }

    private Command createStereotype() {
	return new Command() {
	    public void execute() {
		edit();
	    }
	};
    }

    private Command deleteStereotype() {
	return new Command() {
	    public void execute() {
		ObjectPartNameArtifact.this.stereotype = null;
		ObjectPartNameArtifact.this.nodeArtifact.rebuildGfxObject();
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

}
