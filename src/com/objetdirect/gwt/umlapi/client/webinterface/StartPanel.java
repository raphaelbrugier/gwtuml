package com.objetdirect.gwt.umlapi.client.webinterface;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.UMLDrawer;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.LinearGeometry;
import com.objetdirect.gwt.umlapi.client.engine.ShapeGeometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.GWTCanvasGfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.IncubatorGfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.tatami.TatamiGfxPlatfrom;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager.QualityLevel;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager.Theme;
/**
 * @author  florian
 */
public class StartPanel extends VerticalPanel {
	
private static StartPanel instance = null;
	
private DrawerPanel drawerPanel;
private LoadingScreen loadingScreen;
	
	final Image logoImg = new Image("gwtumllogo.png");
	final Button startBtn = new Button("Start New Uml Class Diagram...");
	final Button startDemoBtn = new Button("... Or Start The Demo");
	final HorizontalPanel gfxEnginePanel = new HorizontalPanel();
	final Label gfxEngineLbl = new Label("Graphics Engine : ");
	final ListBox gfxEngineListBox = new ListBox();
	final HorizontalPanel geometryStylePanel = new HorizontalPanel();
	final Label geometryStyleLbl = new Label("Geometry Style : ");
	final ListBox geometryStyleListBox = new ListBox();
	final HorizontalPanel themePanel = new HorizontalPanel();
	final Label themeLbl = new Label("Theme : ");
	final ListBox themeListBox = new ListBox();
	final HorizontalPanel resolutionAutoPanel = new HorizontalPanel();
	final CheckBox isResolutionAutoChkBox = new CheckBox(" Auto Resolution");
	final HorizontalPanel qualityPanel = new HorizontalPanel();
	final Label qualityLbl = new Label("Quality : ");
	final ListBox qualityListBox = new ListBox();
	final HorizontalPanel resolutionPanel = new HorizontalPanel();
	final Label resolutionLbl = new Label("Resolution : ");
	final TextBox heightTxtBox = new TextBox();
	final Label crossLbl = new Label("x");
	final TextBox widthTxtBox = new TextBox();
	
	public StartPanel(boolean isFromHistory) {
		instance = this;
		loadingScreen = new LoadingScreen();
		loadingScreen.show();
		Log.trace("Starting App");
		HotKeyManager.forceStaticInit();
		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(10);
		startBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				makeFirstDrawer();
				History.newItem("Drawer", false);
				drawerPanel.addDefaultClass();
				
			}
		});
		startDemoBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				makeFirstDrawer();
				History.newItem("Demo", false);
				new Demo(drawerPanel.getGc());
			}
		});
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				if (event.getValue().equals("Drawer")) {
					makeDrawerForHistory();
					drawerPanel.addDefaultClass();
				}
			}
			
		});
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				if (event.getValue().equals("Demo")) {
					makeDrawerForHistory();
					new Demo(drawerPanel.getGc());
				}
			}
			
		});
		gfxEnginePanel.setSpacing(5);
		geometryStylePanel.setSpacing(5);
		themePanel.setSpacing(5);
		resolutionPanel.setSpacing(5);
		gfxEngineListBox.addItem("Tatami GFX");
		gfxEngineListBox.addItem("Incubator Canvas GFX");
		gfxEngineListBox.addItem("GWT Canvas GFX");
		geometryStyleListBox.addItem("Linear");
		geometryStyleListBox.addItem("Shape Based");
		for (Theme theme : Theme.values()) {
			themeListBox.addItem(ThemeManager.getThemeName(theme));
		}
		isResolutionAutoChkBox.setValue(true);
		isResolutionAutoChkBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				widthTxtBox.setEnabled(!isResolutionAutoChkBox.getValue());
				heightTxtBox.setEnabled(!isResolutionAutoChkBox.getValue());
				
			}});
		

		widthTxtBox.setText("" + (Window.getClientWidth() - 50));
		heightTxtBox.setText("" + (Window.getClientHeight() - 50));
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent arg0) {
				if(isResolutionAutoChkBox.getValue()) {
					drawerPanel.setWidth(Window.getClientWidth() - 50);
					drawerPanel.setHeight(Window.getClientHeight() - 50);
					drawerPanel.setPixelSize( Window.getClientWidth() - 50, Window.getClientHeight() - 50);
					drawerPanel.clearShadow(); 
					drawerPanel.makeShadow();
					GfxManager.getPlatform().setSize(drawerPanel.getGc(), Window.getClientWidth() - 50, Window.getClientHeight() - 50);
				}				
			}
		
		});
		widthTxtBox.setWidth("50px");
		heightTxtBox.setWidth("50px");
		for (QualityLevel qlvl : QualityLevel.values()) {
            qualityListBox.addItem(qlvl.toString());
        }
		qualityListBox.setSelectedIndex(1); // High quality
		
		this.add(logoImg);
		this.add(startBtn);
		this.add(startDemoBtn);
		gfxEnginePanel.add(gfxEngineLbl);
		gfxEnginePanel.add(gfxEngineListBox);
		this.add(gfxEnginePanel);
		geometryStylePanel.add(geometryStyleLbl);
		geometryStylePanel.add(geometryStyleListBox);
		this.add(geometryStylePanel);
		
		themePanel.add(themeLbl);
		themePanel.add(themeListBox);
		this.add(themePanel);
		
		resolutionAutoPanel.add(isResolutionAutoChkBox);
		this.add(resolutionAutoPanel);
		
		resolutionPanel.add(resolutionLbl);
		resolutionPanel.add(widthTxtBox);
		resolutionPanel.add(crossLbl);
		resolutionPanel.add(heightTxtBox);
		this.add(resolutionPanel);
		
		qualityPanel.add(qualityLbl);
		qualityPanel.add(qualityListBox);
		this.add(qualityPanel);
		
		loadingScreen.hide();
		RootPanel.get().add(this);
	}
	
	public void makeFirstDrawer() {
		ThemeManager.setCurrentTheme(Theme
				.getThemeFromName(themeListBox.getItemText(themeListBox
						.getSelectedIndex())));
		if (gfxEngineListBox.getItemText(
				gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase(
				"Tatami GFX"))
			GfxManager.setPlatform(new TatamiGfxPlatfrom());
		else if (gfxEngineListBox.getItemText(
				gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase(
				"Incubator Canvas GFX"))
			GfxManager.setPlatform(new IncubatorGfxPlatform());
		else
			GfxManager.setPlatform(new GWTCanvasGfxPlatform());
		

		if (geometryStyleListBox.getItemText(
                geometryStyleListBox.getSelectedIndex()).equalsIgnoreCase(
                "Linear"))
            GeometryManager.setPlatform(new LinearGeometry());
        else
			GeometryManager.setPlatform(new ShapeGeometry());
		OptionsManager.setQualityLevel(QualityLevel.getQualityFromName(qualityListBox.getItemText(qualityListBox.getSelectedIndex())));
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
		drawerPanel = new DrawerPanel(w, h);	
		
		loadingScreen.hide();
		UMLDrawer.addtoAppRootPanel(drawerPanel);
	}
	
	public void makeDrawerForHistory() {
		
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
		drawerPanel = new DrawerPanel(w, h);
		loadingScreen.hide();
		
		UMLDrawer.addtoAppRootPanel(drawerPanel);
		
	}
}
