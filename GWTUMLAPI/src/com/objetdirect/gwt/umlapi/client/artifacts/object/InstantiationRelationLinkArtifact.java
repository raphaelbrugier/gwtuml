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
package com.objetdirect.gwt.umlapi.client.artifacts.object;

import static com.objetdirect.gwt.umlapi.client.gfx.GfxStyle.LONGDASH;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment.WIRE_ARROW;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind.INSTANTIATION;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.InstantiationRelation;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class InstantiationRelationLinkArtifact extends RelationLinkArtifact {

	private transient GfxObject arrowVirtualGroup;
	private transient GfxObject line;
	private transient GfxObject textVirtualGroup;

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	private ClassSimplifiedArtifact classArtifact;
	private ObjectArtifact objectArtifact;
	private InstantiationRelation instantiationRelation;

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
	public InstantiationRelationLinkArtifact(final UMLCanvas canvas, int id, final ClassSimplifiedArtifact left, final ObjectArtifact right) {
		super(canvas, id, left, right, INSTANTIATION);
		classArtifact = left;
		left.addDependency(this, right);
		objectArtifact = right;
		right.addDependency(this, left);

		instantiationRelation = new InstantiationRelation(objectArtifact.toUMLComponent(), classArtifact.toUMLComponent());

		objectArtifact.toUMLComponent().setInstantiatedClass(classArtifact.toUMLComponent());
		objectArtifact.rebuildGfxObject();
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		// Instantiation are not editable
	}

	/**
	 * Return the metamodel UmlComponent wrapped by the artifact.
	 * 
	 * @return the instantiationRelation
	 */
	public InstantiationRelation toUMLComponent() {
		return instantiationRelation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact #getRightMenu()
	 */
	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		String name = objectArtifact.getName() + " instance of  " + classArtifact.getName();
		rightMenu.setName(name);
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

		rightPoint = objectArtifact.getCenter();
		leftPoint = GeometryManager.getPlatform().getPointForLine(classArtifact, rightPoint);

		Point tempRightPoint = null;
		if (order == 0) {
			line = GfxManager.getPlatform().buildLine(leftPoint, rightPoint);
			tempRightPoint = rightPoint;
		} else {
			int factor = 50 * ((order + 1) / 2);
			factor *= (order % 2) == 0 ? -1 : 1;
			curveControl = GeometryManager.getPlatform().getShiftedCenter(leftPoint, rightPoint, factor);
			line = GfxManager.getPlatform().buildPath();
			GfxManager.getPlatform().moveTo(line, leftPoint);
			GfxManager.getPlatform().curveTo(line, rightPoint, curveControl);
			line.setOpacity(0, true);
			tempRightPoint = curveControl;
		}

		line.setStroke(ThemeManager.getTheme().getInstantiationForegroundColor(), 1);
		line.setStrokeStyle(LONGDASH);
		line.addToVirtualGroup(gfxObject);

		// Making arrows group :
		arrowVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		arrowVirtualGroup.addToVirtualGroup(gfxObject);
		GfxObject leftAdornment = GeometryManager.getPlatform().buildAdornment(leftPoint, tempRightPoint, WIRE_ARROW);
		leftAdornment.addToVirtualGroup(arrowVirtualGroup);

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
