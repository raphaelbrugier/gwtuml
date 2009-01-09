package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;


public class IncubatorGraphicsPlatform implements GraphicsPlatform{
	
	private GWTCanvas canvas;

	public Widget makeCanvas() {
		return makeCanvas(DEFAULTWIDTH, DEFAULTHEIGHT);
	}

	public Widget makeCanvas(int width, int height) {
		canvas = new GWTCanvas(width, height);
		
	    canvas.setLineWidth(1);
	    canvas.setStrokeStyle(Color.BLACK);
	    
		return canvas;
	}
	
	public void Line(int x1, int y1, int x2, int y2) {
		canvas.beginPath();
			canvas.moveTo(x1,y1);	
			canvas.lineTo(x2,y2);
		canvas.closePath();
		canvas.stroke();
	}
}
