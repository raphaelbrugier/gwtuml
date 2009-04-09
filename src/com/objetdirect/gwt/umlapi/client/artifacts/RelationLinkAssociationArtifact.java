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
	this.relation = new Relation(RelationKind.ASSOCIATION);
	this.relation.setLeftCardinality("0..*");
	this.relation.setRightCardinality("0..*");
	this.adornmentLeft = LinkAdornment.WIRE_ARROW;
	this.adornmentRight = LinkAdornment.NONE;
	this.style = LinkStyle.SOLID;
    }
}
