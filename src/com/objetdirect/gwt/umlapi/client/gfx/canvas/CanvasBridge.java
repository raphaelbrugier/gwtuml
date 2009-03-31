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
public interface CanvasBridge {

	public enum AbstractCanvasType {
		GWTCANVAS, INCUBATORCANVAS;
	}
	
	public void saveContext();
	public void setFillStyle(Color fillColor);
	public void setStrokeStyle(Color strokeColor);
	public void setLineWidth(int strokeWidth);
	public void beginPath();
	public void moveTo(int x, int y);
	public void lineTo(int i, int j);
	public void closePath();
	public void stroke();
	public void restoreContext();
	public void addMouseListener(MouseListener mouseListener);
	public void addClickListener(ClickListener clickHandler);
	public void setBackgroundColor(Color color);
	public void clear();
	public void fill();
	public void fillRect(int x, int y, int w, int h);
	public void strokeRect(int x, int y, int w, int h);
	public Widget getWidget();
}
