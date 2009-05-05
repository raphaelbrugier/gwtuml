/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This field editor is a specialized editor for object name and stereotype edition
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class ObjectPartNameFieldEditor extends FieldEditor {

    private final boolean isTheStereotype;
    
    /**
     * Constructor of the {@link ObjectPartNameFieldEditor} 
     *     
     * @param canvas The canvas on which is the artifact
     * @param objectPartNameArtifact The artifact being edited
     * @param isTheStereotype This boolean determine if the edition is on the stereotype (True) or the object name (False)
     */
    public ObjectPartNameFieldEditor(final UMLCanvas canvas,
	    final ObjectPartNameArtifact objectPartNameArtifact, final boolean isTheStereotype) {
	super(canvas, objectPartNameArtifact);
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
		((ObjectPartNameArtifact) this.artifact).setStereotype(null);
	    } else {
		if (!(newContent.startsWith("«") && newContent.endsWith("»"))) {
		    ((ObjectPartNameArtifact) this.artifact).setStereotype("«"
			    + newContent + "»");
		} else {
		    ((ObjectPartNameArtifact) this.artifact)
			    .setStereotype(newContent);
		}
	    }
	} else {
	    if (newContent.equals("")) {
		((ObjectPartNameArtifact) this.artifact).setObjectName("Object");
	    } else {
		((ObjectPartNameArtifact) this.artifact).setObjectName(newContent);
	    }
	}
	((ObjectPartNameArtifact) this.artifact).getNodeArtifact()
		.rebuildGfxObject();
	return false;
    }
}
