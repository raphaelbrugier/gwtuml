package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.ui.Widget;

public interface GraphicsPlatform {
	final static int DEFAULTWIDTH  = 800;
	final static int DEFAULTHEIGHT = 600;
	Widget makeCanvas();
	Widget makeCanvas(int width, int height);
	void Line(int x1, int y1, int x2, int y2);

}
