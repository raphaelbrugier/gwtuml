/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * @author florian
 *
 */
public class GeneralizationLinkArtifact extends RelationshipLinkArtifact {

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
    public LinkedHashMap<String, Command> getRightMenu() {
        LinkedHashMap<String, Command> rightMenu = new LinkedHashMap<String, Command>();
        Command doNothing = new Command() {
            public void execute() {
            }
        };
        Command remove = new Command() {
            public void execute() {
                getCanvas().removeSelected();
            }
        };
        rightMenu.put("Generalization " + leftClassArtifact.getClassName() + " <-> "
                + rightClassArtifact.getClassName(), doNothing);
        rightMenu.put("-", null);
        rightMenu.put("> Edit", doNothing);
        rightMenu.put("> Reverse", doNothing);
        rightMenu.put("> Delete", remove);
        return rightMenu;
    }
}
