package com.objetdirect.gwt.umlapi.client;

import com.allen_sauer.gwt.log.client.DivLogger;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.webinterface.StartPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UMLDrawer implements EntryPoint {

	public final static DockPanel appRootPanel = new DockPanel();
	private static Button log;

	public static void addtoAppRootPanel(Widget w) {
		Log.trace("History : adding center");
		appRootPanel.add(w, DockPanel.CENTER);
		DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(),
				"display", "none");
	}

	public static void clearAppRootPanel() {
		Log.trace("History : clearing panel");
		appRootPanel.clear();
		appRootPanel.add(log, DockPanel.SOUTH);
		DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(),
				"display", "none");
	}

	private StartPanel startPanel;

	public void gwt_main() {

		Log.setCurrentLogLevel(Log.LOG_LEVEL_WARN);
		DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(),
				"display", "none");

		appRootPanel.setSize("100%", "100%");

		startPanel = new StartPanel(false);

		History.newItem("Start");
		History.addHistoryListener(new HistoryListener() {
			public void onHistoryChanged(String historyToken) {
				if (historyToken.equals("Start")) {

					clearAppRootPanel();
					startPanel = new StartPanel(true);
					appRootPanel.add(startPanel, DockPanel.CENTER);
				}
			}
		});

		log = new Button("ToggleLog");

		log.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				DivLogger dl = Log.getDivLogger();
				Element e = dl.getWidget().getElement();
				String p = DOM.getStyleAttribute(e, "display");
				String v = ("none".equals(p)) ? "" : "none";
				DOM.setStyleAttribute(e, "display", v);
				Log.getDivLogger().moveTo(log.getAbsoluteLeft(),
						log.getAbsoluteTop() + log.getOffsetHeight() + 10);
			}
		});

		appRootPanel.add(startPanel, DockPanel.CENTER);
		appRootPanel.add(log, DockPanel.SOUTH);
		RootPanel.get().add(appRootPanel);
		Log.getDivLogger().moveTo(log.getAbsoluteLeft(),
				log.getAbsoluteTop() + log.getOffsetHeight() + 10);
	}

	public void onModuleLoad() {
		Log.setUncaughtExceptionHandler();
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				gwt_main();
			}
		});
	}
}
