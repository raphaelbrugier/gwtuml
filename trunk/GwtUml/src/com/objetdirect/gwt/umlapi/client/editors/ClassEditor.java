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

public class ClassEditor {

	public static final int FIELD_XMARGIN= 8;
	public static final int FIELD_YMARGIN= -4;
	public static final int FIELD_HEIGHT = 18;
	private ClassArtifact editedClass = null;
	
	public ClassEditor(ClassArtifact editedClass) {
		this.editedClass = editedClass;
	}

	public void editName() {
		this.subpart = ClassArtifact.NAME;
		editField = getEditField(editedClass.getClassName(), editedClass.getWidth());
		editedClass.getCanvas().add(editField, editedClass.getX()+FIELD_XMARGIN, editedClass.getY()+editedClass.getNameY()+FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}

	public void editAttribute(Attribute attribute) {
		this.subpart = attribute;
		editField = getEditField(attribute.toString(), editedClass.getWidth());
		editedClass.getCanvas().add(editField, editedClass.getX()+FIELD_XMARGIN, editedClass.getY()+editedClass.getAttributeY(attribute)+FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}
	
	
	public void editNewAttribute() {
		Attribute attr = new Attribute("type", "name");
		attr.setValidated(false);
		editedClass.addAttribute(attr);
		subpart = attr;
		editAttribute(attr);
	}

	public void editMethod(Method method) {
		this.subpart = method;
		editField = getEditField(method.toString(), editedClass.getWidth());
		editedClass.getCanvas().add(editField, editedClass.getX()+FIELD_XMARGIN, editedClass.getY()+editedClass.getMethodY(method)+FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}
	
	
	public void editNewMethod() {
		Method method = new Method("type", "name", null);
		method.setValidated(false);
		editedClass.addMethod(method);
		subpart = method;
		editMethod(method);
	}

	protected void validate() {
		if (validationInProcess)
			return;
		try {			
			validationInProcess = true;
			if (subpart==ClassArtifact.NAME) {
				editedClass.setClassName(editField.getText());
			}
			else if (subpart instanceof Attribute) {
				updateAttribute(editedClass, (Attribute)subpart, editField.getText());
			}
			else if (subpart instanceof Method) {
				updateMethod(editedClass, (Method)subpart, editField.getText());
			}
			editedClass.getCanvas().remove(editField);
			editField = null;
		}
		finally {
			validationInProcess = false;
		}
	}
	

	protected void goDown() {
		if (subpart==ClassArtifact.NAME) {
			validate();
			if (editedClass.getAttributes().size()>0)
				editAttribute((Attribute)editedClass.getAttributes().get(0));
		}
		else if (subpart instanceof Attribute) {
			int i = editedClass.getAttributes().indexOf(subpart);
			Attribute nextAttr = null;
			if (editedClass.getAttributes().size()>i+1)
				nextAttr = (Attribute)editedClass.getAttributes().get(i+1);
			if (nextAttr!=null) {	
				validate();
				editAttribute(nextAttr);
			} else if (editedClass.getMethods().size()>0) {
				validate();
				editMethod((Method)editedClass.getMethods().get(0));
			}
		}
		else if (subpart instanceof Method) {
			int i = editedClass.getMethods().indexOf(subpart);
			Method nextMethod = null;
			if (editedClass.getMethods().size()>i+1)
				nextMethod = (Method)editedClass.getMethods().get(i+1);
			if (nextMethod!=null) {	
				validate();
				editMethod(nextMethod);
			}
		}
	}

	protected void goUp() {
		if (subpart instanceof Attribute) {
			int i = editedClass.getAttributes().indexOf(subpart);
			Attribute prevAttr = null;
			if (i>0)
				prevAttr = (Attribute)editedClass.getAttributes().get(i-1);
			validate();
			if (prevAttr!=null)	
				editAttribute(prevAttr);
			else
				editName();
		}
		else if (subpart instanceof Method) {
			int i = editedClass.getMethods().indexOf(subpart);
			Method prevMethod = null;
			if (i>0)
				prevMethod = (Method)editedClass.getMethods().get(i-1);
			validate();
			if (prevMethod!=null)	
				editMethod(prevMethod);
			else if (editedClass.getAttributes().size()>0) {
				Attribute attr = (Attribute)editedClass.getAttributes().get(editedClass.getAttributes().size()-1);
				editAttribute(attr);
			}
			else
				editName();
		}
	}

	protected void moveUp() {
		if (subpart instanceof Attribute) {
			int i = editedClass.getAttributes().indexOf(subpart);
			if (i>0) {
				Attribute attr = (Attribute)editedClass.getAttributes().get(i-1);
				editedClass.exchangeAttribute((Attribute)subpart, attr);
				int y = editedClass.getAttributeY((Attribute)subpart);
				editedClass.getCanvas().setWidgetPosition(editField, editedClass.getX()+FIELD_XMARGIN, editedClass.getY()+y+FIELD_YMARGIN);
			}
		}
		else if (subpart instanceof Method) {
			int i = editedClass.getMethods().indexOf(subpart);
			if (i>0) {
				Method method = (Method)editedClass.getMethods().get(i-1);
				editedClass.exchangeMethod((Method)subpart, method);
				int y = editedClass.getMethodY((Method)subpart);
				editedClass.getCanvas().setWidgetPosition(editField, editedClass.getX()+FIELD_XMARGIN, editedClass.getY()+y+FIELD_YMARGIN);
			}
		}
	}
	
	protected void moveDown() {
		if (subpart instanceof Attribute) {
			int i = editedClass.getAttributes().indexOf(subpart);
			if (i<editedClass.getAttributes().size()-1) {
				Attribute attr = (Attribute)editedClass.getAttributes().get(i+1);
				editedClass.exchangeAttribute((Attribute)subpart, attr);
				int y = editedClass.getAttributeY((Attribute)subpart);
				editedClass.getCanvas().setWidgetPosition(editField, editedClass.getX()+FIELD_XMARGIN, editedClass.getY()+y+FIELD_YMARGIN);
			}
		}
		else if (subpart instanceof Method) {
			int i = editedClass.getMethods().indexOf(subpart);
			if (i<editedClass.getAttributes().size()-1) {
				Method method = (Method)editedClass.getMethods().get(i+1);
				editedClass.exchangeMethod((Method)subpart, method);
				int y = editedClass.getMethodY((Method)subpart);
				editedClass.getCanvas().setWidgetPosition(editField, editedClass.getX()+FIELD_XMARGIN, editedClass.getY()+y+FIELD_YMARGIN);
			}
		}
	}

	protected void goNextLine() {
		if (subpart==ClassArtifact.NAME) {
			validate();
			if (editedClass.getAttributes().size()>0)
				editAttribute((Attribute)editedClass.getAttributes().get(0));
		}
		else if (subpart instanceof Attribute) {
			int i = editedClass.getAttributes().indexOf(subpart);
			Attribute nextAttr = null;
			if (editedClass.getAttributes().size()>i+1)
				nextAttr = (Attribute)editedClass.getAttributes().get(i+1);
			validate();
			if (nextAttr!=null)	
				editAttribute(nextAttr);
			else 
				editNewAttribute();
		}
		else if (subpart instanceof Method) {
			int i = editedClass.getMethods().indexOf(subpart);
			Method nextMethod = null;
			if (editedClass.getMethods().size()>i+1)
				nextMethod = (Method)editedClass.getMethods().get(i+1);
			validate();
			if (nextMethod!=null)	
				editMethod(nextMethod);
			else 
				editNewMethod();
		}
	}
	
	protected void goNextBox() {
		if (subpart==ClassArtifact.NAME) {
			validate();
			if (editedClass.getAttributes().size()>0)
				editAttribute((Attribute)editedClass.getAttributes().get(0));
			else
				editNewAttribute();
		}
		else if (subpart instanceof Attribute) {
			validate();
			if (editedClass.getMethods().size()>0)
				editMethod((Method)editedClass.getMethods().get(0));
			else
				editNewMethod();
		}
	}

	protected void goPrevBox() {
		if (subpart instanceof Attribute) {
			validate();
			editName();
		}
		else if (subpart instanceof Attribute) {
			validate();
			if (editedClass.getAttributes().size()>0)
				editAttribute((Attribute)editedClass.getAttributes().get(0));
			else
				editNewAttribute();
		}
	}

	protected void cancel() {
		validationInProcess = true;
		try {
			if (subpart instanceof Attribute) {
				Attribute attr = (Attribute)subpart;
				if (!attr.isValidated())
					removeAttribute(editedClass, attr);
			}
			if (subpart instanceof Method) {
				Method method = (Method)subpart;
				if (!method.isValidated())
					removeMethod(editedClass, method);
			}
			editedClass.getCanvas().remove(editField);
			editField = null;
		}
		finally {
			validationInProcess = false;
		}		
	}
	
	void updateAttribute(ClassArtifact editedClass, Attribute attr, String text) {
		if (text.trim().length()==0)
			removeAttribute(editedClass, attr);
		else
			replaceAttribute(editedClass, attr, text);
	}
	
	void removeAttribute(ClassArtifact editedClass, Attribute attr) {
		editedClass.removeAttribute(attr);
	}
	
	void replaceAttribute(ClassArtifact editedClass, Attribute attr, String text) {
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
			editedClass.setAttribute(attr, new Attribute(type, name));
			attr.setValidated(true);
		} catch (UMLDrawerException e) {
			Window.alert(e.getMessage());
		}
	}
	
	void updateMethod(ClassArtifact editedClass, Method method, String text) {
		if (text.trim().length()==0)
			removeMethod(editedClass, method);
		else
			replaceMethod(editedClass, method, text);
	}
	
	void removeMethod(ClassArtifact editedClass, Method method) {
		editedClass.removeMethod(method);
	}
	
	void replaceMethod(ClassArtifact editedClass, Method method, String text) {
		LexicalAnalyser lex = new LexicalAnalyser(text);
		try {
			MethodSyntaxAnalyser ma = new MethodSyntaxAnalyser();
			ma.process(lex, null);
			Method newMethod = ma.getMethod();
			editedClass.setMethod(method, newMethod);
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
	Object subpart;
}
