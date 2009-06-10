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
package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.artifacts.NodeArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment.Shape;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;

/**
 * This abstract class contains several common geometry methods used by {@link UMLArtifact}s
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public abstract class GeometryPlatform {

	/**
	 * This method return a graphical object containing a link adornment
	 * 
	 * @param target
	 *            The {@link Point} where the adornment is to be built
	 * @param origin
	 *            The {@link Point} of the other side of the link
	 * @param adornment
	 *            The {@link LinkAdornment} that defines which adornment must be drawn
	 * @return The graphical object containing a link adornment
	 */
	public GfxObject buildAdornment(final Point target, final Point origin, final LinkAdornment adornment) {
		if (adornment == LinkAdornment.NONE) {
			return null;
		}
		final GfxObject adornmentGfxObject;
		int width = 0, lenght = 0;
		GfxColor foreColor, backColor;
		foreColor = ThemeManager.getTheme().getDefaultForegroundColor();
		if (adornment.isInverted()) {
			backColor = ThemeManager.getTheme().getDefaultForegroundColor();
		} else {
			backColor = ThemeManager.getTheme().getDefaultBackgroundColor();
		}

		switch (adornment.getShape()) {
			case DIAMOND:
				width = OptionsManager.get("DiamondWidth");
				lenght = OptionsManager.get("DiamondLength");
				break;
			case ARROW:
				if (adornment.isSolid()) {
					width = OptionsManager.get("SolidArrowWidth");
					lenght = OptionsManager.get("SolidArrowLength");
				} else {
					width = OptionsManager.get("ArrowWidth");
					lenght = OptionsManager.get("ArrowLength");
				}
				break;
			case CROSS:
				width = OptionsManager.get("CrossWidth");
				lenght = OptionsManager.get("CrossLength");
				break;
		}

		final ArrayList<Point> points = this.getAdornmentPoints(target, origin, width, lenght);
		if (adornment.getShape() == Shape.CIRCLE) {
			adornmentGfxObject = GfxManager.getPlatform().buildCircle(OptionsManager.get("CircleRadius"));
			GfxManager.getPlatform().translate(adornmentGfxObject, points.get(0));
		} else {
			adornmentGfxObject = GfxManager.getPlatform().buildPath();
			GfxManager.getPlatform().moveTo(adornmentGfxObject, points.get(0));

			if (adornment == LinkAdornment.WIRE_CROSS) {
				GfxManager.getPlatform().lineTo(adornmentGfxObject, points.get(4));
				GfxManager.getPlatform().moveTo(adornmentGfxObject, points.get(1));
				GfxManager.getPlatform().lineTo(adornmentGfxObject, points.get(3));
			} else {
				GfxManager.getPlatform().lineTo(adornmentGfxObject, target);
				GfxManager.getPlatform().lineTo(adornmentGfxObject, points.get(1));
				if (adornment == LinkAdornment.WIRE_ARROW) {
					GfxManager.getPlatform().lineTo(adornmentGfxObject, target);
				} else {
					if (adornment.getShape() == Shape.DIAMOND) {
						GfxManager.getPlatform().lineTo(adornmentGfxObject, points.get(2));
					}
					GfxManager.getPlatform().lineTo(adornmentGfxObject, points.get(0));
				}
			}
		}
		GfxManager.getPlatform().setStroke(adornmentGfxObject, foreColor, adornment == LinkAdornment.WIRE_CROSS ? 2 : 1);
		GfxManager.getPlatform().setFillColor(adornmentGfxObject, backColor);
		return adornmentGfxObject;
	}

	/**
	 * This method return an array of point containing the intersection <br>
	 * between a line between two artifact centers and these artifacts
	 * 
	 * @param firstUMLArtifact
	 *            The {@link UMLArtifact} from which the line starts
	 * @param secondUMLArtifact
	 *            The {@link UMLArtifact} from which the line ends
	 * 
	 * @return The two intersection {@link Point}s to draw the line in an {@link ArrayList}
	 */
	public ArrayList<Point> getLineBetween(final UMLArtifact firstUMLArtifact, final UMLArtifact secondUMLArtifact) {
		final long t = System.currentTimeMillis();
		final ArrayList<Point> pointList = this.getLineBetweenImpl(firstUMLArtifact, secondUMLArtifact);
		Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to compute line between " + firstUMLArtifact + " and " + secondUMLArtifact);
		return pointList;
	}

	/**
	 * This method return an array of point containing the intersection <br>
	 * between a line between an artifact center and a point and this artifact
	 * 
	 * @param uMLArtifact
	 *            The {@link UMLArtifact} from which the line starts
	 * @param point
	 *            The {@link Point} where the line ends
	 * 
	 * @return The intersection {@link Point}
	 */
	public Point getPointForLine(final UMLArtifact uMLArtifact, final Point point) {
		final long t = System.currentTimeMillis();
		final Point pt = this.getPointForLineImpl(uMLArtifact, point);
		Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to compute line between " + uMLArtifact + " and a point");
		return pt;
	}

	/**
	 * This method calculates the point to draw a reflexive link (between a {@link NodeArtifact} and itself)
	 * 
	 * @param nodeArtifact
	 *            The {@link NodeArtifact} which the reflexive link belongs
	 * @return An {@link ArrayList} of the {@link Point}s computed to draw the path
	 */
	public ArrayList<Point> getReflexiveLineFor(final NodeArtifact nodeArtifact) {
		final Point center = nodeArtifact.getCenter();
		final int halfClassWidth = nodeArtifact.getWidth() / 2;
		final int halfClassHeight = nodeArtifact.getHeight() / 2;
		final ArrayList<Point> pointList = new ArrayList<Point>();
		final Point point0 = center.clonePoint();
		point0.translate(halfClassWidth, 0);
		final Point point1 = point0.clonePoint();
		point1.translate(OptionsManager.get("ReflexivePathXGap"), 0);
		final Point point2 = point1.clonePoint();
		point2.translate(0, -halfClassHeight - OptionsManager.get("ReflexivePathYGap"));
		final Point point3 = point2.clonePoint();
		point3.translate(-halfClassWidth - OptionsManager.get("ReflexivePathXGap"), 0);
		final Point point4 = point3.clonePoint();
		point4.translate(0, OptionsManager.get("ReflexivePathYGap"));
		pointList.add(point0);
		pointList.add(point1);
		pointList.add(point2);
		pointList.add(point3);
		pointList.add(point4);
		return pointList;
	}

	/**
	 * Get a point shifted orthogonally from the center of the line between point1 and point2 <br />
	 * It is used to draw quadratic Bezier curve for links
	 * 
	 * @param point1
	 *            First line {@link Point}
	 * @param point2
	 *            Second line {@link Point}
	 * @param shift
	 *            Value of the shift between the returned {@link Point} and the center
	 * @return The center shifted {@link Point}
	 */
	public Point getShiftedCenter(final Point point1, final Point point2, final int shift) {
		double xDiff = point2.getX() - point1.getX();
		if (xDiff == 0.) {
			xDiff = 0.00000001;
		}
		final double yDiff = point2.getY() - point1.getY();
		final double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));
		final double slope = yDiff / xDiff;
		final double yShift = distance * (shift / 200.) / Math.sqrt(1. + Math.pow(slope, 2));
		final double xShift = slope * yShift;
		final double thalesConst = 0.5;
		final Point shifted = point1.clonePoint();
		shifted.translate((xDiff * thalesConst) - xShift, (yDiff * thalesConst) + yShift);
		return shifted;

	}

	protected abstract ArrayList<Point> getLineBetweenImpl(UMLArtifact firstUMLArtifact, UMLArtifact secondUMLArtifact);

	protected abstract Point getPointForLineImpl(UMLArtifact uMLArtifact, Point point);

	private ArrayList<Point> getAdornmentPoints(final Point point1, final Point point2, final int width, final int lenght) {
		final ArrayList<Point> arrowPoints = new ArrayList<Point>();

		double xDiff = point2.getX() - point1.getX();
		if (xDiff == 0.) {
			xDiff = 0.00000001;
		}
		final double yDiff = point2.getY() - point1.getY();
		final double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));
		final double slope = yDiff / xDiff;
		final double yShift = width / 2. / Math.sqrt(1. + Math.pow(slope, 2));
		final double xShift = slope * yShift;
		final Point root = this.getArrowRoot(point1, xDiff, yDiff, distance, lenght);
		final Point up = root.clonePoint();
		final Point down = root.clonePoint();
		up.translate(-xShift, yShift);
		down.translate(xShift, -yShift);
		final Point diamondTail = Point.substract(root, Point.substract(point1, root));
		final Point crossUp = Point.substract(diamondTail, Point.substract(down, diamondTail));
		final Point crossDown = Point.substract(diamondTail, Point.substract(up, diamondTail));

		arrowPoints.add(up); // 0
		arrowPoints.add(down); // 1
		arrowPoints.add(diamondTail); // 2
		arrowPoints.add(crossUp); // 3
		arrowPoints.add(crossDown); // 4
		return arrowPoints;
	}

	// . origin
	// \ ^
	// \ / \
	// \ / \
	// /°root \
	// / \
	// /__________\. target
	private Point getArrowRoot(final Point target, final double xDiff, final double yDiff, final double distance, final int lenght) {
		final double thalesConst = lenght / distance;
		final Point root = target.clonePoint();
		root.translate(xDiff * thalesConst, yDiff * thalesConst);
		return root;
	}

}
