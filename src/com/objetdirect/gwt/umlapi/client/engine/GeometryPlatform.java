/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author florian
 *
 */
public abstract class GeometryPlatform {


	public GfxObject buildArrow(Point target, Point origin, LinkAdornment adornment) {
	    
	    GfxObject path = GfxManager.getPlatform().buildPath();
	    
		ArrayList<Point> points = getArrowPoints(target, origin, OptionsManager.getArrowWidth(), OptionsManager.getArrowLenght());
		GfxManager.getPlatform().moveTo(path, points.get(0).getX(), points.get(0).getY());
		GfxManager.getPlatform().lineTo(path, target.getX(), target.getY());
		GfxManager.getPlatform().lineTo(path, points.get(1).getX(), points.get(1).getY());
		if(adornment == LinkAdornment.ARROW)
		GfxManager.getPlatform().lineTo(path, target.getX(), target.getY());      
		if(adornment == LinkAdornment.WHITE_DIAMOND)
            GfxManager.getPlatform().lineTo(path, points.get(2).getX(), points.get(2).getY());
		if(adornment == LinkAdornment.WHITE_ARROW || adornment == LinkAdornment.WHITE_DIAMOND)
		    GfxManager.getPlatform().lineTo(path, points.get(0).getX(), points.get(0).getY());
		GfxManager.getPlatform().setStroke(path, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(path, ThemeManager.getBackgroundColor());
		return path;
	}
	
	/*public GfxObject buildFilledArrow(Point point1, Point point2) {
		GfxObject path = GfxManager.getPlatform().buildPath();
		ArrayList<Point> points = getArrowPoints(point1, point2, OptionsManager.getFilledArrowWidth(), OptionsManager.getFilledArrowLenght());
		GfxManager.getPlatform().moveTo(path, point1.getX(), point1.getY());
		
		for(Point point : points) {
		    GfxManager.getPlatform().lineTo(path, point.getX(), point.getY());
		}
	
		GfxManager.getPlatform().lineTo(path, point1.getX(), point1.getY());
		
		GfxManager.getPlatform().setStroke(path, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(path,	ThemeManager.getBackgroundColor());
		return path;
	}    */    

	//. origin
	// \     ^
	//  \  /  \
	//   \/    \
	//   /°root \
	//  /        \
	// /__________\. target
	
	public Point getArrowRoot(Point target, double xDiff, double yDiff, double distance, int lenght) {
	    final double thalesConst = lenght / distance;
	    Point root = new Point(target);
	    root.translate(xDiff * thalesConst, yDiff * thalesConst);
	    return root; 
	}
	
	
	//       ^
    //     /  \
    //    /    \
    //   /      \
    //  /        \
    // /__________\.
	//      |
	//      |
	//      |
	//      |
	//
	public ArrayList<Point> getArrowPoints(Point point1, Point point2, int width, int lenght) {
	    ArrayList<Point> arrowPoints = new ArrayList<Point>();
   
	    final double xDiff = point2.getX() - point1.getX();
	    final double yDiff = point2.getY() - point1.getY();	    
	    final double distance = Math.sqrt(((Math.pow(xDiff, 2) + Math.pow(yDiff, 2))));
	    final double slope = yDiff / xDiff; 
	    final double yShift = (width / 2.) / Math.sqrt(1. + Math.pow(slope, 2));
	    final double xShift = slope * yShift;
	    Log.fatal(xShift + " " + yShift);
		Point root = getArrowRoot(point1, xDiff, yDiff, distance, lenght);
		Point up = new Point(root);
		Point down = new Point(root);
		Point diamondTail = getArrowRoot(root, xDiff, yDiff, distance, lenght);
		up.translate(-xShift, yShift);
		down.translate(xShift, -yShift);
		arrowPoints.add(up); 
		arrowPoints.add(down);
		arrowPoints.add(diamondTail);
		return arrowPoints;
	}
	
	public ArrayList<Point> getLineBetween(UMLArtifact firstUMLArtifact, UMLArtifact secondUMLArtifact){
		long t = System.currentTimeMillis();
		ArrayList<Point> pointList = getLineBetweenWithTime(firstUMLArtifact, secondUMLArtifact);
		Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to compute line between " + firstUMLArtifact + " and " + secondUMLArtifact);
		return pointList;
		
		
	}
	public Point getPointForLine(UMLArtifact uMLArtifact, Point point) {
		long t = System.currentTimeMillis();
		Point pt = getPointForLineWithTime(uMLArtifact, point);
		Log.debug("([" + (System.currentTimeMillis() - t) + "ms]) to compute line between " + uMLArtifact + " and a point");
		return pt;
	}
	
	public abstract ArrayList<Point> getLineBetweenWithTime(UMLArtifact firstUMLArtifact, UMLArtifact secondUMLArtifact);
	
	public abstract Point getPointForLineWithTime(UMLArtifact uMLArtifact, Point point);
}
