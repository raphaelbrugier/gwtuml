/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.Collections;
import java.util.HashMap;

import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLComponent;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public abstract class RelationLinkArtifact extends LinkArtifact {
    /**
     * Constructor of RelationLinkArtifact
     *
     * @param nodeArtifact1 
     * @param nodeArtifact2
     */
    public RelationLinkArtifact(NodeArtifact nodeArtifact1, NodeArtifact nodeArtifact2) {
	super(nodeArtifact1, nodeArtifact2);
    }

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
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final UMLRelation relation) {
		return relation.getLeftCardinality();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
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
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final UMLRelation relation) {
		return relation.getLeftConstraint();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
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
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final UMLRelation relation) {
		return relation.getLeftRole();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final UMLRelation relation, final String text) {
		relation.setLeftRole(text);
	    }
	},
	/**
	 * The relation name
	 */
	NAME("Name", false) {
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final UMLRelation relation) {
		return relation.getName();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
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
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final UMLRelation relation) {
		return relation.getRightCardinality();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
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
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final UMLRelation relation) {
		return relation.getRightConstraint();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
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
	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#getText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation)
	     */
	    @Override
	    public String getText(final UMLRelation relation) {
		return relation.getRightRole();
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact.RelationLinkArtifactPart#setText(com.objetdirect.gwt.umlapi.client.umlcomponents.Relation, java.lang.String)
	     */
	    @Override
	    public void setText(final UMLRelation relation, final String text) {
		relation.setRightRole(text);
	    }
	};

	private static HashMap<GfxObject, RelationLinkArtifactPart> textGfxObject = new HashMap<GfxObject, RelationLinkArtifactPart>();

	/**
	 * Getter for the part represented by the graphical object
	 * 
	 * @param gfxObjectText The graphical object to retrieve the relationLinkArtifactPart
	 * @return The RelationLinkArtifactPart represented by the graphical object
	 */
	public static RelationLinkArtifactPart getPartForGfxObject(final GfxObject gfxObjectText) {
	    return textGfxObject.get(gfxObjectText);
	}

	/**
	 * Setter of the relation between a RelationLinkArtifactPart and his graphical object
	 * 
	 * @param gfxObjectText The graphical object representing the relationLinkArtifactPart
	 * @param relationLinkArtifactPart the relationLinkArtifactPart represented by gfxObjecttext
	 */
	public static void setGfxObjectTextForPart(final GfxObject gfxObjectText, final RelationLinkArtifactPart relationLinkArtifactPart) {
	    textGfxObject.put(gfxObjectText, relationLinkArtifactPart);
	}

	private boolean isLeft;

	private String name;

	private RelationLinkArtifactPart(final String name, final boolean isLeft) {
	    this.name = name;
	    this.isLeft = isLeft;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	    return this.name;
	}

	/**
	 * Getter for the text contained by the graphical object for a part
	 * 
	 * @param relation The relation {@link UMLComponent} this enumeration is about
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
	    return this.isLeft;
	}

	/**
	 * Setter to affect a text to a part of the relation {@link UMLComponent}
	 * 
	 * @param relation The relation {@link UMLComponent} this enumeration is about
	 * @param text The text corresponding to this part
	 */
	public abstract void setText(UMLRelation relation, String text);
    }
    protected UMLRelation relation;    
    
    /**
     * Setter of a part text
     * @param part The {@link RelationLinkArtifactPart} in which the text is to be updated 
     * @param newContent The new text to set for this part 
     */
    public void setPartContent(final RelationLinkArtifactPart part,
	    final String newContent) {
	part.setText(this.relation, newContent);
    }

    
}
