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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.Color;

abstract class IncubatorGfxObject {
	protected CanvasBridge canvas;
	protected Color fillColor;
	protected boolean isVisible = false;
	protected VirtualGroup parentGroup = null;
	protected Color strokeColor;
	protected int strokeWidth = 0;
	protected GfxStyle style;
	protected int x = 0;
	protected int y = 0;
	private int blueFill;
	private int blueStroke;
	private int greenFill;

	private int greenStroke;

	private int redFill;

	private int redStroke;

	public void addOnCanvasAt(final CanvasBridge canvasBridge, final int dx, final int dy) {
		Log.trace("Adding " + this + " on canvas " + canvasBridge);
		isVisible = true;
		canvas = canvasBridge;
		this.translate(dx, dy);
	}

	public abstract void draw();

	/**
	 * @return the canvas
	 */
	public CanvasBridge getCanvas() {
		return canvas;
	}

	public abstract int getHeight();

	/**
	 * @return the parentGroup
	 */
	public VirtualGroup getParentGroup() {
		return parentGroup;
	}

	public abstract int getWidth();

	public int getX() {
		return x + (parentGroup == null ? 0 : parentGroup.getX());
	}

	public int getY() {
		return y + (parentGroup == null ? 0 : parentGroup.getY());
	}

	public abstract boolean isPointed(int xp, int yp);

	public void removeFromCanvas() {
		isVisible = false;
	}

	public void setAlpha(final float alpha) {
		fillColor = new Color(redFill, blueFill, greenFill, alpha);
		strokeColor = new Color(redStroke, blueStroke, greenStroke, alpha);
	}

	public void setFillColor(final GfxColor gfxColor) {
		redFill = gfxColor.getRed();
		blueFill = gfxColor.getBlue();
		greenFill = gfxColor.getGreen();
		fillColor = new Color(redFill, blueFill, greenFill, gfxColor.getAlpha());
	}

	/**
	 * @param parentGroup
	 *            the parentGroup to set
	 */
	public void setParentGroup(final VirtualGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	public void setStrokeColor(final GfxColor gfxColor) {
		redStroke = gfxColor.getRed();
		blueStroke = gfxColor.getBlue();
		greenStroke = gfxColor.getGreen();
		strokeColor = new Color(redStroke, blueStroke, greenStroke, gfxColor.getAlpha());
	}

	public void setStrokeWidth(final int width) {
		strokeWidth = width;
	}

	public void setStyle(final GfxStyle style) {
		this.style = style;
	}

	public void translate(final int dx, final int dy) {
		x += dx;
		y += dy;
	}
}
