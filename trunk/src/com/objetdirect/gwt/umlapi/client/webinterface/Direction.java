/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

/**
 * This enumeration lists all orthogonal directions and gives the movements factors
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public enum Direction {
    /**
     * Down direction
     */
    DOWN(0, 1),
    /**
     * Left direction
     */
    LEFT(-1, 0),
    /**
     * Right direction
     */
    RIGHT(1, 0),
    /**
     * Up direction
     */
    UP(0, -1);

    private int xDirection;
    private int yDirection;

    private Direction(final int x, final int y) {
	this.xDirection = x;
	this.yDirection = y;
    }

    /**
     * Getter for the abscissa direction
     *
     * @return the abscissa Direction
     */
    public final int getXDirection() {
        return this.xDirection;
    }

    /**
     * Getter for the ordinate direction
     *
     * @return the ordinate direction
     */
    public final int getYDirection() {
        return this.yDirection;
    }

    /**
     * Setter for the abscissa direction
     *
     * @param direction the abscissa direction to set
     */
    public final void setXDirection(final int direction) {
        this.xDirection = direction;
    }

    /**
     * Setter for the ordinate direction
     *
     * @param direction the ordinate direction to set
     */
    public final void setYDirection(final int direction) {
        this.yDirection = direction;
    }

}
