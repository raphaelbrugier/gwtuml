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

import java.util.Arrays;
import java.util.List;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class GfxColor {
	/**
	 * The aqua color in RGB
	 */
	static public final GfxColor	AQUA	= new GfxColor(0, 255, 255, 255);
	/**
	 * The balck color in RGB
	 */
	static public final GfxColor	BLACK	= new GfxColor(0, 0, 0, 255);
	/**
	 * The blue color in RGB
	 */
	static public final GfxColor	BLUE	= new GfxColor(0, 0, 255, 255);
	/**
	 * The fuchsia color in RGB
	 */
	static public final GfxColor	FUCHSIA	= new GfxColor(255, 0, 255, 255);
	/**
	 * The gray color in RGB
	 */
	static public final GfxColor	GRAY	= new GfxColor(128, 128, 128, 255);
	/**
	 * The green color in RGB
	 */
	static public final GfxColor	GREEN	= new GfxColor(0, 128, 0, 255);
	/**
	 * The lime color in RGB
	 */
	static public final GfxColor	LIME	= new GfxColor(0, 255, 0, 255);
	/**
	 * The maroon color in RGB
	 */
	static public final GfxColor	MAROON	= new GfxColor(128, 0, 0, 255);
	/**
	 * The navy color in RGB
	 */
	static public final GfxColor	NAVY	= new GfxColor(0, 0, 128, 255);
	/**
	 * The olive color in RGB
	 */
	static public final GfxColor	OLIVE	= new GfxColor(128, 128, 0, 255);
	/**
	 * The purpble color in RGB
	 */
	static public final GfxColor	PURPLE	= new GfxColor(128, 0, 128, 255);
	/**
	 * The red color in RGB
	 */
	static public final GfxColor	RED		= new GfxColor(255, 0, 0, 255);
	/**
	 * The silver color inRGB
	 */
	static public final GfxColor	SILVER	= new GfxColor(192, 192, 192, 255);
	/**
	 * The teal color in RGB
	 */
	static public final GfxColor	TEAL	= new GfxColor(0, 128, 128, 255);
	/**
	 * The white color in RGB
	 */
	static public final GfxColor	WHITE	= new GfxColor(255, 255, 255, 255);
	/**
	 * The yellow color in RGB
	 */
	static public final GfxColor	YELLOW	= new GfxColor(255, 255, 0, 255);
	int								a;
	int								b;
	int								g;
	int								r;

	/**
	 * Constructor of a color
	 * 
	 * @param r
	 *            Red value (0-255)
	 * @param g
	 *            Green value (0-255)
	 * @param b
	 *            Blue value (0-255)
	 */
	public GfxColor(final int r, final int g, final int b) {
		this(r, g, b, 255);
	}

	/**
	 * Constructor of a color
	 * 
	 * @param r
	 *            Red value (0-255)
	 * @param g
	 *            Green value (0-255)
	 * @param b
	 *            Blue value (0-255)
	 * @param a
	 *            Alpha (transparency) value (0-255)
	 */
	public GfxColor(final int r, final int g, final int b, final int a) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Constructor of a color
	 * 
	 * @param hexString
	 *            A standard hex {@link String} for a color (supports : aaa, #bbb, abcd, #abcd, 123456, #123456, 12345678, #12345678)
	 */
	@SuppressWarnings("fallthrough")
	public GfxColor(final String hexString) {
		super();
		String hexColor;
		if (hexString.startsWith("#")) {
			hexColor = hexString.substring(1);
		} else {
			hexColor = hexString;
		}
		this.a = 255;
		switch (hexColor.length()) {
			case 4:
				this.a = Integer.decode("#" + hexColor.substring(3, 4) + hexColor.substring(3, 4));
			case 3:
				this.r = Integer.decode("#" + hexColor.substring(0, 1) + hexColor.substring(0, 1));
				this.g = Integer.decode("#" + hexColor.substring(1, 2) + hexColor.substring(1, 2));
				this.b = Integer.decode("#" + hexColor.substring(2, 3) + hexColor.substring(2, 3));
				break;
			case 8:
				this.a = Integer.decode("#" + hexColor.substring(6, 8));
			case 6:
				this.r = Integer.decode("#" + hexColor.substring(0, 2));
				this.g = Integer.decode("#" + hexColor.substring(2, 4));
				this.b = Integer.decode("#" + hexColor.substring(4, 6));
		}
	}

	/**
	 * Getter for the Alpha value
	 * 
	 * @return The Alpha value
	 */
	public int getAlpha() {
		return this.a;
	}

	/**
	 * Getter for the Blue value
	 * 
	 * @return The Blue value
	 */
	public int getBlue() {
		return this.b;
	}

	/**
	 * Getter for the Green value
	 * 
	 * @return The Green value
	 */
	public int getGreen() {
		return this.g;
	}

	/**
	 * Getter for the Red value
	 * 
	 * @return The Red value
	 */
	public int getRed() {
		return this.r;
	}

	/**
	 * Setter for the Alpha value
	 * 
	 * @param a
	 *            The Alpha value
	 */
	public void setAlpha(final int a) {
		this.a = a;
	}

	/**
	 * Setter for the Blue value
	 * 
	 * @param b
	 *            The Blue value
	 */
	public void setBlue(final int b) {
		this.b = b;
	}

	/**
	 * Setter for the Green value
	 * 
	 * @param g
	 *            The Green value
	 */
	public void setGreen(final int g) {
		this.g = g;
	}

	/**
	 * Setter for the Red value
	 * 
	 * @param r
	 *            The Red value
	 */
	public void setRed(final int r) {
		this.r = r;
	}

	/**
	 * Convert r g b values in h s v values and returns it as a list of {@link Integer}
	 * 
	 * @return The hsv Integers {@link List}
	 */
	public List<Integer> toHSV() {
		double h = 0;
		double s = 0;
		double v = 0;
		final double red = (this.r / 255); // RGB from 0 to 255
		final double green = (this.g / 255);
		final double blue = (this.b / 255);

		final double min = Math.min(Math.min(red, green), blue); // Min. value of RGB
		final double max = Math.max(Math.max(red, green), blue); // Max. value of RGB
		final double delta = max - min; // Delta RGB value

		v = max;

		if (delta == 0) // This is a gray, no chroma...
		{
			h = 0; // HSV results from 0 to 1
			s = 0;
		} else // Chromatic data...
		{
			s = delta / max;

			final double deltaRed = (((max - red) / 6) + (delta / 2)) / delta;
			final double deltaGreen = (((max - green) / 6) + (delta / 2)) / delta;
			final double deltaBlue = (((max - blue) / 6) + (delta / 2)) / delta;

			if (red == max) {
				h = deltaBlue - deltaGreen;
			} else if (green == max) {
				h = (1 / 3) + deltaRed - deltaBlue;
			} else if (blue == max) {
				h = (2 / 3) + deltaGreen - deltaRed;
			}

			if (h < 0) {
				h += 1;
			}
			if (h > 1) {
				h -= 1;
			}
		}
		return Arrays.asList((int) Math.round(h * 360), (int) Math.round(s * 100), (int) Math.round(v * 100));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "#" + (this.r < 15 ? "0" : "") + Integer.toHexString(this.r) + (this.g < 15 ? "0" : "") + Integer.toHexString(this.g) + (this.b < 15 ? "0" : "")
				+ Integer.toHexString(this.b);
	}
}
