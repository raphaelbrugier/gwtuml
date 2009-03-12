package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.LinkedHashMap;
import java.util.Map;

import org.gwt.mosaic.ui.client.PopupMenu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas.Link;

public class ContextMenu {
	
	private int x;
	private int y;
	private UMLCanvas canvas;
	private PopupMenu contextMenu;
	private LinkedHashMap<String, Command> specificRightMenu;

	/**
	 * @param x
	 * @param y
	 * @param canvas
	 */
	
	public ContextMenu(int x, int y, UMLCanvas canvas) {
		this.x = x;
		this.y = y;
		this.canvas = canvas;
		this.specificRightMenu = null;
		makeMenu();
	}
	/**
	 * @param x
	 * @param y
	 * @param canvas
	 */
	public ContextMenu(int x, int y, UMLCanvas canvas, LinkedHashMap<String, Command> specificRightMenu) {
		this.x = x;
		this.y = y;
		this.canvas = canvas;
		this.specificRightMenu = specificRightMenu;
		makeMenu();
	}
	
	private final Command addExtensionClassDependency = new Command() {
		public void execute() {
			canvas.addNewLink(Link.EXTENSION);
		}

	};
	private final Command addImplementationClassDependency = new Command() {
		public void execute() {
			canvas.addNewLink(Link.IMPLEMENTATION);
		}

	};

	private final Command addNewClass = new Command() {
		public void execute() {
			canvas.addNewClass(x, y);
		}

	};
	private final Command addNewNote = new Command() {
		public void execute() {
			canvas.addNewNote(x, y);
		}

	};
	private final Command addRelationship = new Command() {
		public void execute() {
			canvas.addNewLink(Link.RELATIONSHIP);
		}

	};
	private final Command addSimpleClassDependency = new Command() {
		public void execute() {
			canvas.addNewLink(Link.SIMPLE);
		}

	};



	private void makeMenu() {
		contextMenu = new PopupMenu();
		if (specificRightMenu != null) {
		for (Map.Entry<String, Command> item : specificRightMenu.entrySet()) {
			if (item.getKey().equals("-"))
				contextMenu.addSeparator();
			else
				contextMenu.addItem(item.getKey(), item.getValue());
		}
		contextMenu.addSeparator();
		contextMenu.addSeparator();
		}
		
		contextMenu.addItem("Add new class", addNewClass);
		contextMenu.addItem("Add new note", addNewNote);
		contextMenu.addItem("Add new dependency", addSimpleClassDependency);
		contextMenu.addItem("Add new extension dependency",
				addExtensionClassDependency);
		contextMenu.addItem("Add new implementation dependency",
				addImplementationClassDependency);
		contextMenu.addItem("Add new relationship", addRelationship);
	}

	public void show() {
		
		contextMenu.setPopupPositionAndShow(new PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				contextMenu.setPopupPosition(x, y);
			}
		});
	}


}