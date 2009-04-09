package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public class RelationFieldEditor extends FieldEditor {

    RelationLinkArtifactPart relationshipPart;

    public RelationFieldEditor(final UMLCanvas canvas,
	    final RelationLinkArtifact artifact,
	    final RelationLinkArtifactPart relationshipPart) {
	super(canvas, artifact);
	this.relationshipPart = relationshipPart;
    }

    @Override
    protected void next() {
	// No next part to edit
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	((RelationLinkArtifact) this.artifact).setPartContent(this.relationshipPart,
		newContent);
	this.artifact.rebuildGfxObject();
	return false;
    }
}
