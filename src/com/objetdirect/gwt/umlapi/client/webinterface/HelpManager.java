/**
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
	//MessageBox.info("Help", htmlContent.toString());
    }
}
