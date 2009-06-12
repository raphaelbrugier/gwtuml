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

import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ObjectPartAttributesFieldEditor extends FieldEditor {

	UMLObjectAttribute	attributeToChange;

	/**
	 * @param canvas
	 * @param objectPartAttributesArtifact
	 * @param attributeToChange
	 */
	public ObjectPartAttributesFieldEditor(final UMLCanvas canvas, final ObjectPartAttributesArtifact objectPartAttributesArtifact,
			final UMLObjectAttribute attributeToChange) {
		super(canvas, objectPartAttributesArtifact);
		this.attributeToChange = attributeToChange;
	}

	@Override
	protected void next() {
		((NodePartArtifact) this.artifact).edit();
	}

	@Override
	protected boolean updateUMLArtifact(final String newContent) {
		if (newContent.trim().equals("")) {
			((ObjectPartAttributesArtifact) this.artifact).remove(this.attributeToChange);
			((ObjectPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
			return false;
		}

		final UMLObjectAttribute newAttribute = UMLObjectAttribute.parseAttribute(newContent);
		if ((newAttribute.getName() + newAttribute.getType()).equals("")) {
			((ObjectPartAttributesArtifact) this.artifact).remove(this.attributeToChange);
			((ObjectPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
			return false;
		}
		this.attributeToChange.setVisibility(newAttribute.getVisibility());
		this.attributeToChange.setName(newAttribute.getName());
		this.attributeToChange.setType(newAttribute.getType());
		this.attributeToChange.setInstance(newAttribute.getInstance());

		((ObjectPartAttributesArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();

		return true;
	}
}
