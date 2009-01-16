package com.objetdirect.gwt.umlapi.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
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
	
	public static Boolean isDivShown;
	
	public final static DockPanel appRootPanel = new DockPanel();
	
	public static void addtoAppRootPanel(Widget w) {
		appRootPanel.add(w, DockPanel.CENTER);
	}
	public static void clearAppRootPanel() {
		appRootPanel.clear();
	}
	public void onModuleLoad() {
		
		Log.setCurrentLogLevel(Log.LOG_LEVEL_WARN);
		
		appRootPanel.setSize("100%", "100%");
		
		StartPanel startPanel = new StartPanel();
		
		Log.getDivLogger().moveTo(-10000, -10000);
		
		final Button log = new Button("ToggleLog");
		isDivShown  = false;
		log.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				isDivShown = ! isDivShown;
				if(isDivShown)
					Log.getDivLogger().moveTo(log.getAbsoluteLeft() , log.getAbsoluteTop() + log.getOffsetHeight() + 10);
				else
					Log.getDivLogger().moveTo(-10000, -10000);
		}
		});
		
		appRootPanel.add(startPanel, DockPanel.CENTER);
		appRootPanel.add(log, DockPanel.SOUTH);
		RootPanel.get().add(appRootPanel);
		
	}

	public static void hideLog() {
		isDivShown = false;
		Log.getDivLogger().moveTo(-10000, -10000);
		
	}

}
