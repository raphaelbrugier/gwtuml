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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;

/**
 * Manage the history url and token. 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class HistoryManager implements ValueChangeHandler<String> {
	
	public static final String START_PAGE = "Start";
	public static final String ANIMATED_PAGE = "AnimatedDemo";
	public static final String DEMO_PAGE = "Demo";
	public static final String DRAWER_PAGE = "Drawer";
	
	private LayoutPanel appContainer;

	static void upgradeDiagramURL(final String url) {
		String historyToken = DRAWER_PAGE + "?" + OptionsManager.toURL();
		if (!historyToken.endsWith("&")) {
			historyToken += "&";
		}
		historyToken += "diagram64=" + url;
		History.newItem(historyToken, false);
	}
	
	public HistoryManager() {
		History.addValueChangeHandler(this);
	}


	@Override
	public void onValueChange(final ValueChangeEvent<String> event) {
		String historyToken = event.getValue();
		UrlParser urlParser = new UrlParser(historyToken);
		String pageName = urlParser.getPageName();
		
		if (pageName.equals(DRAWER_PAGE)) {
			DrawerContainer drawerContainer = new DrawerContainer(urlParser); 
			changePage(drawerContainer);
			forceDrawerResize(drawerContainer);
		} 
		else if (pageName.equals(START_PAGE)) {
			changePage(new StartPanel());
		} 
		else if (pageName.equals(DEMO_PAGE)) {
			DrawerContainer drawerContainer = new DrawerContainer();
			new Demo(drawerContainer.getUmlCanvas());
			changePage(drawerContainer);
			forceDrawerResize(drawerContainer);
		} 
		else if (pageName.equals(ANIMATED_PAGE)) { // not working
			DrawerContainer drawerContainer = new DrawerContainer();
			new AnimatedDemo(drawerContainer);
			changePage(drawerContainer);
			forceDrawerResize(drawerContainer);
		}
	}
	
	/**
	 * Switch the page currently displayed in the container.
	 * @param newPage
	 */
	private void changePage(Widget newPage)  {
		appContainer.clear();
		appContainer.add(newPage);
	}
	
	/**
	 * Render a page from rule on the given container
	 * @param container The container where the page is rendered (usually the RootLayoutPanel)
	 */
	public void go(LayoutPanel container) {
		appContainer = container;
		 if ("".equals(History.getToken())) {
		      History.newItem(START_PAGE);
		 }
		 else {
		     History.fireCurrentHistoryState();
		 }
	}
	
	/**
	 * Add a deferred command to force the drawer resizing.
	 */
	private void forceDrawerResize(final DrawerContainer drawerContainer) {
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				drawerContainer.onResize();
			}
		});
	}
}