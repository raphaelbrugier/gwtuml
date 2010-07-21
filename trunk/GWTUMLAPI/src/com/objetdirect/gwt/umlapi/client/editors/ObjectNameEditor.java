/*
 * This file is part of the Gwt-Generator project and was written by Raphaël Brugier <raphael dot brugier at gmail dot com > for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct
 * 
 * Gwt-Generator is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Gwt-Generator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Gwt-Generator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.editors;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.object.ObjectPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.umlCanvas.ObjectDiagram;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

/**
 * Special editor for an object's name.
 * 
 * In the object diagram, an object's names is compound of : 1/ the instance name 2/ the class instantiated
 * 
 * The class instantiated should be chosen in a list of classes that can be instantiated in the current object diagram.
 * 
 * @author Raphaël Brugier <raphael dot brugier at gmail dot com>
 */
public class ObjectNameEditor extends Composite {

	private static ObjectNameEditorUiBinder uiBinder = GWT.create(ObjectNameEditorUiBinder.class);

	interface ObjectNameEditorUiBinder extends UiBinder<Widget, ObjectNameEditor> {
	}

	private final UMLCanvas canvas;
	private final ObjectPartNameArtifact artifact;
	private final Map<Integer, UMLClass> umlClassById;

	@UiField
	TextBox nameField;

	@UiField
	ListBox classesList;

	@UiField
	Anchor okButton;

	public ObjectNameEditor(UMLCanvas canvas, ObjectPartNameArtifact artifact) {
		initWidget(uiBinder.createAndBindUi(this));
		this.canvas = canvas;
		this.artifact = artifact;
		umlClassById = new HashMap<Integer, UMLClass>();

		nameField.setText(artifact.getUmlObject().getInstanceName());
		setClassesInList();
		canvas.setHotKeysEnabled(false);
	}

	private void setClassesInList() {
		ObjectDiagram objectDiagram = (ObjectDiagram) canvas;

		for (int i = 0; i < objectDiagram.getClasses().size(); i++) {
			UMLClass clazz = objectDiagram.getClasses().get(i);
			umlClassById.put(i, clazz);
			classesList.addItem(clazz.getName(), Integer.toString(i));
		}
	}

	public void startEdition(final int x, final int y) {
		canvas.getContainer().add(this, x + canvas.getCanvasOffset().getX(), y + canvas.getCanvasOffset().getY());
		canvas.setFieldEditor(this);
	}

	@UiHandler("okButton")
	void onClickOnOk(@SuppressWarnings("unused") ClickEvent clickEvent) {
		String instanceName = nameField.getText();
		int id = Integer.parseInt(classesList.getValue(classesList.getSelectedIndex()));

		UMLClass clazz = umlClassById.get(id);
		artifact.getUmlObject().setInstanceName(instanceName);
		artifact.getUmlObject().setInstantiatedClass(clazz);
		artifact.getNodeArtifact().rebuildGfxObject();

		canvas.getContainer().remove(this);
		canvas.setHotKeysEnabled(true);
	}

}
