package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

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
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Visibility;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author fmounier
 */
public class ClassMethodsArtifact extends ClassPartArtifact {

    private GfxObject lastGfxObject;
    private final Map<GfxObject, Method> methodGfxObjects;
    private GfxObject methodRect;
    private final List<Method> methods;

    public ClassMethodsArtifact() {
	this.methods = new ArrayList<Method>();
	this.methodGfxObjects = new LinkedHashMap<GfxObject, Method>();
	// List<Parameter> methodParameters = new ArrayList<Parameter>();
	// methodParameters.add(new Parameter("String", "parameter1"));
	// methods.add(new Method("void","method", methodParameters));
	this.height = 0;
	this.width = 0;
    }

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
	GfxManager.getPlatform().translate(this.textVirtualGroup,
		OptionsManager.getRectangleLeftPadding(),
		OptionsManager.getRectangleTopPadding());
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
		    OptionsManager.getFont());

	    GfxManager.getPlatform().setStroke(methodText,
		    ThemeManager.getBackgroundColor(), 0);
	    GfxManager.getPlatform().setFillColor(methodText,
		    ThemeManager.getForegroundColor());
	    int thisMethodWidth = GfxManager.getPlatform().getWidthFor(
		    methodText);
	    int thisMethodHeight = GfxManager.getPlatform().getHeightFor(
		    methodText);

	    GfxManager.getPlatform().translate(
		    methodText,
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + this.height
			    + thisMethodHeight);
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

    private Command deleteCommand(final Method method) {
	return new Command() {
	    public void execute() {
		remove(method);
		ClassMethodsArtifact.this.classArtifact.rebuildGfxObject();
	    }
	};
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
			    (this.classArtifact.getX()
				    + OptionsManager.getTextLeftPadding() + OptionsManager
				    .getRectangleLeftPadding()), (this.classArtifact
				    .getY()
				    + this.classArtifact.className.getHeight()
				    + this.classArtifact.classAttributes.getHeight()
				    + GfxManager.getPlatform().getYFor(
					    editedGfxObject)
				    - GfxManager.getPlatform().getHeightFor(
					    editedGfxObject) + OptionsManager
				    .getRectangleTopPadding()), this.classWidth
				    - OptionsManager.getTextXTotalPadding()
				    - OptionsManager
					    .getRectangleXTotalPadding(), false);
	}
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

    @Override
    public int getHeight() {
	return this.height;
    }

    public List<Method> getList() {
	return this.methods;
    }

    @Override
    public int[] getOpaque() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public GfxObject getOutline() {
	// TODO Auto-generated method stub
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
    public int getX() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int getY() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public boolean isDraggable() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void moveTo(final int fx, final int fy) {
	// TODO Auto-generated method stub

    }

    public void remove(final Method method) {
	this.methods.remove(method);
    }

    @Override
    public void select() {
	GfxManager.getPlatform().setStroke(this.methodRect,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    @Override
    public void setClassWidth(final int width) {
	this.classWidth = width;
    }

    @Override
    public void setHeight(final int height) {
	this.height = height;
    }

    @Override
    public void setWidth(final int width) {
	this.width = width;
    }

    @Override
    public void unselect() {
	GfxManager.getPlatform().setStroke(this.methodRect,
		ThemeManager.getForegroundColor(), 1);
    }
}
