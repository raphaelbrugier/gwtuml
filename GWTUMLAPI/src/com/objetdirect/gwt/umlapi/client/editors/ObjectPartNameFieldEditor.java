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

import java.util.List;

import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;

/**
 * This field editor is a specialized editor for object name and stereotype edition
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class ObjectPartNameFieldEditor extends FieldEditor {

	private final boolean	isTheStereotype;

	/**
	 * Constructor of the {@link ObjectPartNameFieldEditor}
	 * 
	 * @param canvas
	 *            The canvas on which is the artifact
	 * @param objectPartNameArtifact
	 *            The artifact being edited
	 * @param isTheStereotype
	 *            This boolean determine if the edition is on the stereotype (True) or the object name (False)
	 */
	public ObjectPartNameFieldEditor(final UMLCanvas canvas, final ObjectPartNameArtifact objectPartNameArtifact, final boolean isTheStereotype) {
		super(canvas, objectPartNameArtifact);
		this.isTheStereotype = isTheStereotype;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next() No next part to edit
	 */
	@Override
	protected void next() {
		// No next part to edit
	}

	@Override
	protected boolean updateUMLArtifact(final String newContent) {
		final String newContentWithoutSpaces = newContent.replaceAll(" ", "_");
		if (this.isTheStereotype) {
			final String newStereotype = UMLObject.parseStereotype(newContentWithoutSpaces.replaceAll("[«»]", ""));
			if (newStereotype.equals("")) {
				((ObjectPartNameArtifact) this.artifact).setStereotype("");
			} else {
				((ObjectPartNameArtifact) this.artifact).setStereotype("«" + newStereotype + "»");
			}
		} else {
			final List<String> newNameInstance = UMLObject.parseName(newContentWithoutSpaces);
			if (newNameInstance.get(1).equals("")) {
				((ObjectPartNameArtifact) this.artifact).setObjectName("Object");
			} else {
				((ObjectPartNameArtifact) this.artifact).setObjectName(newNameInstance.get(1));
			}
			((ObjectPartNameArtifact) this.artifact).setInstanceName(newNameInstance.get(0));
		}

		((ObjectPartNameArtifact) this.artifact).getNodeArtifact().rebuildGfxObject();
		return false;
	}
}
