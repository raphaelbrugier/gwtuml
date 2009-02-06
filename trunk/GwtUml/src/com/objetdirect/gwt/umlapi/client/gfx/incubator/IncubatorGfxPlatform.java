package com.objetdirect.gwt.umlapi.client.gfx.incubator;

import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.IncubatorGfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.Line;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.Path;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.Rect;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.Text;
import com.objetdirect.gwt.umlapi.client.gfx.incubator.objects.VirtualGroup;

public class IncubatorGfxPlatform implements GfxPlatform {

	private GWTCanvasWithListeners canvas;
	private Set<GfxObject> canvasObjects = new HashSet<GfxObject>();

	public void addObjectListenerToCanvas(Widget canvas,
			final GfxObjectListener gfxObjectListener) {

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

		ClickListener clickListener = new ClickListener() {

			public void onClick(Widget sender) {
				gfxObjectListener.mouseClicked();
			}

		};
		((GWTCanvasWithListeners) canvas).addMouseListener(mouseListener);
		((GWTCanvasWithListeners) canvas).addClickListener(clickListener);
	}

	public void addToCanvas(Widget canvas, GfxObject gfxO, int x, int y) {
		getIncubatorGraphicalObjectFrom(gfxO).addOnCanvasAt(x, y);
		canvasObjects.add(gfxO);
		getIncubatorGraphicalObjectFrom(gfxO).draw((GWTCanvas) canvas);
	}

	public void addToVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO) {
		((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
				.add(getIncubatorGraphicalObjectFrom(gfxO));
	}

	public GfxObject buildLine(double x1, double y1, double x2, double y2) {
		return new IncubatorGfxObjectContainer(new Line((int) x1, (int) y1,
				(int) x2, (int) y2));
	}

	public GfxObject buildPath() {
		return new IncubatorGfxObjectContainer(new Path());
	}

	public GfxObject buildRect(double width, double height) {
		return new IncubatorGfxObjectContainer(new Rect((int) width,
				(int) height));
	}

	public GfxObject buildText(String text) {
		// TODO Auto-generated method stub
		return new IncubatorGfxObjectContainer(new Text(text));
	}

	public GfxObject buildVirtualGroup() {
		return new IncubatorGfxObjectContainer(new VirtualGroup());
	}

	public double getHeightFor(GfxObject gfxO) {
		if (gfxO != null)
			return getIncubatorGraphicalObjectFrom(gfxO).getHeight();
		return 0;
	}

	public double getWidthFor(GfxObject gfxO) {
		if (gfxO != null)
			return getIncubatorGraphicalObjectFrom(gfxO).getWidth();
		return 0;
	}

	public double getXFor(GfxObject gfxO) {
		if (gfxO != null)
			return getIncubatorGraphicalObjectFrom(gfxO).getX();
		return 0;
	}

	public double getYFor(GfxObject gfxO) {
		if (gfxO != null)
			return getIncubatorGraphicalObjectFrom(gfxO).getY();
		return 0;
	}

	public void lineTo(GfxObject gfxO, double x, double y) {
		((Path) getIncubatorGraphicalObjectFrom(gfxO)).lineTo(x, y);

	}

	public Widget makeCanvas() {
		return makeCanvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT,
				GfxColor.WHITE);
	}

	public Widget makeCanvas(int width, int height, GfxColor backgroundColor) {
		canvas = new GWTCanvasWithListeners(width, height);
		// Default values :
		canvas.setLineWidth(1);
		canvas.setStrokeStyle(Color.BLUEVIOLET);
		canvas.setBackgroundColor(new Color(backgroundColor.getRed(),
				backgroundColor.getBlue(), backgroundColor.getGreen()
		/*
		 * , backgroundColor.getAlpha() Disabled to ensure#@&~#! IE
		 * compatibility
		 */
		));
		canvas.clear();
		return canvas;
	}

	public void moveTo(GfxObject gfxO, double x, double y) {
		((Path) getIncubatorGraphicalObjectFrom(gfxO)).moveTo(x, y);
	}

	public void redraw() {
		Log.trace("{incubator} Redraw");
		canvas.clear();
		for (GfxObject gfxO : canvasObjects) {
			getIncubatorGraphicalObjectFrom(gfxO).draw(canvas);
		}
	}

	public void removeFromCanvas(Widget canvas, GfxObject gfxO) {
		getIncubatorGraphicalObjectFrom(gfxO).removeFromCanvas();
		redraw();

	}

	public void removeFromVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO,
			boolean isSilent) {
		((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
				.remove(getIncubatorGraphicalObjectFrom(gfxO));
		redraw();

	}
	public void clearVirtualGroup(GfxObject gfxOGroup) {
		((VirtualGroup) getIncubatorGraphicalObjectFrom(gfxOGroup))
		.clear();
	}
	public void setFillColor(GfxObject gfxO, GfxColor color) {
		getIncubatorGraphicalObjectFrom(gfxO).setFillColor(color);
		getIncubatorGraphicalObjectFrom(gfxO).draw(canvas);

	}

	public void setFont(GfxObject gfxO, GfxFont gfxF) {
		// TODO Auto-generated method stub

	}

	public void setStroke(GfxObject gfxO, GfxColor color, int width) {
		getIncubatorGraphicalObjectFrom(gfxO).setStrokeColor(color);
		getIncubatorGraphicalObjectFrom(gfxO).setStrokeWidth(width);
		getIncubatorGraphicalObjectFrom(gfxO).draw(canvas);
		// redraw();
	}

	public void setStrokeStyle(GfxObject gfxO, GfxStyle style) {
		getIncubatorGraphicalObjectFrom(gfxO).setStyle(style);
		getIncubatorGraphicalObjectFrom(gfxO).draw(canvas);
		// redraw();

	}

	public void translate(GfxObject gfxO, int x, int y) {
		getIncubatorGraphicalObjectFrom(gfxO).translate(x, y);
		getIncubatorGraphicalObjectFrom(gfxO).draw(canvas);
		redraw();

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

}
