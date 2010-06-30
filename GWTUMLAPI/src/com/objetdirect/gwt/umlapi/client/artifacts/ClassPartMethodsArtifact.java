/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.editors.ClassPartMethodsFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

/**
 * This class represent the lower Part of a {@link NodeArtifact} It can hold a method list
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @Contributor Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ClassPartMethodsArtifact extends NodePartArtifact {

	private transient GfxObject lastGfxObject;
	private transient Map<GfxObject, UMLClassMethod> methodGfxObjects;
	private transient GfxObject methodRect;
	private List<UMLClassMethod> methods;

	/** Default constructor ONLY for gwt rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ClassPartMethodsArtifact() {
	}

	/**
	 * Constructor of ClassPartMethodsArtifact
	 * 
	 * @param methods
	 *            methods displayed by this part.
	 */
	public ClassPartMethodsArtifact(final UMLCanvas canvas, final List<UMLClassMethod> methods) {
		super(canvas);
		this.methods = methods;
		methodGfxObjects = new LinkedHashMap<GfxObject, UMLClassMethod>();
		height = 0;
		width = 0;
	}

	/**
	 * Add a method to the current method list. The graphical object must be rebuilt to reflect the changes
	 * 
	 * @param method
	 *            The new method to add
	 */
	public void add(final UMLClassMethod method) {
		methods.add(method);
	}

	@Override
	public void buildGfxObject() {
		if (textVirtualGroup == null) {
			this.computeBounds();
		}
		methodRect = GfxManager.getPlatform().buildRect(nodeWidth, height);

		methodRect.addToVirtualGroup(gfxObject);
		methodRect.setFillColor(ThemeManager.getTheme().getClassBackgroundColor());
		methodRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
		textVirtualGroup.translate(new Point(OptionsManager.get("RectangleLeftPadding"), OptionsManager.get("RectangleTopPadding")));
		textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		methodGfxObjects.clear();
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);

		for (final UMLClassMethod method : methods) {
			final GfxObject methodText = GfxManager.getPlatform().buildText(method.toString(),
					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + height));
			methodText.addToVirtualGroup(textVirtualGroup);
			methodText.setFont(OptionsManager.getSmallFont());

			methodText.setStroke(ThemeManager.getTheme().getClassBackgroundColor(), 0);
			methodText.setFillColor(ThemeManager.getTheme().getClassForegroundColor());
			int thisMethodWidth = GfxManager.getPlatform().getTextWidthFor(methodText);
			int thisMethodHeight = GfxManager.getPlatform().getTextHeightFor(methodText);
			thisMethodWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			thisMethodHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
			width = thisMethodWidth > width ? thisMethodWidth : width;
			height += thisMethodHeight;

			methodGfxObjects.put(methodText, method);
			lastGfxObject = methodText;
		}
		width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}

	@Override
	public void edit() {
		final List<UMLParameter> methodToCreateParameters = new ArrayList<UMLParameter>();
		methodToCreateParameters.add(new UMLParameter("String", "parameter1"));
		methods.add(new UMLClassMethod(UMLVisibility.PUBLIC, "void", "method", methodToCreateParameters));
		nodeArtifact.rebuildGfxObject();
		this.edit(lastGfxObject);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final UMLClassMethod methodToChange = methodGfxObjects.get(editedGfxObject);
		if (methodToChange == null) {
			this.edit();
		} else {
			final ClassPartMethodsFieldEditor editor = new ClassPartMethodsFieldEditor(canvas, this, methodToChange);
			editor.startEdition(methodToChange.toString(), (nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
					.get("RectangleLeftPadding")), (nodeArtifact.getLocation().getY() + ((ClassArtifact) nodeArtifact).className.getHeight()
					+ ((ClassArtifact) nodeArtifact).classAttributes.getHeight() + editedGfxObject.getLocation().getY() + OptionsManager
					.get("RectangleTopPadding")), nodeWidth - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding")
					- OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), false, true);
		}
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Getter for the method list
	 * 
	 * @return The current method list
	 */
	public List<UMLClassMethod> getList() {
		return methods;
	}

	@Override
	public int[] getOpaque() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#getOutline()
	 */
	@Override
	public GfxObject getOutline() {
		final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		final GfxObject rect = GfxManager.getPlatform().buildRect(nodeWidth, this.getHeight());

		rect.setStrokeStyle(GfxStyle.DASH);
		rect.setStroke(ThemeManager.getTheme().getClassHighlightedForegroundColor(), 1);
		rect.setFillColor(ThemeManager.getTheme().getClassBackgroundColor());
		rect.addToVirtualGroup(vg);
		return vg;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName("Methods");

		for (final Entry<GfxObject, UMLClassMethod> method : methodGfxObjects.entrySet()) {
			final MenuBar subsubMenu = new MenuBar(true);
			subsubMenu.addItem("Edit ", this.editCommand(method.getKey()));
			subsubMenu.addItem("Delete ", this.deleteCommand(method.getValue()));
			rightMenu.addItem(method.getValue().toString(), subsubMenu);
		}
		rightMenu.addItem("Add new", this.editCommand());
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

	/**
	 * Remove a method to the current method list. The graphical object must be rebuilt to reflect the changes
	 * 
	 * @param method
	 *            The method to be removed
	 */
	public void remove(final UMLClassMethod method) {
		methods.remove(method);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		final StringBuilder methodsURL = new StringBuilder();
		for (final UMLClassMethod method : methods) {
			methodsURL.append(method);
			methodsURL.append("%");
		}
		return methodsURL.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		methodRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		methodRect.setStroke(ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
	}

	private Command deleteCommand(final UMLClassMethod method) {
		return new Command() {
			public void execute() {
				ClassPartMethodsArtifact.this.remove(method);
				ClassPartMethodsArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				ClassPartMethodsArtifact.this.edit();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ClassPartMethodsArtifact.this.edit(gfxo);
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		this.initializeGfxObject();
		methodGfxObjects = new LinkedHashMap<GfxObject, UMLClassMethod>();
		buildGfxObject();
	}
}
