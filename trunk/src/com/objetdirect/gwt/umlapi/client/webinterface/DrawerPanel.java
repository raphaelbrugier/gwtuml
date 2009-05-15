package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
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
    
    private final Button up = new Button("⋏");//≺≻⋎⋏
    private final Button down = new Button("⋎");//≺≻⋎⋏
    private final Button left = new Button("≺");//≺≻⋎⋏
    private final Button right = new Button("≻");//≺≻⋎⋏
    
    public DrawerPanel() {
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

	this.uMLCanvas = new UMLCanvas(new UMLDiagram(UMLDiagram.Type.getUMLDiagramFromIndex(OptionsManager.get("DiagramType"))), this.width + 2, this.height);
	this.uMLCanvas.setStylePrimaryName("canvas");
	this.add(this.uMLCanvas);
	Log.trace("Canvas added");
	if(isShadowed) {
	    this.width += 2;// Border Size
	    this.height += 2;// Border Size
	    Log.trace("Making shadow");
	    makeShadow();
	}
	
	this.add(this.up, this.getWidth() / 2, 10);
	this.add(this.down, this.getWidth() / 2, this.getHeight() - 10 - 28);
	this.add(this.left, 10, this.getHeight() / 2);
	this.add(this.right, this.getWidth() - 10 - 28, this.getHeight() / 2);
	
	this.up.addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(ClickEvent event) {
		Session.getActiveCanvas().moveAll(Direction.DOWN);
	    }
	});
	this.down.addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(ClickEvent event) {
		Session.getActiveCanvas().moveAll(Direction.UP);
	    }
	});
	this.left.addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(ClickEvent event) {
		Session.getActiveCanvas().moveAll(Direction.RIGHT);
	    }
	});
	this.right.addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(ClickEvent event) {
		Session.getActiveCanvas().moveAll(Direction.LEFT);
	    }
	});
	
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
		final ClassArtifact defaultclass = new ClassArtifact("Class 1");
		defaultclass.setInitialLocation(new Point(this.width / 2, this.height / 2));
		this.uMLCanvas.add(defaultclass);
	}
	if(type.isObjectType()) {
		final ObjectArtifact defaultobject = new ObjectArtifact("obj1:Object 1");
		defaultobject.setInitialLocation(new Point(this.width / 3, this.height / 3));
		this.uMLCanvas.add(defaultobject);
	}
	
    }

}
