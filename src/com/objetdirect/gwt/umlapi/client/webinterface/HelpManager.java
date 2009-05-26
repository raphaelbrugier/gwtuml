/**
 * 
 */
/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.gwt.mosaic.ui.client.MessageBox;

/**
 * This class supply an easy way to bring help about the drawer
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class HelpManager {

    private static LinkedHashMap<String, String> hotkeysHelp = new LinkedHashMap<String, String>();

    /**
     * Add a line to inform the user about a hot key
     * @param key The hot key string
     * @param description The text that explains what this key does
     */
    public static void addHotkeyHelp(final String key, final String description) {
	hotkeysHelp.put(key, description);
    }

    /**
     * A call to ths method will bring a popup with the help added previously.
     */
    public static void bringHelpPopup() {
	final StringBuilder htmlContent = new StringBuilder();
	htmlContent.append("<table style='width: 100%'>");
	for (final Entry<String, String> entry : hotkeysHelp.entrySet()) {
	    htmlContent.append("<tr><td style='text-align: right'><b>["
		    + entry.getKey() + "]</b></td><td> - </td><td>"
		    + entry.getValue() + "</td></tr>");
	}
	htmlContent.append("</table>");
	MessageBox.info("Help", htmlContent.toString());
    }
}
