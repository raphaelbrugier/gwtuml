package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;

/**
 * This class implement a linear geometric formula to determine intersection between a line and a rectangle 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class LinearGeometry extends GeometryPlatform {
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.engine.GeometryPlatform#getLineBetweenImpl(com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact, com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact)
     */
    @Override
    public ArrayList<Point> getLineBetweenImpl(
	    final UMLArtifact firstUMLArtifact,
	    final UMLArtifact secondUMLArtifact) {
	final ArrayList<Point> pointList = new ArrayList<Point>();
	pointList.add(getPointForLine(firstUMLArtifact, new Point(
		secondUMLArtifact.getCenter().getX(), secondUMLArtifact
			.getCenter().getY())));
	pointList.add(getPointForLine(secondUMLArtifact, new Point(
		firstUMLArtifact.getCenter().getX(), firstUMLArtifact
			.getCenter().getY())));
	return pointList;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.engine.GeometryPlatform#getPointForLineImpl(com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    @Override
    public Point getPointForLineImpl(final UMLArtifact uMLArtifact,
	    final Point targetCenter) {
	final Point targetInFrameReference = Point.substract(targetCenter,
		uMLArtifact.getLocation());
	final Point point = Point.getOrigin();
	final int constA = uMLArtifact.getHeight()
		* targetInFrameReference.getX();
	final int constB = uMLArtifact.getWidth()
		* targetInFrameReference.getY();
	final int constC = uMLArtifact.getHeight() * uMLArtifact.getWidth();
	if (constA > constB) {
	    if (constA > constC - constB) {
		point.setX(uMLArtifact.getWidth());
		point.setY((constC - constB - constA)
			/ (uMLArtifact.getWidth() - 2 * targetInFrameReference
				.getX()));
	    } else {
		point.setX((constA - constB)
			/ (uMLArtifact.getHeight() - 2 * targetInFrameReference
				.getY()));
		point.setY(0);
	    }
	} else {
	    if (constA > constC - constB) {
		point.setX((constC + constA - constB - 2
			* uMLArtifact.getHeight()
			* targetInFrameReference.getX())
			/ (uMLArtifact.getHeight() - 2 * targetInFrameReference
				.getY()));
		point.setY(uMLArtifact.getHeight());
	    } else {
		point.setX(0);
		point.setY((constB - constA)
			/ (uMLArtifact.getWidth() - 2 * targetInFrameReference
				.getX()));
	    }
	}
	return Point.add(point, uMLArtifact.getLocation());
    }
}
