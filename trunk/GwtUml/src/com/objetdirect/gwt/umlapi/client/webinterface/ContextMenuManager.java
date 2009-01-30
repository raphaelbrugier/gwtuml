package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.LinkedHashMap;
import java.util.Map;

import org.gwt.mosaic.ui.client.PopupMenu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas.Link;

public class ContextMenuManager {
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
			canvas.addNewClass();
		}

	};
	private final Command addNewNote = new Command() {
		public void execute() {
			canvas.addNewNote();
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
	private UMLCanvas canvas;
	private PopupMenu contextMenu;

	public ContextMenuManager(UMLCanvas UMLcanvas) {
		canvas = UMLcanvas;
	}

	public void makeMenu() {
		makeMenu(true);
	}

	public void makeMenu(LinkedHashMap<String, Command> specificRightMenu) {
		contextMenu = new PopupMenu();
		for (Map.Entry<String, Command> item : specificRightMenu.entrySet()) {
			if (item.getKey().equals("-"))
				contextMenu.addSeparator();
			else
				contextMenu.addItem(item.getKey(), item.getValue());
		}
		contextMenu.addSeparator();
		contextMenu.addSeparator();
		makeMenu(false);
	}

	public void show(final int x, final int y) {
		contextMenu.setPopupPositionAndShow(new PositionCallback() {
			public void setPosition(int offsetWidth, int offsetHeight) {
				contextMenu.setPopupPosition(x, y);
			}
		});
	}

	private void makeMenu(boolean withInitialisation) {
		if (withInitialisation)
			contextMenu = new PopupMenu();
		contextMenu.addItem("Add new class", addNewClass);
		contextMenu.addItem("Add new note", addNewNote);
		contextMenu.addItem("Add new dependency", addSimpleClassDependency);
		contextMenu.addItem("Add new extension dependency",
				addExtensionClassDependency);
		contextMenu.addItem("Add new implementation dependency",
				addImplementationClassDependency);
		contextMenu.addItem("Add new relationship", addRelationship);
	}

}