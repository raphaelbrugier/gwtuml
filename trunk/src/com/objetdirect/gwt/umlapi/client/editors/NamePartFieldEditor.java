/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassNameArtifact;
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
	protected boolean updateUMLArtifact(String newContent) {
		((ClassNameArtifact) artifact).setClassName(newContent);
		((ClassNameArtifact) artifact).getClassArtifact().rebuildGfxObject();
		return false;
	}
	@Override
	protected void next() {		
	}
}