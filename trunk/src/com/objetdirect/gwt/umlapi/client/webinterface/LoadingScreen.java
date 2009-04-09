package com.objetdirect.gwt.umlapi.client.webinterface;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.gwt.umlapi.client.webinterface.CursorIconManager.PointerStyle;

public class LoadingScreen {
    private final HorizontalPanel loadingPanel;

    public LoadingScreen() {
	final Image loader = new Image("ajax-loader.gif");
	this.loadingPanel = new HorizontalPanel();
	this.loadingPanel
		.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	this.loadingPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	this.loadingPanel.setSize("100%", "100%");
	this.loadingPanel.add(loader);
	this.loadingPanel.setVisible(false);
	RootPanel.get().add(this.loadingPanel, 0, 0);
    }

    public void hide() {
	CursorIconManager.setCursorIcon(PointerStyle.AUTO);
	this.loadingPanel.setVisible(false);
    }

    public void show() {
	CursorIconManager.setCursorIcon(PointerStyle.WAIT);
	this.loadingPanel.setVisible(true);
    }
}
