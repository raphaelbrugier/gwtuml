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

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.LifeLineFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLifeLine;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.QualityLevel;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This artifact represent a time line
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class LifeLineArtifact extends BoxArtifact {
    int rectHeight;
    int width;
    private final UMLLifeLine uMLLifeLine;
    private GfxObject lifeLineText;
    private GfxObject lifeLineRect;
    private int lineLength = 0;
    private GfxObject lifeLineLine;
    /**
     * Constructor of the LifeLineArtifact
     * 
     * @param name The name of the LifeLine
     * 
     */
    public LifeLineArtifact(final String name) {
	this(name, "");
    }
    /**
     * Constructor of the LifeLineArtifact
     * 
     * @param name The name of the LifeLine
     * @param instance The instance of the LifeLine
     * 
     */
    public LifeLineArtifact(final String name, String instance) {
	super(true);
	this.rectHeight = 0;
	this.width = 0;
	this.uMLLifeLine = new UMLLifeLine(name, instance);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final LifeLineFieldEditor editor = new LifeLineFieldEditor(this.canvas, this);
	editor.setHeightForMultiLine(this.rectHeight
		- OptionsManager.get("TextTopPadding") - OptionsManager.get("TextBottomPadding")
		- OptionsManager.get("RectangleTopPadding") - OptionsManager.get("RectangleBottomPadding"));
	editor.startEdition(this.uMLLifeLine.toString(), getLocation().getX()
		+ OptionsManager.get("TextLeftPadding")
		+ OptionsManager.get("RectangleLeftPadding"), getLocation()
		.getY()
		+ OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding")
		+ OptionsManager.get("RectangleTopPadding"), this.width
		- OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding")
		- OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), false, false);

    }

    /**
     * Getter for the content
     * 
     * @return The content of the note
     */
    public String getContent() {
	return this.uMLLifeLine.toString();
    }

    @Override
    public int getHeight() {
	return this.rectHeight;
    }

    @Override
    public GfxObject getOutline() {
	if (QualityLevel.IsAlmost(QualityLevel.NORMAL)) {
	    final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();

	    GfxObject lifeLineRectOutline = GfxManager.getPlatform().buildRect(this.width, this.rectHeight);
	    GfxManager.getPlatform().addToVirtualGroup(vg, lifeLineRectOutline);
	    GfxManager.getPlatform().setStrokeStyle(lifeLineRectOutline, GfxStyle.DASH);
	    GfxManager.getPlatform().setStroke(lifeLineRectOutline,		    
		    ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(lifeLineRectOutline,
			ThemeManager.getTheme().getLifeLineBackgroundColor());
	    Point lineStart = new Point(this.width/2, this.rectHeight);
	    Point lineEnd = lineStart.clonePoint();
	    lineEnd.translate(0, this.lineLength);
	    GfxObject lifeLineLineOutline =  GfxManager.getPlatform().buildLine(lineStart, lineEnd);
	    GfxManager.getPlatform().addToVirtualGroup(vg, lifeLineLineOutline);

	    GfxManager.getPlatform().setStrokeStyle(lifeLineLineOutline, GfxStyle.DASH);
	    GfxManager.getPlatform().setStroke(lifeLineLineOutline,
		    ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 1);
	    return vg;
	}
	return super.getOutline();

    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("LifeLine");
	rightMenu.addItem("Edit name", editCommand());
	return rightMenu;
    }

    @Override
    public int getWidth() {
	return this.width;
    }

    @Override
    protected void select() {
	super.select();
	GfxManager.getPlatform().setStroke(this.lifeLineRect,
		ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
	GfxManager.getPlatform().setStroke(this.lifeLineLine,
		ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
    }

    /**
     * Setter for the content
     * The graphical object MUST be rebuilt to reflect the changes 
     * 
     * @param content The new content of the note
     */
    public void setName(final String content) {
	this.uMLLifeLine.setName(content);
    }

    @Override
    public void unselect() {
	super.unselect();
	GfxManager.getPlatform().setStroke(this.lifeLineRect,
		ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
	GfxManager.getPlatform().setStroke(this.lifeLineLine,
		ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
    }


    @Override
    protected void buildGfxObject() {
	this.rectHeight = 0;
	this.width = 0;
	this.lineLength = OptionsManager.get("LifeLineSpacing") * (getAllDirectionsDependenciesCount() + 3);
	this.lifeLineText = GfxManager.getPlatform().buildText(this.uMLLifeLine.toString(),  new Point(
		OptionsManager.get("RectangleLeftPadding") + OptionsManager.get("TextLeftPadding"),
		OptionsManager.get("RectangleTopPadding") + OptionsManager.get("TextTopPadding")));

	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.lifeLineText);
	GfxManager.getPlatform().setFont(this.lifeLineText,
		OptionsManager.getFont());
	GfxManager.getPlatform().setStroke(this.lifeLineText,
		ThemeManager.getTheme().getLifeLineBackgroundColor(), 0);
	GfxManager.getPlatform().setFillColor(this.lifeLineText,
		ThemeManager.getTheme().getLifeLineForegroundColor());
	this.width = GfxManager.getPlatform().getTextWidthFor(this.lifeLineText);
	this.rectHeight = GfxManager.getPlatform().getTextHeightFor(this.lifeLineText);
	this.width += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
	this.rectHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
	this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
	this.rectHeight += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

	this.lifeLineRect = GfxManager.getPlatform().buildRect(this.width, this.rectHeight);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.lifeLineRect);
	GfxManager.getPlatform().setFillColor(this.lifeLineRect,
		ThemeManager.getTheme().getLifeLineBackgroundColor());
	GfxManager.getPlatform().setStroke(this.lifeLineRect,
		ThemeManager.getTheme().getLifeLineForegroundColor(), 1);

	Point lineStart = new Point(this.width/2, this.rectHeight);
	Point lineEnd = lineStart.clonePoint();
	lineEnd.translate(0, this.lineLength);
	this.lifeLineLine =  GfxManager.getPlatform().buildLine(lineStart, lineEnd);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.lifeLineLine);
	GfxManager.getPlatform().setFillColor(this.lifeLineLine,
		ThemeManager.getTheme().getLifeLineBackgroundColor());
	GfxManager.getPlatform().setStroke(this.lifeLineLine,
		ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
	GfxManager.getPlatform().setStrokeStyle(this.lifeLineLine, GfxStyle.DASH);
	GfxManager.getPlatform().moveToFront(this.lifeLineText);
    }


    private Command editCommand() {
	return new Command() {
	    public void execute() {
		edit(null);
	    }
	};
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	return "LifeLine$" +  this.getLocation() + "!" + this.uMLLifeLine.toString();
    }
    
    /**
     * Get the instance name of the life line
     * 
     * @return the instance name of the life line
     */
    public String getInstance() {
	return this.uMLLifeLine.getInstance();
    }
    
    /**
     * Set the instance name of the life line
     * 
     * @param instance The new instance name of the life line
     */
    public void setInstance(String instance) {
	this.uMLLifeLine.setInstance(instance);
    }

}
