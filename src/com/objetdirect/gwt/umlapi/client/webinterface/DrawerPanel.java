package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;

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

    /**
     * Constructor of a drawer panel
     *
     * @param width The canvas width
     * @param height The canvas height
     * @param isShadowed If true draws a shadow around the canvas
     */
    public DrawerPanel(final int width, final int height, final boolean isShadowed) {
	Log.trace("Creating drawer");

	this.uMLCanvas = new UMLCanvas(width + 2, height);
	this.uMLCanvas.setStylePrimaryName("canvas");
	this.add(this.uMLCanvas);
	Log.trace("Canvas added");
	if(isShadowed) {
	    this.width = width + 2;// Border Size
	    this.height = height + 2;// Border Size
	    Log.trace("Making shadow");
	    makeShadow();
	}
	// TODO : under chrome redraw doesn't work if the canvas is at a
	// different point than (0,0) tatami ? dojo ? chrome ?
	// example : this.setSpacing(50);
	Log.trace("Setting active canvas");
	HotKeyManager.setActiveCanvas(this.uMLCanvas);
	Log.trace("Disabling browser events");
	UMLDrawerHelper.disableBrowserEvents();
	Log.trace("Init end");
    }

    void addDefaultClass() {
	final ClassArtifact defaultclass = new ClassArtifact("Class 1");
	defaultclass.setInitialLocation(new Point(this.width / 2, this.height / 2));
	this.uMLCanvas.add(defaultclass);
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

}
