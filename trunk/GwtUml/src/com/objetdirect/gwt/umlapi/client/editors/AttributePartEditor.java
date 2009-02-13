/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassPartArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author fmounier
 *
 */
public class AttributePartEditor extends FieldEditor {
	
	Attribute attributeToChange;
	
	public AttributePartEditor(UMLCanvas canvas, ClassAttributesArtifact artifact, Attribute attributeToChange) {
		super(canvas, artifact);
		this.attributeToChange = attributeToChange;
	}

	@Override
	protected void updateClass(String newContent) {
		attributeToChange.setName(newContent);
		artifact.getClassArtifact().rebuildGfxObject();
		
	}
}
