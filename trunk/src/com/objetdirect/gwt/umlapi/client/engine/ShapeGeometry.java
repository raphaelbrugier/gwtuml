package com.objetdirect.gwt.umlapi.client.engine;

import java.util.ArrayList;

import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;

public class ShapeGeometry extends GeometryPlatform {

    public int[] computeLineBounds(final int x1, final int y1,
	    final int[] zone1, final int x2, final int y2) {
	final int[] line = new int[] { x1, y1, x2, y2 };
	final int[] segment1 = getInternalSegment(zone1, line);
	return new int[] { segment1[2], segment1[3], x2, y2 };
    }

    public int[] computeLineBounds(final int x1, final int y1,
	    final int[] zone1, final int x2, final int y2, final int[] zone2) {
	final int[] line = new int[] { x1, y1, x2, y2 };
	final int[] result = new int[] { x1, y1, x2, y2 };
	if (zone1 != null) {
	    final int[] segment = getInternalSegment(zone1, line);
	    result[0] = segment[2];
	    result[1] = segment[3];
	}
	if (zone2 != null) {
	    final int[] segment = getInternalSegment(zone2, line);
	    result[2] = segment[0];
	    result[3] = segment[1];
	}
	return result;
    }

    public int[] computeLineBounds(final UMLArtifact art1, final int x2,
	    final int y2) {
	return computeLineBounds(art1.getCenter().getX(), art1.getCenter()
		.getY(), art1.getOpaque(), x2, y2);
    }

    public int[] computeLineBounds(final UMLArtifact art1,
	    final UMLArtifact art2) {
	return computeLineBounds(art1.getCenter().getX(), art1.getCenter()
		.getY(), art1.getOpaque(), art2.getCenter().getX(), art2
		.getCenter().getY(), art2.getOpaque());
    }

    public float[] getIntermediatePoint(final int x1, final int y1,
	    final int x2, final int y2, final int D) {
	final float r = (float) Math.sqrt(((x2 - x1) * (x2 - x1) + (y2 - y1)
		* (y2 - y1)));
	final float xf = (x2 - x1) / r;
	final float yf = (y2 - y1) / r;
	return new float[] { xf * D + x1, yf * D + y1 };
    }

    public int[] getInternalSegment(final int[] shape, final int[] line) {
	float tlow = 0.0f;
	float tup = 1.0f;
	final float[] direction = new float[] { line[2] - line[0],
		line[3] - line[1] };
	for (int i = 0; i < shape.length >> 1; i++) {
	    final float[] ponderation = new float[] { line[0] - shape[i << 1],
		    line[1] - shape[(i << 1) + 1] };
	    final float[] normal = new float[] {
		    shape[((i << 1) + 3) % shape.length]
			    - shape[((i << 1) + 1) % shape.length],
		    -shape[((i << 1) + 2) % shape.length]
			    + shape[(i << 1) % shape.length] };
	    final float dscaln = direction[0] * normal[0] + direction[1]
		    * normal[1];
	    final float wscaln = ponderation[0] * normal[0] + ponderation[1]
		    * normal[1];
	    if (dscaln == 0.0) {
		if (wscaln < 0.0) {
		    return null;
		}
	    } else {
		final float t = -wscaln / dscaln;
		if (dscaln < 0.0) {
		    if (t >= 0.0 && t < tup) {
			tup = t;
		    }
		} else {
		    if (t <= 1.0 && t > tlow) {
			tlow = t;
		    }
		}
	    }
	}
	if (tlow >= tup) {
	    return null;
	}

	return new int[] { (int) (line[0] + direction[0] * tlow),
		(int) (line[1] + direction[1] * tlow),
		(int) (line[0] + direction[0] * tup),
		(int) (line[1] + direction[1] * tup) };
    }

    @Override
    public ArrayList<Point> getLineBetweenImpl(
	    final UMLArtifact firstUMLArtifact,
	    final UMLArtifact secondUMLArtifact) {
	final int[] points = computeLineBounds(firstUMLArtifact,
		secondUMLArtifact);
	final ArrayList<Point> pointList = new ArrayList<Point>();
	pointList.add(new Point(points[0], points[1]));
	pointList.add(new Point(points[2], points[3]));
	return pointList;
    }

    @Override
    public Point getPointForLineImpl(final UMLArtifact uMLArtifact,
	    final Point point) {
	final int[] points = computeLineBounds(uMLArtifact, point.getX(), point
		.getY());
	return new Point(points[0], points[1]);
    }

}
