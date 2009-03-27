package com.objetdirect.gwt.umlapi.client.gfx.tatami;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
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
	public void addObjectListenerToCanvas(Widget canvas,
			final GfxObjectListener gfxObjectListener) {
		GraphicObjectListener graphicObjectListener = new GraphicObjectListener() {
			public void mouseClicked(GraphicObject graphicObject, Event e) {
				gfxObjectListener.mouseClicked();
			}
			public void mouseDblClicked(GraphicObject graphicObject, Event e) {
				Log.warn(""+graphicObject);
				gfxObjectListener.mouseDblClicked(TatamiGfxObjectContainer
						.getContainerOf(graphicObject), DOM.eventGetClientX(e),
						DOM.eventGetClientY(e));
			}
			public void mouseMoved(GraphicObject graphicObject, Event e) {
				gfxObjectListener.mouseMoved(DOM.eventGetClientX(e), DOM
						.eventGetClientY(e));
			}
			public void mousePressed(GraphicObject graphicObject, Event e) {
				if (e.getButton() == Event.BUTTON_RIGHT)
					gfxObjectListener
							.mouseRightClickPressed(TatamiGfxObjectContainer
									.getContainerOf(graphicObject), DOM
									.eventGetClientX(e), DOM.eventGetClientY(e));
				else
					gfxObjectListener
							.mouseLeftClickPressed(TatamiGfxObjectContainer
									.getContainerOf(graphicObject), DOM
									.eventGetClientX(e), DOM.eventGetClientY(e));
			}
			public void mouseReleased(GraphicObject graphicObject, Event e) {
				gfxObjectListener.mouseReleased(TatamiGfxObjectContainer
						.getContainerOf(graphicObject), DOM.eventGetClientX(e),
						DOM.eventGetClientY(e));
			}
		};
		((GraphicCanvas) canvas)
				.addGraphicObjectListener(graphicObjectListener);
	}
	public void addToCanvas(Widget canvas, GfxObject gfxO, int x, int y) {
		Log.info("Adding to Tcanvas : "
				+ UMLDrawerHelper
						.getShortName(getTatamiGraphicalObjectFrom(gfxO)));
		((GraphicCanvas) canvas).add(getTatamiGraphicalObjectFrom(gfxO), x, y);
	}
	public void addToVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO) {
		Log.trace("Adding " + gfxO + " to " + gfxOGroup);
		((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup))
				.add(getTatamiGraphicalObjectFrom(gfxO));
	}
	public GfxObject buildLine(int x1, int y1, int x2, int y2) {
		return new TatamiGfxObjectContainer(new Line( x1,  y1,
				 x2,  y2));
	}
	public GfxObject buildPath() {
		return new TatamiGfxObjectContainer(new Path());
	}
	public GfxObject buildRect(int width, int height) {
		return new TatamiGfxObjectContainer(new Rect(width, height));
	}
	public GfxObject buildText(String text) {
		
		return new TatamiGfxObjectContainer(new Text(text));
	}
	public GfxObject buildVirtualGroup() {
		GfxObject vg = new TatamiGfxObjectContainer(new VirtualGroup());
		Log.trace("Creating " + vg);
		return vg;
	}
	public int getHeightFor(GfxObject gfxO) {
		if (gfxO != null)
			return (int) (((Text) getTatamiGraphicalObjectFrom(gfxO)).getHeight() * 4 / 5); // Converting point to pixel
		return 0;
	}
	public int getWidthFor(GfxObject gfxO) {
		if (gfxO != null) {
			return (int)  (((Text) getTatamiGraphicalObjectFrom(gfxO)).getWidth() * 4 / 5); // Converting point to pixel	
		}
		
		return 0;
	}
	public int getXFor(GfxObject gfxO) {
		if (gfxO != null)
			return (int)  getTatamiGraphicalObjectFrom(gfxO).getX();
		return 0;
	}
	public int getYFor(GfxObject gfxO) {
		if (gfxO != null)
			return (int)  getTatamiGraphicalObjectFrom(gfxO).getY();
		return 0;
	}
	public void lineTo(GfxObject gfxO, int x, int y) {
		((Path) getTatamiGraphicalObjectFrom(gfxO)).lineTo(x, y);
	}
	public Widget makeCanvas() {
		return makeCanvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT,
				GfxColor.WHITE);
	}
	public Widget makeCanvas(int width, int height, GfxColor backgroundColor) {
		GraphicCanvas canvas = new GraphicCanvas();
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
	
	public void setSize(Widget canvas, int width, int height) {
		canvas.setSize(width + "px", height + "px");
	}
	
	public void moveTo(GfxObject gfxO, int x, int y) {
		((Path) getTatamiGraphicalObjectFrom(gfxO)).moveTo(x, y);
	}
	public void removeFromCanvas(Widget canvas, GfxObject gfxO) {
		Log.info("Removing from Tcanvas : "
				+ UMLDrawerHelper
						.getShortName(getTatamiGraphicalObjectFrom(gfxO)));
		((GraphicCanvas) canvas).remove(getTatamiGraphicalObjectFrom(gfxO));
	}
	public void removeFromVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO,
			boolean isSilent) {
		((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup)).remove(
				getTatamiGraphicalObjectFrom(gfxO), isSilent);
	}	
	public void clearVirtualGroup(GfxObject gfxOGroup) {
		((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup)).clear();
	}
	public void setFillColor(GfxObject gfxO, GfxColor color) {
		getTatamiGraphicalObjectFrom(gfxO).setFillColor(convertColor(color));
	}
	public void setFont(GfxObject gfxO, GfxFont gfxF) {
		((Text) getTatamiGraphicalObjectFrom(gfxO)).setFont(convertFont(gfxF));
	}
	public void setStroke(GfxObject gfxO, GfxColor color, int width) {
		getTatamiGraphicalObjectFrom(gfxO)
				.setStroke(convertColor(color), width);
	}
	public void setStrokeStyle(GfxObject gfxO, GfxStyle style) {
		getTatamiGraphicalObjectFrom(gfxO).setStrokeStyle(
				style.getStyleString());
	}
	public void translate(GfxObject gfxO, int x, int y) {
		getTatamiGraphicalObjectFrom(gfxO).translate(x, y);
	}
	private Color convertColor(GfxColor gfxColor) {
		return new Color(gfxColor.getRed(), gfxColor.getGreen(), gfxColor
				.getBlue(), gfxColor.getAlpha());
	}
	private Font convertFont(GfxFont gfxFont) {
		return new Font(gfxFont.getFamily(), gfxFont.getSize(), gfxFont
				.getStyle(), gfxFont.getVariant(), gfxFont.getWeight());
	}
	private GraphicObject getTatamiGraphicalObjectFrom(GfxObject gfxO) {
		return ((TatamiGfxObjectContainer) gfxO).getGraphicObject();
	}
	public void moveToBack(GfxObject gfxO) {
		getTatamiGraphicalObjectFrom(gfxO).moveToBack();
		
	}
	
	public GfxObject getGroup(GfxObject gfxO) {
		return TatamiGfxObjectContainer.getContainerOf(getTatamiGraphicalObjectFrom(gfxO).getGroup());
		
	}
	public void moveToFront(GfxObject gfxO) {
		getTatamiGraphicalObjectFrom(gfxO).moveToFront();
		
	}
	public void hide(GfxObject gfxO) {
		getTatamiGraphicalObjectFrom(gfxO).translate(5000, 5000);
	}
	public void show(GfxObject gfxO) {
		getTatamiGraphicalObjectFrom(gfxO).translate(-5000, -5000);
	}
}