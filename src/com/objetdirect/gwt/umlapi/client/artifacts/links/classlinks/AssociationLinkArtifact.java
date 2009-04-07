/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks;

import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;

/**
 * @author florian
 *
 */
public class AssociationLinkArtifact extends RelationLinkArtifact {

    public AssociationLinkArtifact(ClassArtifact left, ClassArtifact right) {
        super(left, right);
        relation = new Relation(RelationKind.ASSOCIATION);
        relation.setLeftCardinality("0..*");
        relation.setRightCardinality("0..*");
        this.adornmentLeft = LinkAdornment.WIRE_ARROW;
        this.adornmentRight = LinkAdornment.NONE;
        this.style = LinkStyle.SOLID;
    }
}
