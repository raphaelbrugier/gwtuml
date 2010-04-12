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
import com.objetdirect.gwt.umlapi.client.editors.LifeLineFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.QualityLevel;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLifeLine;

/**
 * This artifact represent a time line
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class LifeLineArtifact extends BoxArtifact {
	int							rectHeight;
	int							width;
	private final UMLLifeLine	uMLLifeLine;
	private GfxObject			lifeLineText;
	private GfxObject			lifeLineRect;
	private int					lineLength	= 0;
	private GfxObject			lifeLineLine;

	/**
	 * Constructor of the LifeLineArtifact
	 * 
	 * @param name
	 *            The name of the LifeLine
	 * 
	 */
	public LifeLineArtifact(final String name) {
		this(name, "");
	}

	/**
	 * Constructor of the LifeLineArtifact
	 * 
	 * @param name
	 *            The name of the LifeLine
	 * @param instance
	 *            The instance of the LifeLine
	 * 
	 */
	public LifeLineArtifact(final String name, final String instance) {
		super(true);
		this.rectHeight = 0;
		this.width = 0;
		this.uMLLifeLine = new UMLLifeLine(name, instance);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final LifeLineFieldEditor editor = new LifeLineFieldEditor(this.canvas, this);
		editor.setHeightForMultiLine(this.rectHeight - OptionsManager.get("TextTopPadding") - OptionsManager.get("TextBottomPadding")
				- OptionsManager.get("RectangleTopPadding") - OptionsManager.get("RectangleBottomPadding"));
		editor.startEdition(this.uMLLifeLine.toString(), this.getLocation().getX() + OptionsManager.get("TextLeftPadding")
				+ OptionsManager.get("RectangleLeftPadding"), this.getLocation().getY() + OptionsManager.get("TextTopPadding")
				+ OptionsManager.get("TextBottomPadding") + OptionsManager.get("RectangleTopPadding"), this.width - OptionsManager.get("TextRightPadding")
				- OptionsManager.get("TextLeftPadding") - OptionsManager.get("RectangleRightPadding") - OptionsManager.get("RectangleLeftPadding"), false,
				false);

	}

	/**
	 * Getter for the content
	 * 
	 * @return The content of the note
	 */
	public String getContent() {
		return this.uMLLifeLine.toString();
	}

	@Override
	public int getHeight() {
		return this.rectHeight;
	}

	/**
	 * Get the instance name of the life line
	 * 
	 * @return the instance name of the life line
	 */
	public String getInstance() {
		return this.uMLLifeLine.getInstance();
	}

	@Override
	public GfxObject getOutline() {
		if (QualityLevel.IsAlmost(QualityLevel.NORMAL)) {
			final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();

			final GfxObject lifeLineRectOutline = GfxManager.getPlatform().buildRect(this.width, this.rectHeight);
			lifeLineRectOutline.addToVirtualGroup(vg);
			lifeLineRectOutline.setStrokeStyle(GfxStyle.DASH);
			lifeLineRectOutline.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 1);
			lifeLineRectOutline.setFillColor(ThemeManager.getTheme().getLifeLineBackgroundColor());
			final Point lineStart = new Point(this.width / 2, this.rectHeight);
			final Point lineEnd = lineStart.clonePoint();
			lineEnd.translate(0, this.lineLength);
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
		return this.width;
	}

	/**
	 * Set the instance name of the life line
	 * 
	 * @param instance
	 *            The new instance name of the life line
	 */
	public void setInstance(final String instance) {
		this.uMLLifeLine.setInstance(instance);
	}

	/**
	 * Setter for the content The graphical object MUST be rebuilt to reflect the changes
	 * 
	 * @param content
	 *            The new content of the note
	 */
	public void setName(final String content) {
		this.uMLLifeLine.setName(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "LifeLine$" + this.getLocation() + "!" + this.uMLLifeLine.toString();
	}

	@Override
	public void unselect() {
		super.unselect();
		this.lifeLineRect.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
		this.lifeLineLine.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
	}

	@Override
	protected void buildGfxObject() {
		this.rectHeight = 0;
		this.width = 0;
		this.lineLength = OptionsManager.get("LifeLineSpacing") * (this.getAllDirectionsDependenciesCount() + 3);
		this.lifeLineText = GfxManager.getPlatform().buildText(
				this.uMLLifeLine.toString(),
				new Point(OptionsManager.get("RectangleLeftPadding") + OptionsManager.get("TextLeftPadding"), OptionsManager.get("RectangleTopPadding")
						+ OptionsManager.get("TextTopPadding")));

		this.lifeLineText.addToVirtualGroup(this.gfxObject);
		this.lifeLineText.setFont(OptionsManager.getFont());
		this.lifeLineText.setStroke(ThemeManager.getTheme().getLifeLineBackgroundColor(), 0);
		this.lifeLineText.setFillColor(ThemeManager.getTheme().getLifeLineForegroundColor());
		this.width = GfxManager.getPlatform().getTextWidthFor(this.lifeLineText);
		this.rectHeight = GfxManager.getPlatform().getTextHeightFor(this.lifeLineText);
		this.width += OptionsManager.get("TextRightPadding") + OptionsManager.get("TextLeftPadding");
		this.rectHeight += OptionsManager.get("TextTopPadding") + OptionsManager.get("TextBottomPadding");
		this.width += OptionsManager.get("RectangleRightPadding") + OptionsManager.get("RectangleLeftPadding");
		this.rectHeight += OptionsManager.get("RectangleTopPadding") + OptionsManager.get("RectangleBottomPadding");

		this.lifeLineRect = GfxManager.getPlatform().buildRect(this.width, this.rectHeight);
		this.lifeLineRect.addToVirtualGroup(this.gfxObject);
		this.lifeLineRect.setFillColor(ThemeManager.getTheme().getLifeLineBackgroundColor());
		this.lifeLineRect.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);

		final Point lineStart = new Point(this.width / 2, this.rectHeight);
		final Point lineEnd = lineStart.clonePoint();
		lineEnd.translate(0, this.lineLength);
		this.lifeLineLine = GfxManager.getPlatform().buildLine(lineStart, lineEnd);
		this.lifeLineLine.addToVirtualGroup(this.gfxObject);
		this.lifeLineLine.setFillColor(ThemeManager.getTheme().getLifeLineBackgroundColor());
		this.lifeLineLine.setStroke(ThemeManager.getTheme().getLifeLineForegroundColor(), 1);
		this.lifeLineLine.setStrokeStyle(GfxStyle.DASH);
		this.lifeLineText.moveToFront();
	}

	@Override
	protected void select() {
		super.select();
		this.lifeLineRect.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
		this.lifeLineLine.setStroke(ThemeManager.getTheme().getLifeLineHighlightedForegroundColor(), 2);
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				LifeLineArtifact.this.edit(null);
			}
		};
	}

}
