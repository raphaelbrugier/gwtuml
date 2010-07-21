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
import com.objetdirect.gwt.umlapi.client.editors.NoteFieldEditor;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.QualityLevel;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.umlCanvas.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNote;

/**
 * This artifact represent a note
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class NoteArtifact extends BoxArtifact {
	private transient GfxObject borderPath;
	private transient GfxObject contentText;
	private transient GfxObject cornerPath;

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	private int height;
	private int width;
	private UMLNote note;

	/** Default constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private NoteArtifact() {
	}

	/**
	 * Constructor of the NoteArtifact
	 * 
	 * @param canvas
	 *            Where the gfxObject are displayed
	 * @param id
	 *            The artifacts's id
	 * @param content
	 *            The text contained by the note
	 */
	public NoteArtifact(final UMLCanvas canvas, int id, final String content) {
		super(canvas, id);
		height = 0;
		width = 0;
		note = new UMLNote(content);
	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		final NoteFieldEditor editor = new NoteFieldEditor(canvas, this);
		editor.setHeightForMultiLine(height - TEXT_TOP_PADDING - TEXT_BOTTOM_PADDING - RECTANGLE_TOP_PADDING - RECTANGLE_BOTTOM_PADDING);
		editor.startEdition(note.getText(), this.getLocation().getX() + TEXT_LEFT_PADDING + RECTANGLE_LEFT_PADDING, this.getLocation().getY()
				+ TEXT_TOP_PADDING + TEXT_BOTTOM_PADDING + RECTANGLE_TOP_PADDING, width - TEXT_RIGHT_PADDING - TEXT_LEFT_PADDING - RECTANGLE_RIGHT_PADDING
				- RECTANGLE_LEFT_PADDING, true, false);

	}

	/**
	 * Getter for the content
	 * 
	 * @return The content of the note
	 */
	public String getContent() {
		return note.getText();
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int[] getOpaque() {
		final int[] opaque = new int[] { this.getLocation().getX(), this.getLocation().getY(), this.getLocation().getX(),
				this.getLocation().getY() + this.getHeight(), this.getLocation().getX() + this.getWidth(), this.getLocation().getY() + this.getHeight(),
				this.getLocation().getX() + this.getWidth(), this.getLocation().getY() + NOTE_CORNER_HEIGHT,
				this.getLocation().getX() + this.getWidth() - NOTE_CORNER_WIDTH, this.getLocation().getY() };
		return opaque;
	}

	@Override
	public GfxObject getOutline() {
		if (QualityLevel.IsAlmost(QualityLevel.NORMAL)) {
			final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
			final GfxObject outlineBorderPath = this.getBorderPath();
			final GfxObject outlineCornerPath = this.getCornerPath();
			outlineBorderPath.addToVirtualGroup(vg);
			outlineCornerPath.addToVirtualGroup(vg);
			outlineBorderPath.setStrokeStyle(GfxStyle.DASH);
			outlineCornerPath.setStrokeStyle(GfxStyle.DASH);
			outlineBorderPath.setStroke(ThemeManager.getTheme().getNoteHighlightedForegroundColor(), 1);
			outlineCornerPath.setStroke(ThemeManager.getTheme().getNoteHighlightedForegroundColor(), 1);
			return vg;
		}
		return super.getOutline();

	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName("Note");
		rightMenu.addItem("Edit content", this.editCommand());
		return rightMenu;
	}

	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Setter for the content The graphical object MUST be rebuilt to reflect the changes
	 * 
	 * @param content
	 *            The new content of the note
	 */
	public void setContent(final String content) {
		note.setText(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "Note$" + this.getLocation() + "!" + note.getText();
	}

	@Override
	public void unselect() {
		super.unselect();
		borderPath.setStroke(ThemeManager.getTheme().getNoteForegroundColor(), 1);
		cornerPath.setStroke(ThemeManager.getTheme().getNoteForegroundColor(), 1);
	}

	void createNoteText() {
		height = 0;
		width = 0;
		final String[] noteMultiLine = note.getText().split("\n");
		contentText = GfxManager.getPlatform().buildVirtualGroup();
		contentText.addToVirtualGroup(gfxObject);

		for (final String noteLine : noteMultiLine) {
			final GfxObject textLine = GfxManager.getPlatform().buildText(noteLine, new Point(TEXT_LEFT_PADDING, TEXT_TOP_PADDING + height));
			textLine.addToVirtualGroup(contentText);
			textLine.setFont(OptionsManager.getSmallFont());
			textLine.setStroke(ThemeManager.getTheme().getNoteBackgroundColor(), 0);
			textLine.setFillColor(ThemeManager.getTheme().getNoteForegroundColor());
			int thisLineWidth = GfxManager.getPlatform().getTextWidthFor(textLine);
			int thisLineHeight = GfxManager.getPlatform().getTextHeightFor(textLine);
			thisLineWidth += TEXT_RIGHT_PADDING + TEXT_LEFT_PADDING;
			thisLineHeight += TEXT_TOP_PADDING + TEXT_BOTTOM_PADDING;
			width = thisLineWidth > width ? thisLineWidth : width;
			height += thisLineHeight;
		}
		height += RECTANGLE_TOP_PADDING + RECTANGLE_BOTTOM_PADDING;
		width += RECTANGLE_RIGHT_PADDING + RECTANGLE_LEFT_PADDING + NOTE_CORNER_WIDTH;

	}

	@Override
	protected void buildGfxObject() {
		this.createNoteText();
		borderPath = this.getBorderPath();
		borderPath.addToVirtualGroup(gfxObject);
		cornerPath = this.getCornerPath();
		cornerPath.addToVirtualGroup(gfxObject);
		contentText.translate(new Point(RECTANGLE_LEFT_PADDING, RECTANGLE_TOP_PADDING));
		contentText.moveToFront();
	}

	protected GfxObject getBorderPath() {
		final GfxObject thisBorderPath = GfxManager.getPlatform().buildPath();
		GfxManager.getPlatform().moveTo(thisBorderPath, Point.getOrigin());
		GfxManager.getPlatform().lineTo(thisBorderPath, new Point(width - NOTE_CORNER_WIDTH, 0));
		GfxManager.getPlatform().lineTo(thisBorderPath, new Point(width, NOTE_CORNER_HEIGHT));
		GfxManager.getPlatform().lineTo(thisBorderPath, new Point(width, height));
		GfxManager.getPlatform().lineTo(thisBorderPath, new Point(0, height));
		GfxManager.getPlatform().lineTo(thisBorderPath, Point.getOrigin());
		thisBorderPath.setFillColor(ThemeManager.getTheme().getNoteBackgroundColor());
		thisBorderPath.setStroke(ThemeManager.getTheme().getNoteForegroundColor(), 1);
		return thisBorderPath;
	}

	protected GfxObject getCornerPath() {
		final GfxObject thisCornerPath = GfxManager.getPlatform().buildPath();
		GfxManager.getPlatform().moveTo(thisCornerPath, new Point(width - NOTE_CORNER_WIDTH, 0));
		GfxManager.getPlatform().lineTo(thisCornerPath, new Point(width - NOTE_CORNER_WIDTH, NOTE_CORNER_HEIGHT));
		GfxManager.getPlatform().lineTo(thisCornerPath, new Point(width, NOTE_CORNER_HEIGHT));
		thisCornerPath.setFillColor(ThemeManager.getTheme().getNoteBackgroundColor());
		thisCornerPath.setStroke(ThemeManager.getTheme().getNoteForegroundColor(), 1);
		return thisCornerPath;
	}

	@Override
	protected void select() {
		super.select();
		borderPath.setStroke(ThemeManager.getTheme().getNoteHighlightedForegroundColor(), 2);
		cornerPath.setStroke(ThemeManager.getTheme().getNoteHighlightedForegroundColor(), 2);
	}

	private Command editCommand() {
		return new Command() {
			public void execute() {
				NoteArtifact.this.edit(null);
			}
		};
	}

	@Override
	public void setUpAfterDeserialization() {
		buildGfxObject();
	}
}
