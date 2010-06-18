/*
 * This file is part of the GWTUML project and was written by Mounier Florian <raphael-dot-brugier.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation;

import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;

/**
 * This enumeration list all the style that a link could have
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public enum LinkStyle {

	/**
	 * Dash style : - - - - -
	 */
	DASHED("Dashed", GfxStyle.DASH),
	/**
	 * Long dash style : -- -- -- -- --
	 */
	LONG_DASHED("LongDashed", GfxStyle.LONGDASH),
	/**
	 * Dash dot style : -.-.-.-.-.-
	 */
	DASHED_DOTTED("DashedDotted", GfxStyle.DASHDOT),
	/**
	 * Solid style : ------------
	 */
	SOLID("Solid", GfxStyle.NONE);

	/**
	 * Static getter of a {@link LinkStyle} by its name
	 * 
	 * @param linkStyleName
	 *            The name of the {@link LinkStyle} to retrieve
	 * @return The {@link LinkStyle} that has linkStyleName for name or null if not found
	 */
	public static LinkStyle getLinkStyleFromName(final String linkStyleName) {
		for (final LinkStyle linkStyle : LinkStyle.values()) {
			if (linkStyle.getName().equals(linkStyleName)) {
				return linkStyle;
			}
		}
		return null;
	}

	private GfxStyle style;

	private String name;

	/**
	 * Default constructor ONLY for gwt-rpc serialization
	 */
	private LinkStyle() {
	}

	private LinkStyle(final String name, final GfxStyle style) {
		this.name = name;
		this.style = style;
	}

	/**
	 * Getter for the {@link GfxStyle}
	 * 
	 * @return the {@link GfxStyle} to set to a line
	 */
	public GfxStyle getGfxStyle() {
		return style;
	}

	/**
	 * Getter for the {@link LinkStyle} name
	 * 
	 * @return the {@link LinkStyle} name
	 */
	public String getName() {
		return name;
	}
}
