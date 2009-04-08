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
 * @author florian
 */
public class StartPanel extends VerticalPanel {

    private static StartPanel instance = null;

    final Label crossLbl = new Label("x");
    private DrawerPanel drawerPanel;

    final Label geometryStyleLbl = new Label("Geometry Style : ");
    final ListBox geometryStyleListBox = new ListBox();
    final HorizontalPanel geometryStylePanel = new HorizontalPanel();
    final Label gfxEngineLbl = new Label("Graphics Engine : ");
    final ListBox gfxEngineListBox = new ListBox();
    final HorizontalPanel gfxEnginePanel = new HorizontalPanel();
    final TextBox heightTxtBox = new TextBox();
    final CheckBox isResolutionAutoChkBox = new CheckBox(" Auto Resolution");
    private final LoadingScreen loadingScreen;
    final Image logoImg = new Image("gwtumllogo.png");
    final Label qualityLbl = new Label("Quality : ");
    final ListBox qualityListBox = new ListBox();
    final HorizontalPanel qualityPanel = new HorizontalPanel();
    final HorizontalPanel resolutionAutoPanel = new HorizontalPanel();
    final Label resolutionLbl = new Label("Resolution : ");
    final HorizontalPanel resolutionPanel = new HorizontalPanel();
    final Button startBtn = new Button("Start New Uml Class Diagram...");
    final Button startDemoBtn = new Button("... Or Start The Demo");
    final Label themeLbl = new Label("Theme : ");
    final ListBox themeListBox = new ListBox();
    final HorizontalPanel themePanel = new HorizontalPanel();
    final TextBox widthTxtBox = new TextBox();

    public StartPanel(final boolean isFromHistory) {
	instance = this;
	this.loadingScreen = new LoadingScreen();
	this.loadingScreen.show();
	Log.trace("Starting App");
	HotKeyManager.forceStaticInit();
	setWidth("100%");
	setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	setSpacing(10);
	this.startBtn.addClickHandler(new ClickHandler() {
	    public void onClick(final ClickEvent event) {
		makeFirstDrawer();
		History.newItem("Drawer", false);
		StartPanel.this.drawerPanel.addDefaultClass();

	    }
	});
	this.startDemoBtn.addClickHandler(new ClickHandler() {
	    public void onClick(final ClickEvent event) {
		makeFirstDrawer();
		History.newItem("Demo", false);
		new Demo(StartPanel.this.drawerPanel.getGc());
	    }
	});
	History.addValueChangeHandler(new ValueChangeHandler<String>() {
	    public void onValueChange(final ValueChangeEvent<String> event) {
		if (event.getValue().equals("Drawer")) {
		    makeDrawerForHistory();
		    StartPanel.this.drawerPanel.addDefaultClass();
		}
	    }

	});
	History.addValueChangeHandler(new ValueChangeHandler<String>() {
	    public void onValueChange(final ValueChangeEvent<String> event) {
		if (event.getValue().equals("Demo")) {
		    makeDrawerForHistory();
		    new Demo(StartPanel.this.drawerPanel.getGc());
		}
	    }

	});
	this.gfxEnginePanel.setSpacing(5);
	this.geometryStylePanel.setSpacing(5);
	this.themePanel.setSpacing(5);
	this.resolutionPanel.setSpacing(5);
	this.gfxEngineListBox.addItem("Tatami GFX");
	this.gfxEngineListBox.addItem("Incubator Canvas GFX");
	this.gfxEngineListBox.addItem("GWT Canvas GFX");
	this.geometryStyleListBox.addItem("Linear");
	this.geometryStyleListBox.addItem("Shape Based");
	for (final Theme theme : Theme.values()) {
	    this.themeListBox.addItem(ThemeManager.getThemeName(theme));
	}
	this.isResolutionAutoChkBox.setValue(true);
	this.widthTxtBox.setEnabled(false);
	this.heightTxtBox.setEnabled(false);
	this.isResolutionAutoChkBox.addClickHandler(new ClickHandler() {
	    public void onClick(final ClickEvent event) {
		StartPanel.this.widthTxtBox.setEnabled(!StartPanel.this.isResolutionAutoChkBox.getValue());
		StartPanel.this.heightTxtBox.setEnabled(!StartPanel.this.isResolutionAutoChkBox.getValue());

	    }
	});

	this.widthTxtBox.setText("" + (Window.getClientWidth() - 50));
	this.heightTxtBox.setText("" + (Window.getClientHeight() - 50));
	Window.addResizeHandler(new ResizeHandler() {
	    public void onResize(final ResizeEvent arg0) {
		if (StartPanel.this.isResolutionAutoChkBox.getValue()) {
		    if (StartPanel.this.drawerPanel != null) {
			StartPanel.this.drawerPanel.setWidth(Window.getClientWidth() - 50);
			StartPanel.this.drawerPanel.setHeight(Window.getClientHeight() - 50);
			StartPanel.this.drawerPanel.setPixelSize(Window.getClientWidth() - 50,
				Window.getClientHeight() - 50);
			StartPanel.this.drawerPanel.clearShadow();
			StartPanel.this.drawerPanel.makeShadow();
			GfxManager.getPlatform().setSize(StartPanel.this.drawerPanel.getGc(),
				Window.getClientWidth() - 50,
				Window.getClientHeight() - 50);
		    }
		}
	    }

	});
	this.widthTxtBox.setWidth("50px");
	this.heightTxtBox.setWidth("50px");
	for (final QualityLevel qlvl : QualityLevel.values()) {
	    this.qualityListBox.addItem(qlvl.toString());
	}
	this.qualityListBox.setSelectedIndex(1); // High quality

	this.add(this.logoImg);
	this.add(this.startBtn);
	this.add(this.startDemoBtn);
	this.gfxEnginePanel.add(this.gfxEngineLbl);
	this.gfxEnginePanel.add(this.gfxEngineListBox);
	this.add(this.gfxEnginePanel);
	this.geometryStylePanel.add(this.geometryStyleLbl);
	this.geometryStylePanel.add(this.geometryStyleListBox);
	this.add(this.geometryStylePanel);

	this.themePanel.add(this.themeLbl);
	this.themePanel.add(this.themeListBox);
	this.add(this.themePanel);

	this.resolutionAutoPanel.add(this.isResolutionAutoChkBox);
	this.add(this.resolutionAutoPanel);

	this.resolutionPanel.add(this.resolutionLbl);
	this.resolutionPanel.add(this.widthTxtBox);
	this.resolutionPanel.add(this.crossLbl);
	this.resolutionPanel.add(this.heightTxtBox);
	this.add(this.resolutionPanel);

	this.qualityPanel.add(this.qualityLbl);
	this.qualityPanel.add(this.qualityListBox);
	this.add(this.qualityPanel);

	this.loadingScreen.hide();
	RootPanel.get().add(this);
    }

    public void makeDrawerForHistory() {

	UMLDrawer.clearAppRootPanel();
	this.loadingScreen.show();
	int w;
	int h;
	try {
	    w = Integer.parseInt(this.widthTxtBox.getText());
	    h = Integer.parseInt(this.heightTxtBox.getText());
	} catch (final Exception ex) {
	    Log.warn("Unreadable resolution " + this.widthTxtBox.getText() + "x"
		    + this.heightTxtBox.getText() + "! (Hist)");
	    w = 800;
	    h = 600;
	}
	this.drawerPanel = new DrawerPanel(w, h);
	this.loadingScreen.hide();

	UMLDrawer.addtoAppRootPanel(this.drawerPanel);

    }

    public void makeFirstDrawer() {
	ThemeManager.setCurrentTheme(Theme.getThemeFromName(this.themeListBox
		.getItemText(this.themeListBox.getSelectedIndex())));
	if (this.gfxEngineListBox.getItemText(this.gfxEngineListBox.getSelectedIndex())
		.equalsIgnoreCase("Tatami GFX")) {
	    GfxManager.setPlatform(new TatamiGfxPlatfrom());
	} else if (this.gfxEngineListBox.getItemText(
		this.gfxEngineListBox.getSelectedIndex()).equalsIgnoreCase(
		"Incubator Canvas GFX")) {
	    GfxManager.setPlatform(new IncubatorGfxPlatform());
	} else {
	    GfxManager.setPlatform(new GWTCanvasGfxPlatform());
	}

	if (this.geometryStyleListBox.getItemText(
		this.geometryStyleListBox.getSelectedIndex()).equalsIgnoreCase(
		"Linear")) {
	    GeometryManager.setPlatform(new LinearGeometry());
	} else {
	    GeometryManager.setPlatform(new ShapeGeometry());
	}
	OptionsManager.setQualityLevel(QualityLevel
		.getQualityFromName(this.qualityListBox.getItemText(this.qualityListBox
			.getSelectedIndex())));
	instance.removeFromParent();
	this.loadingScreen.show();
	int w;
	int h;
	try {
	    w = Integer.parseInt(this.widthTxtBox.getText());
	    h = Integer.parseInt(this.heightTxtBox.getText());
	} catch (final Exception ex) {
	    Log.warn("Unreadable resolution " + this.widthTxtBox.getText() + "x"
		    + this.heightTxtBox.getText() + "!");
	    w = 800;
	    h = 600;
	}
	this.drawerPanel = new DrawerPanel(w, h);

	this.loadingScreen.hide();
	UMLDrawer.addtoAppRootPanel(this.drawerPanel);
    }
}
