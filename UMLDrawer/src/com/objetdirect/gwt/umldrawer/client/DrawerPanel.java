/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umldrawer.client;

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
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LifeLineArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Direction;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.helpers.Mouse;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.helpers.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager.Theme;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;

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
	this.uMLCanvas.makeArrows(this.width, this.height);
	this.add(this.uMLCanvas);

	final int directionPanelSizes = OptionsManager.get("DirectionPanelSizes");

	final HashMap<FocusPanel, Point> panelsSizes = makeDirectionPanelsSizes(directionPanelSizes);
	final HashMap<FocusPanel, Point> panelsPositions =  makeDirectionPanelsPositions(directionPanelSizes);



	for (Entry<FocusPanel, Direction> panelEntry: this.directionPanels.entrySet()) {
	    final FocusPanel panel = panelEntry.getKey();
	    final Direction direction = panelEntry.getValue();
	    DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getDirectionPanelColor().toString());
	    DOM.setStyleAttribute(panel.getElement(), "opacity", Double.toString(((double) OptionsManager.get("DirectionPanelOpacity")) / 100));
	    panel.addMouseOverHandler(new MouseOverHandler() {	
		@Override
		public void onMouseOver(final MouseOverEvent event) {

		    for(double d = ((double) OptionsManager.get("DirectionPanelOpacity")) / 100 ; d <= ((double) OptionsManager.get("DirectionPanelMaxOpacity")) / 100; d += 0.05) {
			final double opacity = Math.ceil(d*100)/100;

			new Scheduler.Task("Opacifying") {@Override public void process() {
			    DOM.setStyleAttribute(panel.getElement(), "opacity", Double.toString(opacity));
			}};
		    }
		    new Scheduler.Task("MovingAllArtifacts") {@Override public void process() {
			Scheduler.cancel("MovingAllArtifactsRecursive");
			DrawerPanel.this.uMLCanvas.moveAll(direction.withSpeed(Direction.getDependingOnQualityLevelSpeed()), true);
		    }};
		}
	    });
	    panel.addMouseOutHandler(new MouseOutHandler() {	
		@Override
		public void onMouseOut(MouseOutEvent event) {
		    Scheduler.cancel("Opacifying");
		    Scheduler.cancel("MovingAllArtifacts");
		    Scheduler.cancel("MovingAllArtifactsRecursive");
		    for(final FocusPanel onePanel : DrawerPanel.this.directionPanels.keySet()) {
			double currentOpacity = 0;
			try {
			    currentOpacity = Math.ceil(Double.parseDouble(DOM.getStyleAttribute(onePanel.getElement(), "opacity"))*100)/100;
			} catch (Exception ex) {
			    Log.error("Unable to parse element opacity : " + ex);
			}
			for(double d = currentOpacity ; d >= ((double) OptionsManager.get("DirectionPanelOpacity")) / 100 ; d -= 0.05) {
			    final double opacity = Math.ceil(d*100)/100;

			    new Scheduler.Task("Desopacifying") {@Override public void process() {
				DOM.setStyleAttribute(onePanel.getElement(), "opacity", Double.toString(opacity));
			    }};
			}
		    }
		}
	    });
	    panel.addMouseDownHandler(new MouseDownHandler() {
		@Override
		public void onMouseDown(MouseDownEvent event) {
		    DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getDirectionPanelPressedColor().toString());
		    Scheduler.cancel("MovingAllArtifactsRecursive");
		}
	    });
	    panel.addMouseUpHandler(new MouseUpHandler() {
		@Override
		public void onMouseUp(MouseUpEvent event) {
		    DOM.setStyleAttribute(panel.getElement(), "backgroundColor", ThemeManager.getTheme().getDirectionPanelColor().toString());
		    DrawerPanel.this.uMLCanvas.moveAll(direction.withSpeed(Math.min(DrawerPanel.this.uMLCanvas.getOffsetHeight(), DrawerPanel.this.uMLCanvas.getOffsetWidth())), false);
		}
	    });
	    panel.addMouseMoveHandler(new MouseMoveHandler() {
		@Override
		public void onMouseMove(MouseMoveEvent event) {
		    Mouse.move(new Point(event.getClientX(), event.getClientY()), event.getNativeButton(), event.isControlKeyDown(), event.isAltKeyDown(), event.isShiftKeyDown(), event.isMetaKeyDown());
		}
	    });
	    Point panelPosition = panelsPositions.get(panel);
	    Point panelSize = panelsSizes.get(panel);
	    panel.setPixelSize(panelSize.getX(), panelSize.getY());
	    this.add(panel, panelPosition.getX(), panelPosition.getY());

	}

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
		    //DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(), "display", "none");
		    DrawerPanel.this.width = resizeEvent.getWidth() - 50;
		    DrawerPanel.this.height = resizeEvent.getHeight() - 50;
		    DrawerPanel.this.setPixelSize(DrawerPanel.this.width, DrawerPanel.this.height);			
		    DrawerPanel.this.getUMLCanvas().setPixelSize(DrawerPanel.this.width, DrawerPanel.this.height);
		    GfxManager.getPlatform().setSize(Session.getActiveCanvas().getDrawingCanvas(), DrawerPanel.this.width, DrawerPanel.this.height);
		    DrawerPanel.this.clearShadow();
		    DrawerPanel.this.makeShadow();
		    final HashMap<FocusPanel, Point> panelsNewSizes = makeDirectionPanelsSizes(directionPanelSizes);
		    final HashMap<FocusPanel, Point> panelsNewPositions =  makeDirectionPanelsPositions(directionPanelSizes);
		    for (FocusPanel panel: DrawerPanel.this.directionPanels.keySet()) {
			Point panelPosition = panelsNewPositions.get(panel);
			Point panelSize = panelsNewSizes.get(panel);
			panel.setPixelSize(panelSize.getX(), panelSize.getY());
			setWidgetPosition(panel, panelPosition.getX(), panelPosition.getY());
		    } 
		    DrawerPanel.this.uMLCanvas.clearArrows();
		    DrawerPanel.this.uMLCanvas.makeArrows(DrawerPanel.this.width, DrawerPanel.this.height);
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

    private HashMap<FocusPanel, Point> makeDirectionPanelsPositions(final int directionPanelSizes) {
	return new HashMap<FocusPanel, Point>() {{
	    put(DrawerPanel.this.topLeft, Point.getOrigin());
	    put(DrawerPanel.this.top, new Point(directionPanelSizes, 0));
	    put(DrawerPanel.this.topRight, new Point(getWidth() - directionPanelSizes, 0));
	    put(DrawerPanel.this.right, new Point(getWidth() - directionPanelSizes, directionPanelSizes));
	    put(DrawerPanel.this.bottomRight, new Point(getWidth() - directionPanelSizes, getHeight() - directionPanelSizes));
	    put(DrawerPanel.this.bottom, new Point(directionPanelSizes, getHeight() - directionPanelSizes));
	    put(DrawerPanel.this.bottomLeft, new Point(0, getHeight() - directionPanelSizes));
	    put(DrawerPanel.this.left, new Point(0, directionPanelSizes));
	}};
    }



    private HashMap<FocusPanel, Point> makeDirectionPanelsSizes(final int directionPanelSizes) {
	return  new HashMap<FocusPanel, Point>() {{
	    put(DrawerPanel.this.topLeft, new Point(directionPanelSizes, directionPanelSizes));
	    put(DrawerPanel.this.top, new Point(getWidth() - 2 * directionPanelSizes, directionPanelSizes));
	    put(DrawerPanel.this.topRight, new Point(directionPanelSizes, directionPanelSizes));
	    put(DrawerPanel.this.right, new Point(directionPanelSizes, getHeight() - 2 * directionPanelSizes));
	    put(DrawerPanel.this.bottomRight, new Point(directionPanelSizes, directionPanelSizes));
	    put(DrawerPanel.this.bottom, new Point(getWidth() - 2 * directionPanelSizes, directionPanelSizes));
	    put(DrawerPanel.this.bottomLeft, new Point(directionPanelSizes, directionPanelSizes));
	    put(DrawerPanel.this.left, new Point(directionPanelSizes, getHeight() - 2 * directionPanelSizes));
	}};
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
	if(type == Type.SEQUENCE) {
	    final LifeLineArtifact defaultLifeLineArtifact = new LifeLineArtifact("LifeLine1", "ll1");
	    defaultLifeLineArtifact.setLocation(new Point(this.width / 2, this.height / 2));
	    this.uMLCanvas.add(defaultLifeLineArtifact);
	}
    }
    /* (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Widget#onAttach()
     */
    @Override
    protected void onAttach() {
	// TODO Auto-generated method stub
	super.onAttach();

    }
}

