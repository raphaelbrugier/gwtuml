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

/**
 * This enumeration list all the adornments that a relation could have
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public enum LinkAdornment {

	/**
	 * No adornment -
	 */
	NONE("None", Shape.UNSHAPED, false, true),
	/**
	 * A cross: -x
	 */
	WIRE_CROSS("WireCross", Shape.CROSS, false, true),

	/**
	 * A wire arrow : -&gt;
	 */
	WIRE_ARROW("WireArrow", Shape.ARROW, false, true),

	/**
	 * A simple filled arrow : -|&gt;
	 */
	SOLID_ARROW("SolidArrow", Shape.ARROW, true, false),

	/**
	 * A filled diamond : -&lt;&gt;
	 */
	SOLID_DIAMOND("SolidDiamond", Shape.DIAMOND, true, false),
	/**
	 * A filled circle : -o;
	 */
	SOLID_CIRCLE("SolidCircle", Shape.CIRCLE, true, false),
	/**
	 * A simple filled arrow : -|@&gt;
	 */
	INVERTED_SOLID_ARROW("InvertedSolidArrow", Shape.ARROW, true, false, true),
	/**
	 * A filled diamond with foreground color : -&lt;@&gt;
	 */
	INVERTED_SOLID_DIAMOND("InvertedSolidDiamond", Shape.DIAMOND, true, false, true),
	/**
	 * A filled circle with foreground color : -@;
	 */
	INVERTED_SOLID_CIRCLE("InvertedSolidCircle", Shape.CIRCLE, true, false, true);

	/**
	 * This sub enumeration specify the global shape of the adornment
	 * 
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 * 
	 */
	public enum Shape {
		/**
		 * Arrow type
		 */
		ARROW("<"),
		/**
		 * Cross type
		 */
		CROSS("x"),
		/**
		 * Diamond type
		 */
		DIAMOND("<>"),
		/**
		 * Circle type
		 */
		CIRCLE("o"),
		/**
		 * No shape
		 */
		UNSHAPED("");

		private String idiom;

		private Shape() {
		}

		private Shape(final String idiom) {
			this.idiom = idiom;
		}

		/**
		 * Getter for the idiom
		 * 
		 * @return a string that represent the shape textually : for arrow &lt;
		 */
		public String getIdiom() {
			return this.getIdiom(false);
		}

		/**
		 * Specific Getter for the idiom which change shape orientation between left &lt; and right &gt;
		 * 
		 * @param isRight
		 *            : if the shape is oriented to the rightDIAMOND
		 * @return a string that represent the shape textually in the right orientation : for right arrow &gt;
		 */
		public String getIdiom(final boolean isRight) {
			if (idiom.equals("<") && isRight) {
				return ">";
			}
			return idiom;
		}
	}

	/**
	 * Static getter of a {@link LinkAdornment} by its name
	 * 
	 * @param linkAdornmentName
	 *            The name of the {@link LinkAdornment} to retrieve
	 * @return The {@link LinkAdornment} that has linkAdornmentName for name or null if not found
	 */
	public static LinkAdornment getLinkAdornmentFromName(final String linkAdornmentName) {
		for (final LinkAdornment linkAdornment : LinkAdornment.values()) {
			if (linkAdornment.getName().equals(linkAdornmentName)) {
				return linkAdornment;
			}
		}
		return null;
	}

	private boolean isInverted;
	private boolean isSolid;
	private Shape shape;
	private boolean isNavigabilityAdornment;
	private String name;

	/**
	 * Default constructor ONLY for gwt-rpc serialization
	 */
	private LinkAdornment() {
	}

	private LinkAdornment(final String name, final Shape shape, final boolean isSolid, final boolean isNavigabilityAdronment) {
		this(name, shape, isSolid, isNavigabilityAdronment, false);
	}

	private LinkAdornment(final String name, final Shape shape, final boolean isSolid, final boolean isNavigabilityAdornment, final boolean isInverted) {
		this.name = name;
		this.shape = shape;
		this.isSolid = isSolid;
		this.isNavigabilityAdornment = isNavigabilityAdornment;
		this.isInverted = isInverted;
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for the shape
	 * 
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * Determine if the shape is inverted or not (ie : the fill color is the foreground color)
	 * 
	 * @return <ul>
	 *         <li><b>True</b> if it is inverted</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isInverted() {
		return isInverted;
	}

	/**
	 * Determine if the adornment is a navigability adornment : &gt; x or none
	 * 
	 * @return <ul>
	 *         <li><b>True</b> if the adornment is a navigability one</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isNavigabilityAdornment() {
		return isNavigabilityAdornment;
	}

	/**
	 * Determine if the shape is filled or not
	 * 
	 * @return <ul>
	 *         <li><b>True</b> if it is filled</li>
	 *         <li><b>False</b> otherwise</li>
	 *         </ul>
	 */
	public boolean isSolid() {
		return isSolid;
	}

}
