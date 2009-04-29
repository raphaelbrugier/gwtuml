package com.objetdirect.gwt.umlapi.client.webinterface;

import org.gwt.mosaic.ui.client.PopupMenu;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

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
	   saveTo(DOM.getInnerHTML((Element) ContextMenu.this.canvas.getElement().getFirstChildElement()));
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

	this.contextMenu.addItem("Add new class", this.addNewClass);
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
    
    /**
     * @param toSave
     */
    private native void saveTo(String toSave) /*-{

        ir=document.createElement('iframe');
        ir.id='ifr';
        ir.location='about:blank';
        ir.style.display='none';
        document.documentElement.appendChild(ir);
        document.getElementById('ifr').contentWindow.document.open();
        document.getElementById('ifr').contentWindow.document.write(toSave);
        document.getElementById('ifr').contentWindow.document.close();
        document.getElementById('ifr').contentWindow.document.location='data:image/svg, '+encodeURIComponent(toSave);
        setTimeout(function(){documentElement.removeChild(ir)},1000);
  
  }-*/;
    
}