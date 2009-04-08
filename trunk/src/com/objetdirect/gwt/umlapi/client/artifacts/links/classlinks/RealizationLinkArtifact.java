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
public class RealizationLinkArtifact extends RelationLinkArtifact {

    public RealizationLinkArtifact(final ClassArtifact left,
	    final ClassArtifact right) {
	super(left, right);
	this.relation = new Relation(RelationKind.REALIZATION);
	this.adornmentLeft = LinkAdornment.SOLID_ARROW;
	this.adornmentRight = LinkAdornment.NONE;
	this.style = LinkStyle.LONG_DASHED;
    }
}
