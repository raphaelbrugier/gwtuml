package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.Attribute;
import com.objetdirect.gwt.umlapi.client.Method;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyser;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationshipArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.RelationshipArtifact.RelationShipArtifactPart;

public class RelationshipEditor {

	public static final int FIELD_XMARGIN= 8;
	public static final int FIELD_YMARGIN= -4;
	public static final int FIELD_HEIGHT = 18;
	private RelationshipArtifact editedRelationship = null;
	
	public RelationshipEditor(RelationshipArtifact editedRelationship) {
		this.editedRelationship = editedRelationship;
	}

	public void editPart(RelationShipArtifactPart subpart) {
		this.subpart = subpart;
		editField = getEditField(editedRelationship.getStringForPart(subpart), editedRelationship.getWidth(subpart));
		editedRelationship.getCanvas().add(editField, editedRelationship.getTextX(subpart), editedRelationship.getTextY(subpart) - editedRelationship.getHeight(subpart)/2);
		editField.selectAll();
		editField.setFocus(true);
	}

	protected void validate() {
		if (validationInProcess)
			return;
		try {			
			validationInProcess = true;
			switch(subpart) {
			case NAME:
				editedRelationship.setName(editField.getText());
				break;
			case LEFT_CARDINALITY:
				editedRelationship.setLeftCardinality(editField.getText());
				break;
			case RIGHT_CARDINALITY:
				editedRelationship.setRightCardinality(editField.getText());
				break;
			case LEFT_ROLE:
				editedRelationship.setLeftRole(editField.getText());
				break;
			case RIGHT_ROLE:
				editedRelationship.setRightRole(editField.getText());
				break;
			case LEFT_CONSTRAINT:
				editedRelationship.setLeftConstraint(editField.getText());
				break;
			case RIGHT_CONSTRAINT:
				editedRelationship.setRightConstraint(editField.getText());
				break;
			}			
			editedRelationship.getCanvas().remove(editField);
			editField = null;
		}
		finally {
			validationInProcess = false;
		}
	}
	
	protected void cancel() {
		validationInProcess = true;
		try {
			editedRelationship.getCanvas().remove(editField);
			editField = null;
		}
		finally {
			validationInProcess = false;
		}		
	}
	
	
	TextBox getEditField(String value, int width) {
		TextBox editField = new TextBox();
		editField.setText(value);
		DOM.setStyleAttribute(editField.getElement(), "border", "1px solid blue");
		DOM.setStyleAttribute(editField.getElement(), "height", FIELD_HEIGHT+"px");
		DOM.setStyleAttribute(editField.getElement(), "width", (width-FIELD_XMARGIN*2)+"px");
		prepareEditField(editField);
		return editField;
	}

	void prepareEditField(TextBox editField) {
		FocusListener focusLst = new FocusListener() {
			public void onFocus(Widget sender) {
			}
			public void onLostFocus(Widget sender) {
				validate();
			}
		};
		KeyboardListener keybLst = new KeyboardListener() {
			public void onKeyDown(Widget sender, char keyCode, int modifiers) {
			}
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
			}
			public void onKeyUp(Widget sender, char keyCode, int modifiers) {
				if (keyCode==KEY_ENTER)
					validate();

				else if (keyCode==KEY_ESCAPE)
					cancel();
			}
		};
		editField.addFocusListener(focusLst);
		editField.addKeyboardListener(keybLst);
	}
	
	boolean validationInProcess = false;
	TextBox editField = null;
	RelationShipArtifactPart subpart;



	
}
