package com.objetdirect.gwt.umlapi.client.gfx.incubator.objects;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
/**
 * @author  florian
 */
public abstract class IncubatorGfxObject {
	protected Color fillColor;
	protected boolean isVisible = false;
	protected Color strokeColor;
	protected int strokeWidth = 0;
	/**
	 * @uml.property  name="style"
	 * @uml.associationEnd  
	 */
	protected GfxStyle style;
	/**
	 * @uml.property  name="x"
	 */
	protected int x = 0;
	/**
	 * @uml.property  name="y"
	 */
	protected int y = 0;
	public void addOnCanvasAt(int dx, int dy) {
		isVisible = true;
		translate(dx, dy);
	}
	public abstract void draw(GWTCanvas canvas);
	public abstract int getHeight();
	public abstract int getWidth();
	/**
	 * @return
	 * @uml.property  name="x"
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return
	 * @uml.property  name="y"
	 */
	public int getY() {
		return y;
	}
	public abstract boolean isPointed(int x, int y);
	public void removeFromCanvas() {
		isVisible = false;
	}
	public void setFillColor(GfxColor gfxColor) {
		this.fillColor = new Color(gfxColor.getRed(), gfxColor.getBlue(),
				gfxColor.getGreen(), gfxColor.getAlpha());
	}
	public void setStrokeColor(GfxColor gfxColor) {
		this.strokeColor = new Color(gfxColor.getRed(), gfxColor.getBlue(),
				gfxColor.getGreen(), gfxColor.getAlpha());
	}
	/**
	 * @param width
	 * @uml.property  name="strokeWidth"
	 */
	public void setStrokeWidth(int width) {
		this.strokeWidth = width;
	}
	/**
	 * @param style
	 * @uml.property  name="style"
	 */
	public void setStyle(GfxStyle style) {
		this.style = style;
	}
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}
}
