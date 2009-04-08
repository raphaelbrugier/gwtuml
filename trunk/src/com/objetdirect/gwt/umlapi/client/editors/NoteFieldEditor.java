package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public class NoteFieldEditor extends FieldEditor {

    public NoteFieldEditor(final UMLCanvas canvas, final NoteArtifact artifact) {
	super(canvas, artifact);
    }

    @Override
    protected void next() {
	// No next part to edit
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (newContent.equals("")) {
	    this.canvas.remove(this.artifact);
	    return false;
	}
	((NoteArtifact) this.artifact).setContent(newContent);
	this.artifact.rebuildGfxObject();
	return false;
    }
}
