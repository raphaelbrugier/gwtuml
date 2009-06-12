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

/**
 * This class is simply a class with a context sub menu component with a title for the upper level display
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class MenuBarAndTitle {

	private String			name;
	private final MenuBar	subMenu;

	/**
	 * Constructor of {@link MenuBarAndTitle}
	 * 
	 */
	public MenuBarAndTitle() {
		super();
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
