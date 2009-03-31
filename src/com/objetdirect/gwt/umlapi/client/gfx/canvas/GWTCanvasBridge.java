package com.objetdirect.gwt.umlapi.client.gfx.canvas;

import gwt.canvas.client.Canvas;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;

public class GWTCanvasBridge implements CanvasBridge {

	private Canvas gwtCanvas;
	
	public GWTCanvasBridge(int width, int height) {
		gwtCanvas = new Canvas(width, height);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addClickListener(com.google.gwt.user.client.ui.ClickListener)
	 */
	public void addClickListener(ClickListener clickListener) {
		gwtCanvas.addClickListener(clickListener);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addMouseListener(com.google.gwt.user.client.ui.MouseListener)
	 */
	public void addMouseListener(MouseListener mouseListener) {
		gwtCanvas.addMouseListener(mouseListener);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#beginPath()
	 */
	public void beginPath() {
		gwtCanvas.beginPath();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#clear()
	 */
	public void clear() {
		gwtCanvas.clear();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#closePath()
	 */
	public void closePath() {
		gwtCanvas.closePath();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fill()
	 */
	public void fill() {
		gwtCanvas.fill();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fillRect(int, int, int, int)
	 */
	public void fillRect(int x, int y, int w, int h) {
		gwtCanvas.fillRect(x, y, w, h);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#getWidget()
	 */
	public Widget getWidget() {
		return gwtCanvas;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#lineTo(int, int)
	 */
	public void lineTo(int i, int j) {
		gwtCanvas.lineTo(i, j);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#moveTo(int, int)
	 */
	public void moveTo(int x, int y) {
		gwtCanvas.moveTo(x, y);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#restoreContext()
	 */
	public void restoreContext() {
		gwtCanvas.restore();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#saveContext()
	 */
	public void saveContext() {
		gwtCanvas.save();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setBackgroundColor(com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setBackgroundColor(Color color) {
		gwtCanvas.setBackgroundColor(color.toString());
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setFillStyle(com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setFillStyle(Color fillColor) {
		gwtCanvas.setFillStyle(fillColor.toString());
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setLineWidth(int)
	 */
	public void setLineWidth(int strokeWidth) {
		gwtCanvas.setLineWidth(strokeWidth);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setStrokeStyle(com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setStrokeStyle(Color strokeColor) {
		gwtCanvas.setStrokeStyle(strokeColor.toString());
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#stroke()
	 */
	public void stroke() {
		gwtCanvas.stroke();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#strokeRect(int, int, int, int)
	 */
	public void strokeRect(int x, int y, int w, int h) {
		gwtCanvas.strokeRect(x, y, w, h);
	}

}
