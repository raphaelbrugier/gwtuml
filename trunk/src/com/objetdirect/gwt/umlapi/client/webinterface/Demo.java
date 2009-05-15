package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.ArrayList;
import java.util.Arrays;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLRelation.RelationKind;

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
	
	
	
	
	
	
	
	final RelationLinkArtifact uMLCanvas_uMLEventListener 				= new RelationLinkArtifact(uMLCanvas, uMLEventListener, RelationKind.AGGREGATION);
	final RelationLinkArtifact uMLEventListener_externClass				= new RelationLinkArtifact(uMLEventListener, externClass, RelationKind.REALIZATION);
	final RelationLinkArtifact uMLCanvas_uMLArtifact 				= new RelationLinkArtifact(uMLCanvas, uMLArtifact, RelationKind.COMPOSITION);
	final RelationLinkArtifact uMLArtifact_uMLArtifact 				= new RelationLinkArtifact(uMLArtifact, uMLArtifact, RelationKind.COMPOSITION);
	final RelationLinkArtifact uMLArtifact_boxArtifact 				= new RelationLinkArtifact(uMLArtifact, boxArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact uMLArtifact_linkArtifact 				= new RelationLinkArtifact(uMLArtifact, linkArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact uMLArtifact_linkArtifact_2 				= new RelationLinkArtifact(uMLArtifact, linkArtifact, RelationKind.ASSOCIATION, 1);
	final RelationLinkArtifact boxArtifact_classArtifact				= new RelationLinkArtifact(boxArtifact, classArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact boxArtifact_classPartArtifact 			= new RelationLinkArtifact(boxArtifact, classPartArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact boxArtifact_noteArtifact 				= new RelationLinkArtifact(boxArtifact, noteArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact linkArtifact_relationLinkArtifact 			= new RelationLinkArtifact(linkArtifact, relationLinkArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact linkArtifact_linkClassRelationArtifact 		= new RelationLinkArtifact(linkArtifact, linkClassRelationArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact linkArtifact_linkNoteArtifact 			= new RelationLinkArtifact(linkArtifact, linkNoteArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact classPartArtifact_classPartAttributesArtifact	= new RelationLinkArtifact(classPartArtifact, classPartAttributesArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact classPartArtifact_classPartMethodsArtifact 		= new RelationLinkArtifact(classPartArtifact, classPartMethodsArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact classPartArtifact_classPartNameArtifact 		= new RelationLinkArtifact(classPartArtifact, classPartNameArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact classArtifact_classPartArtifact 			= new RelationLinkArtifact(classArtifact, classPartArtifact, RelationKind.COMPOSITION);
	
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
