package com.objetdirect.gwt.umlapi.client;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.tatami.client.gfx.GraphicObject;

public class ClassEditor {

	public void edit(ClassArtifact elem, GraphicObject item) {
		Object subPart = ((ClassArtifact)elem).getSubPart(item);
		if (subPart==null)
			return;
		else if (subPart==ClassArtifact.NAME)
			editName(elem);
		else if (subPart==ClassArtifact.NEW_ATTRIBUTE)
			editNewAttribute(elem);
		else if (subPart instanceof Attribute)
			editAttribute(elem, (Attribute)subPart);
		else if (subPart==ClassArtifact.NEW_METHOD)
			editNewMethod(elem);
		else if (subPart instanceof Method)
			editMethod(elem, (Method)subPart);
		else
			throw new UMLDrawerException("Invalid class subpart : "+subPart);
	}

	protected void editMethod(ClassArtifact elem, Method method) {
	}

	protected void editNewMethod(ClassArtifact elem) {
	}

	protected void editAttribute(ClassArtifact elem, Attribute attribute) {
	}

	protected void editNewAttribute(ClassArtifact elem) {
	}

	protected void editName(ClassArtifact elem) {
	}

}
