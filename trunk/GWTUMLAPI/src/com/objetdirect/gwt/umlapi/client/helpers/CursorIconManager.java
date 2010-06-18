/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright Â© 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.helpers;

import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.resources.Resources;
import com.objetdirect.gwt.umlapi.client.resources.styles.IconStyles;

/**
 * This class is an helper to change the current mouse cursor
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @contributor Raphaël Brugier(raphael dot brugier at gmail dot com)
 */
public class CursorIconManager {

	/**
	 * This enumeration lists all the cursor available for the canvas.
	 * 
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	public enum PointerStyle {

		/**
		 * Auto cursor
		 */
		AUTO(iconStyles().autoCursor()),
		/**
		 * Crosshair cursor
		 */
		CROSSHAIR(iconStyles().crossHairCursor()),
		/**
		 * Move cursor
		 */
		MOVE(iconStyles().moveCursor());

		private final String cssName;

		private PointerStyle(final String cssName) {
			this.cssName = cssName;
		}
	}

	/**
	 * Convenience method to get the styles class string.
	 * 
	 * @return
	 */
	private static IconStyles iconStyles() {
		return Resources.INSTANCE.iconStyles();
	}

	private String currentStyleApplied;

	public CursorIconManager() {
		currentStyleApplied = null;
	}

	public void changeCursorIcon(PointerStyle cursor, Widget widget) {
		if (currentStyleApplied != null) {
			widget.removeStyleName(currentStyleApplied);
		}

		widget.addStyleName(cursor.cssName);
		currentStyleApplied = cursor.cssName;
	}
}
