/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;
/**
 * @author  florian
 */
public class Relation {
	public enum RelationKind {
	    AGGREGATION("Aggregation"),
	    ASSOCIATION("Association"),
	    COMPOSITION("Composition"),
	    DEPENDENCY("Dependency"),
	    GENERALIZATION("Generalization"),
	    REALIZATION("Realization");
	    
	    private String name;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        private RelationKind(String name) {
	        this.name = name;
	    }
	}
	private RelationKind relationKind;
	private String leftCardinality;	 
	private String leftConstraint;	 
	private String leftRole;	 
	private String name;	 
	private String rightCardinality;	 
	private String rightConstraint;	 
	private String rightRole;

	public Relation(RelationKind relationKind) {
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
	 * @return  the leftCardinality
	 * 
	 */
	public String getLeftCardinality() {
		return leftCardinality;
	}
 
	public void setLeftCardinality(String leftCardinality) {
		this.leftCardinality = leftCardinality;
	}
	/**
	 * @return  the leftConstraint
	 * 
	 */
	public String getLeftConstraint() {
		return leftConstraint;
	}
 
	public void setLeftConstraint(String leftConstraint) {
		this.leftConstraint = leftConstraint;
	}
	/**
	 * @return  the leftRole
	 * 
	 */
	public String getLeftRole() {
		return leftRole;
	}
 
	public void setLeftRole(String leftRole) {
		this.leftRole = leftRole;
	}
	/**
	 * @return  the name
	 * 
	 */
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return  the rightCardinality
	 * 
	 */
	public String getRightCardinality() {
		return rightCardinality;
	}
 
	public void setRightCardinality(String rightCardinality) {
		this.rightCardinality = rightCardinality;
	}
	/**
	 * @return  the rightConstraint
	 * 
	 */
	public String getRightConstraint() {
		return rightConstraint;
	}
 
	public void setRightConstraint(String rightConstraint) {
		this.rightConstraint = rightConstraint;
	}
	/**
	 * @return  the rightRole
	 * 
	 */
	public String getRightRole() {
		return rightRole;
	}
 
	public void setRightRole(String rightRole) {
		this.rightRole = rightRole;
	}

    /**
     * @return the relationKind
     */
    public RelationKind getRelationKind() {
        return relationKind;
    }

    /**
     * @param relationKind the relationKind to set
     */
    public void setRelationKind(RelationKind relationKind) {
        this.relationKind = relationKind;
    }
}
