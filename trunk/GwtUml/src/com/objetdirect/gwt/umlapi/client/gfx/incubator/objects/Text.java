package com.objetdirect.gwt.umlapi.client.gfx.incubator.objects;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class Text extends IncubatorGfxObject {
	
	private String text; 
	
	public Text(String text)	{
		this.text = text;
	}
	
	
	public void draw(GWTCanvas canvas) {
		if(!isVisible) return;
		Log.trace("{Incubator} Drawing " + this);
		canvas.saveContext();
		if(fillColor != null) canvas.setFillStyle(fillColor);
		if(strokeColor != null) canvas.setStrokeStyle(strokeColor);
		if(strokeWidth != 0) canvas.setLineWidth(strokeWidth);
        for (int i = 0 ; i < text.length() ; i++) {
        	
        	canvas.strokeRect(x + 10*i, y-8, 8, 10);	
			
		}	
        
		canvas.restoreContext();
	}


	public boolean isPointed(int x, int y) {
		return false;
	}


	public double getHeight() {
		return 8;
	}


	public double getWidth() {
		return text.length() * 10;
	}
}