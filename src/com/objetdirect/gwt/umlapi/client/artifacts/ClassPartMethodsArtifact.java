package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.MethodPartEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Visibility;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This class represent the lower Part of a {@link ClassArtifact}
 * It can hold a method list
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartMethodsArtifact extends ClassPartArtifact {

    private GfxObject lastGfxObject;
    private final Map<GfxObject, Method> methodGfxObjects;
    private GfxObject methodRect;
    private final List<Method> methods;
    
    /**
     * Constructor of ClassPartMethodsArtifact
     * It initializes the method list
     * 
     */
    public ClassPartMethodsArtifact() {
	this.methods = new ArrayList<Method>();
	this.methodGfxObjects = new LinkedHashMap<GfxObject, Method>();
	// List<Parameter> methodParameters = new ArrayList<Parameter>();
	// methodParameters.add(new Parameter("String", "parameter1"));
	// methods.add(new Method("void","method", methodParameters));
	this.height = 0;
	this.width = 0;
    }
    /**
     * Add a method to the current method list.
     * The graphical object must be rebuilt to reflect the changes
     * 
     * @param method The new method to add
     */
    public void add(final Method method) {
	this.methods.add(method);
    }

    @Override
    public void buildGfxObject() {
	if (this.textVirtualGroup == null) {
	    computeBounds();
	}
	this.methodRect = GfxManager.getPlatform().buildRect(this.classWidth, this.height);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.methodRect);
	GfxManager.getPlatform().setFillColor(this.methodRect,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(this.methodRect,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().translate(this.textVirtualGroup, new Point(
		OptionsManager.getRectangleLeftPadding(),
		OptionsManager.getRectangleTopPadding()));
	GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	this.methodGfxObjects.clear();
	this.height = 0;
	this.width = 0;
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);

	for (final Method method : this.methods) {
	    final GfxObject methodText = GfxManager.getPlatform().buildText(
		    method.toString());
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    methodText);
	    GfxManager.getPlatform().setFont(methodText,
		    OptionsManager.getSmallFont());

	    GfxManager.getPlatform().setStroke(methodText,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(methodText,
		    ThemeManager.getForegroundColor());
	    int thisMethodWidth = GfxManager.getPlatform().getTextWidthFor(
		    methodText);
	    int thisMethodHeight = GfxManager.getPlatform().getTextHeightFor(
		    methodText);

	    GfxManager.getPlatform().translate(
		    methodText,new Point(
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + this.height
			    + thisMethodHeight));
	    thisMethodWidth += OptionsManager.getTextXTotalPadding();
	    thisMethodHeight += OptionsManager.getTextYTotalPadding();
	    this.width = thisMethodWidth > this.width ? thisMethodWidth : this.width;
	    this.height += thisMethodHeight;

	    this.methodGfxObjects.put(methodText, method);
	    this.lastGfxObject = methodText;
	}
	this.width += OptionsManager.getRectangleXTotalPadding();
	this.height += OptionsManager.getRectangleYTotalPadding();

	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ this.width + "x" + this.height);
    }

    @Override
    public void edit() {
	final List<Parameter> methodToCreateParameters = new ArrayList<Parameter>();
	methodToCreateParameters.add(new Parameter("String", "parameter1"));
	this.methods.add(new Method(Visibility.PUBLIC, "void", "method",
		methodToCreateParameters));
	this.classArtifact.rebuildGfxObject();
	edit(this.lastGfxObject);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final Method methodToChange = this.methodGfxObjects.get(editedGfxObject);
	if (methodToChange == null) {
	    edit();
	} else {
	    final MethodPartEditor editor = new MethodPartEditor(this.canvas, this,
		    methodToChange);
	    editor
		    .startEdition(
			    methodToChange.toString(),
			    (this.classArtifact.getLocation().getX()
				    + OptionsManager.getTextLeftPadding() + OptionsManager
				    .getRectangleLeftPadding()), (this.classArtifact
				    .getLocation().getY()
				    + this.classArtifact.className.getHeight()
				    + this.classArtifact.classAttributes.getHeight()
				    + GfxManager.getPlatform().getLocationFor(
					    editedGfxObject).getY()
				    - GfxManager.getPlatform().getTextHeightFor(
					    editedGfxObject) + OptionsManager
				    .getRectangleTopPadding()), this.classWidth
				    - OptionsManager.getTextXTotalPadding()
				    - OptionsManager
					    .getRectangleXTotalPadding(), false, true);
	}
    }

    @Override
    public int getHeight() {
	return this.height;
    }
    
    /**
     * Getter for the method list
     * 
     * @return The current method list
     */
    public List<Method> getList() {
	return this.methods;
    }

    @Override
    public int[] getOpaque() {
	return null;
    }

    @Override
    public GfxObject getOutline() {
	return null;
    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Methods");

	for (final Entry<GfxObject, Method> method : this.methodGfxObjects
		.entrySet()) {
	    final MenuBar subsubMenu = new MenuBar(true);
	    subsubMenu.addItem("Edit ", editCommand(method.getKey()));
	    subsubMenu.addItem("Delete ", deleteCommand(method.getValue()));
	    rightMenu.addItem(method.getValue().toString(), subsubMenu);
	}
	rightMenu.addItem("Add new", editCommand());
	return rightMenu;
    }

    @Override
    public int getWidth() {
	return this.width;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }
    
    /**
     * Remove a method to the current method list.
     * The graphical object must be rebuilt to reflect the changes
     * 
     * @param method The method to be removed
     */
    public void remove(final Method method) {
	this.methods.remove(method);
    }

    @Override
    public void select() {
	GfxManager.getPlatform().setStroke(this.methodRect,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    @Override
    void setClassWidth(final int width) {
	this.classWidth = width;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.methodRect,
		ThemeManager.getForegroundColor(), 1);
    }

    private Command deleteCommand(final Method method) {
	return new Command() {
	    public void execute() {
		remove(method);
		ClassPartMethodsArtifact.this.classArtifact.rebuildGfxObject();
	    }
	};
    }

    private Command editCommand() {
	return new Command() {
	    public void execute() {
		edit();
	    }
	};
    }

    private Command editCommand(final GfxObject gfxo) {
	return new Command() {
	    public void execute() {
		edit(gfxo);
	    }
	};
    }
}
