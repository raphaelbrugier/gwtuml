/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class NamePartFieldEditor extends FieldEditor {

    private final boolean isTheStereotype;

    public NamePartFieldEditor(final UMLCanvas canvas,
	    final ClassPartNameArtifact artifact, final boolean isTheStereotype) {
	super(canvas, artifact);
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
	if (isTheStereotype) {
	    if (newContent.equals("")) {
		((ClassPartNameArtifact) artifact).setStereotype(null);
	    } else {
		if (!(newContent.startsWith("<<") && newContent.endsWith(">>"))) {
		    ((ClassPartNameArtifact) artifact).setStereotype("<<"
			    + newContent + ">>");
		} else {
		    ((ClassPartNameArtifact) artifact)
			    .setStereotype(newContent);
		}
	    }
	} else {
	    if (newContent.equals("")) {
		((ClassPartNameArtifact) artifact).setClassName("Class");
	    } else {
		((ClassPartNameArtifact) artifact).setClassName(newContent);
	    }
	}
	((ClassPartNameArtifact) artifact).getClassArtifact()
		.rebuildGfxObject();
	return false;
    }
}
