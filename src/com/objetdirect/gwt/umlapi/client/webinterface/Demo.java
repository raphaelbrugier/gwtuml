package com.objetdirect.gwt.umlapi.client.webinterface;
import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.NoteLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.RelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.AssociationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.DependencyLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.GeneralizationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.links.classlinks.RealizationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Visibility;
/**
 * @author florian
 *
 */
public class Demo extends AbsolutePanel {
	public Demo(UMLCanvas gc) {
		Log.trace("Creating demo");
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
			clientClass.addAttribute(new Attribute(Visibility.PRIVATE, "String", "firstName"));
			clientClass.addAttribute(new Attribute(Visibility.PROTECTED, "String", "lastName"));
			List<Parameter> clientParameters = new ArrayList<Parameter>();
			clientParameters.add(new Parameter("String", "firstName"));
			clientParameters.add(new Parameter("String", "lastName"));
			clientClass.addMethod(new Method(Visibility.PUBLIC, null, "Client", clientParameters));
			List<Parameter> addProductParameters = new ArrayList<Parameter>();
			addProductParameters.add(new Parameter("Product", "product"));
			addProductParameters.add(new Parameter("Date", "when"));
			clientClass.addMethod(new Method(Visibility.PACKAGE,"void", "addProduct", addProductParameters));
			clientClass.setLocation(300, 250);
		gc.add(clientClass);
		
		RelationLinkArtifact clientDataManager = new DependencyLinkArtifact(
				clientClass, dataManagerClass);
		gc.add(clientDataManager);
		RelationLinkArtifact clientBusinessObject = new GeneralizationLinkArtifact(
				clientClass, businessObjectClass);
		gc.add(clientBusinessObject);
		RelationLinkArtifact clientSerializable = new RealizationLinkArtifact(
				clientClass, serializableClass);
		gc.add(clientSerializable);
		ClassArtifact eventClass = new ClassArtifact("Event");		
			eventClass.setLocation(250, 100);
		gc.add(eventClass);
		
		RelationLinkArtifact relClientEvent = new AssociationLinkArtifact(
				clientClass, eventClass);
			relClientEvent.setName("client-event");
			relClientEvent.setLeftCardinality("1");
			relClientEvent.setRightCardinality("0..*");
			relClientEvent.setLeftRole("events");
			relClientEvent.setLeftConstraint("{ordered}");
			relClientEvent.setRightRole("client");
		gc.add(relClientEvent);
		
		ClassArtifact addressClass = new ClassArtifact("Address");
			addressClass.setLocation(50, 200);
		gc.add(addressClass);
		
		RelationLinkArtifact relClientAddress = new AssociationLinkArtifact(
				clientClass, addressClass);
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
		
		RelationLinkArtifact relClientProduct = new AssociationLinkArtifact(
				clientClass, productClass);
			relClientProduct.setName("client-product");
			relClientProduct.setLeftCardinality("1");
			relClientProduct.setRightCardinality("0..*");
		gc.add(relClientProduct);
		
		ClassRelationLinkArtifact relClassClientProductPayment = new ClassRelationLinkArtifact(paymentClass, relClientProduct);
	    gc.add(relClassClientProductPayment);
		
		NoteArtifact note = new NoteArtifact("Ceci est une note\nconcernant le client");
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
		Log.trace("Init demodrawer end");
	}
}
