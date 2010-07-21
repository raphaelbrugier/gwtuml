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

import java.util.ArrayList;

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.sequence.LifeLineArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.sequence.MessageLinkArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * UMLCanvas concrete class for a sequence diagram.
 * 
 * @author Raphaël Brugier <raphael dot brugier at gmail dot com>
 */
public class UMLCanvasSequenceDiagram extends UMLCanvas {

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	private int lifeLineCount;

	/**
	 * Default constructor only for gwt-rpc serialization
	 */
	@SuppressWarnings("unused")
	private UMLCanvasSequenceDiagram() {
	}

	protected UMLCanvasSequenceDiagram(@SuppressWarnings("unused") boolean dummy) {
		super(true);
		lifeLineCount = 0;
	}

	@Override
	public void dropContextualMenu(GfxObject gfxObject, Point location) {
		this.doSelection(gfxObject, false, false);
		final UMLArtifact elem = this.getUMLArtifact(gfxObject);
		MenuBarAndTitle rightMenu = elem == null ? null : elem.getRightMenu();

		ContextMenu contextMenu = ContextMenu.createSequenceDiagramContextMenu(location, this, rightMenu);
		contextMenu.show();
	}

	/**
	 * Add a new lifeLine with default values to this canvas to the current mouse position
	 */
	public void addNewLifeLine() {
		this.addNewLifeLine(wrapper.getCurrentMousePosition());
	}

	/**
	 * Add a new life life with default values to this canvas at the specified location
	 * 
	 * @param location
	 *            The initial life line location
	 * 
	 */
	private void addNewLifeLine(final Point location) {
		if (dragAndDropState != DragAndDropState.NONE) {
			return;
		}
		final LifeLineArtifact newLifeLine = new LifeLineArtifact(this, idCount, "LifeLine" + ++lifeLineCount, "ll" + lifeLineCount);

		wrapper.setHelpText("Adding a new life line", new Point(0, 0));
		this.add(newLifeLine);
		newLifeLine.moveTo(Point.substract(location, getCanvasOffset()));
		for (final UMLArtifact selectedArtifact : selectedArtifacts.keySet()) {
			selectedArtifact.unselect();
		}
		selectedArtifacts.clear();
		this.doSelection(newLifeLine.getGfxObject(), false, false);
		selectedArtifacts.put(newLifeLine, new ArrayList<Point>());
		dragOffset = location;
		wrapper.setCursorIcon(MOVE);
		dragAndDropState = DragAndDropState.TAKING;
		mouseIsPressed = true;

		wrapper.setHelpText("Adding a new life line", location.clonePoint());
	}

	@Override
	protected LinkArtifact makeLinkBetween(UMLArtifact uMLArtifact, UMLArtifact uMLArtifactNew) {
		try {
			return new MessageLinkArtifact(this, idCount, (LifeLineArtifact) uMLArtifactNew, (LifeLineArtifact) uMLArtifact, activeLinking);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
