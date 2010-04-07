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
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import junit.framework.TestCase;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;

/**
 * Tests for the UMLRelation class.
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class TestUMLRelation extends TestCase {

	public void testIsBidirectionnal() {
		UMLClass leftEntity = new UMLClass("LeftEntity");
		UMLClass rightEntity = new UMLClass("RightEntity");
		
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		relation.setName("");
		
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setLeftCardinality("1");
		relation.setLeftConstraint("{owner}");
		relation.setLeftRole("rlink");
		relation.setLeftTarget(leftEntity);
		
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setRightCardinality("1");
		relation.setRightConstraint("");
		relation.setRightRole("link");
		relation.setRightTarget(rightEntity);
		
		assertTrue(relation.isBidirectional());
		
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		
		assertFalse(relation.isBidirectional());
	}
	
	
	public void testGetOwner() {
		UMLClass leftEntity = new UMLClass("LeftEntity");
		UMLClass rightEntity = new UMLClass("RightEntity");
		
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		relation.setName("");
		
		// Bidirectional 
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setLeftCardinality("1");
		relation.setLeftConstraint("{owner}");
		relation.setLeftRole("rlink");
		relation.setLeftTarget(leftEntity);
		
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setRightCardinality("1");
		relation.setRightConstraint("");
		relation.setRightRole("link");
		relation.setRightTarget(rightEntity);
		
		assertEquals(leftEntity, relation.getOwner());
		assertEquals(rightEntity, relation.getTarget());
		
		
		// Composition
		relation.setLinkKind(LinkKind.COMPOSITION_RELATION);
		
		relation.setLeftAdornment(LinkAdornment.SOLID_DIAMOND);
		relation.setLeftCardinality("");
		relation.setLeftConstraint("");
		relation.setLeftRole("");
		
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		
		assertEquals(leftEntity, relation.getOwner());
		assertEquals(rightEntity, relation.getTarget());
	}
	
	
	public void testIsLeftOwner() {
		UMLClass leftEntity = new UMLClass("LeftEntity");
		UMLClass rightEntity = new UMLClass("RightEntity");
		
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		relation.setName("");
		
		// Bidirectional 
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setLeftCardinality("1");
		relation.setLeftConstraint("{owner}");
		relation.setLeftRole("rlink");
		relation.setLeftTarget(leftEntity);
		
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setRightCardinality("1");
		relation.setRightConstraint("");
		relation.setRightRole("link");
		relation.setRightTarget(rightEntity);
		
		assertTrue(relation.isLeftOwner());
		
		// Composition
		relation.setLinkKind(LinkKind.COMPOSITION_RELATION);
		
		relation.setLeftAdornment(LinkAdornment.SOLID_DIAMOND);
		relation.setLeftCardinality("");
		relation.setLeftConstraint("");
		relation.setLeftRole("");
		
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		
		assertTrue(relation.isLeftOwner());
	}
	
	
	public void testIsAComposition() {
		UMLClass leftEntity = new UMLClass("LeftEntity");
		UMLClass rightEntity = new UMLClass("RightEntity");
		
		UMLRelation relation = new UMLRelation(LinkKind.COMPOSITION_RELATION);
		relation.setName("");
		
		// Composition 
		relation.setLeftAdornment(LinkAdornment.INVERTED_SOLID_DIAMOND);
		relation.setLeftCardinality("");
		relation.setLeftConstraint("");
		relation.setLeftRole("");
		relation.setLeftTarget(leftEntity);
		
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightCardinality("1");
		relation.setRightConstraint("");
		relation.setRightRole("link");
		relation.setRightTarget(rightEntity);
		
		assertTrue(relation.isAComposition());
		
		// Non composition relation
		relation.setLeftAdornment(LinkAdornment.NONE);
		
		assertFalse(relation.isAComposition());
	}
	
	
	public void testIsOneToOne() {
		
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		
		relation.setLeftCardinality("");
		relation.setRightCardinality("1");
		assertTrue(relation.isOneToOne());
		
		relation.setLeftCardinality("1");
		relation.setRightCardinality("");
		assertTrue(relation.isOneToOne());
		
		relation.setLeftCardinality("1");
		relation.setRightCardinality("1");
		assertTrue(relation.isOneToOne());
		
		relation.setLeftCardinality("1");
		relation.setRightCardinality("*");
		assertFalse(relation.isOneToOne());
		
		relation.setLeftCardinality("*");
		relation.setRightCardinality("1");
		assertFalse(relation.isOneToOne());
	}
}
