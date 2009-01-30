package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.Attribute;
import com.objetdirect.gwt.umlapi.client.Method;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.editors.ClassEditor;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
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

	// interface

	public static final int TEXT_MARGIN = 8;

	GfxObject attrDivRect;

	List<Attribute> attributes = new ArrayList<Attribute>();

	List<GfxObject[]> attrTexts = null;

	List<ClassDependencyArtifact> classDependencies = new ArrayList<ClassDependencyArtifact>();

	GfxObject classDivRect;

	String className;

	GfxObject[] classNameText = new GfxObject[1];

	GfxObject methodDivRect;

	List<Method> methods = new ArrayList<Method>();

	List<GfxObject[]> methodTexts = null;

	List<RelationshipArtifact> relationshipDependencies = new ArrayList<RelationshipArtifact>();

	List<RelationshipArtifact> relationships = new ArrayList<RelationshipArtifact>();

	private ClassEditor editor;

	public ClassArtifact() {
		this("");
	}

	public ClassArtifact(String className) {
		this.className = className;
		this.editor = new ClassEditor(this);
	}

	public void addAttribute(Attribute attr) {
		this.attributes.add(attr);
		if (attrTexts != null) {
			GfxObject[] slot = new GfxObject[1];
			attrTexts.add(slot);
			set(slot, createAttrText(attr, getAttributeY(attr)));
		}
	}

	public void addClassDependency(ClassDependencyArtifact clazz) {
		classDependencies.add(clazz);
	}

	public void addMethod(Method method) {
		this.methods.add(method);
		if (methodTexts != null) {
			GfxObject[] slot = new GfxObject[1];
			methodTexts.add(slot);
			set(slot, createMethodText(method, getMethodY(method)));
		}
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

	public void edit(GfxObject gfxObject, int x, int y) {
		ClassArtifactPart subPart = getSubPart(gfxObject);
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
		}
	}

	public void exchangeAttribute(int attribute1Slot, int attribute2Slot) {

		Attribute attribute1 = attributes.get(attribute1Slot);
		Attribute attribute2 = attributes.get(attribute2Slot);
		this.attributes.set(attribute2Slot, attribute1);
		this.attributes.set(attribute1Slot, attribute2);
		if (attrTexts != null) {
			GfxObject[] slot1 = attrTexts.get(attribute1Slot);
			GfxObject[] slot2 = attrTexts.get(attribute2Slot);
			int y1 = (int) GfxManager.getPlatform().getYFor(slot1[0]);
			int y2 = (int) GfxManager.getPlatform().getYFor(slot2[0]);
			GfxManager.getPlatform().translate(slot1[0], 0, y2 - y1);
			GfxManager.getPlatform().translate(slot2[0], 0, y1 - y2);
			GfxObject t = slot1[0];
			slot1[0] = slot2[0];
			slot2[0] = t;
		}
	}

	public void exchangeMethod(int method1Slot, int method2Slot) {
		Method method1 = methods.get(method1Slot);
		Method method2 = methods.get(method2Slot);
		this.methods.set(method1Slot, method2);
		this.methods.set(method2Slot, method1);
		if (methodTexts != null) {
			GfxObject[] slot1 = methodTexts.get(method1Slot);
			GfxObject[] slot2 = methodTexts.get(method2Slot);
			int y1 = (int) GfxManager.getPlatform().getYFor(slot1[0]);
			int y2 = (int) GfxManager.getPlatform().getYFor(slot2[0]);
			GfxManager.getPlatform().translate(slot1[0], 0, y2 - y1);
			GfxManager.getPlatform().translate(slot2[0], 0, y1 - y2);
			GfxObject t = slot1[0];
			slot1[0] = slot2[0];
			slot2[0] = t;
		}
	}

	public int getAttributeHeight() {
		return getLineHeight() + TEXT_MARGIN;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public int getAttributeY(Attribute attribute) {
		return getClassNameHeight() + TEXT_MARGIN
				+ attributes.indexOf(attribute) * getAttributeHeight();
	}

	public int getAttributeY(int attributeSlot) {
		return getClassNameHeight() + TEXT_MARGIN + attributeSlot
				* getAttributeHeight();
	}

	public String getClassName() {
		return className;
	}

	// implementation

	public List<GfxObject> getComponents() {
		List<GfxObject> comps = new ArrayList<GfxObject>();
		comps.add(classDivRect);
		comps.add(classNameText[0]);
		comps.add(attrDivRect);
		for (int i = 0; i < attrTexts.size(); i++)
			comps.add(attrTexts.get(i)[0]);
		comps.add(methodDivRect);
		for (int i = 0; i < methodTexts.size(); i++)
			comps.add(methodTexts.get(i)[0]);
		return comps;
	}

	@Override
	public int getHeight() {
		int height = getClassNameHeight() + getAttrHeight() + getMethodHeight();
		return height;
	}

	public int getMethHeight() {
		return getLineHeight() + TEXT_MARGIN;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public int getMethodY(int methodSlot) {
		return getClassNameHeight() + getAttrHeight() + TEXT_MARGIN
				+ methodSlot * getMethHeight();
	}

	public int getMethodY(Method method) {
		return getClassNameHeight() + getAttrHeight() + TEXT_MARGIN
				+ methods.indexOf(method) * getMethHeight();
	}

	public int getNameY() {
		return TEXT_MARGIN;
	}

	@Override
	public GfxObject getOutline() {

		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();

		GfxObject line1 = GfxManager.getPlatform().buildLine(0, 0, getWidth(),
				0);
		GfxObject line2 = GfxManager.getPlatform().buildLine(getWidth(), 0,
				getWidth(), getHeight());
		GfxObject line3 = GfxManager.getPlatform().buildLine(getWidth(),
				getHeight(), 0, getHeight());
		GfxObject line4 = GfxManager.getPlatform().buildLine(0, getHeight(), 0,
				0);

		GfxObject line5 = GfxManager.getPlatform().buildLine(0,
				getClassNameHeight(), getWidth(), getClassNameHeight());
		GfxObject line6 = GfxManager.getPlatform().buildLine(0,
				getClassNameHeight() + getAttrHeight(), getWidth(),
				getClassNameHeight() + getAttrHeight());

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

		GfxManager.getPlatform().addToVirtualGroup(vg, line1);
		GfxManager.getPlatform().addToVirtualGroup(vg, line2);
		GfxManager.getPlatform().addToVirtualGroup(vg, line3);
		GfxManager.getPlatform().addToVirtualGroup(vg, line4);
		GfxManager.getPlatform().addToVirtualGroup(vg, line5);
		GfxManager.getPlatform().addToVirtualGroup(vg, line6);
		return vg;
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
		rightMenu.put("Class " + className, doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Rename", doNothing);
		rightMenu.put("> Edit attribute", doNothing);
		rightMenu.put("> Edit method", doNothing);
		rightMenu.put("> Delete", remove);
		return rightMenu;
	}

	public ClassArtifactPart getSubPart(GfxObject o) {
		Log.trace("Trying to find subpart of " + o + "\n\tname is "
				+ classNameText[0] + "\n\tattr is " + attrDivRect
				+ "\n\t method is " + methodDivRect);

		if (o.equals(classNameText[0]) || o.equals(classDivRect))
			return ClassArtifactPart.NAME;

		if (o.equals(attrDivRect))
			return ClassArtifactPart.NEW_ATTRIBUTE;

		if (o.equals(methodDivRect))
			return ClassArtifactPart.NEW_METHOD;

		if (o.equals(getGfxObject()))
			return ClassArtifactPart.NAME;

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
		}

		return ClassArtifactPart.NONE;
	}

	@Override
	public int getWidth() {
		return computeWidth();
	}

	public boolean isAttributeValidated(int slotIndex) {
		return attributes.get(slotIndex).isValidated();
	}

	public boolean isMethodValidated(int slotIndex) {
		return methods.get(slotIndex).isValidated();
	}

	public Iterator<RelationshipArtifact> relationships() {
		return relationships.iterator();
	}

	public void removeAttribute(int attrSlot) {
		attributes.remove(attrSlot);
		if (attrTexts != null) {
			if (attrSlot != -1) {
				GfxObject[] slot = attrTexts.get(attrSlot);
				if (!set(slot, null)) {
					for (int j = attrSlot + 1; j < attrTexts.size(); j++) {
						GfxObject[] slot2 = attrTexts.get(j);
						GfxManager.getPlatform().translate(slot2[0], 0,
								-getAttributeHeight());
					}
				}
				attrTexts.remove(slot);
			}
		}
	}

	public boolean removeClassDependency(ClassDependencyArtifact clazz) {
		return classDependencies.remove(clazz);
	}

	public void removeMethod(int methodSlot) {

		methods.remove(methodSlot);
		if (methodTexts != null) {
			if (methodSlot != -1) {
				GfxObject[] slot = methodTexts.get(methodSlot);
				if (!set(slot, null)) {
					for (int j = methodSlot + 1; j < methodTexts.size(); j++) {
						GfxObject[] slot2 = methodTexts.get(j);
						GfxManager.getPlatform().translate(slot2[0], 0,
								-getMethHeight());
					}
				}
				methodTexts.remove(slot);
			}
		}
	}

	public boolean removeRelationship(RelationshipArtifact relationship) {
		return relationships.remove(relationship);
	}

	public boolean removeRelationshipDependency(
			RelationshipArtifact relationship) {
		return relationshipDependencies.remove(relationship);
	}

	public void select() {

		GfxManager.getPlatform().setStroke(classDivRect,
				ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(attrDivRect,
				ThemeManager.getHighlightedForegroundColor(), 2);
		GfxManager.getPlatform().setStroke(methodDivRect,
				ThemeManager.getHighlightedForegroundColor(), 2);
	}

	public void setAttribute(int attrSlot, Attribute newAttr) {
		Attribute attr = attributes.get(attrSlot);
		attr.setName(newAttr.getName());
		attr.setType(newAttr.getType());
		if (attrTexts != null) {
			int i = attributes.indexOf(attr);
			GfxObject[] slot = attrTexts.get(i);
			set(slot, createAttrText(attr, getAttributeY(attr)));
		}
	}

	public void setAttributeValidated(int slotIndex) {
		attributes.get(slotIndex).setValidated(true);
	}

	public void setClassName(String className) {
		this.className = className;
		set(this.classNameText, createClassNameText());
	}

	public void setMethod(int methodSlot, Method newMethod) {
		Method method = methods.get(methodSlot);
		method.setName(newMethod.getName());
		method.setReturnType(newMethod.getReturnType());
		method.setParameters(newMethod.getParameters());
		if (methodTexts != null) {
			int i = methods.indexOf(method);
			GfxObject[] slot = methodTexts.get(i);
			set(slot, createMethodText(method, getMethodY(method)));
		}
	}

	public void setMethodValidated(int slotIndex) {
		methods.get(slotIndex).setValidated(true);
	}
	public void unselect() {

		GfxManager.getPlatform().setStroke(classDivRect,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(attrDivRect,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(methodDivRect,
				ThemeManager.getForegroundColor(), 1);
	}
	int computeWidth() {

		int width = DEFAULT_WIDTH;
		width = updateWidth(width, (int) GfxManager.getPlatform().getWidthFor(
				classNameText[0]));
		for (int i = 0; i < attributes.size(); i++) {
			GfxObject attrText = attrTexts.get(i)[0];
			if (attrText != null)
				width = updateWidth(width, (int) GfxManager.getPlatform()
						.getWidthFor(attrText));
		}
		for (int i = 0; i < methods.size(); i++) {
			GfxObject methodText = methodTexts.get(i)[0];
			if (methodText != null)
				width = updateWidth(width, (int) GfxManager.getPlatform()
						.getWidthFor(methodText));
		}
		return width;
	}
	void createAttrDiv(int width) {

		attrDivRect = GfxManager.getPlatform()
				.buildRect(width, getAttrHeight());
		GfxManager.getPlatform().setFillColor(attrDivRect,
				ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(attrDivRect,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform()
				.translate(attrDivRect, 0, getClassNameHeight());

	}
	GfxObject createAttrText(Attribute attr, int height) {

		GfxObject attrText = GfxManager.getPlatform()
				.buildText(attr.toString());
		GfxManager.getPlatform().setFont(attrText, font);
		GfxManager.getPlatform().setFillColor(attrText,
				ThemeManager.getForegroundColor());
		GfxManager.getPlatform().translate(attrText, TEXT_MARGIN,
				height + getLineHeight());
		return attrText;
	}
	void createAttrTexts() {
		attrTexts = new ArrayList<GfxObject[]>();
		for (int i = 0; i < attributes.size(); i++) {
			GfxObject[] attrText = new GfxObject[1];
			Attribute attr = attributes.get(i);
			attrText[0] = createAttrText(attr, getAttributeY(attr));
			attrTexts.add(attrText);
		}
	}

	void createClassDiv(int width) {

		classDivRect = GfxManager.getPlatform().buildRect(width,
				getClassNameHeight());
		GfxManager.getPlatform().setFillColor(classDivRect,
				ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(classDivRect,
				ThemeManager.getForegroundColor(), 1);
	}
	GfxObject createClassNameText() {

		GfxObject classNameText = GfxManager.getPlatform().buildText(className);
		GfxManager.getPlatform().setFont(classNameText, font);
		GfxManager.getPlatform().setFillColor(classNameText,
				ThemeManager.getForegroundColor());
		GfxManager.getPlatform().translate(classNameText, TEXT_MARGIN,
				getNameY() + getLineHeight());
		return classNameText;
	}
	void createMethodDiv(int width) {

		methodDivRect = GfxManager.getPlatform().buildRect(width,
				getMethodHeight());
		GfxManager.getPlatform().setFillColor(methodDivRect,
				ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().setStroke(methodDivRect,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().translate(methodDivRect, 0,
				getClassNameHeight() + getAttrHeight());
	}
	GfxObject createMethodText(Method method, int height) {

		GfxObject methodText = GfxManager.getPlatform().buildText(
				method.toString());
		GfxManager.getPlatform().setFont(methodText, font);
		GfxManager.getPlatform().setFillColor(methodText,
				ThemeManager.getForegroundColor());
		GfxManager.getPlatform().translate(methodText, TEXT_MARGIN,
				height + getLineHeight());
		return methodText;
	}
	void createMethodTexts() {
		methodTexts = new ArrayList<GfxObject[]>();
		for (int i = 0; i < methods.size(); i++) {
			GfxObject[] methodText = new GfxObject[1];
			Method method = methods.get(i);
			methodText[0] = createMethodText(method, getMethodY(method));
			methodTexts.add(methodText);
		}
	}
	int getAttrHeight() {
		int size = getSize(attrTexts);
		return TEXT_MARGIN + ((size + 2) / 3) * 3
				* (getLineHeight() + TEXT_MARGIN);
	}

	int getClassNameHeight() {
		return TEXT_MARGIN + getLineHeight() + TEXT_MARGIN;
	}

	int getLineHeight() {
		return font.getSize();
	}

	int getMethodHeight() {
		int size = getSize(methodTexts);
		return TEXT_MARGIN + ((size + 2) / 3) * 3
				* (getLineHeight() + TEXT_MARGIN);
	}

	int getSize(List<GfxObject[]> list) {
		int size = 0;
		for (int i = 0; i < list.size(); i++) {
			GfxObject[] slot = list.get(i);
			if (slot[0] != null)
				size++;
		}
		return size;
	}

	int indexOf(List<GfxObject[]> texts, GfxObject text) {
		for (int i = 0; i < texts.size(); i++) {
			GfxObject[] slot = texts.get(i);
			if (slot[0] == text)
				return i;
		}
		return -1;
	}

	int updateWidth(int width, int widthEl) {
		widthEl = TEXT_MARGIN + widthEl + TEXT_MARGIN;
		return width > widthEl ? width : widthEl;
	}

	@Override
	protected GfxObject buildGfxObject() {
		Log.trace("Building GfxObject for "
				+ UMLDrawerHelper.getShortName(this));

		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		classNameText[0] = createClassNameText();
		createAttrTexts();
		createMethodTexts();
		int width = computeWidth();
		createClassDiv(width);
		createAttrDiv(width);
		createMethodDiv(width);

		GfxManager.getPlatform().addToVirtualGroup(vg, attrDivRect);
		GfxManager.getPlatform().addToVirtualGroup(vg, classDivRect);
		GfxManager.getPlatform().addToVirtualGroup(vg, methodDivRect);
		GfxManager.getPlatform().addToVirtualGroup(vg, classNameText[0]);

		for (int i = 0; i < attrTexts.size(); i++)
			GfxManager.getPlatform().addToVirtualGroup(vg, attrTexts.get(i)[0]);
		for (int i = 0; i < methodTexts.size(); i++)
			GfxManager.getPlatform().addToVirtualGroup(vg,
					methodTexts.get(i)[0]);

		Log.trace("GfxObject is " + vg);
		return vg;
	}
}
