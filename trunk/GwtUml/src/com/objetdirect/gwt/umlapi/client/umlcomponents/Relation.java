/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;


/**
 * @author florian
 *
 */
public class Relation {
	private String leftCardinality = "0..*";
	private String leftConstraint;
	private String leftRole;
	private String name;
	private String rightCardinality = "0..*";
	private String rightConstraint;
	private String rightRole;
	/**
	 * @param leftCardinality
	 * @param leftConstraint
	 * @param leftRole
	 * @param name
	 * @param rightCardinality
	 * @param rightConstraint
	 * @param rightRole
	 */
	public Relation() {
		this.leftCardinality = "0..*";
		this.leftConstraint = null;
		this.leftRole = null;
		this.name = null;
		this.rightCardinality = "0..*";
		this.rightConstraint = null;
		this.rightRole = null;
	}

	
	
	/**
	 * @return the leftCardinality
	 */
	public String getLeftCardinality() {
		return leftCardinality;
	}
	/**
	 * @param leftCardinality the leftCardinality to set
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
	 * @param leftConstraint the leftConstraint to set
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
	 * @param leftRole the leftRole to set
	 */
	public void setLeftRole(String leftRole) {
		this.leftRole = leftRole;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the rightCardinality
	 */
	public String getRightCardinality() {
		return rightCardinality;
	}
	/**
	 * @param rightCardinality the rightCardinality to set
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
	 * @param rightConstraint the rightConstraint to set
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
	 * @param rightRole the rightRole to set
	 */
	public void setRightRole(String rightRole) {
		this.rightRole = rightRole;
	}

}
