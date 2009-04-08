/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.Window;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyser;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassPartArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author fmounier
 */
public class MethodPartEditor extends FieldEditor {

    Method methodToChange;

    public MethodPartEditor(final UMLCanvas canvas,
	    final ClassMethodsArtifact artifact, final Method methodToChange) {
	super(canvas, artifact);
	this.methodToChange = methodToChange;
    }

    @Override
    protected void next() {
	((ClassPartArtifact) this.artifact).edit();
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (newContent.equals("")) {
	    ((ClassMethodsArtifact) this.artifact).remove(this.methodToChange);
	    ((ClassMethodsArtifact) this.artifact).getClassArtifact()
		    .rebuildGfxObject();
	    return false;
	}

	final LexicalAnalyser lex = new LexicalAnalyser(newContent);
	try {
	    final MethodSyntaxAnalyser ma = new MethodSyntaxAnalyser();
	    ma.process(lex, null);
	    final Method newMethod = ma.getMethod();
	    this.methodToChange.setVisibility(newMethod.getVisibility());
	    this.methodToChange.setName(newMethod.getName());
	    this.methodToChange.setReturnType(newMethod.getReturnType());
	    this.methodToChange.setParameters(newMethod.getParameters());
	    ((ClassMethodsArtifact) this.artifact).getClassArtifact()
		    .rebuildGfxObject();
	} catch (final UMLDrawerException e) {
	    Window.alert(e.getMessage());
	}
	return true;

    }
}
