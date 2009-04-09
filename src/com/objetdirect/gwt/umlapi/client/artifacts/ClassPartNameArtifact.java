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
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartNameArtifact extends ClassPartArtifact {

    private String className;
    private GfxObject nameRect;
    private GfxObject nameText;
    private String stereotype;
    private GfxObject stereotypeText;

    public ClassPartNameArtifact(final String className) {
	this.className = className;
	height = 0;
	width = 0;
    }

    @Override
    public void buildGfxObject() {
	if (textVirtualGroup == null) {
	    computeBounds();
	}
	nameRect = GfxManager.getPlatform().buildRect(classWidth, height);
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, nameRect);
	GfxManager.getPlatform().setFillColor(nameRect,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(nameRect,
		ThemeManager.getForegroundColor(), 1);

	// Centering name class :
	GfxManager.getPlatform().translate(
		textVirtualGroup, new Point(
		OptionsManager.getRectangleLeftPadding() + (classWidth - width)
			/ 2, OptionsManager.getRectangleTopPadding()));
	GfxManager.getPlatform().moveToFront(textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	height = 0;
	width = 0;
	textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);
	if (stereotype != null) {
	    stereotypeText = GfxManager.getPlatform().buildText(stereotype);
	    GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup,
		    stereotypeText);
	    GfxManager.getPlatform().setFont(stereotypeText,
		    OptionsManager.getSmallCapsFont());
	    GfxManager.getPlatform().setStroke(stereotypeText,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(stereotypeText,
		    ThemeManager.getForegroundColor());
	    width = GfxManager.getPlatform().getWidthFor(stereotypeText);
	    height += GfxManager.getPlatform().getHeightFor(stereotypeText);

	    GfxManager.getPlatform().translate(stereotypeText, new Point(
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + height));

	    width += OptionsManager.getTextXTotalPadding();
	    height += OptionsManager.getTextYTotalPadding();
	}
	nameText = GfxManager.getPlatform().buildText(className);
	GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, nameText);
	GfxManager.getPlatform().setFont(nameText,
		OptionsManager.getSmallCapsFont());
	GfxManager.getPlatform().setStroke(nameText,
		ThemeManager.getBackgroundColor(), 0);
	GfxManager.getPlatform().setFillColor(nameText,
		ThemeManager.getForegroundColor());
	final int thisAttributeWidth = GfxManager.getPlatform().getWidthFor(
		nameText)
		+ OptionsManager.getTextXTotalPadding();
	width = thisAttributeWidth > width ? thisAttributeWidth : width;
	height += GfxManager.getPlatform().getHeightFor(nameText);
	GfxManager.getPlatform().translate(nameText, new Point(
		OptionsManager.getTextLeftPadding(),
		OptionsManager.getTextTopPadding() + height));
	height += OptionsManager.getTextYTotalPadding();

	width += OptionsManager.getRectangleXTotalPadding();
	height += OptionsManager.getRectangleYTotalPadding();
	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ width + "x" + height);
    }

    @Override
    public void edit() {
	if (stereotype == null) {
	    stereotype = "<<Abstract>>";
	    classArtifact.rebuildGfxObject();
	    edit(stereotypeText);
	} else {
	    edit(nameText);
	}

    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final boolean isTheStereotype = editedGfxObject.equals(stereotypeText);
	final NamePartFieldEditor editor = new NamePartFieldEditor(canvas,
		this, isTheStereotype);
	String edited;
	if (isTheStereotype) {
	    edited = stereotype;
	} else {
	    edited = className;
	}
	editor.startEdition(edited, (classArtifact.getLocation().getX()
		+ OptionsManager.getTextLeftPadding() + OptionsManager
		.getRectangleLeftPadding()),
		(classArtifact.getLocation().getY()
			+ GfxManager.getPlatform().getLocationFor(editedGfxObject).getY()
			- GfxManager.getPlatform()
				.getHeightFor(editedGfxObject) + OptionsManager
			.getRectangleTopPadding()), classWidth
			- OptionsManager.getTextXTotalPadding()
			- OptionsManager.getRectangleXTotalPadding(), false);
    }

    public String getClassName() {
	return className;
    }

    @Override
    public int getHeight() {
	return height;
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
	rightMenu.addItem("Edit Name", editCommand(nameText));
	if (stereotypeText == null) {
	    rightMenu.addItem("Add stereotype", createStereotype());
	} else {
	    rightMenu.addItem("Edit Stereotype", editCommand(stereotypeText));
	    rightMenu.addItem("Delete Stereotype", deleteStereotype());
	}

	return rightMenu;
    }

    /**
     * @return the stereotype
     */
    public String getStereotype() {
	return stereotype;
    }

    @Override
    public int getWidth() {
	return width;
    }

    @Override
    public void select() {
	GfxManager.getPlatform().setStroke(nameRect,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    public void setClassName(final String className) {
	this.className = className;
    }

    @Override
    public void setClassWidth(final int width) {
	classWidth = width;
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
	GfxManager.getPlatform().setStroke(nameRect,
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
		stereotype = null;
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
