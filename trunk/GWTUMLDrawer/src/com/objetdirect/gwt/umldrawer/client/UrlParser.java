/*
 * This file is part of the Gwt-Uml project and was written by Raphaël Brugier <raphael dot brugier at gmail dot com > for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct
 * 
 * Gwt-Uml is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Gwt-Uml is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Gwt-Generator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umldrawer.client;

import java.util.HashMap;

/**
 * Parser for an history token.
 * @see the associated test.
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class UrlParser {

	private String pageName;
	
	private String diagram64;
	
	private HashMap<String, String> optionsList;
	
	public UrlParser(String stringToParse) {
		optionsList = new HashMap<String, String>();
		
		final String[] parts = stringToParse.split("\\?");
		pageName = parts[0];
		optionsList.clear();
		if (parts.length > 1) {
			final String[] params = parts[1].split("&");
			for (final String argument : params) {
				final String[] paramVar = argument.split("=", 2);
				if ((paramVar.length > 0) && (paramVar[0].length() > 0)) {
					if (!paramVar[0].equals("diagram64")) {
						optionsList.put(paramVar[0], paramVar.length > 1 ? paramVar[1] : "");
					} else {
						diagram64 = paramVar.length > 1 ? paramVar[1] : "";
					}
				}
			}
		}
	}

	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * @return the diagram64
	 */
	public String getDiagram64() {
		return diagram64;
	}

	/**
	 * @return the optionsList
	 */
	public HashMap<String, String> getOptionsList() {
		return optionsList;
	}
}
