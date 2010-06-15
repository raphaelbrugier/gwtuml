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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import com.objetdirect.gwt.umldrawer.client.UrlParser;


/**
 * Test the UrlParser class;
 *  @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class TestUrlParser {
	
	@Test
	public void withNormalValues() throws Exception {
		String stringToParse = "Drawer?DiagramType=0&diagram64=PDA+XUNsYX";
		
		UrlParser urlParser = new UrlParser(stringToParse);
		
		assertEquals("Drawer", urlParser.getPageName());
		assertOptionExist(urlParser, "DiagramType", "0");
		assertEquals("PDA+XUNsYX", urlParser.getDiagram64());
	}
	
	private void assertOptionExist(UrlParser urlParser, String key, String value) {
		assertTrue(urlParser.getOptionsList().containsKey(key));
		assertEquals(value, urlParser.getOptionsList().get(key));
	}
}
