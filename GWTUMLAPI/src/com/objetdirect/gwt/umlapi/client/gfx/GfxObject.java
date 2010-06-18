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
package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.engine.Point;

/**
 * Abstract class representing the different graphical objects
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class GfxObject {

	private static GfxPlatform gfxPlatform = GfxManager.getPlatform();

	/**
	 * Adds the {@link GfxObject} to the canvas at the specified position.
	 * 
	 * @param canvas
	 *            The canvas on which the {@link GfxObject} will be added
	 * @param location
	 *            The location {@link Point} of the added gfxObject
	 */
	public void addToCanvas(Widget canvas, Point location) {
		gfxPlatform.addToCanvas(canvas, this, location);
	}

	/**
	 * Add the {@link GfxObject} to a virtual group
	 * 
	 * @param gfxObjectGroup
	 *            The group that will contain the {@link GfxObject}
	 */
	public void addToVirtualGroup(GfxObject gfxObjectGroup) {
		gfxPlatform.addToVirtualGroup(gfxObjectGroup, this);
	}

	/**
	 * Getter for the {@link GfxObject} virtual group if any
	 * 
	 * @return The virtual group of this {@link GfxObject} if any, null otherwise
	 */
	public GfxObject getGroup() {
		return gfxPlatform.getGroup(this);
	}

	/**
	 * Getter for the {@link GfxObject} location in the canvas
	 * 
	 * @return The location {@link Point} of the gfxObject
	 */
	public Point getLocation() {
		return gfxPlatform.getLocationFor(this);
	}

	/**
	 * Move the {@link GfxObject} to the background.
	 */
	public void moveToBack() {
		gfxPlatform.moveToBack(this);
	}

	/**
	 * Move the {@link GfxObject} to the foreground.
	 */
	public void moveToFront() {
		gfxPlatform.moveToFront(this);
	}

	/**
	 * Remove the {@link GfxObject} from the canvas.
	 * 
	 * @param canvas
	 *            The graphic canvas widget the {@link GfxObject} is on
	 */
	public void removeFromCanvas(Widget canvas) {
		gfxPlatform.removeFromCanvas(canvas, this);
	}

	/**
	 * Removes the {@link GfxObject} from its virtual group.
	 * 
	 * @param gfxObjectGroup
	 *            the virtual group containing the gfxObject
	 * @param isSilent
	 *            If true the canvas is not redrawn
	 */
	public void removeFromVirtualGroup(GfxObject gfxObjectGroup, boolean isSilent) {
		gfxPlatform.removeFromVirtualGroup(gfxObjectGroup, this, isSilent);
	}

	/**
	 * Rotate the graphical object with the given angle in radians and center.
	 * 
	 * @param angle
	 *            The angle of rotation in radians
	 * @param center
	 *            The center {@link Point} of the rotation
	 */
	public void rotate(final float angle, final Point center) {
		gfxPlatform.rotate(this, angle, center);
	}

	/**
	 * Setter for the filling color.
	 * 
	 * @param color
	 *            The filling {@link GfxColor}
	 */
	public void setFillColor(GfxColor color) {
		gfxPlatform.setFillColor(this, color);
	}

	/**
	 * Setter for the font.
	 * 
	 * @param gfxFont
	 *            The text {@link GfxFont}
	 */
	public void setFont(GfxFont gfxFont) {
		gfxPlatform.setFont(this, gfxFont);
	}

	/**
	 * Setter for the opacity.
	 * 
	 * @param opacity
	 *            The new opacity of the graphical object
	 * @param isForBack
	 *            If set to true set the background opacity, otherwise it is the stroke opacity
	 * 
	 */
	public void setOpacity(int opacity, boolean isForBack) {
		gfxPlatform.setOpacity(this, opacity, isForBack);
	}

	/**
	 * Setter of the stroke color and width.
	 * 
	 * @param color
	 *            The new stroke {@link GfxColor}
	 * @param width
	 *            The new stroke width
	 */
	public void setStroke(GfxColor color, int width) {
		gfxPlatform.setStroke(this, color, width);
	}

	/**
	 * Setter of the stroke style.
	 * 
	 * @param style
	 *            The new {@link GfxStyle} of the graphical object
	 */
	public void setStrokeStyle(GfxStyle style) {
		gfxPlatform.setStrokeStyle(this, style);
	}

	/**
	 * Translate to a new destination by an offset.
	 * 
	 * @param offset
	 *            The {@link Point} containing the two offset dimensions
	 */
	public void translate(Point offset) {
		gfxPlatform.translate(this, offset);
	}
}
