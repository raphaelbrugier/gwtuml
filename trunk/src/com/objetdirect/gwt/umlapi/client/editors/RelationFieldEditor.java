package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This field editor is a specialized editor for {@link UMLRelation} editing
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class RelationFieldEditor extends FieldEditor {

    RelationLinkArtifactPart relationshipPart;
    
    /**
     * Constructor of the {@link RelationFieldEditor} 
     *     
     * @param canvas The canvas on which is the artifact
     * @param artifact The artifact being edited
     * @param relationshipPart The {@link RelationLinkArtifactPart} of  {@link RelationLinkArtifact} on which edition has been requested
     */
    public RelationFieldEditor(final UMLCanvas canvas,
	    final RelationLinkArtifact artifact,
	    final RelationLinkArtifactPart relationshipPart) {
	super(canvas, artifact);
	this.relationshipPart = relationshipPart;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next()
     */
    @Override
    protected void next() {
	// No next part to edit
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#updateUMLArtifact(java.lang.String)
     */
    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	((RelationLinkArtifact) this.artifact).setPartContent(this.relationshipPart,
		newContent);
	this.artifact.rebuildGfxObject();
	return false;
    }
}
