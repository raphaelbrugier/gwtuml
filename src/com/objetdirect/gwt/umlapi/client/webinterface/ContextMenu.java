package com.objetdirect.gwt.umlapi.client.webinterface;

import org.gwt.mosaic.ui.client.PopupMenu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * @author florian
 */
public class ContextMenu {

    private final Command addNewClass = new Command() {
	public void execute() {
	    ContextMenu.this.canvas.addNewClass(ContextMenu.this.x, ContextMenu.this.y);
	}
    };
    private final Command addNewNote = new Command() {
	public void execute() {
	    ContextMenu.this.canvas.addNewNote(ContextMenu.this.x, ContextMenu.this.y);
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
	    ContextMenu.this.canvas.removeSelected();
	}
    };

    private final MenuBarAndTitle specificRightMenu;

    private final int x;
    private final int y;

    /**
     * @param x
     * @param y
     * @param canvas
     */

    public ContextMenu(final int x, final int y, final UMLCanvas canvas) {
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
     * @param specificRightMenu
     */
    public ContextMenu(final int x, final int y, final UMLCanvas canvas,
	    final MenuBarAndTitle specificRightMenu) {
	this.x = x;
	this.y = y;
	this.canvas = canvas;
	this.specificRightMenu = specificRightMenu;
	makeMenu();
    }

    private Command addRelation(final RelationKind relation) {
	return new Command() {
	    public void execute() {
		ContextMenu.this.canvas.toLinkMode(relation);
	    }
	};
    }

    private void makeMenu() {
	this.contextMenu = new PopupMenu();
	if (this.specificRightMenu != null) {
	    final MenuBar specificSubMenu = this.specificRightMenu.getSubMenu();

	    specificSubMenu.addItem("Delete", this.remove);
	    this.contextMenu.addItem(this.specificRightMenu.getName(), specificSubMenu);
	    this.contextMenu.addSeparator();
	}

	this.contextMenu.addItem("Add new class", this.addNewClass);
	this.contextMenu.addItem("Add new note", this.addNewNote);
	final MenuBar linkSubMenu = new MenuBar(true);
	for (final RelationKind relation : RelationKind.values()) {
	    linkSubMenu.addItem(relation.getName(), addRelation(relation));
	}
	this.contextMenu.addItem("Add relation", linkSubMenu);
	this.contextMenu.addSeparator();
	this.contextMenu.addItem("Help...", this.bringHelp);
    }

    public void show() {

	this.contextMenu.setPopupPositionAndShow(new PositionCallback() {
	    public void setPosition(final int offsetWidth,
		    final int offsetHeight) {
		ContextMenu.this.contextMenu.setPopupPosition(ContextMenu.this.x, ContextMenu.this.y);
	    }
	});
    }
}