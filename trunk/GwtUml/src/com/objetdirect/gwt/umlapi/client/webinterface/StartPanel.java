package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawer;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.IncubatorGfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.tatami.TatamiGfxPlatfrom;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager.Theme;

public class StartPanel extends VerticalPanel {
	static StartPanel instance = null;
	private HorizontalPanel current_Panel;
	private LoadingScreen loadingScreen;

	public StartPanel(boolean isFromHistory) {
		instance = this;
		loadingScreen = new LoadingScreen();
		loadingScreen.show();

		Log.info("Starting App");

		HotKeyManager.forceStaticInit();

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(10);
		final Image logoImg = new Image("gwtumllogo.png");
		final Button startBtn = new Button("Start New Uml Class Diagram");
		final Button startDemoBtn = new Button("Load Class Diagram Demo");
		final HorizontalPanel gfxEnginePanel = new HorizontalPanel();
		final Label gfxEngineLbl = new Label("Graphics Engine : ");
		final ListBox gfxEngineListBox = new ListBox();
		final HorizontalPanel themePanel = new HorizontalPanel();
		final Label themeLbl = new Label("Theme : ");
		final ListBox themeListBox = new ListBox();
		final HorizontalPanel resolutionPanel = new HorizontalPanel();
		final Label resolutionLbl = new Label("Resolution : ");
		final TextBox heightTxtBox = new TextBox();
		final Label crossLbl = new Label("x");
		final TextBox widthTxtBox = new TextBox();
		startBtn.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				ThemeManager.setCurrentTheme(ThemeManager
						.getThemeFromName(themeListBox.getItemText(themeListBox
								.getSelectedIndex())));

				if (gfxEngineListBox.getItemText(
						gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase(
						"Tatami Gfx"))
					GfxManager.setPlatform(new TatamiGfxPlatfrom());
				else
					GfxManager.setPlatform(new IncubatorGfxPlatform());

				instance.removeFromParent();
				loadingScreen.show();
				int w;
				int h;
				try {
					w = Integer.parseInt(widthTxtBox.getText());
					h = Integer.parseInt(heightTxtBox.getText());
				} catch (Exception ex) {
					Log.warn("Unreadable resolution " + widthTxtBox.getText()
							+ "x" + heightTxtBox.getText() + "!");
					w = 800;
					h = 600;
				}

				current_Panel = new DrawerPanel(w, h);
				History.newItem("Drawer", false);

				loadingScreen.hide();
				UMLDrawer.addtoAppRootPanel(current_Panel);
			}
		});

		startDemoBtn.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				ThemeManager.setCurrentTheme(ThemeManager
						.getThemeFromName(themeListBox.getItemText(themeListBox
								.getSelectedIndex())));

				if (gfxEngineListBox.getItemText(
						gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase(
						"Tatami Gfx"))
					GfxManager.setPlatform(new TatamiGfxPlatfrom());
				else
					GfxManager.setPlatform(new IncubatorGfxPlatform());
				instance.removeFromParent();
				loadingScreen.show();
				current_Panel = new DemoPanel();
				History.newItem("Demo", false);

				loadingScreen.hide();
				UMLDrawer.addtoAppRootPanel(current_Panel);
			}
		});

		History.addHistoryListener(new HistoryListener() {
			public void onHistoryChanged(String historyToken) {
				if (historyToken.equals("Drawer")) {
					UMLDrawer.clearAppRootPanel();
					loadingScreen.show();
					int w;
					int h;
					try {
						w = Integer.parseInt(widthTxtBox.getText());
						h = Integer.parseInt(heightTxtBox.getText());
					} catch (Exception ex) {
						Log.warn("Unreadable resolution "
								+ widthTxtBox.getText() + "x"
								+ heightTxtBox.getText() + "! (Hist)");
						w = 800;
						h = 600;
					}

					current_Panel = new DrawerPanel(w, h);
					loadingScreen.hide();
					UMLDrawer.addtoAppRootPanel(current_Panel);
				}
			}
		});
		History.addHistoryListener(new HistoryListener() {

			public void onHistoryChanged(String historyToken) {
				if (historyToken.equals("Demo")) {
					UMLDrawer.clearAppRootPanel();
					loadingScreen.show();
					current_Panel = new DemoPanel();
					loadingScreen.hide();
					UMLDrawer.addtoAppRootPanel(current_Panel);
				}
			}

		});
		gfxEnginePanel.setSpacing(5);
		themePanel.setSpacing(5);
		resolutionPanel.setSpacing(5);
		gfxEngineListBox.addItem("Tatami Gfx");
		gfxEngineListBox.addItem("Incubator GWTCanvas Gfx");

		for (Theme theme : Theme.values()) {
			themeListBox.addItem(ThemeManager.getThemeName(theme));
		}

		widthTxtBox.setText("" + Window.getClientWidth());
		heightTxtBox.setText("" + (Window.getClientHeight() - 20));
		widthTxtBox.setWidth("50px");
		heightTxtBox.setWidth("50px");

		this.add(logoImg);
		this.add(startBtn);
		this.add(startDemoBtn);
		gfxEnginePanel.add(gfxEngineLbl);
		gfxEnginePanel.add(gfxEngineListBox);
		this.add(gfxEnginePanel);
		themePanel.add(themeLbl);
		themePanel.add(themeListBox);
		this.add(themePanel);
		resolutionPanel.add(resolutionLbl);
		resolutionPanel.add(widthTxtBox);
		resolutionPanel.add(crossLbl);
		resolutionPanel.add(heightTxtBox);
		this.add(resolutionPanel);

		loadingScreen.hide();
		RootPanel.get().add(this);

	}
}
