package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
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

	public StartPanel() {
		if (instance == null) instance = this;
		else return;
		loadingScreen = new LoadingScreen();
		loadingScreen.show();

		Log.info("Starting App");

		HotKeyManager.forceStaticInit();

		this.setWidth("100%");
		this.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		this.setSpacing(10);

		Button start = new Button("Start New Uml Class Diagram");
		Button startDemo = new Button("Start Demo");
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
		    	DrawerPanel drawerPanel = new DrawerPanel();				
				UMLDrawer.hideLog();
				loadingScreen.hide();		
				UMLDrawer.addtoAppRootPanel(drawerPanel);
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
				DemoPanel demo = new DemoPanel();
				UMLDrawer.hideLog();
				loadingScreen.hide();
				UMLDrawer.addtoAppRootPanel(demo);
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
