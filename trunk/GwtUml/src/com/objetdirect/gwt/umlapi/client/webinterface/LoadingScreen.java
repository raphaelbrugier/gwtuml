package com.objetdirect.gwt.umlapi.client.webinterface;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class LoadingScreen {

	private HorizontalPanel loadingPanel;

	public LoadingScreen() {
		Image loader = new Image("ajax-loader.gif");
		loadingPanel = new HorizontalPanel();
		loadingPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		loadingPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		loadingPanel.setSize("100%", "100%");	
		loadingPanel.add(loader);
		loadingPanel.setVisible(false);
		RootPanel.get().add(loadingPanel, 0, 0);
	}

	public void show() {
		loadingPanel.setVisible(true);
	}

	public void hide() {
		loadingPanel.setVisible(false);
	}

}
