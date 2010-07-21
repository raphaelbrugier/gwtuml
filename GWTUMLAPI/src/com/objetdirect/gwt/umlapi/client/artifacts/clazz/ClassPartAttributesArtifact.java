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
package com.objetdirect.gwt.umlapi.client.artifacts.clazz;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.objetdirect.gwt.umlapi.client.artifacts.NodeArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.editors.ClassPartAttributesFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

/**
 * This class represent the middle Part of a {@link NodeArtifact} It can hold an attribute list
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @Contributor Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class ClassPartAttributesArtifact extends NodePartArtifact {

	private transient Map<GfxObject, UMLClassAttribute> attributeGfxObjects;
	private transient GfxObject attributeRect;
	private transient GfxObject lastGfxObject;

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	private List<UMLClassAttribute> attributes;

	/** Default constructor ONLY for GWT-RPC serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ClassPartAttributesArtifact() {
	}

	/**
	 * Constructor of ClassPartAttributesArtifact
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 * @param attributes
	 *            UMLAttributes displayed by this part.
	 */
	public ClassPartAttributesArtifact(final UMLCanvas canvas, final List<UMLClassAttribute> attributes) {
		super(canvas);
		this.attributes = attributes;
		attributeGfxObjects = new LinkedHashMap<GfxObject, UMLClassAttribute>();
		height = 0;
		width = 0;
	}

	/**
	 * Add an attribute to the current attribute list. The graphical object must be rebuilt to reflect the changes
	 * 
	 * @param attribute
	 *            The new attribute to add
	 */
	public void addAttribute(final UMLClassAttribute attribute) {
		attributes.add(attribute);
	}

	@Override
	public void buildGfxObject() {
		if (textVirtualGroup == null) {
			this.computeBounds();
		}
		attributeRect = GfxManager.getPlatform().buildRect(nodeWidth, height);
		attributeRect.addToVirtualGroup(gfxObject);
		attributeRect.setFillColor(ThemeManager.getTheme().getClassBackgroundColor());
		attributeRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
		textVirtualGroup.translate(new Point(RECTANGLE_LEFT_PADDING, RECTANGLE_TOP_PADDING));
		textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		attributeGfxObjects.clear();
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);

		for (final UMLClassAttribute attribute : attributes) {
			final GfxObject attributeText = GfxManager.getPlatform().buildText(attribute.toString(), new Point(TEXT_LEFT_PADDING, TEXT_TOP_PADDING + height));

			attributeText.addToVirtualGroup(textVirtualGroup);
			attributeText.setFont(OptionsManager.getSmallFont());
			attributeText.setStroke(ThemeManager.getTheme().getClassBackgroundColor(), 0);
			attributeText.setFillColor(ThemeManager.getTheme().getClassForegroundColor());

			int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(attributeText);
			int thisAttributeHeight = GfxManager.getPlatform().getTextHeightFor(attributeText);
			thisAttributeWidth += TEXT_RIGHT_PADDING + TEXT_LEFT_PADDING;
			thisAttributeHeight += TEXT_TOP_PADDING + TEXT_BOTTOM_PADDING;
			width = thisAttributeWidth > width ? thisAttributeWidth : width;
			height += thisAttributeHeight;
			attributeGfxObjects.put(attributeText, attribute);
			lastGfxObject = attributeText;
		}
		width += RECTANGLE_RIGHT_PADDING + RECTANGLE_LEFT_PADDING;
		height += RECTANGLE_TOP_PADDING + RECTANGLE_BOTTOM_PADDING;

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}

	@Override
	public void edit() {
		final UMLClassAttribute attributeToCreate = new UMLClassAttribute(UMLVisibility.PRIVATE, "String", "attribute");
		this.addAttribute(attributeToCreate);
		nodeArtifact.rebuildGfxObject();
		attributeGfxObjects.put(lastGfxObject, attributeToCreate);
		this.edit(lastGfxObject);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final UMLClassAttribute attributeToChange = attributeGfxObjects.get(editedGfxObject);
		if (attributeToChange == null) {
			this.edit();
		} else {
			final ClassPartAttributesFieldEditor editor = new ClassPartAttributesFieldEditor(canvas, this, attributeToChange);
			editor.startEdition(attributeToChange.toString(), (nodeArtifact.getLocation().getX() + TEXT_LEFT_PADDING + RECTANGLE_LEFT_PADDING), (nodeArtifact
					.getLocation().getY()
					+ ((ClassArtifact) nodeArtifact).className.getHeight() + editedGfxObject.getLocation().getY() + RECTANGLE_TOP_PADDING), nodeWidth
					- TEXT_RIGHT_PADDING - TEXT_LEFT_PADDING - RECTANGLE_RIGHT_PADDING - RECTANGLE_LEFT_PADDING, false, true);
		}
	}

	@Override
	public int getHeight() {
		return height;
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
		rightMenu.setName("Attributes");

		for (final Entry<GfxObject, UMLClassAttribute> attribute : attributeGfxObjects.entrySet()) {
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
	public void remove(final UMLClassAttribute attribute) {
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
		for (final UMLClassAttribute attribute : attributes) {
			attributesURL.append(attribute);
			attributesURL.append("%");
		}
		return attributesURL.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		attributeRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	public void setNodeWidth(final int width) {
		nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		attributeRect.setStroke(ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
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
		this.initializeGfxObject();
		attributeGfxObjects = new LinkedHashMap<GfxObject, UMLClassAttribute>();
		buildGfxObject();
	}
}
