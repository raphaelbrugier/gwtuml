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

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * This class supply an easy way to bring help about the drawer
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class HelpManager {

	private static LinkedHashMap<String, String>	hotkeysHelp	= new LinkedHashMap<String, String>();
	private static boolean isHelpOpened = false;
	/**
	 * Add a line to inform the user about a hot key
	 * 
	 * @param key
	 *            The hot key string
	 * @param description
	 *            The text that explains what this key does
	 */
	public static void addHotkeyHelp(final String key, final String description) {
		HelpManager.hotkeysHelp.put(key, description);
	}

	/**
	 * A call to ths method will bring a popup with the help added previously.
	 */
	public static void bringHelpPopup() {
		if(isHelpOpened) {
			return;
		}
		isHelpOpened = true;
		final StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<table style='width: 100%'>");
		for (final Entry<String, String> entry : HelpManager.hotkeysHelp.entrySet()) {
			htmlContent.append("<tr><td style='text-align: right'><b>[" + entry.getKey() + "]</b></td><td> - </td><td>" + entry.getValue() + "</td></tr>");
		}
		htmlContent.append("</table>");
		final PopupPanel pop = new PopupPanel(true);
		// Attach (display) the glass panel
		final GlassPanel glassPanel = new GlassPanel(false);
		RootPanel.get().add(glassPanel, 0, 0);
		final VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(10);
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		final HTML htmlHTMLTitle = new HTML("<h2>Hotkeys</h2>");
		final HTML htmlHTMLContent = new HTML(htmlContent.toString());
		final Button close = new Button("Close");
		
		close.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pop.removeFromParent();
				glassPanel.removeFromParent();		
				isHelpOpened = false;
			}
		});
		vPanel.add(htmlHTMLTitle);
		vPanel.add(htmlHTMLContent);
		vPanel.add(close);
		pop.add(vPanel);
		pop.center();
	}
}
