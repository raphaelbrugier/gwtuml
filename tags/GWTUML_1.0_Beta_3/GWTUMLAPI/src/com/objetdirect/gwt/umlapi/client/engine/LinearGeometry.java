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

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;

/**
 * This class implement a linear geometric formula to determine intersection between a line and a rectangle
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class LinearGeometry extends GeometryPlatform {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.engine.GeometryPlatform#getLineBetweenImpl(com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact,
	 * com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact)
	 */
	@Override
	public ArrayList<Point> getLineBetweenImpl(final UMLArtifact firstUMLArtifact, final UMLArtifact secondUMLArtifact) {
		final ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(this.getPointForLine(firstUMLArtifact, new Point(secondUMLArtifact.getCenter().getX(), secondUMLArtifact.getCenter().getY())));
		pointList.add(this.getPointForLine(secondUMLArtifact, new Point(firstUMLArtifact.getCenter().getX(), firstUMLArtifact.getCenter().getY())));
		return pointList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.engine.GeometryPlatform#getPointForLineImpl(com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public Point getPointForLineImpl(final UMLArtifact uMLArtifact, final Point targetCenter) {
		final Point targetInFrameReference = Point.substract(targetCenter, uMLArtifact.getLocation());
		final Point point = Point.getOrigin();
		final int constA = uMLArtifact.getHeight() * targetInFrameReference.getX();
		final int constB = uMLArtifact.getWidth() * targetInFrameReference.getY();
		final int constC = uMLArtifact.getHeight() * uMLArtifact.getWidth();
		if (constA > constB) {
			if (constA > constC - constB) {
				point.setX(uMLArtifact.getWidth());
				if (uMLArtifact.getWidth() - 2 * targetInFrameReference.getX() != 0) {
					point.setY((constC - constB - constA) / (uMLArtifact.getWidth() - 2 * targetInFrameReference.getX()));
				} else {
					point.setY(0);
				}

			} else {
				if (uMLArtifact.getHeight() - 2 * targetInFrameReference.getY() != 0) {
					point.setX((constA - constB) / (uMLArtifact.getHeight() - 2 * targetInFrameReference.getY()));
				} else {
					point.setX(0);
				}
				point.setY(0);
			}
		} else {
			if (constA > constC - constB) {
				if (uMLArtifact.getHeight() - 2 * targetInFrameReference.getY() != 0) {
					point.setX((constC + constA - constB - 2 * uMLArtifact.getHeight() * targetInFrameReference.getX())
							/ (uMLArtifact.getHeight() - 2 * targetInFrameReference.getY()));
				} else {
					point.setX(0);
				}
				point.setY(uMLArtifact.getHeight());
			} else {
				point.setX(0);
				if (uMLArtifact.getWidth() - 2 * targetInFrameReference.getX() != 0) {
					point.setY((constB - constA) / (uMLArtifact.getWidth() - 2 * targetInFrameReference.getX()));
				} else {
					point.setY(0);
				}
			}
		}
		return Point.add(point, uMLArtifact.getLocation());
	}
}
