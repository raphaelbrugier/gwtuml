/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.Window;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectPartAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartAttributesEditor extends FieldEditor {

    UMLClassAttribute attributeToChange;

    /**
     * @param canvas
     * @param objectPartAttributesArtifact
     * @param attributeToChange
     */
    public ClassPartAttributesEditor(final UMLCanvas canvas,
	    final ObjectPartAttributesArtifact objectPartAttributesArtifact,
	    final UMLClassAttribute attributeToChange) {
	super(canvas, objectPartAttributesArtifact);
	this.attributeToChange = attributeToChange;
    }

    /**
     * Constructor of 
     *
     * @param canvas
     * @param classPartAttributesArtifact
     * @param attributeToChange
     */
    public ClassPartAttributesEditor(UMLCanvas canvas,
	    ClassPartAttributesArtifact classPartAttributesArtifact,
	    UMLClassAttribute attributeToChange) {
	super(canvas, classPartAttributesArtifact);
	this.attributeToChange = attributeToChange;
    }

    @Override
    protected void next() {
	((NodePartArtifact) this.artifact).edit();
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	if (newContent.equals("")) {
	    ((ClassPartAttributesArtifact) this.artifact).remove(this.attributeToChange);
	    ((ClassPartAttributesArtifact) this.artifact).getNodeArtifact()
		    .rebuildGfxObject();
	    return false;
	}

	final LexicalAnalyzer lex = new LexicalAnalyzer(newContent);
	try {
	    String type = null;
	    String name = null;
	    UMLVisibility visibility = UMLVisibility.PACKAGE;
	    LexicalAnalyzer.Token tk = lex.getToken();
	    if (tk != null && tk.getType() != LexicalFlag.VISIBILITY) {
		visibility = UMLVisibility.PACKAGE;
	    } else if (tk != null) {
		visibility = UMLVisibility.getVisibilityFromToken(tk.getContent()
			.charAt(0));
		tk = lex.getToken();
	    }
	    if (tk == null || tk.getType() != LexicalFlag.IDENTIFIER) {
		throw new UMLDrawerException(
			"invalid format : must match 'identifier:type'");
	    }
	    name = tk.getContent();
	    tk = lex.getToken();
	    if (tk != null) {
		if (tk.getType() != LexicalFlag.SIGN
			|| !tk.getContent().equals(":")) {
		    throw new UMLDrawerException(
			    "invalid format : must match 'identifier:type'");
		}
		tk = lex.getToken();
		if (tk == null || tk.getType() != LexicalFlag.IDENTIFIER) {
		    throw new UMLDrawerException(
			    "invalid format : must match 'identifier:type'");
		}
		type = tk.getContent();
	    }
	    this.attributeToChange.setVisibility(visibility);
	    this.attributeToChange.setName(name);
	    this.attributeToChange.setType(type);
	    ((ClassPartAttributesArtifact) this.artifact).getNodeArtifact()
		    .rebuildGfxObject();
	} catch (final UMLDrawerException e) {
	    Window.alert(e.getMessage());
	}
	return true;
    }
}
