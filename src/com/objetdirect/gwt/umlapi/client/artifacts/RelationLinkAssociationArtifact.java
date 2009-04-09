/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class RelationLinkAssociationArtifact extends RelationLinkArtifact {

    /**
     * @param left
     * @param right
     */
    public RelationLinkAssociationArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	super(left, right);
	relation = new Relation(RelationKind.ASSOCIATION);
	relation.setLeftCardinality("0..*");
	relation.setRightCardinality("0..*");
	adornmentLeft = LinkAdornment.WIRE_ARROW;
	adornmentRight = LinkAdornment.NONE;
	style = LinkStyle.SOLID;
    }
}
