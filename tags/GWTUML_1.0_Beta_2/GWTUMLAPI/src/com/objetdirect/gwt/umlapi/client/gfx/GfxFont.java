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
 * Represent a Font for text object :
 * 
 */
public class GfxFont {
	/**
	 * use for variant, style, weight attributes
	 */
	public static final String	_NORMAL			= "normal";
	/** use for the weight attribute */
	public static final String	BOLD			= "bold";

	/** use for the weight attribute */
	public static final String	BOLDER			= "bolder";

	/**
	 * the default font used (monospace 10)
	 * 
	 */
	public static final GfxFont	DEFAULT_FONT	= new GfxFont("monospace", 10, GfxFont._NORMAL, GfxFont._NORMAL, GfxFont._NORMAL);
	/** use for style */
	public static final String	ITALIC			= "italic";

	/** use for the weight attribute */
	public static final String	LIGHTER			= "lighter";
	/** use for style */
	public static final String	OBLIQUE			= "oblique";

	/**
	 * use for variant attribute : In a small-caps font the lower case letters look similar to the uppercase ones, but in a smaller size and with slightly
	 * different proportions.
	 **/
	public static final String	SMALL_CAPS		= "small-caps";
	/**
	 * the family of the font
	 * 
	 */
	private String				family;
	/**
	 * the size of the font
	 * 
	 */
	private int					size;
	/**
	 * the style of the font
	 * 
	 */
	private String				style;
	/**
	 * the variant of the font
	 * 
	 */
	private String				variant;
	/**
	 * the weight of the font
	 * 
	 */
	private String				weight;

	/**
	 * Creates a Font object for text object
	 * 
	 * @param family
	 *            the family to use
	 * @param size
	 *            the size of the font
	 * @param style
	 *            the style to use : <code>NORMAL,OBLIQUE,ITALIC  </code>
	 * @param variant
	 *            the varient to use : <code>NORMAL,SMALL_CAPS</code>
	 * @param weight
	 *            the wieght to use : <code>	NORMAL,BOLD,BOLDER,LIGHTER ,100,200,300,400,500,600,700,800,900</code>
	 */
	public GfxFont(final String family, final int size, final String style, final String variant, final String weight) {
		super();
		this.family = family;
		this.size = size;
		this.style = style;
		this.variant = variant;
		this.weight = weight;
	}

	/**
	 * Returns the family of the font
	 * 
	 * @return the family of the font
	 * 
	 */
	public String getFamily() {
		return this.family;
	}

	/**
	 * Returns the size of the font
	 * 
	 * @return the size of the font
	 * 
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Returns the style used by the font
	 * 
	 * @return style used by the font
	 * 
	 */
	public String getStyle() {
		return this.style;
	}

	/**
	 * Returns the variant of the font
	 * 
	 * @return the variant of the font
	 * 
	 */
	public String getVariant() {
		return this.variant;
	}

	/**
	 * Returns the weight of the font
	 * 
	 * @return the weight of the font
	 * 
	 */
	public String getWeight() {
		return this.weight;
	}

	/**
	 * Sets the family of the font
	 * 
	 * @param family
	 *            a family
	 * 
	 */
	public void setFamily(final String family) {
		this.family = family;
	}

	/**
	 * Sets the size of the font in pt
	 * 
	 * @param size
	 *            size of the font
	 * 
	 */
	public void setSize(final int size) {
		this.size = size;
	}

	/**
	 * Sets the style of the font
	 * 
	 * @param style
	 *            style of the font
	 * 
	 */
	public void setStyle(final String style) {
		this.style = style;
	}

	/**
	 * Sets the variant of the font
	 * 
	 * @param variant
	 *            variant of the font
	 * 
	 */
	public void setVariant(final String variant) {
		this.variant = variant;
	}

	/**
	 * Sets the weight of the font
	 * 
	 * @param weight
	 *            weight of the font
	 * 
	 */
	public void setWeight(final String weight) {
		this.weight = weight;
	}
}
