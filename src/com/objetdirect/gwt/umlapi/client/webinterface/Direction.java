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
	xDirection = x;
	yDirection = y;
    }

    public int getXDirection() {
	return xDirection;
    }

    public int getYDirection() {
	return yDirection;
    }

}
