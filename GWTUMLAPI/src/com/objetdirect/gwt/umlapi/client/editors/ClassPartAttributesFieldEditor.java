/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.clazz.ClassPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartAttributesFieldEditor extends FieldEditor {

	UMLClassAttribute attributeToChange;

	/**
	 * Constructor of ClassPartAttributesEditor
	 * 
	 * @param canvas
	 * @param classPartAttributesArtifact
	 * @param attributeToChange
	 */
	public ClassPartAttributesFieldEditor(final UMLCanvas canvas, final ClassPartAttributesArtifact classPartAttributesArtifact,
			final UMLClassAttribute attributeToChange) {
		super(canvas, classPartAttributesArtifact);
		this.attributeToChange = attributeToChange;
	}

	@Override
	protected void next() {
		((NodePartArtifact) artifact).edit();
	}

	@Override
	protected boolean updateUMLArtifact(final String newContent) {
		if (newContent.trim().equals("")) {
			((ClassPartAttributesArtifact) artifact).remove(attributeToChange);
			((ClassPartAttributesArtifact) artifact).getNodeArtifact().rebuildGfxObject();
			return false;
		}
		final UMLClassAttribute newAttribute = UMLClassAttribute.parseAttribute(newContent);
		if ((newAttribute.getName() + newAttribute.getType()).equals("")) {
			((ClassPartAttributesArtifact) artifact).remove(attributeToChange);
			((ClassPartAttributesArtifact) artifact).getNodeArtifact().rebuildGfxObject();
			return false;
		}
		attributeToChange.setVisibility(newAttribute.getVisibility());
		attributeToChange.setName(newAttribute.getName());
		attributeToChange.setType(newAttribute.getType());

		((ClassPartAttributesArtifact) artifact).getNodeArtifact().rebuildGfxObject();
		return true;
	}
}
