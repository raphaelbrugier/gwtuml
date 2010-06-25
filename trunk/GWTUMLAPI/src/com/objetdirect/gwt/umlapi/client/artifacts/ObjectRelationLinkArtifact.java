/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.HashMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.editors.RelationFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.exceptions.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkStyle;

/**
 * This object represents any relation artifact between two objectes
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ObjectRelationLinkArtifact extends RelationLinkArtifact {

	/**
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	public enum Anchor {
		BOTTOM, LEFT, RIGHT, TOP, UNKNOWN;
	}

	private transient GfxObject arrowVirtualGroup;
	private transient GfxObject line;
	private transient GfxObject textVirtualGroup;
	private transient HashMap<RelationLinkArtifactPart, GfxObject> gfxObjectPart;

	protected ObjectArtifact leftObjectArtifact;
	protected ObjectArtifact rightObjectArtifact;

	private int current_delta;

	/** Default constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ObjectRelationLinkArtifact() {
	}

	/**
	 * Constructor of {@link ObjectRelationLinkArtifact}
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param left
	 *            The left {@link ObjectArtifact} of the relation
	 * @param right
	 *            The right {@link ObjectArtifact} of the relation
	 * @param relationKind
	 *            The kind of relation this link is.
	 */
	public ObjectRelationLinkArtifact(final UMLCanvas canvas, int id, final ObjectArtifact left, final ObjectArtifact right, final LinkKind relationKind) {
		super(canvas, id, left, right, relationKind);

		gfxObjectPart = new HashMap<RelationLinkArtifactPart, GfxObject>();

		if ((relationKind == LinkKind.NOTE) || (relationKind == LinkKind.CLASSRELATION)) {
			Log.error("Making a relation artifact for : " + relationKind.getName());
		}
		if ((relationKind == LinkKind.GENERALIZATION_RELATION)) {
			Log.error("Making an object relation artifact for : " + relationKind.getName());
		}

		relation = new UMLRelation(relationKind);
		leftObjectArtifact = left;
		left.addDependency(this, right);
		rightObjectArtifact = right;
		if (right != left) {
			right.addDependency(this, left);
		}

	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		RelationLinkArtifactPart editPart = null;

		for (Entry<RelationLinkArtifactPart, GfxObject> entry : gfxObjectPart.entrySet()) {
			if (entry.getValue().equals(editedGfxObject)) {
				editPart = entry.getKey();
			}
		}

		if (editPart == null) {
			this.createPart(RelationLinkArtifactPart.NAME);
		} else {
			edit(editPart);
		}
	}

	/**
	 * Build and editor for the editPart and display it.
	 * 
	 * @param editPart
	 *            the edited part.
	 */
	public void edit(final RelationLinkArtifactPart editPart) {
		if (editPart == null) {
			throw new GWTUMLAPIException("There is no corresponding RelationLinkArtifactPart attached to the given GfxObject");
		}

		GfxObject editedGfxObject = gfxObjectPart.get(editPart);

		final RelationFieldEditor editor = new RelationFieldEditor(canvas, this, editPart);
		editor.startEdition(editPart.getText(relation), editedGfxObject.getLocation().getX(), editedGfxObject.getLocation().getY(), GfxManager.getPlatform()
				.getTextWidthFor(editedGfxObject)
				+ OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding"), false, true);
	}

	/**
	 * Request an creation of a {@link RelationLinkArtifact.RelationLinkArtifactPart} and set its default text;
	 * 
	 * @param part
	 *            The {@link RelationLinkArtifact.RelationLinkArtifactPart} to edit
	 */
	public void createPart(final RelationLinkArtifactPart part) {
		String defaultText;
		switch (part) {
			case NAME:
				final String name = part.getText(relation);
				if ((name == null) || name.equals("")) {
					defaultText = leftObjectArtifact.getName() + "-" + rightObjectArtifact.getName();
				} else {
					defaultText = name;
				}
				break;
			case LEFT_ROLE:
			case RIGHT_ROLE:
				defaultText = "role";
				break;
			default:
				defaultText = "?";

		}
		part.setText(relation, defaultText);
		this.rebuildGfxObject();
		this.edit(gfxObjectPart.get(part));
	}

	/**
	 * Getter for the left {@link ObjectArtifact} of this relation
	 * 
	 * @return the left {@link ObjectArtifact} of this relation
	 */
	public ObjectArtifact getLeftObjectArtifact() {
		return leftObjectArtifact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact #getRightMenu()
	 */
	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(relation.getLinkKind().getName() + " " + leftObjectArtifact.getName() + " " + relation.getLeftAdornment().getShape().getIdiom() + "-"
				+ relation.getRightAdornment().getShape().getIdiom(true) + " " + rightObjectArtifact.getName());
		final MenuBar leftSide = new MenuBar(true);
		final MenuBar rightSide = new MenuBar(true);

		for (final RelationLinkArtifactPart relationLinkArtifactPart : RelationLinkArtifactPart.values()) {
			if ((relationLinkArtifactPart != RelationLinkArtifactPart.LEFT_CARDINALITY)
					&& (relationLinkArtifactPart != RelationLinkArtifactPart.RIGHT_CARDINALITY)
					&& (relationLinkArtifactPart != RelationLinkArtifactPart.LEFT_CONSTRAINT)
					&& (relationLinkArtifactPart != RelationLinkArtifactPart.RIGHT_CONSTRAINT)
					&& (relationLinkArtifactPart != RelationLinkArtifactPart.LEFT_STEREOTYPE)
					&& (relationLinkArtifactPart != RelationLinkArtifactPart.RIGHT_STEREOTYPE)) {
				final MenuBar editDelete = new MenuBar(true);
				if (!relationLinkArtifactPart.getText(relation).equals("")) {
					editDelete.addItem("Edit", this.editCommand(relationLinkArtifactPart));
					editDelete.addItem("Delete", this.deleteCommand(relationLinkArtifactPart));
				} else {
					editDelete.addItem("Create", this.createCommand(relationLinkArtifactPart));
				}
				if (relationLinkArtifactPart.isLeft()) {
					leftSide.addItem(relationLinkArtifactPart.toString(), editDelete);
				} else {
					if (relationLinkArtifactPart != RelationLinkArtifactPart.NAME) {
						rightSide.addItem(relationLinkArtifactPart.toString(), editDelete);
					} else {
						rightMenu.addItem(relationLinkArtifactPart.toString(), editDelete);
					}
				}
			}
		}
		switch (relation.getLeftAdornment()) {
			case NONE:
			case WIRE_ARROW:
			case WIRE_CROSS:
				final MenuBar leftNavigability = new MenuBar(true);
				leftNavigability.addItem("Navigable", this.setNavigabilityCommand(relation, true, true));
				leftNavigability.addItem("Not Navigable", this.setNavigabilityCommand(relation, true, false));
				leftNavigability.addItem("Unknown", this.setNavigabilityCommand(relation, true));
				leftSide.addItem("Navigability", leftNavigability);
		}
		switch (relation.getRightAdornment()) {
			case NONE:
			case WIRE_ARROW:
			case WIRE_CROSS:
				final MenuBar rightNavigability = new MenuBar(true);
				rightNavigability.addItem("Navigable", this.setNavigabilityCommand(relation, false, true));
				rightNavigability.addItem("Not Navigable", this.setNavigabilityCommand(relation, false, false));
				rightNavigability.addItem("Unknown", this.setNavigabilityCommand(relation, false));
				rightSide.addItem("Navigability", rightNavigability);
		}

		rightMenu.addItem(leftObjectArtifact.getName() + " side", leftSide);
		rightMenu.addItem(rightObjectArtifact.getName() + " side", rightSide);
		rightMenu.addItem("Reverse", this.reverseCommand(relation));
		return rightMenu;
	}

	/**
	 * Getter for the right {@link ObjectArtifact} of this relation
	 * 
	 * @return the right {@link ObjectArtifact} of this relation
	 */
	public ObjectArtifact getRightObjectArtifact() {
		return rightObjectArtifact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
	 */
	@Override
	public void removeCreatedDependency() {
		leftObjectArtifact.removeDependency(this);
		rightObjectArtifact.removeDependency(this);
	}

	/**
	 * Reset the navigability of the left side to unknown <br />
	 * The left side must not be a generalization, realization, aggregation or composition otherwise this method do
	 * nothing
	 */
	public void resetLeftNavigability() {
		if (relation.getLeftAdornment().isNavigabilityAdornment()) {
			relation.setLeftAdornment(LinkAdornment.NONE);
		}
	}

	/**
	 * Reset the navigability of the right side to unknown <br />
	 * The right side must not be a generalization, realization, aggregation or composition otherwise this method do
	 * nothing
	 */
	public void resetRightNavigability() {
		if (relation.getRightAdornment().isNavigabilityAdornment()) {
			relation.setRightAdornment(LinkAdornment.NONE);
		}
	}

	/**
	 * Setter for the relation left {@link LinkArtifact.LinkAdornment}
	 * 
	 * @param leftAdornment
	 *            The left {@link LinkArtifact.LinkAdornment} to be set
	 */
	public void setLeftAdornment(final LinkAdornment leftAdornment) {
		relation.setLeftAdornment(leftAdornment);
	}

	/**
	 * Set the state of left navigability <br />
	 * The left side must not be a generalization, realization, aggregation or composition otherwise this method do
	 * nothing <br />
	 * To set the unknown state see {@link ObjectRelationLinkArtifact#resetLeftNavigability()}
	 * 
	 * @param isNavigable
	 *            If true set the link's side to navigable otherwise set it to NOT navigable
	 * 
	 */
	public void setLeftNavigability(final boolean isNavigable) {
		if (relation.getLeftAdornment().isNavigabilityAdornment()) {
			relation.setLeftAdornment(isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS);
		}

	}

	/**
	 * Setter for the relation {@link LinkArtifact.LinkStyle}
	 * 
	 * @param linkStyle
	 *            The {@link LinkArtifact.LinkStyle} to be set
	 */
	public void setLinkStyle(final LinkStyle linkStyle) {
		relation.setLinkStyle(linkStyle);
	}

	/**
	 * Setter for the name in {@link UMLRelation} This does not update the graphical object
	 * 
	 * @param name
	 *            The name text to be set
	 */
	public void setName(final String name) {
		relation.setName(name);
	}

	/**
	 * Setter for the relation {@link LinkKind}
	 * 
	 * @param relationKind
	 *            The {@link LinkKind} to be set
	 */
	public void setRelationKind(final LinkKind relationKind) {
		relation.setLinkKind(relationKind);
	}

	/**
	 * Setter for the relation right {@link LinkArtifact.LinkAdornment}
	 * 
	 * @param rightAdornment
	 *            The right{@link LinkArtifact.LinkAdornment} to be set
	 */
	public void setRightAdornment(final LinkAdornment rightAdornment) {
		relation.setRightAdornment(rightAdornment);
	}

	/**
	 * Setter for the rightCardinality in {@link UMLRelation} This does not update the graphical object
	 * 
	 * @param rightCardinality
	 *            The rightCardinality text to be set
	 */
	public void setRightCardinality(final String rightCardinality) {
		relation.setRightCardinality(rightCardinality);
	}

	/**
	 * Setter for the rightConstraint in {@link UMLRelation} This does not update the graphical object
	 * 
	 * @param rightConstraint
	 *            The rightConstraint text to be set
	 */
	public void setRightConstraint(final String rightConstraint) {
		relation.setRightConstraint(rightConstraint);
	}

	/**
	 * Set the state of right navigability <br />
	 * The right side must not be a generalization, realization, aggregation or composition otherwise this method do
	 * nothing <br />
	 * To set the unknown state see {@link ObjectRelationLinkArtifact#resetRightNavigability()}
	 * 
	 * @param isNavigable
	 *            If true set the link's side to navigable otherwise set it to NOT navigable
	 * 
	 */
	public void setRightNavigability(final boolean isNavigable) {
		if (relation.getRightAdornment().isNavigabilityAdornment()) {
			relation.setRightAdornment(isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "ObjectRelationLink$<" + leftObjectArtifact.getId() + ">!<" + rightObjectArtifact.getId() + ">!" + relation.getLinkKind().getName() + "!"
				+ relation.getName() + "!" + relation.getLinkStyle().getName() + "!" + relation.getLeftAdornment().getName() + "!"
				+ relation.getRightAdornment().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
	 */
	@Override
	public void unselect() {
		super.unselect();
		line.setStroke(ThemeManager.getTheme().getObjectRelationForegroundColor(), 1);
		arrowVirtualGroup.setStroke(ThemeManager.getTheme().getObjectRelationForegroundColor(), 1);
	}

	Anchor getAnchorType(final ObjectArtifact objectArtifact, final Point point) {
		if (point.getX() == objectArtifact.getLocation().getX()) {
			return Anchor.LEFT;
		} else if (point.getY() == objectArtifact.getLocation().getY()) {
			return Anchor.TOP;
		} else if (point.getX() == objectArtifact.getLocation().getX() + objectArtifact.getWidth()) {
			return Anchor.RIGHT;
		} else if (point.getY() == objectArtifact.getLocation().getY() + objectArtifact.getHeight()) {
			return Anchor.BOTTOM;
		}
		return Anchor.UNKNOWN;
	}

	int getTextX(final GfxObject text, final boolean isLeft) {

		Point relative_point1 = leftPoint;
		Point relative_point2 = rightPoint;
		final int textWidth = GfxManager.getPlatform().getTextWidthFor(text);
		if (!isLeft) {
			relative_point1 = rightPoint;
			relative_point2 = leftPoint;
		}
		switch (this.getAnchorType(isLeft ? leftObjectArtifact : rightObjectArtifact, relative_point1)) {
			case LEFT:
				return relative_point1.getX() - textWidth - OptionsManager.get("RectangleLeftPadding");
			case RIGHT:
				return relative_point1.getX() + OptionsManager.get("RectangleRightPadding");
			case TOP:
			case BOTTOM:
			case UNKNOWN:
				if (relative_point1.getX() < relative_point2.getX()) {
					return relative_point1.getX() - textWidth - OptionsManager.get("RectangleLeftPadding");
				}
				return relative_point1.getX() + OptionsManager.get("RectangleRightPadding");
		}
		return 0;
	}

	int getTextY(final GfxObject text, final boolean isLeft) {
		Point relative_point1 = leftPoint;
		Point relative_point2 = rightPoint;
		if (!isLeft) {
			relative_point1 = rightPoint;
			relative_point2 = leftPoint;
		}
		final int textHeight = GfxManager.getPlatform().getTextHeightFor(text);
		final int delta = current_delta;
		current_delta += 8; // TODO : Fix Height
		switch (this.getAnchorType(isLeft ? leftObjectArtifact : rightObjectArtifact, relative_point1)) {
			case LEFT:
			case RIGHT:
				if (relative_point1.getY() > relative_point2.getY()) {
					return relative_point1.getY() + OptionsManager.get("RectangleBottomPadding") + delta;
				}
				return relative_point1.getY() - textHeight - OptionsManager.get("RectangleTopPadding") - delta;
			case TOP:
				return relative_point1.getY() - textHeight - OptionsManager.get("RectangleTopPadding") - delta;
			case BOTTOM:
			case UNKNOWN:
				return relative_point1.getY() + OptionsManager.get("RectangleBottomPadding") + delta;
		}
		return 0;
	}

	@Override
	protected void buildGfxObject() {
		gfxObjectPart.clear();

		line = this.buildLine();

		line.setStroke(ThemeManager.getTheme().getClassRelationForegroundColor(), 1);
		line.setStrokeStyle(relation.getLinkStyle().getGfxStyle());
		line.addToVirtualGroup(gfxObject);

		// Making arrows group :
		arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		arrowVirtualGroup.addToVirtualGroup(gfxObject);
		final GfxObject leftArrow = GeometryManager.getPlatform().buildAdornment(leftPoint, leftDirectionPoint, relation.getLeftAdornment());
		final GfxObject rightArrow = GeometryManager.getPlatform().buildAdornment(rightPoint, rightDirectionPoint, relation.getRightAdornment());

		if (leftArrow != null) {
			leftArrow.addToVirtualGroup(arrowVirtualGroup);
		}
		if (rightArrow != null) {
			rightArrow.addToVirtualGroup(arrowVirtualGroup);
		}
		// Making the text group :
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);
		if (!relation.getName().equals("")) {
			Log.trace("Creating name");
			final GfxObject nameGfxObject = GfxManager.getPlatform().buildText(
					(relation.getLinkKind() == LinkKind.INSTANTIATION) ? "«InstanceOf»" : relation.getName(), nameAnchorPoint);
			nameGfxObject.setFont(OptionsManager.getSmallFont());
			nameGfxObject.addToVirtualGroup(textVirtualGroup);
			if (relation.getLinkKind() != LinkKind.INSTANTIATION) {
				final int yUnderline = nameAnchorPoint.getY() + GfxManager.getPlatform().getTextHeightFor(nameGfxObject) + OptionsManager.get("TextTopPadding");
				final GfxObject underline = GfxManager.getPlatform().buildLine(
						new Point(nameAnchorPoint.getX() + OptionsManager.get("TextLeftPadding"), yUnderline),
						new Point(nameAnchorPoint.getX() + OptionsManager.get("TextLeftPadding") + GfxManager.getPlatform().getTextWidthFor(nameGfxObject),
								yUnderline));
				underline.addToVirtualGroup(textVirtualGroup);
				underline.setStroke(ThemeManager.getTheme().getObjectRelationForegroundColor(), 1);
				underline.setFillColor(ThemeManager.getTheme().getObjectRelationForegroundColor());
				underline.translate(new Point(-GfxManager.getPlatform().getTextWidthFor(nameGfxObject) / 2, 0));
			}
			nameGfxObject.setStroke(ThemeManager.getTheme().getObjectRelationBackgroundColor(), 0);
			nameGfxObject.setFillColor(ThemeManager.getTheme().getObjectRelationForegroundColor());
			nameGfxObject.translate(new Point(-GfxManager.getPlatform().getTextWidthFor(nameGfxObject) / 2, 0));
			gfxObjectPart.put(RelationLinkArtifactPart.NAME, nameGfxObject);
		}
		current_delta = 0;
		if (!relation.getLeftRole().equals("")) {
			GfxObject leftRoleGfxText = this.createText(relation.getLeftRole(), RelationLinkArtifactPart.LEFT_ROLE);
			leftRoleGfxText.addToVirtualGroup(textVirtualGroup);
		}
		current_delta = 0;
		if (!relation.getRightRole().equals("")) {
			GfxObject rightRoleGfxText = this.createText(relation.getRightRole(), RelationLinkArtifactPart.RIGHT_ROLE);
			rightRoleGfxText.addToVirtualGroup(textVirtualGroup);
		}

		gfxObject.moveToBack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
	 */
	@Override
	protected void select() {
		super.select();
		line.setStroke(ThemeManager.getTheme().getObjectRelationHighlightedForegroundColor(), 2);
		arrowVirtualGroup.setStroke(ThemeManager.getTheme().getObjectRelationHighlightedForegroundColor(), 2);
	}

	private Command createCommand(final RelationLinkArtifactPart relationArtifactPart) {
		return new Command() {
			public void execute() {
				ObjectRelationLinkArtifact.this.createPart(relationArtifactPart);
			}
		};
	}

	private GfxObject createText(final String text, final RelationLinkArtifactPart part) {

		final GfxObject textGfxObject = GfxManager.getPlatform().buildText(text, Point.getOrigin());
		textGfxObject.setFont(OptionsManager.getSmallFont());
		textGfxObject.setStroke(ThemeManager.getTheme().getObjectRelationBackgroundColor(), 0);
		textGfxObject.setFillColor(ThemeManager.getTheme().getObjectRelationForegroundColor());
		if (leftObjectArtifact != rightObjectArtifact) {
			Log.trace("Creating text : " + text + " at " + this.getTextX(textGfxObject, part.isLeft()) + " : " + this.getTextY(textGfxObject, part.isLeft()));
			textGfxObject.translate(new Point(this.getTextX(textGfxObject, part.isLeft()), this.getTextY(textGfxObject, part.isLeft())));
		} else {
			if (part.isLeft()) {
				textGfxObject.translate(Point.add(leftObjectArtifact.getCenter(), new Point(OptionsManager.get("ArrowWidth") / 2
						+ OptionsManager.get("TextLeftPadding"), -(leftObjectArtifact.getHeight() + OptionsManager.get("ReflexivePathYGap")) / 2
						+ current_delta)));
			} else {
				textGfxObject.translate(Point.add(leftObjectArtifact.getLocation(), new Point(leftObjectArtifact.getWidth()
						+ OptionsManager.get("ReflexivePathXGap") + OptionsManager.get("TextLeftPadding"), current_delta)));
			}
			current_delta += 8;
		}

		gfxObjectPart.put(part, textGfxObject);
		return textGfxObject;
	}

	private Command deleteCommand(final RelationLinkArtifactPart relationArtifactPart) {
		return new Command() {
			public void execute() {
				relationArtifactPart.setText(ObjectRelationLinkArtifact.this.relation, "");
				ObjectRelationLinkArtifact.this.rebuildGfxObject();
			}
		};
	}

	private Command editCommand(final RelationLinkArtifactPart relationArtifactPart) {
		return new Command() {
			public void execute() {
				ObjectRelationLinkArtifact.this.edit(relationArtifactPart);
			}
		};
	}

	private Command reverseCommand(final UMLRelation linkRelation) {
		return new Command() {
			public void execute() {
				linkRelation.reverse();
				ObjectRelationLinkArtifact.this.rebuildGfxObject();
			}

		};
	}

	private Command setNavigabilityCommand(final UMLRelation relation, final boolean isLeft) {
		return new Command() {
			public void execute() {
				if (isLeft) {
					relation.setLeftAdornment(LinkAdornment.NONE);
				} else {
					relation.setRightAdornment(LinkAdornment.NONE);
				}
				ObjectRelationLinkArtifact.this.rebuildGfxObject();
			}
		};
	}

	private Command setNavigabilityCommand(final UMLRelation relation, final boolean isLeft, final boolean isNavigable) {
		return new Command() {
			public void execute() {
				final LinkAdornment adornment = isNavigable ? LinkAdornment.WIRE_ARROW : LinkAdornment.WIRE_CROSS;
				if (isLeft) {
					relation.setLeftAdornment(adornment);
				} else {
					relation.setRightAdornment(adornment);
				}
				ObjectRelationLinkArtifact.this.rebuildGfxObject();
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		gfxObjectPart = new HashMap<RelationLinkArtifactPart, GfxObject>();
		buildGfxObject();
	}
}
