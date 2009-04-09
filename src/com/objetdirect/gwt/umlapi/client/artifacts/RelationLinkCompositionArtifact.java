/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * This artifact represent a composition relation between two classes
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class RelationLinkCompositionArtifact extends RelationLinkArtifact {

    /**
     * Constructor of {@link RelationLinkCompositionArtifact}
     * 
     * @param left The left {@link ClassArtifact} of the relation
     * @param right The right {@link ClassArtifact} of the relation
     */
    public RelationLinkCompositionArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	super(left, right);
	this.relation = new Relation(RelationKind.COMPOSITION);
	this.relation.setLeftCardinality("1");
	this.relation.setRightCardinality("0..*");
	this.adornmentLeft = LinkAdornment.INVERTED_SOLID_DIAMOND;
	this.adornmentRight = LinkAdornment.NONE;
	this.style = LinkStyle.SOLID;
    }
}