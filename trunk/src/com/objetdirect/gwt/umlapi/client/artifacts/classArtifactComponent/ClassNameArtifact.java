package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.NamePartFieldEditor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author fmounier
 */
public class ClassNameArtifact extends ClassPartArtifact {

    private String className;
    private GfxObject nameRect;
    private GfxObject nameText;
    private String stereotype;
    private GfxObject stereotypeText;

    public ClassNameArtifact(final String className) {
	this.className = className;
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
		this.textVirtualGroup,
		OptionsManager.getRectangleLeftPadding() + (this.classWidth - this.width)
			/ 2, OptionsManager.getRectangleTopPadding());
	GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	this.height = 0;
	this.width = 0;
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);
	if (this.stereotype != null) {
	    this.stereotypeText = GfxManager.getPlatform().buildText(this.stereotype);
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    this.stereotypeText);
	    GfxManager.getPlatform().setFont(this.stereotypeText,
		    OptionsManager.getSmallCapsFont());
	    GfxManager.getPlatform().setStroke(this.stereotypeText,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(this.stereotypeText,
		    ThemeManager.getForegroundColor());
	    this.width = GfxManager.getPlatform().getWidthFor(this.stereotypeText);
	    this.height += GfxManager.getPlatform().getHeightFor(this.stereotypeText);

	    GfxManager.getPlatform().translate(this.stereotypeText,
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + this.height);

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
	final int thisAttributeWidth = GfxManager.getPlatform().getWidthFor(
		this.nameText)
		+ OptionsManager.getTextXTotalPadding();
	this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
	this.height += GfxManager.getPlatform().getHeightFor(this.nameText);
	GfxManager.getPlatform().translate(this.nameText,
		OptionsManager.getTextLeftPadding(),
		OptionsManager.getTextTopPadding() + this.height);
	this.height += OptionsManager.getTextYTotalPadding();

	this.width += OptionsManager.getRectangleXTotalPadding();
	this.height += OptionsManager.getRectangleYTotalPadding();
	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ this.width + "x" + this.height);
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
		ClassNameArtifact.this.stereotype = null;
		ClassNameArtifact.this.classArtifact.rebuildGfxObject();
	    }
	};
    }

    @Override
    public void edit() {
	if (this.stereotype == null) {
	    this.stereotype = "<<Abstract>>";
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
	    edited = this.stereotype;
	} else {
	    edited = this.className;
	}
	editor.startEdition(edited, (this.classArtifact.getX()
		+ OptionsManager.getTextLeftPadding() + OptionsManager
		.getRectangleLeftPadding()),
		(this.classArtifact.getY()
			+ GfxManager.getPlatform().getYFor(editedGfxObject)
			- GfxManager.getPlatform()
				.getHeightFor(editedGfxObject) + OptionsManager
			.getRectangleTopPadding()), this.classWidth
			- OptionsManager.getTextXTotalPadding()
			- OptionsManager.getRectangleXTotalPadding(), false);
    }

    private Command editCommand(final GfxObject gfxo) {
	return new Command() {
	    public void execute() {
		edit(gfxo);
	    }
	};
    }

    public String getClassName() {
	return this.className;
    }

    @Override
    public int getHeight() {
	return this.height;
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

    @Override
    public void select() {
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    public void setClassName(final String className) {
	this.className = className;
    }

    @Override
    public void setClassWidth(final int width) {
	this.classWidth = width;
    }

    @Override
    public void setHeight(final int height) {
	this.height = height;
    }

    /**
     * @param stereotype
     *            the stereotype to set
     */
    public void setStereotype(final String stereotype) {
	this.stereotype = stereotype;
    }

    @Override
    public void setWidth(final int width) {
	this.width = width;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getForegroundColor(), 1);
    }

}
