/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassPartArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author fmounier
 *
 */
public class NamePartFieldEditor extends FieldEditor {
	

	
	public NamePartFieldEditor(UMLCanvas canvas, ClassNameArtifact artifact) {
		super(canvas, artifact);
	}


	@Override
	protected void updateClass(String newContent) {
		((ClassNameArtifact) artifact).setClassName(newContent);
		artifact.getClassArtifact().rebuildGfxObject();
		
	}
}
