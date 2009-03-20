package com.objetdirect.gwt.umlapi.client.artifacts.classArtifactComponent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.artifacts.BoxArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Scheduler;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Attribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

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
			classMethods.edit(gfxObject);
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
							classAttributes.edit(gfxObject);
						} else if (gfxObjectGroup.equals(classMethods.getGfxObject())) {
							classMethods.edit(gfxObject);
						} else if (gfxObjectGroup.equals(getGfxObject())) {
							Log.warn("Selecting the master virtual group : this should NOT happen !");
							className.edit();
						} else Log.warn("No editable part found");
					} else Log.warn("No editable part found");
				}
			}
		}
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
	@Override
	public void rebuildGfxObject() {
		GfxManager.getPlatform().clearVirtualGroup(className.getGfxObject());
		GfxManager.getPlatform().clearVirtualGroup(classAttributes.getGfxObject());
		GfxManager.getPlatform().clearVirtualGroup(classMethods.getGfxObject());
		GfxManager.getPlatform().clearVirtualGroup(gfxObject);
		buildGfxObject();
		for(final UMLArtifact dependentUMLArtifact : dependentUMLArtifacts) {
			Log.warn("Rebuilding : " + dependentUMLArtifact);
			new Scheduler.Task(dependentUMLArtifact) {
				@Override
				public void process() {
					dependentUMLArtifact.rebuildGfxObject();
				}
			};
		}
		
	}

}
