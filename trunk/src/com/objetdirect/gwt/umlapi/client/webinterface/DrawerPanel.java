package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class DrawerPanel extends AbsolutePanel {

    private SimplePanel bottomLeftCornerShadow;

    private SimplePanel bottomRightCornerShadow;

    private SimplePanel bottomShadow;
    private final UMLCanvas gc;
    private int height;
    private SimplePanel rightShadow;
    private SimplePanel topRightCornerShadow;
    private int width;

    public DrawerPanel(final int width, final int height) {
	Log.trace("Creating drawer");

	gc = new UMLCanvas(width + 2, height);
	gc.setStylePrimaryName("canvas");
	this.add(gc);
	Log.trace("Canvas added");

	this.width = width + 2;// Border Size
	this.height = height + 2;// Border Size
	Log.trace("Making shadow");
	makeShadow();

	// TODO : under chrome redraw doesn't work if the canvas is at a
	// different point than (0,0) tatami ? dojo ? chrome ?
	// example : this.setSpacing(50);
	Log.trace("Setting active canvas");
	HotKeyManager.setActiveCanvas(gc);
	Log.trace("Disabling browser events");
	UMLDrawerHelper.disableBrowserEvents();
	Log.trace("Init end");
    }

    public void addDefaultClass() {
	final ClassArtifact defaultclass = new ClassArtifact("Class 1");
	defaultclass.setLocation(new Point(width / 2, height / 2));
	gc.add(defaultclass);
    }

    public void clearShadow() {
	this.remove(bottomShadow);
	this.remove(rightShadow);
	this.remove(bottomRightCornerShadow);
	this.remove(topRightCornerShadow);
	this.remove(bottomLeftCornerShadow);
    }

    public UMLCanvas getGc() {
	return gc;
    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public void makeShadow() {
	final int shadowSize = 8;

	this.setWidth(width + shadowSize + getAbsoluteLeft() + "px");
	this.setHeight(height + shadowSize + getAbsoluteTop() + "px");

	bottomShadow = new SimplePanel();
	bottomShadow.setPixelSize(width - shadowSize, shadowSize);
	bottomShadow.setStylePrimaryName("bottomShadow");
	this.add(bottomShadow, shadowSize, height);

	rightShadow = new SimplePanel();
	rightShadow.setPixelSize(shadowSize, height - shadowSize);
	rightShadow.setStylePrimaryName("rightShadow");
	this.add(rightShadow, width, shadowSize);

	bottomRightCornerShadow = new SimplePanel();
	bottomRightCornerShadow.setPixelSize(shadowSize, shadowSize);
	bottomRightCornerShadow.setStylePrimaryName("bottomRightCornerShadow");
	this.add(bottomRightCornerShadow, width, height);

	topRightCornerShadow = new SimplePanel();
	topRightCornerShadow.setPixelSize(shadowSize, shadowSize);
	topRightCornerShadow.setStylePrimaryName("topRightCornerShadow");
	this.add(topRightCornerShadow, width, 0);

	bottomLeftCornerShadow = new SimplePanel();
	bottomLeftCornerShadow.setPixelSize(shadowSize, shadowSize);
	bottomLeftCornerShadow.setStylePrimaryName("bottomLeftCornerShadow");
	this.add(bottomLeftCornerShadow, 0, height);
    }

    public void setHeight(final int height) {
	this.height = height;
    }

    public void setWidth(final int width) {
	this.width = width;
    }

}
