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
public class LinkClassRelationArtifact extends LinkArtifact {
    ClassArtifact classArtifact;
    GfxObject line = null;
    RelationLinkArtifact relation;

    public LinkClassRelationArtifact(final ClassArtifact classArtifact,
	    final RelationLinkArtifact relation) {
	this.classArtifact = classArtifact;
	this.classArtifact.addDependency(this, relation);
	this.relation = relation;
	this.relation.addDependency(this, classArtifact);
    }

    @Override
    public void buildGfxObject() {
	leftPoint = classArtifact.getCenter();
	rightPoint = relation.getCenter();
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
	rightMenu
		.setName("Class relation link " + classArtifact.getClassName());
	return rightMenu;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }

     @Override
    public void removeCreatedDependency() {
	classArtifact.removeDependency(this);
	relation.removeDependency(this);
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
