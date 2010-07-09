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
package com.objetdirect.gwt.umlapi.client.artifacts.object;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.NodeArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.editors.ObjectNameEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;

/**
 * This object represent the upper Part of a {@link NodeArtifact} It can hold a name and a stereotype
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ObjectPartNameArtifact extends NodePartArtifact {

	private transient GfxObject nameRect;
	private transient GfxObject nameText;
	private transient GfxObject stereotypeText;
	private transient GfxObject underline;

	private UMLObject umlObject;

	/** Default constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ObjectPartNameArtifact() {
	}

	/**
	 * Constructor of ObjectPartNameArtifact with object name and stereotype
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 * @param objectInstance
	 *            The name of the instance of the object
	 * @param objectName
	 *            The name of the object
	 * @param stereotype
	 *            The stereotype associated with the object
	 */
	public ObjectPartNameArtifact(final UMLCanvas canvas, UMLObject umlObject) {
		super(canvas);
		this.umlObject = umlObject;
		height = 0;
		width = 0;
	}

	@Override
	public void buildGfxObject() {
		if (textVirtualGroup == null) {
			this.computeBounds();
		}
		nameRect = GfxManager.getPlatform().buildRect(nodeWidth, height);
		nameRect.addToVirtualGroup(gfxObject);
		nameRect.setFillColor(ThemeManager.getTheme().getObjectBackgroundColor());
		nameRect.setStroke(ThemeManager.getTheme().getObjectForegroundColor(), 1);

		// Centering name object :
		nameText.translate(new Point((nodeWidth - GfxManager.getPlatform().getTextWidthFor(nameText) - TEXT_RIGHT_PADDING - TEXT_LEFT_PADDING) / 2,
				RECTANGLE_TOP_PADDING));
		underline.translate(new Point((nodeWidth - GfxManager.getPlatform().getTextWidthFor(nameText) - TEXT_RIGHT_PADDING - TEXT_LEFT_PADDING) / 2,
				RECTANGLE_TOP_PADDING));
		if (stereotypeText != null) {
			stereotypeText
					.translate(new Point((nodeWidth - GfxManager.getPlatform().getTextWidthFor(stereotypeText) - TEXT_RIGHT_PADDING - TEXT_LEFT_PADDING) / 2,
							RECTANGLE_TOP_PADDING));
		}
		textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);
		String stereotype = umlObject.getStereotype();
		if ((stereotype != null) && (!stereotype.isEmpty())) {
			stereotypeText = GfxManager.getPlatform().buildText(stereotype, new Point(TEXT_LEFT_PADDING, TEXT_TOP_PADDING));
			stereotypeText.addToVirtualGroup(textVirtualGroup);
			stereotypeText.setFont(OptionsManager.getFont());
			stereotypeText.setStroke(ThemeManager.getTheme().getObjectBackgroundColor(), 0);
			stereotypeText.setFillColor(ThemeManager.getTheme().getObjectForegroundColor());
			width = GfxManager.getPlatform().getTextWidthFor(stereotypeText);
			height += GfxManager.getPlatform().getTextHeightFor(stereotypeText);
			width += TEXT_RIGHT_PADDING + TEXT_LEFT_PADDING;
			height += TEXT_TOP_PADDING + TEXT_BOTTOM_PADDING;
		}

		nameText = GfxManager.getPlatform().buildText(umlObject.getFormattedName(), new Point(TEXT_LEFT_PADDING, TEXT_TOP_PADDING + height));
		nameText.addToVirtualGroup(textVirtualGroup);
		final int yUnderline = height + GfxManager.getPlatform().getTextHeightFor(nameText) + TEXT_TOP_PADDING;
		underline = GfxManager.getPlatform().buildLine(new Point(TEXT_LEFT_PADDING, yUnderline),
				new Point(TEXT_LEFT_PADDING + GfxManager.getPlatform().getTextWidthFor(nameText), yUnderline));
		underline.addToVirtualGroup(textVirtualGroup);

		nameText.setFont(OptionsManager.getFont());
		nameText.setStroke(ThemeManager.getTheme().getObjectBackgroundColor(), 0);
		nameText.setFillColor(ThemeManager.getTheme().getObjectForegroundColor());
		underline.setStroke(ThemeManager.getTheme().getObjectForegroundColor(), 1);
		underline.setFillColor(ThemeManager.getTheme().getObjectForegroundColor());
		final int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(nameText) + TEXT_RIGHT_PADDING + TEXT_LEFT_PADDING;
		width = thisAttributeWidth > width ? thisAttributeWidth : width;
		height += GfxManager.getPlatform().getTextHeightFor(nameText);
		height += TEXT_TOP_PADDING + TEXT_BOTTOM_PADDING + OptionsManager.get("UnderlineShift");
		width += RECTANGLE_RIGHT_PADDING + RECTANGLE_LEFT_PADDING;
		height += RECTANGLE_TOP_PADDING + RECTANGLE_BOTTOM_PADDING;

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}

	@Override
	public void edit() {
		if ((umlObject.getStereotype() == null) || umlObject.getStereotype().equals("")) {
			umlObject.setStereotype("«Abstract»");
			nodeArtifact.rebuildGfxObject();
			this.edit(stereotypeText);
		} else {
			this.edit(nameText);
		}
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final boolean isTheStereotype = editedGfxObject.equals(stereotypeText);
		if (!isTheStereotype && !editedGfxObject.equals(nameText)) {
			this.edit();
			return;
		}
		int x  = nodeArtifact.getLocation().getX() + TEXT_LEFT_PADDING + RECTANGLE_LEFT_PADDING;
		int y = nodeArtifact.getLocation().getY() + editedGfxObject.getLocation().getY();
		int w = nodeWidth - TEXT_RIGHT_PADDING - TEXT_LEFT_PADDING - RECTANGLE_RIGHT_PADDING - RECTANGLE_LEFT_PADDING;
		
		final ObjectNameEditor editor = new ObjectNameEditor(canvas, this);
		editor.startEdition(x, y, w);
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
		rect.setStroke(ThemeManager.getTheme().getObjectHighlightedForegroundColor(), 1);
		rect.setFillColor(ThemeManager.getTheme().getObjectBackgroundColor());
		rect.addToVirtualGroup(vg);
		return vg;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName("Name");
		rightMenu.addItem("Edit Name", this.editCommand(nameText));
		if (stereotypeText == null) {
			rightMenu.addItem("Add stereotype", this.createStereotype());
		} else {
			rightMenu.addItem("Edit Stereotype", this.editCommand(stereotypeText));
			rightMenu.addItem("Delete Stereotype", this.deleteStereotype());
		}

		return rightMenu;
	}

	@Override
	public int getWidth() {
		return width;
	}
	

	/**
	 * @return the umlObject
	 */
	public UMLObject getUmlObject() {
		return umlObject;
	}

	/**
	 * Setter for the object instance name
	 * 
	 * @param instanceName
	 *            The new instance name of the object
	 */
	public void setInstanceName(final String instanceName) {
		umlObject.setInstanceName(instanceName);
	}

	/**
	 * Setter for the object name
	 * 
	 * @param objectName
	 *            The new name of the object
	 */
	public void setObjectName(final String objectName) {
		umlObject.setClassName(objectName);
	}

	/**
	 * @param stereotype
	 *            the stereotype to set
	 */
	public void setStereotype(final String stereotype) {
		umlObject.setStereotype(stereotype);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		String stereotype = umlObject.getStereotype().replaceAll("»", "").replaceAll("«", "");
		String formattedName = umlObject.getFormattedName();
		return formattedName + "!" + stereotype;
	}

	@Override
	public void unselect() {
		super.unselect();
		nameRect.setStroke(ThemeManager.getTheme().getObjectForegroundColor(), 1);
	}

	@Override
	public void setNodeWidth(final int width) {
		nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		nameRect.setStroke(ThemeManager.getTheme().getObjectHighlightedForegroundColor(), 2);
	}

	private Command createStereotype() {
		return new Command() {
			public void execute() {
				ObjectPartNameArtifact.this.edit();
			}
		};
	}

	private Command deleteStereotype() {
		return new Command() {
			public void execute() {
				umlObject.setStereotype("");
				ObjectPartNameArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ObjectPartNameArtifact.this.edit(gfxo);
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		this.initializeGfxObject();
		buildGfxObject();
	}
}
