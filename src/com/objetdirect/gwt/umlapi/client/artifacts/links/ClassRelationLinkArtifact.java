package com.objetdirect.gwt.umlapi.client.artifacts.links;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author florian
 */
public class ClassRelationLinkArtifact extends LinkArtifact {
    ClassArtifact classArtifact;
    GfxObject line = null;
    RelationLinkArtifact relation;

    public ClassRelationLinkArtifact(final ClassArtifact classArtifact,
	    final RelationLinkArtifact relation) {
	this.classArtifact = classArtifact;
	this.classArtifact.addDependency(this, relation);
	this.relation = relation;
	this.relation.addDependency(this, classArtifact);
    }

    @Override
    public void buildGfxObject() {
	this.leftPoint = this.classArtifact.getCenter();
	this.rightPoint = this.relation.getCenter();
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
	rightMenu
		.setName("Class relation link " + this.classArtifact.getClassName());
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
	this.classArtifact.removeDependency(this);
	this.relation.removeDependency(this);
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
