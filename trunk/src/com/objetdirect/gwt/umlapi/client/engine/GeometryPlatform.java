/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author florian
 *
 */
public abstract class GeometryPlatform {

	public GfxObject buildArrow(Point point1, Point point2) {
		GfxObject path = GfxManager.getPlatform().buildPath();
		ArrayList<Point> points = getArrowPoints(point1, point2, OptionsManager.getArrowWidth(),
				OptionsManager.getArrowLenght());
		GfxManager.getPlatform().moveTo(path, points.get(0).getX(), points.get(0).getY());
		GfxManager.getPlatform().lineTo(path, point1.getX(), point1.getY());
		GfxManager.getPlatform().lineTo(path, points.get(1).getX(), points.get(1).getY());
		GfxManager.getPlatform().setStroke(path,
				ThemeManager.getForegroundColor(), 1);
		return path;
	}
	public GfxObject buildFilledArrow(Point point1, Point point2) {
		GfxObject path = GfxManager.getPlatform().buildPath();
		ArrayList<Point> points = getArrowPoints(point1, point2,
				OptionsManager.getFilledArrowWidth(), OptionsManager.getFilledArrowLenght());
		GfxManager.getPlatform().moveTo(path, point1.getX(), point1.getY());
		
		for(Point point : points) {
		    GfxManager.getPlatform().lineTo(path, point.getX(), point.getY());
		}
	
		GfxManager.getPlatform().lineTo(path, point1.getX(), point1.getY());
		
		GfxManager.getPlatform().setStroke(path, ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(path,	ThemeManager.getBackgroundColor());
		return path;
	}
	
	public ArrayList<Point> getArrowPoints(Point point1, Point point2, int width, int lenght) {
	    ArrayList<Point> arrowPoints = new ArrayList<Point>();
	    
	    int xDiff = point2.getX() - point1.getX();
	    int yDiff = point2.getY() - point1.getY();
		double root = Math.sqrt(((xDiff^2 + yDiff^2)));
		int xf = (int) Math.round(((double) xDiff) / root);
		int yf = (int) Math.round(((double) yDiff) / root);
		
		arrowPoints.add(new Point(xf * lenght - yf * width + point1.getX(), yf * lenght + xf * width + point1.getY()));
		arrowPoints.add(new Point(xf * lenght + yf * width + point1.getX(), yf * lenght - xf * width + point1.getY()));
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
