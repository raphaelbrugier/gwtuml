package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;

/**
 * @author  florian
 */
public class DrawerPanel extends AbsolutePanel {
	
	/**
	 * @uml.property  name="gc"
	 * @uml.associationEnd  
	 */
	private final UMLCanvas gc;
	/**
	 * @uml.property  name="height"
	 */
	private int height;
	/**
	 * @uml.property  name="width"
	 */
	private int width;
	private SimplePanel bottomShadow;
	private SimplePanel rightShadow;
	private SimplePanel bottomRightCornerShadow;
	private SimplePanel topRightCornerShadow;
	private SimplePanel bottomLeftCornerShadow;
	
	public DrawerPanel(int width, int height) {
		Log.info("Creating drawer");

		
		gc = new UMLCanvas(width, height);
		gc.setStylePrimaryName("canvas");
		this.add(gc);
		
		width += 2; //Border Size
		height += 2; //Border Size		
		this.width = width;
		this.height = height;
		makeShadow();
		
		// TODO : under chrome redraw doesn't work if the canvas is at a
		// different point than (0,0) tatami ? dojo ? chrome ?
		// example : this.setSpacing(50);

		HotKeyManager.setActiveCanvas(gc);
		UMLDrawerHelper.disableBrowserEvents();
		Log.info("Init end");

	}

	/**
	 * @return
	 * @uml.property  name="height"
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 * @uml.property  name="height"
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return
	 * @uml.property  name="width"
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 * @uml.property  name="width"
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return
	 * @uml.property  name="gc"
	 */
	public UMLCanvas getGc() {
		return gc;
	}
	
	public void clearShadow() {
		this.remove(bottomShadow);
		this.remove(rightShadow);
		this.remove(bottomRightCornerShadow);
		this.remove(topRightCornerShadow);
		this.remove(bottomLeftCornerShadow);		
	}
	
	public void makeShadow() {
		int shadowSize = 8;
		
		this.setWidth(width + shadowSize + this.getAbsoluteLeft() + "px");
		this.setHeight(height + shadowSize + this.getAbsoluteTop() + "px");
		
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
	
	public void addDefaultClass() {
		ClassArtifact defaultclass = new ClassArtifact("Class 1");
			defaultclass.setLocation(width/2, height/2);
		gc.add(defaultclass);
	}
	

}
