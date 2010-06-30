/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.contextMenu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.contrib.PopupMenu;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.helpers.HelpManager;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind;

/**
 * This class manages the right click context drop menu
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class ContextMenu {

	private final Command addNewNoteCommand	=
		new Command() {
		public void execute() {
			canvas.addNewNote();
		}
	};

	private final Command bringHelpCommand = new Command() {
		public void execute() {
			HelpManager.bringHelpPopup();
		}
	};

	private final Command clearDiagramCommand = new Command() {
		public void execute() {
			canvas.selectAll();
			canvas.removeSelected();
		}
	};

	private final Command changeLinkStyleCommand = new Command() {
		public void execute() {
			OptionsManager.set("AngularLinks", 1 - OptionsManager.get("AngularLinks"));
			canvas.rebuildAllGFXObjects();
		}
	};

	private final Command removeCommand = new Command() {
		public void execute() {
			canvas.removeSelected();
		}
	};

	private final Command cutCommand = new Command() {
		public void execute() {
			canvas.cut();
		}
	};

	private final Command copyCommmand= new Command() {
		public void execute() {
			canvas.copy();
		}
	};

	private final Command pasteCommand	= new Command() {
		public void execute() {
			canvas.paste();
		}
	};

	protected final UMLCanvas canvas;
	protected PopupMenu contextMenu;
	private final MenuBarAndTitle specificRightMenu;
	private final Point location;

	/**
	 * Factory to create a contextual menu for a class diagram.
	 * 
	 * @param location The {@link Point} location where to display it (generally the mouse coordinates)
	 * @param canvas The canvas where the actions must be called
	 * @param specificRightMenu The specific contextual menu if the menu is requested on a gfx object else null.
	 */
	public static ContextMenu createClassDiagramContextMenu(final Point location, final UMLCanvas canvas, final MenuBarAndTitle specificRightMenu) {
		return new ClassContextMenu(location, canvas, specificRightMenu);
	}

	/**
	 * Factory to create a contextual menu for an object diagram.
	 * 
	 * @param location The {@link Point} location where to display it (generally the mouse coordinates)
	 * @param canvas The canvas where the actions must be called
	 * @param specificRightMenu The specific contextual menu if the menu is requested on a gfx object else null.
	 */
	public static ContextMenu createObjectDiagramContextMenu(final Point location, final UMLCanvas canvas, final MenuBarAndTitle specificRightMenu) {
		return new ObjectContextMenu(location, canvas, specificRightMenu);
	}

	/**
	 * Factory to create a contextual menu for a sequence diagram.
	 * 
	 * @param location The {@link Point} location where to display it (generally the mouse coordinates)
	 * @param canvas The canvas where the actions must be called
	 * @param specificRightMenu The specific contextual menu if the menu is requested on a gfx object else null.
	 */
	public static ContextMenu createSequenceDiagramContextMenu(final Point location, final UMLCanvas canvas, final MenuBarAndTitle specificRightMenu) {
		return new SequenceContextMenu(location, canvas, specificRightMenu);
	}

	/**
	 * Constructor of ContextMenu with a specific context menu part
	 * 
	 * @param location The {@link Point} location where to display it (generally the mouse coordinates)
	 * @param canvas The canvas where the actions must be called
	 * @param specificRightMenu The right menu specific to an artifact to add in this menu
	 */
	protected ContextMenu(final Point location, final UMLCanvas canvas, final MenuBarAndTitle specificRightMenu) {
		this.location = location;
		this.canvas = canvas;
		this.specificRightMenu = specificRightMenu;
		this.makeMenu();
	}

	/**
	 * This method show the constructed context menu
	 */
	public void show() {
		contextMenu.setPopupPositionAndShow(new PositionCallback() {
			public void setPosition(final int offsetWidth, final int offsetHeight) {
				contextMenu.setPopupPosition(location.getX(), location.getY());
			}
		});
	}

	/**
	 * Build the internal contextual menu.
	 */
	private void makeMenu() {
		contextMenu = new PopupMenu();
		contextMenu.setAutoOpen(true);
		contextMenu.setAutoHideEnabled(true);
		if (specificRightMenu != null) {
			final MenuBar specificSubMenu = specificRightMenu.getSubMenu();
			specificSubMenu.addItem("Delete", removeCommand);
			contextMenu.addItem(specificRightMenu.getName(), specificSubMenu);
			contextMenu.addSeparator();
		}

		makeSpecificDiagramMenu();

		contextMenu.addItem("Add new note", addNewNoteCommand);

		makeSpecificRelationDiagramMenu();

		contextMenu.addItem("Cut",  cutCommand);
		contextMenu.addItem("Copy",  copyCommmand);
		contextMenu.addItem("Paste", pasteCommand);

		contextMenu.addSeparator();
		contextMenu.addItem("Switch links style", changeLinkStyleCommand);

		contextMenu.addItem("Clear diagram", clearDiagramCommand);
		contextMenu.addItem("Hotkeys...", bringHelpCommand);
	}

	/**
	 * Add a relation menu item on the given menu.
	 * @param subMenu The menu where the new menu item is added
	 * @param relationName The name of the menu item
	 * @param relationKind the kind of relation
	 */
	protected final void addRelationCommand(final MenuBar subMenu, String relationName, final LinkKind relationKind) {
		final Command relationCommand = new Command() {
			@Override
			public void execute() {
				canvas.toLinkMode(relationKind);
			}
		};

		subMenu.addItem(relationName, relationCommand);
	}

	/**
	 * Build a menu entry specific for the diagram.
	 * e.g A class diagram has a special entry "create class"
	 */
	protected abstract void makeSpecificDiagramMenu();

	/**
	 * Build a relation subMenu specific for the diagram.
	 * e.g A class diagram has a relation submenu with only the entries : association, aggregation, composition, inheritance, note link.
	 */
	protected abstract void makeSpecificRelationDiagramMenu();
}