package com.objetdirect.gwt.umlapi.client.editors;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
public class NoteFieldEditor extends FieldEditor {
	
	public NoteFieldEditor(UMLCanvas canvas, NoteArtifact artifact) {
		super(canvas, artifact);
	}
	@Override
	protected boolean updateUMLArtifact(String newContent) {
	    if(newContent.equals("")) {
	        canvas.remove(artifact);
	        return false;
	    }
		((NoteArtifact) artifact).setContent(newContent);
		artifact.rebuildGfxObject();
		return false;
	}
	@Override
	protected void next() {
	}
}
