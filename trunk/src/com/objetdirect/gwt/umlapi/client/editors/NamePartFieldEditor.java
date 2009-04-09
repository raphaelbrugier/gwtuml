/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This field editor is a specialized editor for class name and stereotype edition
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class NamePartFieldEditor extends FieldEditor {

    private final boolean isTheStereotype;
    
    /**
     * Constructor of the {@link NamePartFieldEditor} 
     *     
     * @param canvas The canvas on which is the artifact
     * @param artifact The artifact being edited
     * @param isTheStereotype This boolean determine if the edition is on the stereotype (True) or the class name (False)
     */
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
	if (this.isTheStereotype) {
	    if (newContent.equals("")) {
		((ClassPartNameArtifact) this.artifact).setStereotype(null);
	    } else {
		if (!(newContent.startsWith("<<") && newContent.endsWith(">>"))) {
		    ((ClassPartNameArtifact) this.artifact).setStereotype("<<"
			    + newContent + ">>");
		} else {
		    ((ClassPartNameArtifact) this.artifact)
			    .setStereotype(newContent);
		}
	    }
	} else {
	    if (newContent.equals("")) {
		((ClassPartNameArtifact) this.artifact).setClassName("Class");
	    } else {
		((ClassPartNameArtifact) this.artifact).setClassName(newContent);
	    }
	}
	((ClassPartNameArtifact) this.artifact).getClassArtifact()
		.rebuildGfxObject();
	return false;
    }
}
