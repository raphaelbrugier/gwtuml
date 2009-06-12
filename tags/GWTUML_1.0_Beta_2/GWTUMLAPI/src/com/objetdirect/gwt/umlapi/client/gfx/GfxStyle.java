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
public final class GfxStyle {
	/**
	 * Style for the stroke a suite of dash
	 */
	public static final GfxStyle	DASH			= new GfxStyle("Dash");
	/**
	 * Style for the stroke a suite of dash follow by a dot
	 */
	public static final GfxStyle	DASHDOT			= new GfxStyle("DashDot");
	/**
	 * Style for the stroke, a suite of dots
	 */
	public static final GfxStyle	DOT				= new GfxStyle("Dot");
	/**
	 * Style for the stroke, a suite of long dash
	 */
	public static final GfxStyle	LONGDASH		= new GfxStyle("LongDash");
	/**
	 * Style for the stroke a suite of a long dash follow by a dot
	 */
	public static final GfxStyle	LONGDASHDOT		= new GfxStyle("LongDashDot");
	/**
	 * Style for the stroke a suite of a long dash follow by 2 dots
	 */
	public static final GfxStyle	LONGDASHDOTDOT	= new GfxStyle("LongDashDotDot");
	/**
	 * Style for the stroke
	 */
	public static final GfxStyle	NONE			= new GfxStyle("none");
	/**
	 * Style for the stroke, a suite a shot dashes
	 */
	public static final GfxStyle	SHORTDASH		= new GfxStyle("ShortDash");
	/**
	 * Style for the stroke a suite of a short dash follow by a dot
	 */
	public static final GfxStyle	SHORTDASHDOT	= new GfxStyle("ShortDashDot");
	/**
	 * Style for the stroke a suite of a short dash follow by 2 dots
	 */
	public static final GfxStyle	SHORTDASHDOTDOT	= new GfxStyle("ShortDashDotDot");
	/**
	 * Style for the stroke a suite of a short dots
	 */
	public static final GfxStyle	SHORTDOT		= new GfxStyle("ShortDot");
	/**
	 * Style for the stroke, a solid stroke
	 */
	public static final GfxStyle	SOLID			= new GfxStyle("Solid");
	private final String			style;

	/**
	 * Constructor of the graphic style
	 * 
	 * @param style
	 *            The text style
	 */
	public GfxStyle(final String style) {
		super();
		this.style = style;
	}

	/**
	 * Getter for the style text
	 * 
	 * @return The style text
	 */
	public String getStyleString() {
		return this.style;
	}
}
