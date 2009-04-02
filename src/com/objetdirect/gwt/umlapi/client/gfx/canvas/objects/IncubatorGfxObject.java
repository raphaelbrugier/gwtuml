package com.objetdirect.gwt.umlapi.client.gfx.canvas.objects;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.CanvasBridge;
/**
 * @author  florian
 */
public abstract class IncubatorGfxObject {
	private int redFill;
	private int greenFill;
	private int blueFill;
	private int redStroke;
	private int greenStroke;
	private int blueStroke;
	protected Color fillColor;
	protected boolean isVisible = false;
	protected Color strokeColor;
	protected int strokeWidth = 0;
	protected VirtualGroup parentGroup = null;
	protected CanvasBridge canvas;
	/**
	 * @return the canvas
	 */
	public CanvasBridge getCanvas() {
		return canvas;
	}
	/**
	 * @return the parentGroup
	 */
	public VirtualGroup getParentGroup() {
		return parentGroup;
	}
	/**
	 * @param parentGroup the parentGroup to set
	 */
	public void setParentGroup(VirtualGroup parentGroup) {
		this.parentGroup = parentGroup;
	}
protected GfxStyle style;
	 
	protected int x = 0;
	 
	protected int y = 0;
	public void addOnCanvasAt(CanvasBridge canvas, int dx, int dy) {
		Log.trace("Adding " + this + " on canvas " + canvas);
		isVisible = true;
		this.canvas = canvas;
		translate(dx, dy);
	}
	public abstract void draw();
	
	public abstract int getHeight();
	public abstract int getWidth();
public int getX() {
		return x + (parentGroup == null ? 0 : parentGroup.getX());
	}
public int getY() {
		return y + (parentGroup == null ? 0 : parentGroup.getY());
	}
	public abstract boolean isPointed(int x, int y);
	public void removeFromCanvas() {
		isVisible = false;
	}
	public void setFillColor(GfxColor gfxColor) {
		redFill = gfxColor.getRed();
		blueFill = gfxColor.getBlue();
		greenFill = gfxColor.getGreen();
		this.fillColor = new Color(redFill, blueFill, greenFill, gfxColor.getAlpha());
	}
	public void setAlpha(float alpha) {
		this.fillColor = new Color(redFill, blueFill, greenFill, alpha);
		this.strokeColor = new Color(redStroke, blueStroke, greenStroke, alpha);
	}
	public void setStrokeColor(GfxColor gfxColor) {
		redStroke = gfxColor.getRed();
		blueStroke = gfxColor.getBlue();
		greenStroke = gfxColor.getGreen();
		this.strokeColor = new Color(redStroke, blueStroke, greenStroke, gfxColor.getAlpha());
	}
 
	public void setStrokeWidth(int width) {
		this.strokeWidth = width;
	}
 
	public void setStyle(GfxStyle style) {
		this.style = style;
	}
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}
}
