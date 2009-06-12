package com.google.gwt.user.client.ui;

/**
 * 
 * @author georgopoulos.georgios(at)gmail.com
 */
public abstract class LayoutManagerHelper {

	/**
	 * Gets the panel-defined layout data associated with this widget.
	 * 
	 * @param widget
	 *            the widget
	 * @return the widget's layout data
	 */
	protected static Object _getLayoutData(final Widget widget) {
		return widget.getLayoutData();
	}

	/**
	 * Sets the panel-defined layout data associated with this widget. Only the panel that currently contains a widget should ever set this value. It serves as
	 * a place to store layout bookkeeping data associated with a widget.
	 * 
	 * @param widget
	 *            the widget
	 * @param layoutData
	 *            the widget's layout data
	 */
	protected static void _setLayoutData(final Widget widget, final Object layoutData) {
		widget.setLayoutData(layoutData);
	}

}
