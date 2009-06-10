/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

/**
 * This field editor is a specialized editor for class name and stereotype edition
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class ClassPartNameFieldEditor extends FieldEditor {

    private final boolean isTheStereotype;

    /**
     * Constructor of 
     *
     * @param canvas
     * @param classPartNameArtifact
     * @param isTheStereotype
     */
    public ClassPartNameFieldEditor(UMLCanvas canvas,
	    ClassPartNameArtifact classPartNameArtifact,
	    boolean isTheStereotype) {
	super(canvas, classPartNameArtifact);
	this.isTheStereotype = isTheStereotype;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next() No next
     * part to edit
     */
    @Override
    protected void next() {
	// No next part to edit
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (this.isTheStereotype) {
	    String newStereotype = UMLClass.parseNameOrStereotype(newContent.replaceAll("[«»]", ""));
	    if (newStereotype.equals("")) {
		((ClassPartNameArtifact) this.artifact).setStereotype("");
	    } else {
		((ClassPartNameArtifact) this.artifact).setStereotype("«"
			+ newStereotype + "»");
	    }
	} else {
	    String newName = UMLClass.parseNameOrStereotype(newContent);
	    if (newName.equals("")) {
		((ClassPartNameArtifact) this.artifact).setClassName("Class");
	    } else {
		((ClassPartNameArtifact) this.artifact).setClassName(newName);
	    }
	}
	((ClassPartNameArtifact) this.artifact).getNodeArtifact()
	.rebuildGfxObject();
	return false;
    }
}
