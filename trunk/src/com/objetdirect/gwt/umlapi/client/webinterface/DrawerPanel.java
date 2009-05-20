package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.HashMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager.Theme;

/**
 * This panel is an intermediate panel that contains the graphic canvas <br>
 * And can draw a shadow around it
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class DrawerPanel extends AbsolutePanel {

    private SimplePanel bottomLeftCornerShadow;

    private SimplePanel bottomRightCornerShadow;

    private SimplePanel bottomShadow;
    private final UMLCanvas uMLCanvas;


    private int height;
    private SimplePanel rightShadow;
    private SimplePanel topRightCornerShadow;
    private int width;

    //≺≻⋎⋏    
    FocusPanel topLeft = new FocusPanel();
    FocusPanel top= new FocusPanel();
    FocusPanel topRight = new FocusPanel();
    FocusPanel right = new FocusPanel();
    FocusPanel bottomRight = new FocusPanel();
    FocusPanel bottom = new FocusPanel();
    FocusPanel bottomLeft = new FocusPanel();
    FocusPanel left = new FocusPanel();
    private final HashMap<FocusPanel, Direction> directionPanels = new HashMap<FocusPanel, Direction>() {{
	put(DrawerPanel.this.topLeft, Direction.UP_LEFT);
	put(DrawerPanel.this.top, Direction.UP);
	put(DrawerPanel.this.topRight, Direction.UP_RIGHT);
	put(DrawerPanel.this.right, Direction.RIGHT);
	put(DrawerPanel.this.bottomRight, Direction.DOWN_RIGHT);
	put(DrawerPanel.this.bottom, Direction.DOWN);
	put(DrawerPanel.this.bottomLeft, Direction.DOWN_LEFT);
	put(DrawerPanel.this.left, Direction.LEFT);
    }};

    private final ResizeHandler resizeHandler;

    /**
     * Default constructor of a DrawerPanel
     *
     */
    public DrawerPanel() {
	super();
	ThemeManager.setCurrentTheme((Theme.getThemeFromIndex(OptionsManager.get("Theme"))));
	GfxManager.setPlatform(OptionsManager.get("GraphicEngine"));
	GeometryManager.setPlatform(OptionsManager.get("GeometryStyle"));
	if(OptionsManager.get("AutoResolution") == 0) {
	    this.width = OptionsManager.get("Width");
	    this.height = OptionsManager.get("Height");
	} else {
	    this.width = Window.getClientWidth() - 50;
	    this.height = Window.getClientHeight() - 50;    
	}	

	boolean isShadowed = OptionsManager.get("Shadowed") == 1;
	Log.trace("Creating drawer");

	this.uMLCanvas = new UMLCanvas(new UMLDiagram(UMLDiagram.Type.getUMLDiagramFromIndex(OptionsManager.get("DiagramType"))), this.width, this.height);

	this.add(this.uMLCanvas);

	final int size = 15;

	this.topLeft.setPixelSize(size, size);
	this.top.setPixelSize(getWidth() - 2 * size, size);
	this.topRight.setPixelSize(size, size);
	this.right.setPixelSize(size, getHeight() - 2 * size);
	this.bottomRight.setPixelSize(size, size);
	this.bottom.setPixelSize(getWidth() - 2 * size, size);
	this.bottomLeft.setPixelSize(size, size);
	this.left.setPixelSize(size, getHeight() - 2 * size);

	for (Entry<FocusPanel, Direction> panelEntry: this.directionPanels.entrySet()) {
	    final FocusPanel panel = panelEntry.getKey();
	    final Direction direction = panelEntry.getValue();
	    DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getSelectBoxBackgroundColor().toString());
	    DOM.setStyleAttribute(panel.getElement(), "opacity", "0.1");
	    panel.addMouseOverHandler(new MouseOverHandler() {	
		@Override
		public void onMouseOver(MouseOverEvent event) {
		    DrawerPanel.this.uMLCanvas.setMouseStillInDirectionPanel(true);
		    Scheduler.cancel("Desopacifying");
		    for (final FocusPanel allPanel : DrawerPanel.this.directionPanels.keySet()) {
			DOM.setStyleAttribute(allPanel.getElement(), "opacity", "0.1");
		    }
		    for(double d = 0.1; d < 0.75 ; d+=0.05) {
			final double opacity = d;
			new Scheduler.Task("Opacifying") {@Override public void process() {			     
			    DOM.setStyleAttribute(panel.getElement(), "opacity", Double.toString(opacity));
			}};
		    }
		    new Scheduler.Task("MovingAllArtifacts") {@Override public void process() {		
			DrawerPanel.this.uMLCanvas.moveAll(direction.withSpeed(5));
		    }};
		}
	    });
	    panel.addMouseOutHandler(new MouseOutHandler() {	
		@Override
		public void onMouseOut(MouseOutEvent event) {
		    DrawerPanel.this.uMLCanvas.setMouseStillInDirectionPanel(false);
		    Scheduler.cancel("Opacifying");
		    for (final FocusPanel allPanel : DrawerPanel.this.directionPanels.keySet()) {
			DOM.setStyleAttribute(allPanel.getElement(), "opacity", "0.1");
		    }

		    for(double d = 0.75; d < 0.1 ; d-=0.05) {
			final double opacity = d;
			new Scheduler.Task("Desopacifying") {@Override public void process() {			     
			    DOM.setStyleAttribute(panel.getElement(), "opacity", Double.toString(opacity));
			}};
		    }

		}
	    });
	    panel.addMouseDownHandler(new MouseDownHandler() {
		@Override
		public void onMouseDown(MouseDownEvent event) {
		    Point location = new Point(event.getClientX(), event.getClientY());
		    Mouse.press(DrawerPanel.this.uMLCanvas.getArtifactAt(location), location, event.getNativeButton(), event.isControlKeyDown(), event.isAltKeyDown(), event.isShiftKeyDown(), event.isMetaKeyDown());
		}
	    });
	    panel.addMouseUpHandler(new MouseUpHandler() {
		@Override
		public void onMouseUp(MouseUpEvent event) {
		    Point location = new Point(event.getClientX(), event.getClientY());
		    Mouse.release(DrawerPanel.this.uMLCanvas.getArtifactAt(location), location, event.getNativeButton(), event.isControlKeyDown(), event.isAltKeyDown(), event.isShiftKeyDown(), event.isMetaKeyDown());
		}
	    });
	    panel.addMouseMoveHandler(new MouseMoveHandler() {
		@Override
		public void onMouseMove(MouseMoveEvent event) {
		    Mouse.move(new Point(event.getClientX(), event.getClientY()), event.getNativeButton(), event.isControlKeyDown(), event.isAltKeyDown(), event.isShiftKeyDown(), event.isMetaKeyDown());
		}
	    });
	}

	add(this.topLeft, 0, 0);
	add(this.top, size, 0);
	add(this.topRight, getWidth() - size, 0);
	add(this.right, getWidth() - size, size);
	add(this.bottomRight, getWidth() - size, getHeight() - size);
	add(this.bottom, size, getHeight() - size);
	add(this.bottomLeft, 0, getHeight() - size);
	add(this.left, 0, size);


	Log.trace("Canvas added");
	if(isShadowed) {
	    Log.trace("Making shadow");
	    makeShadow();
	} else {
	    this.uMLCanvas.setStylePrimaryName("canvas");
	}

	this.resizeHandler = new ResizeHandler() {
	    public void onResize(final ResizeEvent resizeEvent) {
		if (OptionsManager.get("AutoResolution") == 1) {
		    DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(), "display", "none");
		    DrawerPanel.this.width = resizeEvent.getWidth() - 50;
		    DrawerPanel.this.height = resizeEvent.getHeight() - 50;
		    DrawerPanel.this.setPixelSize(DrawerPanel.this.width, DrawerPanel.this.height);			
		    DrawerPanel.this.getUMLCanvas().setPixelSize(DrawerPanel.this.width, DrawerPanel.this.height);
		    GfxManager.getPlatform().setSize(Session.getActiveCanvas().getDrawingCanvas(), DrawerPanel.this.width, DrawerPanel.this.height);
		    DrawerPanel.this.clearShadow();
		    DrawerPanel.this.makeShadow();
		    /*
		    DrawerPanel.this.setWidgetPosition(DrawerPanel.this.up, DrawerPanel.this.getWidth() / 2, 10);
		    DrawerPanel.this.setWidgetPosition(DrawerPanel.this.down, DrawerPanel.this.getWidth() / 2, DrawerPanel.this.getHeight() - 10 - 28);
		    DrawerPanel.this.setWidgetPosition(DrawerPanel.this.left, 10, DrawerPanel.this.getHeight() / 2);
		    DrawerPanel.this.setWidgetPosition(DrawerPanel.this.right, DrawerPanel.this.getWidth() - 10 - 28, DrawerPanel.this.getHeight() / 2);
		     */
		}
	    }

	};
	Window.addResizeHandler(this.resizeHandler);


	// TODO : under chrome redraw doesn't work if the canvas is at a
	// different point than (0,0) tatami ? dojo ? chrome ?
	// example : this.setSpacing(50);
	Log.trace("Setting active canvas");
	Session.setActiveCanvas(this.uMLCanvas);
	Log.trace("Disabling browser events");
	UMLDrawerHelper.disableBrowserEvents();
	Log.trace("Init end");
    }



    void clearShadow() {
	this.remove(this.bottomShadow);
	this.remove(this.rightShadow);
	this.remove(this.bottomRightCornerShadow);
	this.remove(this.topRightCornerShadow);
	this.remove(this.bottomLeftCornerShadow);
    }

    void makeShadow() {
	final int shadowSize = 8;

	this.setWidth(this.width + shadowSize + getAbsoluteLeft() + "px");
	this.setHeight(this.height + shadowSize + getAbsoluteTop() + "px");

	this.bottomShadow = new SimplePanel();
	this.bottomShadow.setPixelSize(this.width - shadowSize, shadowSize);
	this.bottomShadow.setStylePrimaryName("bottomShadow");
	this.add(this.bottomShadow, shadowSize, this.height);

	this.rightShadow = new SimplePanel();
	this.rightShadow.setPixelSize(shadowSize, this.height - shadowSize);
	this.rightShadow.setStylePrimaryName("rightShadow");
	this.add(this.rightShadow, this.width, shadowSize);

	this.bottomRightCornerShadow = new SimplePanel();
	this.bottomRightCornerShadow.setPixelSize(shadowSize, shadowSize);
	this.bottomRightCornerShadow.setStylePrimaryName("bottomRightCornerShadow");
	this.add(this.bottomRightCornerShadow, this.width, this.height);

	this.topRightCornerShadow = new SimplePanel();
	this.topRightCornerShadow.setPixelSize(shadowSize, shadowSize);
	this.topRightCornerShadow.setStylePrimaryName("topRightCornerShadow");
	this.add(this.topRightCornerShadow, this.width, 0);

	this.bottomLeftCornerShadow = new SimplePanel();
	this.bottomLeftCornerShadow.setPixelSize(shadowSize, shadowSize);
	this.bottomLeftCornerShadow.setStylePrimaryName("bottomLeftCornerShadow");
	this.add(this.bottomLeftCornerShadow, 0, this.height);
    }

    /**
     * Getter for the height
     *
     * @return the height
     */
    public final int getHeight() {
	return this.height;
    }

    /**
     * Getter for the width
     *
     * @return the width
     */
    public final int getWidth() {
	return this.width;
    }

    /**
     * Setter for the height
     *
     * @param height the height to set
     */
    public final void setHeight(int height) {
	this.height = height;
    }

    /**
     * Setter for the width
     *
     * @param width the width to set
     */
    public final void setWidth(int width) {
	this.width = width; 
    }
    /**
     * Getter for the uMLCanvas
     *
     * @return the uMLCanvas
     */
    public final UMLCanvas getUMLCanvas() {
	return this.uMLCanvas;
    }


    void addDefaultNode() {
	Type type = UMLDiagram.Type.getUMLDiagramFromIndex(OptionsManager.get("DiagramType"));
	if(type.isClassType()) {
	    final ClassArtifact defaultclass = new ClassArtifact("Class1");
	    defaultclass.setLocation(new Point(this.width / 2, this.height / 2));
	    this.uMLCanvas.add(defaultclass);
	}
	if(type.isObjectType()) {
	    final ObjectArtifact defaultobject = new ObjectArtifact("obj1", "Object1");
	    defaultobject.setLocation(new Point(this.width / 3, this.height / 3));
	    this.uMLCanvas.add(defaultobject);
	}
    }
}
