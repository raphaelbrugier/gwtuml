/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * @author florian
 */
public class Relation {
    public enum RelationKind {
	AGGREGATION("Aggregation"), ASSOCIATION("Association"), COMPOSITION(
		"Composition"), DEPENDENCY("Dependency"), GENERALIZATION(
		"Generalization"), OTHER("Other"), REALIZATION("Realization");

	private String name;

	private RelationKind(final String name) {
	    this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
	    return this.name;
	}
    }

    private String leftCardinality;
    private String leftConstraint;
    private String leftRole;
    private String name;
    private RelationKind relationKind;
    private String rightCardinality;
    private String rightConstraint;
    private String rightRole;

    public Relation(final RelationKind relationKind) {
	this.relationKind = relationKind;
	this.leftCardinality = "";
	this.leftConstraint = "";
	this.leftRole = "";
	this.name = "";
	this.rightCardinality = "";
	this.rightConstraint = "";
	this.rightRole = "";
    }

    /**
     * @return the leftCardinality
     * 
     */
    public String getLeftCardinality() {
	return this.leftCardinality;
    }

    /**
     * @return the leftConstraint
     * 
     */
    public String getLeftConstraint() {
	return this.leftConstraint;
    }

    /**
     * @return the leftRole
     * 
     */
    public String getLeftRole() {
	return this.leftRole;
    }

    /**
     * @return the name
     * 
     */
    public String getName() {
	return this.name;
    }

    /**
     * @return the relationKind
     */
    public RelationKind getRelationKind() {
	return this.relationKind;
    }

    /**
     * @return the rightCardinality
     * 
     */
    public String getRightCardinality() {
	return this.rightCardinality;
    }

    /**
     * @return the rightConstraint
     * 
     */
    public String getRightConstraint() {
	return this.rightConstraint;
    }

    /**
     * @return the rightRole
     * 
     */
    public String getRightRole() {
	return this.rightRole;
    }

    public void setLeftCardinality(final String leftCardinality) {
	this.leftCardinality = leftCardinality;
    }

    public void setLeftConstraint(final String leftConstraint) {
	this.leftConstraint = leftConstraint;
    }

    public void setLeftRole(final String leftRole) {
	this.leftRole = leftRole;
    }

    public void setName(final String name) {
	this.name = name;
    }

    /**
     * @param relationKind
     *            the relationKind to set
     */
    public void setRelationKind(final RelationKind relationKind) {
	this.relationKind = relationKind;
    }

    public void setRightCardinality(final String rightCardinality) {
	this.rightCardinality = rightCardinality;
    }

    public void setRightConstraint(final String rightConstraint) {
	this.rightConstraint = rightConstraint;
    }

    public void setRightRole(final String rightRole) {
	this.rightRole = rightRole;
    }
}
