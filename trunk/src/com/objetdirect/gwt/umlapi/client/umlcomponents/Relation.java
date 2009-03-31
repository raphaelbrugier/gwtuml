/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;
/**
 * @author  florian
 */
public class Relation {
	/**
	 * @uml.property  name="leftCardinality"
	 */
	private String leftCardinality = "0..*";
	/**
	 * @uml.property  name="leftConstraint"
	 */
	private String leftConstraint;
	/**
	 * @uml.property  name="leftRole"
	 */
	private String leftRole;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="rightCardinality"
	 */
	private String rightCardinality = "0..*";
	/**
	 * @uml.property  name="rightConstraint"
	 */
	private String rightConstraint;
	/**
	 * @uml.property  name="rightRole"
	 */
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
		this.leftConstraint = "";
		this.leftRole = "";
		this.name = "";
		this.rightCardinality = "0..*";
		this.rightConstraint = "";
		this.rightRole = "";
	}
	
	
	/**
	 * @return  the leftCardinality
	 * @uml.property  name="leftCardinality"
	 */
	public String getLeftCardinality() {
		return leftCardinality;
	}
	/**
	 * @param leftCardinality  the leftCardinality to set
	 * @uml.property  name="leftCardinality"
	 */
	public void setLeftCardinality(String leftCardinality) {
		this.leftCardinality = leftCardinality;
	}
	/**
	 * @return  the leftConstraint
	 * @uml.property  name="leftConstraint"
	 */
	public String getLeftConstraint() {
		return leftConstraint;
	}
	/**
	 * @param leftConstraint  the leftConstraint to set
	 * @uml.property  name="leftConstraint"
	 */
	public void setLeftConstraint(String leftConstraint) {
		this.leftConstraint = leftConstraint;
	}
	/**
	 * @return  the leftRole
	 * @uml.property  name="leftRole"
	 */
	public String getLeftRole() {
		return leftRole;
	}
	/**
	 * @param leftRole  the leftRole to set
	 * @uml.property  name="leftRole"
	 */
	public void setLeftRole(String leftRole) {
		this.leftRole = leftRole;
	}
	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return  the rightCardinality
	 * @uml.property  name="rightCardinality"
	 */
	public String getRightCardinality() {
		return rightCardinality;
	}
	/**
	 * @param rightCardinality  the rightCardinality to set
	 * @uml.property  name="rightCardinality"
	 */
	public void setRightCardinality(String rightCardinality) {
		this.rightCardinality = rightCardinality;
	}
	/**
	 * @return  the rightConstraint
	 * @uml.property  name="rightConstraint"
	 */
	public String getRightConstraint() {
		return rightConstraint;
	}
	/**
	 * @param rightConstraint  the rightConstraint to set
	 * @uml.property  name="rightConstraint"
	 */
	public void setRightConstraint(String rightConstraint) {
		this.rightConstraint = rightConstraint;
	}
	/**
	 * @return  the rightRole
	 * @uml.property  name="rightRole"
	 */
	public String getRightRole() {
		return rightRole;
	}
	/**
	 * @param rightRole  the rightRole to set
	 * @uml.property  name="rightRole"
	 */
	public void setRightRole(String rightRole) {
		this.rightRole = rightRole;
	}
}
