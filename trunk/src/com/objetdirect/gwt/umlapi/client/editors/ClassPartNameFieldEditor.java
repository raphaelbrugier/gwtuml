/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This field editor is a specialized editor for class name and stereotype edition
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class ClassPartNameFieldEditor extends FieldEditor {

    private final boolean isTheStereotype;
    
    /**
     * Constructor of the {@link ClassPartNameFieldEditor} 
     *     
     * @param canvas The canvas on which is the artifact
     * @param objectPartNameArtifact The artifact being edited
     * @param isTheStereotype This boolean determine if the edition is on the stereotype (True) or the class name (False)
     */
    public ClassPartNameFieldEditor(final UMLCanvas canvas,
	    final ObjectPartNameArtifact objectPartNameArtifact, final boolean isTheStereotype) {
	super(canvas, objectPartNameArtifact);
	this.isTheStereotype = isTheStereotype;
    }

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
	    if (newContent.equals("")) {
		((ClassPartNameArtifact) this.artifact).setStereotype(null);
	    } else {
		if (!(newContent.startsWith("«") && newContent.endsWith("»"))) {
		    ((ClassPartNameArtifact) this.artifact).setStereotype("«"
			    + newContent + "»");
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
	((ClassPartNameArtifact) this.artifact).getNodeArtifact()
		.rebuildGfxObject();
	return false;
    }
}
