/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.ClassPartNameFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

/**
 * This class represent the upper Part of a {@link NodeArtifact}
 * It can hold a name and a stereotype
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartNameArtifact extends NodePartArtifact {

    private UMLClass uMLclass;
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
	super();
	this.uMLclass = new UMLClass(className);
	this.stereotype = stereotype.equals("") ? "" : "«" + stereotype + "»";
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
		ThemeManager.getTheme().getClassBackgroundColor());
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getTheme().getClassForegroundColor(), 1);

	// Centering name class :
	GfxManager.getPlatform().translate(
		this.nameText, new Point(
		 (this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding"))
			/ 2, OptionsManager.get("RectangleTopPadding")));
	if(this.stereotypeText != null) {
	GfxManager.getPlatform().translate(
		this.stereotypeText, new Point(
			(this.nodeWidth -  GfxManager.getPlatform().getTextWidthFor(this.stereotypeText) - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding"))
			/ 2, OptionsManager.get("RectangleTopPadding")));
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
		    OptionsManager.get("TextLeftPadding"),
		    OptionsManager.get("TextTopPadding")));
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    this.stereotypeText);
	    GfxManager.getPlatform().setFont(this.stereotypeText,
		    OptionsManager.getFont());
	    GfxManager.getPlatform().setStroke(this.stereotypeText,
		    ThemeManager.getTheme().getClassBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(this.stereotypeText,
		    ThemeManager.getTheme().getClassForegroundColor());
	    this.width = GfxManager.getPlatform().getTextWidthFor(this.stereotypeText);
	    this.height = GfxManager.getPlatform().getTextHeightFor(this.stereotypeText);
	    this.width += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
	    this.height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
	}
	this.nameText = GfxManager.getPlatform().buildText(this.uMLclass.getName(),  new Point(
		OptionsManager.get("TextLeftPadding"),
		OptionsManager.get("TextTopPadding") + this.height));
	GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, this.nameText);
	GfxManager.getPlatform().setFont(this.nameText,
		OptionsManager.getFont());
	GfxManager.getPlatform().setStroke(this.nameText,
		ThemeManager.getTheme().getClassBackgroundColor(), 0);
	GfxManager.getPlatform().setFillColor(this.nameText,
		ThemeManager.getTheme().getClassForegroundColor());
	final int thisNameWidth = GfxManager.getPlatform().getTextWidthFor(
		this.nameText)
		+ OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
	this.width = thisNameWidth > this.width ? thisNameWidth : this.width;
	this.height += GfxManager.getPlatform().getTextHeightFor(this.nameText);
	this.height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
	this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
	this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");
	
	
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
	final ClassPartNameFieldEditor editor = new ClassPartNameFieldEditor(this.canvas,
		this, isTheStereotype);
	String edited;
	if (isTheStereotype) {
	    edited = this.stereotype.replaceAll("»", "").replaceAll("«", "");
	} else {
	    edited = this.uMLclass.getName();
	}
	editor.startEdition(edited, (this.nodeArtifact.getLocation().getX()
		+ OptionsManager.get("TextLeftPadding") + OptionsManager
		.get("RectangleLeftPadding")),
		this.nodeArtifact.getLocation().getY()
			+ GfxManager.getPlatform().getLocationFor(editedGfxObject).getY() /*+ OptionsManager
			.get("RectangleTopPadding")*/, this.nodeWidth
			- OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding")
			- OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), false, false);
    }

    /**
     * Getter for the class name
     * 
     * @return The class name
     */
    public String getClassName() {
	return this.uMLclass.getName();
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
	return this.stereotype.replaceAll("»", "").replaceAll("«", "");
    }

    @Override
    public int getWidth() {
	return this.width;
    }

    @Override
    protected void select() {
	super.select();
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
    }

    /**
     * Setter for the class name
     * 
     * @param className 
     * 			The new name of the class 
     */
    public void setClassName(final String className) {
	this.uMLclass.setName(className);
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
	super.unselect();
	GfxManager.getPlatform().setStroke(this.nameRect,
		ThemeManager.getTheme().getClassForegroundColor(), 1);
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
		ClassPartNameArtifact.this.nodeArtifact.rebuildGfxObject();
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
	return this.getClassName() + "!" + this.getStereotype();
    }
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#getOutline()
     */
    @Override
    public GfxObject getOutline() {
	final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
	final GfxObject rect = GfxManager.getPlatform().buildRect(this.nodeWidth, getHeight());
	GfxManager.getPlatform().setStrokeStyle(rect, GfxStyle.DASH);
	GfxManager.getPlatform().setStroke(rect,
		ThemeManager.getTheme().getClassHighlightedForegroundColor(), 1);
	GfxManager.getPlatform().setFillColor(rect,
		ThemeManager.getTheme().getClassBackgroundColor());
	GfxManager.getPlatform().addToVirtualGroup(vg, rect);
	return vg;
    }
}
