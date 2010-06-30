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

import static com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType.SEQUENCE;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

/**
 * UMLCanvas concrete class for a sequence diagram.
 * 
 * @author Raphaël Brugier <raphael dot brugier at gmail dot com>
 */
@SuppressWarnings("serial")
public class UMLCanvasSequenceDiagram extends UMLCanvas {

	/**
	 * Default constructor only for gwt-rpc serialization
	 */
	@SuppressWarnings("unused")
	private UMLCanvasSequenceDiagram() {
	}

	protected UMLCanvasSequenceDiagram(@SuppressWarnings("unused") boolean dummy) {
		super(SEQUENCE);
	}

	@Override
	public void dropContextualMenu(GfxObject gfxObject, Point location) {
		this.doSelection(gfxObject, false, false);
		final UMLArtifact elem = this.getUMLArtifact(gfxObject);
		MenuBarAndTitle rightMenu = elem == null ? null : elem.getRightMenu();

		ContextMenu contextMenu = ContextMenu.createSequenceDiagramContextMenu(location, this, rightMenu);
		contextMenu.show();
	}

}
