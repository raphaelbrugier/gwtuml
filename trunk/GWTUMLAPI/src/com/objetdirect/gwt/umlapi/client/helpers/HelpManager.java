/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright Â© 2009 Objet Direct Contact: gwtuml@googlegroups.com
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
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class supply an easy way to bring help about the drawer
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @contributor Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class HelpManager {

	private static LinkedHashMap<String, String>	hotkeysHelp	= new LinkedHashMap<String, String>();
	private static PopupPanel popup;
	/**
	 * Add a line to inform the user about a hot key
	 * 
	 * @param key
	 *            The hot key string
	 * @param description
	 *            The text that explains what this key does
	 */
	private static void addHotkeyHelp(final String key, final String description) {
		HelpManager.hotkeysHelp.put(key, description);
	}
	
	/**
	 * Fill the hotkeys and descriptions map
	 */
	private static void forceStaticInit() {
		addHotkeyHelp("H", "Bring this help");
		addHotkeyHelp("C", "Add a new class");
		addHotkeyHelp("O", "Add a new object");
		addHotkeyHelp("N", "Add a new note");
		addHotkeyHelp("F", "Add a new life line");
		addHotkeyHelp("A", "Add a new aggregation relation");
		addHotkeyHelp("L", "Add a new association relation");
		addHotkeyHelp("K", "Add a new composition relation");
		addHotkeyHelp("D", "Add a new dependency relation");
		addHotkeyHelp("G", "Add a new generalization relation");
		addHotkeyHelp("R", "Add a new realization relation");
		addHotkeyHelp("I", "Add a new instantiation relation");
		addHotkeyHelp("T", "Add a new note link");
		addHotkeyHelp("E", "Add a new class relation");
		addHotkeyHelp("M", "Add a new asynchronous message");
		addHotkeyHelp("P", "Add a new synchronous message");
		addHotkeyHelp("B", "Add a new object creation message");
		addHotkeyHelp("J", "Add a new lost message");
		addHotkeyHelp("Y", "Add a new found message");
		addHotkeyHelp("U", "Update URL with current diagram");
		addHotkeyHelp("Del", "Remove selected object(s)");
		addHotkeyHelp("Ctrl]+[Up", "Move up selected object");
		addHotkeyHelp("Ctrl]+[Down", "Move down selected object");
		addHotkeyHelp("Ctrl]+[Left", "Move left selected object");
		addHotkeyHelp("Ctrl]+[Right", "Move right selected object");
	}

	/**
	 * A call to this method will bring a popup with the help added previously.
	 */
	public static void bringHelpPopup() {
		if(popup == null) {
			forceStaticInit();
			
			final StringBuilder htmlContent = new StringBuilder();
			htmlContent.append("<table style='width: 100%'>");
			for (final Entry<String, String> entry : HelpManager.hotkeysHelp.entrySet()) {
				htmlContent.append("<tr><td style='text-align: right'><b>[" + entry.getKey() + "]</b></td><td> - </td><td>" + entry.getValue() + "</td></tr>");
			}
			htmlContent.append("</table>");
			popup = new PopupPanel(true);
			popup.setModal(true);
			popup.setGlassEnabled(true);
			
			final VerticalPanel vPanel = new VerticalPanel();
			vPanel.setSpacing(10);
			vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			final HTML htmlHTMLTitle = new HTML("<h2>Hotkeys</h2>");
			final HTML htmlHTMLContent = new HTML(htmlContent.toString());
			final Button close = new Button("Close");
			
			close.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					popup.hide();
				}
			});
			vPanel.add(htmlHTMLTitle);
			vPanel.add(htmlHTMLContent);
			vPanel.add(close);
			
			popup.add(vPanel);
		}

		popup.center();
	}
}
