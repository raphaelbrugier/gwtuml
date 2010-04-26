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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.gwt.umlapi.client.helpers.HotKeyManager;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;

/**
 * Main class for gwtuml application. This class does some initialization and calls the start panel.
 * 
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class GWTUMLDrawer implements EntryPoint {
	private final static DockPanel	appRootPanel	= new DockPanel();
	// private static Button log;
	static HorizontalPanel			southBar;

	/**
	 * Entry point of the application This class make a StartPanel and manage the history for it
	 */
	public void gwt_main() {
		// Log.setCurrentLogLevel(Log.LOG_LEVEL_WARN);
		GWTUMLDrawer.southBar = new HorizontalPanel();
		GWTUMLDrawer.southBar.setVisible(false);
		GWTUMLDrawer.southBar.setSpacing(5);
		final Button toUrl = new Button("Export to url");
		toUrl.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				HistoryManager.upgradeDiagramURL(Session.getActiveCanvas().toUrl());
			}
		});
		GWTUMLDrawer.southBar.add(toUrl);
		final Button clearUrl = new Button("Clear diagram in url");
		clearUrl.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				HistoryManager.upgradeDiagramURL("");
			}
		});
		GWTUMLDrawer.southBar.add(clearUrl);
		final Button exportToSvg = new Button("Export to SVG (Experimental)");
		exportToSvg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				String svg = "<?xml version='1.0' standalone='no'?><!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'>";
				Session.getActiveCanvas().clearArrows();
				svg += DOM.getInnerHTML((Element) Session.getActiveCanvas().getContainer().getElement().getFirstChildElement());
				Window.open("data:image/xml+svg," + svg, "SVG export", "top");
				Session.getActiveCanvas().makeArrows();
			}
		});
		GWTUMLDrawer.southBar.add(exportToSvg);
		OptionsManager.initialize();
		HotKeyManager.forceStaticInit();
		final HistoryManager historyManager = new HistoryManager();
		historyManager.initApplication(GWTUMLDrawer.appRootPanel);

		DOM.setInnerHTML(RootPanel.get("loading-screen").getElement(), "");
		// DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(), "display", "none");

		// log = new Button("ToggleLog");
		// log.addClickHandler(new ClickHandler() {
		// public void onClick(final ClickEvent event) {
		// final DivLogger dl = Log.getDivLogger();
		// final Element e = dl.getWidget().getElement();
		// final String p = DOM.getStyleAttribute(e, "display");
		// final String v = "none".equals(p) ? "" : "none";
		// DOM.setStyleAttribute(e, "display", v);
		// Log.getDivLogger().moveTo(log.getAbsoluteLeft(),
		// log.getAbsoluteTop() + log.getOffsetHeight() + 10);
		// }
		// });
		GWTUMLDrawer.appRootPanel.setSize("100%", "100%");
		// appRootPanel.add(log, DockPanel.SOUTH);

		GWTUMLDrawer.appRootPanel.add(GWTUMLDrawer.southBar, DockPanel.SOUTH);
		RootPanel.get().add(GWTUMLDrawer.appRootPanel);
		// Log.getDivLogger().moveTo(log.getAbsoluteLeft(),
		// log.getAbsoluteTop() + log.getOffsetHeight() + 10);
		// 
	}

	/*
	 * Real gwt app entry point, this code allow GWT Log to catch exception and display it (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		Log.setUncaughtExceptionHandler();
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				GWTUMLDrawer.this.gwt_main();
			}
		});
	}
}
