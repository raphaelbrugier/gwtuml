package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.ui.Widget;

public interface GfxPlatform {
    final static int DEFAULT_CANVAS_HEIGHT = 600;
    final static int DEFAULT_CANVAS_WIDTH = 800;

    void addObjectListenerToCanvas(Object canvas,
	    GfxObjectListener gfxObjectListener);

    void addToCanvas(Object canvas, GfxObject gfxO, int x, int y);

    void addToVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO);

    GfxObject buildLine(int x1, int y1, int x2, int y2);

    GfxObject buildPath();

    GfxObject buildRect(int width, int height);

    GfxObject buildText(String text);

    GfxObject buildVirtualGroup();

    void clearVirtualGroup(GfxObject gfxOGroup);

    GfxObject getGroup(GfxObject gfxO);

    int getHeightFor(GfxObject gfxO);

    int getWidthFor(GfxObject gfxO);

    int getXFor(GfxObject gfxO);

    int getYFor(GfxObject gfxO);

    void lineTo(GfxObject gfxO, int x, int y);

    Widget makeCanvas();

    Widget makeCanvas(int width, int height, GfxColor backgroundColor);

    void moveTo(GfxObject gfxO, int x, int y);

    void moveToBack(GfxObject gfxO);

    void moveToFront(GfxObject gfxO);

    void removeFromCanvas(Object canvas, GfxObject gfxO);

    void removeFromVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO,
	    boolean isSilent);

    void setFillColor(GfxObject gfxO, GfxColor color);

    void setFont(GfxObject gfxO, GfxFont gfxF);

    void setOpacity(GfxObject gfxO, int opacity, boolean isForBack);

    void setSize(Widget canvas, int width, int height);

    void setStroke(GfxObject gfxO, GfxColor color, int width);

    void setStrokeStyle(GfxObject gfxO, GfxStyle style);

    void translate(GfxObject gfxO, int x, int y);
}
