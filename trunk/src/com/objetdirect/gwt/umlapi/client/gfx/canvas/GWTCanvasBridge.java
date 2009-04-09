package com.objetdirect.gwt.umlapi.client.gfx.canvas;

import gwt.canvas.client.Canvas;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;

public class GWTCanvasBridge implements CanvasBridge {

    private final Canvas gwtCanvas;

    public GWTCanvasBridge(final int width, final int height) {
	this.gwtCanvas = new Canvas(width, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addClickListener
     * (com.google.gwt.user.client.ui.ClickListener)
     */
    public void addClickListener(final ClickListener clickListener) {
	this.gwtCanvas.addClickListener(clickListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addMouseListener
     * (com.google.gwt.user.client.ui.MouseListener)
     */
    public void addMouseListener(final MouseListener mouseListener) {
	this.gwtCanvas.addMouseListener(mouseListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#beginPath()
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
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#closePath()
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
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fillRect
     * (int, int, int, int)
     */
    public void fillRect(final int x, final int y, final int w, final int h) {
	this.gwtCanvas.fillRect(x, y, w, h);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#getWidget()
     */
    public Widget getWidget() {
	return this.gwtCanvas;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#lineTo(int,
     * int)
     */
    public void lineTo(final int i, final int j) {
	this.gwtCanvas.lineTo(i, j);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#moveTo(int,
     * int)
     */
    public void moveTo(final int x, final int y) {
	this.gwtCanvas.moveTo(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#restoreContext
     * ()
     */
    public void restoreContext() {
	this.gwtCanvas.restore();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#saveContext
     * ()
     */
    public void saveContext() {
	this.gwtCanvas.save();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#
     * setBackgroundColor(com.google.gwt.widgetideas.graphics.client.Color)
     */
    public void setBackgroundColor(final Color color) {
	this.gwtCanvas.setBackgroundColor(color.toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setFillStyle
     * (com.google.gwt.widgetideas.graphics.client.Color)
     */
    public void setFillStyle(final Color fillColor) {
	this.gwtCanvas.setFillStyle(fillColor.toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setLineWidth
     * (int)
     */
    public void setLineWidth(final int strokeWidth) {
	this.gwtCanvas.setLineWidth(strokeWidth);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setStrokeStyle
     * (com.google.gwt.widgetideas.graphics.client.Color)
     */
    public void setStrokeStyle(final Color strokeColor) {
	this.gwtCanvas.setStrokeStyle(strokeColor.toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#stroke()
     */
    public void stroke() {
	this.gwtCanvas.stroke();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#strokeRect
     * (int, int, int, int)
     */
    public void strokeRect(final int x, final int y, final int w, final int h) {
	this.gwtCanvas.strokeRect(x, y, w, h);
    }

}
