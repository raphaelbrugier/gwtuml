package com.objetdirect.gwt.umlapi.client.gfx.incubator.objects;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;

public abstract class IncubatorGfxObject {

	protected boolean isVisible = false;
	
	protected int x = 0;
	protected int y = 0;
	
	protected Color fillColor;
	protected Color strokeColor;
	protected int strokeWidth = 0;
	protected GfxStyle style;
	
	
	public abstract void draw(GWTCanvas canvas);
	public abstract boolean isPointed(int x, int y);
	public abstract double getHeight();
	public abstract double getWidth();	
	
	public void addOnCanvasAt(int dx, int dy) {	
		isVisible = true;
		translate(dx, dy);		
	}
		
	public void removeFromCanvas() { 
		isVisible = false;	
	}	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}

	public void setFillColor(GfxColor gfxColor) {
		this.fillColor = new Color(gfxColor.getRed(), gfxColor.getBlue(), gfxColor.getGreen(), gfxColor.getAlpha());
	}
	public void setStrokeColor(GfxColor gfxColor) {
		this.strokeColor = new Color(gfxColor.getRed(), gfxColor.getBlue(), gfxColor.getGreen(), gfxColor.getAlpha());
	}
	public void setStrokeWidth(int width) {
		this.strokeWidth = width;
	}
	public void setStyle(GfxStyle style) {
		this.style = style;
	}

	

}
