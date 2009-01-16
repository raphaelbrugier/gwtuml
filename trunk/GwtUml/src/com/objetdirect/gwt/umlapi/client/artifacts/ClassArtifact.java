package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.Attribute;
import com.objetdirect.gwt.umlapi.client.Method;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxPlatform;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

public class ClassArtifact extends BoxArtifact {

    public static final int TEXT_MARGIN = 8;
    public static final Object NAME = new Object();
    public static final Object NEW_ATTRIBUTE = new Object();
    public static final Object NEW_METHOD = new Object();

    // interface

    public ClassArtifact() {
        this("");
    }

    public ClassArtifact(String className) {
        this.className = className;
    }

    public void setClassName(String className) {
        this.className = className;
        set(this.classNameText, createClassNameText());
    }

    public void setAttribute(Attribute attr, Attribute newAttr) {
        attr.setName(newAttr.getName());
        attr.setType(newAttr.getType());
        if (attrTexts!=null) {
            int i = attributes.indexOf(attr);
            GfxObject[] slot = attrTexts.get(i);
            set(slot, createAttrText(attr, getAttributeY(attr)));
        }
    }

    public void addAttribute(Attribute attr) {
        this.attributes.add(attr);
        if (attrTexts!=null) {
            GfxObject[] slot = new GfxObject[1];
            attrTexts.add(slot);
            set(slot, createAttrText(attr, getAttributeY(attr)));
        }
    }

    public void exchangeAttribute(Attribute attr1, Attribute attr2) {
        GfxPlatform gPlatform = GfxManager.getInstance();
        int i1 = this.attributes.indexOf(attr1);
        int i2 = this.attributes.indexOf(attr2);
        this.attributes.set(i1, attr2);
        this.attributes.set(i2, attr1);
        if (attrTexts!=null) {
            GfxObject[] slot1 = attrTexts.get(i1);
            GfxObject[] slot2 = attrTexts.get(i2);
            int y1 = (int) gPlatform.getYFor(slot1[0]);
            int y2 = (int) gPlatform.getYFor(slot2[0]);
            gPlatform.translate(slot1[0], 0, y2-y1);
            gPlatform.translate(slot2[0], 0, y1-y2);
            GfxObject t = slot1[0];
            slot1[0]=slot2[0];
            slot2[0]=t;
        }
    }

    public void exchangeMethod(Method method1, Method method2) {
        GfxPlatform gPlatform = GfxManager.getInstance();
        int i1 = this.methods.indexOf(method1);
        int i2 = this.methods.indexOf(method2);
        this.methods.set(i1, method2);
        this.methods.set(i2, method1);
        if (methodTexts!=null) {
            GfxObject[] slot1 = methodTexts.get(i1);
            GfxObject[] slot2 = methodTexts.get(i2);
            int y1 = (int) gPlatform.getYFor(slot1[0]);
            int y2 = (int) gPlatform.getYFor(slot2[0]);
            gPlatform.translate(slot1[0], 0, y2-y1);
            gPlatform.translate(slot2[0], 0, y1-y2);
            GfxObject t = slot1[0];
            slot1[0]=slot2[0];
            slot2[0]=t;
        }
    }

    public boolean removeAttribute(Attribute attr) {
        int i = attributes.indexOf(attr);
        boolean result = attributes.remove(attr);
        if (attrTexts!=null) {
            if (i!=-1) {
                GfxObject[] slot = attrTexts.get(i);
                if (!set(slot, null)) {
                    for (int j=i+1; j<attrTexts.size(); j++) {
                        GfxObject[] slot2 = attrTexts.get(j);
                        GfxManager.getInstance().translate(slot2[0], 0,  -getAttributeHeight());
                    }
                }
                attrTexts.remove(slot);
            }
        }
        return result;
    }

    public void setMethod(Method method, Method newMethod) {
        method.setName(newMethod.getName());
        method.setReturnType(newMethod.getReturnType());
        method.setParameters(newMethod.getParameters());
        if (methodTexts!=null) {
            int i = methods.indexOf(method);
            GfxObject[] slot = methodTexts.get(i);
            set(slot, createMethodText(method, getMethodY(method)));
        }
    }

    public void addMethod(Method method) {
        this.methods.add(method);
        if (methodTexts!=null) {
            GfxObject[] slot = new GfxObject[1];
            methodTexts.add(slot);
            set(slot, createMethodText(method, getMethodY(method)));
        }
    }

    public boolean removeMethod(Method method) {
        int i = methods.indexOf(method);
        boolean result = methods.remove(method);
        if (methodTexts!=null) {
            if (i!=-1) {
                GfxObject[] slot = methodTexts.get(i);
                if (!set(slot, null)) {
                    for (int j=i+1; j<methodTexts.size(); j++) {
                        GfxObject[] slot2 = methodTexts.get(j);
                        GfxManager.getInstance().translate(slot2[0], 0,  -getMethHeight());
                    }
                }
                methodTexts.remove(slot);
            }
        }
        return result;
    }

    public void addRelationship(RelationshipArtifact relationship) {
        this.relationships.add(relationship);
    }

    public Iterator<RelationshipArtifact> relationships() {
        return relationships.iterator();
    }

    public boolean removeRelationship(RelationshipArtifact relationship) {
        return relationships.remove(relationship);
    }

    public void addRelationshipDependency(RelationshipArtifact relationship) {
        relationshipDependencies.add(relationship);
    }

    public boolean removeRelationshipDependency(RelationshipArtifact relationship) {
        return relationshipDependencies.remove(relationship);
    }

    public void addClassDependency(ClassDependencyArtifact clazz) {
        classDependencies.add(clazz);
    }

    public boolean removeClassDependency(ClassDependencyArtifact clazz) {
        return classDependencies.remove(clazz);
    }

    public int getWidth() {
        return computeWidth();
    }

    public int getHeight() {
        int height = getClassNameHeight()+getAttrHeight()+getMethodHeight();
        return height;
    }

    public Object getSubPart(GfxObject o) {
        if (o==classNameText[0])
            return NAME;
        if (o==attrDivRect)
            return NEW_ATTRIBUTE;
        if (o==methodDivRect)
            return NEW_METHOD;
        int i = indexOf(attrTexts, o); 
        if (i!=-1)
            return attributes.get(i);
        i = indexOf(methodTexts, o); 
        if (i!=-1)
            return methods.get(i);
        return null;
    }

    public String getClassName() {
        return className;
    }

    public int getNameY() {
        return TEXT_MARGIN;
    }

    public int getAttributeHeight() {
        return getLineHeight()+TEXT_MARGIN;
    }

    public int getAttributeY(Attribute attribute) {
        return getClassNameHeight()+TEXT_MARGIN+attributes.indexOf(attribute)*getAttributeHeight();	
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public int getMethHeight() {
        return getLineHeight()+TEXT_MARGIN;
    }

    public int getMethodY(Method method) {
        return getClassNameHeight()+getAttrHeight()+TEXT_MARGIN+methods.indexOf(method)*getMethHeight();	
    }

    public List<Method> getMethods() {
        return methods;
    }

    // implementation

    int indexOf(List<GfxObject[]> texts, GfxObject text) {
        for (int i=0; i<texts.size(); i++) {
            GfxObject[] slot = texts.get(i);
            if (slot[0]==text)
                return i;
        }
        return -1;
    }

    protected GfxObject buildGfxObject() {
    	Log.trace("Building GfxObject for " + this);
        GfxPlatform gPlatform = GfxManager.getInstance();
        GfxObject vg = gPlatform.buildVirtualGroup();
        classNameText[0]=createClassNameText();
        createAttrTexts();
        createMethodTexts();
        int width = computeWidth();
        createClassDiv(width);
        createAttrDiv(width);
        createMethodDiv(width);
        gPlatform.addToVirtualGroup(vg, attrDivRect);
        gPlatform.addToVirtualGroup(vg, classDivRect);
        gPlatform.addToVirtualGroup(vg, methodDivRect);
        gPlatform.addToVirtualGroup(vg, classNameText[0]);

        for (int i=0; i<attrTexts.size(); i++)
            gPlatform.addToVirtualGroup(vg, attrTexts.get(i)[0]);
        for (int i=0; i<methodTexts.size(); i++)
            gPlatform.addToVirtualGroup(vg, methodTexts.get(i)[0]);
        return vg;
    }

    void createClassDiv(int width) {
        GfxPlatform gPlatform = GfxManager.getInstance();
        classDivRect = gPlatform.buildRect(width, getClassNameHeight());
        gPlatform.setFillColor(classDivRect, ThemeManager.getBackgroundColor());
        gPlatform.setStroke(classDivRect, ThemeManager.getForegroundColor(), 1);
    }
    
    void createAttrDiv(int width) {
        GfxPlatform gPlatform = GfxManager.getInstance();
        attrDivRect = gPlatform.buildRect(width, getAttrHeight());
        gPlatform.setFillColor(attrDivRect, ThemeManager.getBackgroundColor());
        gPlatform.setStroke(attrDivRect, ThemeManager.getForegroundColor(), 1);
        gPlatform.translate(attrDivRect, 0, getClassNameHeight());

    }

    void createMethodDiv(int width) {
        GfxPlatform gPlatform = GfxManager.getInstance();
        methodDivRect = gPlatform.buildRect(width, getMethodHeight());
        gPlatform.setFillColor(methodDivRect, ThemeManager.getBackgroundColor());
        gPlatform.setStroke(methodDivRect, ThemeManager.getForegroundColor(), 1);
        gPlatform.translate(methodDivRect, 0, getClassNameHeight()+getAttrHeight());
    }
    
    GfxObject createClassNameText() {
        GfxPlatform gPlatform = GfxManager.getInstance();
        GfxObject classNameText = gPlatform.buildText(className);
        gPlatform.setFont(classNameText, font);
        gPlatform.setFillColor(classNameText, ThemeManager.getForegroundColor());
        gPlatform.translate(classNameText, TEXT_MARGIN, getNameY()+getLineHeight());
        return classNameText;
    }
    
    void createAttrTexts() {
        attrTexts = new ArrayList<GfxObject[]>();
        for (int i=0; i<attributes.size(); i++) {
            GfxObject[] attrText = new GfxObject[1];
            Attribute attr = attributes.get(i);
            attrText[0] = createAttrText(attr, getAttributeY(attr)); 
            attrTexts.add(attrText);
        }
    }

    GfxObject createAttrText(Attribute attr, int height) {
        GfxPlatform gPlatform = GfxManager.getInstance();
        GfxObject attrText = gPlatform.buildText(attr.toString());
        gPlatform.setFont(attrText, font);
        gPlatform.setFillColor(attrText, ThemeManager.getForegroundColor());
        gPlatform.translate(attrText, TEXT_MARGIN, height+getLineHeight());
        return attrText;
    }

    void createMethodTexts() {
        methodTexts = new ArrayList<GfxObject[]>();
        for (int i=0; i<methods.size(); i++) {
            GfxObject[] methodText = new GfxObject[1];
            Method method = methods.get(i);
            methodText[0] = createMethodText(method, getMethodY(method)); 
            methodTexts.add(methodText);
        }
    }

    GfxObject createMethodText(Method method, int height) {
        GfxPlatform gPlatform = GfxManager.getInstance();
        GfxObject methodText = gPlatform.buildText(method.toString());
        gPlatform.setFont(methodText, font);
        gPlatform.setFillColor(methodText, ThemeManager.getForegroundColor());
        gPlatform.translate(methodText, TEXT_MARGIN, height+getLineHeight());
        return methodText;
    }

    
    int computeWidth() {
        GfxPlatform gPlatform = GfxManager.getInstance();
        int width = DEFAULT_WIDTH;
        width = updateWidth(width, (int)gPlatform.getWidthFor(classNameText[0]));
        for (int i=0; i<attributes.size(); i++) {
            GfxObject attrText = attrTexts.get(i)[0];
            if (attrText!=null)
                width = updateWidth(width, (int)gPlatform.getWidthFor(attrText));
        }
        for (int i=0; i<methods.size(); i++) {
            GfxObject methodText = methodTexts.get(i)[0];
            if (methodText!=null)
                width = updateWidth(width, (int)gPlatform.getWidthFor(methodText));
        }
        return width;
    }



    int updateWidth(int width, int widthEl) {
        widthEl=widthEl+TEXT_MARGIN+TEXT_MARGIN;
        return width>widthEl ? width : widthEl;
    }

    int getLineHeight() {
        return font.getSize();
    }

    int getClassNameHeight() {
        return TEXT_MARGIN+getLineHeight()+TEXT_MARGIN;
    }

    int getAttrHeight() {
        int size = getSize(attrTexts);
        return TEXT_MARGIN+((size+2)/3)*3*(getLineHeight()+TEXT_MARGIN);
    }

    int getMethodHeight() {
        int size = getSize(methodTexts);
        return TEXT_MARGIN+((size+2)/3)*3*(getLineHeight()+TEXT_MARGIN);
    }

    int getSize(List<GfxObject[]> list) {
        int size = 0;
        for (int i=0; i<list.size(); i++) {
            GfxObject[] slot = list.get(i);
            if (slot[0]!=null)
                size++;
        }
        return size;
    }

    public List<GfxObject> getComponents() {
        List<GfxObject> comps = new ArrayList<GfxObject>();
        comps.add(classDivRect);
        comps.add(classNameText[0]);
        comps.add(attrDivRect);
        for (int i=0; i<attrTexts.size(); i++)
            comps.add(attrTexts.get(i)[0]);
        comps.add(methodDivRect);
        for (int i=0; i<methodTexts.size(); i++)
            comps.add(methodTexts.get(i)[0]);
        return comps;
    }

    public void select() {
        GfxPlatform gPlatform = GfxManager.getInstance();
        gPlatform.setStroke(classDivRect, ThemeManager.getHighlightedForegroundColor(), 2);
        gPlatform.setStroke(attrDivRect, ThemeManager.getHighlightedForegroundColor(), 2);
        gPlatform.setStroke(methodDivRect, ThemeManager.getHighlightedForegroundColor(), 2);
    }

    public void unselect() {
        GfxPlatform gPlatform = GfxManager.getInstance();
        gPlatform.setStroke(classDivRect, ThemeManager.getForegroundColor(), 1);
        gPlatform.setStroke(attrDivRect, ThemeManager.getForegroundColor(), 1);
        gPlatform.setStroke(methodDivRect, ThemeManager.getForegroundColor(), 1);  
    }

    public void adjusted() {	
        super.adjusted();
        for (int i=0; i<relationshipDependencies.size(); i++) {
            final RelationshipArtifact element = relationshipDependencies.get(i);
            new Scheduler.Task(element) {
                public void process() {
                    element.adjust();
                }
            };
        }
        for (int i=0; i<relationships.size(); i++) {
            final RelationshipArtifact element = relationships.get(i);
            new Scheduler.Task(element) {
                public void process() {
                    element.adjust();
                }
            };
        }
        for (int i=0; i<classDependencies.size(); i++) {
            final ClassDependencyArtifact element = classDependencies.get(i);
            new Scheduler.Task(element) {
                public void process() {
                    element.adjust();
                }
            };
        }
    }
    
    @Override
	public GfxObject getOutline() {
		GfxPlatform gPlatform = GfxManager.getInstance();
		GfxObject vg = gPlatform.buildVirtualGroup();
		
		GfxObject line1 = gPlatform.buildLine(0, 0, getWidth(), 0);
		GfxObject line2 = gPlatform.buildLine(getWidth(), 0, getWidth(), getHeight());
		GfxObject line3 = gPlatform.buildLine(getWidth(), getHeight(), 0, getHeight());
		GfxObject line4 = gPlatform.buildLine(0, getHeight(), 0, 0);
		
		GfxObject line5 = gPlatform.buildLine(0, getClassNameHeight(), getWidth(), getClassNameHeight());
		GfxObject line6 = gPlatform.buildLine(0, getClassNameHeight() + getAttrHeight(), getWidth(), getClassNameHeight() + getAttrHeight());
		
		gPlatform.setStrokeStyle(line1, GfxStyle.DASH);
		gPlatform.setStrokeStyle(line2, GfxStyle.DASH);
		gPlatform.setStrokeStyle(line3, GfxStyle.DASH);
		gPlatform.setStrokeStyle(line4, GfxStyle.DASH);
		gPlatform.setStrokeStyle(line5, GfxStyle.DASH);
		gPlatform.setStrokeStyle(line6, GfxStyle.DASH);
		
		GfxManager.getInstance().setStroke(line1, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getInstance().setStroke(line2, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getInstance().setStroke(line3, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getInstance().setStroke(line4, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getInstance().setStroke(line5, ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getInstance().setStroke(line6, ThemeManager.getHighlightedForegroundColor(), 1);
		
		gPlatform.addToVirtualGroup(vg, line1);
		gPlatform.addToVirtualGroup(vg, line2);
		gPlatform.addToVirtualGroup(vg, line3);
		gPlatform.addToVirtualGroup(vg, line4);
		gPlatform.addToVirtualGroup(vg, line5);
		gPlatform.addToVirtualGroup(vg, line6);
		return vg;
	}
	
    String className;
    List<Attribute> attributes = new ArrayList<Attribute>();
    List<RelationshipArtifact> relationships = new ArrayList<RelationshipArtifact>();
    List<Method> methods = new ArrayList<Method>();
    List<RelationshipArtifact> relationshipDependencies = new ArrayList<RelationshipArtifact>();
    List<ClassDependencyArtifact> classDependencies = new ArrayList<ClassDependencyArtifact>();

    GfxObject classDivRect;
    GfxObject[] classNameText = new GfxObject[1];
    GfxObject attrDivRect;
    List<GfxObject[]> attrTexts = null;
    GfxObject methodDivRect;
    List<GfxObject[]> methodTexts = null;
    GfxFont font = new GfxFont("monospace", 10, GfxFont.NORMAL, GfxFont.NORMAL, GfxFont.LIGHTER);
}
