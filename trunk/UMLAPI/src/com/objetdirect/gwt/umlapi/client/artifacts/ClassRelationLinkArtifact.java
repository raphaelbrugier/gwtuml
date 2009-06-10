/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.editors.RelationFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;

/**
 * This class represents any relation artifact between two classes
 *  
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassRelationLinkArtifact extends RelationLinkArtifact {
    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */


    protected GfxObject arrowVirtualGroup;
    protected ClassArtifact leftClassArtifact;
    protected GfxObject line;
    protected ClassArtifact rightClassArtifact;
    protected GfxObject textVirtualGroup;
    private int current_delta;
    private final HashMap<RelationLinkArtifactPart, GfxObject> gfxObjectPart = new HashMap<RelationLinkArtifactPart, GfxObject>();

    /**
     * Constructor of {@link ClassRelationLinkArtifact}
     * 
     * @param left The left {@link ClassArtifact} of the relation
     * @param right The right {@link ClassArtifact} of the relation
     * @param relationKind The kind of relation this link is.
     */
    public ClassRelationLinkArtifact(final ClassArtifact left, final ClassArtifact right, final LinkKind relationKind) {
	super(left, right, relationKind);

	this.leftClassArtifact = left;
	left.addDependency(this, right);
	this.rightClassArtifact = right;
	if (right != left) {
	    right.addDependency(this, left);
	}
	else {
	    this.isSelfLink = true;
	}
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final RelationLinkArtifactPart editPart = RelationLinkArtifactPart
	.getPartForGfxObject(editedGfxObject);
	if (editPart == null) {
	    edit(RelationLinkArtifactPart.NAME);
	} else {
	    final RelationFieldEditor editor = new RelationFieldEditor(this.canvas,
		    this, editPart);
	    editor
	    .startEdition(editPart.getText(this.relation), GfxManager
		    .getPlatform().getLocationFor(editedGfxObject).getX(), GfxManager
		    .getPlatform().getLocationFor(editedGfxObject).getY(), GfxManager.getPlatform()
		    .getTextWidthFor(editedGfxObject)
		    + OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding"), false, true);
	}
    }

    /**
     * Request an edition on a {@link RelationLinkArtifact.RelationLinkArtifactPart} and set if the text is empty a default text
     * 
     * @param part The {@link RelationLinkArtifact.RelationLinkArtifactPart} to edit
     */
    public void edit(final RelationLinkArtifactPart part) {
	String defaultText;
	switch (part) {
	case NAME:
	    String name = part.getText(this.relation);
	    if(name == null || name.equals("")) {
		defaultText = this.leftClassArtifact.getName() + "-"
		+ this.rightClassArtifact.getName();
	    }
	    else {
		defaultText = name;
	    }
	    break;
	case LEFT_CARDINALITY:
	case RIGHT_CARDINALITY:
	    defaultText = "0..*";
	    break;
	case LEFT_CONSTRAINT:
	case RIGHT_CONSTRAINT:
	    defaultText = "{union}";
	    break;
	case LEFT_ROLE:
	case RIGHT_ROLE:
	    defaultText = "role";
	    break;
	default:
	    defaultText = "?";

	}
	part.setText(this.relation, defaultText);
	rebuildGfxObject();
	edit(this.gfxObjectPart.get(part));
    }

    /**
     * Getter for the left {@link ClassArtifact} of this relation 
     * 
     * @return the left {@link ClassArtifact} of this relation
     */
    public ClassArtifact getLeftClassArtifact() {
	return this.leftClassArtifact;
    }

    /**
     * Getter for the right {@link ClassArtifact} of this relation 
     * 
     * @return the right {@link ClassArtifact} of this relation
     */
    public ClassArtifact getRightClassArtifact() {
	return this.rightClassArtifact;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact
     * #getRightMenu()
     */
    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName(this.relation.getLinkKind().getName() + " "
		+ this.leftClassArtifact.getName() + " "
		+ this.relation.getLeftAdornment().getShape().getIdiom() + "-"
		+ this.relation.getRightAdornment().getShape().getIdiom(true) + " "
		+ this.rightClassArtifact.getName());
	final MenuBar leftSide = new MenuBar(true);
	final MenuBar rightSide = new MenuBar(true);
	for (final RelationLinkArtifactPart relationLinkArtifactPart : RelationLinkArtifactPart
		.values()) {
	    final MenuBar editDelete = new MenuBar(true);
	    if (!relationLinkArtifactPart.getText(this.relation).equals("")) {
		editDelete.addItem("Edit",
			editCommand(relationLinkArtifactPart));
		editDelete.addItem("Delete",
			deleteCommand(relationLinkArtifactPart));
	    } else {
		editDelete.addItem("Create",
			createCommand(relationLinkArtifactPart));
	    }
	    if (relationLinkArtifactPart.isLeft()) {
		leftSide.addItem(relationLinkArtifactPart.toString(), editDelete);
	    } else {
		if (relationLinkArtifactPart != RelationLinkArtifactPart.NAME) {
		    rightSide.addItem(relationLinkArtifactPart.toString(),
			    editDelete);
		} else {
		    rightMenu.addItem(relationLinkArtifactPart.toString(),
			    editDelete);
		}
	    }
	}
	switch(this.relation.getLeftAdornment()) {
	case NONE:
	case WIRE_ARROW:
	case WIRE_CROSS:
	    MenuBar leftNavigability = new MenuBar(true);
	    leftNavigability.addItem("Navigable", setNavigabilityCommand(this.relation, true, true));
	    leftNavigability.addItem("Not Navigable", setNavigabilityCommand(this.relation, true, false));
	    leftNavigability.addItem("Unknown", setNavigabilityCommand(this.relation, true));
	    leftSide.addItem("Navigability", leftNavigability);
	}
	switch(this.relation.getRightAdornment()) {
	case NONE:
	case WIRE_ARROW:
	case WIRE_CROSS:
	    MenuBar rightNavigability = new MenuBar(true);
	    rightNavigability.addItem("Navigable", setNavigabilityCommand(this.relation, false, true));
	    rightNavigability.addItem("Not Navigable", setNavigabilityCommand(this.relation, false, false));
	    rightNavigability.addItem("Unknown", setNavigabilityCommand(this.relation, false));
	    rightSide.addItem("Navigability", rightNavigability);
	}
	rightMenu.addItem(this.leftClassArtifact.getName() + " side", leftSide);
	rightMenu.addItem(this.rightClassArtifact.getName() + " side", rightSide);
	rightMenu.addItem("Reverse", reverseCommand(this.relation));
	final MenuBar linkSubMenu = new MenuBar(true);
	for (final LinkKind relationKind : LinkKind.values()) {
	    if(relationKind.isForDiagram(UMLDiagram.Type.CLASS)) { 
		linkSubMenu.addItem(relationKind.getName(), changeToCommand(this.relation, relationKind));
	    }
	}
	rightMenu.addItem("Change to", linkSubMenu);
	return rightMenu;
    }



    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
     */
    @Override
    public void removeCreatedDependency() {
	this.leftClassArtifact.removeDependency(this);
	this.rightClassArtifact.removeDependency(this);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
     */
    @Override
    protected void select() {
	super.select();

	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getClassRelationHighlightedForegroundColor(), 2);
	GfxManager.getPlatform().setStroke(this.arrowVirtualGroup,
		ThemeManager.getTheme().getClassRelationHighlightedForegroundColor(), 2);
    }
    /**
     * Setter for the left and right cardinalities in {@link UMLRelation}
     * This does not update the graphical object
     * 
     * @param leftCardinality The left cardinality text to be set
     * @param rightCardinality The right cardinality text to be set
     */
    public void setCardinalities(final String leftCardinality, final String rightCardinality) {
	this.relation.setLeftCardinality(leftCardinality);
	this.relation.setRightCardinality(rightCardinality);
    }

    /**
     * Setter for the leftCardinality in {@link UMLRelation}
     * This does not update the graphical object
     * 
     * @param leftCardinality The leftCardinality text to be set
     */
    public void setLeftCardinality(final String leftCardinality) {
	this.relation.setLeftCardinality(leftCardinality);
    }

    /**
     * Setter for the leftConstraint in {@link UMLRelation}
     * This does not update the graphical object
     * 
     * @param leftConstraint The leftConstraint text to be set
     */
    public void setLeftConstraint(final String leftConstraint) {
	this.relation.setLeftConstraint(leftConstraint);
    }

    /**
     * Setter for the leftRole in {@link UMLRelation}
     * This does not update the graphical object
     * 
     * @param leftRole The leftRole text to be set
     */
    public void setLeftRole(final String leftRole) {
	this.relation.setLeftRole(leftRole);
    }

    /**
     * Setter for the name in {@link UMLRelation}
     * This does not update the graphical object
     * 
     * @param name The name text to be set
     */
    public void setName(final String name) {
	this.relation.setName(name);
    }

    /**
     * Reset the navigability of the left side to unknown <br />
     * The left side must not be a generalization, realization, aggregation or composition otherwise this method do nothing
     */
    public void resetLeftNavigability() {
	if(this.relation.getLeftAdornment().isNavigabilityAdornment()) {
	    this.relation.setLeftAdornment(LinkAdornment.NONE);
	}
    }
    /**
     * Reset the navigability of the right side to unknown <br />
     * The right side must not be a generalization, realization, aggregation or composition otherwise this method do nothing
     */
    public void resetRightNavigability() {
	if(this.relation.getRightAdornment().isNavigabilityAdornment()) {
	    this.relation.setRightAdornment(LinkAdornment.NONE);
	}
    }
    /**
     * Set the state of left navigability <br /> 
     * The left side must not be a generalization, realization, aggregation or composition otherwise this method do nothing <br />
     * To set the unknown state see {@link ClassRelationLinkArtifact#resetLeftNavigability()}
     * 
     * @param isNavigable If true set the link's side to navigable otherwise set it to NOT navigable
     * 
     */
    public void setLeftNavigability(final boolean isNavigable) {
	if(this.relation.getLeftAdornment().isNavigabilityAdornment()) {
	    this.relation.setLeftAdornment(isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS);
	}

    }
    /**
     * Set the state of right navigability <br /> 
     * The right side must not be a generalization, realization, aggregation or composition otherwise this method do nothing <br />
     * To set the unknown state see {@link ClassRelationLinkArtifact#resetRightNavigability()}
     * 
     * @param isNavigable If true set the link's side to navigable otherwise set it to NOT navigable
     * 
     */
    public void setRightNavigability(final boolean isNavigable) {
	if(this.relation.getRightAdornment().isNavigabilityAdornment()) {
	    this.relation.setRightAdornment(isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS);
	}
    }
    /**
     * Setter for the rightCardinality in {@link UMLRelation}
     * This does not update the graphical object
     * 
     * @param rightCardinality The rightCardinality text to be set
     */
    public void setRightCardinality(final String rightCardinality) {
	this.relation.setRightCardinality(rightCardinality);
    }

    /**
     * Setter for the rightConstraint in {@link UMLRelation}
     * This does not update the graphical object
     * 
     * @param rightConstraint The rightConstraint text to be set
     */
    public void setRightConstraint(final String rightConstraint) {
	this.relation.setRightConstraint(rightConstraint);
    }

    /**
     * Setter for the rightRole in {@link UMLRelation}
     * This does not update the graphical object
     * 
     * @param rightRole The rightRole text to be set
     */
    public void setRightRole(final String rightRole) {
	this.relation.setRightRole(rightRole);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
     */
    @Override
    public void unselect() {
	super.unselect();
	GfxManager.getPlatform().setStroke(this.line,
		ThemeManager.getTheme().getClassRelationForegroundColor(), 1);
	GfxManager.getPlatform().setStroke(this.arrowVirtualGroup,
		ThemeManager.getTheme().getClassRelationForegroundColor(), 1);
    }



    int getTextX(final GfxObject text, final boolean isLeft) {
	Point relative_point1 = this.leftPoint;
	Point relative_point2 = this.rightPoint;
	final int textWidth = GfxManager.getPlatform().getTextWidthFor(text);
	if (!isLeft) {
	    relative_point1 = this.rightPoint;
	    relative_point2 = this.leftPoint;
	}
	switch (isLeft ? this.leftDirection: this.rightDirection) {
		case LEFT:
		    return relative_point1.getX() - textWidth
		    - OptionsManager.get("RectangleLeftPadding");
		case RIGHT:
		    return relative_point1.getX()
		    + OptionsManager.get("RectangleRightPadding");
		case UP:
		case DOWN:
		case UNKNOWN:
		    if (relative_point1.getX() < relative_point2.getX()) {
			return relative_point1.getX() - textWidth
			- OptionsManager.get("RectangleLeftPadding");
		    }
		    return relative_point1.getX()
		    + OptionsManager.get("RectangleRightPadding");
	}
	return 0;
    }

    int getTextY(final GfxObject text, final boolean isLeft) {
	Point relative_point1 = this.leftPoint;
	Point relative_point2 = this.rightPoint;
	if (!isLeft) {
	    relative_point1 = this.rightPoint;
	    relative_point2 = this.leftPoint;
	}
	final int textHeight = GfxManager.getPlatform().getTextHeightFor(text);
	final int delta = this.current_delta;
	this.current_delta += 8; // TODO : Fix Height
	switch (isLeft ? this.leftDirection: this.rightDirection) {
		case LEFT:
		case RIGHT:
		    if (relative_point1.getY() > relative_point2.getY()) {
			return relative_point1.getY()
			+ OptionsManager.get("RectangleBottomPadding") + delta;
		    }
		    return relative_point1.getY() - textHeight
		    - OptionsManager.get("RectangleTopPadding") - delta;
		case UP:
		    return relative_point1.getY() - textHeight
		    - OptionsManager.get("RectangleTopPadding") - delta;
		case DOWN:
		case UNKNOWN:
		    return relative_point1.getY()
		    + OptionsManager.get("RectangleBottomPadding") + delta;
	}
	return 0;
    }

    @Override
    protected void buildGfxObject() {
	if(this.isTheOneRebuilding) return;
	this.gfxObjectPart.clear();
	
	this.line = getLine();

	GfxManager.getPlatform().setStroke(this.line, ThemeManager.getTheme().getClassRelationForegroundColor(), 1);
	GfxManager.getPlatform().setStrokeStyle(this.line, this.relation.getLinkStyle().getGfxStyle());
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.line);

	// Making arrows group :
	this.arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.arrowVirtualGroup);
	GfxObject leftArrow = GeometryManager.getPlatform().buildAdornment(this.leftPoint, this.leftDirectionPoint, this.relation.getLeftAdornment());
	GfxObject rightArrow = GeometryManager.getPlatform().buildAdornment(this.rightPoint, this.rightDirectionPoint, this.relation.getRightAdornment());
	
	if(leftArrow != null) { 
	    GfxManager.getPlatform().addToVirtualGroup(this.arrowVirtualGroup, leftArrow);
	}
	if(rightArrow != null) {
	    GfxManager.getPlatform().addToVirtualGroup(this.arrowVirtualGroup, rightArrow);
	}
	// Making the text group :
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);
	if (!this.relation.getName().equals("")) {
	    Log.trace("Creating name");
	    final GfxObject nameGfxObject = GfxManager.getPlatform().buildText(this.relation.getName(), this.nameAnchorPoint);
	    GfxManager.getPlatform().setFont(nameGfxObject, OptionsManager.getSmallFont());
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup, nameGfxObject);
	    GfxManager.getPlatform().setStroke(nameGfxObject,
		    ThemeManager.getTheme().getClassRelationBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(nameGfxObject,
		    ThemeManager.getTheme().getClassRelationForegroundColor());
	    GfxManager.getPlatform().translate(nameGfxObject, new Point(-GfxManager.getPlatform().getTextWidthFor(nameGfxObject) / 2, 0));
	    RelationLinkArtifactPart.setGfxObjectTextForPart(nameGfxObject, RelationLinkArtifactPart.NAME);
	    this.gfxObjectPart.put(RelationLinkArtifactPart.NAME, nameGfxObject);
	}
	
	
	this.current_delta = 0;
	if (!this.relation.getLeftCardinality().equals("")) {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getLeftCardinality(),
			    RelationLinkArtifactPart.LEFT_CARDINALITY));
	}
	if (!this.relation.getLeftConstraint().equals("")) {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getLeftConstraint(),
			    RelationLinkArtifactPart.LEFT_CONSTRAINT));
	}
	if (!this.relation.getLeftRole().equals("")) {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getLeftRole(),
			    RelationLinkArtifactPart.LEFT_ROLE));
	}
	this.current_delta = 0;
	if (!this.relation.getRightCardinality().equals("")) {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getRightCardinality(),
			    RelationLinkArtifactPart.RIGHT_CARDINALITY));
	}
	if (!this.relation.getRightConstraint().equals("")) {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getRightConstraint(),
			    RelationLinkArtifactPart.RIGHT_CONSTRAINT));
	}
	if (!this.relation.getRightRole().equals("")) {
	    GfxManager.getPlatform().addToVirtualGroup(
		    this.textVirtualGroup,
		    createText(this.relation.getRightRole(),
			    RelationLinkArtifactPart.RIGHT_ROLE));
	}

	GfxManager.getPlatform().moveToBack(this.gfxObject);
    }

    private Command createCommand(
	    final RelationLinkArtifactPart relationArtifactPart) {
	return new Command() {
	    public void execute() {
		edit(relationArtifactPart);
	    }
	};
    }

    private GfxObject createText(final String text,
	    final RelationLinkArtifactPart part) {
	final GfxObject textGfxObject = GfxManager.getPlatform()
	.buildText(text, Point.getOrigin());
	GfxManager.getPlatform().setFont(textGfxObject, OptionsManager.getSmallFont());
	GfxManager.getPlatform().setStroke(textGfxObject,
		ThemeManager.getTheme().getClassRelationBackgroundColor(), 0);
	GfxManager.getPlatform().setFillColor(textGfxObject,
		ThemeManager.getTheme().getClassRelationForegroundColor());
	if (this.leftClassArtifact != this.rightClassArtifact) {
	    Log.trace("Creating text : " + text + " at "
		    + getTextX(textGfxObject, part.isLeft()) + " : "
		    + getTextY(textGfxObject, part.isLeft()));
	    GfxManager.getPlatform().translate(textGfxObject, new Point(
		    getTextX(textGfxObject, part.isLeft()),
		    getTextY(textGfxObject, part.isLeft())));
	} else {	    
	    if(part.isLeft()) {
		GfxManager.getPlatform().translate(textGfxObject, Point.add(this.leftClassArtifact.getCenter(), new Point(OptionsManager.get("ArrowWidth") / 2 + OptionsManager.get("TextLeftPadding"), -(this.leftClassArtifact.getHeight() + OptionsManager.get("ReflexivePathYGap"))/2 + this.current_delta)));
	    } else {
		GfxManager.getPlatform().translate(textGfxObject, Point.add(this.leftClassArtifact.getLocation(), new Point(this.leftClassArtifact.getWidth() + OptionsManager.get("ReflexivePathXGap") + OptionsManager.get("TextLeftPadding"), this.current_delta)));
	    }
	    this.current_delta += 8;
	}

	RelationLinkArtifactPart.setGfxObjectTextForPart(textGfxObject, part);
	this.gfxObjectPart.put(part, textGfxObject);
	return textGfxObject;
    }

    private Command changeToCommand(final UMLRelation linkRelation, final LinkKind relationKind) {
	return new Command() {
	    public void execute() {
		linkRelation.setLinkKind(relationKind);
		linkRelation.setLinkStyle(relationKind.getDefaultLinkStyle());
		linkRelation.setLeftAdornment(relationKind.getDefaultLeftAdornment());
		linkRelation.setRightAdornment(relationKind.getDefaultRightAdornment());
		rebuildGfxObject();
	    }
	};
    }
    private Command deleteCommand(
	    final RelationLinkArtifactPart relationArtifactPart) {
	return new Command() {
	    public void execute() {
		relationArtifactPart.setText(ClassRelationLinkArtifact.this.relation, "");
		rebuildGfxObject();
	    }
	};
    }

    private Command editCommand(
	    final RelationLinkArtifactPart relationArtifactPart) {
	return new Command() {
	    public void execute() {
		edit(ClassRelationLinkArtifact.this.gfxObjectPart.get(relationArtifactPart));
	    }
	};
    }

    private Command reverseCommand(final UMLRelation linkRelation) {
	return new Command() {
	    public void execute() {
		linkRelation.reverse();
		rebuildGfxObject();
	    }

	};
    }


    private Command setNavigabilityCommand(final UMLRelation relation, final boolean isLeft) {
	return new Command() {
	    public void execute() {
		if(isLeft) relation.setLeftAdornment(LinkAdornment.NONE);
		else relation.setRightAdornment(LinkAdornment.NONE);
		rebuildGfxObject();
	    }
	};
    }


    private Command setNavigabilityCommand(final UMLRelation relation, final boolean isLeft, final boolean isNavigable) {
	return new Command() {
	    public void execute() {
		LinkAdornment adornment = isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS;
		if(isLeft) relation.setLeftAdornment(adornment);
		else relation.setRightAdornment(adornment);
		rebuildGfxObject();
	    }
	};
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	return "ClassRelationLink$" + this.leftClassArtifact.getId() + "!" + this.rightClassArtifact.getId() + "!"
	+ this.relation.getLinkKind().getName() + "!"
	+ this.relation.getName() + "!"	
	+ this.relation.getLinkStyle().getName() + "!"	
	+ this.relation.getLeftAdornment().getName() + "!"
	+ this.relation.getLeftCardinality() + "!"
	+ this.relation.getLeftConstraint() + "!"
	+ this.relation.getLeftRole() + "!"
	+ this.relation.getRightAdornment().getName() + "!"
	+ this.relation.getRightCardinality() + "!"
	+ this.relation.getRightConstraint() + "!"
	+ this.relation.getRightRole();
    }
    /**
     * Setter for the relation {@link LinkKind}
     * 
     * @param relationKind The {@link LinkKind} to be set
     */
    public void setRelationKind(LinkKind relationKind) {
	this.relation.setLinkKind(relationKind);	
    }
    /**
     * Setter for the relation {@link LinkArtifact.LinkStyle}
     * 
     * @param linkStyle The {@link LinkArtifact.LinkStyle} to be set
     */
    public void setLinkStyle(LinkStyle linkStyle) {
	this.relation.setLinkStyle(linkStyle);	
    }
    /**
     * Setter for the relation left {@link LinkArtifact.LinkAdornment}
     * 
     * @param leftAdornment The left {@link LinkArtifact.LinkAdornment} to be set
     */
    public void setLeftAdornment(LinkAdornment leftAdornment) {
	this.relation.setLeftAdornment(leftAdornment);
    }
    /**
     * Setter for the relation right {@link LinkArtifact.LinkAdornment}
     * 
     * @param rightAdornment The right{@link LinkArtifact.LinkAdornment} to be set
     */
    public void setRightAdornment(LinkAdornment rightAdornment) {
	this.relation.setRightAdornment(rightAdornment);
    }
}

