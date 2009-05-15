/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.HashMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.SimplePanel;



/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class HistoryManager implements ValueChangeHandler<String> {
    private static String lastHistoryAnchor = "";
    private static HashMap<String, String> lastHistoryParametersList = new HashMap<String, String>();
    private static SimplePanel applicationPanel = new SimplePanel();
    private static String urlDiagram = "";



    /**
     * Initialize the history management and therefore the application 
     * 
     * @param appRootPanel The panel on which we can put the pages
     */
    public void initApplication(DockPanel appRootPanel) {
	History.addValueChangeHandler(this);
	appRootPanel.add(applicationPanel, DockPanel.CENTER);
	applicationPanel.setSize("100%", "100%");
	parseHistoryToken(History.getToken());
	processHistory();
    }

    /* (non-Javadoc)
     * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
     */
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
	String historyToken = event.getValue();	
	parseHistoryToken(historyToken);
	processHistory();
    }


    private void processHistory() {
	applicationPanel.clear();
	DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(), "display", "none");

	OptionsManager.setAllFromURL(lastHistoryParametersList);

	if(lastHistoryAnchor.equals("Drawer")) {
	    DrawerPanel drawerPanel = new DrawerPanel();
	    if(urlDiagram.equals("")) {
		drawerPanel.addDefaultNode();
	    } else {
		Session.getActiveCanvas().fromURL(urlDiagram);
	    }	    
	    applicationPanel.add(drawerPanel);
	} else if(lastHistoryAnchor.equals("Demo")) {
	    DrawerPanel drawerPanel = new DrawerPanel();
	    new Demo(drawerPanel.getUMLCanvas());
	    applicationPanel.add(drawerPanel);
	} else if(lastHistoryAnchor.equals("AnimatedDemo")) {
	    DrawerPanel drawerPanel = new DrawerPanel();
	    new AnimatedDemo(drawerPanel.getUMLCanvas());
	    applicationPanel.add(drawerPanel);
	} else { 
	    if(!lastHistoryAnchor.equals("Start")) {
		History.newItem("Start", false);
	    }
	    applicationPanel.add(new StartPanel(false));
	}
    }

    private void parseHistoryToken(String historyToken) {
	String[] parts = historyToken.split("\\?");
	lastHistoryAnchor = parts[0];
	lastHistoryParametersList.clear();
	if(parts.length > 1) {
	    String[] params = parts[1].split("&");
	    for (int i = 0; i < params.length; i++) {
		String argument = params[i];
		String[] paramVar = argument.split("=", 2);
		if(paramVar.length > 0 && paramVar[0].length() > 0) {
		    if(!paramVar[0].equals("diagram64")) {
			lastHistoryParametersList.put(paramVar[0], paramVar.length > 1 ? paramVar[1] : "");
		    } else {
			urlDiagram  = paramVar.length > 1 ? paramVar[1] : "";
		    }
		}
	    }
	}
    }

    static void upgradeDiagramURL(String url) {	
	String historyToken = lastHistoryAnchor + "?" + OptionsManager.toURL(); 
	if(!historyToken.endsWith("&")) {
	    historyToken += "&";
	}
	historyToken += "diagram64=" + url;
	History.newItem(historyToken, false);
    }
}