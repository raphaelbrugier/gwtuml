/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

import com.allen_sauer.gwt.log.client.Log;

/**
 * This useful simple class represent a geometric 2D point <br> 
 * It avoids to have all the time two parameters x and y for locations <br>
 * It also provides some elementary operations
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class Point {
    /**
     * This class method create a new point from the sum of the two {@link Point}s in parameter
     * 
     * @param p1 First {@link Point} to add 
     * @param p2 Second {@link Point} to add
     * @return a new {@link Point} which is the result of the sum of the two other {@link Point}s coordinates. 
     */
    public static Point add(final Point p1, final Point p2) {
	return new Point(p1.x + p2.x, p1.y + p2.y);
    }
    /**
     * This class method create a new point from the minimum of each coordinate of the two {@link Point}s in parameter
     * 
     * @param p1 First {@link Point}
     * @param p2 Second {@link Point}
     * @return a new {@link Point} which is composed by the minimum of each coordinate of the two {@link Point}s
     */
    public static Point min(final Point p1, final Point p2) {
	return new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
    }
    /**
     * This class method create a new point from the maximum of each coordinate of the two {@link Point}s in parameter
     * 
     * @param p1 First {@link Point}
     * @param p2 Second {@link Point}
     * @return a new {@link Point} which is composed by the maximum of each coordinate of the two {@link Point}s
     */
    public static Point max(final Point p1, final Point p2) {
	return new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
    }
    /**
     * This class method create a new point from the subtraction of the two {@link Point}s in parameter
     * 
     * @param p1 First {@link Point}
     * @param p2 Second {@link Point} to subtract from the first
     * @return a new {@link Point} which is the result of the subtraction of the two other {@link Point}s coordinates. 
     */
    public static Point subtract(final Point p1, final Point p2) {
	return new Point(p1.x - p2.x, p1.y - p2.y);
    }   
    /**
     * This class method create a new point which is the middle of the two {@link Point}s in parameter
     * 
     * @param p1 First {@link Point}
     * @param p2 Second {@link Point}
     * @return a new {@link Point} which is the middle of the two other {@link Point}s coordinates. 
     */
    public static Point getMiddleOf(final Point p1, final Point p2) {
	return new Point((p1.x + p2.x) / 2, (p1.y + p2.y)/2);
    }

    /**
     * This class method simply return the origin : a new Point with coordinates at (0, 0)  
     * 
     * @return a new Point with origin coordinates
     */
    public static Point getOrigin() {
	return new Point(0,0);
    }

    private int x;
    private int y;

    /**
     * Constructor of Point that round {@link Double}s to {@link Integer}s
     *
     * @param x The point abscissa
     * @param y The point ordinate
     */
    public Point(final double x, final double y) {
	this.x = (int) Math.round(x);
	this.y = (int) Math.round(y);
    }
    /**
     * Constructor of Point that round {@link Double}s to {@link Integer}s
     *
     * @param x The point abscissa
     * @param y The point ordinate
     */
    public Point(final double x, final int y) {
	this.x = (int) Math.round(x);
	this.y = y;
    }
    /**
     * Constructor of Point that round {@link Double}s to {@link Integer}s
     *
     * @param x The point abscissa
     * @param y The point ordinate
     */
    public Point(final int x, final double y) {
	this.x = x;
	this.y = (int) Math.round(y);
    }
    /**
     * Constructor of Point
     *
     * @param x The point abscissa
     * @param y The point ordinate
     */
    public Point(final int x, final int y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Getter for the abscissa
     * 
     * @return the point abscissa
     */
    public int getX() {
	return this.x;
    }

    /**
     * Getter of the ordinate
     * 
     * @return the point ordinate
     */
    public int getY() {
	return this.y;
    }

    /**
     * Set the point abscissa
     * 
     * @param x
     *            The abscissa
     * 
     */
    public void setX(final int x) {
	this.x = x;
    }

    /**
     * Set the point ordinate
     * 
     * @param y
     *            The ordinate
     * 
     */
    public void setY(final int y) {
	this.y = y;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "(" + this.x + "," + this.y + ")";
    }

    /**
     * Move a point by a rounded approximation of dx and dy pixel (ie x+=dx and
     * y+=dy)
     * 
     * @param dx
     *            the abscissa offset
     * @param dy
     *            the ordinate offset
     */
    public void translate(final double dx, final double dy) {
	this.x += Math.round(dx);
	this.y += Math.round(dy);
    }

    /**
     * Move a point by dx and dy pixel (ie x+=dx and y+=dy)
     * 
     * @param dx
     *            the abscissa offset
     * @param dy
     *            the ordinate offsetRel
     */
    public void translate(final int dx, final int dy) {
	this.x += dx;
	this.y += dy;
    }
    
    /**
     * Move a point by offset.getX() and offset.getY() pixel (ie x+=offset.getX() and y+=offset.getY())
     * 
     * @param offset the coordinate offset
     */
    public void translate(final Point offset) {
	this.x += offset.getX();
	this.y += offset.getY();
    }
    
    /**
     * Point comparator, test if the abscissas and the ordinates are equals
     * 
     * @param point The point to compare with
     * @return <ul>
     *         <li><b>True</b> if the abscissas and the ordinates are equals</li>
     *         <li><b>False</b> otherwise</li>
     *         </ul>
     */
    public boolean equals(Point point) {
	return this.x == point.x && this.y == point.y;
    }
    
    /**
     * Point comparator, test if the abscissas and the ordinates of this point are BOTH superior to the parameter point
     * 
     * @param point The point to compare with
     * @return <ul>
     *         <li><b>True</b> if the abscissas and the ordinates are BOTH superiors</li>
     *         <li><b>False</b> otherwise</li>
     *         </ul>
     */
    public boolean isSuperiorTo(Point point) {
	return this.x >= point.x && this.y >= point.y;
    }

    /**
     * Clone a point
     * 
     * @return The clone of this point
     */
    public Point clonePoint() {
	return new Point(this.x, this.y);
    }
    /**
     * This method tests if the point is at the origin
     * 
     * @return True if x and y are equals to 0;
     */
    public boolean isOrigin() {
	return (this.x == 0 && this.y == 0);
    }
    /**
     * Parse a {@link Point} from a String created with toString()
     * 
     * @param pointString The String containing the point in the form : (x,y) 
     * @return A new {@link Point} containing the coordinates read from the String or the origin if there is a problem
     */
    public static Point parse(String pointString) {
	if(pointString.matches("\\([0-9]+,[0-9]+\\)")) {
	    String[] coordinates = pointString.replaceAll("[\\(\\)]", "").split(",");
	    return new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));	    
	}
	return Point.getOrigin();
    }
}
