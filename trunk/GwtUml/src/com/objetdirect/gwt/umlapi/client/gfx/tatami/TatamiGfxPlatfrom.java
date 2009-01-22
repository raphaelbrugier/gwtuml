package com.objetdirect.gwt.umlapi.client.gfx.tatami;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
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

    public Widget makeCanvas() {
        return makeCanvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT, GfxColor.WHITE);
    }


    public Widget makeCanvas(int width, int height, GfxColor backgroundColor) {
        GraphicCanvas canvas = new GraphicCanvas();
        
        
        canvas.setSize(width + "px", height + "px");
        DOM.setStyleAttribute(canvas.getElement(), "backgroundColor",
				new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()
						/*, backgroundColor.getAlpha() Disabled to ensure *#@&~#! IE compatibility */,0
						).toHex());

 
        return canvas;
    }


    public void addToCanvas(Widget canvas, GfxObject gfxO, int x, int y) {
        ((GraphicCanvas) canvas).add(getTatamiGraphicalObjectFrom(gfxO), x, y);

    }


    public void addToVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO) {
        ((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup)).add(getTatamiGraphicalObjectFrom(gfxO));

    }


    public GfxObject buildLine(double x1, double y1, double x2, double y2) {
        return new TatamiGfxObjectContainer(new Line((int) x1, (int) y1, (int) x2, (int) y2));
    }


    public GfxObject buildPath() {
        return new TatamiGfxObjectContainer(new Path());
    }


    public GfxObject buildRect(double width, double height) {
        return new TatamiGfxObjectContainer(new Rect(width, height));
    }


    public GfxObject buildText(String text) {
        return new TatamiGfxObjectContainer(new Text(text));
    }


    public GfxObject buildVirtualGroup() {
        return new TatamiGfxObjectContainer(new VirtualGroup());
    }


    public double getHeightFor(GfxObject gfxO) {
        return ((Text) getTatamiGraphicalObjectFrom(gfxO)).getHeight();
    }


    public double getWidthFor(GfxObject gfxO) {
        return ((Text) getTatamiGraphicalObjectFrom(gfxO)).getWidth();
    }


    public double getXFor(GfxObject gfxO) {
        return getTatamiGraphicalObjectFrom(gfxO).getX();
    }


    public double getYFor(GfxObject gfxO) {
        return getTatamiGraphicalObjectFrom(gfxO).getY();
    }


    public void lineTo(GfxObject gfxO, double x, double y) {
        ((Path) getTatamiGraphicalObjectFrom(gfxO)).lineTo(x, y);
    }


    public void moveTo(GfxObject gfxO, double x, double y) {
    	((Path) getTatamiGraphicalObjectFrom(gfxO)).moveTo(x, y);

    }


    public void removeFromCanvas(Widget canvas, GfxObject gfxO) {
    	((GraphicCanvas) canvas).remove(getTatamiGraphicalObjectFrom(gfxO));

    }


    public void removeFromVirtualGroup(GfxObject gfxOGroup, GfxObject gfxO,
            boolean isSilent) {
    	 ((VirtualGroup) getTatamiGraphicalObjectFrom(gfxOGroup)).remove(getTatamiGraphicalObjectFrom(gfxO), isSilent);

    }


    public void setFillColor(GfxObject gfxO, GfxColor color) {
    	getTatamiGraphicalObjectFrom(gfxO).setFillColor(convertColor(color));

    }


    public void setFont(GfxObject gfxO, GfxFont gfxF) {
    	((Text) getTatamiGraphicalObjectFrom(gfxO)).setFont(convertFont(gfxF));

    }


    public void setStroke(GfxObject gfxO, GfxColor color, int width) {
    	 getTatamiGraphicalObjectFrom(gfxO).setStroke(convertColor(color), width);

    }

	public void setStrokeStyle(GfxObject gfxO, GfxStyle style) {
		 getTatamiGraphicalObjectFrom(gfxO).setStrokeStyle(style.getStyleString());		
	}

    public void translate(GfxObject gfxO, int x, int y) {
    	getTatamiGraphicalObjectFrom(gfxO).translate(x, y);
    }

    
    private GraphicObject getTatamiGraphicalObjectFrom(GfxObject gfxO) {
    	return ((TatamiGfxObjectContainer) gfxO).getGraphicObject();
    }
    private Color convertColor(GfxColor gfxColor) {
    	return new Color(gfxColor.getRed(), gfxColor.getGreen(), gfxColor.getBlue(), gfxColor.getAlpha());
    }
    private Font convertFont(GfxFont gfxFont) {
    	return new Font(gfxFont.getFamily(), gfxFont.getSize(), gfxFont.getStyle(), gfxFont.getVariant(), gfxFont.getWeight());
    }


	public void addObjectListenerToCanvas(Widget canvas, final GfxObjectListener gfxObjectListener) {
		
		GraphicObjectListener graphicObjectListener = new GraphicObjectListener() 
		{
			
			public void mouseClicked(GraphicObject graphicObject, Event e) {
				gfxObjectListener.mouseClicked();
			}

			public void mouseDblClicked(GraphicObject graphicObject, Event e) {
				gfxObjectListener.mouseDblClicked(TatamiGfxObjectContainer.getContainerOf(graphicObject), DOM.eventGetClientX(e), DOM.eventGetClientY(e));
			}
			
			public void mouseMoved(GraphicObject graphicObject, Event e) {
				gfxObjectListener.mouseMoved(DOM.eventGetClientX(e), DOM.eventGetClientY(e));
			}

			public void mousePressed(GraphicObject graphicObject, Event e) {
				gfxObjectListener.mousePressed(TatamiGfxObjectContainer.getContainerOf(graphicObject), DOM.eventGetClientX(e), DOM.eventGetClientY(e));
			}

			public void mouseReleased(GraphicObject graphicObject, Event e) {
				gfxObjectListener.mouseReleased(TatamiGfxObjectContainer.getContainerOf(graphicObject), DOM.eventGetClientX(e), DOM.eventGetClientY(e));
			}		
		};
		((GraphicCanvas) canvas).addGraphicObjectListener(graphicObjectListener);
	}


}