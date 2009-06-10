/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.engine;

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
	 * Get the absolute value of the two coordinates
	 * 
	 * @param point
	 *            The point to get the absolute coordinates
	 * @return A new {@link Point} containing the absolute coordinates
	 */
	public static Point abs(final Point point) {
		return new Point(Math.abs(point.getX()), Math.abs(point.getY()));
	}

	/**
	 * This class method create a new point from the sum of the two {@link Point}s in parameter
	 * 
	 * @param p1
	 *            First {@link Point} to add
	 * @param p2
	 *            Second {@link Point} to add
	 * @return a new {@link Point} which is the result of the sum of the two other {@link Point}s coordinates.
	 */
	public static Point add(final Point p1, final Point p2) {
		return new Point(p1.x + p2.x, p1.y + p2.y);
	}

	/**
	 * This class method create a new point which is the middle of the two {@link Point}s in parameter
	 * 
	 * @param p1
	 *            First {@link Point}
	 * @param p2
	 *            Second {@link Point}
	 * @return a new {@link Point} which is the middle of the two other {@link Point}s coordinates.
	 */
	public static Point getMiddleOf(final Point p1, final Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}

	/**
	 * This class method simply return the origin : a new Point with coordinates at (0, 0)
	 * 
	 * @return a new Point with origin coordinates
	 */
	public static Point getOrigin() {
		return new Point(0, 0);
	}

	/**
	 * This class method create a new point from the maximum of each coordinate of the two {@link Point}s in parameter
	 * 
	 * @param p1
	 *            First {@link Point}
	 * @param p2
	 *            Second {@link Point}
	 * @return a new {@link Point} which is composed by the maximum of each coordinate of the two {@link Point}s
	 */
	public static Point max(final Point p1, final Point p2) {
		return new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
	}

	/**
	 * This class method create a new point from the minimum of each coordinate of the two {@link Point}s in parameter
	 * 
	 * @param p1
	 *            First {@link Point}
	 * @param p2
	 *            Second {@link Point}
	 * @return a new {@link Point} which is composed by the minimum of each coordinate of the two {@link Point}s
	 */
	public static Point min(final Point p1, final Point p2) {
		return new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
	}

	/**
	 * Parse a {@link Point} from a String created with toString()
	 * 
	 * @param pointString
	 *            The String containing the point in the form : (x,y)
	 * @return A new {@link Point} containing the coordinates read from the String or the origin if there is a problem
	 */
	public static Point parse(final String pointString) {
		if (pointString.matches("\\(\\-?[0-9]+,\\-?[0-9]+\\)")) {
			final String[] coordinates = pointString.replaceAll("[\\(\\)]", "").split(",");
			return new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
		}
		return Point.getOrigin();
	}

	/**
	 * This class method create a new point from the subtraction of the two {@link Point}s in parameter
	 * 
	 * @param p1
	 *            First {@link Point}
	 * @param p2
	 *            Second {@link Point} to subtract from the first
	 * @return a new {@link Point} which is the result of the subtraction of the two other {@link Point}s coordinates.
	 */
	public static Point substract(final Point p1, final Point p2) {
		return new Point(p1.x - p2.x, p1.y - p2.y);
	}

	private int	x;
	private int	y;

	/**
	 * Constructor of Point that round {@link Double}s to {@link Integer}s
	 * 
	 * @param x
	 *            The point abscissa
	 * @param y
	 *            The point ordinate
	 */
	public Point(final double x, final double y) {
		super();
		this.x = (int) Math.round(x);
		this.y = (int) Math.round(y);
	}

	/**
	 * Constructor of Point that round {@link Double}s to {@link Integer}s
	 * 
	 * @param x
	 *            The point abscissa
	 * @param y
	 *            The point ordinate
	 */
	public Point(final double x, final int y) {
		super();
		this.x = (int) Math.round(x);
		this.y = y;
	}

	/**
	 * Constructor of Point that round {@link Double}s to {@link Integer}s
	 * 
	 * @param x
	 *            The point abscissa
	 * @param y
	 *            The point ordinate
	 */
	public Point(final int x, final double y) {
		super();
		this.x = x;
		this.y = (int) Math.round(y);
	}

	/**
	 * Constructor of Point
	 * 
	 * @param x
	 *            The point abscissa
	 * @param y
	 *            The point ordinate
	 */
	public Point(final int x, final int y) {
		super();
		this.x = x;
		this.y = y;
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
	 * Divide the coordinates of a point by the specified factor
	 * 
	 * @param divisor
	 *            The divisor to divide by
	 */
	public void divideBy(final int divisor) {
		this.x /= divisor;
		this.y /= divisor;
	}

	/**
	 * Point comparator, test if the abscissas and the ordinates are equals
	 * 
	 * @param point
	 *            The point to compare with
	 * @return <ul>
	 *         <li><b>True</b> if the abscissas and the ordinates are equals</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean equals(final Point point) {
		return (this.x == point.x) && (this.y == point.y);
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
	 * This method tests if the point is at the origin
	 * 
	 * @return True if x and y are equals to 0;
	 */
	public boolean isOrigin() {
		return ((this.x == 0) && (this.y == 0));
	}

	/**
	 * Point comparator, test if the abscissas and the ordinates of this point are BOTH superior to the parameter point
	 * 
	 * @param point
	 *            The point to compare with
	 * @return <ul>
	 *         <li><b>True</b> if the abscissas and the ordinates are BOTH superiors</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isSuperiorTo(final Point point) {
		return (this.x >= point.x) && (this.y >= point.y);
	}

	/**
	 * Multiply the coordinates of a point by the specified factor
	 * 
	 * @param factor
	 *            The factor to multiply by
	 */
	public void multiplyBy(final int factor) {
		this.x *= factor;
		this.y *= factor;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

	/**
	 * Move a point by a rounded approximation of dx and dy pixel (ie x+=dx and y+=dy)
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
	 * 
	 * @return This point
	 */
	public Point translate(final int dx, final int dy) {
		this.x += dx;
		this.y += dy;
		return this;
	}

	/**
	 * Move a point by offset.getX() and offset.getY() pixel (ie x+=offset.getX() and y+=offset.getY())
	 * 
	 * @param offset
	 *            the coordinate offset
	 */
	public void translate(final Point offset) {
		this.x += offset.getX();
		this.y += offset.getY();
	}
}
