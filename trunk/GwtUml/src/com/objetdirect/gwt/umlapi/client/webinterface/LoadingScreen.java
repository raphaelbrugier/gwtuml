package com.objetdirect.gwt.umlapi.client.webinterface;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.objetdirect.gwt.umlapi.client.webinterface.CursorIconManager.PointerStyle;
public class LoadingScreen {
	private HorizontalPanel loadingPanel;
	public LoadingScreen() {
		Image loader = new Image("ajax-loader.gif");
		loadingPanel = new HorizontalPanel();
		loadingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		loadingPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		loadingPanel.setSize("100%", "100%");
		loadingPanel.add(loader);
		loadingPanel.setVisible(false);
		RootPanel.get().add(loadingPanel, 0, 0);
	}
	public void hide() {
		CursorIconManager.setCursorIcon(PointerStyle.AUTO);
		loadingPanel.setVisible(false);
	}
	public void show() {
		CursorIconManager.setCursorIcon(PointerStyle.WAIT);
		loadingPanel.setVisible(true);
	}
}
