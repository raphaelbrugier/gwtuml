package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyser;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact.ClassArtifactPart;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;

public class ClassEditor {

	public static final int FIELD_HEIGHT = 18;
	public static final int FIELD_XMARGIN = 8;
	public static final int FIELD_YMARGIN = -4;
	TextBox editField = null;

	ClassArtifactPart subPart;

	boolean validationInProcess = false;

	private ClassArtifact editedClass = null;

	public ClassEditor(ClassArtifact editedClass) {
		this.editedClass = editedClass;
	}

	public void editAttribute(Attribute attribute) {
		editField = getEditField(attribute.toString(), editedClass.getWidth());
		editedClass.getCanvas().add(
				editField,
				editedClass.getX() + FIELD_XMARGIN,
				editedClass.getY() + editedClass.getAttributeY(attribute)
						+ FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}

	public void editMethod(Method method) {
		editField = getEditField(method.toString(), editedClass.getWidth());
		editedClass.getCanvas().add(
				editField,
				editedClass.getX() + FIELD_XMARGIN,
				editedClass.getY() + editedClass.getMethodY(method)
						+ FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}

	public void editName() {
		editField = getEditField(editedClass.getClassName(), editedClass
				.getWidth());
		editedClass.getCanvas().add(editField,
				editedClass.getX() + FIELD_XMARGIN,
				editedClass.getY() + editedClass.getNameY() + FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}

	public void editNewAttribute() {
		Attribute attr = new Attribute("type", "name");
		attr.setValidated(false);
		editedClass.addAttribute(attr);
		editAttribute(attr);
	}

	public void editNewMethod() {
		Method method = new Method("type", "name", null);
		method.setValidated(false);
		editedClass.addMethod(method);
		editMethod(method);
	}

	public void setSubPart(ClassArtifactPart subPart) {
		this.subPart = subPart;
	}

	TextBox getEditField(String value, int width) {
		TextBox editField = new TextBox();
		editField.setText(value);
		DOM.setStyleAttribute(editField.getElement(), "border",
				"1px solid blue");
		DOM.setStyleAttribute(editField.getElement(), "height", FIELD_HEIGHT
				+ "px");
		DOM.setStyleAttribute(editField.getElement(), "width",
				(width - FIELD_XMARGIN * 2) + "px");
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
				if (keyCode == KEY_ENTER)
					goNextLine();
				if (keyCode == KEY_PAGEUP)
					goPrevBox();
				if (keyCode == KEY_PAGEDOWN)
					goNextBox();
				if (keyCode == KEY_UP) {
					if ((modifiers & MODIFIER_SHIFT) != 0)
						moveUp();
					else
						goUp();
				}
				if (keyCode == KEY_DOWN) {
					if ((modifiers & MODIFIER_SHIFT) != 0)
						moveDown();
					else
						goDown();
				} else if (keyCode == KEY_ESCAPE)
					cancel();
			}
		};
		editField.addFocusListener(focusLst);
		editField.addKeyboardListener(keybLst);
	}

	void removeAttribute(ClassArtifact editedClass, int attrSlot) {
		editedClass.removeAttribute(attrSlot);
	}

	void removeMethod(ClassArtifact editedClass, int methodSlot) {
		editedClass.removeMethod(methodSlot);
	}

	void replaceAttribute(ClassArtifact editedClass, int attrSlot, String text) {
		LexicalAnalyser lex = new LexicalAnalyser(text);
		try {
			String type = null;
			String name = null;
			LexicalAnalyser.Token tk = lex.getToken();
			if (tk.getType() != LexicalAnalyser.IDENTIFIER)
				throw new UMLDrawerException(
						"invalid format : must match 'identifier:type'");
			name = tk.getContent();
			tk = lex.getToken();
			if (tk != null) {
				if (tk.getType() != LexicalAnalyser.SIGN
						|| !tk.getContent().equals(":"))
					throw new UMLDrawerException(
							"invalid format : must match 'identifier:type'");
				tk = lex.getToken();
				if (tk == null || tk.getType() != LexicalAnalyser.IDENTIFIER)
					throw new UMLDrawerException(
							"invalid format : must match 'identifier:type'");
				type = tk.getContent();
			}
			editedClass.setAttribute(attrSlot, new Attribute(type, name));
			editedClass.setAttributeValidated(attrSlot);
		} catch (UMLDrawerException e) {
			Window.alert(e.getMessage());
		}
	}

	void replaceMethod(ClassArtifact editedClass, int methodSlot, String text) {
		LexicalAnalyser lex = new LexicalAnalyser(text);
		try {
			MethodSyntaxAnalyser ma = new MethodSyntaxAnalyser();
			ma.process(lex, null);
			Method newMethod = ma.getMethod();
			editedClass.setMethod(methodSlot, newMethod);
			editedClass.setMethodValidated(methodSlot);
		} catch (UMLDrawerException e) {
			Window.alert(e.getMessage());
		}
	}

	void updateAttribute(ClassArtifact editedClass, int attrSlot, String text) {
		if (text.trim().length() == 0)
			removeAttribute(editedClass, attrSlot);
		else
			replaceAttribute(editedClass, attrSlot, text);
	}

	void updateMethod(ClassArtifact editedClass, int methodSlot, String text) {
		if (text.trim().length() == 0)
			removeMethod(editedClass, methodSlot);
		else
			replaceMethod(editedClass, methodSlot, text);
	}

	protected void cancel() {
		validationInProcess = true;
		try {

			switch (subPart) {
			case ATTRIBUTE:

				if (!editedClass.isAttributeValidated(subPart.getSlotIndex()))
					removeAttribute(editedClass, subPart.getSlotIndex());
				break;
			case METHOD:
				if (!editedClass.isMethodValidated(subPart.getSlotIndex()))
					removeMethod(editedClass, subPart.getSlotIndex());
				break;
			}
			editedClass.getCanvas().remove(editField);
			editField = null;
		} finally {
			validationInProcess = false;
		}
	}

	protected void goDown() {
		switch (subPart) {
		case NAME:
			validate();
			if (editedClass.getAttributes().size() > 0)
				editAttribute(editedClass.getAttributes().get(0));
			break;
		case ATTRIBUTE:
			Attribute nextAttr = null;
			if (editedClass.getAttributes().size() > subPart.getSlotIndex() + 1)
				nextAttr = editedClass.getAttributes().get(
						subPart.getSlotIndex() + 1);
			if (nextAttr != null) {
				validate();
				editAttribute(nextAttr);
			} else if (editedClass.getMethods().size() > 0) {
				validate();
				editMethod(editedClass.getMethods().get(0));
			}
			break;
		case METHOD:
			Method nextMethod = null;
			if (editedClass.getMethods().size() > subPart.getSlotIndex() + 1)
				nextMethod = editedClass.getMethods().get(
						subPart.getSlotIndex() + 1);
			if (nextMethod != null) {
				validate();
				editMethod(nextMethod);
			}
			break;
		}
	}

	protected void goNextBox() {
		switch (subPart) {
		case NAME:
			validate();
			if (editedClass.getAttributes().size() > 0)
				editAttribute(editedClass.getAttributes().get(0));
			else
				editNewAttribute();
			break;
		case ATTRIBUTE:
			validate();
			if (editedClass.getMethods().size() > 0)
				editMethod(editedClass.getMethods().get(0));
			else
				editNewMethod();
			break;
		}
	}

	protected void goNextLine() {
		switch (subPart) {
		case NAME:
			validate();
			if (editedClass.getAttributes().size() > 0)
				editAttribute(editedClass.getAttributes().get(0));
			break;
		case ATTRIBUTE:
			Attribute nextAttr = null;
			if (editedClass.getAttributes().size() > +1)
				nextAttr = editedClass.getAttributes().get(
						subPart.getSlotIndex() + 1);
			validate();
			if (nextAttr != null)
				editAttribute(nextAttr);
			else
				editNewAttribute();
			break;
		case METHOD:
			Method nextMethod = null;
			if (editedClass.getMethods().size() > subPart.getSlotIndex() + 1)
				nextMethod = editedClass.getMethods().get(
						subPart.getSlotIndex() + 1);
			validate();
			if (nextMethod != null)
				editMethod(nextMethod);
			else
				editNewMethod();
			break;
		}
	}

	protected void goPrevBox() {
		switch (subPart) {
		case ATTRIBUTE:
			validate();
			editName();
			break;
		case METHOD:
			validate();
			if (editedClass.getAttributes().size() > 0)
				editAttribute(editedClass.getAttributes().get(0));
			else
				editNewAttribute();
			break;
		}
	}

	protected void goUp() {
		switch (subPart) {
		case ATTRIBUTE:
			Attribute prevAttr = null;
			if (subPart.getSlotIndex() > 0)
				prevAttr = editedClass.getAttributes().get(
						subPart.getSlotIndex() - 1);
			validate();
			if (prevAttr != null)
				editAttribute(prevAttr);
			else
				editName();
			break;

		case METHOD:
			Method prevMethod = null;
			if (subPart.getSlotIndex() > 0)
				prevMethod = editedClass.getMethods().get(
						subPart.getSlotIndex() - 1);
			validate();
			if (prevMethod != null)
				editMethod(prevMethod);
			else if (editedClass.getAttributes().size() > 0) {
				Attribute attr = editedClass.getAttributes().get(
						editedClass.getAttributes().size() - 1);
				editAttribute(attr);
			} else
				editName();
			break;
		}
	}

	protected void moveDown() {
		switch (subPart) {
		case ATTRIBUTE:
			if (subPart.getSlotIndex() < editedClass.getAttributes().size() - 1) {
				editedClass.exchangeAttribute(subPart.getSlotIndex(), subPart
						.getSlotIndex() + 1);
				int y = editedClass.getAttributeY(subPart.getSlotIndex());
				editedClass.getCanvas().setWidgetPosition(editField,
						editedClass.getX() + FIELD_XMARGIN,
						editedClass.getY() + y + FIELD_YMARGIN);
			}
			break;
		case METHOD:
			if (subPart.getSlotIndex() < editedClass.getAttributes().size() - 1) {
				editedClass.exchangeMethod(subPart.getSlotIndex(), subPart
						.getSlotIndex() + 1);
				int y = editedClass.getMethodY(subPart.getSlotIndex());
				editedClass.getCanvas().setWidgetPosition(editField,
						editedClass.getX() + FIELD_XMARGIN,
						editedClass.getY() + y + FIELD_YMARGIN);
			}
			break;
		}
	}
	protected void moveUp() {
		switch (subPart) {
		case ATTRIBUTE:
			if (subPart.getSlotIndex() > 0) {
				editedClass.exchangeAttribute(subPart.getSlotIndex(), subPart
						.getSlotIndex() - 1);
				int y = editedClass.getAttributeY(subPart.getSlotIndex());
				editedClass.getCanvas().setWidgetPosition(editField,
						editedClass.getX() + FIELD_XMARGIN,
						editedClass.getY() + y + FIELD_YMARGIN);
			}

			break;
		case METHOD:
			if (subPart.getSlotIndex() > 0) {
				editedClass.exchangeMethod(subPart.getSlotIndex(), subPart
						.getSlotIndex() - 1);
				int y = editedClass.getMethodY(subPart.getSlotIndex());
				editedClass.getCanvas().setWidgetPosition(editField,
						editedClass.getX() + FIELD_XMARGIN,
						editedClass.getY() + y + FIELD_YMARGIN);
			}
			break;
		}
	}
	protected void validate() {
		if (validationInProcess)
			return;
		try {
			validationInProcess = true;
			switch (subPart) {
			case NAME:
				editedClass.setClassName(editField.getText());
				break;
			case ATTRIBUTE:
				updateAttribute(editedClass, subPart.getSlotIndex(), editField
						.getText());
				break;
			case METHOD:
				updateMethod(editedClass, subPart.getSlotIndex(), editField
						.getText());
				break;
			}
			editedClass.getCanvas().remove(editField);
			editField = null;
		} finally {
			validationInProcess = false;
		}
	}

}
