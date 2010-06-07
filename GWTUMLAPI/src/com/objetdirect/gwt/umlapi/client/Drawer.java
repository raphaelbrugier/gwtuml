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
package com.objetdirect.gwt.umlapi.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.helpers.Keyboard;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * This is the main entry class to add a drawer.
 * Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class Drawer extends FocusPanel implements RequiresResize {

	private int width;
	private int height;
	
	private UmlCanvasImpl umlCanvas;
	private DecoratorCanvas decoratorPanel;
	
	public Drawer() {
		addHandlers();
		umlCanvas = new UmlCanvasImpl();
		decoratorPanel = new DecoratorCanvas(umlCanvas);
		setWidget(decoratorPanel);
	}
	
	private void addHandlers() {
//		HandlerRegistration registration = this.addKeyPressHandler(new KeyPressHandler() {
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
////				Log.debug("KeyPress on Drawer's focusPanel");
//				NativeEvent nativeEvent = event.getNativeEvent();
//				Keyboard.push(nativeEvent.getKeyCode(), nativeEvent.getCtrlKey(), nativeEvent.getAltKey(), nativeEvent.getShiftKey(), nativeEvent.getMetaKey());
//			}
//		});
	}

	public DiagramArtifact getDiagram() {
		return null;
	}
	
	
	@Deprecated
	public UMLCanvas getUMLCanvas() {
		return null;
	}

	@Override
	public void onResize() {
		int parentHeight = getParent().getOffsetHeight();
		int parentWidth = getParent().getOffsetWidth();
		
		int width = parentWidth - 10;
		int height = parentHeight - 10;
		
		this.width = width;
		this.height = height;
		this.setPixelSize(this.width, this.height);
		decoratorPanel.reSize(width, height);
	}
}
