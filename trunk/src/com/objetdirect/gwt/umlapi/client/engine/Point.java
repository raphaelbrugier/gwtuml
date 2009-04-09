/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com) 
 */
/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class Point {
    public static Point add(final Point p1, final Point p2) {
	return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    public static Point min(final Point p1, final Point p2) {
	return new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
    }

    public static Point substract(final Point p1, final Point p2) {
	return new Point(p1.x - p2.x, p1.y - p2.y);
    }
    public static Point substract(final Point p1, final Point p2,  final Point p3) {
	return new Point(p1.x - p2.x - p3.x, p1.y - p2.y - p3.y);
    }
    public static Point getOrigin() {
	return new Point(0,0);
    }
    /**
     * 
     */
    private int x;
    /**
     * 
     */
    private int y;

    public Point(final double x, final double y) {
	this.x = (int) x;
	this.y = (int) y;
    }

    public Point(final double x, final int y) {
	this.x = (int) x;
	this.y = y;
    }

    public Point(final int x, final double y) {
	this.x = x;
	this.y = (int) y;
    }

    public Point(final int x, final int y) {
	this.x = x;
	this.y = y;
    }

    public Point(final Point point) {
	this.x = point.getX();
	this.y = point.getY();
    }

    /**
     * @return the point abscissa
     * 
     */
    public int getX() {
	return this.x;
    }

    /**
     * @return the point ordinate
     * 
     */
    public int getY() {
	return this.y;
    }

    /**
     * Set the point abscissa
     * 
     * @param x
     *            the abscissa
     * 
     */
    public void setX(final int x) {
	this.x = x;
    }

    /**
     * Set the point ordinate
     * 
     * @param y
     *            the ordinate
     * 
     */
    public void setY(final int y) {
	this.y = y;
    }

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
     *            the ordinate offset
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
    public boolean equals(Point point) {
	return this.x == point.x && this.y == point.y;
    }
}
