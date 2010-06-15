/*
 * This file is part of the Gwt-Uml project and was written by Raphaël Brugier <raphael dot brugier at gmail dot com > for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct
 * 
 * Gwt-Uml is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Gwt-Uml is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Gwt-Generator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umldrawer.client;

import static com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType.CLASS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.Drawer;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.DiagramType;

/**
 * Contains the drawer and the buttons to export the current displayed drawer.
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class DrawerContainer extends ResizeComposite {

	private static DrawerContainerUiBinder uiBinder = GWT
			.create(DrawerContainerUiBinder.class);

	interface DrawerContainerUiBinder extends UiBinder<Widget, DrawerContainer> {
	}

	@UiField
	Button exportToUrl;

	@UiField
	Button clearUrl;
	
	@UiField
	Button exportToSvg;
	
	@UiField
	LayoutPanel drawerContainer;

	
	private UMLCanvas umlCanvas;
	
	private Drawer drawer;

	/**
	 *  Create a default canvas for a class Diagram.
	 */
	public DrawerContainer() {
		initWidget(uiBinder.createAndBindUi(this));
		OptionsManager.set("DiagramType", CLASS.getIndex());
		umlCanvas = new UMLCanvas(CLASS);
		drawer = new Drawer(umlCanvas);
		
		drawerContainer.add(drawer);
	}
	
	
	public DrawerContainer(UrlParser urlParser) {
		initWidget(uiBinder.createAndBindUi(this));
		
		OptionsManager.setAllFromURL(urlParser.getOptionsList());
		int diagramIndex = OptionsManager.get("DiagramType");
		DiagramType diagramType = DiagramType.fromIndex(diagramIndex);
		
		umlCanvas = new UMLCanvas(diagramType);
		drawer = new Drawer(umlCanvas);
		umlCanvas.getArtifactById().clear();
		if (urlParser.getDiagram64() != null) {
			umlCanvas.fromURL(urlParser.getDiagram64(), false);
		}
		
		drawerContainer.add(drawer);
	}

	/**
	 * @return the umlCanvas
	 */
	public UMLCanvas getUmlCanvas() {
		return umlCanvas;
	}

	/**
	 * @return the drawer
	 */
	public Drawer getDrawer() {
		return drawer;
	}
	
	@Override
	public void onResize() {
		super.onResize();
		
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
	}
	
	@UiHandler("exportToUrl")
	void clickExportToUrl(ClickEvent event) {
		HistoryManager.upgradeDiagramURL(umlCanvas.toUrl());
	}
	
	@UiHandler("clearUrl")
	void clickClearUrl(ClickEvent event) {
		HistoryManager.upgradeDiagramURL("");
	}
	
	@UiHandler("exportToSvg")
	void clickExportToSvg(ClickEvent event) {
		String svg = "<?xml version='1.0' standalone='no'?><!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'>";
//		Session.getActiveCanvas().clearArrows(); //TODO
		svg += DOM.getInnerHTML((Element) umlCanvas.getContainer().getElement().getFirstChildElement());
		Window.open("data:image/xml+svg;charset=utf-8," + svg, "SVG export", "top");
//		Session.getActiveCanvas().makeArrows(); //TODO
	}
}
