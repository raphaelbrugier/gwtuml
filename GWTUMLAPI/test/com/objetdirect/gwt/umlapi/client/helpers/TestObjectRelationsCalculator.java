/*
 * This file is part of the GWTUML project and was written by Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com) for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright Â© 2010 Objet Direct Contact: gwtuml@googlegroups.com
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

import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment.WIRE_ARROW;
import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind.OBJECT_RELATION;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.objetdirect.gwt.umlapi.client.umlCanvas.ObjectDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.ObjectRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLRelation;


/**
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class TestObjectRelationsCalculator {

	/**
	 * 
	 */
	private static final String CLASS_NAME_TARGET = "classNameTarget";

	private static final String OBJECT_CLASSNAME = "Class1";

	@Mock
	ObjectDiagram objectDiagram;
	List<UMLRelation> umlRelations;
	List<ObjectRelation> objectRelations;

	@Mock
	UMLObject umlObject;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);

		umlRelations = new ArrayList<UMLRelation>();
		when(objectDiagram.getClassRelations()).thenReturn(umlRelations);
		objectRelations = new ArrayList<ObjectRelation>();
		when(objectDiagram.getObjectRelations()).thenReturn(objectRelations);

		when(umlObject.getClassName()).thenReturn(OBJECT_CLASSNAME);

	}

	@Test
	public void onePossibleRelation() throws Exception {
		// given
		UMLRelation relation1 = new UMLRelation(OBJECT_RELATION);
		UMLClass classInstantiated = new UMLClass(OBJECT_CLASSNAME);
		relation1.setLeftTarget(classInstantiated);

		UMLClass classTargeted = new UMLClass(CLASS_NAME_TARGET);
		relation1.setRightTarget(classTargeted);
		relation1.setRightAdornment(WIRE_ARROW);

		umlRelations.add(relation1);

		// when
		ObjectRelationsCalculator relationsCalculator = new ObjectRelationsCalculator(objectDiagram, umlObject);

		// then
		List<UMLClass> classes = relationsCalculator.getPossibleClasses();
		assertEquals(1, classes.size());
	}

	@Test
	public void oneRelationInTheModel_OneRelationInTheDiagram_returnZeroRelation() throws Exception {
		// given
		// One to one relation
		UMLRelation relation1 = new UMLRelation(OBJECT_RELATION);
		UMLClass classInstantiated = new UMLClass(OBJECT_CLASSNAME);
		relation1.setLeftTarget(classInstantiated);

		UMLClass classTargeted = new UMLClass(CLASS_NAME_TARGET);
		relation1.setRightTarget(classTargeted);
		relation1.setRightAdornment(WIRE_ARROW);
		relation1.setRightCardinality("1");
		umlRelations.add(relation1);

		// Existing relation in the diagram

		UMLObject objecTarget = new UMLObject("", classTargeted);
		ObjectRelation objectRelation = new ObjectRelation(umlObject, objecTarget);
		objectRelations.add(objectRelation);

		// when
		ObjectRelationsCalculator relationsCalculator = new ObjectRelationsCalculator(objectDiagram, umlObject);

		// then
		List<UMLClass> classes = relationsCalculator.getPossibleClasses();
		assertEquals(0, classes.size());
	}

	@Test
	public void twiRelationInTheModel_OneRelationInTheDiagram_returnOneRelation() throws Exception {
		// given
		// One to one relation
		UMLRelation relation1 = new UMLRelation(OBJECT_RELATION);
		UMLClass classInstantiated = new UMLClass(OBJECT_CLASSNAME);
		relation1.setLeftTarget(classInstantiated);

		UMLClass classTargeted = new UMLClass(CLASS_NAME_TARGET);
		relation1.setRightTarget(classTargeted);
		relation1.setRightAdornment(WIRE_ARROW);
		relation1.setRightCardinality("2");
		umlRelations.add(relation1);

		// Existing relation in the diagram

		UMLObject objecTarget = new UMLObject("", classTargeted);
		ObjectRelation objectRelation = new ObjectRelation(umlObject, objecTarget);
		objectRelations.add(objectRelation);

		// when
		ObjectRelationsCalculator relationsCalculator = new ObjectRelationsCalculator(objectDiagram, umlObject);

		// then
		List<UMLClass> classes = relationsCalculator.getPossibleClasses();
		assertEquals(1, classes.size());
	}
}
