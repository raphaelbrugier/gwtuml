/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
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
	 * Getter for the name
	 * 
	 * @return the name
	 */
	public String getName() {
	    return name;
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
	leftCardinality = "";
	leftConstraint = "";
	leftRole = "";
	name = "";
	rightCardinality = "";
	rightConstraint = "";
	rightRole = "";
    }

    /**
     * Getter for the leftCardinality
     * 
     * @return the leftCardinality
     */
    public String getLeftCardinality() {
	return leftCardinality;
    }

    /**
     * Getter for the leftConstraint
     * 
     * @return the leftConstraint
     */
    public String getLeftConstraint() {
	return leftConstraint;
    }

    /**
     * Getter for the leftRole
     * 
     * @return the leftRole
     */
    public String getLeftRole() {
	return leftRole;
    }

    /**
     * Getter for the name
     * 
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * Getter for the relationKind
     * 
     * @return the relationKind
     */
    public RelationKind getRelationKind() {
	return relationKind;
    }

    /**
     * Getter for the rightCardinality
     * 
     * @return the rightCardinality
     */
    public String getRightCardinality() {
	return rightCardinality;
    }

    /**
     * Getter for the rightConstraint
     * 
     * @return the rightConstraint
     */
    public String getRightConstraint() {
	return rightConstraint;
    }

    /**
     * Getter for the rightRole
     * 
     * @return the rightRole
     */
    public String getRightRole() {
	return rightRole;
    }

    /**
     * Setter for the leftCardinality
     * 
     * @param leftCardinality
     *            the leftCardinality to set
     */
    public void setLeftCardinality(final String leftCardinality) {
	this.leftCardinality = leftCardinality;
    }

    /**
     * Setter for the leftConstraint
     * 
     * @param leftConstraint
     *            the leftConstraint to set
     */
    public void setLeftConstraint(final String leftConstraint) {
	this.leftConstraint = leftConstraint;
    }

    /**
     * Setter for the leftRole
     * 
     * @param leftRole
     *            the leftRole to set
     */
    public void setLeftRole(final String leftRole) {
	this.leftRole = leftRole;
    }

    /**
     * Setter for the name
     * 
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * Setter for the relationKind
     * 
     * @param relationKind
     *            the relationKind to set
     */
    public void setRelationKind(final RelationKind relationKind) {
	this.relationKind = relationKind;
    }

    /**
     * Setter for the rightCardinality
     * 
     * @param rightCardinality
     *            the rightCardinality to set
     */
    public void setRightCardinality(final String rightCardinality) {
	this.rightCardinality = rightCardinality;
    }

    /**
     * Setter for the rightConstraint
     * 
     * @param rightConstraint
     *            the rightConstraint to set
     */
    public void setRightConstraint(final String rightConstraint) {
	this.rightConstraint = rightConstraint;
    }

    /**
     * Setter for the rightRole
     * 
     * @param rightRole
     *            the rightRole to set
     */
    public void setRightRole(final String rightRole) {
	this.rightRole = rightRole;
    }

}
