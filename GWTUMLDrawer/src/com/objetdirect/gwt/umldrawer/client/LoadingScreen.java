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
package com.objetdirect.gwt.umldrawer.client;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.gwt.umlapi.client.helpers.CursorIconManager;
import com.objetdirect.gwt.umlapi.client.helpers.CursorIconManager.PointerStyle;

/**
 * This class display an AJAX style loading screen to animate waiting time
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class LoadingScreen {
	private final HorizontalPanel	loadingPanel;

	/**
	 * Constructor of the {@link LoadingScreen}
	 * 
	 */
	public LoadingScreen() {
		super();
		final Image loader = new Image("ajax-loader.gif");
		this.loadingPanel = new HorizontalPanel();
		this.loadingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.loadingPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		this.loadingPanel.setSize("100%", "100%");
		this.loadingPanel.add(loader);
		this.loadingPanel.setVisible(false);
		RootPanel.get().add(this.loadingPanel, 0, 0);
	}

	/**
	 * Hide the loading screen
	 */
	public void hide() {
		CursorIconManager.setCursorIcon(PointerStyle.AUTO);
		this.loadingPanel.setVisible(false);
	}

	/**
	 * Show the loading screen
	 */
	public void show() {
		CursorIconManager.setCursorIcon(PointerStyle.WAIT);
		this.loadingPanel.setVisible(true);
	}
}
