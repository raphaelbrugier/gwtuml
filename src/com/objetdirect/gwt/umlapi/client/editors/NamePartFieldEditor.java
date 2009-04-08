/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassNameArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author fmounier
 * 
 */
public class NamePartFieldEditor extends FieldEditor {

    private final boolean isTheStereotype;

    public NamePartFieldEditor(final UMLCanvas canvas,
	    final ClassNameArtifact artifact, final boolean isTheStereotype) {
	super(canvas, artifact);
	this.isTheStereotype = isTheStereotype;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next()
     *  No next part to edit
     */
    @Override
    protected void next() {
	// No next part to edit 
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (this.isTheStereotype) {
	    if (newContent.equals("")) {
		((ClassNameArtifact) this.artifact).setStereotype(null);
	    } else {
		if (!(newContent.startsWith("<<") && newContent.endsWith(">>"))) {
		    ((ClassNameArtifact) this.artifact).setStereotype("<<"
			    + newContent + ">>");
		} else {
		    ((ClassNameArtifact) this.artifact).setStereotype(newContent);
		}
	    }
	} else {
	    if (newContent.equals("")) {
		((ClassNameArtifact) this.artifact).setClassName("Class");
	    } else {
		((ClassNameArtifact) this.artifact).setClassName(newContent);
	    }
	}
	((ClassNameArtifact) this.artifact).getClassArtifact().rebuildGfxObject();
	return false;
    }
}
