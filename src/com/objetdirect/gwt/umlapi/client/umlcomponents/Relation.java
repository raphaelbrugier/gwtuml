/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkStyle;

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
	AGGREGATION("Aggregation", LinkAdornment.SOLID_DIAMOND, LinkAdornment.WIRE_ARROW, "1", "0..*", LinkStyle.SOLID),
	/**
	 * Association relation
	 */
	ASSOCIATION("Association", LinkAdornment.WIRE_ARROW, LinkAdornment.WIRE_CROSS, "0..*", "0..*", LinkStyle.SOLID),
	/**
	 * Composition relation
	 */
	COMPOSITION("Composition", LinkAdornment.INVERTED_SOLID_DIAMOND, LinkAdornment.WIRE_ARROW, "1", "0..*", LinkStyle.SOLID),
	/**
	 * Dependency relation
	 */
	DEPENDENCY("Dependency", LinkAdornment.WIRE_ARROW, LinkAdornment.WIRE_CROSS, "", "", LinkStyle.DASHED),
	/**
	 * Generalization relation
	 */
	GENERALIZATION("Generalization", LinkAdornment.SOLID_ARROW, LinkAdornment.WIRE_CROSS, "", "", LinkStyle.SOLID), 
	/**
	 * Other relation
	 */
	OTHER("Other", LinkAdornment.NONE, LinkAdornment.NONE, "", "", LinkStyle.SOLID), //TODO other relations (note, class) 
	/**
	 * Realization relation
	 */
	REALIZATION("Realization", LinkAdornment.SOLID_ARROW, LinkAdornment.WIRE_CROSS, "", "", LinkStyle.LONG_DASHED);

	private String name;
	private LinkAdornment defaultLeftAdornment;
	private LinkAdornment defaultRightAdornment;
	private String defaultLeftCardinality;
	private String defaultRightCardinality;
	private LinkStyle defaultLinkStyle;
	
	private RelationKind(final String name, 
		final LinkAdornment defaultLeftAdornment, final LinkAdornment defaultRightAdornment,
		final String defaultLeftCardinality, final String defaultRightCardinality,
		final LinkStyle defaultLinkStyle) {
	    
	    this.name = name;
	    this.defaultLeftAdornment = defaultLeftAdornment;
	    this.defaultRightAdornment = defaultRightAdornment;
	    this.defaultLeftCardinality = defaultLeftCardinality;
	    this.defaultRightCardinality = defaultRightCardinality;
	    this.defaultLinkStyle = defaultLinkStyle;
	}

	/**
	 * Getter for the defaultLeftAdornment
	 *
	 * @return the defaultLeftAdornment
	 */
	public LinkAdornment getDefaultLeftAdornment() {
	    return this.defaultLeftAdornment;
	}

	/**
	 * Getter for the defaultRightAdornment
	 *
	 * @return the defaultRightAdornment
	 */
	public LinkAdornment getDefaultRightAdornment() {
	    return this.defaultRightAdornment;
	}

	/**
	 * Getter for the defaultLeftCardinality
	 *
	 * @return the defaultLeftCardinality
	 */
	public String getDefaultLeftCardinality() {
	    return this.defaultLeftCardinality;
	}

	/**
	 * Getter for the defaultRightCardinality
	 *
	 * @return the defaultRightCardinality
	 */
	public String getDefaultRightCardinality() {
	    return this.defaultRightCardinality;
	}

	/**
	 * Getter for the defaultLinkStyle
	 *
	 * @return the defaultLinkStyle
	 */
	public LinkStyle getDefaultLinkStyle() {
	    return this.defaultLinkStyle;
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
    private LinkAdornment leftAdornment;
    private LinkAdornment rightAdornment;
    private LinkStyle linkStyle;

    /**
     * Constructor of Relation
     *
     * @param relationKind : The type of this relation
     */
    public Relation(final RelationKind relationKind) {
	this.relationKind = relationKind;
	this.linkStyle = relationKind.getDefaultLinkStyle();
	this.leftAdornment = relationKind.getDefaultLeftAdornment();
	this.rightAdornment = relationKind.getDefaultRightAdornment();
	this.leftCardinality = relationKind.getDefaultLeftCardinality();
	this.rightCardinality = relationKind.getDefaultRightCardinality();
	this.leftConstraint = "";
	this.rightConstraint = "";
	this.leftRole = "";
	this.rightRole = "";
	this.name = "";
    }

    /**
     * Getter for the link style
     *
     * @return the link style
     */
    public LinkStyle getLinkStyle() {
        return this.linkStyle;
    }

    /**
     * Setter for the link style
     *
     * @param linkStyle the link style to set
     */
    public void setLinkStyle(LinkStyle linkStyle) {
        this.linkStyle = linkStyle;
    }

    /**
     * Getter for the left adornment
     *
     * @return the left adornment
     */
    public LinkAdornment getLeftAdornment() {
        return this.leftAdornment;
    }

    /**
     * Getter for the right adornment
     *
     * @return the right adornment
     */
    public LinkAdornment getRightAdornment() {
        return this.rightAdornment;
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
     * Setter for the left adornment
     *
     * @param leftAdornment the left adornment to set
     */
    public void setLeftAdornment(LinkAdornment leftAdornment) {
        this.leftAdornment = leftAdornment;
    }

    /**
     * Setter for the right adornment
     *
     * @param rightAdornment the right adornment to set
     */
    public void setRightAdornment(LinkAdornment rightAdornment) {
        this.rightAdornment = rightAdornment;
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

    /**
     * Reverse the current relation
     */
    public void reverse() {
	LinkAdornment tempAdornment = this.leftAdornment;
	String tempCardinality = this.leftCardinality;
	String tempConstraint = this.leftConstraint;
	String tempRole = this.leftRole;
	this.leftAdornment = this.rightAdornment;
	this.leftCardinality = this.rightCardinality;
	this.leftConstraint = this.rightConstraint;
	this.leftRole = this.rightRole;
	this.rightAdornment = tempAdornment;
	this.rightCardinality = tempCardinality;
	this.rightConstraint = tempConstraint;
	this.rightRole = tempRole;
    }

}
