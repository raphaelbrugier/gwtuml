package com.objetdirect.gwt.umlapi.client.editors;

import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNote;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This field editor is a specialized editor for {@link UMLNote} editing
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class NoteFieldEditor extends FieldEditor {
    /**
     * Constructor of the {@link NoteFieldEditor} 
     *     
     * @param canvas The canvas on which is the artifact
     * @param artifact The artifact being edited
     */
    public NoteFieldEditor(final UMLCanvas canvas, final NoteArtifact artifact) {
	super(canvas, artifact);
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
	if (newContent.equals("")) {
	    this.canvas.remove(this.artifact);
	    return false;
	}
	((NoteArtifact) this.artifact).setContent(newContent);
	this.artifact.rebuildGfxObject();
	return false;
    }
}
