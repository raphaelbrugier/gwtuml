/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment.Shape;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

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
     * @param target The {@link Point} where the adornment is to be built
     * @param origin The {@link Point} of the other side of the link
     * @param adornment The {@link LinkAdornment} that defines which adornment must be drawn
     * @return The graphical object containing a link adornment 
     */
    public GfxObject buildAdornment(final Point target, final Point origin,
	    final LinkAdornment adornment) {
	final GfxObject path = GfxManager.getPlatform().buildPath();
	int width = 0, lenght = 0;
	GfxColor foreColor, backColor;
	foreColor = ThemeManager.getForegroundColor();
	if (adornment.isInverted()) {
	    backColor = ThemeManager.getForegroundColor();
	} else {
	    backColor = ThemeManager.getBackgroundColor();
	}

	switch (adornment.getShape()) {
	case DIAMOND:
	    width = OptionsManager.getDiamondWidth();
	    lenght = OptionsManager.getDiamondLength();
	    break;
	case ARROW:
	    if (adornment.isSolid()) {
		width = OptionsManager.getSolidArrowWidth();
		lenght = OptionsManager.getSolidArrowLenght();
	    } else {
		width = OptionsManager.getArrowWidth();
		lenght = OptionsManager.getArrowLenght();
	    }
	    break;
	}

	final ArrayList<Point> points = getAdornmentPoints(target, origin,
		width, lenght);

	GfxManager.getPlatform().moveTo(path, points.get(0));
	GfxManager.getPlatform().lineTo(path, target);
	GfxManager.getPlatform().lineTo(path, points.get(1));
	if (adornment == LinkAdornment.WIRE_ARROW) {
	    GfxManager.getPlatform().lineTo(path, target);
	} else {
	    if (adornment.getShape() == Shape.DIAMOND) {
		GfxManager.getPlatform().lineTo(path, points.get(2));
	    }
	    GfxManager.getPlatform().lineTo(path, points.get(0));
	}
	GfxManager.getPlatform().setStroke(path, foreColor, 1);
	GfxManager.getPlatform().setFillColor(path, backColor);
	return path;
    }


    /**
     * This method return an array of point containing the intersection <br>
     * between a line between two artifact centers and these artifacts
     * 
     * @param firstUMLArtifact The {@link UMLArtifact} from which the line starts  
     * @param secondUMLArtifact The {@link UMLArtifact} from which the line ends
     * 
     * @return The two intersection {@link Point}s to draw the line in an {@link ArrayList}
     */
    public ArrayList<Point> getLineBetween(final UMLArtifact firstUMLArtifact,
	    final UMLArtifact secondUMLArtifact) {
	final long t = System.currentTimeMillis();
	final ArrayList<Point> pointList = getLineBetweenImpl(firstUMLArtifact,
		secondUMLArtifact);
	Log.debug("([" + (System.currentTimeMillis() - t)
		+ "ms]) to compute line between " + firstUMLArtifact + " and "
		+ secondUMLArtifact);
	return pointList;
    }

    /**
     * This method return an array of point containing the intersection <br>
     * between a line between an artifact center and a point and this artifact
     * 
     * @param uMLArtifact The {@link UMLArtifact} from which the line starts  
     * @param point The {@link Point} where the line ends
     * 
     * @return The intersection {@link Point}
     */
    public Point getPointForLine(final UMLArtifact uMLArtifact,
	    final Point point) {
	final long t = System.currentTimeMillis();
	final Point pt = getPointForLineImpl(uMLArtifact, point);
	Log.debug("([" + (System.currentTimeMillis() - t)
		+ "ms]) to compute line between " + uMLArtifact
		+ " and a point");
	return pt;
    }

    /**
     * This method calculates the point to draw a reflexive link (between a {@link ClassArtifact} and itself)  
     * @param classArtifact The {@link ClassArtifact} which the reflexive link belongs
     * @return An {@link ArrayList} of the {@link Point}s computed to draw the path
     */
    public ArrayList<Point> getReflexiveLineFor(
	    final ClassArtifact classArtifact) {
	final Point center = classArtifact.getCenter();
	final int halfClassWidth = classArtifact.getWidth() / 2;
	final int halfClassHeight = classArtifact.getHeight() / 2;
	final ArrayList<Point> pointList = new ArrayList<Point>();
	final Point point0 = center.clone();
	point0.translate(halfClassWidth, 0);
	final Point point1 = point0.clone();
	point1.translate(OptionsManager.getReflexivePathXGap(), 0);
	final Point point2 = point1.clone();
	point2.translate(0, -halfClassHeight
		- OptionsManager.getReflexivePathYGap());
	final Point point3 = point2.clone();
	point3.translate(-halfClassWidth
		- OptionsManager.getReflexivePathXGap(), 0);
	final Point point4 = point3.clone();
	point4.translate(0, OptionsManager.getReflexivePathYGap());
	pointList.add(point0);
	pointList.add(point1);
	pointList.add(point2);
	pointList.add(point3);
	pointList.add(point4);
	return pointList;
    }

    protected abstract ArrayList<Point> getLineBetweenImpl(
	    UMLArtifact firstUMLArtifact, UMLArtifact secondUMLArtifact);

    protected abstract Point getPointForLineImpl(UMLArtifact uMLArtifact,
	    Point point);

    private ArrayList<Point> getAdornmentPoints(final Point point1,
	    final Point point2, final int width, final int lenght) {
	final ArrayList<Point> arrowPoints = new ArrayList<Point>();

	double xDiff = point2.getX() - point1.getX();
	if (xDiff == 0.) {
	    xDiff = 0.00000001;
	}
	final double yDiff = point2.getY() - point1.getY();
	final double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff,
		2)));
	final double slope = yDiff / xDiff;
	final double yShift = width / 2. / Math.sqrt(1. + Math.pow(slope, 2));
	final double xShift = slope * yShift;
	final Point root = getArrowRoot(point1, xDiff, yDiff, distance, lenght);
	final Point up = root.clone();
	final Point down = root.clone();
	final Point diamondTail = Point.subtract(root, Point.subtract(point1,
		root));
	final Point crossUp = Point
		.subtract(up, Point.subtract(point1, root));
	final Point crossDown = Point.subtract(down, Point.subtract(point1,
		root));
	up.translate(-xShift, yShift);
	down.translate(xShift, -yShift);
	arrowPoints.add(up);
	arrowPoints.add(down);
	arrowPoints.add(diamondTail);
	arrowPoints.add(crossUp);
	arrowPoints.add(crossDown);
	return arrowPoints;
    }
    
    //         . origin
    //   \     ^
    //    \   /  \
    //     \ /    \
    //      /°root \
    //     /        \
    //    /__________\. target
    private Point getArrowRoot(final Point target, final double xDiff,
	    final double yDiff, final double distance, final int lenght) {
	final double thalesConst = lenght / distance;
	final Point root = target.clone();
	root.translate(xDiff * thalesConst, yDiff * thalesConst);
	return root;
    }

}
