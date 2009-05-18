/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ObjectPartAttributesEditor extends FieldEditor {

    UMLObjectAttribute attributeToChange;

    /**
     * @param canvas
     * @param objectPartAttributesArtifact
     * @param attributeToChange
     */
    public ObjectPartAttributesEditor(final UMLCanvas canvas,
	    final ObjectPartAttributesArtifact objectPartAttributesArtifact,
	    final UMLObjectAttribute attributeToChange) {
	super(canvas, objectPartAttributesArtifact);
	this.attributeToChange = attributeToChange;
    }

    @Override
    protected void next() {
	((NodePartArtifact) this.artifact).edit();
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (newContent.equals("")) {
	    ((ObjectPartAttributesArtifact) this.artifact).remove(this.attributeToChange);
	    ((ObjectPartAttributesArtifact) this.artifact).getNodeArtifact()
	    .rebuildGfxObject();
	    return false;
	}

	UMLObjectAttribute newAttribute = UMLObjectAttribute.parseAttribute(newContent);
	this.attributeToChange.setVisibility(newAttribute.getVisibility());
	this.attributeToChange.setName(newAttribute.getName());
	this.attributeToChange.setType(newAttribute.getType());
	this.attributeToChange.setInstance(newAttribute.getInstance());

	((ObjectPartAttributesArtifact) this.artifact).getNodeArtifact()
	.rebuildGfxObject();

	return true;
    }
}
