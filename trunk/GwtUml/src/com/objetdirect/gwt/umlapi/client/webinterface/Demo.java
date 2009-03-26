package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.ClassDependencyLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationshipLinkArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;

public class Demo extends AbsolutePanel {

	public Demo(UMLCanvas gc) {
		Log.info("Creating demo");

		ClassArtifact dataManagerClass = new ClassArtifact("DataManager");
			dataManagerClass.setLocation(500, 50);
		gc.add(dataManagerClass);
		
		ClassArtifact businessObjectClass = new ClassArtifact("BusinessObject");
			businessObjectClass.setLocation(650, 50);
		gc.add(businessObjectClass);
		
		ClassArtifact serializableClass = new ClassArtifact("Serializable");
			serializableClass.setLocation(700, 150);
		gc.add(serializableClass);

		ClassArtifact clientClass = new ClassArtifact("Client");
			clientClass.addAttribute(new Attribute("String", "firstName"));
			clientClass.addAttribute(new Attribute("String", "lastName"));
			List<Parameter> clientParameters = new ArrayList<Parameter>();
			clientParameters.add(new Parameter("String", "firstName"));
			clientParameters.add(new Parameter("String", "lastName"));
			clientClass.addMethod(new Method(null, "Client", clientParameters));
			List<Parameter> addProductParameters = new ArrayList<Parameter>();
			addProductParameters.add(new Parameter("Product", "product"));
			addProductParameters.add(new Parameter("Date", "when"));
			clientClass.addMethod(new Method("void", "addProduct", addProductParameters));
			clientClass.setLocation(300, 250);
		gc.add(clientClass);
		
		ClassDependencyLinkArtifact clientDataManager = new ClassDependencyLinkArtifact.Simple(
				clientClass, dataManagerClass);
		gc.add(clientDataManager);

		ClassDependencyLinkArtifact clientBusinessObject = new ClassDependencyLinkArtifact.Extension(
				clientClass, businessObjectClass);
		gc.add(clientBusinessObject);

		ClassDependencyLinkArtifact clientSerializable = new ClassDependencyLinkArtifact.Implementation(
				clientClass, serializableClass);
		gc.add(clientSerializable);

		ClassArtifact eventClass = new ClassArtifact("Event");		
			eventClass.setLocation(250, 100);
		gc.add(eventClass);
		
		RelationshipLinkArtifact relClientEvent = new RelationshipLinkArtifact(
				clientClass, eventClass);
			relClientEvent.setName("client-event");
			relClientEvent.setRightArrow(true);
			relClientEvent.setLeftCardinality("1");
			relClientEvent.setRightCardinality("0..*");
			relClientEvent.setLeftRole("events");
			relClientEvent.setLeftConstraint("{ordered}");
			relClientEvent.setRightRole("client");
		gc.add(relClientEvent);
		
		ClassArtifact addressClass = new ClassArtifact("Address");
			addressClass.setLocation(50, 200);
		gc.add(addressClass);
		
		RelationshipLinkArtifact relClientAddress = new RelationshipLinkArtifact(
				clientClass, addressClass);
			relClientAddress.setRightArrow(true);
			relClientAddress.setLeftCardinality("1");
			relClientAddress.setLeftConstraint("{ordered}");
			relClientAddress.setRightCardinality("0..*");
		gc.add(relClientAddress);
		
		ClassArtifact productClass = new ClassArtifact("Product");
			productClass.setLocation(50, 400);
		gc.add(productClass);
		

		ClassArtifact paymentClass = new ClassArtifact("Payment");
			paymentClass.setLocation(150, 450);
		gc.add(paymentClass);
		
		RelationshipLinkArtifact relClientProduct = new RelationshipLinkArtifact(
				clientClass, productClass);
			relClientProduct.setName("client-product");
			relClientProduct.setRightArrow(true);
			relClientProduct.setLeftCardinality("1");
			relClientProduct.setRightCardinality("0..*");
			relClientProduct.setRelationClass(paymentClass);
		gc.add(relClientProduct);
		
		NoteArtifact note = new NoteArtifact("Ceci est une note concernant le client");
			note.setLocation(400, 450);
		gc.add(note);

		NoteLinkArtifact noteClientLink = new NoteLinkArtifact(note,
				clientClass);
		gc.add(noteClientLink);

		NoteLinkArtifact notePaymentLink = new NoteLinkArtifact(note,
				paymentClass);
		gc.add(notePaymentLink);

		NoteLinkArtifact noteRelationshipLink = new NoteLinkArtifact(note,
				relClientProduct);
		gc.add(noteRelationshipLink);

		NoteLinkArtifact noteDependencyLink = new NoteLinkArtifact(note,
				clientDataManager);
		gc.add(noteDependencyLink);

		Log.info("Init demodrawer end");
	}
}
