/*
 * This file is part of the Gwt-Uml project and was written by Raphaël Brugier <raphael dot brugier at gmail dot com > for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct
 * 
 * Gwt-Uml is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Gwt-Uml is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Gwt-Generator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.contextMenu;

import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind.ASYNCHRONOUS_MESSAGE;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind.FOUND_MESSAGE;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind.LOST_MESSAGE;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind.OBJECT_CREATION_MESSAGE;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind.SYNCHRONOUS_MESSAGE;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlCanvas.SequenceDiagram;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvasSequenceDiagram;

/**
 * Context menu implementation for a sequence diagram
 * 
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class SequenceContextMenu extends ContextMenu {

	private SequenceDiagram sequenceDiagram;

	protected SequenceContextMenu(final Point location, final UMLCanvasSequenceDiagram umlcanvas, final MenuBarAndTitle specificRightMenu) {
		super(location, umlcanvas, specificRightMenu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu#makeSpecificDiagramMenu()
	 */
	@Override
	protected void makeSpecificDiagramMenu() {
		contextMenu.addItem("Add new life line", new Command() {
			public void execute() {
				sequenceDiagram.addNewLifeLine();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu#makeSpecificRelationDiagramMenu()
	 */
	@Override
	protected void addSpecificRelationDiagramMenu() {
		final MenuBar relationsSubMenu = new MenuBar(true);

		addRelationCommand(relationsSubMenu, "Asynchronous", ASYNCHRONOUS_MESSAGE);
		addRelationCommand(relationsSubMenu, "Found", FOUND_MESSAGE);
		addRelationCommand(relationsSubMenu, "Lost", LOST_MESSAGE);
		addRelationCommand(relationsSubMenu, "Synchronous", SYNCHRONOUS_MESSAGE);
		addRelationCommand(relationsSubMenu, "Object Creation", OBJECT_CREATION_MESSAGE);

		contextMenu.addItem("Add relation", relationsSubMenu);
		contextMenu.addSeparator();
	}
}
