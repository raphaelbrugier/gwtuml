package com.objetdirect.gwt.umlapi.client.editors;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
public class NoteFieldEditor extends FieldEditor {
	
	public NoteFieldEditor(UMLCanvas canvas, NoteArtifact artifact) {
		super(canvas, artifact);
	}
	@Override
	protected void updateClass(String newContent) {
		((NoteArtifact) artifact).setContent(newContent);
		artifact.rebuildGfxObject();
	}
	@Override
	protected void next() {
	}
}
