package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.Attribute;
import com.objetdirect.gwt.umlapi.client.Method;
import com.objetdirect.gwt.umlapi.client.Scheduler;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.Font;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.Rect;
import com.objetdirect.tatami.client.gfx.Text;
import com.objetdirect.tatami.client.gfx.VirtualGroup;

public class ClassArtifact extends BoxArtifact {

	static final int DEFAULT_WIDTH = 50;
	static final int TEXT_MARGIN = 8;
	public static final Object NAME = new Object();
	public static final Object NEW_ATTRIBUTE = new Object();
	public static final Object NEW_METHOD = new Object();
		
// interface
	
	public ClassArtifact() {
		className = "";
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
			Text[] slot = attrTexts.get(i);
			set(slot, createAttrText(attr, getAttributeY(attr)));
		}
	}

	public void addAttribute(Attribute attr) {
		this.attributes.add(attr);
		if (attrTexts!=null) {
			Text[] slot = new Text[1];
			attrTexts.add(slot);
			set(slot, createAttrText(attr, getAttributeY(attr)));
		}
	}
	
	public void exchangeAttribute(Attribute attr1, Attribute attr2) {
		int i1 = this.attributes.indexOf(attr1);
		int i2 = this.attributes.indexOf(attr2);
		this.attributes.set(i1, attr2);
		this.attributes.set(i2, attr1);
		if (attrTexts!=null) {
			Text[] slot1 = attrTexts.get(i1);
			Text[] slot2 = attrTexts.get(i2);
			int y1 = (int)slot1[0].getY();
			int y2 = (int)slot2[0].getY();
			slot1[0].translate(0, y2-y1);
			slot2[0].translate(0, y1-y2);
			Text t = slot1[0];
			slot1[0]=slot2[0];
			slot2[0]=t;
		}
	}
	
	public void exchangeMethod(Method method1, Method method2) {
		int i1 = this.methods.indexOf(method1);
		int i2 = this.methods.indexOf(method2);
		this.methods.set(i1, method2);
		this.methods.set(i2, method1);
		if (methodTexts!=null) {
			Text[] slot1 = methodTexts.get(i1);
			Text[] slot2 = methodTexts.get(i2);
			int y1 = (int)slot1[0].getY();
			int y2 = (int)slot2[0].getY();
			slot1[0].translate(0, y2-y1);
			slot2[0].translate(0, y1-y2);
			Text t = slot1[0];
			slot1[0]=slot2[0];
			slot2[0]=t;
		}
	}

	public boolean removeAttribute(Attribute attr) {
		int i = attributes.indexOf(attr);
		boolean result = attributes.remove(attr);
		if (attrTexts!=null) {
			if (i!=-1) {
				Text[] slot = attrTexts.get(i);
				if (!set(slot, null)) {
					for (int j=i+1; j<attrTexts.size(); j++) {
						Text[] slot2 = attrTexts.get(j);
						slot2[0].translate(0, -getAttributeHeight());
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
			Text[] slot = methodTexts.get(i);
			set(slot, createMethodText(method, getMethodY(method)));
		}
	}
	
	public void addMethod(Method method) {
		this.methods.add(method);
		if (methodTexts!=null) {
			Text[] slot = new Text[1];
			methodTexts.add(slot);
			set(slot, createMethodText(method, getMethodY(method)));
		}
	}
	
	public boolean removeMethod(Method method) {
		int i = methods.indexOf(method);
		boolean result = methods.remove(method);
		if (methodTexts!=null) {
			if (i!=-1) {
				Text[] slot = methodTexts.get(i);
				if (!set(slot, null)) {
					for (int j=i+1; j<methodTexts.size(); j++) {
						Text[] slot2 = methodTexts.get(j);
						slot2[0].translate(0, -getMethHeight());
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

	public Object getSubPart(GraphicObject o) {
		if (o==classNameText[0])
			return NAME;
		if (o==attrDiv)
			return NEW_ATTRIBUTE;
		if (o==methodDiv)
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
	
	int indexOf(List<Text[]> texts, GraphicObject text) {
		for (int i=0; i<texts.size(); i++) {
			Text[] slot = texts.get(i);
			if (slot[0]==text)
				return i;
		}
		return -1;
	}
	
	protected GraphicObject buildGraphicObject() {
		VirtualGroup vg = new VirtualGroup();
		classNameText[0]=createClassNameText();
		createAttrTexts();
		createMethodTexts();
		int width = computeWidth();
		createClassDiv(width);
		createAttrDiv(width);
		createMethodDiv(width);
		vg.add(attrDiv);
		vg.add(classDiv);
		vg.add(methodDiv);
		vg.add(classNameText[0]);
		for (int i=0; i<attrTexts.size(); i++)
			vg.add(attrTexts.get(i)[0]);
		for (int i=0; i<methodTexts.size(); i++)
			vg.add(methodTexts.get(i)[0]);
		return vg;
	}

	Text createClassNameText() {
		Text classNameText = new Text(className);
		classNameText.setFont(font);
		classNameText.translate(TEXT_MARGIN, getNameY()+getLineHeight());
		return classNameText;
	}
	
	void createClassDiv(int width) {
		classDiv = new Rect(width, getClassNameHeight());
		classDiv.setFillColor(Color.WHITE);
		classDiv.setStroke(Color.BLACK, 1);
	}
	
	void createAttrTexts() {
		attrTexts = new ArrayList<Text[]>();
		for (int i=0; i<attributes.size(); i++) {
			Text[] attrText = new Text[1];
			Attribute attr = attributes.get(i);
			attrText[0] = createAttrText(attr, getAttributeY(attr)); 
			attrTexts.add(attrText);
		}
	}
	
	Text createAttrText(Attribute attr, int height) {
		Text attrText = new Text(attr.toString());
		attrText.setFont(font);
		attrText.translate(TEXT_MARGIN, height+getLineHeight());
		return attrText;
	}
	
	void createMethodTexts() {
		methodTexts = new ArrayList<Text[]>();
		for (int i=0; i<methods.size(); i++) {
			Text[] methodText = new Text[1];
			Method method = methods.get(i);
			methodText[0] = createMethodText(method, getMethodY(method)); 
			methodTexts.add(methodText);
		}
	}

	Text createMethodText(Method method, int height) {
		Text methodText = new Text(method.toString());
		methodText.setFont(font);
		methodText.translate(TEXT_MARGIN, height+getLineHeight());
		return methodText;
	}

	int computeWidth() {
		int width = DEFAULT_WIDTH;
		width = updateWidth(width, (int)classNameText[0].getWidth());
		for (int i=0; i<attributes.size(); i++) {
			Text attrText = attrTexts.get(i)[0];
			if (attrText!=null)
				width = updateWidth(width, (int)attrText.getWidth());
		}
		for (int i=0; i<methods.size(); i++) {
			Text methodText = methodTexts.get(i)[0];
			if (methodText!=null)
				width = updateWidth(width, (int)methodText.getWidth());
		}
		return width;
	}
	
	void createAttrDiv(int width) {
		attrDiv = new Rect(width, getAttrHeight());
		attrDiv.setFillColor(Color.WHITE);
		attrDiv.setStroke(Color.BLACK, 1);
		attrDiv.translate(0, getClassNameHeight());
	}

	void createMethodDiv(int width) {
		methodDiv = new Rect(width, getMethodHeight());
		methodDiv.setFillColor(Color.WHITE);
		methodDiv.setStroke(Color.BLACK, 1);
		methodDiv.translate(0, getClassNameHeight()+getAttrHeight());
	}

	int updateWidth(int width, int widthEl) {
		widthEl=widthEl/4*3+TEXT_MARGIN+TEXT_MARGIN;
		System.out.println(width+" ?= "+widthEl);
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

	int getSize(List<Text[]> list) {
		int size = 0;
		for (int i=0; i<list.size(); i++) {
			Text[] slot = list.get(i);
			if (slot[0]!=null)
				size++;
		}
		return size;
	}
	
	public List<GraphicObject> getComponents() {
		List<GraphicObject> comps = new ArrayList<GraphicObject>();
		comps.add(classDiv);
		comps.add(classNameText[0]);
		comps.add(attrDiv);
		for (int i=0; i<attrTexts.size(); i++)
			comps.add(attrTexts.get(i)[0]);
		comps.add(methodDiv);
		for (int i=0; i<methodTexts.size(); i++)
			comps.add(methodTexts.get(i)[0]);
		return comps;
	}
	
	public void select() {
		classDiv.setStroke(Color.BLUE, 2);
		attrDiv.setStroke(Color.BLUE, 2);
		methodDiv.setStroke(Color.BLUE, 2);
	}

	public void unselect() {
		classDiv.setStroke(Color.BLACK, 1);
		attrDiv.setStroke(Color.BLACK, 1);
		methodDiv.setStroke(Color.BLACK, 1);
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

	String className;
	List<Attribute> attributes = new ArrayList<Attribute>();
	List<RelationshipArtifact> relationships = new ArrayList<RelationshipArtifact>();
	List<Method> methods = new ArrayList<Method>();
	List<RelationshipArtifact> relationshipDependencies = new ArrayList<RelationshipArtifact>();
	List<ClassDependencyArtifact> classDependencies = new ArrayList<ClassDependencyArtifact>();
	
	Rect classDiv;
	Text[] classNameText = new Text[1];
	Rect attrDiv;
	List<Text[]> attrTexts = null;
	Rect methodDiv;
	List<Text[]> methodTexts = null;
	
	Font font = new Font("Verdana", 10, Font.NORMAL, Font.NORMAL, Font.LIGHTER);
}
