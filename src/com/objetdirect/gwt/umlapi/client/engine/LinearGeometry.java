package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;

/**
 * @author florian
 * 
 */
public class LinearGeometry extends GeometryPlatform {
    @Override
    public ArrayList<Point> getLineBetweenImpl(
	    final UMLArtifact firstUMLArtifact,
	    final UMLArtifact secondUMLArtifact) {
	final ArrayList<Point> pointList = new ArrayList<Point>();
	pointList
		.add(getPointForLine(firstUMLArtifact, new Point(
			secondUMLArtifact.getCenterX(), secondUMLArtifact
				.getCenterY())));
	pointList.add(getPointForLine(secondUMLArtifact, new Point(
		firstUMLArtifact.getCenterX(), firstUMLArtifact.getCenterY())));
	return pointList;
    }

    @Override
    public Point getPointForLineImpl(final UMLArtifact uMLArtifact,
	    final Point targetCenter) {
	final Point targetInFrameReference = new Point(targetCenter.getX()
		- uMLArtifact.getX(), targetCenter.getY() - uMLArtifact.getY());
	final Point point = new Point(0, 0);
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
	return new Point(point.getX() + uMLArtifact.getX(), point.getY()
		+ uMLArtifact.getY());
    }
}
