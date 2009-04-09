package com.objetdirect.gwt.umlapi.client.gfx.tatami;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.Font;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.GraphicObjectListener;
import com.objetdirect.tatami.client.gfx.Line;
import com.objetdirect.tatami.client.gfx.Path;
import com.objetdirect.tatami.client.gfx.Rect;
import com.objetdirect.tatami.client.gfx.Text;
import com.objetdirect.tatami.client.gfx.VirtualGroup;

public class TatamiGfxPlatfrom implements GfxPlatform {
    public void addObjectListenerToCanvas(final Object canvas,
	    final GfxObjectListener gfxObjectListener) {
	final GraphicObjectListener graphicObjectListener = new GraphicObjectListener() {
	    public void mouseClicked(final GraphicObject graphicObject,
		    final Event e) {
		gfxObjectListener.mouseClicked();
	    }

	    public void mouseDblClicked(final GraphicObject graphicObject,
		    final Event e) {
		gfxObjectListener.mouseDblClicked(TatamiGfxObjectContainer
			.getContainerOf(graphicObject), DOM.eventGetClientX(e),
			DOM.eventGetClientY(e));
	    }

	    public void mouseMoved(final GraphicObject graphicObject,
		    final Event e) {
		gfxObjectListener.mouseMoved(DOM.eventGetClientX(e), DOM
			.eventGetClientY(e));
	    }

	    public void mousePressed(final GraphicObject graphicObject,
		    final Event e) {
		if (e.getButton() == NativeEvent.BUTTON_RIGHT) {
		    gfxObjectListener
			    .mouseRightClickPressed(TatamiGfxObjectContainer
				    .getContainerOf(graphicObject), DOM
				    .eventGetClientX(e), DOM.eventGetClientY(e));
		} else {
		    gfxObjectListener
			    .mouseLeftClickPressed(TatamiGfxObjectContainer
				    .getContainerOf(graphicObject), DOM
				    .eventGetClientX(e), DOM.eventGetClientY(e));
		}
	    }

	    public void mouseReleased(final GraphicObject graphicObject,
		    final Event e) {
		gfxObjectListener.mouseReleased(TatamiGfxObjectContainer
			.getContainerOf(graphicObject), DOM.eventGetClientX(e),
			DOM.eventGetClientY(e));
	    }
	};
	((GraphicCanvas) canvas)
		.addGraphicObjectListener(graphicObjectListener);
    }

    public void addToCanvas(final Object canvas, final GfxObject gfxO,
	    final Point location) {
	Log.trace("Adding to Tcanvas : "
		+ UMLDrawerHelper
			.getShortName(getTatamiGraphicalObjectFrom(gfxO)));
	((GraphicCanvas) canvas).add(getTatamiGraphicalObjectFrom(gfxO), location.getX(), location.getY());
    }

    public void addToVirtualGroup(final GfxObject gfxOGroup,
	    final GfxObject gfxO) {
	Log.trace("Adding " + gfxO + " to " + gfxOGroup);
	((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup))
		.add(getTatamiGraphicalObjectFrom(gfxO));
    }

    public GfxObject buildLine(final Point p1, final Point p2) {
	return new TatamiGfxObjectContainer(new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
    }

    public GfxObject buildPath() {
	return new TatamiGfxObjectContainer(new Path());
    }

    public GfxObject buildRect(final int width, final int height) {
	return new TatamiGfxObjectContainer(new Rect(width, height));
    }

    public GfxObject buildText(final String text) {

	return new TatamiGfxObjectContainer(new Text(text));
    }

    public GfxObject buildVirtualGroup() {
	final GfxObject vg = new TatamiGfxObjectContainer(new VirtualGroup());
	Log.trace("Creating " + vg);
	return vg;
    }

    public void clearVirtualGroup(final GfxObject gfxOGroup) {
	((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup)).clear();
    }

    public GfxObject getGroup(final GfxObject gfxO) {
	return TatamiGfxObjectContainer
		.getContainerOf(getTatamiGraphicalObjectFrom(gfxO).getGroup());

    }

    public int getHeightFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return (int) (((Text) getTatamiGraphicalObjectFrom(gfxO))
		    .getHeight() * 4 / 5); // Converting point to pixel
	}
	return 0;
    }

    public int getWidthFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return (int) (((Text) getTatamiGraphicalObjectFrom(gfxO))
		    .getWidth() * 4 / 5); // Converting point to pixel
	}

	return 0;
    }

    public Point getLocationFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return new Point(getTatamiGraphicalObjectFrom(gfxO).getX(), getTatamiGraphicalObjectFrom(gfxO).getY());
	}
	return Point.getOrigin();
    }
    public void lineTo(final GfxObject gfxO, final Point location) {
	((Path) getTatamiGraphicalObjectFrom(gfxO)).lineTo(location.getX(), location.getY());
    }

    public Widget makeCanvas() {
	return makeCanvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT,
		GfxColor.WHITE);
    }

    public Widget makeCanvas(final int width, final int height,
	    final GfxColor backgroundColor) {
	final GraphicCanvas canvas = new GraphicCanvas();
	canvas.setSize(width + "px", height + "px");
	DOM.setStyleAttribute(canvas.getElement(), "backgroundColor",
		new Color(backgroundColor.getRed(), backgroundColor.getGreen(),
			backgroundColor.getBlue()
			/*
			 * , backgroundColor.getAlpha() Disabled to ensure#@&~#!
			 * IE compatibility
			 */, 0).toHex());
	return canvas;
    }

    public void moveTo(final GfxObject gfxO, final Point location) {
	((Path) getTatamiGraphicalObjectFrom(gfxO)).moveTo(location.getX(), location.getY());
    }

    public void moveToBack(final GfxObject gfxO) {
	getTatamiGraphicalObjectFrom(gfxO).moveToBack();

    }

    public void moveToFront(final GfxObject gfxO) {
	getTatamiGraphicalObjectFrom(gfxO).moveToFront();
    }

    public void removeFromCanvas(final Object canvas, final GfxObject gfxO) {
	Log.trace("Removing from Tcanvas : "
		+ UMLDrawerHelper
			.getShortName(getTatamiGraphicalObjectFrom(gfxO)));
	((GraphicCanvas) canvas).remove(getTatamiGraphicalObjectFrom(gfxO));
    }

    public void removeFromVirtualGroup(final GfxObject gfxOGroup,
	    final GfxObject gfxO, final boolean isSilent) {
	((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup)).remove(
		getTatamiGraphicalObjectFrom(gfxO), isSilent);
    }

    public void setFillColor(final GfxObject gfxO, final GfxColor color) {
	getTatamiGraphicalObjectFrom(gfxO).setFillColor(convertColor(color));
    }

    public void setFont(final GfxObject gfxO, final GfxFont gfxF) {
	((Text) getTatamiGraphicalObjectFrom(gfxO)).setFont(convertFont(gfxF));
    }

    public void setOpacity(final GfxObject gfxO, final int opacity,
	    final boolean isForBack) {
	if (isForBack) {
	    getTatamiGraphicalObjectFrom(gfxO).setOpacity(opacity);
	} else {

	    final Color strokeColor = getTatamiGraphicalObjectFrom(gfxO)
		    .getStrokeColor();
	    getTatamiGraphicalObjectFrom(gfxO).setStrokeColor(
		    new Color(strokeColor.getRed(), strokeColor.getGreen(),
			    strokeColor.getBlue(), opacity));
	}
    }

    public void setSize(final Widget canvas, final int width, final int height) {
	canvas.setSize(width + "px", height + "px");
    }

    public void setStroke(final GfxObject gfxO, final GfxColor color,
	    final int width) {
	if (getTatamiGraphicalObjectFrom(gfxO).getClass().equals(Text.class)) {
	    getTatamiGraphicalObjectFrom(gfxO).setOpacity(color.getAlpha());
	}
	getTatamiGraphicalObjectFrom(gfxO)
		.setStroke(convertColor(color), width);
    }

    public void setStrokeStyle(final GfxObject gfxO, final GfxStyle style) {
	getTatamiGraphicalObjectFrom(gfxO).setStrokeStyle(
		style.getStyleString());
    }

    public void translate(final GfxObject gfxO, final Point location) {
	getTatamiGraphicalObjectFrom(gfxO).translate(location.getX(), location.getY());
    }

    private Color convertColor(final GfxColor gfxColor) {
	return new Color(gfxColor.getRed(), gfxColor.getGreen(), gfxColor
		.getBlue(), gfxColor.getAlpha());
    }

    private Font convertFont(final GfxFont gfxFont) {
	return new Font(gfxFont.getFamily(), gfxFont.getSize(), gfxFont
		.getStyle(), gfxFont.getVariant(), gfxFont.getWeight());
    }

    private GraphicObject getTatamiGraphicalObjectFrom(final GfxObject gfxO) {
	return ((TatamiGfxObjectContainer) gfxO).getGraphicObject();
    }

}