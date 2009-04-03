package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.BoxArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.webinterface.OptionsManager.QualityLevel;
/**
 * @author  florian
 */
public class ClassArtifact extends BoxArtifact {
    ClassNameArtifact className;
    ClassAttributesArtifact classAttributes;
    ClassMethodsArtifact classMethods;

    private int width;
    public ClassArtifact() {
        this("");
    }
    public ClassArtifact(String className) {
        this.className = new ClassNameArtifact(className);
        this.classAttributes = new ClassAttributesArtifact();
        this.classMethods = new ClassMethodsArtifact();

        this.className.setClassArtifact(this);
        this.classAttributes.setClassArtifact(this);
        this.classMethods.setClassArtifact(this);
    }
    @Override
    public void setCanvas(UMLCanvas canvas) {
        this.canvas = canvas;
        this.className.setCanvas(canvas);
        this.classAttributes.setCanvas(canvas);
        this.classMethods.setCanvas(canvas);
    }

    public void addAttribute(Attribute attribute) {
        classAttributes.add(attribute);
    }
    public void addMethod(Method method) {
        classMethods.add(method);
    }
    public List<Attribute> getAttributes() {
        return classAttributes.getList();
    }
    public String getClassName() {
        return className.getClassName();
    }
    @Override
    public int getHeight() {
        return className.getHeight() + classAttributes.getHeight() + classMethods.getHeight();
    }
    public List<Method> getMethods() {
        return classMethods.getList();
    }
    protected void buildGfxObject() {
        Log.trace("Building GfxObject for "	+ UMLDrawerHelper.getShortName(this));
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, className.initializeGfxObject());
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, classAttributes.initializeGfxObject());
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, classMethods.initializeGfxObject());
        //Computing text bounds :
        className.computeBounds();
        classAttributes.computeBounds();
        classMethods.computeBounds();
        //Searching largest width :
        List<Integer> widthList = new ArrayList<Integer>();
        widthList.add(className.getWidth());
        widthList.add(classAttributes.getWidth());
        widthList.add(classMethods.getWidth());
        int maxWidth = UMLDrawerHelper.getMaxOf(widthList);
        this.width = maxWidth;
        className.setClassWidth(maxWidth);
        classAttributes.setClassWidth(maxWidth);
        classMethods.setClassWidth(maxWidth);
        className.getGfxObject();		
        GfxObject attributesPart = classAttributes.getGfxObject();		
        GfxObject methodsPart = classMethods.getGfxObject();		
        GfxManager.getPlatform().translate(attributesPart, 0, className.getHeight());
        GfxManager.getPlatform().translate(methodsPart, 0, className.getHeight() + classAttributes.getHeight());
        Log.trace("GfxObject is " + gfxObject);
    }
    @Override
    public GfxObject getOutline() {
        if(OptionsManager.qualityLevelIsAlmost(QualityLevel.NORMAL)) {
            GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
            GfxObject path = GfxManager.getPlatform().buildPath();
            GfxManager.getPlatform().setStrokeStyle(path, GfxStyle.DASH);
            GfxManager.getPlatform().setStroke(path, ThemeManager.getHighlightedForegroundColor(), 1);
            GfxManager.getPlatform().setFillColor(path, ThemeManager.getBackgroundColor());
            GfxManager.getPlatform().addToVirtualGroup(vg, path);
            GfxManager.getPlatform().moveTo(path, 0, 0);
            GfxManager.getPlatform().lineTo(path, getWidth(), 0);
            GfxManager.getPlatform().lineTo(path, getWidth(), getHeight());
            GfxManager.getPlatform().lineTo(path, 0, getHeight());
            GfxManager.getPlatform().lineTo(path, 0, 0);
            GfxManager.getPlatform().moveTo(path, 0, className.getHeight());
            GfxManager.getPlatform().lineTo(path, getWidth(), className.getHeight());
            GfxManager.getPlatform().moveTo(path, 0, className.getHeight() + classAttributes.getHeight());
            GfxManager.getPlatform().lineTo(path, getWidth(), className.getHeight() + classAttributes.getHeight());
            return vg;
        } else {
            return super.getOutline();
        }

    }
    @Override
    public int getWidth() {
        return width;
    }
    public void edit(GfxObject gfxObject, int x, int y) {
        if (gfxObject.equals(className.getGfxObject())) {
            Log.warn("Selecting a virtual group : this should not happen !");
            className.edit();
        } else if (gfxObject.equals(classAttributes.getGfxObject())) {
            Log.warn("Selecting a virtual group : this should not happen !");
            classAttributes.edit();
        } else if (gfxObject.equals(classMethods.getGfxObject())) {
            Log.warn("Selecting a virtual group : this should not happen !");
            classMethods.edit(gfxObject, x, y);
        } else if (gfxObject.equals(getGfxObject())) {
            Log.warn("Selecting a virtual group : this should not happen !");
            className.edit();
        } else {
            GfxObject gfxObjectGroup = GfxManager.getPlatform().getGroup(gfxObject);
            if(gfxObjectGroup != null)
            {				
                if (gfxObjectGroup.equals(className.getGfxObject())) {
                    className.edit();
                } else if (gfxObjectGroup.equals(classAttributes.getGfxObject())) {
                    classAttributes.edit();
                } else if (gfxObjectGroup.equals(classMethods.getGfxObject())) {
                    classMethods.edit();
                } else {			
                    gfxObjectGroup = GfxManager.getPlatform().getGroup(gfxObjectGroup);
                    if(gfxObjectGroup != null)
                    {
                        if (gfxObjectGroup.equals(className.getGfxObject())) {
                            className.edit();
                        } else if (gfxObjectGroup.equals(classAttributes.getGfxObject())) {
                            classAttributes.edit(gfxObject, x, y);
                        } else if (gfxObjectGroup.equals(classMethods.getGfxObject())) {
                            classMethods.edit(gfxObject, x, y);
                        } else if (gfxObjectGroup.equals(getGfxObject())) {
                            Log.warn("Selecting the master virtual group : this should NOT happen !");
                            className.edit();
                        } else Log.warn("No editable part found");
                    } else Log.warn("No editable part found");
                }
            }
        }
    }
    public LinkedHashMap<Command, String> getRightMenu() {
        LinkedHashMap<Command, String> rightMenu = new LinkedHashMap<Command, String>();
        Command doNothing = new Command() {
            public void execute() {
            }
        };

        rightMenu.put(null, "Class " + className.getClassName());

        return rightMenu;
    }
    public void select() {
        GfxManager.getPlatform().moveToFront(gfxObject);
        className.select();
        classAttributes.select();
        classMethods.select();
    }
    public void unselect() {
        className.unselect();
        classAttributes.unselect();
        classMethods.unselect();
    }
    @Override
    public void rebuildGfxObject() {
        GfxManager.getPlatform().clearVirtualGroup(className.getGfxObject());
        GfxManager.getPlatform().clearVirtualGroup(classAttributes.getGfxObject());
        GfxManager.getPlatform().clearVirtualGroup(classMethods.getGfxObject());
        GfxManager.getPlatform().clearVirtualGroup(gfxObject);
        super.rebuildGfxObject();
    }
}
