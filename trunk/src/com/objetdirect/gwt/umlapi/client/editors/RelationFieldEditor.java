package com.objetdirect.gwt.umlapi.client.editors;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationLinkArtifact.RelationLinkArtifactPart;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
public class RelationFieldEditor extends FieldEditor {
	
	RelationLinkArtifactPart relationshipPart;	
	
	public RelationFieldEditor(UMLCanvas canvas, RelationLinkArtifact artifact, RelationLinkArtifactPart relationshipPart) {
		super(canvas, artifact);
		this.relationshipPart = relationshipPart;
	}
	@Override
	protected boolean updateUMLArtifact(String newContent) {
		((RelationLinkArtifact) artifact).setPartContent(relationshipPart, newContent);
		artifact.rebuildGfxObject();
		return false;
	}
	@Override
	protected void next() {		
	}
}
