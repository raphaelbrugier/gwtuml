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
import com.objetdirect.gwt.umlapi.client.editors.ClassPartMethodsFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
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

	private transient GfxObject							lastGfxObject;
	private transient Map<GfxObject, UMLClassMethod>	methodGfxObjects;
	private transient GfxObject							methodRect;
	private List<UMLClassMethod>				methods;

	/** Default constructor ONLY for gwt rpc serialization. */
	ClassPartMethodsArtifact() {}
	
	/**
	 * Constructor of ClassPartMethodsArtifact 
	 * @param methods methods displayed by this part.
	 */
	public ClassPartMethodsArtifact(List<UMLClassMethod> methods) {
		super();
		this.methods = methods;
		this.methodGfxObjects = new LinkedHashMap<GfxObject, UMLClassMethod>();
		this.height = 0;
		this.width = 0;
	}

	/**
	 * Add a method to the current method list. The graphical object must be rebuilt to reflect the changes
	 * 
	 * @param method
	 *            The new method to add
	 */
	public void add(final UMLClassMethod method) {
		this.methods.add(method);
	}

	@Override
	public void buildGfxObject() {
		if (this.textVirtualGroup == null) {
			this.computeBounds();
		}
		this.methodRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
		
		this.methodRect.addToVirtualGroup(this.gfxObject);
		this.methodRect.setFillColor(ThemeManager.getTheme().getClassBackgroundColor());
		this.methodRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
		this.textVirtualGroup.translate(new Point(OptionsManager.get("RectangleLeftPadding"), OptionsManager.get("RectangleTopPadding")));
		this.textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		this.methodGfxObjects.clear();
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		this.textVirtualGroup.addToVirtualGroup(this.gfxObject);

		for (final UMLClassMethod method : this.methods) {
			final GfxObject methodText = GfxManager.getPlatform().buildText(method.toString(),
					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
			methodText.addToVirtualGroup(this.textVirtualGroup);
			methodText.setFont(OptionsManager.getSmallFont());

			methodText.setStroke(ThemeManager.getTheme().getClassBackgroundColor(), 0);
			methodText.setFillColor(ThemeManager.getTheme().getClassForegroundColor());
			int thisMethodWidth = GfxManager.getPlatform().getTextWidthFor(methodText);
			int thisMethodHeight = GfxManager.getPlatform().getTextHeightFor(methodText);
			thisMethodWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			thisMethodHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
			this.width = thisMethodWidth > this.width ? thisMethodWidth : this.width;
			this.height += thisMethodHeight;

			this.methodGfxObjects.put(methodText, method);
			this.lastGfxObject = methodText;
		}
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + this.width + "x" + this.height);
	}

	@Override
	public void edit() {
		final List<UMLParameter> methodToCreateParameters = new ArrayList<UMLParameter>();
		methodToCreateParameters.add(new UMLParameter("String", "parameter1"));
		this.methods.add(new UMLClassMethod(UMLVisibility.PUBLIC, "void", "method", methodToCreateParameters));
		this.nodeArtifact.rebuildGfxObject();
		this.edit(this.lastGfxObject);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final UMLClassMethod methodToChange = this.methodGfxObjects.get(editedGfxObject);
		if (methodToChange == null) {
			this.edit();
		} else {
			final ClassPartMethodsFieldEditor editor = new ClassPartMethodsFieldEditor(this.canvas, this, methodToChange);
			editor
					.startEdition(methodToChange.toString(), (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
							.get("RectangleLeftPadding")),
							(this.nodeArtifact.getLocation().getY() + ((ClassArtifact) this.nodeArtifact).className.getHeight()
									+ ((ClassArtifact) this.nodeArtifact).classAttributes.getHeight()
									+ editedGfxObject.getLocation().getY() + OptionsManager.get("RectangleTopPadding")),
							this.nodeWidth - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding")
									- OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), false, true);
		}
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	/**
	 * Getter for the method list
	 * 
	 * @return The current method list
	 */
	public List<UMLClassMethod> getList() {
		return this.methods;
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
		final GfxObject rect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.getHeight());
		
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

		for (final Entry<GfxObject, UMLClassMethod> method : this.methodGfxObjects.entrySet()) {
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
		return this.width;
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
		this.methods.remove(method);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		final StringBuilder methodsURL = new StringBuilder();
		for (final UMLClassMethod method : this.methods) {
			methodsURL.append(method);
			methodsURL.append("%");
		}
		return methodsURL.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		this.methodRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		this.nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		this.methodRect.setStroke(ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
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
		methodGfxObjects = new LinkedHashMap<GfxObject, UMLClassMethod>();
		buildGfxObject();
	}
}
