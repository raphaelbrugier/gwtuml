package com.objetdirect.gwt.umlapi.client.artifacts.links;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author florian
 */
public class NoteLinkArtifact extends LinkArtifact {
    GfxObject line = null;
    NoteArtifact note;
    UMLArtifact target;

    public NoteLinkArtifact(final NoteArtifact note, final UMLArtifact target) {
	this.note = note;
	this.note.addDependency(this, target);
	this.target = target;
	this.target.addDependency(this, note);
    }

    @Override
    public void buildGfxObject() {

	this.leftPoint = this.note.getCenter();
	this.rightPoint = this.target.getCenter();
	this.line = GfxManager.getPlatform().buildLine(this.leftPoint.getX(),
		this.leftPoint.getY(), this.rightPoint.getX(), this.rightPoint.getY());
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().setStrokeStyle(this.line, GfxStyle.DASH);
	GfxManager.getPlatform().moveToBack(this.gfxObject);

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
    public void moveTo(final int x, final int y) {
	throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
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
    public void setLocation(final int x, final int y) {
	throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getForegroundColor(), 1);
    }
}
