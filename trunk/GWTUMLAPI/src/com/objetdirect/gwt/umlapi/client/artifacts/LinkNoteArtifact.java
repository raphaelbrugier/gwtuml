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

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * This artifact represent a specific link between a note and any uml artifact
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class LinkNoteArtifact extends LinkArtifact {
    GfxObject line = null;
    NoteArtifact note;
    UMLArtifact target;

    /**
     * Constructor of LinkNoteArtifact
     * 
     * @param note The note the link is related to
     * @param target The uml artifact the note is pointing to
     */
    public LinkNoteArtifact(final NoteArtifact note, final UMLArtifact target) {
	super(note, target);
	this.note = note;
	this.note.addDependency(this, target);
	this.target = target;
	this.target.addDependency(this, note);
    }

    @Override
    public void buildGfxObject() {

	this.leftPoint = this.note.getCenter();
	this.rightPoint = this.target.getCenter();
	this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLinkNoteForegroundColor(), 1);
	GfxManager.getPlatform().setStrokeStyle(this.line, GfxStyle.DASH);
	GfxManager.getPlatform().moveToBack(this.gfxObject);

    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	// Nothing to edit
    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Note link");
	return rightMenu;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }

    @Override
    public void removeCreatedDependency() {
	this.note.removeDependency(this);
	this.target.removeDependency(this);
    }

    @Override
    protected void select() {
	super.select();
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLinkNoteHighlightedForegroundColor(), 2);
    }

    @Override
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
    }

    @Override
    public void unselect() {
	super.unselect();
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLinkNoteForegroundColor(), 1);
    }


    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	return "LinkNote$" + this.note.getId() + "!" + this.target.getId();
    }
}
