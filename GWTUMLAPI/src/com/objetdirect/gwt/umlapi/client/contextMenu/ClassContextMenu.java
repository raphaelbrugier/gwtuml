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

import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.AGGREGATION_RELATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.ASSOCIATION_RELATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.CLASSRELATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.COMPOSITION_RELATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.DEPENDENCY_RELATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.GENERALIZATION_RELATION;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.NOTE;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlCanvas.ClassDiagram;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvasClassDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind;

/**
 * Context menu implementation for a class diagram
 * 
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class ClassContextMenu extends ContextMenu {

	private final ClassDiagram classDiagram;

	protected ClassContextMenu(final Point location, final UMLCanvasClassDiagram classDiagram, final MenuBarAndTitle specificRightMenu) {
		super(location, classDiagram, specificRightMenu);
		this.classDiagram = classDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu#makeSpecificDiagramMenu()
	 */
	@Override
	protected void makeSpecificDiagramMenu() {
		contextMenu.addItem("Add new class", new Command() {
			public void execute() {
				classDiagram.addNewClass();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.contextMenu.ContextMenu#makeSpecificRelationDiagramMenu()
	 */
	@Override
	protected void makeSpecificRelationDiagramMenu() {
		final MenuBar relationsSubMenu = new MenuBar(true);

		addRelationCommand(relationsSubMenu, "Aggregation", AGGREGATION_RELATION);
		addRelationCommand(relationsSubMenu, "Association", ASSOCIATION_RELATION);
		addRelationCommand(relationsSubMenu, "Composition", COMPOSITION_RELATION);
		addRelationCommand(relationsSubMenu, "Class relation", CLASSRELATION);
		addRelationCommand(relationsSubMenu, "Dependency", DEPENDENCY_RELATION);
		addRelationCommand(relationsSubMenu, "Generalization", GENERALIZATION_RELATION);
		addRelationCommand(relationsSubMenu, "Note link", NOTE);

		contextMenu.addItem("Add relation", relationsSubMenu);
		contextMenu.addSeparator();
	}

	/**
	 * Add a relation menu item on the given menu.
	 * 
	 * @param subMenu
	 *            The menu where the new menu item is added
	 * @param relationName
	 *            The name of the menu item
	 * @param relationKind
	 *            the kind of relation
	 */
	@Override
	protected void addRelationCommand(final MenuBar subMenu, String relationName, final LinkKind relationKind) {
		final Command relationCommand = new Command() {
			@Override
			public void execute() {
				classDiagram.toLinkMode(relationKind);
			}
		};

		subMenu.addItem(relationName, relationCommand);
	}
}
