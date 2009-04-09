package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

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
		ThemeManager.getForegroundColor(), 1);
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
    public void select() {
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    @Override
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getForegroundColor(), 1);
    }
}
