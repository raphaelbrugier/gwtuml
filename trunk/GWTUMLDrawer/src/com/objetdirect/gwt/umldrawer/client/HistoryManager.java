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

import java.util.HashMap;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class HistoryManager implements ValueChangeHandler<String> {
	private static String					lastHistoryAnchor			= "";
	private static HashMap<String, String>	lastHistoryParametersList	= new HashMap<String, String>();
	private static SimplePanel				applicationPanel			= new SimplePanel();
	private static String					urlDiagram					= "";

	static void upgradeDiagramURL(final String url) {
		String historyToken = HistoryManager.lastHistoryAnchor + "?" + OptionsManager.toURL();
		if (!historyToken.endsWith("&")) {
			historyToken += "&";
		}
		historyToken += "diagram64=" + url;
		History.newItem(historyToken, false);
	}

	/**
	 * Initialize the history management and therefore the application
	 * 
	 * @param appRootPanel
	 *            The panel on which we can put the pages
	 */
	public void initApplication(final DockPanel appRootPanel) {
		History.addValueChangeHandler(this);
		appRootPanel.add(HistoryManager.applicationPanel, DockPanel.CENTER);
		HistoryManager.applicationPanel.setSize("100%", "100%");
		this.parseHistoryToken(History.getToken());
		this.processHistory();
		Window.addCloseHandler(new CloseHandler<Window>() {

			@Override
			public void onClose(final CloseEvent<Window> event) {
				if (HistoryManager.lastHistoryAnchor.equals("Drawer")) {
					HistoryManager.upgradeDiagramURL(Session.getActiveCanvas().toUrl());
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
	 */
	@Override
	public void onValueChange(final ValueChangeEvent<String> event) {
		final String historyToken = event.getValue();
		this.parseHistoryToken(historyToken);
		this.processHistory();
	}

	private void parseHistoryToken(final String historyToken) {
		final String[] parts = historyToken.split("\\?");
		HistoryManager.lastHistoryAnchor = parts[0];
		HistoryManager.lastHistoryParametersList.clear();
		if (parts.length > 1) {
			final String[] params = parts[1].split("&");
			for (final String argument : params) {
				final String[] paramVar = argument.split("=", 2);
				if ((paramVar.length > 0) && (paramVar[0].length() > 0)) {
					if (!paramVar[0].equals("diagram64")) {
						HistoryManager.lastHistoryParametersList.put(paramVar[0], paramVar.length > 1 ? paramVar[1] : "");
					} else {
						HistoryManager.urlDiagram = paramVar.length > 1 ? paramVar[1] : "";
					}
				}
			}
		}
	}

	private void processHistory() {
		HistoryManager.applicationPanel.clear();
		// DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(), "display", "none");

		OptionsManager.setAllFromURL(HistoryManager.lastHistoryParametersList);
		if (HistoryManager.lastHistoryAnchor.equals("Drawer")) {
			final DrawerPanel drawerPanel = new DrawerPanel();
			if (HistoryManager.urlDiagram.equals("")) {
				drawerPanel.addDefaultNode();
			} else {
				Session.getActiveCanvas().getArtifactById().clear();
				Session.getActiveCanvas().fromURL(HistoryManager.urlDiagram, false);
			}
			GWTUMLDrawer.southBar.setVisible(true);
			HistoryManager.applicationPanel.add(drawerPanel);
		} else if (HistoryManager.lastHistoryAnchor.equals("Demo")) {
			final DrawerPanel drawerPanel = new DrawerPanel();
			new Demo(drawerPanel.getUMLCanvas());
			GWTUMLDrawer.southBar.setVisible(true);
			HistoryManager.applicationPanel.add(drawerPanel);
		} else if (HistoryManager.lastHistoryAnchor.equals("AnimatedDemo")) {
			final DrawerPanel drawerPanel = new DrawerPanel();
			new AnimatedDemo(drawerPanel.getUMLCanvas());
			GWTUMLDrawer.southBar.setVisible(true);
			HistoryManager.applicationPanel.add(drawerPanel);
		} else {
			History.newItem("Start", false);
			HistoryManager.urlDiagram = "";
			GWTUMLDrawer.southBar.setVisible(false);
			HistoryManager.applicationPanel.add(new StartPanel(false));
		}
	}
}