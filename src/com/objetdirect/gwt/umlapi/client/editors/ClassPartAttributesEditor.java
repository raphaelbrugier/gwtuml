/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartAttributesEditor extends FieldEditor {

    UMLClassAttribute attributeToChange;

    /**
     * Constructor of ClassPartAttributesEditor
     * 
     * @param canvas
     * @param objectPartAttributesArtifact
     * @param attributeToChange
     */
    public ClassPartAttributesEditor(final UMLCanvas canvas,
	    final ObjectPartAttributesArtifact objectPartAttributesArtifact,
	    final UMLClassAttribute attributeToChange) {
	super(canvas, objectPartAttributesArtifact);
	this.attributeToChange = attributeToChange;
    }

    /**
     * Constructor of ClassPartAttributesEditor
     *
     * @param canvas
     * @param classPartAttributesArtifact
     * @param attributeToChange
     */
    public ClassPartAttributesEditor(UMLCanvas canvas,
	    ClassPartAttributesArtifact classPartAttributesArtifact,
	    UMLClassAttribute attributeToChange) {
	super(canvas, classPartAttributesArtifact);
	this.attributeToChange = attributeToChange;
    }

    @Override
    protected void next() {
	((NodePartArtifact) this.artifact).edit();
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (newContent.equals("")) {
	    ((ClassPartAttributesArtifact) this.artifact).remove(this.attributeToChange);
	    ((ClassPartAttributesArtifact) this.artifact).getNodeArtifact()
		    .rebuildGfxObject();
	    return false;
	}
	UMLClassAttribute newAttribute = UMLClassAttribute.parseAttribute(newContent);

	this.attributeToChange.setVisibility(newAttribute.getVisibility());
	this.attributeToChange.setName(newAttribute.getName());
	this.attributeToChange.setType(newAttribute.getType());
	
	    ((ClassPartAttributesArtifact) this.artifact).getNodeArtifact()
	    .rebuildGfxObject();
	return true;
    }
}
