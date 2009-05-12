package com.objetdirect.gwt.umlapi.client.gfx;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.engine.Point;
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

/**
 * This class implements the graphic platform using the <a href="http://code.google.com/p/tatami/">Tatami</a> graphics library
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class TatamiGfxPlatfrom implements GfxPlatform {
    
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addObjectListenerToCanvas(java.lang.Object, com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener)
     */
    public void addObjectListenerToCanvas(final Widget canvas,
	    final GfxObjectListener gfxObjectListener) {
	final GraphicObjectListener graphicObjectListener = new GraphicObjectListener() {
	    /* (non-Javadoc)
	     * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mouseClicked(com.objetdirect.tatami.client.gfx.GraphicObject, com.google.gwt.user.client.Event)
	     */
	    public void mouseClicked(final GraphicObject graphicObject,
		    final Event e) {
		// This app doesn't need click event
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mouseDblClicked(com.objetdirect.tatami.client.gfx.GraphicObject, com.google.gwt.user.client.Event)
	     */
	    public void mouseDblClicked(final GraphicObject graphicObject,
		    final Event e) {
		gfxObjectListener.mouseDoubleClicked(TatamiGfxObjectContainer
			.getContainerOf(graphicObject), e);
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mouseMoved(com.objetdirect.tatami.client.gfx.GraphicObject, com.google.gwt.user.client.Event)
	     */
	    public void mouseMoved(final GraphicObject graphicObject,
		    final Event e) {
		gfxObjectListener.mouseMoved(e);
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mousePressed(com.objetdirect.tatami.client.gfx.GraphicObject, com.google.gwt.user.client.Event)
	     */
	    public void mousePressed(final GraphicObject graphicObject,
		    final Event e) {
		    gfxObjectListener
			    .mousePressed(TatamiGfxObjectContainer
				    .getContainerOf(graphicObject), e);
	    }

	    /* (non-Javadoc)
	     * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mouseReleased(com.objetdirect.tatami.client.gfx.GraphicObject, com.google.gwt.user.client.Event)
	     */
	    public void mouseReleased(final GraphicObject graphicObject,
		    final Event e) {
		gfxObjectListener.mouseReleased(TatamiGfxObjectContainer
			.getContainerOf(graphicObject), e);
	    }
	};
	((GraphicCanvas) canvas)
		.addGraphicObjectListener(graphicObjectListener);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addToCanvas(java.lang.Object, com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void addToCanvas(final Widget canvas, final GfxObject gfxO,
	    final Point location) {
	Log.trace("Adding to Tcanvas : "
		+ UMLDrawerHelper
			.getShortName(getTatamiGraphicalObjectFrom(gfxO)));
	((GraphicCanvas) canvas).add(getTatamiGraphicalObjectFrom(gfxO), location.getX(), location.getY());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addToVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void addToVirtualGroup(final GfxObject gfxOGroup,
	    final GfxObject gfxO) {
	Log.trace("Adding " + gfxO + " to " + gfxOGroup);
	((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup))
		.add(getTatamiGraphicalObjectFrom(gfxO));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildLine(com.objetdirect.gwt.umlapi.client.engine.Point, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public GfxObject buildLine(final Point p1, final Point p2) {
	return new TatamiGfxObjectContainer(new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildPath()
     */
    public GfxObject buildPath() {
	return new TatamiGfxObjectContainer(new Path());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildRect(int, int)
     */
    public GfxObject buildRect(final int width, final int height) {
	return new TatamiGfxObjectContainer(new Rect(width, height));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildText(java.lang.String)
     */
    public GfxObject buildText(final String text, Point initLocation) {
	Text txt = new Text(text);
	txt.translate(initLocation.getX(), initLocation.getY() + (int) ((txt.getHeight() *  64.) / 100.));
	return  new TatamiGfxObjectContainer(txt);
    }
    
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildText(java.lang.String)
     *//*
    public GfxObject buildText(final String text, final String decoration) {
	return new TatamiGfxObjectContainer(new Text(text, decoration));
    }*/


    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildVirtualGroup()
     */
    public GfxObject buildVirtualGroup() {
	final GfxObject vg = new TatamiGfxObjectContainer(new VirtualGroup());
	Log.trace("Creating " + vg);
	return vg;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#clearVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void clearVirtualGroup(final GfxObject gfxOGroup) {
	((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup)).clear();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public GfxObject getGroup(final GfxObject gfxO) {
	return TatamiGfxObjectContainer
		.getContainerOf(getTatamiGraphicalObjectFrom(gfxO).getGroup());

    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getHeightFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public int getTextHeightFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return (int) ((((Text) getTatamiGraphicalObjectFrom(gfxO))
		    .getHeight() *  8.) / 10.); // Converting point to pixel
	}
	return 0;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getWidthFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public int getTextWidthFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return (int) ((((Text) getTatamiGraphicalObjectFrom(gfxO))
		    .getWidth() *  8.) / 10.);  // Converting point to pixel
	}
	return 0;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getLocationFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public Point getLocationFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    if(getTatamiGraphicalObjectFrom(gfxO).getClass().equals(Text.class)) {
		Text txt = (Text) getTatamiGraphicalObjectFrom(gfxO);
		return new Point(txt.getX(),txt.getY() - (int) ((txt.getHeight() *  64.) / 100.));
	    }
	    return new Point(getTatamiGraphicalObjectFrom(gfxO).getX(), getTatamiGraphicalObjectFrom(gfxO).getY());
	}
	return Point.getOrigin();
    }
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#lineTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void lineTo(final GfxObject gfxO, final Point location) {
	((Path) getTatamiGraphicalObjectFrom(gfxO)).lineTo(location.getX(), location.getY());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#makeCanvas()
     */
    public Widget makeCanvas() {
	return makeCanvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT,
		GfxColor.WHITE);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#makeCanvas(int, int, com.objetdirect.gwt.umlapi.client.gfx.GfxColor)
     */
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

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void moveTo(final GfxObject gfxO, final Point location) {
	((Path) getTatamiGraphicalObjectFrom(gfxO)).moveTo(pointConverter(location));
    }
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void curveTo(final GfxObject gfxO, final Point location, final Point control) {
	((Path) getTatamiGraphicalObjectFrom(gfxO)).qCurveTo(pointConverter(control), pointConverter(location));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveToBack(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void moveToBack(final GfxObject gfxO) {
	getTatamiGraphicalObjectFrom(gfxO).moveToBack();

    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveToFront(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void moveToFront(final GfxObject gfxO) {
	getTatamiGraphicalObjectFrom(gfxO).moveToFront();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#removeFromCanvas(java.lang.Object, com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void removeFromCanvas(final Widget canvas, final GfxObject gfxO) {
	Log.trace("Removing from Tcanvas : "
		+ UMLDrawerHelper
			.getShortName(getTatamiGraphicalObjectFrom(gfxO)));
	((GraphicCanvas) canvas).remove(getTatamiGraphicalObjectFrom(gfxO));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#removeFromVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxObject, boolean)
     */
    public void removeFromVirtualGroup(final GfxObject gfxOGroup,
	    final GfxObject gfxO, final boolean isSilent) {
	((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup)).remove(
		getTatamiGraphicalObjectFrom(gfxO), isSilent);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setFillColor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxColor)
     */
    public void setFillColor(final GfxObject gfxO, final GfxColor color) {
	getTatamiGraphicalObjectFrom(gfxO).setFillColor(convertColor(color));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setFont(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxFont)
     */
    public void setFont(final GfxObject gfxO, final GfxFont gfxF) {
	((Text) getTatamiGraphicalObjectFrom(gfxO)).setFont(convertFont(gfxF));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setOpacity(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, int, boolean)
     */
    public void setOpacity(final GfxObject gfxO, final int opacity,
	    final boolean isForBack) {
	if (isForBack) {
	    getTatamiGraphicalObjectFrom(gfxO).setOpacity((int) (((double) (opacity * 100)) / 255));
	} else {

	    final Color strokeColor = getTatamiGraphicalObjectFrom(gfxO)
		    .getStrokeColor();
	    getTatamiGraphicalObjectFrom(gfxO).setStrokeColor(
		    new Color(strokeColor.getRed(), strokeColor.getGreen(),
			    strokeColor.getBlue(), opacity));
	}
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setSize(com.google.gwt.user.client.ui.Widget, int, int)
     */
    public void setSize(final Widget canvas, final int width, final int height) {
	canvas.setSize(width + "px", height + "px");
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setStroke(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxColor, int)
     */
    public void setStroke(final GfxObject gfxO, final GfxColor color,
	    final int width) {
	if (getTatamiGraphicalObjectFrom(gfxO).getClass().equals(Text.class)) {
	    getTatamiGraphicalObjectFrom(gfxO).setOpacity(color.getAlpha());
	}
	getTatamiGraphicalObjectFrom(gfxO)
		.setStroke(convertColor(color), width);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setStrokeStyle(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxStyle)
     */
    public void setStrokeStyle(final GfxObject gfxO, final GfxStyle style) {
	getTatamiGraphicalObjectFrom(gfxO).setStrokeStyle(
		style.getStyleString());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#translate(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
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
    private com.objetdirect.tatami.client.gfx.Point pointConverter(Point p) {
	return new com.objetdirect.tatami.client.gfx.Point(p.getX(), p.getY());
    }
}