package com.objetdirect.gwt.umlapi.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyser;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;

public class StandardClassEditor extends ClassEditor {

	public static final int FIELD_XMARGIN= 8;
	public static final int FIELD_YMARGIN= -4;
	public static final int FIELD_HEIGHT = 18;
	
	protected void editName(ClassArtifact elem) {
		this.elem = elem;
		this.subpart = ClassArtifact.NAME;
		editField = getEditField(elem.getClassName(), elem.getWidth());
		elem.getCanvas().add(editField, elem.getX()+FIELD_XMARGIN, elem.getY()+elem.getNameY()+FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}

	protected void editAttribute(ClassArtifact elem, Attribute attribute) {
		this.elem = elem;
		this.subpart = attribute;
		editField = getEditField(attribute.toString(), elem.getWidth());
		elem.getCanvas().add(editField, elem.getX()+FIELD_XMARGIN, elem.getY()+elem.getAttributeY(attribute)+FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}
	
	
	protected void editNewAttribute(ClassArtifact elem) {
		Attribute attr = new Attribute("type", "name");
		attr.setValidated(false);
		elem.addAttribute(attr);
		subpart = attr;
		editAttribute(elem, attr);
	}

	protected void editMethod(ClassArtifact elem, Method method) {
		this.elem = elem;
		this.subpart = method;
		editField = getEditField(method.toString(), elem.getWidth());
		elem.getCanvas().add(editField, elem.getX()+FIELD_XMARGIN, elem.getY()+elem.getMethodY(method)+FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}
	
	
	protected void editNewMethod(ClassArtifact elem) {
		Method method = new Method("type", "name", null);
		method.setValidated(false);
		elem.addMethod(method);
		subpart = method;
		editMethod(elem, method);
	}

	protected void validate() {
		if (validationInProcess)
			return;
		try {			
			validationInProcess = true;
			if (subpart==ClassArtifact.NAME) {
				elem.setClassName(editField.getText());
			}
			else if (subpart instanceof Attribute) {
				updateAttribute(elem, (Attribute)subpart, editField.getText());
			}
			else if (subpart instanceof Method) {
				updateMethod(elem, (Method)subpart, editField.getText());
			}
			elem.getCanvas().remove(editField);
			editField = null;
		}
		finally {
			validationInProcess = false;
		}
	}
	

	protected void goDown() {
		if (subpart==ClassArtifact.NAME) {
			validate();
			if (elem.getAttributes().size()>0)
				editAttribute(elem, (Attribute)elem.getAttributes().get(0));
		}
		else if (subpart instanceof Attribute) {
			int i = elem.getAttributes().indexOf(subpart);
			Attribute nextAttr = null;
			if (elem.getAttributes().size()>i+1)
				nextAttr = (Attribute)elem.getAttributes().get(i+1);
			if (nextAttr!=null) {	
				validate();
				editAttribute(elem, nextAttr);
			} else if (elem.getMethods().size()>0) {
				validate();
				editMethod(elem, (Method)elem.getMethods().get(0));
			}
		}
		else if (subpart instanceof Method) {
			int i = elem.getMethods().indexOf(subpart);
			Method nextMethod = null;
			if (elem.getMethods().size()>i+1)
				nextMethod = (Method)elem.getMethods().get(i+1);
			if (nextMethod!=null) {	
				validate();
				editMethod(elem, nextMethod);
			}
		}
	}

	protected void goUp() {
		if (subpart instanceof Attribute) {
			int i = elem.getAttributes().indexOf(subpart);
			Attribute prevAttr = null;
			if (i>0)
				prevAttr = (Attribute)elem.getAttributes().get(i-1);
			validate();
			if (prevAttr!=null)	
				editAttribute(elem, prevAttr);
			else
				editName(elem);
		}
		else if (subpart instanceof Method) {
			int i = elem.getMethods().indexOf(subpart);
			Method prevMethod = null;
			if (i>0)
				prevMethod = (Method)elem.getMethods().get(i-1);
			validate();
			if (prevMethod!=null)	
				editMethod(elem, prevMethod);
			else if (elem.getAttributes().size()>0) {
				Attribute attr = (Attribute)elem.getAttributes().get(elem.getAttributes().size()-1);
				editAttribute(elem, attr);
			}
			else
				editName(elem);
		}
	}

	protected void moveUp() {
		if (subpart instanceof Attribute) {
			int i = elem.getAttributes().indexOf(subpart);
			if (i>0) {
				Attribute attr = (Attribute)elem.getAttributes().get(i-1);
				elem.exchangeAttribute((Attribute)subpart, attr);
				int y = elem.getAttributeY((Attribute)subpart);
				elem.getCanvas().setWidgetPosition(editField, elem.getX()+FIELD_XMARGIN, elem.getY()+y+FIELD_YMARGIN);
			}
		}
		else if (subpart instanceof Method) {
			int i = elem.getMethods().indexOf(subpart);
			if (i>0) {
				Method method = (Method)elem.getMethods().get(i-1);
				elem.exchangeMethod((Method)subpart, method);
				int y = elem.getMethodY((Method)subpart);
				elem.getCanvas().setWidgetPosition(editField, elem.getX()+FIELD_XMARGIN, elem.getY()+y+FIELD_YMARGIN);
			}
		}
	}
	
	protected void moveDown() {
		if (subpart instanceof Attribute) {
			int i = elem.getAttributes().indexOf(subpart);
			if (i<elem.getAttributes().size()-1) {
				Attribute attr = (Attribute)elem.getAttributes().get(i+1);
				elem.exchangeAttribute((Attribute)subpart, attr);
				int y = elem.getAttributeY((Attribute)subpart);
				elem.getCanvas().setWidgetPosition(editField, elem.getX()+FIELD_XMARGIN, elem.getY()+y+FIELD_YMARGIN);
			}
		}
		else if (subpart instanceof Method) {
			int i = elem.getMethods().indexOf(subpart);
			if (i<elem.getAttributes().size()-1) {
				Method method = (Method)elem.getMethods().get(i+1);
				elem.exchangeMethod((Method)subpart, method);
				int y = elem.getMethodY((Method)subpart);
				elem.getCanvas().setWidgetPosition(editField, elem.getX()+FIELD_XMARGIN, elem.getY()+y+FIELD_YMARGIN);
			}
		}
	}

	protected void goNextLine() {
		if (subpart==ClassArtifact.NAME) {
			validate();
			if (elem.getAttributes().size()>0)
				editAttribute(elem, (Attribute)elem.getAttributes().get(0));
		}
		else if (subpart instanceof Attribute) {
			int i = elem.getAttributes().indexOf(subpart);
			Attribute nextAttr = null;
			if (elem.getAttributes().size()>i+1)
				nextAttr = (Attribute)elem.getAttributes().get(i+1);
			validate();
			if (nextAttr!=null)	
				editAttribute(elem, nextAttr);
			else 
				editNewAttribute(elem);
		}
		else if (subpart instanceof Method) {
			int i = elem.getMethods().indexOf(subpart);
			Method nextMethod = null;
			if (elem.getMethods().size()>i+1)
				nextMethod = (Method)elem.getMethods().get(i+1);
			validate();
			if (nextMethod!=null)	
				editMethod(elem, nextMethod);
			else 
				editNewMethod(elem);
		}
	}
	
	protected void goNextBox() {
		if (subpart==ClassArtifact.NAME) {
			validate();
			if (elem.getAttributes().size()>0)
				editAttribute(elem, (Attribute)elem.getAttributes().get(0));
			else
				editNewAttribute(elem);
		}
		else if (subpart instanceof Attribute) {
			validate();
			if (elem.getMethods().size()>0)
				editMethod(elem, (Method)elem.getMethods().get(0));
			else
				editNewMethod(elem);
		}
	}

	protected void goPrevBox() {
		if (subpart instanceof Attribute) {
			validate();
			editName(elem);
		}
		else if (subpart instanceof Attribute) {
			validate();
			if (elem.getAttributes().size()>0)
				editAttribute(elem, (Attribute)elem.getAttributes().get(0));
			else
				editNewAttribute(elem);
		}
	}

	protected void cancel() {
		validationInProcess = true;
		try {
			if (subpart instanceof Attribute) {
				Attribute attr = (Attribute)subpart;
				if (!attr.isValidated())
					removeAttribute(elem, attr);
			}
			if (subpart instanceof Method) {
				Method method = (Method)subpart;
				if (!method.isValidated())
					removeMethod(elem, method);
			}
			elem.getCanvas().remove(editField);
			editField = null;
		}
		finally {
			validationInProcess = false;
		}		
	}
	
	void updateAttribute(ClassArtifact elem, Attribute attr, String text) {
		if (text.trim().length()==0)
			removeAttribute(elem, attr);
		else
			replaceAttribute(elem, attr, text);
	}
	
	void removeAttribute(ClassArtifact elem, Attribute attr) {
		elem.removeAttribute(attr);
	}
	
	void replaceAttribute(ClassArtifact elem, Attribute attr, String text) {
		LexicalAnalyser lex = new LexicalAnalyser(text);
		try {
			String type = null;
			String name = null;
			LexicalAnalyser.Token tk = lex.getToken();
			if (tk.getType()!=LexicalAnalyser.IDENTIFIER)
				throw new UMLDrawerException("invalid format : must match 'identifier:type'");
			name = tk.getContent();
			tk = lex.getToken();
			if (tk!=null) {
				if (tk.getType()!=LexicalAnalyser.SIGN || !tk.getContent().equals(":"))
					throw new UMLDrawerException("invalid format : must match 'identifier:type'");
				tk = lex.getToken();
				if (tk==null || tk.getType()!=LexicalAnalyser.IDENTIFIER)
					throw new UMLDrawerException("invalid format : must match 'identifier:type'");
				type = tk.getContent();
			}
			elem.setAttribute(attr, new Attribute(type, name));
			attr.setValidated(true);
		} catch (UMLDrawerException e) {
			Window.alert(e.getMessage());
		}
	}
	
	void updateMethod(ClassArtifact elem, Method method, String text) {
		if (text.trim().length()==0)
			removeMethod(elem, method);
		else
			replaceMethod(elem, method, text);
	}
	
	void removeMethod(ClassArtifact elem, Method method) {
		elem.removeMethod(method);
	}
	
	void replaceMethod(ClassArtifact elem, Method method, String text) {
		LexicalAnalyser lex = new LexicalAnalyser(text);
		try {
			MethodSyntaxAnalyser ma = new MethodSyntaxAnalyser();
			ma.process(lex, null);
			Method newMethod = ma.getMethod();
			elem.setMethod(method, newMethod);
			method.setValidated(true);
		} catch (UMLDrawerException e) {
			Window.alert(e.getMessage());
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
					goNextLine();
				if (keyCode==KEY_PAGEUP)
					goPrevBox();
				if (keyCode==KEY_PAGEDOWN)
					goNextBox();
				if (keyCode==KEY_UP) {
					if ((modifiers&MODIFIER_SHIFT)!=0)
						moveUp();
					else
						goUp();
				}
				if (keyCode==KEY_DOWN) {
					if ((modifiers&MODIFIER_SHIFT)!=0)
						moveDown();
					else
						goDown();
				}
				else if (keyCode==KEY_ESCAPE)
					cancel();
			}
		};
		editField.addFocusListener(focusLst);
		editField.addKeyboardListener(keybLst);
	}
	
	boolean validationInProcess = false;
	TextBox editField = null;
	ClassArtifact elem;
	Object subpart;
}
