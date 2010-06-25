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

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class InstantiationRelationLinkArtifact extends RelationLinkArtifact {
	/**
	 * 
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	public enum Anchor {
		BOTTOM, LEFT, RIGHT, TOP, UNKNOWN;
	}

	protected transient GfxObject arrowVirtualGroup;
	protected transient GfxObject line;
	protected transient GfxObject textVirtualGroup;

	protected ClassArtifact classArtifact;
	protected ObjectArtifact objectArtifact;

	/** Default constructor ONLY for GWT RPC serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private InstantiationRelationLinkArtifact() {
	}

	/**
	 * Constructor of {@link ObjectRelationLinkArtifact}
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 * @param id
	 *            The artifacts's id
	 * @param left
	 *            The left {@link ObjectArtifact} of the relation
	 * @param right
	 *            The right {@link ObjectArtifact} of the relation
	 * @param relationKind
	 *            The kind of relation this link is.
	 */
	public InstantiationRelationLinkArtifact(final UMLCanvas canvas, int id, final ClassArtifact left, final ObjectArtifact right, final LinkKind relationKind) {
		super(canvas, id, left, right, relationKind);
		if (relationKind != LinkKind.INSTANTIATION) {
			Log.error("Making a instantiation relation artifact for : " + relationKind.getName());
		}
		relation = new UMLRelation(relationKind);
		classArtifact = left;
		left.addDependency(this, right);
		objectArtifact = right;
		right.addDependency(this, left);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		// Instantiation are not editable
	}

	/**
	 * Getter for the left {@link ClassArtifact} of this relation
	 * 
	 * @return the left {@link ClassArtifact} of this relation
	 */
	public ClassArtifact getClassArtifact() {
		return classArtifact;
	}

	/**
	 * Getter for the right {@link ObjectArtifact} of this relation
	 * 
	 * @return the right {@link ObjectArtifact} of this relation
	 */
	public ObjectArtifact getObjectArtifact() {
		return objectArtifact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact #getRightMenu()
	 */
	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(relation.getLinkKind().getName() + " " + classArtifact.getName() + " " + relation.getLeftAdornment().getShape().getIdiom() + "-"
				+ relation.getRightAdornment().getShape().getIdiom(true) + " " + objectArtifact.getName());
		return rightMenu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
	 */
	@Override
	public void removeCreatedDependency() {
		classArtifact.removeDependency(this);
		objectArtifact.removeDependency(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "InstantiationRelationLink$<" + classArtifact.getId() + ">!<" + objectArtifact.getId() + ">";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
	 */
	@Override
	public void unselect() {
		super.unselect();
		line.setStroke(ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		arrowVirtualGroup.setStroke(ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
	}

	@Override
	protected void buildGfxObject() {
		Point curveControl = Point.getOrigin();

		ArrayList<Point> linePoints = new ArrayList<Point>();
		final boolean isComputationNeededOnLeft = relation.getLeftAdornment() != LinkAdornment.NONE;
		final boolean isComputationNeededOnRight = relation.getRightAdornment() != LinkAdornment.NONE;

		if (isComputationNeededOnLeft && isComputationNeededOnRight) {
			linePoints = GeometryManager.getPlatform().getLineBetween(classArtifact, objectArtifact);
			leftPoint = linePoints.get(0);
			rightPoint = linePoints.get(1);
		} else if (isComputationNeededOnLeft) {
			rightPoint = objectArtifact.getCenter();
			leftPoint = GeometryManager.getPlatform().getPointForLine(classArtifact, rightPoint);
		} else if (isComputationNeededOnRight) {
			leftPoint = classArtifact.getCenter();
			rightPoint = GeometryManager.getPlatform().getPointForLine(classArtifact, leftPoint);
		} else {
			leftPoint = classArtifact.getCenter();
			rightPoint = objectArtifact.getCenter();
		}
		if (order == 0) {
			line = GfxManager.getPlatform().buildLine(leftPoint, rightPoint);
		} else {
			int factor = 50 * ((order + 1) / 2);
			factor *= (order % 2) == 0 ? -1 : 1;
			curveControl = GeometryManager.getPlatform().getShiftedCenter(leftPoint, rightPoint, factor);
			line = GfxManager.getPlatform().buildPath();
			GfxManager.getPlatform().moveTo(line, leftPoint);
			GfxManager.getPlatform().curveTo(line, rightPoint, curveControl);
			line.setOpacity(0, true);
		}

		line.setStroke(ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		line.setStrokeStyle(relation.getLinkStyle().getGfxStyle());
		line.addToVirtualGroup(gfxObject);

		// Making arrows group :
		arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		arrowVirtualGroup.addToVirtualGroup(gfxObject);
		if (isComputationNeededOnLeft) {
			GfxObject leftAdornment = order == 0 ? GeometryManager.getPlatform().buildAdornment(leftPoint, rightPoint, relation.getLeftAdornment())
					: GeometryManager.getPlatform().buildAdornment(leftPoint, curveControl, relation.getLeftAdornment());
			leftAdornment.addToVirtualGroup(arrowVirtualGroup);
		}
		if (isComputationNeededOnRight) {
			GfxObject rightAdornment = order == 0 ? GeometryManager.getPlatform().buildAdornment(rightPoint, leftPoint, relation.getRightAdornment())
					: GeometryManager.getPlatform().buildAdornment(rightPoint, curveControl, relation.getRightAdornment());
			rightAdornment.addToVirtualGroup(arrowVirtualGroup);
		}

		// Making the text group :
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);
		Log.trace("Creating name");
		Point linkMiddle = Point.getMiddleOf(leftPoint, rightPoint);
		if (order != 0) {
			linkMiddle = Point.getMiddleOf(curveControl, linkMiddle);
		}
		final GfxObject nameGfxObject = GfxManager.getPlatform().buildText("«InstanceOf»", linkMiddle);
		nameGfxObject.setFont(OptionsManager.getSmallFont());
		nameGfxObject.addToVirtualGroup(textVirtualGroup);
		nameGfxObject.setStroke(ThemeManager.getTheme().getInstantiationBackgroundColor(), 0);
		nameGfxObject.setFillColor(ThemeManager.getTheme().getInstantiationForegroundColor());
		nameGfxObject.translate(new Point(-GfxManager.getPlatform().getTextWidthFor(nameGfxObject) / 2, 0));

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
		line.setStroke(ThemeManager.getTheme().getInstantiationHighlightedForegroundColor(), 2);
		arrowVirtualGroup.setStroke(ThemeManager.getTheme().getInstantiationHighlightedForegroundColor(), 2);
	}

	@Override
	public void setUpAfterDeserialization() {
		buildGfxObject();
	}
}
