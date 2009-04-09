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
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassPartMethodsArtifact extends ClassPartArtifact {

    private GfxObject lastGfxObject;
    private final Map<GfxObject, Method> methodGfxObjects;
    private GfxObject methodRect;
    private final List<Method> methods;

    public ClassPartMethodsArtifact() {
	methods = new ArrayList<Method>();
	methodGfxObjects = new LinkedHashMap<GfxObject, Method>();
	// List<Parameter> methodParameters = new ArrayList<Parameter>();
	// methodParameters.add(new Parameter("String", "parameter1"));
	// methods.add(new Method("void","method", methodParameters));
	height = 0;
	width = 0;
    }

    public void add(final Method method) {
	methods.add(method);
    }

    @Override
    public void buildGfxObject() {
	if (textVirtualGroup == null) {
	    computeBounds();
	}
	methodRect = GfxManager.getPlatform().buildRect(classWidth, height);
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, methodRect);
	GfxManager.getPlatform().setFillColor(methodRect,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().setStroke(methodRect,
		ThemeManager.getForegroundColor(), 1);
	GfxManager.getPlatform().translate(textVirtualGroup, new Point(
		OptionsManager.getRectangleLeftPadding(),
		OptionsManager.getRectangleTopPadding()));
	GfxManager.getPlatform().moveToFront(textVirtualGroup);
    }

    @Override
    public void computeBounds() {
	methodGfxObjects.clear();
	height = 0;
	width = 0;
	textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
	GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);

	for (final Method method : methods) {
	    final GfxObject methodText = GfxManager.getPlatform().buildText(
		    method.toString());
	    GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup,
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
		    methodText,new Point(
		    OptionsManager.getTextLeftPadding(),
		    OptionsManager.getTextTopPadding() + height
			    + thisMethodHeight));
	    thisMethodWidth += OptionsManager.getTextXTotalPadding();
	    thisMethodHeight += OptionsManager.getTextYTotalPadding();
	    width = thisMethodWidth > width ? thisMethodWidth : width;
	    height += thisMethodHeight;

	    methodGfxObjects.put(methodText, method);
	    lastGfxObject = methodText;
	}
	width += OptionsManager.getRectangleXTotalPadding();
	height += OptionsManager.getRectangleYTotalPadding();

	Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now "
		+ width + "x" + height);
    }

    @Override
    public void edit() {
	final List<Parameter> methodToCreateParameters = new ArrayList<Parameter>();
	methodToCreateParameters.add(new Parameter("String", "parameter1"));
	methods.add(new Method(Visibility.PUBLIC, "void", "method",
		methodToCreateParameters));
	classArtifact.rebuildGfxObject();
	edit(lastGfxObject);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	final Method methodToChange = methodGfxObjects.get(editedGfxObject);
	if (methodToChange == null) {
	    edit();
	} else {
	    final MethodPartEditor editor = new MethodPartEditor(canvas, this,
		    methodToChange);
	    editor
		    .startEdition(
			    methodToChange.toString(),
			    (classArtifact.getLocation().getX()
				    + OptionsManager.getTextLeftPadding() + OptionsManager
				    .getRectangleLeftPadding()), (classArtifact
				    .getLocation().getY()
				    + classArtifact.className.getHeight()
				    + classArtifact.classAttributes.getHeight()
				    + GfxManager.getPlatform().getLocationFor(
					    editedGfxObject).getY()
				    - GfxManager.getPlatform().getHeightFor(
					    editedGfxObject) + OptionsManager
				    .getRectangleTopPadding()), classWidth
				    - OptionsManager.getTextXTotalPadding()
				    - OptionsManager
					    .getRectangleXTotalPadding(), false);
	}
    }

    @Override
    public int getHeight() {
	return height;
    }

    public List<Method> getList() {
	return methods;
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

	for (final Entry<GfxObject, Method> method : methodGfxObjects
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
	return width;
    }

    @Override
    public boolean isDraggable() {
	return false;
    }

    public void remove(final Method method) {
	methods.remove(method);
    }

    @Override
    public void select() {
	GfxManager.getPlatform().setStroke(methodRect,
		ThemeManager.getHighlightedForegroundColor(), 2);
    }

    @Override
    public void setClassWidth(final int width) {
	classWidth = width;
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
	GfxManager.getPlatform().setStroke(methodRect,
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
