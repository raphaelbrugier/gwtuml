/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.objetdirect.gwt.umldrawer.client.webinterface;

import java.util.ArrayList;
import java.util.Arrays;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
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
public class Demo extends AbsolutePanel {

    private final static int FIRST_ROW = 85;
    private final static int SECOND_ROW = 250;
    private final static int THIRD_ROW = 450;
    private final static int FOURTH_ROW = 650;
    private final static int MIDDLE_SECOND_THIRD_ROW = 350;
    private final static int MIDDLE_THIRD_FOURTH_ROW = 550;
    
    /**
     * Constructor of the demo, it creates all the demo uml artifacts and positions it 
     *
     * @param canvas The {@link UMLCanvas} where to add the demo uml artifacts 
     */
    public Demo(final UMLCanvas canvas) {
	super();
	Log.trace("Creating demo");
	
	final ClassArtifact uMLCanvas 			= new ClassArtifact("UMLCanvas");
	final ClassArtifact uMLEventListener		= new ClassArtifact("UMLEventListener", "Interface");
	final ClassArtifact externClass 		= new ClassArtifact("ExternClass", "Imaginary");
	final ClassArtifact uMLArtifact 		= new ClassArtifact("UMLArtifact", "Abstract");
	final ClassArtifact boxArtifact 		= new ClassArtifact("BoxArtifact", "Abstract");
	final ClassArtifact classArtifact 		= new ClassArtifact("ClassArtifact");
	final ClassArtifact classPartArtifact 		= new ClassArtifact("ClassPartArtifact", "Abstract");
	final ClassArtifact classPartAttributesArtifact	= new ClassArtifact("ClassPartAttributesArtifact");
	final ClassArtifact classPartMethodsArtifact 	= new ClassArtifact("ClassPartMethodsArtifact");
	final ClassArtifact classPartNameArtifact 	= new ClassArtifact("ClassPartNameArtifact");
	final ClassArtifact noteArtifact		= new ClassArtifact("NoteArtifact");
	final ClassArtifact linkArtifact 		= new ClassArtifact("LinkArtifact", "Abstract");
	final ClassArtifact linkClassRelationArtifact	= new ClassArtifact("LinkClassRelationArtifact");
	final ClassArtifact linkNoteArtifact		= new ClassArtifact("LinkNoteArtifact");
	final ClassArtifact relationLinkArtifact	= new ClassArtifact("RelationLinkArtifact");
	
	uMLCanvas.			setLocation(new Point(500, FIRST_ROW));
	uMLEventListener.		setLocation(new Point(10, FIRST_ROW-10));
	uMLArtifact.			setLocation(new Point(925, FIRST_ROW));
	externClass.			setLocation(new Point(140, SECOND_ROW));
	boxArtifact.			setLocation(new Point(355, SECOND_ROW));
	linkArtifact.			setLocation(new Point(850, MIDDLE_SECOND_THIRD_ROW));
	classArtifact.			setLocation(new Point(100, THIRD_ROW));
	noteArtifact.			setLocation(new Point(550, THIRD_ROW));
	linkClassRelationArtifact.	setLocation(new Point(800, FOURTH_ROW));
	linkNoteArtifact.		setLocation(new Point(700, MIDDLE_THIRD_FOURTH_ROW));
	relationLinkArtifact.		setLocation(new Point(950, MIDDLE_THIRD_FOURTH_ROW));
    	classPartArtifact.		setLocation(new Point(325, THIRD_ROW-15));
	classPartAttributesArtifact.	setLocation(new Point(50, FOURTH_ROW));
	classPartMethodsArtifact.	setLocation(new Point(305, FOURTH_ROW));
	classPartNameArtifact.		setLocation(new Point(525, FOURTH_ROW));
	 
	uMLEventListener.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "boolean", "onNewUMLArtifact", 	Arrays.asList(new UMLParameter("UMLArtifact", "uMLArtifact"))));
	uMLEventListener.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "boolean", "onEditUMLArtifact", 	Arrays.asList(new UMLParameter("UMLArtifact", "uMLArtifact"))));
	uMLEventListener.addMethod(new UMLClassMethod(UMLVisibility.PUBLIC, "boolean", "onDeleteUMLArtifact", 	Arrays.asList(new UMLParameter("UMLArtifact", "uMLArtifact"))));

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
	
	
	
	
	
	
	
	final ClassRelationLinkArtifact uMLCanvas_uMLEventListener 				= new ClassRelationLinkArtifact(uMLCanvas, uMLEventListener, LinkKind.AGGREGATION_RELATION);
	final ClassRelationLinkArtifact uMLEventListener_externClass				= new ClassRelationLinkArtifact(uMLEventListener, externClass, LinkKind.REALIZATION_RELATION);
	final ClassRelationLinkArtifact uMLCanvas_uMLArtifact 				= new ClassRelationLinkArtifact(uMLCanvas, uMLArtifact, LinkKind.COMPOSITION_RELATION);
	final ClassRelationLinkArtifact uMLArtifact_uMLArtifact 				= new ClassRelationLinkArtifact(uMLArtifact, uMLArtifact, LinkKind.COMPOSITION_RELATION);
	final ClassRelationLinkArtifact uMLArtifact_boxArtifact 				= new ClassRelationLinkArtifact(uMLArtifact, boxArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact uMLArtifact_linkArtifact 				= new ClassRelationLinkArtifact(uMLArtifact, linkArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact uMLArtifact_linkArtifact_2 				= new ClassRelationLinkArtifact(uMLArtifact, linkArtifact, LinkKind.ASSOCIATION_RELATION);
	final ClassRelationLinkArtifact boxArtifact_classArtifact				= new ClassRelationLinkArtifact(boxArtifact, classArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact boxArtifact_classPartArtifact 			= new ClassRelationLinkArtifact(boxArtifact, classPartArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact boxArtifact_noteArtifact 				= new ClassRelationLinkArtifact(boxArtifact, noteArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact linkArtifact_relationLinkArtifact 			= new ClassRelationLinkArtifact(linkArtifact, relationLinkArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact linkArtifact_linkClassRelationArtifact 		= new ClassRelationLinkArtifact(linkArtifact, linkClassRelationArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact linkArtifact_linkNoteArtifact 			= new ClassRelationLinkArtifact(linkArtifact, linkNoteArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact classPartArtifact_classPartAttributesArtifact	= new ClassRelationLinkArtifact(classPartArtifact, classPartAttributesArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact classPartArtifact_classPartMethodsArtifact 		= new ClassRelationLinkArtifact(classPartArtifact, classPartMethodsArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact classPartArtifact_classPartNameArtifact 		= new ClassRelationLinkArtifact(classPartArtifact, classPartNameArtifact, LinkKind.GENERALIZATION_RELATION);
	final ClassRelationLinkArtifact classArtifact_classPartArtifact 			= new ClassRelationLinkArtifact(classArtifact, classPartArtifact, LinkKind.COMPOSITION_RELATION);
	
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
	
	
	final NoteArtifact titleNote 		= new NoteArtifact("Demo class diagram\nCanvas and artifact of gwtuml");
	final NoteArtifact classPartNote 	= new NoteArtifact("ClassArtifact\nis cut in 3 parts\nfor code clarity");
	
	titleNote.	setLocation(new Point(15, 15));
	classPartNote.	setLocation(new Point(30, MIDDLE_SECOND_THIRD_ROW));
	
	canvas.add(titleNote);
	canvas.add(classPartNote);

	final LinkNoteArtifact classPartNote_classArtifact 			= new LinkNoteArtifact(classPartNote, classArtifact);
	final LinkNoteArtifact classPartNote_classPartArtifact 			= new LinkNoteArtifact(classPartNote, classPartArtifact);
	final LinkNoteArtifact classPartNote_classArtifact_classPartArtifact  	= new LinkNoteArtifact(classPartNote, classArtifact_classPartArtifact);
	
	canvas.add(classPartNote_classArtifact);
	canvas.add(classPartNote_classPartArtifact);
	canvas.add(classPartNote_classArtifact_classPartArtifact);
	
    }
}
