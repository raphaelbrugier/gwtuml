package com.objetdirect.gwt.umlapi.client.webinterface;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

public class MenuBarAndTitle {
    
    private String name; 
    private MenuBar subMenu;
    public MenuBarAndTitle() {
        subMenu = new MenuBar(true);
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the subMenu
     */
    public MenuBar getSubMenu() {
        return subMenu;
    }
    /**
     * Add an item to the Menu Bar
     * @param text the menu caption
     * @param cmd the command to run
     */
    public void addItem(String text, Command cmd){
        subMenu.addItem(text, cmd);
    }
    /**
     * Add a sub menu
     * @param text the menu caption
     * @param popup the submenu
     */
    public void addItem(String text, MenuBar popup){
        subMenu.addItem(text, popup);
    }
    
}
