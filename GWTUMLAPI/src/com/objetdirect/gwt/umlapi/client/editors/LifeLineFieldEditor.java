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

import com.objetdirect.gwt.umlapi.client.artifacts.LifeLineArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLifeLine;

/**
 * This field editor is a specialized editor for LifeLine editing
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class LifeLineFieldEditor extends FieldEditor {
	/**
	 * Constructor of the {@link LifeLineFieldEditor}
	 * 
	 * @param canvas
	 *            The canvas on which is the artifact
	 * @param artifact
	 *            The artifact being edited
	 */
	public LifeLineFieldEditor(final UMLCanvas canvas, final LifeLineArtifact artifact) {
		super(canvas, artifact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next()
	 */
	@Override
	protected void next() {
		// No next part to edit
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#updateUMLArtifact(java.lang.String)
	 */
	@Override
	protected boolean updateUMLArtifact(final String newContent) {
		final List<String> newNameInstance = UMLLifeLine.parseName(newContent);
		if (newNameInstance.get(1).equals("")) {
			((LifeLineArtifact) this.artifact).setName("LifeLine");
		} else {
			((LifeLineArtifact) this.artifact).setName(newNameInstance.get(1));
		}
		((LifeLineArtifact) this.artifact).setInstance(newNameInstance.get(0));

		this.artifact.rebuildGfxObject();
		return false;
	}
}
