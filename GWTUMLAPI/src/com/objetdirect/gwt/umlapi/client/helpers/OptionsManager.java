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
package com.objetdirect.gwt.umlapi.client.helpers;

import java.util.HashMap;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;

/**
 * This class allows to set and access configuration values
 * 
 * Default ones are :
 * 
 * ArrowLength -> 25 <br />
 * ArrowWidth -> 15 <br />
 * CrossLength -> 5 <br />
 * CrossWidth -> 10 <br />
 * DiamondLength -> 20 <br />
 * DiamondWidth -> 15 <br />
 * SolidArrowLength -> 30 <br />
 * SolidArrowWidth -> 20 <br />
 * CircleRadius -> 5 <br />
 * RectangleTopPadding -> 4 <br />
 * RectangleBottomPadding -> 4 <br />
 * RectangleLeftPadding -> 2 <br />
 * RectangleRightPadding -> 2 <br />
 * TextTopPadding -> 1 <br />
 * TextBottomPadding -> 1 <br />
 * TextLeftPadding -> 1 <br />
 * TextRightPadding -> 1 <br />
 * ReflexivePathXGap -> 25 <br />
 * ReflexivePathYGap -> 50 <br />
 * NoteCornerHeight -> 15 <br />
 * NoteCornerWidth -> 15 <br />
 * UnderlineShift -> 4 <br />
 * QualityLevel -> 1 <br />
 * FontSize -> 10 <br />
 * SmallFontSize -> 9 <br />
 * DiagramType -> 2 <br />
 * GraphicEngine -> 0 <br />
 * GeometryStyle -> 0 <br />
 * Theme -> 0 <br />
 * AutoResolution -> 0 <br />
 * Width -> 800 <br />
 * Height -> 800 <br />
 * Shadowed -> 1 AngularLinks -> 0 Advanced -> 0
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class OptionsManager {

	private static HashMap<String, Integer>	optionsList;
	private static HashMap<String, Integer>	defaultOptionsList;

	/**
	 * Getter for the option optionName
	 * 
	 * @param optionName
	 *            The {@link String} name of the option
	 * 
	 * @return The {@link Integer} value of this option
	 */
	public static Integer get(final String optionName) {
		maybeForceInit();
		final Integer value = OptionsManager.optionsList.get(optionName);
		if (value == null) {
			Log.error("Unknown option : " + optionName);
			return 0;
		}
		return value;
	}

	/**
	 * Getter for the normal application font
	 * 
	 * @return a new font corresponding to the normal font
	 */
	public static GfxFont getFont() {
		maybeForceInit();
		return new GfxFont("monospace", OptionsManager.optionsList.get("FontSize"), GfxFont._NORMAL, GfxFont._NORMAL, GfxFont._NORMAL);
	}

	/**
	 * Getter for the small application font&
	 * 
	 * @return a new font corresponding to the small font
	 */
	public static GfxFont getSmallFont() {
		maybeForceInit();
		return new GfxFont("monospace", OptionsManager.optionsList.get("SmallFontSize"), GfxFont._NORMAL, GfxFont._NORMAL, GfxFont.LIGHTER);
	}

	/**
	 * This method must be called at the start of the app because it initializes all the global properties
	 * 
	 */
	public static void initialize() {
		optionsList	= new HashMap<String, Integer>();
		OptionsManager.optionsList.put("ArrowLength", 25);
		OptionsManager.optionsList.put("ArrowWidth", 15);
		OptionsManager.optionsList.put("CrossLength", 5);
		OptionsManager.optionsList.put("CrossWidth", 10);
		OptionsManager.optionsList.put("DiamondLength", 20);
		OptionsManager.optionsList.put("DiamondWidth", 15);
		OptionsManager.optionsList.put("SolidArrowLength", 30);
		OptionsManager.optionsList.put("SolidArrowWidth", 20);
		OptionsManager.optionsList.put("CircleRadius", 5);

		OptionsManager.optionsList.put("RectangleTopPadding", 4);
		OptionsManager.optionsList.put("RectangleBottomPadding", 4);
		OptionsManager.optionsList.put("RectangleLeftPadding", 2);
		OptionsManager.optionsList.put("RectangleRightPadding", 2);

		OptionsManager.optionsList.put("TextTopPadding", 1);
		OptionsManager.optionsList.put("TextBottomPadding", 1);
		OptionsManager.optionsList.put("TextLeftPadding", 1);
		OptionsManager.optionsList.put("TextRightPadding", 1);

		OptionsManager.optionsList.put("ReflexivePathXGap", 25);
		OptionsManager.optionsList.put("ReflexivePathYGap", 50);

		OptionsManager.optionsList.put("NoteCornerHeight", 15);
		OptionsManager.optionsList.put("NoteCornerWidth", 15);

		OptionsManager.optionsList.put("UnderlineShift", 4);

		OptionsManager.optionsList.put("QualityLevel", 1);

		OptionsManager.optionsList.put("FontSize", 10);
		OptionsManager.optionsList.put("SmallFontSize", 9);

		OptionsManager.optionsList.put("DiagramType", 2);
		OptionsManager.optionsList.put("GraphicEngine", 0);
		OptionsManager.optionsList.put("GeometryStyle", 0);
		OptionsManager.optionsList.put("Theme", 0);
		OptionsManager.optionsList.put("AutoResolution", 1);
		OptionsManager.optionsList.put("Width", 800);
		OptionsManager.optionsList.put("Height", 800);
		OptionsManager.optionsList.put("Shadowed", 1);

		OptionsManager.optionsList.put("DirectionPanelOpacity", 10);
		OptionsManager.optionsList.put("DirectionPanelMaxOpacity", 75);

		OptionsManager.optionsList.put("DirectionPanelSizes", 15);
		OptionsManager.optionsList.put("AngularLinks", 0);
		OptionsManager.optionsList.put("Advanced", 0);

		OptionsManager.optionsList.put("LifeLineSpacing", 25);
		OptionsManager.defaultOptionsList = new HashMap<String, Integer>(OptionsManager.optionsList);
	}

	/**
	 * Setter for the option optionName
	 * 
	 * @param optionName
	 *            The {@link String} name of the option
	 * @param optionValue
	 *            The {@link Integer} new value of this option
	 */
	public static void set(final String optionName, final Integer optionValue) {

		if (!OptionsManager.optionsList.containsKey(optionName)) {
			Log.error("Unknown option : " + optionName);
		} else {
			OptionsManager.optionsList.put(optionName, optionValue);
		}
	}

	/**
	 * Set all options from url
	 * 
	 * @param options
	 *            The {@link HashMap} of options
	 */
	public static void setAllFromURL(final HashMap<String, String> options) {
		for (final Entry<String, String> option : options.entrySet()) {
			if (OptionsManager.optionsList.containsKey(option.getKey())) {

				try {
					OptionsManager.optionsList.put(option.getKey(), Integer.parseInt(option.getValue()));
				} catch (final Exception ex) {
					Log.warn("Unreadable argument : " + option.getKey());
				}

			}
		}
	}

	/**
	 * Export all options in a {@link String} which has the URI parameter format
	 * 
	 * @return The {@link String} containing the parameters in the format : parameterName=parameterValue
	 */
	public static String toURL() {
		final StringBuilder urlParameters = new StringBuilder();
		for (final Entry<String, Integer> option : OptionsManager.optionsList.entrySet()) {
			if (OptionsManager.defaultOptionsList.get(option.getKey()) != option.getValue()) {
				urlParameters.append(option.getKey());
				urlParameters.append("=");
				urlParameters.append(option.getValue());
				urlParameters.append("&");
			}
		}
		return urlParameters.toString();
	}

	static void setAll(final HashMap<String, Integer> options) {
		for (final Entry<String, Integer> option : options.entrySet()) {
			if (OptionsManager.optionsList.containsKey(option.getKey())) {
				OptionsManager.optionsList.put(option.getKey(), option.getValue());
			}
		}
	}
	
	private static void maybeForceInit() {
		if (optionsList == null) {
			initialize();
		}
	}
}
