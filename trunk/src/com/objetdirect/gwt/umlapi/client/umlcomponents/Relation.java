/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represent an uml relation between two {@link Class}es
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class Relation extends UMLComponent {
    /**
     * This enumeration lists all the relations type between two {@link Class}es
     * 
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     *
     */
    public enum RelationKind {
	/**
	 * Aggregation relation
	 */
	AGGREGATION("Aggregation"),	
	/**
	 * Association relation
	 */
	ASSOCIATION("Association"),
	/**
	 * Composition relation
	 */
	COMPOSITION("Composition"),
	/**
	 * Dependency relation
	 */
	DEPENDENCY("Dependency"),
	/**
	 * Generalization relation
	 */
	GENERALIZATION("Generalization"), 
	/**
	 * Other relation
	 */
	OTHER("Other"), //TODO other relations (note, class) 
	/**
	 * Realization relation
	 */
	REALIZATION("Realization");

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

    /**
     * Constructor of Relation
     *
     * @param relationKind : The type of this relation
     */
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
     * Getter for the leftCardinality
     * 
     * @return the leftCardinality
     */
    public String getLeftCardinality() {
	return this.leftCardinality;
    }

    /**
     * Getter for the leftConstraint
     * 
     * @return the leftConstraint
     */
    public String getLeftConstraint() {
	return this.leftConstraint;
    }

    /**
     * Getter for the leftRole
     * 
     * @return the leftRole
     */
    public String getLeftRole() {
	return this.leftRole;
    }

    /**
     * Getter for the name
     * 
     * @return the name
     */
    public String getName() {
	return this.name;
    }

    /**
     * Getter for the relationKind
     * 
     * @return the relationKind
     */
    public RelationKind getRelationKind() {
	return this.relationKind;
    }

    /**
     * Getter for the rightCardinality
     * 
     * @return the rightCardinality
     */
    public String getRightCardinality() {
	return this.rightCardinality;
    }

    /**
     * Getter for the rightConstraint
     * 
     * @return the rightConstraint
     */
    public String getRightConstraint() {
	return this.rightConstraint;
    }

    /**
     * Getter for the rightRole
     * 
     * @return the rightRole
     */
    public String getRightRole() {
	return this.rightRole;
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
