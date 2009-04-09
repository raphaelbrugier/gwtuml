package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.engine.Point;

public interface GfxPlatform {
    final static int DEFAULT_CANVAS_HEIGHT = 600;
    final static int DEFAULT_CANVAS_WIDTH = 800;

    void addObjectListenerToCanvas(Object canvas,
	    GfxObjectListener gfxObjectListener);

    void addToCanvas(Object canvas, GfxObject gfxO, Point location);

    void addToVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO);

    GfxObject buildLine(Point p1, Point p2);
    
    GfxObject buildPath();

    GfxObject buildRect(int width, int height);

    GfxObject buildText(String text);

    GfxObject buildVirtualGroup();

    void clearVirtualGroup(GfxObject gfxOGroup);

    GfxObject getGroup(GfxObject gfxO);

    int getHeightFor(GfxObject gfxO);

    int getWidthFor(GfxObject gfxO);
    
    Point getLocationFor(GfxObject gfxO);

    void lineTo(GfxObject gfxO, Point location);

    Widget makeCanvas();

    Widget makeCanvas(int width, int height, GfxColor backgroundColor);

    void moveTo(GfxObject gfxO, Point destination);

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

    void translate(GfxObject gfxO, Point destination);
}
