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
	
	
	private boolean isTheStereotype;
    public NamePartFieldEditor(UMLCanvas canvas, ClassNameArtifact artifact, boolean isTheStereotype) {
		super(canvas, artifact);
		this.isTheStereotype = isTheStereotype;
	}
	@Override
	protected boolean updateUMLArtifact(String newContent) {
	    if(isTheStereotype) {
	        if(newContent.equals("")) {
	            ((ClassNameArtifact) artifact).setStereotype(null);
	        }
	        else {
	            if(!(newContent.startsWith("<<") && newContent.endsWith(">>"))) newContent = "<<" + newContent + ">>";
	            ((ClassNameArtifact) artifact).setStereotype(newContent);
	        }
	    }
	    else {
	        if(newContent.equals("")) {
	            newContent = "Class";
	        }
	        ((ClassNameArtifact) artifact).setClassName(newContent);
	    }
		((ClassNameArtifact) artifact).getClassArtifact().rebuildGfxObject();
		return false;
	}
	@Override
	protected void next() {		
	}
}
