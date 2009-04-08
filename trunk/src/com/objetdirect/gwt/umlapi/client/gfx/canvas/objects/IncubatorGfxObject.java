package com.objetdirect.gwt.umlapi.client.gfx.canvas.objects;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.CanvasBridge;

/**
 * @author florian
 */
public abstract class IncubatorGfxObject {
    private int blueFill;
    private int blueStroke;
    protected CanvasBridge canvas;
    protected Color fillColor;
    private int greenFill;
    private int greenStroke;
    protected boolean isVisible = false;
    protected VirtualGroup parentGroup = null;
    private int redFill;
    private int redStroke;
    protected Color strokeColor;
    protected int strokeWidth = 0;

    protected GfxStyle style;

    protected int x = 0;

    protected int y = 0;

    public void addOnCanvasAt(final CanvasBridge canvasBridge, final int dx,
	    final int dy) {
	Log.trace("Adding " + this + " on canvas " + canvasBridge);
	this.isVisible = true;
	this.canvas = canvasBridge;
	translate(dx, dy);
    }

    public abstract void draw();

    /**
     * @return the canvas
     */
    public CanvasBridge getCanvas() {
	return this.canvas;
    }

    public abstract int getHeight();

    /**
     * @return the parentGroup
     */
    public VirtualGroup getParentGroup() {
	return this.parentGroup;
    }

    public abstract int getWidth();

    public int getX() {
	return this.x + (this.parentGroup == null ? 0 : this.parentGroup.getX());
    }

    public int getY() {
	return this.y + (this.parentGroup == null ? 0 : this.parentGroup.getY());
    }

    public abstract boolean isPointed(int xp, int yp);

    public void removeFromCanvas() {
	this.isVisible = false;
    }

    public void setAlpha(final float alpha) {
	this.fillColor = new Color(this.redFill, this.blueFill, this.greenFill, alpha);
	this.strokeColor = new Color(this.redStroke, this.blueStroke, this.greenStroke, alpha);
    }

    public void setFillColor(final GfxColor gfxColor) {
	this.redFill = gfxColor.getRed();
	this.blueFill = gfxColor.getBlue();
	this.greenFill = gfxColor.getGreen();
	this.fillColor = new Color(this.redFill, this.blueFill, this.greenFill, gfxColor.getAlpha());
    }

    /**
     * @param parentGroup
     *            the parentGroup to set
     */
    public void setParentGroup(final VirtualGroup parentGroup) {
	this.parentGroup = parentGroup;
    }

    public void setStrokeColor(final GfxColor gfxColor) {
	this.redStroke = gfxColor.getRed();
	this.blueStroke = gfxColor.getBlue();
	this.greenStroke = gfxColor.getGreen();
	this.strokeColor = new Color(this.redStroke, this.blueStroke, this.greenStroke, gfxColor
		.getAlpha());
    }

    public void setStrokeWidth(final int width) {
	this.strokeWidth = width;
    }

    public void setStyle(final GfxStyle style) {
	this.style = style;
    }

    public void translate(final int dx, final int dy) {
	this.x += dx;
	this.y += dy;
    }
}