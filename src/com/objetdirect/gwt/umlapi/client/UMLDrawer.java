package com.objetdirect.gwt.umlapi.client;

import com.allen_sauer.gwt.log.client.DivLogger;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.webinterface.StartPanel;

/**
 * Main class for gwtuml application. This class does some initialization and
 * calls the start panel.
 * 
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLDrawer implements EntryPoint {
    private final static DockPanel appRootPanel = new DockPanel();
    private static Button log;

    /**
     * Add a widget to the center of the application root DockPanel Only one
     * panel must be in the center panel
     * 
     * @param w
     *            The widget to be added
     */
    public static void addtoAppRootPanel(final Widget w) {
	Log.trace("History : adding center");
	appRootPanel.add(w, DockPanel.CENTER);
	DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(),
		"display", "none");
    }

    /**
     * Clear the application root DockPanel from any added widget
     */
    public static void clearAppRootPanel() {
	Log.trace("History : clearing panel");
	appRootPanel.clear();
	appRootPanel.add(log, DockPanel.SOUTH);
	DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(),
		"display", "none");
    }

    private StartPanel startPanel;

    /**
     * Entry point of the application This class make a StartPanel and manage
     * the history for it
     */
    public void gwt_main() {

	DOM.setInnerHTML(RootPanel.get("loading-screen").getElement(), "");

	Log.setCurrentLogLevel(Log.LOG_LEVEL_WARN);

	DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(),
		"display", "none");

	// appRootPanel.setSpacing(8);

	appRootPanel.setSize("100%", "100%");
	this.startPanel = new StartPanel(false);
	History.newItem("Start");
	History.addValueChangeHandler(new ValueChangeHandler<String>() {
	    public void onValueChange(final ValueChangeEvent<String> event) {
		if (event.getValue().equals("Start")) {
		    clearAppRootPanel();
		    UMLDrawer.this.startPanel = new StartPanel(true);
		    appRootPanel.add(UMLDrawer.this.startPanel, DockPanel.CENTER);
		}
	    }

	});
	log = new Button("ToggleLog");
	log.addClickHandler(new ClickHandler() {
	    public void onClick(final ClickEvent event) {
		final DivLogger dl = Log.getDivLogger();
		final Element e = dl.getWidget().getElement();
		final String p = DOM.getStyleAttribute(e, "display");
		final String v = "none".equals(p) ? "" : "none";
		DOM.setStyleAttribute(e, "display", v);
		Log.getDivLogger().moveTo(log.getAbsoluteLeft(),
			log.getAbsoluteTop() + log.getOffsetHeight() + 10);
	    }
	});
	appRootPanel.add(this.startPanel, DockPanel.CENTER);
	appRootPanel.add(log, DockPanel.SOUTH);
	RootPanel.get().add(appRootPanel);
	Log.getDivLogger().moveTo(log.getAbsoluteLeft(),
		log.getAbsoluteTop() + log.getOffsetHeight() + 10);
    }

    /*
     * Real gwt app entry point, this code allow GWT Log to catch exception and
     * display it (non-Javadoc)
     * 
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    public void onModuleLoad() {
	Log.setUncaughtExceptionHandler();
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		gwt_main();
	    }
	});
    }
}
