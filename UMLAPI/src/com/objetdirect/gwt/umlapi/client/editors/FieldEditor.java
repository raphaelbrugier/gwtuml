/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.HotKeyManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.helpers.UMLDrawerHelper;

/**
 * This abstract class is a generic field editor for uml artifacts.<br>
 * It displays a {@link TextBox} and update the artifact if the user hit Enter or the TextBox lose the focus <br>
 * or cancel if the user hit Escape <br>
 * It also supports multiple lines edition with {@link TextArea} 
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class FieldEditor {
    protected UMLArtifact artifact;
    protected UMLCanvas canvas;
    protected String content;
    protected static TextBoxBase editField;
    protected int height = 0;
    protected boolean isMultiLine;
    protected int minBoxWidth = 40; 
    /**
     * Getter for the editField
     *
     * @return the editField
     */
    public static TextBoxBase getEditField() {
        return editField;
    }

    /**
     * Constructor of the FieldEditor
     *
     * @param canvas The canvas on which is the artifact
     * @param artifact The artifact being edited
     */
    public FieldEditor(final UMLCanvas canvas, final UMLArtifact artifact) {
	super();
	this.canvas = canvas;
	this.artifact = artifact;
	HotKeyManager.setInputEnabled(false);
    }

    /**
     * Setter for the {@link TextArea} height in case of multiple lines edition
     * 
     * @param height The height of the {@link TextArea} to set
     */
    public void setHeightForMultiLine(final int height) {
	this.height = height;
    }

    /**
     * This function begin the edition from the parameters
     * 
     * @param text The previous text, it is used as default {@link TextBox} text
     * @param x The abscissa location of the edition {@link TextBox} 
     * @param y The ordinate location of the edition {@link TextBox} 
     * @param w The width of the edition {@link TextBox} 
     * @param isItMultiLine A boolean to precise if the edition box allow multiple lines 
     * @param isSmallFont Set to true if the edited part has a small font
     */
    public void startEdition(final String text, final int x, final int y,
	    final int w, final boolean isItMultiLine, final boolean isSmallFont) {
	this.isMultiLine = isItMultiLine;
	if (this.isMultiLine && this.height == 0) {
	    Log.error("Must set height for multiline editors");
	}
	this.content = text;

	editField = this.isMultiLine ? new TextArea() : new TextBox();
	editField.setText(text);
	editField.setStylePrimaryName("editor"
		+ (isSmallFont ? "-small" : "")
		+ "-field"
		+ (this.isMultiLine ? "-multiline" : ""));
	editField.setWidth(Math.max(w, this.minBoxWidth) + "px");
	if (this.isMultiLine) {
	    editField.setHeight(this.height + "px");
	}
	DOM.setStyleAttribute(editField.getElement(), "backgroundColor",
		ThemeManager.getTheme().getDefaultBackgroundColor().toString());
	DOM.setStyleAttribute(editField.getElement(), "color", ThemeManager.getTheme()
		.getDefaultForegroundColor().toString());
	DOM.setStyleAttribute(editField.getElement(), "selection", ThemeManager.getTheme()
		.getDefaultBackgroundColor().toString()); // CSS 3 :'(
	editField.addFocusHandler(new FocusHandler() {
	    public void onFocus(final FocusEvent event) {
		Log.trace("Focus on " + this);

	    }

	});
	editField.addBlurHandler(new BlurHandler() {
	    public void onBlur(final BlurEvent event) {
		Log.trace("Focus lost on " + this);
		validate(false);
	    }

	});
	editField.addKeyUpHandler(new KeyUpHandler() {
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
	
	this.canvas.add(editField, x + Session.getActiveCanvas().getCanvasOffset().getX(), y + Session.getActiveCanvas().getCanvasOffset().getY());
	editField.selectAll();
	editField.setFocus(true);
	UMLDrawerHelper.enableBrowserEvents();
    }

    protected void cancel() {
	this.canvas.remove(editField);
	editField = null;
	UMLDrawerHelper.disableBrowserEvents();
	HotKeyManager.setInputEnabled(true);
    }

    protected abstract void next();

    protected abstract boolean updateUMLArtifact(String newContent);

    protected void validate(final boolean isNextable) {
	boolean isStillNextable = isNextable;
	final String newContent = editField.getText();
	if (!newContent.equals(this.content)) {
	    isStillNextable = updateUMLArtifact(newContent) && isStillNextable;
	}
	this.canvas.remove(editField);
	editField = null;
	UMLDrawerHelper.disableBrowserEvents();
	HotKeyManager.setInputEnabled(true);
	if (isStillNextable) {
	    next();
	}

    }
}