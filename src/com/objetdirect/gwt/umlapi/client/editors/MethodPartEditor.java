/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.Window;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyzer;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * This field editor is a specialized editor for {@link Method} editing
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class MethodPartEditor extends FieldEditor {

    Method methodToChange;

    /**
     * Constructor of the {@link MethodPartEditor} 
     *     
     * @param canvas The canvas on which is the artifact
     * @param artifact The artifact being edited
     * @param methodToChange The {@link Method} on which edition has been requested
     */
    public MethodPartEditor(final UMLCanvas canvas,
	    final ClassPartMethodsArtifact artifact, final Method methodToChange) {
	super(canvas, artifact);
	this.methodToChange = methodToChange;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#next()
     */
    @Override
    protected void next() {
	((ClassPartArtifact) this.artifact).edit();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.editors.FieldEditor#updateUMLArtifact(java.lang.String)
     */
    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (newContent.equals("")) {
	    ((ClassPartMethodsArtifact) this.artifact).remove(this.methodToChange);
	    ((ClassPartMethodsArtifact) this.artifact).getClassArtifact()
		    .rebuildGfxObject();
	    return false;
	}

	final LexicalAnalyzer lex = new LexicalAnalyzer(newContent);
	try {
	    final MethodSyntaxAnalyzer ma = new MethodSyntaxAnalyzer();
	    ma.process(lex, null);
	    final Method newMethod = ma.getMethod();
	    this.methodToChange.setVisibility(newMethod.getVisibility());
	    this.methodToChange.setName(newMethod.getName());
	    this.methodToChange.setReturnType(newMethod.getReturnType());
	    this.methodToChange.setParameters(newMethod.getParameters());
	    ((ClassPartMethodsArtifact) this.artifact).getClassArtifact()
		    .rebuildGfxObject();
	} catch (final UMLDrawerException e) {
	    Window.alert(e.getMessage());
	}
	return true;

    }
}
