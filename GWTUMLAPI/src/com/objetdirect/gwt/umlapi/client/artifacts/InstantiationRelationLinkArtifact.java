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
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class InstantiationRelationLinkArtifact extends RelationLinkArtifact {
	/**
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	protected enum Anchor {
		BOTTOM, LEFT, RIGHT, TOP, UNKNOWN;
	}

	protected GfxObject											arrowVirtualGroup;
	protected ClassArtifact										classArtifact;
	protected GfxObject											line;
	protected ObjectArtifact									objectArtifact;
	protected GfxObject											textVirtualGroup;

	/**
	 * Constructor of {@link ObjectRelationLinkArtifact}
	 * 
	 * @param left
	 *            The left {@link ObjectArtifact} of the relation
	 * @param right
	 *            The right {@link ObjectArtifact} of the relation
	 * @param relationKind
	 *            The kind of relation this link is.
	 */
	public InstantiationRelationLinkArtifact(final ClassArtifact left, final ObjectArtifact right, final LinkKind relationKind) {
		super(left, right, relationKind);
		if (relationKind != LinkKind.INSTANTIATION) {
			Log.error("Making a instantiation relation artifact for : " + relationKind.getName());
		}
		this.relation = new UMLRelation(relationKind);
		this.classArtifact = left;
		left.addDependency(this, right);
		this.objectArtifact = right;
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
		return this.classArtifact;
	}

	/**
	 * Getter for the right {@link ObjectArtifact} of this relation
	 * 
	 * @return the right {@link ObjectArtifact} of this relation
	 */
	public ObjectArtifact getObjectArtifact() {
		return this.objectArtifact;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact #getRightMenu()
	 */
	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName(this.relation.getLinkKind().getName() + " " + this.classArtifact.getName() + " "
				+ this.relation.getLeftAdornment().getShape().getIdiom() + "-" + this.relation.getRightAdornment().getShape().getIdiom(true) + " "
				+ this.objectArtifact.getName());
		return rightMenu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact#removeCreatedDependency()
	 */
	@Override
	public void removeCreatedDependency() {
		this.classArtifact.removeDependency(this);
		this.objectArtifact.removeDependency(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "InstantiationRelationLink$<" + this.classArtifact.getId() + ">!<" + this.objectArtifact.getId() + ">";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#unselect()
	 */
	@Override
	public void unselect() {
		super.unselect();
		this.line.setStroke(ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		this.arrowVirtualGroup.setStroke(ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
	}

	@Override
	protected void buildGfxObject() {
		Point curveControl = Point.getOrigin();

		ArrayList<Point> linePoints = new ArrayList<Point>();
		final boolean isComputationNeededOnLeft = this.relation.getLeftAdornment() != LinkAdornment.NONE;
		final boolean isComputationNeededOnRight = this.relation.getRightAdornment() != LinkAdornment.NONE;

		if (isComputationNeededOnLeft && isComputationNeededOnRight) {
			linePoints = GeometryManager.getPlatform().getLineBetween(this.classArtifact, this.objectArtifact);
			this.leftPoint = linePoints.get(0);
			this.rightPoint = linePoints.get(1);
		} else if (isComputationNeededOnLeft) {
			this.rightPoint = this.objectArtifact.getCenter();
			this.leftPoint = GeometryManager.getPlatform().getPointForLine(this.classArtifact, this.rightPoint);
		} else if (isComputationNeededOnRight) {
			this.leftPoint = this.classArtifact.getCenter();
			this.rightPoint = GeometryManager.getPlatform().getPointForLine(this.classArtifact, this.leftPoint);
		} else {
			this.leftPoint = this.classArtifact.getCenter();
			this.rightPoint = this.objectArtifact.getCenter();
		}
		if (this.order == 0) {
			this.line = GfxManager.getPlatform().buildLine(this.leftPoint, this.rightPoint);
		} else {
			int factor = 50 * ((this.order + 1) / 2);
			factor *= (this.order % 2) == 0 ? -1 : 1;
			curveControl = GeometryManager.getPlatform().getShiftedCenter(this.leftPoint, this.rightPoint, factor);
			this.line = GfxManager.getPlatform().buildPath();
			GfxManager.getPlatform().moveTo(this.line, this.leftPoint);
			GfxManager.getPlatform().curveTo(this.line, this.rightPoint, curveControl);
			this.line.setOpacity(0, true);
		}

		this.line.setStroke(ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		this.line.setStrokeStyle(this.relation.getLinkStyle().getGfxStyle());
		 this.line.addToVirtualGroup(this.gfxObject);

		// Making arrows group :
		this.arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		this.arrowVirtualGroup.addToVirtualGroup(this.gfxObject);
		if (isComputationNeededOnLeft) {
			GfxObject leftAdornment = this.order == 0 ? GeometryManager.getPlatform().buildAdornment(this.leftPoint, this.rightPoint, this.relation.getLeftAdornment())
					: GeometryManager.getPlatform().buildAdornment(this.leftPoint, curveControl, this.relation.getLeftAdornment());
			leftAdornment.addToVirtualGroup(this.arrowVirtualGroup);
		}
		if (isComputationNeededOnRight) {
			GfxObject rightAdornment = this.order == 0 ? GeometryManager.getPlatform().buildAdornment(this.rightPoint, this.leftPoint, this.relation.getRightAdornment())
					: GeometryManager.getPlatform().buildAdornment(this.rightPoint, curveControl, this.relation.getRightAdornment()); 
			rightAdornment.addToVirtualGroup(this.arrowVirtualGroup);
		}

		// Making the text group :
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		this.textVirtualGroup.addToVirtualGroup(this.gfxObject);
		Log.trace("Creating name");
		Point linkMiddle = Point.getMiddleOf(this.leftPoint, this.rightPoint);
		if (this.order != 0) {
			linkMiddle = Point.getMiddleOf(curveControl, linkMiddle);
		}
		final GfxObject nameGfxObject = GfxManager.getPlatform().buildText("«InstanceOf»", linkMiddle);
		nameGfxObject.setFont(OptionsManager.getSmallFont());
		nameGfxObject.addToVirtualGroup(this.textVirtualGroup);
		nameGfxObject.setStroke(ThemeManager.getTheme().getInstantiationBackgroundColor(), 0);
		nameGfxObject.setFillColor(ThemeManager.getTheme().getInstantiationForegroundColor());
		nameGfxObject.translate(new Point(-GfxManager.getPlatform().getTextWidthFor(nameGfxObject) / 2, 0));

		this.gfxObject.moveToBack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#select()
	 */
	@Override
	protected void select() {
		super.select();
		this.line.setStroke(ThemeManager.getTheme().getInstantiationHighlightedForegroundColor(), 2);
		this.arrowVirtualGroup.setStroke(ThemeManager.getTheme().getInstantiationHighlightedForegroundColor(), 2);
	}
}
