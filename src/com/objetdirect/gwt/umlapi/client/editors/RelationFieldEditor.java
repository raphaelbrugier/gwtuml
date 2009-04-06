package com.objetdirect.gwt.umlapi.client.editors;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationArtifact.RelationArtifactPart;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
public class RelationFieldEditor extends FieldEditor {
	
	RelationArtifactPart relationshipPart;	
	
	public RelationFieldEditor(UMLCanvas canvas, RelationArtifact artifact, RelationArtifactPart relationshipPart) {
		super(canvas, artifact);
		this.relationshipPart = relationshipPart;
	}
	@Override
	protected boolean updateUMLArtifact(String newContent) {
		((RelationArtifact) artifact).setPartContent(relationshipPart, newContent);
		artifact.rebuildGfxObject();
		return false;
	}
	@Override
	protected void next() {		
	}
}
