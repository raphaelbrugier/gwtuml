package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public class NoteLinkArtifact extends LineArtifact {

	GfxObject line = null;

	NoteArtifact note;

	UMLArtifact target;


	public NoteLinkArtifact(NoteArtifact note, UMLArtifact target) {
		this.note = note;
		this.note.addDependency(this);
		this.target = target;
		this.target.addNoteLink(this);

	}

	public void buildGfxObject() {		
		float[] lineBounds = Geometry.computeLineBounds(note, target);
		line = GfxManager.getPlatform().buildLine((int) lineBounds[0],
				(int) lineBounds[1], (int) lineBounds[2], (int) lineBounds[3]);
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
		
	}

	public void edit(GfxObject gfxObject, int x, int y) {
		// TODO Auto-generated method stub

	}


	public LinkedHashMap<String, Command> getRightMenu() {

		LinkedHashMap<String, Command> rightMenu = new LinkedHashMap<String, Command>();

		Command doNothing = new Command() {
			public void execute() {
			}
		};
		Command remove = new Command() {
			public void execute() {
				getCanvas().removeSelected();
			}
		};
		rightMenu.put("Note link", doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Delete", remove);
		return rightMenu;
	}

	public int getX() {
		return x1 < x2 ? x1 : x2;
	}
	public int getY() {
		return y1 < y2 ? y1 : y2;
	}
	public boolean isDraggable() {
		return false;
	}

	public void select() {
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getHighlightedForegroundColor(), 2);
	}
	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	public void setLocation(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}
	public void moveTo(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}

	public void unselect() {
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getForegroundColor(), 1);
	}

}
