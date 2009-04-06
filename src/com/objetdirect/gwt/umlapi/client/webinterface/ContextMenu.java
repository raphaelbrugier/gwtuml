package com.objetdirect.gwt.umlapi.client.webinterface;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.gwt.mosaic.ui.client.PopupMenu;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
/**
 * @author  florian
 */
public class ContextMenu {

    private int x;
    private int y;
    private UMLCanvas canvas;
    private PopupMenu contextMenu;
    private LinkedHashMap<Command, String> specificRightMenu;
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
    public ContextMenu(int x, int y, UMLCanvas canvas, LinkedHashMap<Command, String> specificRightMenu) {
        this.x = x;
        this.y = y;
        this.canvas = canvas;
        this.specificRightMenu = specificRightMenu;
        makeMenu();
    }
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
    private final Command bringHelp = new Command() {
        public void execute() {
            HelpManager.bringHelpPopup();
        }
    };
    private final Command remove = new Command() {
        public void execute() {
            canvas.removeSelected();
        }
    };
    private void makeMenu() {
        contextMenu = new PopupMenu();
        if (specificRightMenu != null) {
            MenuBar specificSubMenu = new MenuBar(true);
            for (Entry<Command, String> item : specificRightMenu.entrySet()) {
                if(item.getKey() != null) {
                if (item.getValue().equals("-"))
                    specificSubMenu.addSeparator();
                else
                    specificSubMenu.addItem(item.getValue(), item.getKey());
                }
            }
            specificSubMenu.addItem("Delete", remove);
            contextMenu.addItem(specificRightMenu.get(null), specificSubMenu);
            contextMenu.addSeparator();
        }

        contextMenu.addItem("Add new class", addNewClass);
        contextMenu.addItem("Add new note", addNewNote);
        MenuBar linkSubMenu = new MenuBar(true);
        for(RelationKind relation : RelationKind.values()) {
            linkSubMenu.addItem(relation.getName(), addRelation(relation));
        }
        contextMenu.addItem("Add relation", linkSubMenu);
        contextMenu.addSeparator();
        contextMenu.addItem("Help...", bringHelp);
    }
    private Command addRelation(final RelationKind relation) {       
        return new Command() {
            public void execute() {
                canvas.toLinkMode(relation);
            }
        };
    }
    public void show() {

        contextMenu.setPopupPositionAndShow(new PositionCallback() {
            public void setPosition(int offsetWidth, int offsetHeight) {
                contextMenu.setPopupPosition(x, y);
            }
        });
    }
}