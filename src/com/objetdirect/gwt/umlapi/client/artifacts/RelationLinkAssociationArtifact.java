/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * This artifact represent an association relation between two classes
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class RelationLinkAssociationArtifact extends RelationLinkArtifact {


    /**
     * Constructor of {@link RelationLinkAssociationArtifact}
     * 
     * @param left The left {@link ClassArtifact} of the relation
     * @param right The right {@link ClassArtifact} of the relation
     */
    public RelationLinkAssociationArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	super(left, right);
	this.relation = new Relation(RelationKind.ASSOCIATION);
	this.relation.setLeftCardinality("0..*");
	this.relation.setRightCardinality("0..*");
	this.relation.setLeftSideNavigable(true);
	this.relation.setLeftSideNavigable(false);
	this.adornmentLeft = LinkAdornment.WIRE_ARROW;
	this.adornmentRight = LinkAdornment.WIRE_CROSS;
	this.style = LinkStyle.SOLID;
    }
}
