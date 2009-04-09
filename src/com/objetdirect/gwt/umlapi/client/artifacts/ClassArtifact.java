package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager.QualityLevel;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ClassArtifact extends BoxArtifact {
    ClassPartAttributesArtifact classAttributes;
    ClassPartMethodsArtifact classMethods;
    ClassPartNameArtifact className;

    private int width;

    /**
     * 
     */
    public ClassArtifact() {
	this("");
    }

    /**
     * @param className
     */
    public ClassArtifact(final String className) {
	this.className = new ClassPartNameArtifact(className);
	classAttributes = new ClassPartAttributesArtifact();
	classMethods = new ClassPartMethodsArtifact();

	this.className.setClassArtifact(this);
	classAttributes.setClassArtifact(this);
	classMethods.setClassArtifact(this);
    }

    /**
     * @param attribute
     */
    public void addAttribute(final Attribute attribute) {
	classAttributes.add(attribute);
    }

    /**
     * @param method
     */
    public void addMethod(final Method method) {
	classMethods.add(method);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	if (editedGfxObject.equals(className.getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    className.edit();
	} else if (editedGfxObject.equals(classAttributes.getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    classAttributes.edit();
	} else if (editedGfxObject.equals(classMethods.getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    classMethods.edit(editedGfxObject);
	} else if (editedGfxObject.equals(getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    className.edit();
	} else {
	    GfxObject gfxObjectGroup = GfxManager.getPlatform().getGroup(
		    editedGfxObject);
	    if (gfxObjectGroup != null) {
		if (gfxObjectGroup.equals(className.getGfxObject())) {
		    className.edit();
		} else if (gfxObjectGroup
			.equals(classAttributes.getGfxObject())) {
		    classAttributes.edit();
		} else if (gfxObjectGroup.equals(classMethods.getGfxObject())) {
		    classMethods.edit();
		} else {
		    gfxObjectGroup = GfxManager.getPlatform().getGroup(
			    gfxObjectGroup);
		    if (gfxObjectGroup != null) {
			if (gfxObjectGroup.equals(className.getGfxObject())) {
			    className.edit(editedGfxObject);
			} else if (gfxObjectGroup.equals(classAttributes
				.getGfxObject())) {
			    classAttributes.edit(editedGfxObject);
			} else if (gfxObjectGroup.equals(classMethods
				.getGfxObject())) {
			    classMethods.edit(editedGfxObject);
			} else if (gfxObjectGroup.equals(getGfxObject())) {
			    Log
				    .warn("Selecting the master virtual group : this should NOT happen !");
			    className.edit();
			} else {
			    Log.warn("No editable part found");
			}
		    } else {
			Log.warn("No editable part found");
		    }
		}
	    }
	}
    }

    /**
     * @return the list of attributes of this class
     */
    public List<Attribute> getAttributes() {
	return classAttributes.getList();
    }

    /**
     * @return the name of this class
     */
    public String getClassName() {
	return className.getClassName();
    }

    @Override
    public int getHeight() {
	return className.getHeight() + classAttributes.getHeight()
		+ classMethods.getHeight();
    }

    /**
     * @return the list of methods of this class
     */
    public List<Method> getMethods() {
	return classMethods.getList();
    }

    @Override
    public GfxObject getOutline() {
	if (OptionsManager.qualityLevelIsAlmost(QualityLevel.NORMAL)) {
	    final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
	    final GfxObject path = GfxManager.getPlatform().buildPath();
	    GfxManager.getPlatform().setStrokeStyle(path, GfxStyle.DASH);
	    GfxManager.getPlatform().setStroke(path,
		    ThemeManager.getHighlightedForegroundColor(), 1);
	    GfxManager.getPlatform().setFillColor(path,
		    ThemeManager.getBackgroundColor());
	    GfxManager.getPlatform().addToVirtualGroup(vg, path);
	    GfxManager.getPlatform().moveTo(path, Point.getOrigin());
	    GfxManager.getPlatform().lineTo(path, new Point(getWidth(), 0));
	    GfxManager.getPlatform().lineTo(path, new Point(getWidth(), getHeight()));
	    GfxManager.getPlatform().lineTo(path, new Point(0, getHeight()));
	    GfxManager.getPlatform().lineTo(path, Point.getOrigin());
	    GfxManager.getPlatform().moveTo(path, new Point(0, className.getHeight()));
	    GfxManager.getPlatform().lineTo(path, new Point(getWidth(),
		    className.getHeight()));
	    GfxManager.getPlatform().moveTo(path, new Point(0,
		    className.getHeight() + classAttributes.getHeight()));
	    GfxManager.getPlatform().lineTo(path, new Point(getWidth(),
		    className.getHeight() + classAttributes.getHeight()));
	    return vg;
	}
	return super.getOutline();

    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	final MenuBarAndTitle classNameRightMenu = className.getRightMenu();
	final MenuBarAndTitle classAttributesRightMenu = classAttributes
		.getRightMenu();
	final MenuBarAndTitle classMethodsRightMenu = classMethods
		.getRightMenu();

	rightMenu.setName("Class " + className.getClassName());

	rightMenu.addItem(classNameRightMenu.getName(), classNameRightMenu
		.getSubMenu());
	rightMenu.addItem(classAttributesRightMenu.getName(),
		classAttributesRightMenu.getSubMenu());
	rightMenu.addItem(classMethodsRightMenu.getName(),
		classMethodsRightMenu.getSubMenu());

	return rightMenu;
    }

    @Override
    public int getWidth() {
	return width;
    }

    @Override
    public void rebuildGfxObject() {
	GfxManager.getPlatform().clearVirtualGroup(className.getGfxObject());
	GfxManager.getPlatform().clearVirtualGroup(
		classAttributes.getGfxObject());
	GfxManager.getPlatform().clearVirtualGroup(classMethods.getGfxObject());
	GfxManager.getPlatform().clearVirtualGroup(gfxObject);
	super.rebuildGfxObject();
    }

    @Override
    public void select() {
	GfxManager.getPlatform().moveToFront(gfxObject);
	className.select();
	classAttributes.select();
	classMethods.select();
    }

    @Override
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
	className.setCanvas(canvas);
	classAttributes.setCanvas(canvas);
	classMethods.setCanvas(canvas);
    }

    @Override
    public void unselect() {
	className.unselect();
	classAttributes.unselect();
	classMethods.unselect();
    }

    @Override
    protected void buildGfxObject() {
	Log.trace("Building GfxObject for "
		+ UMLDrawerHelper.getShortName(this));
	GfxManager.getPlatform().addToVirtualGroup(gfxObject,
		className.initializeGfxObject());
	GfxManager.getPlatform().addToVirtualGroup(gfxObject,
		classAttributes.initializeGfxObject());
	GfxManager.getPlatform().addToVirtualGroup(gfxObject,
		classMethods.initializeGfxObject());
	// Computing text bounds :
	className.computeBounds();
	classAttributes.computeBounds();
	classMethods.computeBounds();
	// Searching largest width :
	final List<Integer> widthList = new ArrayList<Integer>();
	widthList.add(className.getWidth());
	widthList.add(classAttributes.getWidth());
	widthList.add(classMethods.getWidth());
	final int maxWidth = UMLDrawerHelper.getMaxOf(widthList);
	width = maxWidth;
	className.setClassWidth(maxWidth);
	classAttributes.setClassWidth(maxWidth);
	classMethods.setClassWidth(maxWidth);
	className.getGfxObject();
	final GfxObject attributesPart = classAttributes.getGfxObject();
	final GfxObject methodsPart = classMethods.getGfxObject();
	GfxManager.getPlatform().translate(attributesPart, new Point(0,
		className.getHeight()));
	GfxManager.getPlatform().translate(methodsPart, new Point(0,
		className.getHeight() + classAttributes.getHeight()));
	Log.trace("GfxObject is " + gfxObject);
    }
}
