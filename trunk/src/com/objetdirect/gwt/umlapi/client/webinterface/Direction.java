/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public enum Direction {
    DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0), UP(0, -1);

    private int xDirection;
    private int yDirection;

    private Direction(final int x, final int y) {
	this.xDirection = x;
	this.yDirection = y;
    }

    public int getXDirection() {
	return this.xDirection;
    }

    public int getYDirection() {
	return this.yDirection;
    }

}
