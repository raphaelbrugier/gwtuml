package com.objetdirect.gwt.umlapi.client.webinterface;

import org.gwt.mosaic.ui.client.PopupMenu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ContextMenu {

    private final Command addNewClass = new Command() {
	public void execute() {
	    canvas.addNewClass(location);
	}
    };
    private final Command addNewNote = new Command() {
	public void execute() {
	    canvas.addNewNote(location);
	}
    };
    private final Command bringHelp = new Command() {
	public void execute() {
	    HelpManager.bringHelpPopup();
	}
    };
    private final UMLCanvas canvas;
    private PopupMenu contextMenu;

    private final Command remove = new Command() {
	public void execute() {
	    canvas.removeSelected();
	}
    };

    private final MenuBarAndTitle specificRightMenu;

    private final Point location;


    public ContextMenu(final Point location, final UMLCanvas canvas) {
	this.location = location;
	this.canvas = canvas;
	specificRightMenu = null;
	makeMenu();
    }


    public ContextMenu(final Point location, final UMLCanvas canvas,
	    final MenuBarAndTitle specificRightMenu) {
	this.location = location;
	this.canvas = canvas;
	this.specificRightMenu = specificRightMenu;
	makeMenu();
    }

    public void show() {

	contextMenu.setPopupPositionAndShow(new PositionCallback() {
	    public void setPosition(final int offsetWidth,
		    final int offsetHeight) {
		contextMenu.setPopupPosition(location.getX(), location.getY());
	    }
	});
    }

    private Command addRelation(final RelationKind relation) {
	return new Command() {
	    public void execute() {
		canvas.toLinkMode(relation);
	    }
	};
    }

    private void makeMenu() {
	contextMenu = new PopupMenu();
	if (specificRightMenu != null) {
	    final MenuBar specificSubMenu = specificRightMenu.getSubMenu();

	    specificSubMenu.addItem("Delete", remove);
	    contextMenu.addItem(specificRightMenu.getName(), specificSubMenu);
	    contextMenu.addSeparator();
	}

	contextMenu.addItem("Add new class", addNewClass);
	contextMenu.addItem("Add new note", addNewNote);
	final MenuBar linkSubMenu = new MenuBar(true);
	for (final RelationKind relation : RelationKind.values()) {
	    linkSubMenu.addItem(relation.getName(), addRelation(relation));
	}
	contextMenu.addItem("Add relation", linkSubMenu);
	contextMenu.addSeparator();
	contextMenu.addItem("Help...", bringHelp);
    }
}