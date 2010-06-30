/*
 * This file is part of the GWTUML project and was written by Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com) for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright Â© 2010 Objet Direct Contact: gwtuml@googlegroups.com
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

import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class SimpleFieldEditor extends FieldEditor {

	private final EditorPart editable;

	public SimpleFieldEditor(final UMLCanvas canvas, final RelationLinkArtifact artifact, EditorPart editable) {
		super(canvas, artifact);
		this.editable = editable;
	}

	@Override
	protected void next() {
		// No next part to edit
	}

	@Override
	protected boolean updateUMLArtifact(String newContent) {
		editable.setText(newContent);
		artifact.rebuildGfxObject();
		return false;
	}

}
