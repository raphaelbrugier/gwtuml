package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassAttributesArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent.ClassNameArtifact;
import com.objetdirect.gwt.umlapi.client.editors.ClassEditor;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

public class ClassArtifact extends BoxArtifact {

	public enum ClassArtifactPart {
		ATTRIBUTE, METHOD, NAME, NEW_ATTRIBUTE, NEW_METHOD, NONE;

		private int slotIndex;

		private ClassArtifactPart() {
			this.slotIndex = -2;
		}

		public int getSlotIndex() {
			return slotIndex;
		}

		public void setSlotIndex(int slotIndex) {
			this.slotIndex = slotIndex;
		}
	}

	public ClassNameArtifact className;
	private ClassAttributesArtifact classAttributes;
	private ClassMethodsArtifact classMethods;
	private ClassEditor editor;
	private List<ClassDependencyArtifact> classDependencies = new ArrayList<ClassDependencyArtifact>();
	private List<RelationshipArtifact> relationshipDependencies = new ArrayList<RelationshipArtifact>();
	private List<RelationshipArtifact> relationships = new ArrayList<RelationshipArtifact>();
	private int width;
	
	public ClassArtifact() {
		this("");
	}

	public ClassArtifact(String className) {
		this.className = new ClassNameArtifact(className);
		this.classAttributes = new ClassAttributesArtifact();
		this.classMethods = new ClassMethodsArtifact();
		//this.editor = new ClassEditor(this);
	}
	
	public void addAttribute(Attribute attribute) {
		classAttributes.add(attribute);
	}
	
	
	public void addMethod(Method method) {
		classMethods.add(method);
	}
	
	public void addClassDependency(ClassDependencyArtifact clazz) {
		classDependencies.add(clazz);
	}
	
	public void addRelationship(RelationshipArtifact relationship) {
		this.relationships.add(relationship);
	}

	public void addRelationshipDependency(RelationshipArtifact relationship) {
		relationshipDependencies.add(relationship);
	}
	
	@Override
	public void adjusted() {
		super.adjusted();
		for (int i = 0; i < relationshipDependencies.size(); i++) {
			final RelationshipArtifact element = relationshipDependencies
					.get(i);
			new Scheduler.Task(element) {
				@Override
				public void process() {
					element.adjust();
				}
			};
		}
		for (int i = 0; i < relationships.size(); i++) {
			final RelationshipArtifact element = relationships.get(i);
			new Scheduler.Task(element) {
				@Override
				public void process() {
					element.adjust();
				}
			};
		}
		for (int i = 0; i < classDependencies.size(); i++) {
			final ClassDependencyArtifact element = classDependencies.get(i);
			new Scheduler.Task(element) {
				@Override
				public void process() {
					element.adjust();
				}
			};
		}
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
		
		GfxObject namePart = className.getGfxObject();		
		GfxObject attributesPart = classAttributes.getGfxObject();		
		GfxObject methodsPart = classMethods.getGfxObject();		

		GfxManager.getPlatform().translate(attributesPart, 0, className.getHeight());
		GfxManager.getPlatform().translate(methodsPart, 0, className.getHeight() + classAttributes.getHeight());

	

		Log.trace("GfxObject is " + gfxObject);
	}
	
	@Override
	public GfxObject getOutline() {

		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();

		GfxObject line1 = GfxManager.getPlatform().buildLine(0, 0, getWidth(), 0);
		GfxManager.getPlatform().addToVirtualGroup(vg, line1);
		
		GfxObject line2 = GfxManager.getPlatform().buildLine(getWidth(), 0,	getWidth(), getHeight());
		GfxManager.getPlatform().addToVirtualGroup(vg, line2);
		
		GfxObject line3 = GfxManager.getPlatform().buildLine(getWidth(), getHeight(), 0, getHeight());
		GfxManager.getPlatform().addToVirtualGroup(vg, line3);
		
		GfxObject line4 = GfxManager.getPlatform().buildLine(0, getHeight(), 0,	0);
		GfxManager.getPlatform().addToVirtualGroup(vg, line4);

		GfxObject line5 = GfxManager.getPlatform().buildLine(0,	className.getHeight(), getWidth(), className.getHeight());
		GfxManager.getPlatform().addToVirtualGroup(vg, line5);
		
		GfxObject line6 = GfxManager.getPlatform().buildLine(0,	className.getHeight() + classAttributes.getHeight(), getWidth(), className.getHeight() + classAttributes.getHeight());
		GfxManager.getPlatform().addToVirtualGroup(vg, line6);

		GfxManager.getPlatform().setStrokeStyle(line1, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(line2, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(line3, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(line4, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(line5, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(line6, GfxStyle.DASH);

		GfxManager.getPlatform().setStroke(line1,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(line2,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(line3,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(line4,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(line5,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(line6,
				ThemeManager.getHighlightedForegroundColor(), 1);

		return vg;
	}
	
	public boolean removeRelationship(RelationshipArtifact relationship) {
		return relationships.remove(relationship);
	}

	public boolean removeRelationshipDependency(
			RelationshipArtifact relationship) {
		return relationshipDependencies.remove(relationship);
	}

	public boolean removeClassDependency(ClassDependencyArtifact clazz) {
		return classDependencies.remove(clazz);
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	public ClassArtifactPart getSubPart(GfxObject o) {
		Log.trace("Trying to find subpart of " + o + "\n\tname is "
				+ className.getClassName() + "\n\tattr is " + classAttributes.getGfxObject()
				+ "\n\t method is " + classMethods.getGfxObject());

		if (o.equals(className.getGfxObject()))
			return ClassArtifactPart.NAME;

		if (o.equals(classAttributes.getGfxObject()))
			return ClassArtifactPart.NEW_ATTRIBUTE;

		if (o.equals(classMethods.getGfxObject()))
			return ClassArtifactPart.NEW_METHOD;

		if (o.equals(getGfxObject()))
			return ClassArtifactPart.NAME;
/*
		int attrTextIndex = indexOf(attrTexts, o);
		if (attrTextIndex != -1) {
			ClassArtifactPart classArtifactPart = ClassArtifactPart.ATTRIBUTE;
			classArtifactPart.setSlotIndex(attrTextIndex);
			return classArtifactPart;
		}

		int methodTextIndex = indexOf(methodTexts, o);
		if (methodTextIndex != -1) {
			ClassArtifactPart classArtifactPart = ClassArtifactPart.METHOD;
			classArtifactPart.setSlotIndex(methodTextIndex);
			return classArtifactPart;
		}*/

		return ClassArtifactPart.NONE;
	}
	
	public void edit(GfxObject gfxObject, int x, int y) {
		/*ClassArtifactPart subPart = getSubPart(gfxObject);
		if (subPart == null) {
			Log.debug("No subpart found");
			return;
		}
		editor.setSubPart(subPart);
		switch (subPart) {
		case NONE:
		case NAME:
			editor.editName();
			break;
		case NEW_ATTRIBUTE:
			editor.editNewAttribute();
			break;
		case NEW_METHOD:
			editor.editNewMethod();
			break;
		case ATTRIBUTE:
			editor.editAttribute(attributes.get(subPart.getSlotIndex()));
			break;
		case METHOD:
			editor.editMethod(methods.get(subPart.getSlotIndex()));
			break;
		}*/
	}

	public LinkedHashMap<String, Command> getRightMenu() {

		LinkedHashMap<String, Command> rightMenu = new LinkedHashMap<String, Command>();

		Command doNothing = new Command() {
			public void execute() {
			}
		};
		Command remove = new Command() {
			public void execute() {
				getCanvas().removeSelected();
			}
		};
		rightMenu.put("Class " + className.getClassName(), doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Rename", doNothing);
		rightMenu.put("> Edit attribute", doNothing);
		rightMenu.put("> Edit method", doNothing);
		rightMenu.put("> Delete", remove);
		return rightMenu;
	}

	public void select() {
		GfxManager.getPlatform().moveToFront(gfxObject);
		GfxManager.getPlatform().setStroke(className.getGfxObject(), ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(classAttributes.getGfxObject(),	ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(classMethods.getGfxObject(), ThemeManager.getHighlightedForegroundColor(), 2);
		
	}

	public void unselect() {
		GfxManager.getPlatform().setStroke(className.getGfxObject(),
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(classAttributes.getGfxObject(),
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(classMethods.getGfxObject(),
				ThemeManager.getForegroundColor(), 1);
	}

}
