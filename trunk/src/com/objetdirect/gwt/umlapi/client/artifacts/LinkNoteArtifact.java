package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class LinkNoteArtifact extends LinkArtifact {
    GfxObject line = null;
    NoteArtifact note;
    UMLArtifact target;

    public LinkNoteArtifact(final NoteArtifact note, final UMLArtifact target) {
	this.note = note;
	this.note.addDependency(this, target);
	this.target = target;
	this.target.addDependency(this, note);
    }

    @Override
    public void buildGfxObject() {

	leftPoint = note.getCenter();
	rightPoint = target.getCenter();
	line = GfxManager.getPlatform().buildLine(leftPoint, rightPoint);
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);
	GfxManager.getPlatform().setStroke(line,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
	GfxManager.getPlatform().moveToBack(gfxObject);

    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	// TODO Auto-generated method stub
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
	note.removeDependency(this);
	target.removeDependency(this);
    }

    @Override
    public void select() {
	GfxManager.getPlatform().setStroke(line,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    @Override
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(line,
		ThemeManager.getForegroundColor(), 1);
    }
}
