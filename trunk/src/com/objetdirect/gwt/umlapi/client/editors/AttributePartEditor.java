/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.editors;
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
 * @author  fmounier
 */
public class AttributePartEditor extends FieldEditor {

    Attribute attributeToChange;

    public AttributePartEditor(UMLCanvas canvas, ClassAttributesArtifact artifact, Attribute attributeToChange) {
        super(canvas, artifact);
        this.attributeToChange = attributeToChange;
    }
    @Override
    protected void updateClass(String newContent) {
        LexicalAnalyser lex = new LexicalAnalyser(newContent);
        try {
            String type = null;
            String name = null;
            Visibility visibility;
            LexicalAnalyser.Token tk = lex.getToken();
            if (tk.getType()!=LexicalFlag.VISIBILITY) visibility = Visibility.PACKAGE;
            else {
                visibility = Visibility.getVisibilityFromToken(tk.getContent().charAt(0));
                tk = lex.getToken();
            }
            if (tk.getType()!=LexicalFlag.IDENTIFIER) throw new UMLDrawerException("invalid format : must match 'identifier:type'");
            name = tk.getContent();
            tk = lex.getToken();
            if (tk!=null) {
                if (tk.getType()!=LexicalFlag.SIGN || !tk.getContent().equals(":"))
                    throw new UMLDrawerException("invalid format : must match 'identifier:type'");
                tk = lex.getToken();
                if (tk==null || tk.getType()!=LexicalFlag.IDENTIFIER)
                    throw new UMLDrawerException("invalid format : must match 'identifier:type'");
                type = tk.getContent();
            }
            attributeToChange.setVisibility(visibility);
            attributeToChange.setName(name);
            attributeToChange.setType(type);
            ((ClassAttributesArtifact) artifact).getClassArtifact().rebuildGfxObject();
        } catch (UMLDrawerException e) {
            Window.alert(e.getMessage());
        }
    }

    @Override
    protected void next() {
        ((ClassPartArtifact) artifact).edit();
    }
}
