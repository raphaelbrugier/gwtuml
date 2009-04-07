package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;
import java.util.ArrayList;
import java.util.HashMap;
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
 * @author  fmounier
 */
public class ClassMethodsArtifact extends ClassPartArtifact {

    private List<Method> methods;
    private Map<GfxObject, Method> methodGfxObjects;
    private GfxObject lastGfxObject;
    private GfxObject methodRect;
    public ClassMethodsArtifact() {
        methods = new ArrayList<Method>();
        methodGfxObjects = new LinkedHashMap<GfxObject, Method>();
        //List<Parameter> methodParameters = new ArrayList<Parameter>();
        //methodParameters.add(new Parameter("String", "parameter1"));
        //methods.add(new Method("void","method", methodParameters));
        height = 0;
        width = 0;
    }

    public void add(Method method) {
        this.methods.add(method);
    }

    public void remove(Method method) {
        methods.remove(method);
    }

    public List<Method> getList() {
        return methods;
    }


    @Override
    public void buildGfxObject() {
        if(textVirtualGroup == null) computeBounds();	
        methodRect = GfxManager.getPlatform().buildRect(classWidth, height);
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, methodRect);
        GfxManager.getPlatform().setFillColor(methodRect,	ThemeManager.getBackgroundColor());
        GfxManager.getPlatform().setStroke(methodRect, ThemeManager.getForegroundColor(), 1);	
        GfxManager.getPlatform().translate(textVirtualGroup, OptionsManager.getRectangleLeftPadding(), OptionsManager.getRectangleTopPadding());
        GfxManager.getPlatform().moveToFront(textVirtualGroup);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }
    @Override
    public void computeBounds() {
        methodGfxObjects.clear();
        height = 0;
        width = 0;
        textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
        GfxManager.getPlatform().addToVirtualGroup(gfxObject, textVirtualGroup);

        for (Method method : methods) {
            GfxObject methodText = GfxManager.getPlatform().buildText(method.toString());
            GfxManager.getPlatform().addToVirtualGroup(textVirtualGroup, methodText);	
            GfxManager.getPlatform().setFont(methodText, OptionsManager.getFont());

            GfxManager.getPlatform().setStroke(methodText, ThemeManager.getBackgroundColor(), 0);
            GfxManager.getPlatform().setFillColor(methodText, ThemeManager.getForegroundColor());
            int thisMethodWidth =  GfxManager.getPlatform().getWidthFor(methodText);
            int thisMethodHeight =  GfxManager.getPlatform().getHeightFor(methodText);

            GfxManager.getPlatform().translate(methodText, OptionsManager.getTextLeftPadding(), OptionsManager.getTextTopPadding() + height + thisMethodHeight);
            thisMethodWidth += OptionsManager.getTextXTotalPadding();
            thisMethodHeight += OptionsManager.getTextYTotalPadding();
            width  = thisMethodWidth > width ? thisMethodWidth : width;
            height += thisMethodHeight;

            methodGfxObjects.put(methodText, method);
            lastGfxObject = methodText;
        }
        width += OptionsManager.getRectangleXTotalPadding();
        height += OptionsManager.getRectangleYTotalPadding();

        Log.trace("WxH for " + UMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
    }
    @Override
    public void setClassWidth(int width) {
        this.classWidth = width;
    }

    @Override
    public void edit() {
        List<Parameter> methodToCreateParameters = new ArrayList<Parameter>();
        methodToCreateParameters.add(new Parameter("String", "parameter1"));
        methods.add(new Method(Visibility.PUBLIC, "void","method", methodToCreateParameters));
        classArtifact.rebuildGfxObject();
        edit(lastGfxObject);
    }
    @Override
    public void edit(GfxObject gfxObject) {
        Method methodToChange = methodGfxObjects.get(gfxObject);
        if(methodToChange == null) edit();
        else {
            MethodPartEditor editor = new MethodPartEditor(canvas, this, methodToChange);
            editor.startEdition(methodToChange.toString(),  (classArtifact.getX() + OptionsManager.getTextLeftPadding() + OptionsManager.getRectangleLeftPadding()),
                    (classArtifact.getY() + classArtifact.className.getHeight() +  classArtifact.classAttributes.getHeight() + GfxManager.getPlatform().getYFor(gfxObject) - GfxManager.getPlatform().getHeightFor(gfxObject) + OptionsManager.getRectangleTopPadding() ), 
                    classWidth - OptionsManager.getTextXTotalPadding() - OptionsManager.getRectangleXTotalPadding(), false);
        }
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
    private Command editCommand(final GfxObject gfxo) {       
        return new Command() {
            public void execute() {
                edit(gfxo);
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
    private Command deleteCommand(final Method  method) {       
        return new Command() {
            public void execute() {
                remove(method);
                classArtifact.rebuildGfxObject();
            }
        };
    }
    @Override
    public MenuBarAndTitle getRightMenu() {
        MenuBarAndTitle rightMenu = new MenuBarAndTitle();
        rightMenu.setName("Methods");

        for(Entry<GfxObject, Method> method : methodGfxObjects.entrySet()) {
            MenuBar subsubMenu = new MenuBar(true);
            subsubMenu.addItem("Edit ", editCommand(method.getKey()));
            subsubMenu.addItem("Delete ", deleteCommand(method.getValue()));
            rightMenu.addItem(method.getValue().toString(), subsubMenu);
        }
        rightMenu.addItem("Add new", editCommand());
        return rightMenu;
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
    public void moveTo(int fx, int fy) {
        // TODO Auto-generated method stub

    }
    @Override
    public void select() {
        GfxManager.getPlatform().setStroke(methodRect, ThemeManager.getHighlightedForegroundColor(), 2);
    }

    @Override
    public void unselect() {
        GfxManager.getPlatform().setStroke(methodRect, ThemeManager.getForegroundColor(), 1);	
    }
}
