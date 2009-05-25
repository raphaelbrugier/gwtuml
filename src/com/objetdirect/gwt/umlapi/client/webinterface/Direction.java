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
    DOWN(0, 1, "⬇"),
    /**
     * Left direction
     */
    LEFT(-1, 0, "⬅"),
    /**
     * Right direction
     */
    RIGHT(1, 0, "➡"),
    /**
     * Up direction
     */
    UP(0, -1, "⬆"),
    /**
     * Down direction
     */
    UP_LEFT(-1, -1, "⬉"),
    /**
     * Left direction
     */
    UP_RIGHT(1, -1, "⬈"),
    /**
     * Right direction
     */
    DOWN_LEFT(-1, 1, "⬋"),
    /**
     * Up direction
     */
    DOWN_RIGHT(1, 1, "⬊");


    private int xDirection;
    private int yDirection;
    private int speed = 1;
    private String idiom = "x";
    
    private Direction(final int x, final int y, final String idiom) {
	this.xDirection = x;
	this.yDirection = y;
	this.idiom = idiom;
    }
    /**
     * Getter for the idiom for this direction
     *
     * @return the idiom of this Direction
     */
    public final String getIdiom() {
        return this.idiom;
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

    /**
     * Getter for the speed
     *
     * @return the speed
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Setter for the speed
     *
     * @param speed the speed to set
     * 
     * @return This instance 
     */
    public Direction withSpeed(int speed) {
        this.speed = speed;
        return this;
    }
    public static int getDependingOnQualityLevelSpeed() {
	int speedModified = 5 * (OptionsManager.get("QualityLevel") + 1);
	return speedModified;
    }
    public static int getDependingOnModifierSpeed(boolean isCtrlDown, boolean isAltDown, boolean isMetaDown, boolean isShiftDown) {
	int speedModified = 50;
	if(isCtrlDown) speedModified /= 2;
	if(isAltDown) speedModified /= 3;
	if(isMetaDown) speedModified /= 4;
	if(isShiftDown) speedModified /= 5;
	return speedModified;
    }

    public double getXShift() {
	return this.xDirection * this.speed;
    }
    public double getYShift() {
	return this.yDirection * this.speed;
    }
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return "Direction : " + super.toString() + " x : " + getXDirection() + " y : " + getYDirection() + " wth speed = " + this.speed;
    }

}
