/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.engine;

/**
 * @author  fmounier
 */
public class Point {

	/**
	 * @uml.property  name="x"
	 */
	private int x;
	/**
	 * @uml.property  name="y"
	 */
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * @return
	 * @uml.property  name="x"
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x
	 * @uml.property  name="x"
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return
	 * @uml.property  name="y"
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y
	 * @uml.property  name="y"
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}
