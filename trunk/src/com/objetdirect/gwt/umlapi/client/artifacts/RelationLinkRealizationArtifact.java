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
	relation = new Relation(RelationKind.REALIZATION);
	adornmentLeft = LinkAdornment.SOLID_ARROW;
	adornmentRight = LinkAdornment.NONE;
	style = LinkStyle.LONG_DASHED;
    }
}
