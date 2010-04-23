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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.editors.ObjectPartNameFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;

/**
 * This object represent the upper Part of a {@link NodeArtifact} It can hold a name and a stereotype
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ObjectPartNameArtifact extends NodePartArtifact {

	private UMLObject		uMLObject;
	private String			stereotype;
	private transient GfxObject		nameRect;
	private transient GfxObject		nameText;
	private transient GfxObject		stereotypeText;
	private transient GfxObject		underline;

	/**
	 * Constructor of ObjectPartNameArtifact with only object name
	 * 
	 * @param objectInstance
	 *            The name of the instance of the object
	 * @param objectName
	 *            The name of the object
	 */
	public ObjectPartNameArtifact(final String objectInstance, final String objectName) {
		this(objectInstance, objectName, "");
	}

	/**
	 * Constructor of ObjectPartNameArtifact with object name and stereotype
	 * 
	 * @param objectInstance
	 *            The name of the instance of the object
	 * @param objectName
	 *            The name of the object
	 * @param stereotype
	 *            The stereotype associated with the object
	 */
	public ObjectPartNameArtifact(final String objectInstance, final String objectName, final String stereotype) {
		super();
		this.uMLObject = new UMLObject(objectInstance, objectName);
		this.stereotype = stereotype.equals("") ? "" : "«" + stereotype + "»";
		this.height = 0;
		this.width = 0;
	}

	@Override
	public void buildGfxObject() {
		if (this.textVirtualGroup == null) {
			this.computeBounds();
		}
		this.nameRect = GfxManager.getPlatform().buildRect(this.nodeWidth, this.height);
		this.nameRect.addToVirtualGroup(this.gfxObject);
		this.nameRect.setFillColor(ThemeManager.getTheme().getObjectBackgroundColor());
		this.nameRect.setStroke(ThemeManager.getTheme().getObjectForegroundColor(), 1);

		// Centering name object :
		this.nameText.translate(new Point((this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.get("TextRightPadding") - OptionsManager
						.get("TextLeftPadding")) / 2, OptionsManager.get("RectangleTopPadding")));
		this.underline.translate(new Point((this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.nameText) - OptionsManager.get("TextRightPadding") - OptionsManager
						.get("TextLeftPadding")) / 2, OptionsManager.get("RectangleTopPadding")));
		if (this.stereotypeText != null) {
			this.stereotypeText.translate(new Point(
									(this.nodeWidth - GfxManager.getPlatform().getTextWidthFor(this.stereotypeText) - OptionsManager.get("TextRightPadding") - OptionsManager
											.get("TextLeftPadding")) / 2, OptionsManager.get("RectangleTopPadding")));

		}
		this.textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		this.height = 0;
		this.width = 0;
		this.textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		this.textVirtualGroup.addToVirtualGroup(this.gfxObject);
		if ((this.stereotype != null) && (this.stereotype != "")) {
			this.stereotypeText = GfxManager.getPlatform().buildText(this.stereotype,
					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding")));
			this.stereotypeText.addToVirtualGroup(this.textVirtualGroup);
			this.stereotypeText.setFont(OptionsManager.getFont());
			this.stereotypeText.setStroke(ThemeManager.getTheme().getObjectBackgroundColor(), 0);
			this.stereotypeText.setFillColor(ThemeManager.getTheme().getObjectForegroundColor());
			this.width = GfxManager.getPlatform().getTextWidthFor(this.stereotypeText);
			this.height += GfxManager.getPlatform().getTextHeightFor(this.stereotypeText);
			this.width += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			this.height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		}
		this.nameText = GfxManager.getPlatform().buildText(this.uMLObject.toString(),
				new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + this.height)/*
																													 * , "underline" doesn't work yet in common
																													 * browsers
																													 */);
		this.nameText.addToVirtualGroup(this.textVirtualGroup);
		final int yUnderline = this.height + GfxManager.getPlatform().getTextHeightFor(this.nameText) + OptionsManager.get("TextTopPadding");
		this.underline = GfxManager.getPlatform().buildLine(new Point(OptionsManager.get("TextLeftPadding"), yUnderline),
				new Point(OptionsManager.get("TextLeftPadding") + GfxManager.getPlatform().getTextWidthFor(this.nameText), yUnderline));
		this.underline.addToVirtualGroup(this.textVirtualGroup);

		this.nameText.setFont(OptionsManager.getFont());
		this.nameText.setStroke(ThemeManager.getTheme().getObjectBackgroundColor(), 0);
		this.nameText.setFillColor(ThemeManager.getTheme().getObjectForegroundColor());
		this.underline.setStroke(ThemeManager.getTheme().getObjectForegroundColor(), 1);
		this.underline.setFillColor(ThemeManager.getTheme().getObjectForegroundColor());
		final int thisAttributeWidth = GfxManager.getPlatform().getTextWidthFor(this.nameText) + OptionsManager.get("TextRightPadding")
				+ OptionsManager.get("TextLeftPadding");
		this.width = thisAttributeWidth > this.width ? thisAttributeWidth : this.width;
		this.height += GfxManager.getPlatform().getTextHeightFor(this.nameText);
		this.height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding") + OptionsManager.get("UnderlineShift");
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + this.width + "x" + this.height);
	}

	@Override
	public void edit() {
		if ((this.stereotype == null) || this.stereotype.equals("")) {
			this.stereotype = "«Abstract»";
			this.nodeArtifact.rebuildGfxObject();
			this.edit(this.stereotypeText);
		} else {
			this.edit(this.nameText);
		}
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final boolean isTheStereotype = editedGfxObject.equals(this.stereotypeText);
		if (!isTheStereotype && !editedGfxObject.equals(this.nameText)) {
			this.edit();
			return;
		}
		final ObjectPartNameFieldEditor editor = new ObjectPartNameFieldEditor(this.canvas, this, isTheStereotype);
		String edited;
		if (isTheStereotype) {
			edited = this.stereotype.replaceAll("»", "").replaceAll("«", "");
		} else {
			edited = this.uMLObject.toString();
		}
		editor.startEdition(edited, (this.nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
				.get("RectangleLeftPadding")), this.nodeArtifact.getLocation().getY() + editedGfxObject.getLocation().getY()/*
																																						 * +
																																						 * OptionsManager
																																						 * .get(
																																						 * "RectangleTopPadding"
																																						 * ))
																																						 */,
				this.nodeWidth - OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
						- OptionsManager.get("RectangleLeftPadding"), false, false);
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	/**
	 * Getter for the object name
	 * 
	 * @return The object name
	 */
	public String getObjectName() {
		return this.uMLObject.toString();
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
		rect.setStroke(ThemeManager.getTheme().getObjectHighlightedForegroundColor(), 1);
		rect.setFillColor(ThemeManager.getTheme().getObjectBackgroundColor());
		rect.addToVirtualGroup(vg);
		return vg;
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName("Name");
		rightMenu.addItem("Edit Name", this.editCommand(this.nameText));
		if (this.stereotypeText == null) {
			rightMenu.addItem("Add stereotype", this.createStereotype());
		} else {
			rightMenu.addItem("Edit Stereotype", this.editCommand(this.stereotypeText));
			rightMenu.addItem("Delete Stereotype", this.deleteStereotype());
		}

		return rightMenu;
	}

	/**
	 * Getter for the stereotype
	 * 
	 * @return the stereotype
	 */
	public String getStereotype() {
		return this.stereotype.replaceAll("»", "").replaceAll("«", "");
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	/**
	 * Setter for the object instance name
	 * 
	 * @param instanceName
	 *            The new instance name of the object
	 */
	public void setInstanceName(final String instanceName) {
		this.uMLObject.setInstanceName(instanceName);
	}

	/**
	 * Setter for the object name
	 * 
	 * @param objectName
	 *            The new name of the object
	 */
	public void setObjectName(final String objectName) {
		this.uMLObject.setObjectName(objectName);
	}

	/**
	 * @param stereotype
	 *            the stereotype to set
	 */
	public void setStereotype(final String stereotype) {
		this.stereotype = stereotype;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return this.getObjectName() + "!" + this.getStereotype();
	}

	@Override
	public void unselect() {
		super.unselect();
		this.nameRect.setStroke(ThemeManager.getTheme().getObjectForegroundColor(), 1);
	}

	@Override
	void setNodeWidth(final int width) {
		this.nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		this.nameRect.setStroke(ThemeManager.getTheme().getObjectHighlightedForegroundColor(), 2);
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
				ObjectPartNameArtifact.this.stereotype = null;
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
		buildGfxObject();
	}
}
