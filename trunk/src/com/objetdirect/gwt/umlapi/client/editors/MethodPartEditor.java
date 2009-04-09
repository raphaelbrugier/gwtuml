/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.Window;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyser;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class MethodPartEditor extends FieldEditor {

    Method methodToChange;

    public MethodPartEditor(final UMLCanvas canvas,
	    final ClassPartMethodsArtifact artifact, final Method methodToChange) {
	super(canvas, artifact);
	this.methodToChange = methodToChange;
    }

    @Override
    protected void next() {
	((ClassPartArtifact) artifact).edit();
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (newContent.equals("")) {
	    ((ClassPartMethodsArtifact) artifact).remove(methodToChange);
	    ((ClassPartMethodsArtifact) artifact).getClassArtifact()
		    .rebuildGfxObject();
	    return false;
	}

	final LexicalAnalyser lex = new LexicalAnalyser(newContent);
	try {
	    final MethodSyntaxAnalyser ma = new MethodSyntaxAnalyser();
	    ma.process(lex, null);
	    final Method newMethod = ma.getMethod();
	    methodToChange.setVisibility(newMethod.getVisibility());
	    methodToChange.setName(newMethod.getName());
	    methodToChange.setReturnType(newMethod.getReturnType());
	    methodToChange.setParameters(newMethod.getParameters());
	    ((ClassPartMethodsArtifact) artifact).getClassArtifact()
		    .rebuildGfxObject();
	} catch (final UMLDrawerException e) {
	    Window.alert(e.getMessage());
	}
	return true;

    }
}
