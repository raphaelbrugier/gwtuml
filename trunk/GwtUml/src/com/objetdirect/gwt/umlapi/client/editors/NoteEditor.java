package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;

public class NoteEditor {

	public static final int FIELD_XMARGIN= 8;
	public static final int FIELD_YMARGIN= 8;
	public static final int FIELD_HEIGHT = 18;
	private NoteArtifact editedNote = null;
	
	public NoteEditor(NoteArtifact editedNote) {
		this.editedNote = editedNote;
	}

	public void editContent() {
		editField = getEditField(editedNote.getContent(), editedNote.getWidth());
		editedNote.getCanvas().add(editField, editedNote.getX()+FIELD_XMARGIN, editedNote.getY()+FIELD_YMARGIN);
		editField.selectAll();
		editField.setFocus(true);
	}



	protected void validate() {
		if (validationInProcess)
			return;
		try {			
			validationInProcess = true;
			editedNote.setContent(editField.getText());
			editedNote.getCanvas().remove(editField);
			editField = null;
		}
		finally {
			validationInProcess = false;
		}
	}
	

	protected void goDown() {
			validate();
	}

	protected void goNextLine() {
			validate();
	}
	
	protected void goNextBox() {
			validate();
	}


	protected void cancel() {
		validationInProcess = true;
		try {
			editedNote.getCanvas().remove(editField);
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
					goNextLine();
				//if (keyCode==KEY_PAGEUP)
					//goPrevBox();
				if (keyCode==KEY_PAGEDOWN)
					goNextBox();
				/*if (keyCode==KEY_UP) {
					if ((modifiers&MODIFIER_SHIFT)!=0)
						moveUp();
					else
						goUp();
				}*/
				if (keyCode==KEY_DOWN) {
					/*if ((modifiers&MODIFIER_SHIFT)!=0)
						moveDown();
					else*/
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
}
