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
public class ObjectPartNameArtifact extends NodePartArtifact {

	private transient GfxObject nameRect;
	private transient GfxObject nameText;
	private transient GfxObject underline;

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

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
		textVirtualGroup.moveToFront();
	}

	@Override
	public void computeBounds() {
		height = 0;
		width = 0;
		textVirtualGroup = GfxManager.getPlatform().buildVirtualGroup();
		textVirtualGroup.addToVirtualGroup(gfxObject);

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
		this.edit(nameText);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		if (!editedGfxObject.equals(nameText)) {
			this.edit();
			return;
		}
		int x = nodeArtifact.getLocation().getX() + TEXT_LEFT_PADDING + RECTANGLE_LEFT_PADDING;
		int y = nodeArtifact.getLocation().getY() + editedGfxObject.getLocation().getY();

		final ObjectNameEditor editor = new ObjectNameEditor(canvas, this);
		editor.startEdition(x, y);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		String formattedName = umlObject.getFormattedName();
		return formattedName + "!" + "";
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
