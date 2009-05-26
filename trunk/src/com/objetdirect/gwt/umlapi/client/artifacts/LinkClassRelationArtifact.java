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

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This artifact represent the link between a class and a class relation 
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class LinkClassRelationArtifact extends LinkArtifact {
    ClassArtifact classArtifact;
    GfxObject line = null;
    ClassRelationLinkArtifact relationLinkArtifact;

    /**
     * Constructor of LinkClassRelationArtifact
     * @param classArtifact The class for the relation class 
     * @param relation The relation between the two other classes
     */
    public LinkClassRelationArtifact(final ClassArtifact classArtifact,
	    final ClassRelationLinkArtifact relation) {
	super();
	this.classArtifact = classArtifact;
	this.classArtifact.addDependency(this, relation);
	this.relationLinkArtifact = relation;
	this.relationLinkArtifact.addDependency(this, classArtifact);
    }

    @Override
    public void buildGfxObject() {
	this.leftPoint = this.classArtifact.getCenter();
	this.rightPoint = this.relationLinkArtifact.getCenter();
	this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLinkClassForegroundColor(), 1);
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
	rightMenu
		.setName("Class relation link " + this.classArtifact.getName());
	return rightMenu;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }

     @Override
    public void removeCreatedDependency() {
	this.classArtifact.removeDependency(this);
	this.relationLinkArtifact.removeDependency(this);
    }

    @Override
    protected void select() {
	super.select();
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLinkClassHighlightedForegroundColor(), 2);
    }

    @Override
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
    }

    @Override
    public void unselect() {
	super.unselect();
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getLinkClassForegroundColor(), 1);
    }


    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	return "LinkClassRelation$" + this.classArtifact.getId() + "!" + this.relationLinkArtifact.getId();
    }
}
