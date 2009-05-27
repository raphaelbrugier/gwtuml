/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.NoteFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNote;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.QualityLevel;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This artifact represent a note
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class NoteArtifact extends BoxArtifact {
    GfxObject borderPath;
    GfxObject contentText;
    GfxObject cornerPath;
    int height;
    int width;
    private final UMLNote note;

    /**
     * Constructor of the NoteArtifact
     * @param content 
     * 			The text contained by the note
     */
    public NoteArtifact(final String content) {
	super(true);
	this.height = 0;
	this.width = 0;
	this.note = new UMLNote(content);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final NoteFieldEditor editor = new NoteFieldEditor(this.canvas, this);
	editor.setHeightForMultiLine(this.height
		- OptionsManager.get("TextTopPadding") - OptionsManager.get("TextBottomPadding")
		- OptionsManager.get("RectangleTopPadding") - OptionsManager.get("RectangleBottomPadding"));
	editor.startEdition(this.note.getText(), getLocation().getX()
		+ OptionsManager.get("TextLeftPadding")
		+ OptionsManager.get("RectangleLeftPadding"), getLocation()
		.getY()
		+ OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding")
		+ OptionsManager.get("RectangleTopPadding"), this.width
		- OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding")
		- OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), true, false);

    }

    /**
     * Getter for the content
     * 
     * @return The content of the note
     */
    public String getContent() {
	return this.note.getText();
    }

    @Override
    public int getHeight() {
	return this.height;
    }

    @Override
    public int[] getOpaque() {
	final int[] opaque = new int[] { getLocation().getX(),
		getLocation().getY(), getLocation().getX(),
		getLocation().getY() + getHeight(),
		getLocation().getX() + getWidth(),
		getLocation().getY() + getHeight(),
		getLocation().getX() + getWidth(),
		getLocation().getY() + getCornerHeight(),
		getLocation().getX() + getWidth() - getCornerWidth(),
		getLocation().getY() };
	return opaque;
    }

    @Override
    public GfxObject getOutline() {
	if (QualityLevel.IsAlmost(QualityLevel.NORMAL)) {
	    final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
	    final GfxObject outlineBorderPath = getBorderPath();
	    final GfxObject outlineCornerPath = getCornerPath();
	    GfxManager.getPlatform().addToVirtualGroup(vg, outlineBorderPath);
	    GfxManager.getPlatform().addToVirtualGroup(vg, outlineCornerPath);
	    GfxManager.getPlatform().setStrokeStyle(outlineBorderPath,
		    GfxStyle.DASH);
	    GfxManager.getPlatform().setStrokeStyle(outlineCornerPath,
		    GfxStyle.DASH);
	    GfxManager.getPlatform().setStroke(outlineBorderPath,
		    ThemeManager.getTheme().getNoteHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setStroke(outlineCornerPath,
		    ThemeManager.getTheme().getNoteHighlightedForegroundColor(), 1);
	    return vg;
	}
	return super.getOutline();

    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Note");
	rightMenu.addItem("Edit content", editCommand());
	return rightMenu;
    }

    @Override
    public int getWidth() {
	return this.width;
    }

    @Override
    protected void select() {
	super.select();
	GfxManager.getPlatform().setStroke(this.borderPath,
		ThemeManager.getTheme().getNoteHighlightedForegroundColor(), 2);
	GfxManager.getPlatform().setStroke(this.cornerPath,
		ThemeManager.getTheme().getNoteHighlightedForegroundColor(), 2);
    }

    /**
     * Setter for the content
     * The graphical object MUST be rebuilt to reflect the changes 
     * 
     * @param content The new content of the note
     */
    public void setContent(final String content) {
	this.note.setText(content);
    }

    @Override
    public void unselect() {
	super.unselect();
	GfxManager.getPlatform().setStroke(this.borderPath,
		ThemeManager.getTheme().getNoteForegroundColor(), 1);
	GfxManager.getPlatform().setStroke(this.cornerPath,
		ThemeManager.getTheme().getNoteForegroundColor(), 1);
    }

    void createNoteText() {
	this.height = 0;
	this.width = 0;
	final String[] noteMultiLine = this.note.getText().split("\n");
	this.contentText = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.contentText);

	for (final String noteLine : noteMultiLine) {
	    final GfxObject textLine = GfxManager.getPlatform().buildText(noteLine, 
		    new Point(OptionsManager.get("TextLeftPadding"),
			    OptionsManager.get("TextTopPadding") + this.height));
	    GfxManager.getPlatform().addToVirtualGroup(this.contentText, textLine);
	    GfxManager.getPlatform().setFont(textLine, OptionsManager.getSmallFont());
	    GfxManager.getPlatform().setStroke(textLine, ThemeManager.getTheme().getNoteBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(textLine, ThemeManager.getTheme().getNoteForegroundColor());
	    int thisLineWidth = GfxManager.getPlatform().getTextWidthFor(textLine);
	    int thisLineHeight = GfxManager.getPlatform().getTextHeightFor(textLine);
	    thisLineWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
	    thisLineHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
	    this.width = thisLineWidth > this.width ? thisLineWidth : this.width;
	    this.height += thisLineHeight;
	}
	this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");
	this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding") + getCornerWidth();

    }

    @Override
    protected void buildGfxObject() {
	createNoteText();
	this.borderPath = getBorderPath();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.borderPath);
	this.cornerPath = getCornerPath();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.cornerPath);
	GfxManager.getPlatform().translate(this.contentText, new Point(
		OptionsManager.get("RectangleLeftPadding"),
		OptionsManager.get("RectangleTopPadding")));
	GfxManager.getPlatform().moveToFront(this.contentText);
    }

    protected GfxObject getBorderPath() {
	final GfxObject thisBorderPath = GfxManager.getPlatform().buildPath();
	GfxManager.getPlatform().moveTo(thisBorderPath, Point.getOrigin());
	GfxManager.getPlatform().lineTo(thisBorderPath,
		new Point(this.width - getCornerWidth(), 0));
	GfxManager.getPlatform().lineTo(thisBorderPath, new Point(this.width,
		getCornerHeight()));
	GfxManager.getPlatform().lineTo(thisBorderPath, new Point(this.width, this.height));
	GfxManager.getPlatform().lineTo(thisBorderPath, new Point(0, this.height));
	GfxManager.getPlatform().lineTo(thisBorderPath, Point.getOrigin());
	GfxManager.getPlatform().setFillColor(thisBorderPath,
		ThemeManager.getTheme().getNoteBackgroundColor());
	GfxManager.getPlatform().setStroke(thisBorderPath,
		ThemeManager.getTheme().getNoteForegroundColor(), 1);
	return thisBorderPath;
    }

    protected GfxObject getCornerPath() {
	final GfxObject thisCornerPath = GfxManager.getPlatform().buildPath();
	GfxManager.getPlatform().moveTo(thisCornerPath,
		new Point(this.width - getCornerWidth(), 0));
	GfxManager.getPlatform().lineTo(thisCornerPath,
		new Point(this.width - getCornerWidth(), getCornerHeight()));
	GfxManager.getPlatform().lineTo(thisCornerPath, new Point(this.width,
		getCornerHeight()));
	GfxManager.getPlatform().setFillColor(thisCornerPath,
		ThemeManager.getTheme().getNoteBackgroundColor());
	GfxManager.getPlatform().setStroke(thisCornerPath,
		ThemeManager.getTheme().getNoteForegroundColor(), 1);
	return thisCornerPath;
    }

    private Command editCommand() {
	return new Command() {
	    public void execute() {
		edit(null);
	    }
	};
    }

    private int getCornerHeight() {
	return OptionsManager.get("NoteCornerHeight");
    }

    private int getCornerWidth() {
	return OptionsManager.get("NoteCornerWidth");
    }

     /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	return "Note$" + this.getLocation() + "!" + this.note.getText();
    }
}
