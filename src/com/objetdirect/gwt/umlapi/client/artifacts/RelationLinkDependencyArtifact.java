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
public class RelationLinkDependencyArtifact extends RelationLinkArtifact {

    public RelationLinkDependencyArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	super(left, right);
	this.relation = new Relation(RelationKind.DEPENDENCY);
	this.adornmentLeft = LinkAdornment.WIRE_ARROW;
	this.adornmentRight = LinkAdornment.NONE;
	this.style = LinkStyle.DASHED;
    }
}
