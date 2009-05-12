/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.HashMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;


/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class HistoryManager implements ValueChangeHandler<String> {
    private static String lastHistoryAnchor = "";
    private static HashMap<String, String> lastHistoryParametersList = new HashMap<String, String>();

    public void initHistory() {
	History.addValueChangeHandler(this);
	if(!History.getToken().equals("")) {
	    parseHistoryToken(History.getToken());
	}
    }

    /* (non-Javadoc)
     * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
     */
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
	String historyToken = event.getValue();	
	parseHistoryToken(historyToken);
    }

    private void parseHistoryToken(String historyToken) {
	String[] parts = historyToken.split("\\?");
	lastHistoryAnchor = parts[0];
	lastHistoryParametersList.clear();
	if(parts.length > 1) {
	    String[] params = parts[1].split("&");

	    for (int i = 0; i < params.length; i++) {
		String argument = params[i];
		String[] paramVar = argument.split("=");
		if(paramVar.length > 0&& paramVar[0].length() > 0) {
		    lastHistoryParametersList.put(paramVar[0], paramVar.length > 1 ? paramVar[1] : "");
		}
	    }
	}
	Log.fatal("Page : " +lastHistoryAnchor);
	for (Entry<String, String> param : lastHistoryParametersList.entrySet()) {
	    Log.fatal("K : " + param.getKey() + " V : " + param.getValue());

	}
    }
}

