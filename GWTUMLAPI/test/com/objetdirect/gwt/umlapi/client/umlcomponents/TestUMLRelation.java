/*
 * This file is part of the GWTUML project and was written by Raphael Brugier <raphael-dot-brugier.at.gmail'dot'com> for Objet Direct
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

import com.objetdirect.gwt.umlapi.client.exceptions.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;

/**
 * Tests for the UMLRelation class.
 * 
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class TestUMLRelation extends TestCase {

	public void testIsBidirectionnal() {
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		relation.setName("");

		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setLeftCardinality("1");
		relation.setLeftStereotype("<<owner>>");
		relation.setLeftRole("rlink");

		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setRightCardinality("1");
		relation.setRightConstraint("");
		relation.setRightRole("link");

		assertTrue(relation.isBidirectional());

		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		assertFalse(relation.isBidirectional());

		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftAdornment(LinkAdornment.WIRE_ARROW);
		assertFalse(relation.isBidirectional());

		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightRole("");
	}

	public void testOwner() {
		UMLClass leftEntity = new UMLClass("LeftEntity");
		UMLClass rightEntity = new UMLClass("RightEntity");

		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		relation.setName("");

		// Bidirectional
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setLeftCardinality("1");
		relation.setLeftStereotype("<<owner>>");
		relation.setLeftRole("rlink");
		relation.setLeftTarget(leftEntity);

		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setRightCardinality("1");
		relation.setRightStereotype("");
		relation.setRightRole("link");
		relation.setRightTarget(rightEntity);

		assertTrue(relation.isLeftOwner());
		assertFalse(relation.isRightOwner());
		assertEquals(leftEntity, relation.getOwner());
		assertEquals(rightEntity, relation.getTarget());

		relation.setLeftStereotype("");
		relation.setRightStereotype("<<owner>>");

		assertFalse(relation.isLeftOwner());
		assertTrue(relation.isRightOwner());
		assertEquals(rightEntity, relation.getOwner());
		assertEquals(leftEntity, relation.getTarget());

		relation.setRightStereotype("");
		try {
			relation.isLeftOwner();
			fail("Exception expected");
		} catch (GWTUMLAPIException e) {
			assertEquals("A bidirectional relation must have an owner defined.", e.getMessage());
		}

		// Composition
		relation.setLinkKind(LinkKind.COMPOSITION_RELATION);

		relation.setLeftAdornment(LinkAdornment.SOLID_DIAMOND);
		relation.setLeftCardinality("");
		relation.setLeftStereotype("");
		relation.setLeftRole("");

		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);

		assertTrue(relation.isLeftOwner());

		relation.setLeftAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightAdornment(LinkAdornment.SOLID_DIAMOND);
		assertFalse(relation.isLeftOwner());

		relation.setLeftAdornment(LinkAdornment.NONE);
		try {
			relation.isLeftOwner();
			fail("Exception expected");
		} catch (GWTUMLAPIException e) {
			assertEquals("An association must have an arrow on one side to define the owner.", e.getMessage());
		}
	}

	public void testIsAComposition() {
		UMLRelation relation = new UMLRelation(LinkKind.COMPOSITION_RELATION);
		relation.setName("");

		// Composition
		relation.setLeftAdornment(LinkAdornment.INVERTED_SOLID_DIAMOND);
		relation.setLeftCardinality("");
		relation.setLeftConstraint("");
		relation.setLeftRole("");

		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightCardinality("1");
		relation.setRightConstraint("");
		relation.setRightRole("link");

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
	}

	public void testIsNotOneToOne() {
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);

		relation.setLeftCardinality("1");
		relation.setRightCardinality("*");
		assertFalse(relation.isOneToOne());

		relation.setLeftCardinality("*");
		relation.setRightCardinality("1");
		assertFalse(relation.isOneToOne());

		relation.setLeftCardinality("*");
		relation.setRightCardinality("*");
		assertFalse(relation.isOneToOne());
	}

	public void testIsOneToMany() {
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		UMLClass leftClass = new UMLClass("LeftClass");
		UMLClass rightclass = new UMLClass("RightClass");
		relation.setLeftTarget(leftClass);
		relation.setRightTarget(rightclass);
		relation.setLeftRole("");
		relation.setRightRole("");

		// Unidirectional left to right
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightRole("rightRole");

		assertOneToMany(relation, "", "*");
		assertOneToMany(relation, "1", "*");

		// Unidirectional right to left
		relation.setLeftAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftRole("leftRole");
		relation.setRightRole("");

		assertOneToMany(relation, "*", "");
		assertOneToMany(relation, "*", "1");

		// Bidirectional left owner
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftRole("leftRole");
		relation.setRightRole("rightRole");
		relation.setLeftStereotype("<<owner>>");
		relation.setRightStereotype("");

		assertOneToMany(relation, "", "*");
		assertOneToMany(relation, "1", "*");

		// Bidirectional right owner
		relation.setLeftStereotype("");
		relation.setRightStereotype("<<owner>>");

		assertOneToMany(relation, "*", "");
		assertOneToMany(relation, "*", "1");
	}

	private void assertOneToMany(UMLRelation relation, String leftCardinality, String rightCardinality) {
		relation.setLeftCardinality(leftCardinality);
		relation.setRightCardinality(rightCardinality);
		assertTrue(relation.isOneToMany());
	}

	public void testIsNotOneToMany() {
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		UMLClass leftClass = new UMLClass("LeftClass");
		UMLClass rightclass = new UMLClass("RightClass");
		relation.setLeftTarget(leftClass);
		relation.setRightTarget(rightclass);
		relation.setLeftRole("");
		relation.setRightRole("rightRole");
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);

		assertNotOneToMany(relation, "", "");
		assertNotOneToMany(relation, "1", "1");
		assertNotOneToMany(relation, "*", "*");

		// Unidirectional left to right
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		relation.setLeftRole("");
		relation.setRightRole("rightRole");

		assertNotOneToMany(relation, "*", "");
		assertNotOneToMany(relation, "*", "1");

		// Unidirectional right to left
		relation.setLeftAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftRole("leftRole");
		relation.setRightRole("");

		assertNotOneToMany(relation, "", "*");
		assertNotOneToMany(relation, "1", "*");

		// Bidirectional left owner
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftStereotype("<<owner>>");
		relation.setRightStereotype("");
		relation.setLeftRole("leftRole");
		relation.setRightRole("rightRole");

		assertNotOneToMany(relation, "*", "");
		assertNotOneToMany(relation, "*", "1");

		// Bidirectional right owner
		relation.setLeftStereotype("");
		relation.setRightStereotype("<<owner>>");

		assertNotOneToMany(relation, "", "*");
		assertNotOneToMany(relation, "1", "*");
	}

	private void assertNotOneToMany(UMLRelation relation, String leftCardinality, String rightCardinality) {
		relation.setLeftCardinality(leftCardinality);
		relation.setRightCardinality(rightCardinality);
		assertFalse(relation.isOneToMany());
	}

	public void testIsManyToOne() {
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		UMLClass leftClass = new UMLClass("LeftClass");
		UMLClass rightclass = new UMLClass("RightClass");
		relation.setLeftTarget(leftClass);
		relation.setRightTarget(rightclass);
		relation.setLeftRole("");
		relation.setRightRole("");

		// Unidirectional left to right
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightRole("rightRole");

		assertManyToOne(relation, "*", "1");

		// Unidirectional right to left
		relation.setLeftAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftRole("leftRole");
		relation.setRightRole("");

		assertManyToOne(relation, "1", "*");

		// Bidirectional left owner
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftRole("leftRole");
		relation.setRightRole("rightRole");
		relation.setLeftStereotype("<<owner>>");
		relation.setRightStereotype("");

		assertManyToOne(relation, "*", "1");

		// Bidirectional right owner
		relation.setLeftStereotype("");
		relation.setRightStereotype("<<owner>>");

		assertManyToOne(relation, "1", "*");
	}

	private void assertManyToOne(UMLRelation relation, String leftCardinality, String rightCardinality) {
		relation.setLeftCardinality(leftCardinality);
		relation.setRightCardinality(rightCardinality);
		assertTrue(relation.isManyToOne());
	}

	public void testIsNotManyToOne() {
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		UMLClass leftClass = new UMLClass("LeftClass");
		UMLClass rightclass = new UMLClass("RightClass");
		relation.setLeftTarget(leftClass);
		relation.setRightTarget(rightclass);
		relation.setLeftRole("");
		relation.setRightRole("rightRole");
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);

		assertNotManyToOne(relation, "", "");
		assertNotManyToOne(relation, "1", "1");
		assertNotManyToOne(relation, "*", "*");

		// Unidirectional left to right
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		relation.setLeftRole("");
		relation.setRightRole("rightRole");

		assertNotManyToOne(relation, "", "*");
		assertNotManyToOne(relation, "1", "*");

		// Unidirectional right to left
		relation.setLeftAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftRole("leftRole");
		relation.setRightRole("");

		assertNotManyToOne(relation, "*", "");
		assertNotManyToOne(relation, "*", "1");

		// Bidirectional left owner
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftStereotype("<<owner>>");
		relation.setRightStereotype("");
		relation.setLeftRole("leftRole");
		relation.setRightRole("rightRole");

		assertNotManyToOne(relation, "", "*");
		assertNotManyToOne(relation, "1", "*");

		// Bidirectional right owner
		relation.setLeftStereotype("");
		relation.setRightStereotype("<<owner>>");

		assertNotManyToOne(relation, "*", "");
		assertNotManyToOne(relation, "*", "1");
	}

	private void assertNotManyToOne(UMLRelation relation, String leftCardinality, String rightCardinality) {
		relation.setLeftCardinality(leftCardinality);
		relation.setRightCardinality(rightCardinality);
		assertFalse(relation.isManyToOne());
	}

	public void testManyToMany() {
		UMLRelation relation = new UMLRelation(LinkKind.ASSOCIATION_RELATION);
		UMLClass leftClass = new UMLClass("LeftClass");
		UMLClass rightclass = new UMLClass("RightClass");
		relation.setLeftTarget(leftClass);
		relation.setRightTarget(rightclass);
		relation.setLeftCardinality("*");
		relation.setRightCardinality("*");

		// Unidirectional left to right
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightRole("rightRole");

		assertTrue(relation.isManyToMany());

		// Unidirectional right to left
		relation.setLeftAdornment(LinkAdornment.WIRE_ARROW);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftRole("leftRole");
		relation.setRightRole("");

		assertTrue(relation.isManyToMany());

		// Bidirectional left owner
		relation.setLeftAdornment(LinkAdornment.NONE);
		relation.setRightAdornment(LinkAdornment.NONE);
		relation.setLeftRole("leftRole");
		relation.setRightRole("rightRole");
		relation.setLeftStereotype("<<owner>>");
		relation.setRightStereotype("");

		assertTrue(relation.isManyToMany());

		// Bidirectional right owner
		relation.setLeftStereotype("");
		relation.setRightStereotype("<<owner>>");

		assertTrue(relation.isManyToMany());

		// NOT many to many
		relation.setRightCardinality("1");
		assertFalse(relation.isManyToMany());
	}
}
