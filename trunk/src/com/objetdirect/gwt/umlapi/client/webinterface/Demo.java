package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkClassRelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkAssociationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkDependencyArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkGeneralizationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationLinkRealizationArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Visibility;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * 
 */
public class Demo extends AbsolutePanel {
    public Demo(final UMLCanvas gc) {
	Log.trace("Creating demo");
	final ClassArtifact dataManagerClass = new ClassArtifact("DataManager");
	dataManagerClass.setLocation(new Point(500, 50));
	gc.add(dataManagerClass);

	final ClassArtifact businessObjectClass = new ClassArtifact(
		"BusinessObject");
	businessObjectClass.setLocation(new Point(650, 50));
	gc.add(businessObjectClass);

	final ClassArtifact serializableClass = new ClassArtifact(
		"Serializable");
	serializableClass.setLocation(new Point(700, 150));
	gc.add(serializableClass);
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
	gc.add(clientClass);

	final RelationLinkArtifact clientDataManager = new RelationLinkDependencyArtifact(
		clientClass, dataManagerClass);
	gc.add(clientDataManager);
	final RelationLinkArtifact clientBusinessObject = new RelationLinkGeneralizationArtifact(
		clientClass, businessObjectClass);
	gc.add(clientBusinessObject);
	final RelationLinkArtifact clientSerializable = new RelationLinkRealizationArtifact(
		clientClass, serializableClass);
	gc.add(clientSerializable);
	final ClassArtifact eventClass = new ClassArtifact("Event");
	eventClass.setLocation(new Point(250, 100));
	gc.add(eventClass);

	final RelationLinkArtifact relClientEvent = new RelationLinkAssociationArtifact(
		clientClass, eventClass);
	relClientEvent.setName("client-event");
	relClientEvent.setLeftCardinality("1");
	relClientEvent.setRightCardinality("0..*");
	relClientEvent.setLeftRole("events");
	relClientEvent.setLeftConstraint("{ordered}");
	relClientEvent.setRightRole("client");
	gc.add(relClientEvent);

	final ClassArtifact addressClass = new ClassArtifact("Address");
	addressClass.setLocation(new Point(50, 200));
	gc.add(addressClass);

	final RelationLinkArtifact relClientAddress = new RelationLinkAssociationArtifact(
		clientClass, addressClass);
	relClientAddress.setLeftCardinality("1");
	relClientAddress.setLeftConstraint("{ordered}");
	relClientAddress.setRightCardinality("0..*");
	gc.add(relClientAddress);

	final ClassArtifact productClass = new ClassArtifact("Product");
	productClass.setLocation(new Point(50, 400));
	gc.add(productClass);

	final ClassArtifact paymentClass = new ClassArtifact("Payment");
	paymentClass.setLocation(new Point(150, 450));
	gc.add(paymentClass);

	final RelationLinkArtifact relClientProduct = new RelationLinkAssociationArtifact(
		clientClass, productClass);
	relClientProduct.setName("client-product");
	relClientProduct.setLeftCardinality("1");
	relClientProduct.setRightCardinality("0..*");
	gc.add(relClientProduct);

	final LinkClassRelationArtifact relClassClientProductPayment = new LinkClassRelationArtifact(
		paymentClass, relClientProduct);
	gc.add(relClassClientProductPayment);

	final NoteArtifact note = new NoteArtifact(
		"Ceci est une note\nconcernant le client");
	note.setLocation(new Point(400, 450));
	gc.add(note);
	final LinkNoteArtifact noteClientLink = new LinkNoteArtifact(note,
		clientClass);
	gc.add(noteClientLink);
	final LinkNoteArtifact notePaymentLink = new LinkNoteArtifact(note,
		paymentClass);
	gc.add(notePaymentLink);
	final LinkNoteArtifact noteRelationshipLink = new LinkNoteArtifact(
		note, relClientProduct);
	gc.add(noteRelationshipLink);
	final LinkNoteArtifact noteDependencyLink = new LinkNoteArtifact(note,
		clientDataManager);
	gc.add(noteDependencyLink);
	Log.trace("Init demodrawer end");
    }
}
