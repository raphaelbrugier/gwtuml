/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
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
