package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.BoxArtifact;
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
 * @author florian
 */
public class ClassArtifact extends BoxArtifact {
    ClassAttributesArtifact classAttributes;
    ClassMethodsArtifact classMethods;
    ClassNameArtifact className;

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
	this.className = new ClassNameArtifact(className);
	this.classAttributes = new ClassAttributesArtifact();
	this.classMethods = new ClassMethodsArtifact();

	this.className.setClassArtifact(this);
	this.classAttributes.setClassArtifact(this);
	this.classMethods.setClassArtifact(this);
    }

    /**
     * @param attribute
     */
    public void addAttribute(final Attribute attribute) {
	this.classAttributes.add(attribute);
    }

    /**
     * @param method
     */
    public void addMethod(final Method method) {
	this.classMethods.add(method);
    }

    @Override
    protected void buildGfxObject() {
	Log.trace("Building GfxObject for "
		+ UMLDrawerHelper.getShortName(this));
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject,
		this.className.initializeGfxObject());
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject,
		this.classAttributes.initializeGfxObject());
	GfxManager.getPlatform().addToVirtualGroup(this.gfxObject,
		this.classMethods.initializeGfxObject());
	// Computing text bounds :
	this.className.computeBounds();
	this.classAttributes.computeBounds();
	this.classMethods.computeBounds();
	// Searching largest width :
	final List<Integer> widthList = new ArrayList<Integer>();
	widthList.add(this.className.getWidth());
	widthList.add(this.classAttributes.getWidth());
	widthList.add(this.classMethods.getWidth());
	final int maxWidth = UMLDrawerHelper.getMaxOf(widthList);
	this.width = maxWidth;
	this.className.setClassWidth(maxWidth);
	this.classAttributes.setClassWidth(maxWidth);
	this.classMethods.setClassWidth(maxWidth);
	this.className.getGfxObject();
	final GfxObject attributesPart = this.classAttributes.getGfxObject();
	final GfxObject methodsPart = this.classMethods.getGfxObject();
	GfxManager.getPlatform().translate(attributesPart, 0,
		this.className.getHeight());
	GfxManager.getPlatform().translate(methodsPart, 0,
		this.className.getHeight() + this.classAttributes.getHeight());
	Log.trace("GfxObject is " + this.gfxObject);
    }

    @Override
    public void edit(final GfxObject editedGfxObject) {
	if (editedGfxObject.equals(this.className.getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    this.className.edit();
	} else if (editedGfxObject.equals(this.classAttributes.getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    this.classAttributes.edit();
	} else if (editedGfxObject.equals(this.classMethods.getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    this.classMethods.edit(editedGfxObject);
	} else if (editedGfxObject.equals(getGfxObject())) {
	    Log.warn("Selecting a virtual group : this should not happen !");
	    this.className.edit();
	} else {
	    GfxObject gfxObjectGroup = GfxManager.getPlatform().getGroup(
		    editedGfxObject);
	    if (gfxObjectGroup != null) {
		if (gfxObjectGroup.equals(this.className.getGfxObject())) {
		    this.className.edit();
		} else if (gfxObjectGroup
			.equals(this.classAttributes.getGfxObject())) {
		    this.classAttributes.edit();
		} else if (gfxObjectGroup.equals(this.classMethods.getGfxObject())) {
		    this.classMethods.edit();
		} else {
		    gfxObjectGroup = GfxManager.getPlatform().getGroup(
			    gfxObjectGroup);
		    if (gfxObjectGroup != null) {
			if (gfxObjectGroup.equals(this.className.getGfxObject())) {
			    this.className.edit(editedGfxObject);
			} else if (gfxObjectGroup.equals(this.classAttributes
				.getGfxObject())) {
			    this.classAttributes.edit(editedGfxObject);
			} else if (gfxObjectGroup.equals(this.classMethods
				.getGfxObject())) {
			    this.classMethods.edit(editedGfxObject);
			} else if (gfxObjectGroup.equals(getGfxObject())) {
			    Log
				    .warn("Selecting the master virtual group : this should NOT happen !");
			    this.className.edit();
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
	return this.classAttributes.getList();
    }

    /**
     * @return the name of this class
     */
    public String getClassName() {
	return this.className.getClassName();
    }

    @Override
    public int getHeight() {
	return this.className.getHeight() + this.classAttributes.getHeight()
		+ this.classMethods.getHeight();
    }

    /**
     * @return the list of methods of this class
     */
    public List<Method> getMethods() {
	return this.classMethods.getList();
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
	    GfxManager.getPlatform().moveTo(path, 0, 0);
	    GfxManager.getPlatform().lineTo(path, getWidth(), 0);
	    GfxManager.getPlatform().lineTo(path, getWidth(), getHeight());
	    GfxManager.getPlatform().lineTo(path, 0, getHeight());
	    GfxManager.getPlatform().lineTo(path, 0, 0);
	    GfxManager.getPlatform().moveTo(path, 0, this.className.getHeight());
	    GfxManager.getPlatform().lineTo(path, getWidth(),
		    this.className.getHeight());
	    GfxManager.getPlatform().moveTo(path, 0,
		    this.className.getHeight() + this.classAttributes.getHeight());
	    GfxManager.getPlatform().lineTo(path, getWidth(),
		    this.className.getHeight() + this.classAttributes.getHeight());
	    return vg;
	}
	return super.getOutline();

    }

    @Override
    public MenuBarAndTitle getRightMenu() {
	final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
	final MenuBarAndTitle classNameRightMenu = this.className.getRightMenu();
	final MenuBarAndTitle classAttributesRightMenu = this.classAttributes
		.getRightMenu();
	final MenuBarAndTitle classMethodsRightMenu = this.classMethods
		.getRightMenu();

	rightMenu.setName("Class " + this.className.getClassName());

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
	return this.width;
    }

    @Override
    public void rebuildGfxObject() {
	GfxManager.getPlatform().clearVirtualGroup(this.className.getGfxObject());
	GfxManager.getPlatform().clearVirtualGroup(
		this.classAttributes.getGfxObject());
	GfxManager.getPlatform().clearVirtualGroup(this.classMethods.getGfxObject());
	GfxManager.getPlatform().clearVirtualGroup(this.gfxObject);
	super.rebuildGfxObject();
    }

    @Override
    public void select() {
	GfxManager.getPlatform().moveToFront(this.gfxObject);
	this.className.select();
	this.classAttributes.select();
	this.classMethods.select();
    }

    @Override
    public void setCanvas(final UMLCanvas canvas) {
	this.canvas = canvas;
	this.className.setCanvas(canvas);
	this.classAttributes.setCanvas(canvas);
	this.classMethods.setCanvas(canvas);
    }

    @Override
    public void unselect() {
	this.className.unselect();
	this.classAttributes.unselect();
	this.classMethods.unselect();
    }
}
