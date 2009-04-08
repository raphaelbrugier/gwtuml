/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks;

import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * @author florian
 * 
 */
public class AggregationLinkArtifact extends RelationLinkArtifact {

    /**
     * @param left
     * @param right
     */
    public AggregationLinkArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	super(left, right);
	this.relation = new Relation(RelationKind.AGGREGATION);
	this.relation.setLeftCardinality("1");
	this.relation.setRightCardinality("0..*");

	this.adornmentLeft = LinkAdornment.SOLID_DIAMOND;
	this.adornmentRight = LinkAdornment.NONE;
	this.style = LinkStyle.SOLID;
    }

}
