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
public class RealizationLinkArtifact extends RelationArtifact {

    public RealizationLinkArtifact(ClassArtifact left, ClassArtifact right) {
        super(left, right);
        relation = new Relation(RelationKind.REALIZATION);
        this.adornmentLeft = LinkAdornment.SOLID_ARROW;
        this.adornmentRight = LinkAdornment.NONE;
        this.style = LinkStyle.LONG_DASHED;
    }
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact#getRightMenu()
     */
    @Override
    public MenuBarAndTitle getRightMenu() {
        MenuBarAndTitle rightMenu = new MenuBarAndTitle();
        rightMenu.setName("Realization " + leftClassArtifact.getClassName() + 
                " " + adornmentLeft.getShape().getIdiom() +  "-" + adornmentRight.getShape().getIdiom(true) + " "
                + rightClassArtifact.getClassName());
        return rightMenu;
    }
}
