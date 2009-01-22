package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawer;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.IncubatorGfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.tatami.TatamiGfxPlatfrom;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager.Theme;

public class StartPanel extends VerticalPanel{
	static StartPanel instance = null;
	private LoadingScreen loadingScreen;
	private HorizontalPanel current_Panel;

	public StartPanel() {
		instance = this;
		loadingScreen = new LoadingScreen();
		loadingScreen.show();


		Log.info("Starting App");

		HotKeyManager.forceStaticInit();

		this.setWidth("100%");
		this.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		this.setSpacing(10);

		Button start = new Button("Start New Uml Class Diagram");
		Button startDemo = new Button("Start Class Diagram Demo");
		final ListBox gfxEngineListBox = new ListBox();
		final ListBox themeListBox = new ListBox();
		start.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				ThemeManager.setCurrentTheme(
						ThemeManager.getThemeFromName(
								themeListBox.getItemText(themeListBox.getSelectedIndex())
						)
				);

				if(gfxEngineListBox.getItemText(gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase("Tatami Gfx"))
					GfxManager.setInstance(new TatamiGfxPlatfrom());
				else
					GfxManager.setInstance(new IncubatorGfxPlatform());

				instance.removeFromParent();
				loadingScreen.show();	
				current_Panel = new DrawerPanel();	
				History.newItem("Drawer");
				History.addHistoryListener(new HistoryListener() {

					public void onHistoryChanged(String historyToken) {
						if (historyToken.equals("Drawer")) {
							UMLDrawer.clearAppRootPanel();
							loadingScreen.show();	
							current_Panel = new DrawerPanel();	
							UMLDrawer.hideLog();
							loadingScreen.hide();		
							UMLDrawer.addtoAppRootPanel(current_Panel);
						}
					}

				});

				UMLDrawer.hideLog();
				loadingScreen.hide();		
				UMLDrawer.addtoAppRootPanel(current_Panel);
			}
		});
		startDemo.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				ThemeManager.setCurrentTheme(
						ThemeManager.getThemeFromName(
								themeListBox.getItemText(themeListBox.getSelectedIndex())
						)
				);

				if(gfxEngineListBox.getItemText(gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase("Tatami Gfx"))
					GfxManager.setInstance(new TatamiGfxPlatfrom());
				else
					GfxManager.setInstance(new IncubatorGfxPlatform());
				instance.removeFromParent();
				loadingScreen.show();		
				current_Panel = new DemoPanel();
				History.newItem("Demo");
				History.addHistoryListener(new HistoryListener() {

					public void onHistoryChanged(String historyToken) {
						if (historyToken.equals("Demo")) {
							UMLDrawer.clearAppRootPanel();
							loadingScreen.show();		
							current_Panel = new DemoPanel();
							UMLDrawer.hideLog();
							loadingScreen.hide();
							UMLDrawer.addtoAppRootPanel(current_Panel);
						}}


				});

				UMLDrawer.hideLog();
				loadingScreen.hide();
				UMLDrawer.addtoAppRootPanel(current_Panel);
			}
		});
		gfxEngineListBox.addItem("Tatami Gfx");
		gfxEngineListBox.addItem("Incubator GWTCanvas Gfx");

		for(Theme theme : Theme.values())
		{
			themeListBox.addItem(ThemeManager.getThemeName(theme));
		}
		this.add(start);
		this.add(startDemo);
		this.add(gfxEngineListBox);
		this.add(themeListBox);


		loadingScreen.hide();
		RootPanel.get().add(this);



	}
}
