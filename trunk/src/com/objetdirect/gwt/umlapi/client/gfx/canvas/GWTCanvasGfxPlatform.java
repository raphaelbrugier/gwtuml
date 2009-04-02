package com.objetdirect.gwt.umlapi.client.gfx.canvas;

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

public class GWTCanvasGfxPlatform  implements GfxPlatform {
private Set<GfxObject> canvasObjects = new HashSet<GfxObject>();
	private Map<Canvas, CanvasBridge> canvasBridges = new HashMap<Canvas, CanvasBridge>();
	
	public static long timeBetween2Redraw = 10;
	public static long lastRedrawTime = 0;
	private boolean toBeRedrawn = false; 
	public void addObjectListenerToCanvas(Object canvas,
			final GfxObjectListener gfxObjectListener) {
		CanvasBridge canvasBridge = canvasBridges.get((Canvas) canvas);
		MouseListener mouseListener = new MouseListener() {
			public void onMouseDown(Widget sender, int x, int y) {
				if (x < 0)
					gfxObjectListener.mouseRightClickPressed(
							IncubatorGfxObjectContainer
							.getPointedObject(-x, -y), -x, -y);
				else
					gfxObjectListener.mouseLeftClickPressed(
							IncubatorGfxObjectContainer.getPointedObject(x, y),
							x, y);
			}
			public void onMouseEnter(Widget sender) {
			}
			public void onMouseLeave(Widget sender) {
			}
			public void onMouseMove(Widget sender, int x, int y) {
				gfxObjectListener.mouseMoved(x, y);
			}
			public void onMouseUp(Widget sender, int x, int y) {
				// TODO fix this hack :
				if (x < 0)
					gfxObjectListener.mouseDblClicked(
							IncubatorGfxObjectContainer
							.getPointedObject(-x, -y), -x, -y);
				else
					gfxObjectListener.mouseReleased(IncubatorGfxObjectContainer
							.getPointedObject(x, y), x, y);
			}
		};
		ClickListener ClickHandler = new ClickListener() {
			public void onClick(Widget sender) {
				gfxObjectListener.mouseClicked();
			}
		};
		canvasBridge.addMouseListener(mouseListener);
		canvasBridge.addClickListener(ClickHandler);
	}
	public void addToCanvas(Object canvas, GfxObject gfxO, int x, int y) {
		CanvasBridge canvasBridge = canvasBridges.get((Canvas) canvas);
		if(canvasBridge == null) Log.fatal("No bridge for " + canvas + " found");
		getIncubatorGraphicalObjectFrom(gfxO).addOnCanvasAt(canvasBridge, x, y);
		canvasObjects.add(gfxO);
		getIncubatorGraphicalObjectFrom(gfxO).draw();
	}
	public void addToVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO) {
		((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
		.add(getIncubatorGraphicalObjectFrom(gfxO));
	}
	public GfxObject buildLine(int x1, int y1, int x2, int y2) {
		return new IncubatorGfxObjectContainer(new Line( x1,  y1,
				x2,  y2));
	}
	public GfxObject buildPath() {
		return new IncubatorGfxObjectContainer(new Path());
	}
	public GfxObject buildRect(int width, int height) {
		return new IncubatorGfxObjectContainer(new Rect( width,
				height));
	}
	public GfxObject buildText(String text) {
		// TODO Auto-generated method stub
		return new IncubatorGfxObjectContainer(new Text(text));
	}
	public GfxObject buildVirtualGroup() {
		return new IncubatorGfxObjectContainer(new VirtualGroup());
	}
	public int getHeightFor(GfxObject gfxO) {
		if (gfxO != null)
			return getIncubatorGraphicalObjectFrom(gfxO).getHeight();
		return 0;
	}
	public int getWidthFor(GfxObject gfxO) {
		if (gfxO != null)
			return getIncubatorGraphicalObjectFrom(gfxO).getWidth();
		return 0;
	}
	public int getXFor(GfxObject gfxO) {
		if (gfxO != null)
			return getIncubatorGraphicalObjectFrom(gfxO).getX();
		return 0;
	}
	public int getYFor(GfxObject gfxO) {
		if (gfxO != null)
			return getIncubatorGraphicalObjectFrom(gfxO).getY();
		return 0;
	}
	public void lineTo(GfxObject gfxO, int x, int y) {
		((Path) getIncubatorGraphicalObjectFrom(gfxO)).lineTo(x, y);
	}
	public Widget makeCanvas() {
		return makeCanvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT,
				GfxColor.WHITE);
	}
	public Widget makeCanvas(int width, int height, GfxColor backgroundColor) {
		GWTCanvasBridge gWTCanvasBridge = new GWTCanvasBridge(width, height);
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
		canvasBridges.put((Canvas) gWTCanvasBridge.getWidget(), gWTCanvasBridge);
		return gWTCanvasBridge.getWidget();
	}
	public void moveTo(GfxObject gfxO, int x, int y) {
		((Path) getIncubatorGraphicalObjectFrom(gfxO)).moveTo(x, y);
	}
	public void redraw(final CanvasBridge canvas) {
		if(System.currentTimeMillis() - lastRedrawTime > timeBetween2Redraw) {
			Log.debug("Redraw");
			canvas.clear();
			for (GfxObject gfxO : canvasObjects) {
				getIncubatorGraphicalObjectFrom(gfxO).draw();
			}
			lastRedrawTime = System.currentTimeMillis();
			 toBeRedrawn = true;
		}
		else {
			if(toBeRedrawn) {
			Timer t = new Timer() {
			      public void run() {
			    	  redraw(canvas);	
			      }
			    };
			    t.schedule((int) timeBetween2Redraw);
			    toBeRedrawn = false;
			}
		}
			
			    
	}
	public void removeFromCanvas(Object canvas, GfxObject gfxO) {
		CanvasBridge canvasBridge = canvasBridges.get((Canvas) canvas);
		getIncubatorGraphicalObjectFrom(gfxO).removeFromCanvas();
		redraw(canvasBridge);
	}
	public void removeFromVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO,
			boolean isSilent) {
		((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
		.remove(getIncubatorGraphicalObjectFrom(gfxO));
		redraw(getIncubatorGraphicalObjectFrom(gfxOGroup).getCanvas());
	}
	public void clearVirtualGroup(GfxObject gfxOGroup) {
		((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
		.clear();
		redraw(getIncubatorGraphicalObjectFrom(gfxOGroup).getCanvas());
	}
	public void setFillColor(GfxObject gfxO, GfxColor color) {
		getIncubatorGraphicalObjectFrom(gfxO).setFillColor(color);
		getIncubatorGraphicalObjectFrom(gfxO).draw();
	}
	public void setFont(GfxObject gfxO, GfxFont gfxF) {
		// TODO Auto-generated method stub
	}
	public void setStroke(GfxObject gfxO, GfxColor color, int width) {
		getIncubatorGraphicalObjectFrom(gfxO).setStrokeColor(color);
		getIncubatorGraphicalObjectFrom(gfxO).setStrokeWidth(width);
		getIncubatorGraphicalObjectFrom(gfxO).draw();
		//redraw();
	}
	public void setStrokeStyle(GfxObject gfxO, GfxStyle style) {
		getIncubatorGraphicalObjectFrom(gfxO).setStyle(style);
		getIncubatorGraphicalObjectFrom(gfxO).draw();
		//redraw();
	}
	public void translate(GfxObject gfxO, int x, int y) {
		getIncubatorGraphicalObjectFrom(gfxO).translate(x, y);
		getIncubatorGraphicalObjectFrom(gfxO).draw();
		redraw(getIncubatorGraphicalObjectFrom(gfxO).getCanvas());
	}
	private IncubatorGfxObject getIncubatorGraphicalObjectFrom(GfxObject gfxO) {
		return ((IncubatorGfxObjectContainer) gfxO).getGraphicObject();
	}
	public void moveToBack(GfxObject gfxO) {
		// TODO Auto-generated method stub

	}
	public void moveToFront(GfxObject gfxO) {
		// TODO Auto-generated method stub

	}
	public GfxObject getGroup(GfxObject gfxO) {
		return IncubatorGfxObjectContainer.getContainerOf(getIncubatorGraphicalObjectFrom(gfxO).getParentGroup());
	}

	public void setSize(Widget canvas, int width, int height) {		
	}
	
	public void setOpacity(GfxObject gfxO, int opacity, boolean isForBack) {
		getIncubatorGraphicalObjectFrom(gfxO).setAlpha(((float) opacity) / 100);
		
	}
	
}
