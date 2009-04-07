/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks;

import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;

/**
 * @author florian
 *
 */
public class AggregationLinkArtifact extends RelationLinkArtifact {

    public AggregationLinkArtifact(ClassArtifact left, ClassArtifact right) {
        super(left, right);
        relation = new Relation(RelationKind.AGGREGATION);
        relation.setLeftCardinality("1");
        relation.setRightCardinality("0..*");
        
        adornmentLeft = LinkAdornment.SOLID_DIAMOND;
        adornmentRight = LinkAdornment.NONE;
        style = LinkStyle.SOLID;
    }

}
