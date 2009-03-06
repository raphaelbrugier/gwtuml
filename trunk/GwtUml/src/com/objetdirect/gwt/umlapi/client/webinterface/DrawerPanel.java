package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas.Link;

public class DrawerPanel extends HorizontalPanel {

	public DrawerPanel(int w, int h) {
		Log.info("Creating drawer");

		this.setWidth("100%");

		final UMLCanvas gc = new UMLCanvas(w, h);
		gc.setStylePrimaryName("canvas");
		this.add(gc);
		
		// TODO : under chrome redraw doesn't work if the canvas is at a
		// different point than (0,0) tatami ? dojo ? chrome ?
		// example : this.setSpacing(50);

		HotKeyManager.setActiveCanvas(gc);

		ClassArtifact defaultclass = new ClassArtifact("Class 1");
		gc.add(defaultclass);
		defaultclass.setLocation(w/2, h/2);
		

		

		Log.info("Init class end\nBegin sidebar");
/*
		VerticalPanel sidePanel = new VerticalPanel();
		sidePanel.setSpacing(5);

		Button addClass = new Button("Add Class (C)");
		Button addNote = new Button("Add Note (N)");
		Button addSimpleClassDependency = new Button(
				"Add Simple Class Dependency (D)");
		Button addImplementationClassDependency = new Button(
				"Add Implementation Class Dependency (I)");
		Button addExtensionClassDependency = new Button(
				"Add Extension Class Dependency (E)");
		Button addRelationship = new Button("Add Relationship (R)");
		Button delete = new Button("Delete (S)");

		addClass.setStylePrimaryName("drawer-button");
		addNote.setStylePrimaryName("drawer-button");
		addSimpleClassDependency.setStylePrimaryName("drawer-button");
		addImplementationClassDependency.setStylePrimaryName("drawer-button");
		addExtensionClassDependency.setStylePrimaryName("drawer-button");
		addRelationship.setStylePrimaryName("drawer-button");
		delete.setStylePrimaryName("drawer-button");

		addClass.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewClass();
			}
		});

		addNote.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewNote();
			}
		});

		addSimpleClassDependency.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewLink(Link.SIMPLE);
			}
		});
		addImplementationClassDependency.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewLink(Link.IMPLEMENTATION);
			}
		});
		addExtensionClassDependency.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewLink(Link.EXTENSION);
			}
		});
		addRelationship.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.addNewLink(Link.RELATIONSHIP);
			}
		});
		delete.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				gc.setDeleteMode();
			}
		});
		sidePanel.add(addClass);
		sidePanel.add(addNote);
		sidePanel.add(addSimpleClassDependency);
		sidePanel.add(addImplementationClassDependency);
		sidePanel.add(addExtensionClassDependency);
		sidePanel.add(addRelationship);
		sidePanel.add(delete);

		
		this.add(sidePanel);
*/
		Log.info("Init end");

	}

}
