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

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public enum GfxStyle {
	/**
	 * Style for the stroke a suite of dash
	 */
	DASH("Dash"),

	/**
	 * Style for the stroke a suite of dash follow by a dot
	 */
	DASHDOT("DashDot"),

	/**
	 * Style for the stroke, a suite of dots
	 */
	DOT("Dot"),

	/**
	 * Style for the stroke, a suite of long dash
	 */
	LONGDASH("LongDash"),

	/**
	 * Style for the stroke a suite of a long dash follow by a dot
	 */
	LONGDASHDOT("LongDashDot"),

	/**
	 * Style for the stroke a suite of a long dash follow by 2 dots
	 */
	LONGDASHDOTDOT("LongDashDotDot"),

	/**
	 * Style for the stroke
	 */
	NONE("none"),

	/**
	 * Style for the stroke, a suite a shot dashes
	 */
	SHORTDASH("ShortDash"),

	/**
	 * Style for the stroke a suite of a short dash follow by a dot
	 */
	SHORTDASHDOT("ShortDashDot"),

	/**
	 * Style for the stroke a suite of a short dash follow by 2 dots
	 */
	SHORTDASHDOTDOT("ShortDashDotDot"),

	/**
	 * Style for the stroke a suite of a short dots
	 */
	SHORTDOT("ShortDot"),

	/**
	 * Style for the stroke, a solid stroke
	 */
	SOLID("Solid");

	private String style;

	/**
	 * Default constructor, ONLY for gwt-rpc serialization.
	 */
	private GfxStyle() {
	}

	/**
	 * Constructor of the graphic style
	 * 
	 * @param style
	 *            The text style
	 */
	private GfxStyle(final String style) {
		this.style = style;
	}

	/**
	 * Getter for the style text
	 * 
	 * @return The style text
	 */
	public String getStyleString() {
		return style;
	}
}
