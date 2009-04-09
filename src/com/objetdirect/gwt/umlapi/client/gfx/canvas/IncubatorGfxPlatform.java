package com.objetdirect.gwt.umlapi.client.gfx.canvas;

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
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.IncubatorGfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.Line;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.Path;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.Rect;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.Text;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.objects.VirtualGroup;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class IncubatorGfxPlatform implements GfxPlatform {
    public static long lastRedrawTime = 0;
    public static long timeBetween2Redraw = 10;

    private final Map<GWTCanvasWithListeners, CanvasBridge> canvasBridges = new HashMap<GWTCanvasWithListeners, CanvasBridge>();
    // private CanvasBridge incubatorCanvasBridge;
    private final Set<GfxObject> canvasObjects = new HashSet<GfxObject>();
    private boolean toBeRedrawn = false;

    public void addObjectListenerToCanvas(final Object canvas,
	    final GfxObjectListener gfxObjectListener) {
	Log.trace("adding " + gfxObjectListener + " on " + canvas);
	final CanvasBridge canvasBridge = this.canvasBridges.get(canvas);
	final MouseListener mouseListener = new MouseListener() {
	    public void onMouseDown(final Widget sender, final int x,
		    final int y) {
		if (x < 0) {
		    gfxObjectListener.mouseRightClickPressed(
			    IncubatorGfxObjectContainer
				    .getPointedObject(-x, -y), -x, -y);
		} else {
		    gfxObjectListener.mouseLeftClickPressed(
			    IncubatorGfxObjectContainer.getPointedObject(x, y),
			    x, y);
		}
	    }

	    public void onMouseEnter(final Widget sender) {
		// Unused
	    }

	    public void onMouseLeave(final Widget sender) {
		// Unused
	    }

	    public void onMouseMove(final Widget sender, final int x,
		    final int y) {
		gfxObjectListener.mouseMoved(x, y);
	    }

	    public void onMouseUp(final Widget sender, final int x, final int y) {
		// TODO fix this hack :
		if (x < 0) {
		    gfxObjectListener.mouseDblClicked(
			    IncubatorGfxObjectContainer
				    .getPointedObject(-x, -y), -x, -y);
		} else {
		    gfxObjectListener.mouseReleased(IncubatorGfxObjectContainer
			    .getPointedObject(x, y), x, y);
		}
	    }
	};
	final ClickListener clickListener = new ClickListener() {
	    public void onClick(final Widget sender) {
		gfxObjectListener.mouseClicked();
	    }
	};
	Log.trace("adding mouseListener" + mouseListener);
	canvasBridge.addMouseListener(mouseListener);
	Log.trace("adding clickListener" + clickListener);
	canvasBridge.addClickListener(clickListener);
    }

    public void addToCanvas(final Object canvas, final GfxObject gfxO,
	    final Point location) {
	final CanvasBridge canvasBridge = this.canvasBridges.get(canvas);
	if (canvasBridge == null) {
	    Log.fatal("No bridge for " + canvas + " found");
	}
	getIncubatorGraphicalObjectFrom(gfxO).addOnCanvasAt(canvasBridge, location.getX(), location.getY());
	this.canvasObjects.add(gfxO);
	getIncubatorGraphicalObjectFrom(gfxO).draw();
    }

    public void addToVirtualGroup(final GfxObject gfxOGroup,
	    final GfxObject gfxO) {
	((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
		.add(getIncubatorGraphicalObjectFrom(gfxO));
    }

    public GfxObject buildLine(final Point p1, Point p2) {
	return new IncubatorGfxObjectContainer(new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
    }

    public GfxObject buildPath() {
	return new IncubatorGfxObjectContainer(new Path());
    }

    public GfxObject buildRect(final int width, final int height) {
	return new IncubatorGfxObjectContainer(new Rect(width, height));
    }

    public GfxObject buildText(final String text) {
	// TODO Auto-generated method stub
	return new IncubatorGfxObjectContainer(new Text(text));
    }

    public GfxObject buildVirtualGroup() {
	return new IncubatorGfxObjectContainer(new VirtualGroup());
    }

    public void clearVirtualGroup(final GfxObject gfxOGroup) {
	((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup)).clear();
	redraw(getIncubatorGraphicalObjectFrom(gfxOGroup).getCanvas());
    }

    public GfxObject getGroup(final GfxObject gfxO) {
	return IncubatorGfxObjectContainer
		.getContainerOf(getIncubatorGraphicalObjectFrom(gfxO)
			.getParentGroup());
    }

    public int getHeightFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return getIncubatorGraphicalObjectFrom(gfxO).getHeight();
	}
	return 0;
    }

    public int getWidthFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return getIncubatorGraphicalObjectFrom(gfxO).getWidth();
	}
	return 0;
    }

    public Point getLocationFor(final GfxObject gfxO) {
	if (gfxO != null) {
	    return new Point(getIncubatorGraphicalObjectFrom(gfxO).getX(), getIncubatorGraphicalObjectFrom(gfxO).getY());
	}
	return Point.getOrigin();
    }

    public void lineTo(final GfxObject gfxO, final Point location) {
	((Path) getIncubatorGraphicalObjectFrom(gfxO)).lineTo(location.getX(), location.getY());
    }

    public Widget makeCanvas() {
	return makeCanvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT,
		GfxColor.WHITE);
    }

    public Widget makeCanvas(final int width, final int height,
	    final GfxColor backgroundColor) {
	final IncubatorCanvasBridge incubatorCanvasBridge = new IncubatorCanvasBridge(
		width, height);
	// Default values :
	incubatorCanvasBridge.setLineWidth(1);
	incubatorCanvasBridge.setStrokeStyle(Color.BLUEVIOLET);
	incubatorCanvasBridge.setBackgroundColor(new Color(backgroundColor
		.getRed(), backgroundColor.getBlue(), backgroundColor
		.getGreen()
	/*
	 * , backgroundColor.getAlpha() Disabled to ensure#@&~#! IE
	 * compatibility
	 */
	));
	incubatorCanvasBridge.clear();
	this.canvasBridges.put((GWTCanvasWithListeners) incubatorCanvasBridge
		.getWidget(), incubatorCanvasBridge);
	return incubatorCanvasBridge.getWidget();
    }

    public void moveTo(final GfxObject gfxO, final Point location) {
	((Path) getIncubatorGraphicalObjectFrom(gfxO)).moveTo(location.getX(), location.getY());
    }

    public void moveToBack(final GfxObject gfxO) {
	// TODO Auto-generated method stub

    }

    public void moveToFront(final GfxObject gfxO) {
	// TODO Auto-generated method stub

    }

    public void redraw(final CanvasBridge canvas) {
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

    public void removeFromCanvas(final Object canvas, final GfxObject gfxO) {
	final CanvasBridge canvasBridge = this.canvasBridges.get(canvas);
	getIncubatorGraphicalObjectFrom(gfxO).removeFromCanvas();
	redraw(canvasBridge);
    }

    public void removeFromVirtualGroup(final GfxObject gfxOGroup,
	    final GfxObject gfxO, final boolean isSilent) {
	((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
		.remove(getIncubatorGraphicalObjectFrom(gfxO));
	redraw(getIncubatorGraphicalObjectFrom(gfxOGroup).getCanvas());
    }

    public void setFillColor(final GfxObject gfxO, final GfxColor color) {
	getIncubatorGraphicalObjectFrom(gfxO).setFillColor(color);
	getIncubatorGraphicalObjectFrom(gfxO).draw();
    }

    public void setFont(final GfxObject gfxO, final GfxFont gfxF) {
	// TODO Auto-generated method stub
    }

    public void setOpacity(final GfxObject gfxO, final int opacity,
	    final boolean isForBack) {
	getIncubatorGraphicalObjectFrom(gfxO).setAlpha((float) opacity / 100);

    }

    public void setSize(final Widget canvas, final int width, final int height) {
	canvas.setPixelSize(width, height);
    }

    public void setStroke(final GfxObject gfxO, final GfxColor color,
	    final int width) {
	getIncubatorGraphicalObjectFrom(gfxO).setStrokeColor(color);
	getIncubatorGraphicalObjectFrom(gfxO).setStrokeWidth(width);
	getIncubatorGraphicalObjectFrom(gfxO).draw();
	// redraw();
    }

    public void setStrokeStyle(final GfxObject gfxO, final GfxStyle style) {
	getIncubatorGraphicalObjectFrom(gfxO).setStyle(style);
	getIncubatorGraphicalObjectFrom(gfxO).draw();
	// redraw();
    }

    public void translate(final GfxObject gfxO, final Point location) {
	getIncubatorGraphicalObjectFrom(gfxO).translate(location.getX(), location.getY());
	getIncubatorGraphicalObjectFrom(gfxO).draw();
	redraw(getIncubatorGraphicalObjectFrom(gfxO).getCanvas());
    }

    private IncubatorGfxObject getIncubatorGraphicalObjectFrom(
	    final GfxObject gfxO) {
	return ((IncubatorGfxObjectContainer) gfxO).getGraphicObject();
    }

}
