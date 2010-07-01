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
import com.objetdirect.gwt.umlapi.client.editors.EditorPart;
import com.objetdirect.gwt.umlapi.client.editors.SimpleFieldEditor;
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
public class ClassPartNameSimplifiedArtifact extends NodePartArtifact {

	private UMLClass uMLclass;

	private transient GfxObject nameRect;
	private transient GfxObject nameText;

	/** Default constructor ONLY for gwt rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private ClassPartNameSimplifiedArtifact() {
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
	public ClassPartNameSimplifiedArtifact(final UMLCanvas canvas, final UMLClass ownedClass) {
		super(canvas);
		uMLclass = ownedClass;
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

		textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);

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
		this.edit(nameText);
	}

	private EditorPart buildNameEditorPart() {
		return new EditorPart() {

			@Override
			public void setText(String newText) {
				uMLclass.setName(newText);
			}

			@Override
			public String getText() {
				return uMLclass.getName();
			}
		};
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		edit(editedGfxObject, buildNameEditorPart());
	}

	private void edit(final GfxObject editedGfxObject, final EditorPart editorPart) {
		final SimpleFieldEditor editor = new SimpleFieldEditor(canvas, getNodeArtifact(), editorPart);
		editor.startEdition(editorPart.getText(), (nodeArtifact.getLocation().getX() + OptionsManager.get("TextLeftPadding") + OptionsManager
				.get("RectangleLeftPadding")), nodeArtifact.getLocation().getY() + editedGfxObject.getLocation().getY(), nodeWidth
				- OptionsManager.get("TextRightPadding") - OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding")
				- OptionsManager.get("RectangleLeftPadding"), false, true);
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

		return rightMenu;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public String toURL() {
		return this.getClassName();
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

	private Command editCommand(final GfxObject gfxo) {
		return new Command() {
			public void execute() {
				ClassPartNameSimplifiedArtifact.this.edit(gfxo);
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		this.initializeGfxObject();
		buildGfxObject();
	}
}
