/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks;

import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;

/**
 * @author florian
 *
 */
public class GeneralizationLinkArtifact extends RelationArtifact {

    public GeneralizationLinkArtifact(ClassArtifact left, ClassArtifact right) {
        super(left, right);
        relation = new Relation(RelationKind.GENERALIZATION);
        this.adornmentLeft = LinkAdornment.SOLID_ARROW;
        this.adornmentRight = LinkAdornment.NONE;
        this.style = LinkStyle.SOLID;
    }
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact#getRightMenu()
     */
    @Override
    public MenuBarAndTitle getRightMenu() {
        MenuBarAndTitle rightMenu = new MenuBarAndTitle();
        rightMenu.setName("Generalization " + leftClassArtifact.getClassName() + 
                " " + adornmentLeft.getShape().getIdiom() +  "-" + adornmentRight.getShape().getIdiom(true) + " "
                + rightClassArtifact.getClassName());
        return rightMenu;
    }
}
