package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.ui.Widget;

public interface GfxPlatform {
	final static int DEFAULT_CANVAS_WIDTH = 800;
	final static int DEFAULT_CANVAS_HEIGHT = 600;

	// Canvas

	Widget makeCanvas();

	Widget makeCanvas(int width, int height, GfxColor backgroundColor);

	void addToCanvas(Widget canvas, GfxObject gfxO, int x, int y);

	void removeFromCanvas(Widget canvas, GfxObject gfxO);

	void addObjectListenerToCanvas(Widget canvas,
			GfxObjectListener gfxObjectListener);

	// Builders

	GfxObject buildPath();

	GfxObject buildLine(double x1, double y1, double x2, double y2);

	GfxObject buildRect(double width, double height);

	GfxObject buildText(String text);

	GfxObject buildVirtualGroup();

	void addToVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO);

	void removeFromVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO,
			boolean isSilent);

	void setStroke(GfxObject gfxO, GfxColor color, int width);

	void setStrokeStyle(GfxObject gfxO, GfxStyle style);

	void setFillColor(GfxObject gfxO, GfxColor color);

	void setFont(GfxObject gfxO, GfxFont gfxF);

	void moveTo(GfxObject gfxO, double x, double y);

	void lineTo(GfxObject gfxO, double x, double y);

	void translate(GfxObject gfxO, int x, int y);

	double getXFor(GfxObject gfxO);

	double getYFor(GfxObject gfxO);

	double getHeightFor(GfxObject gfxO);

	double getWidthFor(GfxObject gfxO);

}
