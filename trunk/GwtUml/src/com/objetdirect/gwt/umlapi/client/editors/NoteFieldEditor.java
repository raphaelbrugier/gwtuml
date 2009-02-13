package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassNameArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public class NoteFieldEditor extends FieldEditor {
	
	
	public NoteFieldEditor(UMLCanvas canvas, NoteArtifact artifact) {
		super(canvas, artifact);
	}


	@Override
	protected void updateClass(String newContent) {
		((NoteArtifact) artifact).setContent(newContent);
		((NoteArtifact) artifact).rebuildGfxObject();
	}
}
