package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas.Link;

public class DrawerPanel extends AbsolutePanel {
	
	private final UMLCanvas gc;
	private int height;
	private int width;
	
	public DrawerPanel(int width, int height) {
		Log.info("Creating drawer");
		this.width = width;
		this.height = height;
		
		
		
		gc = new UMLCanvas(width, height);
		gc.setStylePrimaryName("canvas");
		this.add(gc);
		
		width += 2; //Border Size
		height += 2; //Border Size		
		int shadowSize = 8;
		
		this.setWidth(width + shadowSize + this.getAbsoluteLeft() + "px");
		this.setHeight(height + shadowSize + this.getAbsoluteTop() + "px");
		
		SimplePanel bottomShadow = new SimplePanel();
		bottomShadow.setPixelSize(width - shadowSize, shadowSize);
		bottomShadow.setStylePrimaryName("bottomShadow");
		this.add(bottomShadow, shadowSize, height);
		
		
		SimplePanel rightShadow = new SimplePanel();
		rightShadow.setPixelSize(shadowSize, height - shadowSize);
		rightShadow.setStylePrimaryName("rightShadow");
		this.add(rightShadow, width, shadowSize);
		
		SimplePanel bottomRightCornerShadow = new SimplePanel();
		bottomRightCornerShadow.setPixelSize(shadowSize, shadowSize);
		bottomRightCornerShadow.setStylePrimaryName("bottomRightCornerShadow");
		this.add(bottomRightCornerShadow, width, height);
		
		SimplePanel topRightCornerShadow = new SimplePanel();
		topRightCornerShadow.setPixelSize(shadowSize, shadowSize);
		topRightCornerShadow.setStylePrimaryName("topRightCornerShadow");
		this.add(topRightCornerShadow, width, 0);
		
		SimplePanel bottomLeftCornerShadow = new SimplePanel();
		bottomLeftCornerShadow.setPixelSize(shadowSize, shadowSize);
		bottomLeftCornerShadow.setStylePrimaryName("bottomLeftCornerShadow");
		this.add(bottomLeftCornerShadow, 0, height);
		
		
		// TODO : under chrome redraw doesn't work if the canvas is at a
		// different point than (0,0) tatami ? dojo ? chrome ?
		// example : this.setSpacing(50);

		HotKeyManager.setActiveCanvas(gc);
		UMLDrawerHelper.disableBrowserEvents();
		Log.info("Init end");

	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public UMLCanvas getGc() {
		return gc;
	}
	
	public void addDefaultClass() {
		ClassArtifact defaultclass = new ClassArtifact("Class 1");
			defaultclass.setLocation(width/2, height/2);
		gc.add(defaultclass);
	}
	

}
