/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.gfx;

import gwt.canvas.client.Canvas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.objetdirect.gwt.umlapi.client.engine.Point;

/**
 * This class implements the graphic platform using the <a href="http://code.google.com/p/gwt-canvas/">GWT Canvas</a> canvas library
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class GWTCanvasGfxPlatform implements GfxPlatform {
    static long lastRedrawTime = 0;
    static long timeBetween2Redraw = 10;

    private final Map<Canvas, CanvasBridge> canvasBridges = new HashMap<Canvas, CanvasBridge>();
    private final Set<GfxObject> canvasObjects = new HashSet<GfxObject>();
    private boolean toBeRedrawn = false;

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addObjectListenerToCanvas(com.google.gwt.user.client.ui.Widget, com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener)
     */
    public void addObjectListenerToCanvas(final Widget canvas,
	    final GfxObjectListener gfxObjectListener) {
	final CanvasBridge canvasBridge = this.canvasBridges.get(canvas);
	final MouseListener mouseListener = new MouseListener() {
	    /* (non-Javadoc)
	     * @see com.google.gwt.user.client.ui.MouseListener#onMouseDown(com.google.gwt.user.client.ui.Widget, int, int)
	     */
	    public void onMouseDown(final Widget sender, final int x,
		    final int y) {
		if (x < 0) {
		    gfxObjectListener.mousePressed(
			    IncubatorGfxObjectContainer
				    .getPointedObject(-x, -y), null);
		} else {
		    gfxObjectListener.mousePressed(
			    IncubatorGfxObjectContainer.getPointedObject(x, y), null);
		}
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see
	     * com.google.gwt.user.client.ui.MouseListener#onMouseEnter(com.
	     * google.gwt.user.client.ui.Widget)
	     */
	    public void onMouseEnter(final Widget sender) {
		// Unused
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see
	     * com.google.gwt.user.client.ui.MouseListener#onMouseLeave(com.
	     * google.gwt.user.client.ui.Widget)
	     */
	    public void onMouseLeave(final Widget sender) {
		// Unused
	    }

	    /* (non-Javadoc)
	     * @see com.google.gwt.user.client.ui.MouseListener#onMouseMove(com.google.gwt.user.client.ui.Widget, int, int)
	     */
	    public void onMouseMove(final Widget sender, final int x,
		    final int y) {
		gfxObjectListener.mouseMoved(null);
	    }

	    /* (non-Javadoc)
	     * @see com.google.gwt.user.client.ui.MouseListener#onMouseUp(com.google.gwt.user.client.ui.Widget, int, int)
	     */
	    public void onMouseUp(final Widget sender, final int x, final int y) {
		// TODO fix this hack :
		if (x < 0) {
		    gfxObjectListener.mouseDoubleClicked(
			    IncubatorGfxObjectContainer
				    .getPointedObject(-x, -y), null);
		} else {
		    gfxObjectListener.mouseReleased(IncubatorGfxObjectContainer
			    .getPointedObject(x, y), null);
		}
	    }
	};
	final ClickListener ClickHandler = new ClickListener() {
	    /* (non-Javadoc)
	     * @see com.google.gwt.user.client.ui.ClickListener#onClick(com.google.gwt.user.client.ui.Widget)
	     */
	    public void onClick(final Widget sender) {
		//Unused
	    }
	};
	canvasBridge.addMouseListener(mouseListener);
	canvasBridge.addClickListener(ClickHandler);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addToCanvas(com.google.gwt.user.client.ui.Widget, com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void addToCanvas(final Widget canvas, final GfxObject gfxO,
	    final Point location) {
	final CanvasBridge canvasBridge = this.canvasBridges.get(canvas);
	if (canvasBridge == null) {
	    Log.fatal("No bridge for " + canvas + " found");
	}
	getIncubatorGraphicalObjectFrom(gfxO).addOnCanvasAt(canvasBridge, location.getX(), location.getY());
	this.canvasObjects.add(gfxO);
	getIncubatorGraphicalObjectFrom(gfxO).draw();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addToVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void addToVirtualGroup(final GfxObject gfxOGroup,
	    final GfxObject gfxO) {
	((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
		.add(getIncubatorGraphicalObjectFrom(gfxO));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildLine(com.objetdirect.gwt.umlapi.client.engine.Point, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public GfxObject buildLine(final Point p1, final Point p2) {
	return new IncubatorGfxObjectContainer(new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildPath()
     */
    public GfxObject buildPath() {
	return new IncubatorGfxObjectContainer(new Path());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildRect(int, int)
     */
    public GfxObject buildRect(final int width, final int height) {
	return new IncubatorGfxObjectContainer(new Rect(width, height));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildText(java.lang.String)
     */
    public GfxObject buildText(final String text, final Point location) {
	return new IncubatorGfxObjectContainer(new Text(text));
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildVirtualGroup()
     */
    public GfxObject buildVirtualGroup() {
	return new IncubatorGfxObjectContainer(new VirtualGroup());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#clearVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void clearVirtualGroup(final GfxObject gfxOGroup) {
	((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup)).clear();
	redraw(getIncubatorGraphicalObjectFrom(gfxOGroup).getCanvas());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public GfxObject getGroup(final GfxObject gfxO) {
	return IncubatorGfxObjectContainer
		.getContainerOf(getIncubatorGraphicalObjectFrom(gfxO)
			.getParentGroup());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getHeightFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public int getTextHeightFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return getIncubatorGraphicalObjectFrom(gfxO).getHeight();
	}
	return 0;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getWidthFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public int getTextWidthFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return getIncubatorGraphicalObjectFrom(gfxO).getWidth();
	}
	return 0;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getLocationFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public Point getLocationFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return new Point(getIncubatorGraphicalObjectFrom(gfxO).getX(),  getIncubatorGraphicalObjectFrom(gfxO).getY());
	}
	return Point.getOrigin();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#lineTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void lineTo(final GfxObject gfxO, final Point location) {
	((Path) getIncubatorGraphicalObjectFrom(gfxO)).lineTo(location.getX(), location.getY());
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
	final GWTCanvasBridge gWTCanvasBridge = new GWTCanvasBridge(width,
		height);
	// Default values :
	gWTCanvasBridge.setLineWidth(1);
	gWTCanvasBridge.setStrokeStyle(Color.BLUEVIOLET);
	gWTCanvasBridge.setBackgroundColor(new Color(backgroundColor.getRed(),
		backgroundColor.getBlue(), backgroundColor.getGreen()
	/*
	 * , backgroundColor.getAlpha() Disabled to ensure#@&~#! IE
	 * compatibility
	 */
	));
	gWTCanvasBridge.clear();
	this.canvasBridges
		.put((Canvas) gWTCanvasBridge.getWidget(), gWTCanvasBridge);
	return gWTCanvasBridge.getWidget();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void moveTo(final GfxObject gfxO, final Point location) {
	((Path) getIncubatorGraphicalObjectFrom(gfxO)).moveTo(location.getX(), location.getY());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveToBack(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void moveToBack(final GfxObject gfxO) {
	// TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveToFront(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void moveToFront(final GfxObject gfxO) {
	// TODO Auto-generated method stub

    }

    void redraw(final CanvasBridge canvas) {
	if (System.currentTimeMillis() - lastRedrawTime > timeBetween2Redraw) {
	    Log.debug("Redraw");
	    canvas.clear();
	    for (final GfxObject gfxO : this.canvasObjects) {
		getIncubatorGraphicalObjectFrom(gfxO).draw();
	    }
	    lastRedrawTime = System.currentTimeMillis();
	    this.toBeRedrawn = true;
	} else {
	    if (this.toBeRedrawn) {
		final Timer t = new Timer() {
		    @Override
		    public void run() {
			redraw(canvas);
		    }
		};
		t.schedule((int) timeBetween2Redraw);
		this.toBeRedrawn = false;
	    }
	}

    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#removeFromCanvas(com.google.gwt.user.client.ui.Widget, com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
     */
    public void removeFromCanvas(final Widget canvas, final GfxObject gfxO) {
	final CanvasBridge canvasBridge = this.canvasBridges.get(canvas);
	getIncubatorGraphicalObjectFrom(gfxO).removeFromCanvas();
	redraw(canvasBridge);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#removeFromVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxObject, boolean)
     */
    public void removeFromVirtualGroup(final GfxObject gfxOGroup,
	    final GfxObject gfxO, final boolean isSilent) {
	((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
		.remove(getIncubatorGraphicalObjectFrom(gfxO));
	redraw(getIncubatorGraphicalObjectFrom(gfxOGroup).getCanvas());
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setFillColor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxColor)
     */
    public void setFillColor(final GfxObject gfxO, final GfxColor color) {
	getIncubatorGraphicalObjectFrom(gfxO).setFillColor(color);
	getIncubatorGraphicalObjectFrom(gfxO).draw();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setFont(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxFont)
     */
    public void setFont(final GfxObject gfxO, final GfxFont gfxF) {
	// TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setOpacity(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, int, boolean)
     */
    public void setOpacity(final GfxObject gfxO, final int opacity,
	    final boolean isForBack) {
	getIncubatorGraphicalObjectFrom(gfxO).setAlpha((float) opacity / 100);

    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setSize(com.google.gwt.user.client.ui.Widget, int, int)
     */
    public void setSize(final Widget canvas, final int width, final int height) {
	canvas.setPixelSize(width, height);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setStroke(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxColor, int)
     */
    public void setStroke(final GfxObject gfxO, final GfxColor color,
	    final int width) {
	getIncubatorGraphicalObjectFrom(gfxO).setStrokeColor(color);
	getIncubatorGraphicalObjectFrom(gfxO).setStrokeWidth(width);
	getIncubatorGraphicalObjectFrom(gfxO).draw();
	// redraw();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setStrokeStyle(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.gfx.GfxStyle)
     */
    public void setStrokeStyle(final GfxObject gfxO, final GfxStyle style) {
	getIncubatorGraphicalObjectFrom(gfxO).setStyle(style);
	getIncubatorGraphicalObjectFrom(gfxO).draw();
	// redraw();
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#translate(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void translate(final GfxObject gfxO, final Point location) {
	getIncubatorGraphicalObjectFrom(gfxO).translate(location.getX(), location.getY());
	getIncubatorGraphicalObjectFrom(gfxO).draw();
	redraw(getIncubatorGraphicalObjectFrom(gfxO).getCanvas());
    }

    private IncubatorGfxObject getIncubatorGraphicalObjectFrom(
	    final GfxObject gfxO) {
	return ((IncubatorGfxObjectContainer) gfxO).getGraphicObject();
    }
    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#curveTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, com.objetdirect.gwt.umlapi.client.engine.Point, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    public void curveTo(GfxObject gfxObject, Point location, Point control) {
	lineTo(gfxObject, location);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildImage(java.lang.String)
     */
    @Override
    public GfxObject buildImage(String url) {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#rotate(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, float, com.objetdirect.gwt.umlapi.client.engine.Point)
     */
    @Override
    public void rotate(GfxObject gfxObject, float angle, Point center) {
	// TODO Auto-generated method stub
	
    }
}
