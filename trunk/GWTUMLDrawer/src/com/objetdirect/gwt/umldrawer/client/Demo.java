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
package com.objetdirect.gwt.umldrawer.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;

/**
 * This class is an exemple of a static contruction of a uml diagram
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class Demo {

	private final static int	FIRST_ROW				= 85;
	private final static int	SECOND_ROW				= 250;
	private final static int	THIRD_ROW				= 450;
	private final static int	FOURTH_ROW				= 650;
	private final static int	MIDDLE_SECOND_THIRD_ROW	= 350;
	private final static int	MIDDLE_THIRD_FOURTH_ROW	= 550;

	/**
	 * Constructor of the demo, it creates all the demo uml artifacts and positions it
	 * 
	 * @param canvas
	 *            The {@link UMLCanvas} where to add the demo uml artifacts
	 */
	public Demo(final UMLCanvas canvas) {
		super();
		Log.trace("Creating demo");

		final ClassArtifact uMLCanvas = new ClassArtifact(canvas, 0, "UMLCanvas");
		final ClassArtifact uMLEventListener = new ClassArtifact(canvas, 1, "UMLEventListener", "Interface");
		final ClassArtifact externClass = new ClassArtifact(canvas, 2, "ExternClass", "Imaginary");
		final ClassArtifact uMLArtifact = new ClassArtifact(canvas, 3, "UMLArtifact", "Abstract");
		final ClassArtifact boxArtifact = new ClassArtifact(canvas, 4, "BoxArtifact", "Abstract");
		final ClassArtifact classArtifact = new ClassArtifact(canvas, 5, "ClassArtifact");
		final ClassArtifact classPartArtifact = new ClassArtifact(canvas, 6, "ClassPartArtifact", "Abstract");
		final ClassArtifact classPartAttributesArtifact = new ClassArtifact(canvas, 7, "ClassPartAttributesArtifact");
		final ClassArtifact classPartMethodsArtifact = new ClassArtifact(canvas, 8, "ClassPartMethodsArtifact");
		final ClassArtifact classPartNameArtifact = new ClassArtifact(canvas, 9, "ClassPartNameArtifact");
		final ClassArtifact noteArtifact = new ClassArtifact(canvas, 10, "NoteArtifact");
		final ClassArtifact linkArtifact = new ClassArtifact(canvas, 11, "LinkArtifact", "Abstract");
		final ClassArtifact linkClassRelationArtifact = new ClassArtifact(canvas, 12, "LinkClassRelationArtifact");
		final ClassArtifact linkNoteArtifact = new ClassArtifact(canvas, 13, "LinkNoteArtifact");
		final ClassArtifact relationLinkArtifact = new ClassArtifact(canvas, 14, "RelationLinkArtifact");

		uMLCanvas.setLocation(new Point(500, Demo.FIRST_ROW));
		uMLEventListener.setLocation(new Point(10, Demo.FIRST_ROW - 10));
		uMLArtifact.setLocation(new Point(925, Demo.FIRST_ROW));
		externClass.setLocation(new Point(140, Demo.SECOND_ROW));
		boxArtifact.setLocation(new Point(355, Demo.SECOND_ROW));
		linkArtifact.setLocation(new Point(850, Demo.MIDDLE_SECOND_THIRD_ROW));
		classArtifact.setLocation(new Point(100, Demo.THIRD_ROW));
		noteArtifact.setLocation(new Point(550, Demo.THIRD_ROW));
		linkClassRelationArtifact.setLocation(new Point(800, Demo.FOURTH_ROW));
		linkNoteArtifact.setLocation(new Point(700, Demo.MIDDLE_THIRD_FOURTH_ROW));
		relationLinkArtifact.setLocation(new Point(950, Demo.MIDDLE_THIRD_FOURTH_ROW));
		classPartArtifact.setLocation(new Point(325, Demo.THIRD_ROW - 15));
		classPartAttributesArtifact.setLocation(new Point(50, Demo.FOURTH_ROW));
		classPartMethodsArtifact.setLocation(new Point(305, Demo.FOURTH_ROW));
		classPartNameArtifact.setLocation(new Point(525, Demo.FOURTH_ROW));

		uMLEventListener.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "boolean", "onNewUMLArtifact", Arrays.asList(new UMLParameter("UMLArtifact",
				"uMLArtifact"))));
		uMLEventListener.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "boolean", "onEditUMLArtifact", Arrays.asList(new UMLParameter("UMLArtifact",
				"uMLArtifact"))));
		uMLEventListener.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "boolean", "onDeleteUMLArtifact", Arrays.asList(new UMLParameter("UMLArtifact",
				"uMLArtifact"))));

		uMLCanvas.addAttribute(new UMLClassAttribute(UMLVisibility.PRIVATE, "HashMap<GfxObject,UMLArtifact>", "objects"));
		uMLCanvas.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "void", "add", Arrays.asList(new UMLParameter("UMLArtifact", "uMLArtifact"))));
		uMLCanvas.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "void", "remove", Arrays.asList(new UMLParameter("UMLArtifact", "uMLArtifact"))));

		uMLArtifact.addAttribute(new UMLClassAttribute(UMLVisibility.PROTECTED, "GfxObject", "gfxObject"));
		uMLArtifact.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "void", "edit", Arrays.asList(new UMLParameter("GfxObject", "editedGfxObject"))));

		classPartArtifact.addMethod(new UMLClassMethod(UMLVisibility.PACKAGE, "void", "computeBounds", new ArrayList<UMLParameter>()));

		canvas.add(uMLCanvas);
		canvas.add(uMLEventListener);
		canvas.add(externClass);
		canvas.add(uMLArtifact);
		canvas.add(boxArtifact);
		canvas.add(classArtifact);
		canvas.add(classPartArtifact);
		canvas.add(classPartAttributesArtifact);
		canvas.add(classPartMethodsArtifact);
		canvas.add(classPartNameArtifact);
		canvas.add(noteArtifact);
		canvas.add(linkArtifact);
		canvas.add(linkClassRelationArtifact);
		canvas.add(linkNoteArtifact);
		canvas.add(relationLinkArtifact);

		final ClassRelationLinkArtifact uMLCanvas_uMLEventListener = new ClassRelationLinkArtifact(canvas, 15, uMLCanvas, uMLEventListener, LinkKind.AGGREGATION_RELATION);
		final ClassRelationLinkArtifact uMLEventListener_externClass = new ClassRelationLinkArtifact(canvas, 16, uMLEventListener, externClass,
				LinkKind.REALIZATION_RELATION);
		final ClassRelationLinkArtifact uMLCanvas_uMLArtifact = new ClassRelationLinkArtifact(canvas, 17, uMLCanvas, uMLArtifact, LinkKind.COMPOSITION_RELATION);
		final ClassRelationLinkArtifact uMLArtifact_uMLArtifact = new ClassRelationLinkArtifact(canvas, 18, uMLArtifact, uMLArtifact, LinkKind.COMPOSITION_RELATION);
		final ClassRelationLinkArtifact uMLArtifact_boxArtifact = new ClassRelationLinkArtifact(canvas, 19, uMLArtifact, boxArtifact, LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact uMLArtifact_linkArtifact = new ClassRelationLinkArtifact(canvas, 20, uMLArtifact, linkArtifact, LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact uMLArtifact_linkArtifact_2 = new ClassRelationLinkArtifact(canvas, 21, uMLArtifact, linkArtifact, LinkKind.ASSOCIATION_RELATION);
		final ClassRelationLinkArtifact boxArtifact_classArtifact = new ClassRelationLinkArtifact(canvas, 22, boxArtifact, classArtifact, LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact boxArtifact_classPartArtifact = new ClassRelationLinkArtifact(canvas, 23, boxArtifact, classPartArtifact,
				LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact boxArtifact_noteArtifact = new ClassRelationLinkArtifact(canvas, 24, boxArtifact, noteArtifact, LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact linkArtifact_relationLinkArtifact = new ClassRelationLinkArtifact(canvas, 25, linkArtifact, relationLinkArtifact,
				LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact linkArtifact_linkClassRelationArtifact = new ClassRelationLinkArtifact(canvas, 26, linkArtifact, linkClassRelationArtifact,
				LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact linkArtifact_linkNoteArtifact = new ClassRelationLinkArtifact(canvas, 27, linkArtifact, linkNoteArtifact,
				LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact classPartArtifact_classPartAttributesArtifact = new ClassRelationLinkArtifact(canvas, 28, classPartArtifact,
				classPartAttributesArtifact, LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact classPartArtifact_classPartMethodsArtifact = new ClassRelationLinkArtifact(canvas, 29, classPartArtifact, classPartMethodsArtifact,
				LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact classPartArtifact_classPartNameArtifact = new ClassRelationLinkArtifact(canvas, 30, classPartArtifact, classPartNameArtifact,
				LinkKind.GENERALIZATION_RELATION);
		final ClassRelationLinkArtifact classArtifact_classPartArtifact = new ClassRelationLinkArtifact(canvas, 31, classArtifact, classPartArtifact,
				LinkKind.COMPOSITION_RELATION);

		uMLCanvas_uMLEventListener.setRightNavigability(false);
		classArtifact_classPartArtifact.setCardinalities("1", "3");
		uMLArtifact_linkArtifact_2.setName("Links");
		uMLArtifact_linkArtifact_2.setLeftCardinality("2");
		uMLArtifact_linkArtifact_2.setRightCardinality("0..*");
		canvas.add(uMLCanvas_uMLEventListener);
		canvas.add(uMLEventListener_externClass);
		canvas.add(uMLCanvas_uMLArtifact);
		canvas.add(uMLArtifact_uMLArtifact);
		canvas.add(uMLArtifact_boxArtifact);
		canvas.add(uMLArtifact_linkArtifact);
		canvas.add(uMLArtifact_linkArtifact_2);
		canvas.add(boxArtifact_classArtifact);
		canvas.add(boxArtifact_classPartArtifact);
		canvas.add(boxArtifact_noteArtifact);
		canvas.add(linkArtifact_relationLinkArtifact);
		canvas.add(linkArtifact_linkClassRelationArtifact);
		canvas.add(linkArtifact_linkNoteArtifact);
		canvas.add(classPartArtifact_classPartAttributesArtifact);
		canvas.add(classPartArtifact_classPartMethodsArtifact);
		canvas.add(classPartArtifact_classPartNameArtifact);
		canvas.add(classArtifact_classPartArtifact);

		final NoteArtifact titleNote = new NoteArtifact(canvas, 32, "Demo class diagram\nCanvas and artifact of gwtuml");
		final NoteArtifact classPartNote = new NoteArtifact(canvas, 33, "ClassArtifact\nis cut in 3 parts\nfor code clarity");

		titleNote.setLocation(new Point(15, 15));
		classPartNote.setLocation(new Point(30, Demo.MIDDLE_SECOND_THIRD_ROW));

		canvas.add(titleNote);
		canvas.add(classPartNote);

		final LinkNoteArtifact classPartNote_classArtifact = new LinkNoteArtifact(canvas, 34, classPartNote, classArtifact);
		final LinkNoteArtifact classPartNote_classPartArtifact = new LinkNoteArtifact(canvas, 35, classPartNote, classPartArtifact);
		final LinkNoteArtifact classPartNote_classArtifact_classPartArtifact = new LinkNoteArtifact(canvas, 36, classPartNote, classArtifact_classPartArtifact);

		canvas.add(classPartNote_classArtifact);
		canvas.add(classPartNote_classPartArtifact);
		canvas.add(classPartNote_classArtifact_classPartArtifact);

	}
}
