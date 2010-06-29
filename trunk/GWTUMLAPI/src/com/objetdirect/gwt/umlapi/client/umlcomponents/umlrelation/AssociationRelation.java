package com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation;

import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkStyle.SOLID;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

public class AssociationRelation extends ClassToClassRelation {

	public enum AssociationType {
		ASSOCIATION, AGGREGATION, COMPOSITION
	}

	private String name;

	private String leftCardinality;
	private String leftConstraint;
	private String leftRole;
	private String leftStereotype;
	private final LinkAdornment leftAdornment;

	private String rightCardinality;
	private String rightConstraint;
	private String rightRole;
	private String rightStereotype;
	private final LinkAdornment rightAdornment;

	/**
	 * @param leftTarget
	 * @param rightTarget
	 * @param name
	 * @param leftCardinality
	 * @param leftConstraint
	 * @param leftRole
	 * @param leftStereotype
	 * @param leftAdornment
	 * @param rightCardinality
	 * @param rightConstraint
	 * @param rightRole
	 * @param rightStereotype
	 * @param rightAdornment
	 */
	private AssociationRelation(UMLClass leftTarget, UMLClass rightTarget, String name, String leftCardinality, String leftConstraint, String leftRole,
			String leftStereotype, LinkAdornment leftAdornment, String rightCardinality, String rightConstraint, String rightRole, String rightStereotype,
			LinkAdornment rightAdornment) {
		super(leftTarget, rightTarget);
		this.name = name;
		this.leftCardinality = leftCardinality;
		this.leftConstraint = leftConstraint;
		this.leftRole = leftRole;
		this.leftStereotype = leftStereotype;
		this.leftAdornment = leftAdornment;
		this.rightCardinality = rightCardinality;
		this.rightConstraint = rightConstraint;
		this.rightRole = rightRole;
		this.rightStereotype = rightStereotype;
		this.rightAdornment = rightAdornment;
	}

	public static AssociationRelation createAssociation(UMLClass leftTarget, UMLClass rightTarget) {
		return new AssociationRelation(leftTarget, rightTarget, "", "", "", "", "", LinkAdornment.NONE, "1", "", "role", "", LinkAdornment.WIRE_ARROW);
	}

	public static AssociationRelation createComposition(UMLClass leftTarget, UMLClass rightTarget) {
		return new AssociationRelation(leftTarget, rightTarget, "", "", "", "", "", LinkAdornment.INVERTED_SOLID_DIAMOND, "1", "", "role", "",
				LinkAdornment.WIRE_ARROW);
	}

	public static AssociationRelation createAggregation(UMLClass leftTarget, UMLClass rightTarget) {
		return new AssociationRelation(leftTarget, rightTarget, "", "", "", "", "", LinkAdornment.SOLID_DIAMOND, "1", "", "role", "", LinkAdornment.WIRE_ARROW);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the leftCardinality
	 */
	public String getLeftCardinality() {
		return leftCardinality;
	}

	/**
	 * @param leftCardinality
	 *            the leftCardinality to set
	 */
	public void setLeftCardinality(String leftCardinality) {
		this.leftCardinality = leftCardinality;
	}

	/**
	 * @return the leftConstraint
	 */
	public String getLeftConstraint() {
		return leftConstraint;
	}

	/**
	 * @param leftConstraint
	 *            the leftConstraint to set
	 */
	public void setLeftConstraint(String leftConstraint) {
		this.leftConstraint = leftConstraint;
	}

	/**
	 * @return the leftRole
	 */
	public String getLeftRole() {
		return leftRole;
	}

	/**
	 * @param leftRole
	 *            the leftRole to set
	 */
	public void setLeftRole(String leftRole) {
		this.leftRole = leftRole;
	}

	/**
	 * @return the leftStereotype
	 */
	public String getLeftStereotype() {
		return leftStereotype;
	}

	/**
	 * @param leftStereotype
	 *            the leftStereotype to set
	 */
	public void setLeftStereotype(String leftStereotype) {
		this.leftStereotype = leftStereotype;
	}

	/**
	 * @return the rightCardinality
	 */
	public String getRightCardinality() {
		return rightCardinality;
	}

	/**
	 * @param rightCardinality
	 *            the rightCardinality to set
	 */
	public void setRightCardinality(String rightCardinality) {
		this.rightCardinality = rightCardinality;
	}

	/**
	 * @return the rightConstraint
	 */
	public String getRightConstraint() {
		return rightConstraint;
	}

	/**
	 * @param rightConstraint
	 *            the rightConstraint to set
	 */
	public void setRightConstraint(String rightConstraint) {
		this.rightConstraint = rightConstraint;
	}

	/**
	 * @return the rightRole
	 */
	public String getRightRole() {
		return rightRole;
	}

	/**
	 * @param rightRole
	 *            the rightRole to set
	 */
	public void setRightRole(String rightRole) {
		this.rightRole = rightRole;
	}

	/**
	 * @return the rightStereotype
	 */
	public String getRightStereotype() {
		return rightStereotype;
	}

	/**
	 * @param rightStereotype
	 *            the rightStereotype to set
	 */
	public void setRightStereotype(String rightStereotype) {
		this.rightStereotype = rightStereotype;
	}

	/**
	 * @return the leftAdornment
	 */
	public LinkAdornment getLeftAdornment() {
		return leftAdornment;
	}

	/**
	 * @return the rightAdornment
	 */
	public LinkAdornment getRightAdornment() {
		return rightAdornment;
	}

	@Override
	public LinkStyle getLinkStyle() {
		return SOLID;
	}

}
