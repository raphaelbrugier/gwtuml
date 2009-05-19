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
import com.objetdirect.gwt.umlapi.client.editors.ClassPartMethodsEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * This class represent the lower Part of a {@link NodeArtifact}
 * It can hold a method list
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartMethodsArtifact extends NodePartArtifact {

    private GfxObject lastGfxObject;
    private final Map<GfxObject, UMLClassMethod> methodGfxObjects;
    private GfxObject methodRect;
    private final List<UMLClassMethod> methods;
    
    /**
     * Constructor of ClassPartMethodsArtifact
     * It initializes the method list
     * 
     */
    public ClassPartMethodsArtifact() {
	super();
	this.methods = new ArrayList<UMLClassMethod>();
	this.methodGfxObjects = new LinkedHashMap<GfxObject, UMLClassMethod>();
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
    public void add(final UMLClassMethod method) {
	this.methods.add(method);
    }

    @Override
    public void buildGfxObject() {
	if (this.textVirtualGroup == null) {
	    computeBounds();
	}
	this.methodRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.methodRect);
	GfxManager.getPlatform().setFillColor(this.methodRect,
		ThemeManager.getTheme().getBackgroundColor());
	GfxManager.getPlatform().setStroke(this.methodRect,
		ThemeManager.getTheme().getForegroundColor(), 1);
	GfxManager.getPlatform().translate(this.textVirtualGroup, new Point(
		OptionsManager.get("RectangleLeftPadding"),
		OptionsManager.get("RectangleTopPadding")));
	GfxManager.getPlatform().moveToFront(this.textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	this.methodGfxObjects.clear();
	this.height = 0;
	this.width = 0;
	this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject, this.textVirtualGroup);

	for (final UMLClassMethod method : this.methods) {
	    final GfxObject methodText = GfxManager.getPlatform().buildText(
		    method.toString(), new Point(
			    OptionsManager.get("TextLeftPadding"),
			    OptionsManager.get("TextTopPadding") + this.height
				    ));
	    GfxManager.getPlatform().addToVirtualGroup(this.textVirtualGroup,
		    methodText);
	    GfxManager.getPlatform().setFont(methodText,
		    OptionsManager.getSmallFont());

	    GfxManager.getPlatform().setStroke(methodText,
		    ThemeManager.getTheme().getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(methodText,
		    ThemeManager.getTheme().getForegroundColor());
	    int thisMethodWidth = GfxManager.getPlatform().getTextWidthFor(
		    methodText);
	    int thisMethodHeight = GfxManager.getPlatform().getTextHeightFor(
		    methodText);
	    thisMethodWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
	    thisMethodHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
	    this.width = thisMethodWidth > this.width ? thisMethodWidth : this.width;
	    this.height += thisMethodHeight;

	    this.methodGfxObjects.put(methodText, method);
	    this.lastGfxObject = methodText;
	}
	this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
	this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ this.width + "x" + this.height);
    }

    @Override
    public void edit() {
	final List<UMLParameter> methodToCreateParameters = new ArrayList<UMLParameter>();
	methodToCreateParameters.add(new UMLParameter("String", "parameter1"));
	this.methods.add(new UMLClassMethod(UMLVisibility.PUBLIC, "void", "method",
		methodToCreateParameters));
	this.nodeArtifact.rebuildGfxObject();
	edit(this.lastGfxObject);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final UMLClassMethod methodToChange = this.methodGfxObjects.get(editedGfxObject);
	if (methodToChange == null) {
	    edit();
	} else {
	    final ClassPartMethodsEditor editor = new ClassPartMethodsEditor(this.canvas, this,
		    methodToChange);
	    editor
		    .startEdition(
			    methodToChange.toString(),
			    (this.nodeArtifact.getLocation().getX()
				    + OptionsManager.get("TextLeftPadding") + OptionsManager
				    .get("RectangleLeftPadding")), (this.nodeArtifact
				    .getLocation().getY()
				    + ((ClassArtifact) this.nodeArtifact).className.getHeight()
				    + ((ClassArtifact) this.nodeArtifact).classAttributes.getHeight()
				    + GfxManager.getPlatform().getLocationFor(
					    editedGfxObject).getY() + OptionsManager
				    .get("RectangleTopPadding")), this.nodeWidth
				    - OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding")
				    - OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"),
				    false, true);
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
    public List<UMLClassMethod> getList() {
	return this.methods;
    }

    @Override
    public int[] getOpaque() {
	return null;
    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	rightMenu.setName("Methods");

	for (final Entry<GfxObject, UMLClassMethod> method : this.methodGfxObjects
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
    public void remove(final UMLClassMethod method) {
	this.methods.remove(method);
    }

    @Override
    protected void select() {
	GfxManager.getPlatform().setStroke(this.methodRect,
		ThemeManager.getTheme().getHighlightedForegroundColor(), 2);
    }

    @Override
    void setNodeWidth(final int width) {
	this.nodeWidth = width;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.methodRect,
		ThemeManager.getTheme().getForegroundColor(), 1);
    }

    private Command deleteCommand(final UMLClassMethod method) {
	return new Command() {
	    public void execute() {
		remove(method);
		ClassPartMethodsArtifact.this.nodeArtifact.rebuildGfxObject();
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

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
     */
    @Override
    public String toURL() {
	StringBuilder methodsURL = new StringBuilder();
	for(UMLClassMethod method : this.methods) {
	    methodsURL.append(method);
	    methodsURL.append("%");
	}
	return methodsURL.toString();
    }
}
