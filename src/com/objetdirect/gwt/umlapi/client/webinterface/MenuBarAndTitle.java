package com.objetdirect.gwt.umlapi.client.webinterface;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/** 
 * This class is simply a class with a context sub menu component with a title for the upper level display   
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class MenuBarAndTitle {

    private String name;
    private final MenuBar subMenu;

    /**
     * Constructor of {@link MenuBarAndTitle}
     *
     */
    public MenuBarAndTitle() {
	this.subMenu = new MenuBar(true);
    }

    /**
     * Add an item to the Menu Bar
     * 
     * @param text
     *            the menu caption
     * @param cmd
     *            the command to run
     */
    public void addItem(final String text, final Command cmd) {
	this.subMenu.addItem(text, cmd);
    }

    /**
     * Add a sub menu
     * 
     * @param text
     *            the menu caption
     * @param popup
     *            the submenu
     */
    public void addItem(final String text, final MenuBar popup) {
	this.subMenu.addItem(text, popup);
    }

    /**
     * @return the name
     */
    public String getName() {
	return this.name;
    }

    /**
     * @return the subMenu
     */
    public MenuBar getSubMenu() {
	return this.subMenu;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
	this.name = name;
    }

}
