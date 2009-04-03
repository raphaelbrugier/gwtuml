/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

/**
 * @author florian
 *
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int xDirection;
    private int yDirection;

    private Direction(int x, int y) {
        this.xDirection = x;
        this.yDirection = y;
    }

    public int getXDirection() {
        return xDirection;
    }

    public int getYDirection() {
        return yDirection;
    }
    
    
}
