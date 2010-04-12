/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.gfx;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.tatami.client.gfx.Circle;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.Font;
import com.objetdirect.tatami.client.gfx.GraphicCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.GraphicObjectListener;
import com.objetdirect.tatami.client.gfx.ImageGfx;
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
public class TatamiGfxPlatform implements GfxPlatform {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addObjectListenerToCanvas(java.lang.Object,
	 * com.objetdirect.gwt.umlapi.client.gfx.GfxObjectListener)
	 */
	@Override
	public void addObjectListenerToCanvas(final Widget canvas, final GfxObjectListener gfxObjectListener) {
		final GraphicObjectListener graphicObjectListener = new GraphicObjectListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mouseClicked(com.objetdirect.tatami.client.gfx.GraphicObject,
			 * com.google.gwt.user.client.Event)
			 */
			@Override
			public void mouseClicked(final GraphicObject graphicObject, final Event e) {
				// This app doesn't need click event
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mouseDblClicked(com.objetdirect.tatami.client.gfx.GraphicObject,
			 * com.google.gwt.user.client.Event)
			 */
			@Override
			public void mouseDblClicked(final GraphicObject graphicObject, final Event e) {
				gfxObjectListener.mouseDoubleClicked(TatamiGfxObjectContainer.getContainerOf(graphicObject), e);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mouseMoved(com.objetdirect.tatami.client.gfx.GraphicObject,
			 * com.google.gwt.user.client.Event)
			 */
			@Override
			public void mouseMoved(final GraphicObject graphicObject, final Event e) {
				gfxObjectListener.mouseMoved(e);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mousePressed(com.objetdirect.tatami.client.gfx.GraphicObject,
			 * com.google.gwt.user.client.Event)
			 */
			@Override
			public void mousePressed(final GraphicObject graphicObject, final Event e) {
				gfxObjectListener.mousePressed(TatamiGfxObjectContainer.getContainerOf(graphicObject), e);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.objetdirect.tatami.client.gfx.GraphicObjectListener#mouseReleased(com.objetdirect.tatami.client.gfx.GraphicObject,
			 * com.google.gwt.user.client.Event)
			 */
			@Override
			public void mouseReleased(final GraphicObject graphicObject, final Event e) {
				gfxObjectListener.mouseReleased(TatamiGfxObjectContainer.getContainerOf(graphicObject), e);
			}
		};
		((GraphicCanvas) canvas).addGraphicObjectListener(graphicObjectListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addToCanvas(java.lang.Object, com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public void addToCanvas(final Widget canvas, final GfxObject gfxO, final Point location) {
		Log.trace("Adding to Tcanvas : " + GWTUMLDrawerHelper.getShortName(this.getTatamiGraphicalObjectFrom(gfxO)));
		((GraphicCanvas) canvas).add(this.getTatamiGraphicalObjectFrom(gfxO), location.getX(), location.getY());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#addToVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public void addToVirtualGroup(final GfxObject gfxOGroup, final GfxObject gfxO) {
		Log.trace("Adding " + gfxO + " to " + gfxOGroup);
		((VirtualGroup) this.getTatamiGraphicalObjectFrom(gfxOGroup)).add(this.getTatamiGraphicalObjectFrom(gfxO));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildRect(int, int)
	 */
	@Override
	public GfxObject buildCircle(final int radius) {
		return new TatamiGfxObjectContainer(new Circle(radius));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildImage(java.lang.String)
	 */
	@Override
	public GfxObject buildImage(final String url) {
		return new TatamiGfxObjectContainer(new ImageGfx(new Image(url)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildLine(com.objetdirect.gwt.umlapi.client.engine.Point,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public GfxObject buildLine(final Point p1, final Point p2) {
		return new TatamiGfxObjectContainer(new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildPath()
	 */
	@Override
	public GfxObject buildPath() {
		return new TatamiGfxObjectContainer(new Path());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildRect(int, int)
	 */
	@Override
	public GfxObject buildRect(final int width, final int height) {
		return new TatamiGfxObjectContainer(new Rect(width, height));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildText(java.lang.String)
	 */
	@Override
	public GfxObject buildText(final String text, final Point initLocation) {
		final Text txt = new Text(text);
		txt.translate(initLocation.getX(), initLocation.getY() + (int) ((txt.getHeight() * 64.) / 100.));
		return new TatamiGfxObjectContainer(txt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#buildVirtualGroup()
	 */
	@Override
	public GfxObject buildVirtualGroup() {
		final GfxObject vg = new TatamiGfxObjectContainer(new VirtualGroup());
		Log.trace("Creating " + vg);
		return vg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#clearVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public void clearVirtualGroup(final GfxObject gfxOGroup) {
		((VirtualGroup) this.getTatamiGraphicalObjectFrom(gfxOGroup)).clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public void curveTo(final GfxObject gfxO, final Point location, final Point control) {
		((Path) this.getTatamiGraphicalObjectFrom(gfxO)).qCurveTo(this.pointConverter(control), this.pointConverter(location));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public void curveTo(final GfxObject gfxO, final Point location, final Point control1, final Point control2) {
		((Path) this.getTatamiGraphicalObjectFrom(gfxO)).curveTo(this.pointConverter(control1), this.pointConverter(control2), this.pointConverter(location));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public GfxObject getGroup(final GfxObject gfxO) {
		return TatamiGfxObjectContainer.getContainerOf(this.getTatamiGraphicalObjectFrom(gfxO).getGroup());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getLocationFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public Point getLocationFor(final GfxObject gfxO) {
		if (gfxO != null) {
			if (this.getTatamiGraphicalObjectFrom(gfxO).getClass().equals(Text.class)) {
				final Text txt = (Text) this.getTatamiGraphicalObjectFrom(gfxO);
				return new Point(txt.getX(), txt.getY() - (int) ((txt.getHeight() * 64.) / 100.));
			}
			return new Point(this.getTatamiGraphicalObjectFrom(gfxO).getX(), this.getTatamiGraphicalObjectFrom(gfxO).getY());
		}
		return Point.getOrigin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getHeightFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public int getTextHeightFor(final GfxObject gfxO) {
		if (gfxO != null) {
			return (int) ((((Text) this.getTatamiGraphicalObjectFrom(gfxO)).getHeight() * 8.) / 10.); // Converting point to pixel
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#getWidthFor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public int getTextWidthFor(final GfxObject gfxO) {
		if (gfxO != null) {
			return (int) ((((Text) this.getTatamiGraphicalObjectFrom(gfxO)).getWidth() * 8.) / 10.); // Converting point to pixel
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#lineTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public void lineTo(final GfxObject gfxO, final Point location) {
		((Path) this.getTatamiGraphicalObjectFrom(gfxO)).lineTo(location.getX(), location.getY());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#makeCanvas()
	 */
	@Override
	public Widget makeCanvas() {
		return this.makeCanvas(GfxPlatform.DEFAULT_CANVAS_WIDTH, GfxPlatform.DEFAULT_CANVAS_HEIGHT, GfxColor.WHITE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#makeCanvas(int, int, com.objetdirect.gwt.umlapi.client.gfx.GfxColor)
	 */
	@Override
	public Widget makeCanvas(final int width, final int height, final GfxColor backgroundColor) {
		final GraphicCanvas canvas = new GraphicCanvas();
		canvas.setSize(width + "px", height + "px");
		DOM.setStyleAttribute(canvas.getElement(), "backgroundColor", new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()
		/*
		 * , backgroundColor.getAlpha() Disabled to ensure#@&~#! IE compatibility
		 */, 0).toHex());
		return canvas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveTo(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public void moveTo(final GfxObject gfxO, final Point location) {
		((Path) this.getTatamiGraphicalObjectFrom(gfxO)).moveTo(this.pointConverter(location));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveToBack(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public void moveToBack(final GfxObject gfxO) {
		this.getTatamiGraphicalObjectFrom(gfxO).moveToBack();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#moveToFront(com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public void moveToFront(final GfxObject gfxO) {
		this.getTatamiGraphicalObjectFrom(gfxO).moveToFront();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#removeFromCanvas(java.lang.Object, com.objetdirect.gwt.umlapi.client.gfx.GfxObject)
	 */
	@Override
	public void removeFromCanvas(final Widget canvas, final GfxObject gfxO) {
		Log.trace("Removing from Tcanvas : " + GWTUMLDrawerHelper.getShortName(this.getTatamiGraphicalObjectFrom(gfxO)));
		((GraphicCanvas) canvas).remove(this.getTatamiGraphicalObjectFrom(gfxO));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#removeFromVirtualGroup(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.gfx.GfxObject, boolean)
	 */
	@Override
	public void removeFromVirtualGroup(final GfxObject gfxOGroup, final GfxObject gfxO, final boolean isSilent) {
		((VirtualGroup) this.getTatamiGraphicalObjectFrom(gfxOGroup)).remove(this.getTatamiGraphicalObjectFrom(gfxO), isSilent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#translate(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public void rotate(final GfxObject gfxO, final float angle, final Point center) {
		this.getTatamiGraphicalObjectFrom(gfxO).rotate(angle, this.pointConverter(center));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setFillColor(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.gfx.GfxColor)
	 */
	@Override
	public void setFillColor(final GfxObject gfxO, final GfxColor color) {
		this.getTatamiGraphicalObjectFrom(gfxO).setFillColor(this.convertColor(color));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setFont(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.gfx.GfxFont)
	 */
	@Override
	public void setFont(final GfxObject gfxO, final GfxFont gfxF) {
		((Text) this.getTatamiGraphicalObjectFrom(gfxO)).setFont(this.convertFont(gfxF));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setOpacity(com.objetdirect.gwt.umlapi.client.gfx.GfxObject, int, boolean)
	 */
	@Override
	public void setOpacity(final GfxObject gfxO, final int opacity, final boolean isForBack) {
		if (isForBack) {
			this.getTatamiGraphicalObjectFrom(gfxO).setOpacity((int) (((double) (opacity * 100)) / 255));
		} else {

			final Color strokeColor = this.getTatamiGraphicalObjectFrom(gfxO).getStrokeColor();
			this.getTatamiGraphicalObjectFrom(gfxO).setStrokeColor(new Color(strokeColor.getRed(), strokeColor.getGreen(), strokeColor.getBlue(), opacity));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setSize(com.google.gwt.user.client.ui.Widget, int, int)
	 */
	@Override
	public void setSize(final Widget canvas, final int width, final int height) {
		((GraphicCanvas) canvas).setDimensions(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setStroke(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.gfx.GfxColor, int)
	 */
	@Override
	public void setStroke(final GfxObject gfxO, final GfxColor color, final int width) {
		if (this.getTatamiGraphicalObjectFrom(gfxO).getClass().equals(Text.class)) {
			this.getTatamiGraphicalObjectFrom(gfxO).setOpacity(color.getAlpha());
		}
		this.getTatamiGraphicalObjectFrom(gfxO).setStroke(this.convertColor(color), width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#setStrokeStyle(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.gfx.GfxStyle)
	 */
	@Override
	public void setStrokeStyle(final GfxObject gfxO, final GfxStyle style) {
		this.getTatamiGraphicalObjectFrom(gfxO).setStrokeStyle(style.getStyleString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform#translate(com.objetdirect.gwt.umlapi.client.gfx.GfxObject,
	 * com.objetdirect.gwt.umlapi.client.engine.Point)
	 */
	@Override
	public void translate(final GfxObject gfxO, final Point location) {
		this.getTatamiGraphicalObjectFrom(gfxO).translate(location.getX(), location.getY());
	}

	private Color convertColor(final GfxColor gfxColor) {
		return new Color(gfxColor.getRed(), gfxColor.getGreen(), gfxColor.getBlue(), gfxColor.getAlpha());
	}

	private Font convertFont(final GfxFont gfxFont) {
		return new Font(gfxFont.getFamily(), gfxFont.getSize(), gfxFont.getStyle(), gfxFont.getVariant(), gfxFont.getWeight());
	}

	private GraphicObject getTatamiGraphicalObjectFrom(final GfxObject gfxO) {
		return ((TatamiGfxObjectContainer) gfxO).getGraphicObject();
	}

	private com.objetdirect.tatami.client.gfx.Point pointConverter(final Point p) {
		return new com.objetdirect.tatami.client.gfx.Point(p.getX(), p.getY());
	}

}