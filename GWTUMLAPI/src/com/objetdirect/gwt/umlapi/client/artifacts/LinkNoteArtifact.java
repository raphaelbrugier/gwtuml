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

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.helpers.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.helpers.ThemeManager;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

/**
 * This artifact represent a specific link between a note and any uml artifact
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class LinkNoteArtifact extends LinkArtifact {
	private transient GfxObject line;
	private NoteArtifact note;
	private UMLArtifact target;

	/** Default Constructor ONLY for gwt-rpc serialization. */
	@Deprecated
	@SuppressWarnings("unused")
	private LinkNoteArtifact() {
	}

	/**
	 * Constructor of LinkNoteArtifact
	 * 
	 * @param canvas
	 *            Where the gfxObjects are displayed
	 * @param id
	 *            The artifacts's id
	 * @param note
	 *            The note the link is related to
	 * @param target
	 *            The uml artifact the note is pointing to
	 */
	// Dirty constructor to clean up the umlcanvas.makelinkbetween function.
	public LinkNoteArtifact(final UMLCanvas canvas, int id, final UMLArtifact artifact1, final UMLArtifact artifact2) {
		super(canvas, id, artifact1, artifact2);

		if (artifact1 instanceof NoteArtifact) {
			note = (NoteArtifact) artifact1;
			target = artifact2;
		} else if (artifact2 instanceof NoteArtifact) {
			note = (NoteArtifact) artifact2;
			target = artifact1;
		} else {
			throw new IllegalArgumentException();
		}
		line = null;
		note.addDependency(this, target);
		target.addDependency(this, note);
	}

	@Override
	public void buildGfxObject() {

		leftPoint = note.getCenter();
		rightPoint = target.getCenter();
		line = GfxManager.getPlatform().buildLine(leftPoint, rightPoint);
		line.addToVirtualGroup(gfxObject);
		line.setStroke(ThemeManager.getTheme().getLinkNoteForegroundColor(), 1);
		line.setStrokeStyle(GfxStyle.DASH);
		gfxObject.moveToBack();

	}

	@Override
	public void edit(final GfxObject editedGfxObject) {
		// Nothing to edit
	}

	@Override
	public MenuBarAndTitle getRightMenu() {
		final MenuBarAndTitle rightMenu = new MenuBarAndTitle();
		rightMenu.setName("Note link");
		return rightMenu;
	}

	@Override
	public boolean isDraggable() {
		return false;
	}

	@Override
	public void removeCreatedDependency() {
		note.removeDependency(this);
		target.removeDependency(this);
	}

	@Override
	public void setCanvas(final UMLCanvas canvas) {
		this.canvas = canvas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact#toURL()
	 */
	@Override
	public String toURL() {
		return "LinkNote$<" + note.getId() + ">!<" + target.getId() + ">";
	}

	@Override
	public void unselect() {
		super.unselect();
		line.setStroke(ThemeManager.getTheme().getLinkNoteForegroundColor(), 1);
	}

	@Override
	protected void select() {
		super.select();
		line.setStroke(ThemeManager.getTheme().getLinkNoteHighlightedForegroundColor(), 2);
	}

	@Override
	public void setUpAfterDeserialization() {
		buildGfxObject();
	}
}
