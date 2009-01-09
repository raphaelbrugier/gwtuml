package com.objetdirect.gwt.umlapi.client;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLElement;
import com.objetdirect.tatami.client.gfx.GraphicObject;

public class ClassDiagramAnimator implements UMLElementListener {

	public void itemEdited(UMLElement elem, GraphicObject item) {
		if (elem instanceof ClassArtifact) {
			if (classEditor!=null)
				classEditor.edit((ClassArtifact)elem, item);
		}
			
	}

	public ClassDiagramAnimator setClassEditor(ClassEditor classEditor) {
		this.classEditor = classEditor;
		return this;
	}
	
	ClassEditor classEditor;
}
