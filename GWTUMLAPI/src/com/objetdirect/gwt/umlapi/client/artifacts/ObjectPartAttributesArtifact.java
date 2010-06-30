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
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.editors.ObjectPartAttributesFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;

/**
 * This object represent the middle Part of a {@link NodeArtifact} It can hold an attribute list
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ObjectPartAttributesArtifact extends NodePartArtifact {
	private transient Map<GfxObject, UMLObjectAttribute> attributeGfxObjects;
	private transient GfxObject attributeRect;
	private transient GfxObject lastGfxObject;

	private List<UMLObjectAttribute> attributes;

	/** Default constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ObjectPartAttributesArtifact() {
	}

	/**
	 * Constructor of ObjectPartAttributesArtifact It initializes the attribute list
	 */
	public ObjectPartAttributesArtifact(final UMLCanvas canvas, List<UMLObjectAttribute> attributes) {
		super(canvas);
		this.attributes = attributes;
		attributeGfxObjects = new LinkedHashMap<GfxObject, UMLObjectAttribute>();
		height = 0;
		width = 0;
	}

	/**
	 * Add an attribute to the current attribute list. The graphical object must be rebuilt to reflect the changes
	 * 
	 * @param attribute
	 *            The new attribute to add
	 */
	public void add(final UMLObjectAttribute attribute) {
		attributes.add(attribute);
	}

	@Override
	public void buildGfxObject() {
		if (textVirtualGroup == null) {
			this.computeBounds();
		}
		attributeRect = GfxManager.getPlatform().buildRect(nodeWidth, height);
		attributeRect.addToVirtualGroup(gfxObject);
		attributeRect.setFillColor(ThemeManager.getTheme().getObjectBackgroundColor());
		attributeRect.setStroke(ThemeManager.getTheme().getObjectForegroundColor(), 1);
		textVirtualGroup.translate(new Point(OptionsManager.get("RectangleLeftPadding"), OptionsManager.get("RectangleTopPadding")));
		textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		attributeGfxObjects.clear();
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);

		for (final UMLObjectAttribute attribute : attributes) {
			final GfxObject attributeText = GfxManager.getPlatform().buildText(attribute.toString(),
					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + height));
			attributeText.addToVirtualGroup(textVirtualGroup);
			attributeText.setFont(OptionsManager.getSmallFont());
			attributeText.setStroke(ThemeManager.getTheme().getObjectBackgroundColor(), 0);
			attributeText.setFillColor(ThemeManager.getTheme().getObjectForegroundColor());
			int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(attributeText);
			int thisAttributeHeight = GfxManager.getPlatform().getTextHeightFor(attributeText);
			thisAttributeWidth += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			thisAttributeHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
			width = thisAttributeWidth > width ? thisAttributeWidth : width;
			height += thisAttributeHeight;

			attributeGfxObjects.put(attributeText, attribute);
			lastGfxObject = attributeText;
		}
		width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}

	@Override
	public void edit() {
		// final UMLObjectAttribute attributeToCreate = new UMLObjectAttribute("attribute", "\"aStringValue\"");
		String chainToParse = "attribute = \"value\"";
		final UMLObjectAttribute attributeToCreate = UMLObjectAttribute.parseAttribute(chainToParse);
		attributes.add(attributeToCreate);
		nodeArtifact.rebuildGfxObject();
		attributeGfxObjects.put(lastGfxObject, attributeToCreate);
		this.edit(lastGfxObject);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final UMLObjectAttribute attributeToChange = attributeGfxObjects.get(editedGfxObject);
		if (attributeToChange == null) {
			this.edit();
		} else {
			final ObjectPartAttributesFieldEditor editor = new ObjectPartAttributesFieldEditor(canvas, this, attributeToChange);
			editor.startEdition(attributeToChange.toString(), (nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
					.get("RectangleLeftPadding")), (nodeArtifact.getLocation().getY() + ((ObjectArtifact) nodeArtifact).getObjectNameArtifact().getHeight()
					+ editedGfxObject.getLocation().getY() + OptionsManager.get("RectangleTopPadding")), nodeWidth - OptionsManager.get("TextRightPadding")
					- OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), false,
					true);
		}
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Getter for the attribute list
	 * 
	 * @return The current attribute list
	 */
	public List<UMLObjectAttribute> getList() {
		return attributes;
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
		rect.setStroke(ThemeManager.getTheme().getObjectHighlightedForegroundColor(), 1);
		rect.setFillColor(ThemeManager.getTheme().getObjectBackgroundColor());
		rect.addToVirtualGroup(vg);
		return vg;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName("Attributes");

		for (final Entry<GfxObject, UMLObjectAttribute> attribute : attributeGfxObjects.entrySet()) {
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
		return width;
	}

	/**
	 * Remove an attribute to the current attribute list. The graphical object must be rebuilt to reflect the changes
	 * 
	 * @param attribute
	 *            The attribute to be removed
	 */
	public void remove(final UMLObjectAttribute attribute) {
		attributes.remove(attribute);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		final StringBuilder attributesURL = new StringBuilder();
		for (final UMLObjectAttribute attribute : attributes) {
			attributesURL.append(attribute.toUrl());
			attributesURL.append("%");
		}
		return attributesURL.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		attributeRect.setStroke(ThemeManager.getTheme().getObjectForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		attributeRect.setStroke(ThemeManager.getTheme().getObjectHighlightedForegroundColor(), 2);
	}

	private Command deleteCommand(final UMLObjectAttribute attribute) {
		return new Command() {
			public void execute() {
				ObjectPartAttributesArtifact.this.remove(attribute);
				ObjectPartAttributesArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				ObjectPartAttributesArtifact.this.edit();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ObjectPartAttributesArtifact.this.edit(gfxo);
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		this.initializeGfxObject();
		attributeGfxObjects = new LinkedHashMap<GfxObject, UMLObjectAttribute>();
		buildGfxObject();
	}
}
