package com.objetdirect.gwt.umlapi.client.editors;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.webinterface.HotKeyManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class FieldEditor {
    protected UMLArtifact artifact;
    protected UMLCanvas canvas;
    protected String content;
    protected TextBoxBase editField;

    protected int height = 0;

    protected boolean isMultiLine;

    public FieldEditor(final UMLCanvas canvas, final UMLArtifact artifact) {
	this.canvas = canvas;
	this.artifact = artifact;
	HotKeyManager.setEnabled(false);
    }

    public void setHeightForMultiLine(final int height) {
	this.height = height;
    }

    public void startEdition(final String text, final int x, final int y,
	    final int w, final boolean isitMultiLine) {
	this.isMultiLine = isitMultiLine;
	if (this.isMultiLine && this.height == 0) {
	    Log.error("Must set height for multiline editors");
	}
	this.content = text;

	this.editField = this.isMultiLine ? new TextArea() : new TextBox();
	this.editField.setText(text);
	this.editField.setStylePrimaryName("editor-field"
		+ (this.isMultiLine ? "-multiline" : ""));
	this.editField.setWidth(w + "px");
	if (this.isMultiLine) {
	    this.editField.setHeight(this.height + "px");
	}
	DOM.setStyleAttribute(this.editField.getElement(), "backgroundColor",
		ThemeManager.getBackgroundColor().toString());
	DOM.setStyleAttribute(this.editField.getElement(), "color", ThemeManager
		.getForegroundColor().toString());
	DOM.setStyleAttribute(this.editField.getElement(), "selection", ThemeManager
		.getBackgroundColor().toString()); // CSS 3 :'(
	this.editField.addFocusHandler(new FocusHandler() {
	    public void onFocus(final FocusEvent event) {
		Log.trace("Focus on " + this);

	    }

	});
	this.editField.addBlurHandler(new BlurHandler() {
	    public void onBlur(final BlurEvent event) {
		Log.trace("Focus lost on " + this);
		validate(false);
	    }

	});
	this.editField.addKeyUpHandler(new KeyUpHandler() {
	    public void onKeyUp(final KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    if (!FieldEditor.this.isMultiLine || event.isAnyModifierKeyDown()) {
			validate(true);
		    }

		} else if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
		    cancel();
		}
	    }
	});
	this.canvas.add(this.editField, x, y);
	this.editField.selectAll();
	this.editField.setFocus(true);
	UMLDrawerHelper.enableBrowserEvents();
    }

    protected void cancel() {
	this.canvas.remove(this.editField);
	this.editField = null;
	UMLDrawerHelper.disableBrowserEvents();
	HotKeyManager.setEnabled(true);
    }

    protected abstract void next();

    protected abstract boolean updateUMLArtifact(String newContent);

    protected void validate(final boolean isNextable) {
	boolean isStillNextable = isNextable;
	final String newContent = this.editField.getText();
	if (!newContent.equals(this.content)) {
	    isStillNextable = updateUMLArtifact(newContent) && isStillNextable;
	}
	this.canvas.remove(this.editField);
	this.editField = null;
	UMLDrawerHelper.disableBrowserEvents();
	HotKeyManager.setEnabled(true);
	if (isStillNextable) {
	    next();
	}

    }
}