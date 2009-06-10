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

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
class IncubatorCanvasBridge implements CanvasBridge {

	private final GWTCanvasWithListeners	gwtCanvasWithListeners;

	public IncubatorCanvasBridge(final int width, final int height) {
		super();
		this.gwtCanvasWithListeners = new GWTCanvasWithListeners(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addClickListener (com.google.gwt.user.client.ui.ClickListener)
	 */
	public void addClickListener(final ClickListener clickListener) {
		this.gwtCanvasWithListeners.addClickListener(clickListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addMouseListener (com.google.gwt.user.client.ui.MouseListener)
	 */
	public void addMouseListener(final MouseListener mouseListener) {
		this.gwtCanvasWithListeners.addMouseListener(mouseListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#beginPath()
	 */
	public void beginPath() {
		this.gwtCanvasWithListeners.beginPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#clear()
	 */
	public void clear() {
		this.gwtCanvasWithListeners.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#closePath()
	 */
	public void closePath() {
		this.gwtCanvasWithListeners.closePath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fill()
	 */
	public void fill() {
		this.gwtCanvasWithListeners.fill();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fillRect (int, int, int, int)
	 */
	public void fillRect(final int x, final int y, final int w, final int h) {
		this.gwtCanvasWithListeners.fillRect(x, y, w, h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#getWidget()
	 */
	public Widget getWidget() {
		return this.gwtCanvasWithListeners;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#lineTo(int, int)
	 */
	public void lineTo(final int i, final int j) {
		this.gwtCanvasWithListeners.lineTo(i, j);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#moveTo(int, int)
	 */
	public void moveTo(final int x, final int y) {
		this.gwtCanvasWithListeners.moveTo(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#restoreContext ()
	 */
	public void restoreContext() {
		this.gwtCanvasWithListeners.restoreContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#saveContext ()
	 */
	public void saveContext() {
		this.gwtCanvasWithListeners.saveContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge# setBackgroundColor(com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setBackgroundColor(final Color color) {
		this.gwtCanvasWithListeners.setBackgroundColor(color);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setFillStyle (com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setFillStyle(final Color fillColor) {
		this.gwtCanvasWithListeners.setFillStyle(fillColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setLineWidth (int)
	 */
	public void setLineWidth(final int strokeWidth) {
		this.gwtCanvasWithListeners.setLineWidth(strokeWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setStrokeStyle (com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setStrokeStyle(final Color strokeColor) {
		this.gwtCanvasWithListeners.setStrokeStyle(strokeColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#stroke()
	 */
	public void stroke() {
		this.gwtCanvasWithListeners.stroke();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#strokeRect (int, int, int, int)
	 */
	public void strokeRect(final int x, final int y, final int w, final int h) {
		this.gwtCanvasWithListeners.strokeRect(x, y, w, h);
	}

}
