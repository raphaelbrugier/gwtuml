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
public class RelationLinkRealizationArtifact extends RelationLinkArtifact {

    public RelationLinkRealizationArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	super(left, right);
	this.relation = new Relation(RelationKind.REALIZATION);
	this.adornmentLeft = LinkAdornment.SOLID_ARROW;
	this.adornmentRight = LinkAdornment.NONE;
	this.style = LinkStyle.LONG_DASHED;
    }
}
