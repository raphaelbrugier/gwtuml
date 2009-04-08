package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;

/**
 * @author florian
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

	this.gc = new UMLCanvas(width + 2, height);
	this.gc.setStylePrimaryName("canvas");
	this.add(this.gc);
	Log.trace("Canvas added");

	this.width = width + 2;// Border Size
	this.height = height + 2;// Border Size
	Log.trace("Making shadow");
	makeShadow();

	// TODO : under chrome redraw doesn't work if the canvas is at a
	// different point than (0,0) tatami ? dojo ? chrome ?
	// example : this.setSpacing(50);
	Log.trace("Setting active canvas");
	HotKeyManager.setActiveCanvas(this.gc);
	Log.trace("Disabling browser events");
	UMLDrawerHelper.disableBrowserEvents();
	Log.trace("Init end");
    }

    public void addDefaultClass() {
	final ClassArtifact defaultclass = new ClassArtifact("Class 1");
	defaultclass.setLocation(this.width / 2, this.height / 2);
	this.gc.add(defaultclass);
    }

    public void clearShadow() {
	this.remove(this.bottomShadow);
	this.remove(this.rightShadow);
	this.remove(this.bottomRightCornerShadow);
	this.remove(this.topRightCornerShadow);
	this.remove(this.bottomLeftCornerShadow);
    }

    public UMLCanvas getGc() {
	return this.gc;
    }

    public int getHeight() {
	return this.height;
    }

    public int getWidth() {
	return this.width;
    }

    public void makeShadow() {
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

    public void setHeight(final int height) {
	this.height = height;
    }

    public void setWidth(final int width) {
	this.width = width;
    }

}
