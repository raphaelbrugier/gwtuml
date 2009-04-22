package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Relation.RelationKind;

/**
 * This class is an exemple of a static contruction of a uml diagram
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class Demo extends AbsolutePanel {

    private final static int FIRST_ROW = 60;
    private final static int SECOND_ROW = 250;
    private final static int THIRD_ROW = 450;
    private final static int FOURTH_ROW = 650;
    private final static int MIDDLE_THIRD_FOURTH_ROW = 550;
    
    /**
     * Constructor of the demo, it creates all the uml artifacts and positions it 
     *
     * @param canvas The {@link UMLCanvas} where to add the demo uml artifacts 
     */
    public Demo(final UMLCanvas canvas) {
	Log.trace("Creating demo");
	final ClassArtifact uMLCanvas 			= new ClassArtifact("UMLCanvas");
	final ClassArtifact uMLEventListener		= new ClassArtifact("UMLEventListener", "«Interface»");
	final ClassArtifact uMLArtifact 		= new ClassArtifact("UMLArtifact", "«Abstract»");
	final ClassArtifact boxArtifact 		= new ClassArtifact("BoxArtifact", "«Abstract»");
	final ClassArtifact classArtifact 		= new ClassArtifact("ClassArtifact");
	final ClassArtifact classPartArtifact 		= new ClassArtifact("ClassPartArtifact", "«Abstract»");
	final ClassArtifact classPartAttributesArtifact	= new ClassArtifact("ClassPartAttributesArtifact");
	final ClassArtifact classPartMethodsArtifact 	= new ClassArtifact("ClassPartMethodsArtifact");
	final ClassArtifact classPartNameArtifact 	= new ClassArtifact("ClassPartNameArtifact");
	final ClassArtifact noteArtifact		= new ClassArtifact("NoteArtifact");
	final ClassArtifact linkArtifact 		= new ClassArtifact("LinkArtifact", "«Abstract»");
	final ClassArtifact linkClassRelationArtifact	= new ClassArtifact("LinkClassRelationArtifact");
	final ClassArtifact linkNoteArtifact		= new ClassArtifact("LinkNoteArtifact");
	final ClassArtifact relationLinkArtifact	= new ClassArtifact("RelationLinkArtifact");
	
	uMLCanvas.			setLocation(new Point(350, FIRST_ROW));
	uMLEventListener.		setLocation(new Point(50, FIRST_ROW));
	uMLArtifact.			setLocation(new Point(600, FIRST_ROW-10));
	boxArtifact.			setLocation(new Point(350, SECOND_ROW));
	linkArtifact.			setLocation(new Point(800, SECOND_ROW));
	classArtifact.			setLocation(new Point(100, THIRD_ROW));
	noteArtifact.			setLocation(new Point(500, THIRD_ROW));
	linkClassRelationArtifact.	setLocation(new Point(750, MIDDLE_THIRD_FOURTH_ROW));
	linkNoteArtifact.		setLocation(new Point(700, THIRD_ROW));
	relationLinkArtifact.		setLocation(new Point(900, THIRD_ROW));
    	classPartArtifact.		setLocation(new Point(300, THIRD_ROW));
	classPartAttributesArtifact.	setLocation(new Point(20, FOURTH_ROW));
	classPartMethodsArtifact.	setLocation(new Point(250, FOURTH_ROW));
	classPartNameArtifact.		setLocation(new Point(500, FOURTH_ROW));
	
	canvas.add(uMLCanvas);
	canvas.add(uMLEventListener);
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
	
	final RelationLinkArtifact uMLCanvas_uMLArtifact 				= new RelationLinkArtifact(uMLCanvas, uMLArtifact, RelationKind.COMPOSITION);
	final RelationLinkArtifact uMLArtifact_boxArtifact 				= new RelationLinkArtifact(uMLArtifact, boxArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact uMLArtifact_linkArtifact 				= new RelationLinkArtifact(uMLArtifact, linkArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact boxArtifact_classArtifact				= new RelationLinkArtifact(boxArtifact, classArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact boxArtifact_classPartArtifact 			= new RelationLinkArtifact(boxArtifact, classPartArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact boxArtifact_noteArtifact 				= new RelationLinkArtifact(boxArtifact, noteArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact linkArtifact_relationLinkArtifact 			= new RelationLinkArtifact(linkArtifact, relationLinkArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact linkArtifact_linkClassRelationArtifact 		= new RelationLinkArtifact(linkArtifact, linkClassRelationArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact linkArtifact_linkNoteArtifact 			= new RelationLinkArtifact(linkArtifact, linkNoteArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact classPartArtifact_classPartAttributesArtifact	= new RelationLinkArtifact(classPartArtifact, classPartAttributesArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact classPartArtifact_classPartMethodsArtifact 		= new RelationLinkArtifact(classPartArtifact, classPartMethodsArtifact, RelationKind.GENERALIZATION);
	final RelationLinkArtifact classPartArtifact_classPartNameArtifact 		= new RelationLinkArtifact(classPartArtifact, classPartNameArtifact, RelationKind.GENERALIZATION);
	
	canvas.add(uMLCanvas_uMLArtifact);
	canvas.add(uMLArtifact_boxArtifact);
	canvas.add(uMLArtifact_linkArtifact);
	canvas.add(boxArtifact_classArtifact);
	canvas.add(boxArtifact_classPartArtifact);
	canvas.add(boxArtifact_noteArtifact);
	canvas.add(linkArtifact_relationLinkArtifact);
	canvas.add(linkArtifact_linkClassRelationArtifact);
	canvas.add(linkArtifact_linkNoteArtifact);
	canvas.add(classPartArtifact_classPartAttributesArtifact);
	canvas.add(classPartArtifact_classPartMethodsArtifact);
	canvas.add(classPartArtifact_classPartNameArtifact);
	
	/*
	final ClassArtifact dataManagerClass = new ClassArtifact("DataManager");
	dataManagerClass.setLocation(new Point(500, 50));
	uMLCanvas.add(dataManagerClass);

	final ClassArtifact businessObjectClass = new ClassArtifact(
		"BusinessObject");
	businessObjectClass.setLocation(new Point(650, 50));
	uMLCanvas.add(businessObjectClass);

	final ClassArtifact serializableClass = new ClassArtifact(
		"Serializable");
	serializableClass.setLocation(new Point(700, 150));
	uMLCanvas.add(serializableClass);
	final ClassArtifact clientClass = new ClassArtifact("Client");
	clientClass.addAttribute(new Attribute(Visibility.PRIVATE, "String",
		"firstName"));
	clientClass.addAttribute(new Attribute(Visibility.PROTECTED, "String",
		"lastName"));
	final List<Parameter> clientParameters = new ArrayList<Parameter>();
	clientParameters.add(new Parameter("String", "firstName"));
	clientParameters.add(new Parameter("String", "lastName"));
	clientClass.addMethod(new Method(Visibility.PUBLIC, null, "Client",
		clientParameters));
	final List<Parameter> addProductParameters = new ArrayList<Parameter>();
	addProductParameters.add(new Parameter("Product", "product"));
	addProductParameters.add(new Parameter("Date", "when"));
	clientClass.addMethod(new Method(Visibility.PACKAGE, "void",
		"addProduct", addProductParameters));
	clientClass.setLocation(new Point(300, 250));
	uMLCanvas.add(clientClass);

	final RelationLinkArtifact clientDataManager = new RelationLinkDependencyArtifact(
		clientClass, dataManagerClass);
	uMLCanvas.add(clientDataManager);
	final RelationLinkArtifact clientBusinessObject = new RelationLinkGeneralizationArtifact(
		clientClass, businessObjectClass);
	uMLCanvas.add(clientBusinessObject);
	final RelationLinkArtifact clientSerializable = new RelationLinkArtifact(
		clientClass, serializableClass);
	uMLCanvas.add(clientSerializable);
	final ClassArtifact eventClass = new ClassArtifact("Event");
	eventClass.setLocation(new Point(250, 100));
	uMLCanvas.add(eventClass);

	final RelationLinkArtifact relClientEvent = new RelationLinkArtifact(
		clientClass, eventClass, RelationKind.ASSOCIATION);
	relClientEvent.setName("client-event");
	relClientEvent.setLeftCardinality("1");
	relClientEvent.setRightCardinality("0..*");
	relClientEvent.setLeftRole("events");
	relClientEvent.setLeftConstraint("{ordered}");
	relClientEvent.setRightRole("client");
	uMLCanvas.add(relClientEvent);

	final ClassArtifact addressClass = new ClassArtifact("Address");
	addressClass.setLocation(new Point(50, 200));
	uMLCanvas.add(addressClass);

	final RelationLinkArtifact relClientAddress = new RelationLinkAssociationArtifact(
		clientClass, addressClass);
	relClientAddress.setLeftCardinality("1");
	relClientAddress.setLeftConstraint("{ordered}");
	relClientAddress.setRightCardinality("0..*");
	uMLCanvas.add(relClientAddress);

	final ClassArtifact productClass = new ClassArtifact("Product");
	productClass.setLocation(new Point(50, 400));
	uMLCanvas.add(productClass);

	final ClassArtifact paymentClass = new ClassArtifact("Payment");
	paymentClass.setLocation(new Point(150, 450));
	uMLCanvas.add(paymentClass);

	final RelationLinkArtifact relClientProduct = new RelationLinkAssociationArtifact(
		clientClass, productClass);
	relClientProduct.setName("client-product");
	relClientProduct.setLeftCardinality("1");
	relClientProduct.setRightCardinality("0..*");
	uMLCanvas.add(relClientProduct);

	final LinkClassRelationArtifact relClassClientProductPayment = new LinkClassRelationArtifact(
		paymentClass, relClientProduct);
	uMLCanvas.add(relClassClientProductPayment);

	final NoteArtifact note = new NoteArtifact(
		"Ceci est une note\nconcernant le client");
	note.setLocation(new Point(400, 450));
	uMLCanvas.add(note);
	final LinkNoteArtifact noteClientLink = new LinkNoteArtifact(note,
		clientClass);
	uMLCanvas.add(noteClientLink);
	final LinkNoteArtifact notePaymentLink = new LinkNoteArtifact(note,
		paymentClass);
	uMLCanvas.add(notePaymentLink);
	final LinkNoteArtifact noteRelationshipLink = new LinkNoteArtifact(
		note, relClientProduct);
	uMLCanvas.add(noteRelationshipLink);
	final LinkNoteArtifact noteDependencyLink = new LinkNoteArtifact(note,
		clientDataManager);
	uMLCanvas.add(noteDependencyLink);
	Log.trace("Init demodrawer end");*/
    }
}
