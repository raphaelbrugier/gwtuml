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

import gwt.canvas.client.Canvas;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;

class GWTCanvasBridge implements CanvasBridge {

	private final Canvas	gwtCanvas;

	public GWTCanvasBridge(final int width, final int height) {
		super();
		this.gwtCanvas = new Canvas(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addClickListener (com.google.gwt.user.client.ui.ClickListener)
	 */
	public void addClickListener(final ClickListener clickListener) {
		this.gwtCanvas.addClickListener(clickListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addMouseListener (com.google.gwt.user.client.ui.MouseListener)
	 */
	public void addMouseListener(final MouseListener mouseListener) {
		this.gwtCanvas.addMouseListener(mouseListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#beginPath()
	 */
	public void beginPath() {
		this.gwtCanvas.beginPath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#clear()
	 */
	public void clear() {
		this.gwtCanvas.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#closePath()
	 */
	public void closePath() {
		this.gwtCanvas.closePath();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fill()
	 */
	public void fill() {
		this.gwtCanvas.fill();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fillRect (int, int, int, int)
	 */
	public void fillRect(final int x, final int y, final int w, final int h) {
		this.gwtCanvas.fillRect(x, y, w, h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#getWidget()
	 */
	public Widget getWidget() {
		return this.gwtCanvas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#lineTo(int, int)
	 */
	public void lineTo(final int i, final int j) {
		this.gwtCanvas.lineTo(i, j);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#moveTo(int, int)
	 */
	public void moveTo(final int x, final int y) {
		this.gwtCanvas.moveTo(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#restoreContext ()
	 */
	public void restoreContext() {
		this.gwtCanvas.restore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#saveContext ()
	 */
	public void saveContext() {
		this.gwtCanvas.save();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge# setBackgroundColor(com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setBackgroundColor(final Color color) {
		this.gwtCanvas.setBackgroundColor(color.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setFillStyle (com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setFillStyle(final Color fillColor) {
		this.gwtCanvas.setFillStyle(fillColor.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setLineWidth (int)
	 */
	public void setLineWidth(final int strokeWidth) {
		this.gwtCanvas.setLineWidth(strokeWidth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setStrokeStyle (com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setStrokeStyle(final Color strokeColor) {
		this.gwtCanvas.setStrokeStyle(strokeColor.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#stroke()
	 */
	public void stroke() {
		this.gwtCanvas.stroke();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#strokeRect (int, int, int, int)
	 */
	public void strokeRect(final int x, final int y, final int w, final int h) {
		this.gwtCanvas.strokeRect(x, y, w, h);
	}

}
