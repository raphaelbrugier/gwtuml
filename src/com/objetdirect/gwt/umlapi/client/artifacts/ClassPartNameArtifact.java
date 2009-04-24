package com.objetdirect.gwt.umlapi.client.artifacts;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.NamePartFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This class represent the upper Part of a {@link ClassArtifact}
 * It can hold a name and a stereotype
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartNameArtifact extends ClassPartArtifact {

    private String className;
    private GfxObject nameRect;
    private GfxObject nameText;
    private String stereotype;
    private GfxObject stereotypeText;
    /**
     * Constructor of ClassPartNameArtifact with only class name
     * 
     * @param className The name of the class
     */
    public ClassPartNameArtifact(final String className) {
	this(className, "");
    }
    /**
     * Constructor of ClassPartNameArtifact with class name and stereotype
     * 
     * @param className The name of the class
     * @param stereotype The stereotype associated with the class
     */
    public ClassPartNameArtifact(final String className, final String stereotype) {
	this.className = className;
	this.stereotype = stereotype == "" ? "" : "«" + stereotype + "»";
	this.height = 0;
	this.width = 0;
    }

    @Override
    public void buildGfxObject() {
	if (this.textVirtualGroup == null) {
	    computeBounds();
	}
	this.nameRect = GfxManager.getPlatform().buildRect(this.classWidth, this.height);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.nameRect);
	GfxManager.getPlatform().setFillColor(this.nameRect,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getForegroundColor(), 1);

	// Centering name class :
	GfxManager.getPlatform().translate(
		this.textVirtualGroup, new Point(
		OptionsManager.getRectangleLeftPadding() + (this.classWidth - this.width)
			/ 2, OptionsManager.getRectangleTopPadding()));
	GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	this.height = 0;
	this.width = 0;
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);
	if (this.stereotype != null && this.stereotype != "") {
	    this.stereotypeText = GfxManager.getPlatform().buildText(this.stereotype);
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    this.stereotypeText);
	    GfxManager.getPlatform().setFont(this.stereotypeText,
		    OptionsManager.getSmallCapsFont());
	    GfxManager.getPlatform().setStroke(this.stereotypeText,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(this.stereotypeText,
		    ThemeManager.getForegroundColor());
	    this.width = GfxManager.getPlatform().getTextWidthFor(this.stereotypeText);
	    this.height += GfxManager.getPlatform().getTextHeightFor(this.stereotypeText);

	    GfxManager.getPlatform().translate(this.stereotypeText, new Point(
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + this.height));

	    this.width += OptionsManager.getTextXTotalPadding();
	    this.height += OptionsManager.getTextYTotalPadding();
	}
	this.nameText = GfxManager.getPlatform().buildText(this.className);
	GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, this.nameText);
	GfxManager.getPlatform().setFont(this.nameText,
		OptionsManager.getSmallCapsFont());
	GfxManager.getPlatform().setStroke(this.nameText,
		ThemeManager.getBackgroundColor(), 0);
	GfxManager.getPlatform().setFillColor(this.nameText,
		ThemeManager.getForegroundColor());
	final int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(
		this.nameText)
		+ OptionsManager.getTextXTotalPadding();
	this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
	this.height += GfxManager.getPlatform().getTextHeightFor(this.nameText);
	GfxManager.getPlatform().translate(this.nameText, new Point(
		OptionsManager.getTextLeftPadding(),
		OptionsManager.getTextTopPadding() + this.height));
	this.height += OptionsManager.getTextYTotalPadding();
	if(this.stereotypeText != null) GfxManager.getPlatform().translate(this.stereotypeText, new Point((this.width - OptionsManager.getTextXTotalPadding() - GfxManager.getPlatform().getTextWidthFor(this.stereotypeText))/2,0));
	this.width += OptionsManager.getRectangleXTotalPadding();
	this.height += OptionsManager.getRectangleYTotalPadding();
	
	
	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ this.width + "x" + this.height);
    }

    @Override
    public void edit() {
	if (this.stereotype == null) {
	    this.stereotype = "Abstract";
	    this.classArtifact.rebuildGfxObject();
	    edit(this.stereotypeText);
	} else {
	    edit(this.nameText);
	}

    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final boolean isTheStereotype = editedGfxObject.equals(this.stereotypeText);
	final NamePartFieldEditor editor = new NamePartFieldEditor(this.canvas,
		this, isTheStereotype);
	String edited;
	if (isTheStereotype) {
	    edited = this.stereotype.replaceAll("»", "").replaceAll("«", "");
	} else {
	    edited = this.className;
	}
	editor.startEdition(edited, (this.classArtifact.getLocation().getX()
		+ OptionsManager.getTextLeftPadding() + OptionsManager
		.getRectangleLeftPadding()),
		(this.classArtifact.getLocation().getY()
			+ GfxManager.getPlatform().getLocationFor(editedGfxObject).getY()
			- GfxManager.getPlatform()
				.getTextHeightFor(editedGfxObject) + OptionsManager
			.getRectangleTopPadding()), this.classWidth
			- OptionsManager.getTextXTotalPadding()
			- OptionsManager.getRectangleXTotalPadding(), false, false);
    }

    /**
     * Getter for the class name
     * 
     * @return The class name
     */
    public String getClassName() {
	return this.className;
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
    public GfxObject getOutline() {
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
    public void select() {
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    /**
     * Setter for the class name
     * 
     * @param className 
     * 			The new name of the class 
     */
    public void setClassName(final String className) {
	this.className = className;
    }

    @Override
    void setClassWidth(final int width) {
	this.classWidth = width;
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
		ThemeManager.getForegroundColor(), 1);
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
		ClassPartNameArtifact.this.stereotype = null;
		ClassPartNameArtifact.this.classArtifact.rebuildGfxObject();
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
