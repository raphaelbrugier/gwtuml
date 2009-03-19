package com.objetdirect.gwt.umlapi.client.editors;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
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
		editField.addFocusHandler(new FocusHandler() {

			public void onFocus(FocusEvent event) {
				Log.debug("Focus on " + this);
				
			}
			
		});
		editField.addBlurHandler(new BlurHandler() {

			public void onBlur(BlurEvent event) {
				Log.debug("Focus lost on " + this);
				validate();
			}
			
		});
		editField.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					validate();
				} else if (event.getNativeKeyCode()  == KeyCodes.KEY_ESCAPE)
					cancel();
				
			}
		});
		canvas.add(editField, x, y);
		editField.selectAll();
		editField.setFocus(true);
		UMLDrawerHelper.enableBrowserEvents();
	
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
		UMLDrawerHelper.disableBrowserEvents();
	}
	protected void cancel() {
		//validationInProcess = true;
		canvas.remove(editField);
		editField = null;
		//validationInProcess = false;
		UMLDrawerHelper.disableBrowserEvents();
	}
}
