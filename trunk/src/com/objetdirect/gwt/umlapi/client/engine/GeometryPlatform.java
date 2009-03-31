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

	public GfxObject buildArrow(int x1, int y1, int x2, int y2) {
		GfxObject path = GfxManager.getPlatform().buildPath();
		int[] points = getArrowPoints(x1, y1, x2, y2, OptionsManager.getArrowWidth(),
				OptionsManager.getArrowLenght());
		GfxManager.getPlatform().moveTo(path, points[0], points[1]);
		GfxManager.getPlatform().lineTo(path, x1, y1);
		GfxManager.getPlatform().lineTo(path, points[2], points[3]);
		GfxManager.getPlatform().lineTo(path, x1, y1);
		GfxManager.getPlatform().setStroke(path,
				ThemeManager.getForegroundColor(), 1);
		return path;
	}
	public GfxObject buildFilledArrow(int x1, int y1, int x2, int y2) {
		GfxObject path = GfxManager.getPlatform().buildPath();
		int[] points = getArrowPoints(x1, y1, x2, y2,
				OptionsManager.getFilledArrowWidth(), OptionsManager.getFilledArrowLenght());
		GfxManager.getPlatform().moveTo(path, x1, y1);
		GfxManager.getPlatform().lineTo(path, points[0], points[1]);
		GfxManager.getPlatform().lineTo(path, points[2], points[3]);
		GfxManager.getPlatform().lineTo(path, x1, y1);
		GfxManager.getPlatform().setStroke(path,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setFillColor(path,
				ThemeManager.getBackgroundColor());
		return path;
	}
	
	public int[] getArrowPoints(int x1, int y1, int x2, int y2, int d,
			int D) {
		float r = (float) Math.sqrt((((x2 - x1) * (x2 - x1) + (y2 - y1)
				* (y2 - y1))));
		float xf = ((x2 - x1)) / r;
		float yf = ((y2 - y1)) / r;
		return new int[] {  (int) (xf * D - yf * d + x1),  (int) (yf * D + xf * d + y1),
				 (int) (xf * D + yf * d + x1),  (int) (yf * D - xf * d + y1) };
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
