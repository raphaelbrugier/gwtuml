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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.editors.ClassPartAttributesFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

/**
 * This class represent the middle Part of a {@link NodeArtifact} It can hold an attribute list
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @Contributor Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ClassPartAttributesArtifact extends NodePartArtifact {
	
	transient private Map<GfxObject, UMLClassAttribute>		attributeGfxObjects;
	transient private GfxObject								attributeRect;
	private List<UMLClassAttribute>					attributes;
	transient private GfxObject								lastGfxObject;
	
	
	/** Default constructor ONLY for GWT-RPC serialization. */
	ClassPartAttributesArtifact() {}
	
	/**
	 * Constructor of ClassPartAttributesArtifact
	 * @param attributes UMLAttributes displayed by this part.
	 */
	public ClassPartAttributesArtifact(final List<UMLClassAttribute> attributes) {
		super();
		this.attributes = attributes;
		this.attributeGfxObjects = new LinkedHashMap<GfxObject, UMLClassAttribute>();
		this.height = 0;
		this.width = 0;
	}

	/**
	 * Add an attribute to the current attribute list. The graphical object must be rebuilt to reflect the changes
	 * 
	 * @param attribute
	 *            The new attribute to add
	 */
	public void addAttribute(final UMLClassAttribute attribute) {
		this.attributes.add(attribute);
	}

	@Override
	public void buildGfxObject() {
		if (this.textVirtualGroup == null) {
			this.computeBounds();
		}
		this.attributeRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
		this.attributeRect.addToVirtualGroup(this.gfxObject);
		this.attributeRect.setFillColor(ThemeManager.getTheme().getClassBackgroundColor());
		this.attributeRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
		this.textVirtualGroup.translate(new Point(OptionsManager.get("RectangleLeftPadding"), OptionsManager.get("RectangleTopPadding")));
		this.textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		this.attributeGfxObjects.clear();
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		this.textVirtualGroup.addToVirtualGroup(this.gfxObject);

		for (final UMLClassAttribute attribute : this.attributes) {
			final GfxObject attributeText = GfxManager.getPlatform().buildText(attribute.toString(),
					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height));
			
			attributeText.addToVirtualGroup(this.textVirtualGroup);
			attributeText.setFont(OptionsManager.getSmallFont());
			attributeText.setStroke(ThemeManager.getTheme().getClassBackgroundColor(), 0);
			attributeText.setFillColor(ThemeManager.getTheme().getClassForegroundColor());
			
			int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(attributeText);
			int thisAttributeHeight = GfxManager.getPlatform().getTextHeightFor(attributeText);
			thisAttributeWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			thisAttributeHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
			this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
			this.height += thisAttributeHeight;
			this.attributeGfxObjects.put(attributeText, attribute);
			this.lastGfxObject = attributeText;
		}
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + this.width + "x" + this.height);
	}

	@Override
	public void edit() {
		final UMLClassAttribute attributeToCreate = new UMLClassAttribute(UMLVisibility.PRIVATE, "String", "attribute");
		this.addAttribute(attributeToCreate);
		this.nodeArtifact.rebuildGfxObject();
		this.attributeGfxObjects.put(this.lastGfxObject, attributeToCreate);
		this.edit(this.lastGfxObject);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final UMLClassAttribute attributeToChange = this.attributeGfxObjects.get(editedGfxObject);
		if (attributeToChange == null) {
			this.edit();
		} else {
			final ClassPartAttributesFieldEditor editor = new ClassPartAttributesFieldEditor(this.canvas, this, attributeToChange);
			editor.startEdition(attributeToChange.toString(), (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
					.get("RectangleLeftPadding")), (this.nodeArtifact.getLocation().getY() + ((ClassArtifact) this.nodeArtifact).className.getHeight()
					+ editedGfxObject.getLocation().getY() + OptionsManager.get("RectangleTopPadding")), this.nodeWidth
					- OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
					- OptionsManager.get("RectangleLeftPadding"), false, true);
		}
	}

	@Override
	public int getHeight() {
		return this.height;
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
		rightMenu.setName("Attributes");

		for (final Entry<GfxObject, UMLClassAttribute> attribute : this.attributeGfxObjects.entrySet()) {
			final MenuBar subsubMenu = new MenuBar(true);
			subsubMenu.addItem("Edit ", this.editCommand(attribute.getKey()));
			subsubMenu.addItem("Delete ", this.deleteCommand(attribute.getValue()));
			rightMenu.addItem(attribute.getValue().toString(), subsubMenu);
		}
		rightMenu.addItem("Add new", this.editCommand());
		return rightMenu;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	/**
	 * Remove an attribute to the current attribute list. The graphical object must be rebuilt to reflect the changes
	 * 
	 * @param attribute
	 *            The attribute to be removed
	 */
	public void remove(final UMLClassAttribute attribute) {
		this.attributes.remove(attribute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		final StringBuilder attributesURL = new StringBuilder();
		for (final UMLClassAttribute attribute : this.attributes) {
			attributesURL.append(attribute);
			attributesURL.append("%");
		}
		return attributesURL.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		this.attributeRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		this.nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		this.attributeRect.setStroke(ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
	}

	private Command deleteCommand(final UMLClassAttribute attribute) {
		return new Command() {
			public void execute() {
				ClassPartAttributesArtifact.this.remove(attribute);
				ClassPartAttributesArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				ClassPartAttributesArtifact.this.edit();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ClassPartAttributesArtifact.this.edit(gfxo);
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		attributeGfxObjects = new LinkedHashMap<GfxObject, UMLClassAttribute>();
		buildGfxObject();
	}
}
