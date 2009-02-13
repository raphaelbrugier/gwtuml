package com.objetdirect.gwt.umlapi.client.editors;

import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassPartArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public abstract class FieldEditor {

	protected UMLCanvas canvas;
	protected TextBox editField;
	protected Object artifact;
	protected String content;
	//protected boolean validationInProcess = false;
	protected abstract void updateClass(String newContent);
	
	public FieldEditor(UMLCanvas canvas, Object artifact) {
		this.canvas = canvas;
		this.artifact = artifact;
	}
	
	public void startEdition(String content, int x, int y, int w) {
		this.content = content;
		editField = new TextBox();
		editField.setText(content);
		editField.setStylePrimaryName("editor-field");
		editField.setWidth(w + "px");
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
				if (keyCode == KEY_ENTER) {
					validate();
				} else if (keyCode == KEY_ESCAPE)
					cancel();
			}
		};
		editField.addFocusListener(focusLst);
		editField.addKeyboardListener(keybLst);
		canvas.add(editField, x, y);
		editField.selectAll();
		editField.setFocus(true);
	
	}
	
	protected void validate() {
		String newContent = editField.getText();
		if(!newContent.equals(content)) {
			//if (validationInProcess) return;
			//validationInProcess = true;
			
			updateClass(newContent);
			
			
			
		}
		canvas.remove(editField);
		editField = null;
	}
	protected void cancel() {
		//validationInProcess = true;
		canvas.remove(editField);
		editField = null;
		//validationInProcess = false;
	}
}
