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

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.contextMenu.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.editors.LifeLineFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.QualityLevel;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLifeLine;

/**
 * This artifact represent a time line
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class LifeLineArtifact extends BoxArtifact {

	private transient GfxObject lifeLineText;
	private transient GfxObject lifeLineRect;
	private transient GfxObject lifeLineLine;

	private UMLLifeLine uMLLifeLine;

	private int lineLength;
	private int rectHeight;
	private int width;

	/** Default constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private LifeLineArtifact() {
	}

	/**
	 * Constructor of the LifeLineArtifact
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param name
	 *            The name of the LifeLine
	 */
	public LifeLineArtifact(final UMLCanvas canvas, int id, final String name) {
		this(canvas, id, name, "");
	}

	/**
	 * Constructor of the LifeLineArtifact
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param name
	 *            The name of the LifeLine
	 * @param instance
	 *            The instance of the LifeLine
	 * 
	 */
	public LifeLineArtifact(final UMLCanvas canvas, int id, final String name, final String instance) {
		super(canvas, id);
		lineLength = 0;
		rectHeight = 0;
		width = 0;
		uMLLifeLine = new UMLLifeLine(name, instance);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final LifeLineFieldEditor editor = new LifeLineFieldEditor(canvas, this);
		editor.setHeightForMultiLine(rectHeight - OptionsManager.get("TextTopPadding") - OptionsManager.get("TextBottomPadding")
				- OptionsManager.get("RectangleTopPadding") - OptionsManager.get("RectangleBottomPadding"));
		editor.startEdition(uMLLifeLine.toString(), this.getLocation().getX() + OptionsManager.get("TextLeftPadding")
				+ OptionsManager.get("RectangleLeftPadding"), this.getLocation().getY() + OptionsManager.get("TextTopPadding")
				+ OptionsManager.get("TextBottomPadding") + OptionsManager.get("RectangleTopPadding"), width - OptionsManager.get("TextRightPadding")
				- OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), false,
				false);

	}

	/**
	 * Getter for the content
	 * 
	 * @return The content of the note
	 */
	public String getContent() {
		return uMLLifeLine.toString();
	}

	@Override
	public int getHeight() {
		return rectHeight;
	}

	/**
	 * Get the instance name of the life line
	 * 
	 * @return the instance name of the life line
	 */
	public String getInstance() {
		return uMLLifeLine.getInstance();
	}

	@Override
	public GfxObject getOutline() {
		if (QualityLevel.IsAlmost(QualityLevel.NORMAL)) {
			final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();

			final GfxObject lifeLineRectOutline = GfxManager.getPlatform().buildRect(width, rectHeight);
			lifeLineRectOutline.addToVirtualGroup(vg);
			lifeLineRectOutline.setStrokeStyle(GfxStyle.DASH);
			lifeLineRectOutline.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 1);
			lifeLineRectOutline.setFillColor(ThemeManager.getTheme().getLifeLineBackgroundColor());
			final Point lineStart = new Point(width / 2, rectHeight);
			final Point lineEnd = lineStart.clonePoint();
			lineEnd.translate(0, lineLength);
			final GfxObject lifeLineLineOutline = GfxManager.getPlatform().buildLine(lineStart, lineEnd);
			lifeLineLineOutline.addToVirtualGroup(vg);

			lifeLineLineOutline.setStrokeStyle(GfxStyle.DASH);
			lifeLineLineOutline.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 1);
			return vg;
		}
		return super.getOutline();

	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName("LifeLine");
		rightMenu.addItem("Edit name", this.editCommand());
		return rightMenu;
	}

	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Set the instance name of the life line
	 * 
	 * @param instance
	 *            The new instance name of the life line
	 */
	public void setInstance(final String instance) {
		uMLLifeLine.setInstance(instance);
	}

	/**
	 * Setter for the content The graphical object MUST be rebuilt to reflect the changes
	 * 
	 * @param content
	 *            The new content of the note
	 */
	public void setName(final String content) {
		uMLLifeLine.setName(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "LifeLine$" + this.getLocation() + "!" + uMLLifeLine.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		lifeLineRect.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
		lifeLineLine.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
	}

	@Override
	protected void buildGfxObject() {
		rectHeight = 0;
		width = 0;
		lineLength = OptionsManager.get("LifeLineSpacing") * (this.getAllDirectionsDependenciesCount() + 3);
		lifeLineText = GfxManager.getPlatform().buildText(
				uMLLifeLine.toString(),
				new Point(OptionsManager.get("RectangleLeftPadding") + OptionsManager.get("TextLeftPadding"), OptionsManager.get("RectangleTopPadding")
						+ OptionsManager.get("TextTopPadding")));

		lifeLineText.addToVirtualGroup(gfxObject);
		lifeLineText.setFont(OptionsManager.getFont());
		lifeLineText.setStroke(ThemeManager.getTheme().getLifeLineBackgroundColor(), 0);
		lifeLineText.setFillColor(ThemeManager.getTheme().getLifeLineForegroundColor());
		width = GfxManager.getPlatform().getTextWidthFor(lifeLineText);
		rectHeight = GfxManager.getPlatform().getTextHeightFor(lifeLineText);
		width += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
		rectHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		rectHeight += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		lifeLineRect = GfxManager.getPlatform().buildRect(width, rectHeight);
		lifeLineRect.addToVirtualGroup(gfxObject);
		lifeLineRect.setFillColor(ThemeManager.getTheme().getLifeLineBackgroundColor());
		lifeLineRect.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);

		final Point lineStart = new Point(width / 2, rectHeight);
		final Point lineEnd = lineStart.clonePoint();
		lineEnd.translate(0, lineLength);
		lifeLineLine = GfxManager.getPlatform().buildLine(lineStart, lineEnd);
		lifeLineLine.addToVirtualGroup(gfxObject);
		lifeLineLine.setFillColor(ThemeManager.getTheme().getLifeLineBackgroundColor());
		lifeLineLine.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
		lifeLineLine.setStrokeStyle(GfxStyle.DASH);
		lifeLineText.moveToFront();
	}

	@Override
	protected void select() {
		super.select();
		lifeLineRect.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
		lifeLineLine.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				LifeLineArtifact.this.edit(null);
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		buildGfxObject();
	}
}
