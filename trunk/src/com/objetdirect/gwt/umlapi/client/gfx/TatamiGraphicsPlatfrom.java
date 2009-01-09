package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.Line;

public class TatamiGraphicsPlatfrom  implements GraphicsPlatform{

	private GraphicCanvas canvas;
	
	public Widget makeCanvas() {
		return makeCanvas(DEFAULTWIDTH, DEFAULTHEIGHT);
	}


	public Widget makeCanvas(int width, int height) {
		canvas = new GraphicCanvas();
	    canvas.setSize(width + "px", height + "px");
		return canvas;
	}
	
	public void Line(int x1, int y1, int x2, int y2) {
		Line l = new Line(x1, y1, x2, y2); 
    	canvas.add(l, 0, 0);
	}

}