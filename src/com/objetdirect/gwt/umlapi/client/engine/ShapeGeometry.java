package com.objetdirect.gwt.umlapi.client.engine;
import java.util.ArrayList;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
public class ShapeGeometry extends GeometryPlatform {

	@Override
	public ArrayList<Point> getLineBetweenImpl(UMLArtifact firstUMLArtifact,
			UMLArtifact secondUMLArtifact) {
		int[] points = computeLineBounds(firstUMLArtifact, secondUMLArtifact);
		ArrayList<Point> pointList = new ArrayList<Point>();
		pointList.add(new Point(points[0], points[1]));
		pointList.add(new Point(points[2], points[3]));
		return pointList;
	}

	@Override
	public Point getPointForLineImpl(UMLArtifact uMLArtifact, Point point) {
		int[] points = computeLineBounds(uMLArtifact, point.getX(), point.getY());
		return new Point(points[0], points[1]);
	}

	public int[] computeLineBounds(int x1, int y1, int[] zone1,
			int x2, int y2) {
		int[] line = new int[] { x1, y1, x2, y2 };
		int[] segment1 = getInternalSegment(zone1, line);
		return new int[] { segment1[2], segment1[3], x2, y2 };
	}
	
	public int[] computeLineBounds(int x1, int y1, int[] zone1,
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
	
	public int[] computeLineBounds(UMLArtifact art1, int x2, int y2) {
		return computeLineBounds(art1.getCenterX(), art1.getCenterY(), art1
				.getOpaque(), x2, y2);
	}
	
	public int[] computeLineBounds(UMLArtifact art1, UMLArtifact art2) {
		return computeLineBounds(art1.getCenterX(), art1.getCenterY(), art1
				.getOpaque(), art2.getCenterX(), art2.getCenterY(), art2
				.getOpaque());
	}


	public float[] getIntermediatePoint(int x1, int y1, int x2, int y2,
			int D) {
		float r = (float) Math.sqrt((((x2 - x1) * (x2 - x1) + (y2 - y1)
				* (y2 - y1))));
		float xf = ((x2 - x1)) / r;
		float yf = ((y2 - y1)) / r;
		return new float[] { xf * D + x1, yf * D + y1 };
	}
	
	public int[] getInternalSegment(int[] shape, int[] line) {
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

}
