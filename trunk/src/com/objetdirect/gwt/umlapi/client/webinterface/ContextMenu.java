package com.objetdirect.gwt.umlapi.client.webinterface;

import org.gwt.mosaic.ui.client.PopupMenu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation.RelationKind;

/**
 * This class manages the right click context drop menu
 *  
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ContextMenu {

    private final Command addNewClass = new Command() {
	public void execute() {
	    ContextMenu.this.canvas.addNewClass(ContextMenu.this.location);
	}
    };
    private final Command addNewObject = new Command() {
	public void execute() {
	    ContextMenu.this.canvas.addNewObject(ContextMenu.this.location);
	}
    };
    private final Command addNewNote = new Command() {
	public void execute() {
	    ContextMenu.this.canvas.addNewNote(ContextMenu.this.location);
	}
    };
    private final Command bringHelp = new Command() {
	public void execute() {
	    HelpManager.bringHelpPopup();
	}
    };
    private final Command saveToSVG = new Command() {
	public void execute() {
	    String svg = "<?xml version='1.0' standalone='no'?><!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'>";
	    svg += DOM.getInnerHTML((Element) ContextMenu.this.canvas.getElement().getFirstChildElement());
	   Window.open("data:image/svg," + svg, "SVG export", "");
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

    private final Point location;


    /**
     * Constructor of ContextMenu without a specific context menu part
     *
     * @param location The {@link Point} location where to display it (generally the mouse coordinates)
     * @param canvas The canvas where the actions must be called
     */
    public ContextMenu(final Point location, final UMLCanvas canvas) {
	this(location, canvas, null);
    }
    
    /**
     * Constructor of ContextMenu with a specific context menu part
     *
     * @param location The {@link Point} location where to display it (generally the mouse coordinates)
     * @param canvas The canvas where the actions must be called
     * @param specificRightMenu The right menu specific to an artifact to add in this menu 
     */
    public ContextMenu(final Point location, final UMLCanvas canvas,
	    final MenuBarAndTitle specificRightMenu) {
	this.location = location;
	this.canvas = canvas;
	this.specificRightMenu = specificRightMenu;
	makeMenu();
    }

    /**
     * This method show the constructed context menu
     * 
     */
    public void show() {

	this.contextMenu.setPopupPositionAndShow(new PositionCallback() {
	    public void setPosition(final int offsetWidth,
		    final int offsetHeight) {
		ContextMenu.this.contextMenu.setPopupPosition(ContextMenu.this.location.getX(), ContextMenu.this.location.getY());
	    }
	});
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
	this.contextMenu.setAutoOpen(true);
	this.contextMenu.setAutoHideEnabled(true);
	if (this.specificRightMenu != null) {
	    final MenuBar specificSubMenu = this.specificRightMenu.getSubMenu();

	    specificSubMenu.addItem("Delete", this.remove);
	    this.contextMenu.addItem(this.specificRightMenu.getName(), specificSubMenu);
	    this.contextMenu.addSeparator();
	}
	if(this.canvas.getUMLDiagram().getType().isClassType()) {
	    this.contextMenu.addItem("Add new class", this.addNewClass);
	}
	if(this.canvas.getUMLDiagram().getType().isObjectType()) {
	    this.contextMenu.addItem("Add new object", this.addNewObject);
	}
	this.contextMenu.addItem("Add new note", this.addNewNote);
	final MenuBar linkSubMenu = new MenuBar(true);
	for (final RelationKind relationKind : RelationKind.values()) {
	    linkSubMenu.addItem(relationKind.getName(), addRelation(relationKind));
	}
	this.contextMenu.addItem("Add relation", linkSubMenu);
	this.contextMenu.addSeparator();
	this.contextMenu.addItem("Save to SVG", this.saveToSVG);
	this.contextMenu.addItem("Help...", this.bringHelp);
    }
}