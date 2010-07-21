package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLComponent;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLRelation;

/**
 * This enumeration list all text part of a RelationLinkArtifact
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public enum RelationLinkArtifactPart {
	/**
	 * Left end cardinality
	 */
	LEFT_CARDINALITY("Cardinality", true) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getLeftCardinality();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setLeftCardinality(text);
		}
	},
	/**
	 * Left end constraint
	 */
	LEFT_CONSTRAINT("Constraint", true) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getLeftConstraint();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setLeftConstraint(text);
		}
	},
	/**
	 * Left end role
	 */
	LEFT_ROLE("Role", true) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getLeftRole();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setLeftRole(text);
		}
	},
	/**
	 * Left stereotype
	 */
	LEFT_STEREOTYPE("Stereotype", true) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getLeftStereotype();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setLeftStereotype(text);
		}
	},
	/**
	 * The relation name
	 */
	NAME("Name", false) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getName();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setName(text);
		}
	},

	/**
	 * Right end cardinality
	 */
	RIGHT_CARDINALITY("Cardinality", false) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getRightCardinality();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setRightCardinality(text);
		}
	},
	/**
	 * Right end constraint
	 */
	RIGHT_CONSTRAINT("Constraint", false) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getRightConstraint();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setRightConstraint(text);
		}
	},
	/**
	 * Right end role
	 */
	RIGHT_ROLE("Role", false) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getRightRole();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setRightRole(text);
		}
	},
	/**
	 * Left end role
	 */
	RIGHT_STEREOTYPE("Stereotype", false) {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation)
		 */
		@Override
		public String getText(final UMLRelation relation) {
			return relation.getRightStereotype();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com
		 * .objetdirect.gwt.umlapi.client.umlcomponents .Relation, java.lang.String)
		 */
		@Override
		public void setText(final UMLRelation relation, final String text) {
			relation.setRightStereotype(text);
		}
	};

	private boolean isLeft;

	private String name;

	/** Default constructor ONLY for gwt-rpc serialization. */
	private RelationLinkArtifactPart() {
	}

	private RelationLinkArtifactPart(final String name, final boolean isLeft) {
		this.name = name;
		this.isLeft = isLeft;
	}

	/**
	 * Getter for the text contained by the graphical object for a part
	 * 
	 * @param relation
	 *            The relation {@link UMLComponent} this enumeration is about
	 * @return the text corresponding to this part
	 */
	public abstract String getText(UMLRelation relation);

	/**
	 * Determine if this part is "Left"
	 * 
	 * @return <ul>
	 *         <li><b>True</b> if it is actually "Left"</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isLeft() {
		return isLeft;
	}

	/**
	 * Setter to affect a text to a part of the relation {@link UMLComponent}
	 * 
	 * @param relation
	 *            The relation {@link UMLComponent} this enumeration is about
	 * @param text
	 *            The text corresponding to this part
	 */
	public abstract void setText(UMLRelation relation, String text);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}