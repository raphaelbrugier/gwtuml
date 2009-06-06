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

import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This field editor is a specialized editor for {@link UMLClassMethod} editing
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartMethodsEditor extends FieldEditor {

    UMLClassMethod methodToChange;

    /**
     * Constructor of the {@link ClassPartMethodsEditor} 
     *     
     * @param canvas The canvas on which is the artifact
     * @param artifact The artifact being edited
     * @param methodToChange The {@link UMLClassMethod} on which edition has been requested
     */
    public ClassPartMethodsEditor(final UMLCanvas canvas,
	    final ClassPartMethodsArtifact artifact, final UMLClassMethod methodToChange) {
	super(canvas, artifact);
	this.methodToChange = methodToChange;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next()
     */
    @Override
    protected void next() {
	((NodePartArtifact) this.artifact).edit();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#updateUMLArtifact(java.lang.String)
     */
    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (newContent.equals("")) {
	    ((ClassPartMethodsArtifact) this.artifact).remove(this.methodToChange);
	    ((ClassPartMethodsArtifact) this.artifact).getNodeArtifact()
	    .rebuildGfxObject();
	    return false;
	}
	UMLClassMethod newMethod = UMLClassMethod.parseMethod(newContent);
	if(newMethod != null) {
	    this.methodToChange.setVisibility(newMethod.getVisibility());
	    this.methodToChange.setName(newMethod.getName());
	    this.methodToChange.setReturnType(newMethod.getReturnType());
	    this.methodToChange.setParameters(newMethod.getParameters());
	    ((ClassPartMethodsArtifact) this.artifact).getNodeArtifact()
	    .rebuildGfxObject();
	    return true;
	}
	((ClassPartMethodsArtifact) this.artifact).remove(this.methodToChange);
	((ClassPartMethodsArtifact) this.artifact).getNodeArtifact()
	.rebuildGfxObject();
	return false;

    }
}
