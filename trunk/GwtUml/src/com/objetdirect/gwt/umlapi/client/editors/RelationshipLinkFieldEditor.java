package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact.RelationshipArtifactPart;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public class RelationshipLinkFieldEditor extends FieldEditor {
	
	RelationshipArtifactPart relationshipPart;	
	
	public RelationshipLinkFieldEditor(UMLCanvas canvas, RelationshipLinkArtifact artifact, RelationshipArtifactPart relationshipPart) {
		super(canvas, artifact);
		this.relationshipPart = relationshipPart;
	}


	@Override
	protected void updateClass(String newContent) {
		((RelationshipLinkArtifact) artifact).setPartContent(relationshipPart, newContent);
		artifact.rebuildGfxObject();
	}
}
