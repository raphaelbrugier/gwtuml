package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.ui.Widget;

public interface GfxPlatform {
	final static int DEFAULT_CANVAS_HEIGHT = 600;
	final static int DEFAULT_CANVAS_WIDTH = 800;

	// Canvas

	void addObjectListenerToCanvas(Widget canvas,
			GfxObjectListener gfxObjectListener);

	void addToCanvas(Widget canvas, GfxObject gfxO, int x, int y);

	void addToVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO);

	GfxObject buildLine(double x1, double y1, double x2, double y2);

	GfxObject buildPath();

	// Builders

	GfxObject buildRect(double width, double height);

	GfxObject buildText(String text);

	GfxObject buildVirtualGroup();

	double getHeightFor(GfxObject gfxO);

	double getWidthFor(GfxObject gfxO);

	double getXFor(GfxObject gfxO);

	double getYFor(GfxObject gfxO);

	void lineTo(GfxObject gfxO, double x, double y);

	Widget makeCanvas();

	Widget makeCanvas(int width, int height, GfxColor backgroundColor);

	void moveTo(GfxObject gfxO, double x, double y);

	void removeFromCanvas(Widget canvas, GfxObject gfxO);

	void removeFromVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO,
			boolean isSilent);

	void setFillColor(GfxObject gfxO, GfxColor color);

	void setFont(GfxObject gfxO, GfxFont gfxF);

	void setStroke(GfxObject gfxO, GfxColor color, int width);

	void setStrokeStyle(GfxObject gfxO, GfxStyle style);

	void translate(GfxObject gfxO, int x, int y);

}
