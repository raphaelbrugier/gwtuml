package com.objetdirect.gwt.umlapi.client.webinterface;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassDependencyArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationshipArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;

public class DemoPanel extends AbsolutePanel {

	public DemoPanel(int w, int h) {
		Log.info("Creating demodrawer");
		final UMLCanvas gc = new UMLCanvas(w, h);
		this.add(gc);		
		gc.setStylePrimaryName("canvas");
		this.setWidth("100%");
		this.setHeight("100%");

		w += 2; //Border Size
		h += 2; //Border Size	
		int shadowSize = 8;
		

		
		SimplePanel bottomShadow = new SimplePanel();
		bottomShadow.setPixelSize(w - shadowSize, shadowSize);
		bottomShadow.setStylePrimaryName("bottomShadow");
		this.add(bottomShadow, shadowSize, h);
		
		
		SimplePanel rightShadow = new SimplePanel();
		rightShadow.setPixelSize(shadowSize, h - shadowSize);
		rightShadow.setStylePrimaryName("rightShadow");
		this.add(rightShadow, w, shadowSize);
		
		SimplePanel bottomRightCornerShadow = new SimplePanel();
		bottomRightCornerShadow.setPixelSize(shadowSize, shadowSize);
		bottomRightCornerShadow.setStylePrimaryName("bottomRightCornerShadow");
		this.add(bottomRightCornerShadow, w, h);
		
		SimplePanel topRightCornerShadow = new SimplePanel();
		topRightCornerShadow.setPixelSize(shadowSize, shadowSize);
		topRightCornerShadow.setStylePrimaryName("topRightCornerShadow");
		this.add(topRightCornerShadow, w, 0);
		
		SimplePanel bottomLeftCornerShadow = new SimplePanel();
		bottomLeftCornerShadow.setPixelSize(shadowSize, shadowSize);
		bottomLeftCornerShadow.setStylePrimaryName("bottomLeftCornerShadow");
		this.add(bottomLeftCornerShadow, 0, h);
		
		
		ClassArtifact dataManagerClass = new ClassArtifact("DataManager");
		gc.add(dataManagerClass);
		dataManagerClass.setLocation(500, 100);
		
		ClassArtifact businessObjectClass = new ClassArtifact("BusinessObject");
		gc.add(businessObjectClass);
		businessObjectClass.setLocation(650, 100);
		
		ClassArtifact serializableClass = new ClassArtifact("Serializable");
		gc.add(serializableClass);
		serializableClass.setLocation(700, 200);

		ClassArtifact clientClass = new ClassArtifact("Client");
		gc.add(clientClass);
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
		
		clientClass.setLocation(300, 300);

		ClassDependencyArtifact clientDataManager = new ClassDependencyArtifact.Simple(
				clientClass, dataManagerClass);
		gc.add(clientDataManager);
		
		ClassDependencyArtifact clientBusinessObject = new ClassDependencyArtifact.Extension(
				clientClass, businessObjectClass);
		gc.add(clientBusinessObject);
		
		ClassDependencyArtifact clientSerializable = new ClassDependencyArtifact.Implementation(
				clientClass, serializableClass);
		gc.add(clientSerializable);
		
		ClassArtifact eventClass = new ClassArtifact("Event");
		gc.add(eventClass);
		eventClass.setLocation(250, 150);
		
		RelationshipArtifact relClientEvent = new RelationshipArtifact(
				clientClass, eventClass);
		gc.add(relClientEvent);
		relClientEvent.setName("client-event");
		relClientEvent.setRightArrow(true);
		relClientEvent.setLeftCardinality("1");
		relClientEvent.setRightCardinality("0..*");
		relClientEvent.setLeftRole("events");
		relClientEvent.setLeftConstraint("{ordered}");
		relClientEvent.setRightRole("client");

		ClassArtifact addressClass = new ClassArtifact("Address");
		gc.add(addressClass);
		addressClass.setLocation(50, 250);
		
		RelationshipArtifact relClientAddress = new RelationshipArtifact(
				clientClass, addressClass);
		gc.add(relClientAddress);
		relClientAddress.setRightArrow(true);
		relClientAddress.setLeftCardinality("1");
		relClientAddress.setLeftConstraint("{ordered}");
		relClientAddress.setRightCardinality("0..*");

		ClassArtifact productClass = new ClassArtifact("Product");
		gc.add(productClass);
		productClass.setLocation(50, 450);
		
		ClassArtifact paymentClass = new ClassArtifact("Payment");
		gc.add(paymentClass);
		paymentClass.setLocation(150, 550);
		
		RelationshipArtifact relClientProduct = new RelationshipArtifact(
				clientClass, productClass);
		gc.add(relClientProduct);
		relClientProduct.setName("client-product");
		relClientProduct.setRightArrow(true);
		relClientProduct.setLeftCardinality("1");
		relClientProduct.setRightCardinality("0..*");
		relClientProduct.setRelationClass(paymentClass);

		NoteArtifact note = new NoteArtifact("Ceci est une note concernant le client");
		gc.add(note);
		note.setLocation(400, 500);
		
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
