/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.gfx.canvas;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;

/**
 * @author florian
 *
 */
public class IncubatorCanvasBridge implements CanvasBridge {

	private GWTCanvasWithListeners gwtCanvasWithListeners;
	public IncubatorCanvasBridge(int width, int height) {
		gwtCanvasWithListeners = new GWTCanvasWithListeners(width, height);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addClickListener(com.google.gwt.user.client.ui.ClickListener)
	 */
	public void addClickListener(ClickListener clickListener) {
		gwtCanvasWithListeners.addClickListener(clickListener);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#addMouseListener(com.google.gwt.user.client.ui.MouseListener)
	 */
	public void addMouseListener(MouseListener mouseListener) {
		gwtCanvasWithListeners.addMouseListener(mouseListener);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#beginPath()
	 */
	public void beginPath() {
		gwtCanvasWithListeners.beginPath();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#clear()
	 */
	public void clear() {
		gwtCanvasWithListeners.clear();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#closePath()
	 */
	public void closePath() {
		gwtCanvasWithListeners.closePath();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fill()
	 */
	public void fill() {
		gwtCanvasWithListeners.fill();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#fillRect(int, int, int, int)
	 */
	public void fillRect(int x, int y, int w, int h) {
		gwtCanvasWithListeners.fillRect(x, y, w, h);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#getWidget()
	 */
	public Widget getWidget() {
		return gwtCanvasWithListeners;
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#lineTo(int, int)
	 */
	public void lineTo(int i, int j) {
		gwtCanvasWithListeners.lineTo(i, j);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#moveTo(int, int)
	 */
	public void moveTo(int x, int y) {
		gwtCanvasWithListeners.moveTo(x, y);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#restoreContext()
	 */
	public void restoreContext() {
		gwtCanvasWithListeners.restoreContext();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#saveContext()
	 */
	public void saveContext() {
		gwtCanvasWithListeners.saveContext();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setBackgroundColor(com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setBackgroundColor(Color color) {
		gwtCanvasWithListeners.setBackgroundColor(color);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setFillStyle(com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setFillStyle(Color fillColor) {
		gwtCanvasWithListeners.setFillStyle(fillColor);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setLineWidth(int)
	 */
	public void setLineWidth(int strokeWidth) {
		gwtCanvasWithListeners.setLineWidth(strokeWidth);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#setStrokeStyle(com.google.gwt.widgetideas.graphics.client.Color)
	 */
	public void setStrokeStyle(Color strokeColor) {
		gwtCanvasWithListeners.setStrokeStyle(strokeColor);
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#stroke()
	 */
	public void stroke() {
		gwtCanvasWithListeners.stroke();
	}

	/* (non-Javadoc)
	 * @see com.objetdirect.gwt.umlapi.client.gfx.incubator.CanvasBridge#strokeRect(int, int, int, int)
	 */
	public void strokeRect(int x, int y, int w, int h) {
		gwtCanvasWithListeners.strokeRect(x, y, w, h);
	}

}
