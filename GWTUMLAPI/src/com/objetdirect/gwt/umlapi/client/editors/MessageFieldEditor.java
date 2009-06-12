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

import com.objetdirect.gwt.umlapi.client.artifacts.MessageLinkArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLMessage;

/**
 * This field editor is a specialized editor for {@link UMLMessage} editing
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class MessageFieldEditor extends FieldEditor {
	/**
	 * Constructor of the {@link MessageFieldEditor}
	 * 
	 * @param canvas
	 *            The canvas on which is the artifact
	 * @param messageLinkArtifact
	 *            The artifact being edited
	 */
	public MessageFieldEditor(final UMLCanvas canvas, final MessageLinkArtifact messageLinkArtifact) {
		super(canvas, messageLinkArtifact);
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
		if (newContent.trim().equals("")) {
			this.canvas.remove(this.artifact);
			return false;
		}
		((MessageLinkArtifact) this.artifact).setName(newContent);
		this.artifact.rebuildGfxObject();
		return false;
	}
}
