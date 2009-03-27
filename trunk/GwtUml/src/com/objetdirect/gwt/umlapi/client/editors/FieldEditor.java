package com.objetdirect.gwt.umlapi.client.editors;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
/**
 * @author  florian
 */
public abstract class FieldEditor {
	/**
	 * @uml.property  name="canvas"
	 * @uml.associationEnd  
	 */
	protected UMLCanvas canvas;
	protected TextBoxBase editField;
	protected UMLArtifact artifact;
	protected String content;
	protected abstract void updateClass(String newContent);
	protected abstract void next();
	protected boolean isMultiLine;
	protected int height = 0;
	
	public FieldEditor(UMLCanvas canvas, UMLArtifact artifact) {
		this.canvas = canvas;
		this.artifact = artifact;
	}
	
	public void setHeightForMultiLine(int height) {
		this.height = height;
	}
	
	public void startEdition(String content, int x, int y, int w, final boolean isMultiLine) {
		if(isMultiLine && height == 0) Log.error("Must set height for multiline editors");
		this.content = content;
		this.isMultiLine = isMultiLine;
		editField = isMultiLine ? new TextArea() : new TextBox();
		editField.setText(content);
		editField.setStylePrimaryName("editor-field");
		editField.setWidth(w + "px");
		if(isMultiLine) editField.setHeight(height + "px");
		
		editField.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				Log.debug("Focus on " + this);
				
			}
			
		});
		editField.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				Log.debug("Focus lost on " + this);
				validate(true);
			}
			
		});
		editField.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if(!isMultiLine || event.isAnyModifierKeyDown()) validate(false);
					
				} else if (event.getNativeKeyCode()  == KeyCodes.KEY_ESCAPE)
					cancel();
			}
		});
		canvas.add(editField, x, y);
		editField.selectAll();
		editField.setFocus(true);
		UMLDrawerHelper.enableBrowserEvents();
	}
	
	protected void validate(boolean isFinished) {
		
		String newContent = editField.getText();
		if(!newContent.equals(content)) {	
			updateClass(newContent);
		}
		canvas.remove(editField);
		editField = null;
		UMLDrawerHelper.disableBrowserEvents();
		if(!isFinished) next();
	}
	protected void cancel() {
		canvas.remove(editField);
		editField = null;
		UMLDrawerHelper.disableBrowserEvents();
	}
}