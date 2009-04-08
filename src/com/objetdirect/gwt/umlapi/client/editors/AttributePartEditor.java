/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassPartArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Visibility;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author fmounier
 */
public class AttributePartEditor extends FieldEditor {

    Attribute attributeToChange;

    /**
     * @param canvas
     * @param artifact
     * @param attributeToChange
     */
    public AttributePartEditor(final UMLCanvas canvas,
	    final ClassAttributesArtifact artifact,
	    final Attribute attributeToChange) {
	super(canvas, artifact);
	this.attributeToChange = attributeToChange;
    }

    @Override
    protected void next() {
	((ClassPartArtifact) this.artifact).edit();
    }

    @Override
    protected boolean updateUMLArtifact(final String newContent) {
	Log.fatal(newContent);
	if (newContent.equals("")) {
	    ((ClassAttributesArtifact) this.artifact).remove(this.attributeToChange);
	    ((ClassAttributesArtifact) this.artifact).getClassArtifact()
		    .rebuildGfxObject();
	    return false;
	}

	final LexicalAnalyser lex = new LexicalAnalyser(newContent);
	try {
	    String type = null;
	    String name = null;
	    Visibility visibility;
	    LexicalAnalyser.Token tk = lex.getToken();
	    if (tk.getType() != LexicalFlag.VISIBILITY) {
		visibility = Visibility.PACKAGE;
	    } else {
		visibility = Visibility.getVisibilityFromToken(tk.getContent()
			.charAt(0));
		tk = lex.getToken();
	    }
	    if (tk.getType() != LexicalFlag.IDENTIFIER) {
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
	    ((ClassAttributesArtifact) this.artifact).getClassArtifact()
		    .rebuildGfxObject();
	} catch (final UMLDrawerException e) {
	    Window.alert(e.getMessage());
	}
	return true;
    }
}
