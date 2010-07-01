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
import static com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas.DragAndDropState.TAKING;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.INSTANTIATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.OBJECT_RELATION;

import java.util.ArrayList;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.InstantiationRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * UMLCanvas concrete class for an object diagram.
 * 
 * @author Raphaël Brugier <raphael dot brugier at gmail dot com>
 */
@SuppressWarnings("serial")
public class UMLCanvasObjectDiagram extends UMLCanvas implements ObjectDiagram {

	private int objectCount;

	/**
	 * Default constructor only for gwt-rpc serialization
	 */
	@SuppressWarnings("unused")
	private UMLCanvasObjectDiagram() {
	}

	protected UMLCanvasObjectDiagram(@SuppressWarnings("unused") boolean dummy) {
		super(true);
		objectCount = 0;
	}

	@Override
	public void addNewClass() {
		this.addNewClass(wrapper.getCurrentMousePosition());
	}

	public void addNewObject() {
		this.addNewObject(wrapper.getCurrentMousePosition());
	}

	@Override
	public void dropContextualMenu(GfxObject gfxObject, Point location) {
		doSelection(gfxObject, false, false);
		final UMLArtifact elem = getUMLArtifact(gfxObject);
		MenuBarAndTitle rightMenu = elem == null ? null : elem.getRightMenu();

		ContextMenu contextMenu = ContextMenu.createObjectDiagramContextMenu(location, this, rightMenu);

		contextMenu.show();
	}

	// TODO Create a special artifact to display a class in an object Diagram.
	private void addNewClass(final Point location) {
		if (dragAndDropState != NONE) {
			return;
		}
		final ClassArtifact newClass = new ClassArtifact(this, idCount, "Class --");

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

	/**
	 * Add a new object with default values to this canvas at the specified location
	 * 
	 * @param location
	 *            The initial object location
	 */
	private void addNewObject(final Point location) {
		if (dragAndDropState != NONE) {
			return;
		}
		final ObjectArtifact newObject = new ObjectArtifact(this, idCount, "obj" + ++objectCount, "Object" + objectCount);

		this.add(newObject);
		newObject.moveTo(Point.substract(location, getCanvasOffset()));
		for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {
			selectedArtifact.unselect();
		}
		selectedArtifacts.clear();
		this.doSelection(newObject.getGfxObject(), false, false);
		selectedArtifacts.put(newObject, new ArrayList<Point>());
		dragOffset = location;
		wrapper.setCursorIcon(MOVE);
		dragAndDropState = TAKING;
		mouseIsPressed = true;

		wrapper.setHelpText("Adding a new object", location.clonePoint());
	}

	@Override
	protected LinkArtifact makeLinkBetween(UMLArtifact uMLArtifact, UMLArtifact uMLArtifactNew) {
		try {
			if (activeLinking == OBJECT_RELATION && uMLArtifactNew instanceof ObjectArtifact && uMLArtifact instanceof ObjectArtifact) {
				return new ObjectRelationLinkArtifact(this, idCount, (ObjectArtifact) uMLArtifact, (ObjectArtifact) uMLArtifactNew);
			} else if ((activeLinking == INSTANTIATION)) {
				ClassArtifact classArtifact = null;
				ObjectArtifact objectArtifact = null;

				// Dirty dirty dirty :(
				if (uMLArtifactNew instanceof ClassArtifact && uMLArtifact instanceof ObjectArtifact) {
					classArtifact = (ClassArtifact) uMLArtifactNew;
					objectArtifact = (ObjectArtifact) uMLArtifact;
				} else if (uMLArtifact instanceof ClassArtifact && uMLArtifactNew instanceof ObjectArtifact) {
					classArtifact = (ClassArtifact) uMLArtifact;
					objectArtifact = (ObjectArtifact) uMLArtifactNew;
				} else {
					return null;
				}
				return new InstantiationRelationLinkArtifact(this, idCount, classArtifact, objectArtifact);
			}
		} catch (IllegalArgumentException e) {
			return null;
		}
		return null;
	}
}
