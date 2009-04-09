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
public class RelationLinkAggregationArtifact extends RelationLinkArtifact {

    /**
     * @param left
     * @param right
     */
    public RelationLinkAggregationArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	super(left, right);
	relation = new Relation(RelationKind.AGGREGATION);
	relation.setLeftCardinality("1");
	relation.setRightCardinality("0..*");

	adornmentLeft = LinkAdornment.SOLID_DIAMOND;
	adornmentRight = LinkAdornment.NONE;
	style = LinkStyle.SOLID;
    }

}
