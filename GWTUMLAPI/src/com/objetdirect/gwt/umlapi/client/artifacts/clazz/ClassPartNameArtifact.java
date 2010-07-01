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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.artifacts.NodeArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NodePartArtifact;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.editors.ClassPartNameFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.GWTUMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

/**
 * This class represent the upper Part of a {@link NodeArtifact} It can hold a name and a stereotype
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @Contributor Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ClassPartNameArtifact extends NodePartArtifact {

	private UMLClass uMLclass;

	private transient GfxObject nameRect;
	private transient GfxObject nameText;
	private transient GfxObject stereotypeText;

	/** Default constructor ONLY for gwt rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ClassPartNameArtifact() {
	}

	/**
	 * Constructor of ClassPartNameArtifact with class name and stereotype
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 * @param ownedClass
	 *            the UMLclass displayed by the artifact.
	 * @param className
	 *            The name of the class
	 * @param stereotype
	 *            The stereotype associated with the class
	 */
	public ClassPartNameArtifact(final UMLCanvas canvas, final UMLClass ownedClass, final String stereotype) {
		super(canvas);
		uMLclass = ownedClass;
		uMLclass.setStereotype(stereotype.equals("") ? "" : "«" + stereotype + "»");
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
		nameRect.setFillColor(ThemeManager.getTheme().getClassBackgroundColor());
		nameRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);

		// Centering name class :
		nameText.translate(new Point((nodeWidth - GfxManager.getPlatform().getTextWidthFor(nameText) - OptionsManager.get("TextRightPadding") - OptionsManager
				.get("TextLeftPadding")) / 2, OptionsManager.get("RectangleTopPadding")));

		if (stereotypeText != null) {
			stereotypeText.translate(new Point(
					(nodeWidth - GfxManager.getPlatform().getTextWidthFor(stereotypeText) - OptionsManager.get("TextRightPadding") - OptionsManager
							.get("TextLeftPadding")) / 2, OptionsManager.get("RectangleTopPadding")));
		}

		textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);

		final String stereotype = uMLclass.getStereotype();
		if ((stereotype != null) && GWTUMLDrawerHelper.isNotBlank(stereotype)) {
			stereotypeText = GfxManager.getPlatform().buildText(stereotype,
					new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding")));

			stereotypeText.addToVirtualGroup(textVirtualGroup);
			stereotypeText.setFont(OptionsManager.getFont());
			stereotypeText.setStroke(ThemeManager.getTheme().getClassBackgroundColor(), 0);
			stereotypeText.setFillColor(ThemeManager.getTheme().getClassForegroundColor());

			width = GfxManager.getPlatform().getTextWidthFor(stereotypeText);
			height = GfxManager.getPlatform().getTextHeightFor(stereotypeText);
			width += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
			height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		}

		nameText = GfxManager.getPlatform().buildText(uMLclass.getName(),
				new Point(OptionsManager.get("TextLeftPadding"), OptionsManager.get("TextTopPadding") + height));

		nameText.addToVirtualGroup(textVirtualGroup);
		nameText.setFont(OptionsManager.getFont());
		nameText.setStroke(ThemeManager.getTheme().getClassBackgroundColor(), 0);
		nameText.setFillColor(ThemeManager.getTheme().getClassForegroundColor());

		final int thisNameWidth = GfxManager.getPlatform().getTextWidthFor(nameText) + OptionsManager.get("TextRightPadding")
				+ OptionsManager.get("TextLeftPadding");
		width = thisNameWidth > width ? thisNameWidth : width;
		height += GfxManager.getPlatform().getTextHeightFor(nameText);
		height += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		height += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		Log.trace("WxH for " + GWTUMLDrawerHelper.getShortName(this) + "is now " + width + "x" + height);
	}

	@Override
	public void edit() {
		final String stereotype = uMLclass.getStereotype();
		if ((stereotype == null) || stereotype.equals("")) {
			uMLclass.setStereotype("«Abstract»");
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
		final ClassPartNameFieldEditor editor = new ClassPartNameFieldEditor(canvas, this, isTheStereotype);
		String edited;
		if (isTheStereotype) {
			edited = uMLclass.getStereotype().replaceAll("»", "").replaceAll("«", "");
		} else {
			edited = uMLclass.getName();
		}
		editor.startEdition(edited, (nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager.get("RectangleLeftPadding")),
				nodeArtifact.getLocation().getY() + editedGfxObject.getLocation().getY(), nodeWidth - OptionsManager.get("TextRightPadding")
						- OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"),
				false, false);
	}

	/**
	 * Getter for the class name
	 * 
	 * @return The class name
	 */
	public String getClassName() {
		return uMLclass.getName();
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

	/**
	 * Getter for the stereotype
	 * 
	 * @return the stereotype
	 */
	public String getStereotype() {
		return uMLclass.getStereotype().replaceAll("»", "").replaceAll("«", "");
	}

	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Setter for the class name
	 * 
	 * @param className
	 *            The new name of the class
	 */
	public void setClassName(final String className) {
		uMLclass.setName(className);
	}

	/**
	 * @param stereotype
	 *            the stereotype to set
	 */
	public void setStereotype(final String stereotype) {
		uMLclass.setStereotype(stereotype);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return this.getClassName() + "!" + this.getStereotype();
	}

	@Override
	public void unselect() {
		super.unselect();
		nameRect.setStroke(ThemeManager.getTheme().getClassForegroundColor(), 1);
	}

	@Override
	public void setNodeWidth(final int width) {
		nodeWidth = width;
	}

	@Override
	protected void select() {
		super.select();
		nameRect.setStroke(ThemeManager.getTheme().getClassHighlightedForegroundColor(), 2);
	}

	private Command createStereotype() {
		return new Command() {
			public void execute() {
				ClassPartNameArtifact.this.edit();
			}
		};
	}

	private Command deleteStereotype() {
		return new Command() {
			public void execute() {
				uMLclass.setStereotype(null);
				ClassPartNameArtifact.this.nodeArtifact.rebuildGfxObject();
			}
		};
	}

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ClassPartNameArtifact.this.edit(gfxo);
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		this.initializeGfxObject();
		buildGfxObject();
	}
}
