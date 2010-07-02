/*
 * This file is part of the Gwt-Generator project and was written by Raphaël Brugier <raphael dot brugier at gmail dot com > for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct
 * 
 * Gwt-Generator is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Gwt-Generator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Gwt-Generator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.umlCanvas;

import static com.objetdirect.gwt.umlapi.client.helpers.CursorIconManager.PointerStyle.MOVE;
import static com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas.DragAndDropState.NONE;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.CLASSRELATION;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.clazz.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.clazz.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.clazz.LinkClassRelationArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLRelation;

/**
 * UMLCanvas concrete class for a class diagram.
 * 
 * @author Raphaël Brugier <raphael dot brugier at gmail dot com>
 */
@SuppressWarnings("serial")
public class UMLCanvasClassDiagram extends UMLCanvas implements ClassDiagram {

	private long classCount;

	/**
	 * Default constructor only for gwt-rpc serialization
	 */
	@SuppressWarnings("unused")
	private UMLCanvasClassDiagram() {
	}

	protected UMLCanvasClassDiagram(@SuppressWarnings("unused") boolean dummy) {
		super(true);
		classCount = 0;
	}

	@Override
	public void addNewClass() {
		this.addNewClass(wrapper.getCurrentMousePosition());
	}

	@Override
	public void dropContextualMenu(GfxObject gfxObject, Point location) {
		doSelection(gfxObject, false, false);
		final UMLArtifact elem = getUMLArtifact(gfxObject);
		MenuBarAndTitle rightMenu = elem == null ? null : elem.getRightMenu();

		ContextMenu contextMenu = ContextMenu.createClassDiagramContextMenu(location, this, rightMenu);

		contextMenu.show();
	}

	private void addNewClass(final Point location) {
		if (dragAndDropState != NONE) {
			return;
		}
		final ClassArtifact newClass = new ClassArtifact(this, idCount, "Class" + ++classCount);

		this.add(newClass);
		newClass.moveTo(Point.substract(location, getCanvasOffset()));
		for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {
			selectedArtifact.unselect();
		}
		selectedArtifacts.clear();
		this.doSelection(newClass.getGfxObject(), false, false);
		selectedArtifacts.put(newClass, new ArrayList<Point>());
		dragOffset = location;
		wrapper.setCursorIcon(MOVE);
		dragAndDropState = DragAndDropState.TAKING;
		mouseIsPressed = true;

		wrapper.setHelpText("Adding a new class", location.clonePoint());
	}

	@Override
	protected LinkArtifact makeLinkBetween(UMLArtifact uMLArtifact, UMLArtifact uMLArtifactNew) {
		try {
			if (activeLinking == CLASSRELATION) { // CLASS TO ANY CLASS-TO-CLASS RELATION
				return new LinkClassRelationArtifact(this, idCount, uMLArtifactNew, uMLArtifact);
			} else if (activeLinking.isClassToClassRelation()) { // Class to class relation
				return new ClassRelationLinkArtifact(this, idCount, (ClassArtifact) uMLArtifactNew, (ClassArtifact) uMLArtifact, activeLinking);
			}
		} catch (IllegalArgumentException e) {
			return null;
		}
		return null;
	}

	@Override
	public List<UMLClass> getUmlClasses() {
		// TODO that's a bad way to find the classes, we should stored them in a List separately and add class in the
		// addClass() method.
		// This TODO apply to the others get[UMLComponents] methods here and in ObjectDiagram.

		ArrayList<UMLClass> umlClasses = new ArrayList<UMLClass>();
		for (final UMLArtifact umlArtifact : getArtifactById().values()) {
			if (umlArtifact instanceof ClassArtifact) {
				ClassArtifact classArtifact = (ClassArtifact) umlArtifact;
				umlClasses.add(classArtifact.toUMLComponent());
			}
		}
		return umlClasses;
	}

	@Override
	public List<UMLRelation> getClassRelations() {
		ArrayList<UMLRelation> umlRelations = new ArrayList<UMLRelation>();
		for (final UMLArtifact umlArtifact : getArtifactById().values()) {
			if (umlArtifact instanceof ClassRelationLinkArtifact) {
				ClassRelationLinkArtifact relationLinkArtifact = (ClassRelationLinkArtifact) umlArtifact;
				umlRelations.add(relationLinkArtifact.toUMLComponent());
			}
		}
		return umlRelations;
	}

}
