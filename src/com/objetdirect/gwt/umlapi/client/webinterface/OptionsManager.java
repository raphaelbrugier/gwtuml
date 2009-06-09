/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

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
 * Shadowed -> 1
 * AngularLinks -> 0
 * Advanced -> 0
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class OptionsManager {

    private static HashMap<String, Integer> optionsList = new HashMap<String, Integer>();
    private static HashMap<String, Integer> defaultOptionsList;

    /**
     * This method must be called at the start of the app because it initializes all the global properties
     * 
     */
    public static void initialize() {
	optionsList.put("ArrowLength", 25);
	optionsList.put("ArrowWidth", 15);
	optionsList.put("CrossLength", 5);
	optionsList.put("CrossWidth", 10);
	optionsList.put("DiamondLength", 20);
	optionsList.put("DiamondWidth", 15);
	optionsList.put("SolidArrowLength", 30);
	optionsList.put("SolidArrowWidth", 20);
	optionsList.put("CircleRadius", 5);


	optionsList.put("RectangleTopPadding", 4);
	optionsList.put("RectangleBottomPadding", 4);
	optionsList.put("RectangleLeftPadding", 2);
	optionsList.put("RectangleRightPadding", 2);

	optionsList.put("TextTopPadding", 1);
	optionsList.put("TextBottomPadding", 1);
	optionsList.put("TextLeftPadding", 1);
	optionsList.put("TextRightPadding", 1);

	optionsList.put("ReflexivePathXGap", 25);
	optionsList.put("ReflexivePathYGap", 50);

	optionsList.put("NoteCornerHeight", 15);
	optionsList.put("NoteCornerWidth", 15);

	optionsList.put("UnderlineShift", 4);

	optionsList.put("QualityLevel", 1);

	optionsList.put("FontSize", 10);
	optionsList.put("SmallFontSize", 9);

	optionsList.put("DiagramType", 2);
	optionsList.put("GraphicEngine", 0);	
	optionsList.put("GeometryStyle",  0);	
	optionsList.put("Theme",  0);
	optionsList.put("AutoResolution", 1);
	optionsList.put("Width",  800);
	optionsList.put("Height",  800);
	optionsList.put("Shadowed",  1);
	
	optionsList.put("DirectionPanelOpacity",  10);
	optionsList.put("DirectionPanelMaxOpacity",  75);
	
	optionsList.put("DirectionPanelSizes",  15);
	optionsList.put("AngularLinks",  0);
	optionsList.put("Advanced",  0);
	
	optionsList.put("LifeLineSpacing",  25);
	defaultOptionsList = new HashMap<String, Integer>(optionsList);
    }

    /**
     * Getter for the option optionName 
     * 
     * @param optionName The {@link String} name of the option
     * 
     * @return The {@link Integer} value of this option
     */
    public static Integer get(String optionName) {
	Integer value = optionsList.get(optionName);
	if(value == null) {
	    Log.error("Unknown option : " + optionName);
	    return 0;
	}
	return value;	
    }

    /**
     * Setter for the option optionName
     * 
     * @param optionName The {@link String} name of the option
     * @param optionValue The {@link Integer} new value of this option
     */
    public static void set(String optionName, Integer optionValue) {

	if(!optionsList.containsKey(optionName)) {
	    Log.error("Unknown option : " + optionName);
	} else {
	    optionsList.put(optionName, optionValue);
	}
    }

    static void setAll(HashMap<String, Integer> options) {
	for (Entry<String, Integer> option : options.entrySet()) {    
	    if(optionsList.containsKey(option.getKey())) {
		optionsList.put(option.getKey(), option.getValue());
	    }
	}
    }
    static void setAllFromURL(HashMap<String, String> options) {
	for (Entry<String, String> option : options.entrySet()) {    
	    if(optionsList.containsKey(option.getKey())) {

		try {
		    optionsList.put(option.getKey(), Integer.parseInt(option.getValue()));
		} catch (Exception ex) {
		    Log.warn("Unreadable argument : " + option.getKey());
		}

	    }
	}
    }
    /** 
     * Getter for the normal application font
     * 
     * @return a new font corresponding to the normal font 
     */
    public static GfxFont getFont() {
	return new GfxFont("monospace", optionsList.get("FontSize"), GfxFont._NORMAL,
		GfxFont._NORMAL, GfxFont._NORMAL);
    }
    /** 
     * Getter for the small application font&
     * 
     * @return a new font corresponding to the small font 
     */
    public static GfxFont getSmallFont() {
	return new GfxFont("monospace", optionsList.get("SmallFontSize"), GfxFont._NORMAL,
		GfxFont._NORMAL, GfxFont.LIGHTER);
    }

    /**
     * Export all options in a {@link String} which has the URI parameter format
     * 
     * @return The {@link String} containing the parameters in the format : parameterName=parameterValue 
     */
    public static String toURL() {
	StringBuilder urlParameters = new StringBuilder();
	for (Entry<String, Integer> option : optionsList.entrySet()) {
	    if(defaultOptionsList.get(option.getKey()) != option. getValue() ) {
		urlParameters.append(option.getKey());
		urlParameters.append("=");
		urlParameters.append(option.getValue());
		urlParameters.append("&");
	    }
	}
	return urlParameters.toString();
    }

}
