/*
 * This file is part of the GWTUML project and was written by Brugier Raphael <raphael-dot-brugier.at.gmail'dot'com> for Objet Direct
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
 
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import junit.framework.TestCase;

/**
 * Test for the UMLType class
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class TestUMLType extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test the supportedTypes	
	 */
	public void testSupportedTypes() {
		assertEquals(UMLType.STRING, UMLType.getUMLTypeFromString("String"));
		
		assertEquals(UMLType.INT, UMLType.getUMLTypeFromString("int"));
		
		assertEquals(UMLType.INTEGER, UMLType.getUMLTypeFromString("Integer"));
		
		assertEquals(UMLType.LONG, UMLType.getUMLTypeFromString("long"));
		
		assertEquals(UMLType.WRAPPED_LONG, UMLType.getUMLTypeFromString("Long"));
		
		assertEquals(UMLType.BYTE, UMLType.getUMLTypeFromString("byte"));
		
		assertEquals(UMLType.WRAPPED_BYTE, UMLType.getUMLTypeFromString("Byte"));

		assertEquals(UMLType.SHORT, UMLType.getUMLTypeFromString("short"));
		
		assertEquals(UMLType.WRAPPED_SHORT, UMLType.getUMLTypeFromString("Short"));
		
		assertEquals(UMLType.BOOLEAN, UMLType.getUMLTypeFromString("boolean"));
		
		assertEquals(UMLType.CHAR, UMLType.getUMLTypeFromString("char"));
		
		assertEquals(UMLType.CHARACTER, UMLType.getUMLTypeFromString("Character"));
		
		assertEquals(UMLType.FLOAT, UMLType.getUMLTypeFromString("float"));
		
		assertEquals(UMLType.WRAPPED_FLOAT, UMLType.getUMLTypeFromString("Float"));
		
		assertEquals(UMLType.DOUBLE, UMLType.getUMLTypeFromString("double"));
		
		assertEquals(UMLType.WRAPPED_FLOAT, UMLType.getUMLTypeFromString("Double"));
	}
	
	/**
	 * The unsupported types should return a CUSTOM type.
	 */
	public void testUnsupportedType() {
		assertEquals(UMLType.CUSTOM, UMLType.getUMLTypeFromString("MyClassType"));
	}
}
