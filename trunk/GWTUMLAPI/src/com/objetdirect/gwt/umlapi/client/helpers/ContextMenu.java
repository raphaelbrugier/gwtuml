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
package com.objetdirect.gwt.umlapi.client.helpers;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.UmlCanvas;
import com.objetdirect.gwt.umlapi.client.contrib.PopupMenu;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;

/**
 * This class manages the right click context drop menu
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ContextMenu {

	private final Command			addNewClass		= new Command() {
														public void execute() {
															ContextMenu.this.canvas.addNewClass();
														}
													};
	private final Command			addNewObject	= new Command() {
														public void execute() {
															ContextMenu.this.canvas.addNewObject();
														}
													};
	private final Command			addNewLifeLine	= new Command() {
														public void execute() {
															ContextMenu.this.canvas.addNewLifeLine();
														}
													};
	private final Command			addNewNote		= new Command() {
														public void execute() {
															ContextMenu.this.canvas.addNewNote();
														}
													};
	private final Command			bringHelp		= new Command() {
														public void execute() {
															HelpManager.bringHelpPopup();
														}
													};

	private final Command			clearDiagram	= new Command() {
														public void execute() {
															ContextMenu.this.canvas.selectAll();
															ContextMenu.this.canvas.removeSelected();
														}
													};
	private final Command			changeLinkStyle	= new Command() {
														public void execute() {
															OptionsManager.set("AngularLinks", 1 - OptionsManager.get("AngularLinks"));
															ContextMenu.this.canvas.rebuildAllGFXObjects();
														}
													};


	private final Command			remove			= new Command() {
														public void execute() {
															ContextMenu.this.canvas.removeSelected();
														}
													};

	
	private final Command	 			cut			= new Command() {
														public void execute() {
															ContextMenu.this.canvas.cut();
														}
													};
	private final Command	 			copy			= new Command() {
														public void execute() {
															ContextMenu.this.canvas.copy();
														}
													};
	private final Command	 			paste			= new Command() {
														public void execute() {
															ContextMenu.this.canvas.paste();
														}
													};
													
	private final UmlCanvas canvas;
	private PopupMenu contextMenu;
	private final MenuBarAndTitle specificRightMenu;
	private final Point location;
	private Type diagramType;
													
	/**
	 * Constructor of ContextMenu without a specific context menu part
	 * 
	 * @param location
	 *            The {@link Point} location where to display it (generally the mouse coordinates)
	 * @param canvas
	 *            The canvas where the actions must be called
	 */
	public ContextMenu(final Point location, final UmlCanvas canvas, Type diagramType) {
		this(location, canvas, null, diagramType);
	}

	/**
	 * Constructor of ContextMenu with a specific context menu part
	 * 
	 * @param location
	 *            The {@link Point} location where to display it (generally the mouse coordinates)
	 * @param canvas
	 *            The canvas where the actions must be called
	 * @param specificRightMenu
	 *            The right menu specific to an artifact to add in this menu
	 */
	public ContextMenu(final Point location, final UmlCanvas canvas, final MenuBarAndTitle specificRightMenu, Type diagramType) {
		super();
		this.diagramType = diagramType;
		this.location = location;
		this.canvas = canvas;
		this.specificRightMenu = specificRightMenu;
		this.makeMenu();
	}

	/**
	 * This method show the constructed context menu
	 * 
	 */
	public void show() {
		this.contextMenu.setPopupPositionAndShow(new PositionCallback() {
			public void setPosition(final int offsetWidth, final int offsetHeight) {
				ContextMenu.this.contextMenu.setPopupPosition(ContextMenu.this.location.getX(), ContextMenu.this.location.getY());
			}
		});
	}

	private Command createCommandAddRelation(final LinkKind relation) {
		return new Command() {
			public void execute() {
				ContextMenu.this.canvas.toLinkMode(relation);
			}
		};
	}

	private void makeMenu() {
		this.contextMenu = new PopupMenu();
		this.contextMenu.setAutoOpen(true);
		this.contextMenu.setAutoHideEnabled(true);
		if (this.specificRightMenu != null) {
			final MenuBar specificSubMenu = this.specificRightMenu.getSubMenu();

			specificSubMenu.addItem("Delete", this.remove);
			this.contextMenu.addItem(this.specificRightMenu.getName(), specificSubMenu);
			this.contextMenu.addSeparator();
		}
		if (diagramType.isClassType()) {
			this.contextMenu.addItem("Add new class", this.addNewClass);
		}
		if (diagramType.isObjectType()) {
			this.contextMenu.addItem("Add new object", this.addNewObject);
		}
		if (diagramType == Type.SEQUENCE) {
			this.contextMenu.addItem("Add new life line", this.addNewLifeLine);
		}
		this.contextMenu.addItem("Add new note", this.addNewNote);
		final MenuBar linkSubMenu = new MenuBar(true);
		for (final LinkKind relationKind : LinkKind.values()) {
			if (relationKind.isForDiagram(diagramType)) {
				linkSubMenu.addItem(relationKind.getName(), createCommandAddRelation(relationKind));
			}
		}
		this.contextMenu.addItem("Add relation", linkSubMenu);
		this.contextMenu.addSeparator();
		
		this.contextMenu.addItem("Cut",  this.cut);
		this.contextMenu.addItem("Copy",  this.copy);
		this.contextMenu.addItem("Paste", this.paste);
		
		this.contextMenu.addSeparator();
		if (diagramType.isClassOrObjectType()) {
			this.contextMenu.addItem("Switch links style", this.changeLinkStyle);
		}

		this.contextMenu.addItem("Clear diagram", this.clearDiagram);
		this.contextMenu.addItem("Hotkeys...", this.bringHelp);
	}
}