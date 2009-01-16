package com.objetdirect.gwt.umlapi.client;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

public class Geometry {

	static public float[] getInternalSegment(float[] shape, float[] line) {
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
			return new float[] { line[0] + direction[0] * tlow,
					line[1] + direction[1] * tlow,
					line[0] + direction[0] * tup, line[1] + direction[1] * tup, };
	}

	public static float[] computeLineBounds(
			int x1, int y1, float[] zone1, 
			int x2, int y2) 
	{
		float[] line = new float[] {x1, y1, x2, y2};
		float[] segment1 = getInternalSegment(zone1, line);
		return new float[] { segment1[2], segment1[3], x2, y2 };
	}
	
	public static float[] computeLineBounds(
		int x1, int y1, float[] zone1,
		int x2, int y2, float[] zone2)
	{
		float[] line = new float[] {x1, y1, x2, y2};
		float[] result = new float[] {x1, y1, x2, y2};
		if (zone1!=null) {
			float[] segment = getInternalSegment(zone1, line);
			result[0] = segment[2];
			result[1] = segment[3];
		}
		if (zone2!=null) {
			float[] segment = getInternalSegment(zone2, line);
			result[2] = segment[0];
			result[3] = segment[1];
		}
		return result;
	}
	
	public static float[] computeLineBounds(
			UMLArtifact art1, 
			UMLArtifact art2) 
	{
		return computeLineBounds(
			art1.getCenterX(), art1.getCenterY(), art1.getOpaque(), 
			art2.getCenterX(), art2.getCenterY(), art2.getOpaque());
	}

	public static float[] computeLineBounds(
			UMLArtifact art1, 
			int x2, int y2) 
	{
		return computeLineBounds(
			art1.getCenterX(), art1.getCenterY(), art1.getOpaque(), 
			x2, y2);
	}

	public static float[] getArrowPoints(int x1, int y1, int x2, int y2, int d, int D) {
		float r = (float)Math.sqrt(((float)((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1))));
		float xf = ((float)(x2-x1))/r;
		float yf = ((float)(y2-y1))/r;
		return new float[] {xf*D - yf*d + x1, yf*D + xf*d + y1, xf*D + yf*d + x1, yf*D - xf*d + y1};
	}

	public static float[] getIntermediatePoint(int x1, int y1, int x2, int y2, int D) {
		float r = (float)Math.sqrt(((float)((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1))));
		float xf = ((float)(x2-x1))/r;
		float yf = ((float)(y2-y1))/r;
		return new float[] {xf*D + x1, yf*D + y1};
	}

	static final int ARROW_LENGTH = 15;
	static final int ARROW_WIDTH = 5;
	static final int FILLED_ARROW_LENGTH = 25;
	static final int FILLED_ARROW_WIDTH = 10;
	
	public static GfxObject buildArrow(int x1, int y1, int x2, int y2) {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject path = gPlatform.buildPath();
		float[] points = Geometry.getArrowPoints(x1, y1, x2, y2, ARROW_WIDTH, ARROW_LENGTH);
		gPlatform.moveTo(path, points[0], points[1]);
		gPlatform.lineTo(path, x1, y1);
		gPlatform.lineTo(path, points[2], points[3]);
		gPlatform.lineTo(path, x1, y1);
		gPlatform.setStroke(path, ThemeManager.getForegroundColor(), 1);
		return path;
	}
	
	public static GfxObject buildFilledArrow(int x1, int y1, int x2, int y2) {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject path = gPlatform.buildPath();
		float[] points = Geometry.getArrowPoints(x1, y1, x2, y2, FILLED_ARROW_WIDTH, FILLED_ARROW_LENGTH);
		gPlatform.moveTo(path, x1, y1);
		gPlatform.lineTo(path, points[0], points[1]);
		gPlatform.lineTo(path, points[2], points[3]);
		gPlatform.lineTo(path, x1, y1);
		gPlatform.setStroke(path, ThemeManager.getForegroundColor(), 1);
		gPlatform.setFillColor(path, ThemeManager.getBackgroundColor());
		return path;
	}
}
