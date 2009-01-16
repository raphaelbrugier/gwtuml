package com.objetdirect.gwt.umlapi.client.webinterface;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.objetdirect.gwt.umlapi.client.Attribute;
import com.objetdirect.gwt.umlapi.client.Method;
import com.objetdirect.gwt.umlapi.client.Parameter;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassDependencyArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationshipArtifact;
import com.objetdirect.gwt.umlapi.client.engine.ClassDiagramAnimator;
import com.objetdirect.gwt.umlapi.client.engine.StandardClassEditor;

public class DemoPanel extends HorizontalPanel{

	public DemoPanel() {
		Log.info("Creating demodrawer");

		final UMLCanvas gc = new UMLCanvas(1000, 600);
		
		ClassArtifact dataManagerClass = new ClassArtifact("DataManager");
		dataManagerClass.setLocation(500, 100);
		ClassArtifact businessObjectClass = new ClassArtifact("BusinessObject");
		businessObjectClass.setLocation(650, 100);
		ClassArtifact serializableClass = new ClassArtifact("Serializable");
		serializableClass.setLocation(700, 200);

		ClassArtifact clientClass = new ClassArtifact("Client");
		clientClass.addAttribute(new Attribute("String", "firstName"));
		clientClass.addAttribute(new Attribute("String", "lastName"));
		clientClass.addMethod(new Method(null, "Client", new Parameter[] {
				new Parameter("String", "firstName"),
				new Parameter("String", "lastName")}));
		clientClass.addMethod(new Method("void", "addProduct", new Parameter[] {
				new Parameter("Product", "product"),
				new Parameter("Date", "when")}));
		clientClass.setLocation(300, 300);

		ClassDependencyArtifact clientDataManager = new ClassDependencyArtifact.Simple(clientClass, dataManagerClass);	
		ClassDependencyArtifact clientBusinessObject = new ClassDependencyArtifact.Extension(clientClass, businessObjectClass);
		ClassDependencyArtifact clientSerializable = new ClassDependencyArtifact.Implementation(clientClass, serializableClass);

		ClassArtifact eventClass = new ClassArtifact("Event");
		eventClass.setLocation(250, 150);
		RelationshipArtifact relClientEvent = new RelationshipArtifact(clientClass, eventClass);
		relClientEvent.setName("client-event");
		relClientEvent.setRightArrow(true);
		relClientEvent.setLeftCardinality("1");
		relClientEvent.setRightCardinality("0..*");
		relClientEvent.setLeftRole("events");
		relClientEvent.setLeftConstraint("{ordered}");
		relClientEvent.setRightRole("client");

		ClassArtifact addressClass = new ClassArtifact("Address");
		addressClass.setLocation(50, 250);
		RelationshipArtifact relClientAddress = new RelationshipArtifact(clientClass, addressClass);
		relClientAddress.setRightArrow(true);
		relClientAddress.setLeftCardinality("1");
		relClientAddress.setLeftConstraint("{ordered}");
		relClientAddress.setRightCardinality("0..*");

		ClassArtifact productClass = new ClassArtifact("Product");
		productClass.setLocation(50, 450);		
		ClassArtifact paymentClass = new ClassArtifact("Payment");
		paymentClass.setLocation(150, 550);		
		RelationshipArtifact relClientProduct = new RelationshipArtifact(clientClass, productClass);
		relClientProduct.setName("client-product");
		relClientProduct.setRightArrow(true);
		relClientProduct.setLeftCardinality("1");
		relClientProduct.setRightCardinality("0..*");
		relClientProduct.setRelationClass(paymentClass);

		NoteArtifact note = new NoteArtifact();
		note.setContent("Ceci est une note\nconcernant le client");
		note.setLocation(400, 500);
		NoteLinkArtifact noteClientLink = new NoteLinkArtifact(note, clientClass);
		NoteLinkArtifact notePaymentLink = new NoteLinkArtifact(note, paymentClass);
		NoteLinkArtifact noteRelationshipLink = new NoteLinkArtifact(note, relClientProduct);
		NoteLinkArtifact noteDependencyLink = new NoteLinkArtifact(note, clientDataManager);

		gc.add(clientClass);
		gc.add(dataManagerClass);
		gc.add(clientDataManager);
		gc.add(businessObjectClass);
		gc.add(clientBusinessObject);
		gc.add(serializableClass);
		gc.add(clientSerializable);
		gc.add(eventClass);
		gc.add(relClientEvent);
		gc.add(addressClass);
		gc.add(relClientAddress);
		gc.add(productClass);
		gc.add(paymentClass);
		gc.add(relClientProduct);
		gc.add(note);
		gc.add(noteClientLink);
		gc.add(notePaymentLink);
		gc.add(noteRelationshipLink);
		gc.add(noteDependencyLink);
		DOM.setStyleAttribute(gc.getElement(), "border", "2px solid black");

		gc.addUMLElementListener(new ClassDiagramAnimator().
				setClassEditor(new StandardClassEditor())
		);

		Log.info("Init demodrawer end");
		this.add(gc);

	}
}
