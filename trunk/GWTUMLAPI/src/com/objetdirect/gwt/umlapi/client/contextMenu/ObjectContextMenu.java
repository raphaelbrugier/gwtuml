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

import static com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind.ASSOCIATION_RELATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind.INSTANTIATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind.NOTE;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.UmlCanvas;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;

/**
 * Context menu implementation for an object diagram
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class ObjectContextMenu extends ContextMenu {

	private final Command addNewObjectCommand =
		new Command() {
		public void execute() {
			canvas.addNewObject();
		}
	};

	protected ObjectContextMenu(final Point location, final UmlCanvas umlcanvas, final MenuBarAndTitle specificRightMenu) {
		super(location, umlcanvas, specificRightMenu);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu#makeSpecificDiagramMenu()
	 */
	@Override
	protected void makeSpecificDiagramMenu() {
		contextMenu.addItem("Add new object", addNewObjectCommand);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu#makeSpecificRelationDiagramMenu()
	 */
	@Override
	protected void makeSpecificRelationDiagramMenu() {
		final MenuBar relationsSubMenu = new MenuBar(true);

		addRelationCommand(relationsSubMenu, "Assocation", ASSOCIATION_RELATION);
		addRelationCommand(relationsSubMenu, "Instantiation", INSTANTIATION);
		addRelationCommand(relationsSubMenu, "Note link", NOTE);

		contextMenu.addItem("Add relation", relationsSubMenu);
		contextMenu.addSeparator();
	}
}
