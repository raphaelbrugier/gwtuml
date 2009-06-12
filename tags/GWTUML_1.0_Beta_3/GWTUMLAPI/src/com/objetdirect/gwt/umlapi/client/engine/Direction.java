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

import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;

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
	DOWN_RIGHT(1, 1, "⬊"),
	/**
	 * Unknown direction
	 */
	UNKNOWN(0, 0, "?");

	/**
	 * Get the speed depending on which modifiers keys are down (ctrl, alt, shift, meta).
	 * 
	 * @param isCtrlDown
	 *            True if ctrl is down
	 * @param isAltDown
	 *            True if alt is down
	 * @param isMetaDown
	 *            True if meta is down
	 * @param isShiftDown
	 *            True if shift is down
	 * 
	 * @return The computed speed
	 */
	public static int getDependingOnModifierSpeed(final boolean isCtrlDown, final boolean isAltDown, final boolean isMetaDown, final boolean isShiftDown) {
		int speedModified = 50;
		if (isCtrlDown) {
			speedModified /= 2;
		}
		if (isAltDown) {
			speedModified /= 3;
		}
		if (isMetaDown) {
			speedModified /= 4;
		}
		if (isShiftDown) {
			speedModified /= 5;
		}
		return speedModified;
	}

	/**
	 * Get the speed depending on the current quality level. The higher the quality, the lower the speed will be.
	 * 
	 * @return The computed speed
	 */
	public static int getDependingOnQualityLevelSpeed() {
		final int speedModified = 5 * (OptionsManager.get("QualityLevel") + 1);
		return speedModified;
	}

	private int		xDirection;
	private int		yDirection;

	private int		speed	= 1;
	private String	idiom	= "x";

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
	 * Getter for the speed
	 * 
	 * @return the speed
	 */
	public int getSpeed() {
		return this.speed;
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
	 * Compute the x speed from the direction and the speed
	 * 
	 * @return the x speed
	 */
	public double getXShift() {
		return this.xDirection * this.speed;
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
	 * Compute the y speed from the direction and the speed
	 * 
	 * @return the y speed
	 */
	public double getYShift() {
		return this.yDirection * this.speed;
	}

	/**
	 * Determine if this direction is horizontal
	 * 
	 * @return True if it is purely horizontal
	 */
	public boolean isHorizontal() {
		return (this.yDirection == 0) && (this.xDirection != 0);
	}

	/**
	 * Determine if the given direction is the opposite of this one.
	 * 
	 * @param opposite
	 *            The direction to determine if it is an opposite of this one
	 * 
	 * @return True if it is the opposite
	 */
	public boolean isOppositeOf(final Direction opposite) {
		return ((this.xDirection == -opposite.xDirection) && (this.yDirection == -opposite.yDirection));
	}

	/**
	 * Determine if this direction is vertical
	 * 
	 * @return True if it is purely vertical
	 */
	public boolean isVertical() {
		return (this.xDirection == 0) && (this.yDirection != 0);
	}

	/**
	 * Setter for the abscissa direction
	 * 
	 * @param direction
	 *            the abscissa direction to set
	 */
	public final void setXDirection(final int direction) {
		this.xDirection = direction;
	}

	/**
	 * Setter for the ordinate direction
	 * 
	 * @param direction
	 *            the ordinate direction to set
	 */
	public final void setYDirection(final int direction) {
		this.yDirection = direction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return "Direction : " + super.toString() + " x : " + this.getXDirection() + " y : " + this.getYDirection() + " wth speed = " + this.speed;
	}

	/**
	 * Setter for the speed
	 * 
	 * @param theSpeed
	 *            the speed to set
	 * 
	 * @return This instance
	 */
	public Direction withSpeed(final int theSpeed) {
		this.speed = theSpeed;
		return this;
	}

}
