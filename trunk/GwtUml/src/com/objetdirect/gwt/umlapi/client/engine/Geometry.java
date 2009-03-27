package com.objetdirect.gwt.umlapi.client.engine;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
public class Geometry {
	static final int ARROW_LENGTH = 15;
	static final int ARROW_WIDTH = 5;
	static final int FILLED_ARROW_LENGTH = 25;
	static final int FILLED_ARROW_WIDTH = 10;
	public static GfxObject buildArrow(int x1, int y1, int x2, int y2) {
		GfxObject path = GfxManager.getPlatform().buildPath();
		int[] points = Geometry.getArrowPoints(x1, y1, x2, y2, ARROW_WIDTH,
				ARROW_LENGTH);
		GfxManager.getPlatform().moveTo(path, points[0], points[1]);
		GfxManager.getPlatform().lineTo(path, x1, y1);
		GfxManager.getPlatform().lineTo(path, points[2], points[3]);
		GfxManager.getPlatform().lineTo(path, x1, y1);
		GfxManager.getPlatform().setStroke(path,
				ThemeManager.getForegroundColor(), 1);
		return path;
	}
	public static GfxObject buildFilledArrow(int x1, int y1, int x2, int y2) {
		GfxObject path = GfxManager.getPlatform().buildPath();
		int[] points = Geometry.getArrowPoints(x1, y1, x2, y2,
				FILLED_ARROW_WIDTH, FILLED_ARROW_LENGTH);
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
/*	public static int[] computeLineBounds(int x1, int y1, int[] zone1,
			int x2, int y2) {
		int[] line = new int[] { x1, y1, x2, y2 };
		int[] segment1 = getInternalSegment(zone1, line);
		return new int[] { segment1[2], segment1[3], x2, y2 };
	}
	public static int[] computeLineBounds(int x1, int y1, int[] zone1,
			int x2, int y2, int[] zone2) {
		int[] line = new int[] { x1, y1, x2, y2 };
		int[] result = new int[] { x1, y1, x2, y2 };
		if (zone1 != null) {
			int[] segment = getInternalSegment(zone1, line);
			result[0] = segment[2];
			result[1] = segment[3];
		}
		if (zone2 != null) {
			int[] segment = getInternalSegment(zone2, line);
			result[2] = segment[0];
			result[3] = segment[1];
		}
		return result;
	}
	public static int[] computeLineBounds(UMLArtifact art1, int x2, int y2) {
		return computeLineBounds(art1.getCenterX(), art1.getCenterY(), art1
				.getOpaque(), x2, y2);
	}
	public static int[] computeLineBounds(UMLArtifact art1, UMLArtifact art2) {
		return computeLineBounds(art1.getCenterX(), art1.getCenterY(), art1
				.getOpaque(), art2.getCenterX(), art2.getCenterY(), art2
				.getOpaque());
	}*/
	public static int[] getArrowPoints(int x1, int y1, int x2, int y2, int d,
			int D) {
		float r = (float) Math.sqrt((((x2 - x1) * (x2 - x1) + (y2 - y1)
				* (y2 - y1))));
		float xf = ((x2 - x1)) / r;
		float yf = ((y2 - y1)) / r;
		return new int[] {  (int) (xf * D - yf * d + x1),  (int) (yf * D + xf * d + y1),
				 (int) (xf * D + yf * d + x1),  (int) (yf * D - xf * d + y1) };
	}
/*
	public static float[] getIntermediatePoint(int x1, int y1, int x2, int y2,
			int D) {
		float r = (float) Math.sqrt((((x2 - x1) * (x2 - x1) + (y2 - y1)
				* (y2 - y1))));
		float xf = ((x2 - x1)) / r;
		float yf = ((y2 - y1)) / r;
		return new float[] { xf * D + x1, yf * D + y1 };
	}
	static public int[] getInternalSegment(int[] shape, int[] line) {
		float tlow = 0.0f;
		float tup = 1.0f;
		float[] direction = new float[] { line[2] - line[0], line[3] - line[1] };
		for (int i = 0; i < shape.length >> 1; i++) {
			float[] ponderation = new float[] { line[0] - shape[i << 1],
					line[1] - shape[(i << 1) + 1] };
			float[] normal = new float[] {
					shape[((i << 1) + 3) % shape.length]
							- shape[((i << 1) + 1) % shape.length],
					-shape[((i << 1) + 2) % shape.length]
							+ shape[(i << 1) % shape.length] };
			float dscaln = direction[0] * normal[0] + direction[1] * normal[1];
			float wscaln = ponderation[0] * normal[0] + ponderation[1]
					* normal[1];
			if (dscaln == 0.0) {
				if (wscaln < 0.0)
					return null;
			} else {
				float t = -wscaln / dscaln;
				if (dscaln < 0.0) {
					if (t >= 0.0 && t < tup)
						tup = t;
				} else {
					if (t <= 1.0 && t > tlow)
						tlow = t;
				}
			}
		}
		if (tlow >= tup)
			return null;
		else
			return new int[] {  (int) (line[0] + direction[0] * tlow),
					 (int) (line[1] + direction[1] * tlow),
					 (int) (line[0] + direction[0] * tup),  (int) (line[1] + direction[1] * tup) };
	}
*/
	public static GfxObject getLineBetween(UMLArtifact leftUMLArtifact,
			UMLArtifact rightUMLArtifact) {		
		Point lineLeftPoint  = getPointForLine(leftUMLArtifact, new Point(rightUMLArtifact.getCenterX(), rightUMLArtifact.getCenterY()));
		Point lineRightPoint = getPointForLine(rightUMLArtifact, new Point(leftUMLArtifact.getCenterX(), leftUMLArtifact.getCenterY()));
		return GfxManager.getPlatform().buildLine(lineLeftPoint.getX(), lineLeftPoint.getY(), lineRightPoint.getX(), lineRightPoint.getY());
		
	}
		public static Point getPointForLine(UMLArtifact umlArtifact, Point targetCenter) {
			Point targetInFrameReference = new Point(targetCenter.getX() - umlArtifact.getX(), targetCenter.getY() - umlArtifact.getY());
			Point point = new Point(0,0);
			final int constA = umlArtifact.getHeight() * targetInFrameReference.getX();
			final int constB = umlArtifact.getWidth() * targetInFrameReference.getY();
			final int constC = umlArtifact.getHeight() * umlArtifact.getWidth();
			if(constA > constB) {
				if(constA > constC - constB) {
					point.setX(umlArtifact.getWidth());
					point.setY((constC - constB - constA) / (umlArtifact.getWidth() - 2 * targetInFrameReference.getX()));
				} 
				else {
					point.setX((constA - constB) / (umlArtifact.getHeight() - 2 * targetInFrameReference.getY()));
					point.setY(0);
				}
			}
			else {
				if(constA > constC - constB) {
					point.setX((constC + constA - constB - 2 * umlArtifact.getHeight() * targetInFrameReference.getX()) / (umlArtifact.getHeight() - 2 * targetInFrameReference.getY()));
					point.setY(umlArtifact.getHeight());
				} 
				else {
					point.setX(0);
					point.setY((constB - constA) / (umlArtifact.getWidth() - 2 * targetInFrameReference.getX()));		
				}
			}
		return new Point(point.getX() + umlArtifact.getX(), point.getY() + umlArtifact.getY());
	}
}
