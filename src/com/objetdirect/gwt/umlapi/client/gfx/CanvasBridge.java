/**
 * 
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
interface CanvasBridge {

    /**
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     * 
     */
    public enum AbstractCanvasType {
	/**
		 * 
		 */
	GWTCANVAS, /**
		 * 
		 */
	INCUBATORCANVAS;
    }

    /**
     * @param clickHandler
     */
    public void addClickListener(ClickListener clickHandler);

    /**
     * @param mouseListener
     */
    public void addMouseListener(MouseListener mouseListener);

    /**
	 * 
	 */
    public void beginPath();

    /**
	 * 
	 */
    public void clear();

    /**
	 * 
	 */
    public void closePath();

    /**
	 * 
	 */
    public void fill();

    /**
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void fillRect(int x, int y, int w, int h);

    /**
     * @return the widget of the current canvas
     */
    public Widget getWidget();

    /**
     * @param i
     * @param j
     */
    public void lineTo(int i, int j);

    /**
     * @param x
     * @param y
     */
    public void moveTo(int x, int y);

    /**
	 * 
	 */
    public void restoreContext();

    /**
	 * 
	 */
    public void saveContext();

    /**
     * @param color
     */
    public void setBackgroundColor(Color color);

    /**
     * @param fillColor
     */
    public void setFillStyle(Color fillColor);

    /**
     * @param strokeWidth
     */
    public void setLineWidth(int strokeWidth);

    /**
     * @param strokeColor
     */
    public void setStrokeStyle(Color strokeColor);

    /**
	 * 
	 */
    public void stroke();

    /**
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void strokeRect(int x, int y, int w, int h);
}
