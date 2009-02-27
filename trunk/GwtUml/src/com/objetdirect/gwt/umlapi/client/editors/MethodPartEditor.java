/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyser;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassPartArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author fmounier
 *
 */
public class MethodPartEditor extends FieldEditor {
	
	Method methodToChange;
	
	public MethodPartEditor(UMLCanvas canvas, ClassMethodsArtifact artifact, Method methodToChange) {
		super(canvas, artifact);
		this.methodToChange = methodToChange;
	}

	@Override
	protected void updateClass(String newContent) {
		LexicalAnalyser lex = new LexicalAnalyser(newContent);
        try {
                MethodSyntaxAnalyser ma = new MethodSyntaxAnalyser();
                ma.process(lex, null);
                Method newMethod = ma.getMethod();
                methodToChange.setName(newMethod.getName());
                methodToChange.setReturnType(newMethod.getReturnType());
                methodToChange.setParameters(newMethod.getParameters());
        		((ClassMethodsArtifact) artifact).getClassArtifact().rebuildGfxObject();
        } catch (UMLDrawerException e) {
                Window.alert(e.getMessage());
        }
		
		
	}
}
