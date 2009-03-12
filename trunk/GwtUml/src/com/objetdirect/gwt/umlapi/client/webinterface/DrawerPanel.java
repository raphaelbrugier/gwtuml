package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas.Link;

public class DrawerPanel extends AbsolutePanel {

	public DrawerPanel(int w, int h) {
		Log.info("Creating drawer");

		this.setWidth("100%");
		this.setHeight("100%");
		
		final UMLCanvas gc = new UMLCanvas(w, h);
		gc.setStylePrimaryName("canvas");
		this.add(gc);
		
		w += 2; //Border Size
		h += 2; //Border Size		
		int shadowSize = 8;
		

		
		SimplePanel bottomShadow = new SimplePanel();
		bottomShadow.setPixelSize(w - shadowSize, shadowSize);
		bottomShadow.setStylePrimaryName("bottomShadow");
		this.add(bottomShadow, shadowSize, h);
		
		
		SimplePanel rightShadow = new SimplePanel();
		rightShadow.setPixelSize(shadowSize, h - shadowSize);
		rightShadow.setStylePrimaryName("rightShadow");
		this.add(rightShadow, w, shadowSize);
		
		SimplePanel bottomRightCornerShadow = new SimplePanel();
		bottomRightCornerShadow.setPixelSize(shadowSize, shadowSize);
		bottomRightCornerShadow.setStylePrimaryName("bottomRightCornerShadow");
		this.add(bottomRightCornerShadow, w, h);
		
		SimplePanel topRightCornerShadow = new SimplePanel();
		topRightCornerShadow.setPixelSize(shadowSize, shadowSize);
		topRightCornerShadow.setStylePrimaryName("topRightCornerShadow");
		this.add(topRightCornerShadow, w, 0);
		
		SimplePanel bottomLeftCornerShadow = new SimplePanel();
		bottomLeftCornerShadow.setPixelSize(shadowSize, shadowSize);
		bottomLeftCornerShadow.setStylePrimaryName("bottomLeftCornerShadow");
		this.add(bottomLeftCornerShadow, 0, h);

		
		
		// TODO : under chrome redraw doesn't work if the canvas is at a
		// different point than (0,0) tatami ? dojo ? chrome ?
		// example : this.setSpacing(50);

		//HotKeyManager.setActiveCanvas(gc);

		ClassArtifact defaultclass = new ClassArtifact("Class 1");
		gc.add(defaultclass);
		defaultclass.setLocation(w/2, h/2);

		Log.info("Init end");

	}

}
