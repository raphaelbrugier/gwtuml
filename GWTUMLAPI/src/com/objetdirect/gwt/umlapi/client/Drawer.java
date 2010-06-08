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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.helpers.HotKeyManager;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager.Theme;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;

/**
 * This is the main entry class to add a drawer.
 * Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class Drawer extends FocusPanel implements RequiresResize {

	private int width;
	private int height;
	
	private UMLCanvas umlCanvas;
	private DecoratorCanvas decoratorPanel;
	
	public Drawer() {
		this(new UMLCanvas(Type.CLASS));
	}
	
	public Drawer(UMLCanvas umlCanvas) {
		setupGfxPlatform();
		addHandlers();
		decoratorPanel = new DecoratorCanvas(umlCanvas);
		setWidget(decoratorPanel);
		this.umlCanvas = umlCanvas;
	}
	
	private void setupGfxPlatform() {
		HotKeyManager.forceStaticInit();
		HotKeyManager.setInputEnabled(false);
		ThemeManager.setCurrentTheme((Theme.getThemeFromIndex(OptionsManager.get("Theme"))));
		GfxManager.setPlatform(OptionsManager.get("GraphicEngine"));
		GeometryManager.setPlatform(OptionsManager.get("GeometryStyle"));
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

	/**
	 * @return the umlCanvas
	 */
	public UMLCanvas getUmlCanvas() {
		return umlCanvas;
	}

	/**
	 * @return A list of UmlClass currently displayed in the modeler
	 */
	public List<UMLClass> getUmlClasses() {
		ArrayList<UMLClass> umlClasses = new ArrayList<UMLClass>();
		for (final UMLArtifact umlArtifact : umlCanvas.getArtifactById().values()) {
			if (umlArtifact instanceof ClassArtifact) {
				ClassArtifact classArtifact  = (ClassArtifact)umlArtifact;
				umlClasses.add(classArtifact.toUMLComponent());
			} 
		}
		return umlClasses;
	}
	
	
	/**
	 * @return A list of the relations between the umlComponents currently displayed. 
	 */
	public List<UMLRelation> getUmlRelations() {
		ArrayList<UMLRelation> umlRelations = new  ArrayList<UMLRelation>();
		for (final UMLArtifact umlArtifact : umlCanvas.getArtifactById().values()) {
			if (umlArtifact instanceof ClassRelationLinkArtifact) {
				ClassRelationLinkArtifact relationLinkArtifact = (ClassRelationLinkArtifact)umlArtifact;
				umlRelations.add(relationLinkArtifact.toUMLComponent());
			}
		}
		return umlRelations;
	}
}
