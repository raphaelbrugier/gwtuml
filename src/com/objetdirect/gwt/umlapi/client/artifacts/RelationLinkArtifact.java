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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLComponent;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation.RelationKind;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;

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
     * @param relationKind 
     */
    public RelationLinkArtifact(NodeArtifact nodeArtifact1, NodeArtifact nodeArtifact2, final RelationKind relationKind) {
	super(nodeArtifact1, nodeArtifact2);
	this.leftNodeArtifact = nodeArtifact1;
	this.rightNodeArtifact = nodeArtifact2;
	if(relationKind == RelationKind.NOTE || relationKind == RelationKind.CLASSRELATION) Log.error("Making a relation artifact for : " + relationKind.getName());
	this.relation = new UMLRelation(relationKind);	
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
    protected Point leftDirectionPoint = Point.getOrigin();
    protected Point rightDirectionPoint = Point.getOrigin();
    protected Point nameAnchorPoint = Point.getOrigin();
    private NodeArtifact rightNodeArtifact;
    private NodeArtifact leftNodeArtifact;
    /**
     * Setter of a part text
     * @param part The {@link RelationLinkArtifactPart} in which the text is to be updated 
     * @param newContent The new text to set for this part 
     */
    public void setPartContent(final RelationLinkArtifactPart part,
	    final String newContent) {
	part.setText(this.relation, newContent);
    }


    protected GfxObject getLine() {
	if(this.isSelfLink) {
	    return getSelfLine();
	}
	return getPeerLine();

    }


    private GfxObject getPeerLine() {
	return OptionsManager.get("AngularLinks") == 1 ? getPeerAngularLine() : getPeerRightLine();
    }

    private GfxObject getSelfLine() {
	return OptionsManager.get("AngularLinks") == 1 ? getSelfAngularLine() : getSelfRightLine();
    }

    private GfxObject getSelfRightLine() {
	this.leftPoint = this.leftNodeArtifact.getCenter().translate(0, -this.leftNodeArtifact.getHeight() / 2);
	this.rightPoint = this.leftNodeArtifact.getCenter().translate(this.leftNodeArtifact.getWidth() / 2, 0);
	GfxObject line;
	Point edge = new Point(this.rightPoint.getX(), this.leftPoint.getY());
	Point pointsDiff = Point.substract(this.rightPoint, this.leftPoint);
	if(pointsDiff.getX() < pointsDiff.getY()) {
	    this.rightPoint.translate(0, pointsDiff.getX() - pointsDiff.getY());
	    line = GfxManager.getPlatform().buildCircle(pointsDiff.getX());
	} else {
	    this.leftPoint.translate(pointsDiff.getX() - pointsDiff.getY(), 0);
	    line = GfxManager.getPlatform().buildCircle(pointsDiff.getY());
	}
	Point rightShiftedPoint =  Point.add(this.rightPoint, new Point(OptionsManager.get("ReflexivePathXGap"), 0));
	Point leftShiftedPoint = Point.add(this.leftPoint, new Point(0, -OptionsManager.get("ReflexivePathYGap")));
	this.leftDirectionPoint = leftShiftedPoint;
	this.rightDirectionPoint = rightShiftedPoint;
	this.nameAnchorPoint = new Point((this.leftPoint.getX() + this.rightPoint.getX() + OptionsManager.get("ReflexivePathXGap"))/2, this.leftPoint.getY() - OptionsManager.get("ReflexivePathYGap"));
	GfxManager.getPlatform().translate(line, edge);
	GfxManager.getPlatform().setOpacity(line, 0, true);

	return line;
    }

    private GfxObject getSelfAngularLine() {
	this.leftPoint = this.leftNodeArtifact.getCenter().translate(0, -this.leftNodeArtifact.getHeight() / 2);
	this.rightPoint = this.leftNodeArtifact.getCenter().translate(this.leftNodeArtifact.getWidth() / 2, 0);
	GfxObject line = GfxManager.getPlatform().buildPath();
	Point rightShiftedPoint =  Point.add(this.rightPoint, new Point(OptionsManager.get("ReflexivePathXGap"), 0));
	Point leftShiftedPoint = Point.add(this.leftPoint, new Point(0, -OptionsManager.get("ReflexivePathYGap")));
	this.leftDirectionPoint = leftShiftedPoint;
	this.rightDirectionPoint = rightShiftedPoint;
	GfxManager.getPlatform().moveTo(line, this.leftPoint);
	GfxManager.getPlatform().lineTo(line, leftShiftedPoint);
	GfxManager.getPlatform().lineTo(line, new Point(rightShiftedPoint.getX(), leftShiftedPoint.getY()));
	GfxManager.getPlatform().lineTo(line, rightShiftedPoint);
	GfxManager.getPlatform().lineTo(line, this.rightPoint);
	GfxManager.getPlatform().setOpacity(line, 0, true);
	this.nameAnchorPoint = new Point((this.leftPoint.getX() + this.rightPoint.getX() + OptionsManager.get("ReflexivePathXGap"))/2, this.leftPoint.getY() - OptionsManager.get("ReflexivePathYGap"));
	return line;
    }

    private GfxObject getPeerRightLine() {
	
	ArrayList<Point> points = GeometryManager.getPlatform().getLineBetween(this.leftNodeArtifact, this.rightNodeArtifact);
	this.leftPoint = points.get(0);
	this.rightPoint = points.get(1);
	
	GfxObject line;
	this.nameAnchorPoint = Point.getMiddleOf(this.leftPoint, this.rightPoint);
	if(this.order == 0) {
	    line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
	    this.leftDirectionPoint = this.rightPoint;
	    this.rightDirectionPoint = this.leftPoint;
	} else {
	    Point curveControl = GeometryManager.getPlatform().getShiftedCenter(this.leftPoint, this.rightPoint, 50 * ((this.order + 1)/2) * ((this.order % 2) == 0 ? -1 : 1));
	    this.leftDirectionPoint = curveControl;
	    this.rightDirectionPoint = curveControl;
	    line = GfxManager.getPlatform().buildPath();
	    GfxManager.getPlatform().moveTo(line, this.leftPoint);
	    GfxManager.getPlatform().curveTo(line, this.rightPoint, curveControl);
	    GfxManager.getPlatform().setOpacity(line, 0, true);
	    this.nameAnchorPoint = Point.getMiddleOf(curveControl, this.nameAnchorPoint);
	}

	return line;
    }

    private GfxObject getPeerAngularLine() {
	this.leftPoint = this.leftNodeArtifact.getCenter();
	this.rightPoint = this.rightNodeArtifact.getCenter();
	if(this.leftPoint.getY() > this.rightPoint.getY()) {
	    int sign = this.leftPoint.getX() > this.rightPoint.getX() ? -1 : 1;
	    this.leftPoint.translate(sign * this.leftNodeArtifact.getWidth()/2, 0);
	    this.rightPoint.translate(0, this.rightNodeArtifact.getHeight()/2);
	} else {
	    int sign = this.rightPoint.getX() > this.leftPoint.getX() ? -1 : 1;
	    this.rightPoint.translate(sign * this.rightNodeArtifact.getWidth()/2, 0);
	    this.leftPoint.translate(0, this.leftNodeArtifact.getHeight()/2);
	}
	GfxObject line = GfxManager.getPlatform().buildPath();

	if(this.leftPoint.getY() < this.rightPoint.getY()) {
	    this.leftDirectionPoint = this.rightDirectionPoint = new Point(this.leftPoint.getX(), this.rightPoint.getY());
	    this.nameAnchorPoint = Point.getMiddleOf(this.leftDirectionPoint, this.rightPoint);
	} else {
	    this.leftDirectionPoint = this.rightDirectionPoint = new Point(this.rightPoint.getX(), this.leftPoint.getY());
	    this.nameAnchorPoint = Point.getMiddleOf(this.leftDirectionPoint, this.leftPoint);
	}
	GfxManager.getPlatform().moveTo(line, this.leftPoint);
	GfxManager.getPlatform().lineTo(line, this.leftDirectionPoint);
	GfxManager.getPlatform().lineTo(line, this.rightPoint);
	GfxManager.getPlatform().setOpacity(line, 0, true);

	return line;
    }


}
