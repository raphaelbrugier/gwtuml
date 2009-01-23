package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;


public class NoteLinkArtifact implements UMLElement {

	public NoteLinkArtifact(NoteArtifact note, UMLArtifact target) {
		this.note = note;
		this.note.addDependency(this);
		this.target = target;
		this.target.addNoteLink(this);
		
	}
	
	public GfxObject getGfxObject() {
		if (gfxObject == null) {
			gfxObject = buildGfxObject();
		}
		return gfxObject;
	}

	public GfxObject buildGfxObject() {
		float[] lineBounds = Geometry.computeLineBounds(note, target);
		line = GfxManager.getInstance().buildLine((int) lineBounds[0], (int)lineBounds[1], (int)lineBounds[2], (int)lineBounds[3]);
		GfxManager.getInstance().setStroke(line, ThemeManager.getForegroundColor(), 1);
		GfxManager.getInstance().setStrokeStyle(line, GfxStyle.DASH);
		return line;
	}
	
	public int getX() {
		return x1<x2 ? x1 : x2;
	}
	
	public int getY() {
		return y1<y2 ? y1 : y2;
	}

	public List<GfxObject> getComponents() {
		List<GfxObject> comps = new ArrayList<GfxObject>();
		comps.add(line);
		return comps;
	}

	public boolean isDraggable() {
		return false;
	}
	
	public GfxObject getOutline() {
		return null;
	}
	
	public void select() {
		GfxManager.getInstance().setStroke(line, ThemeManager.getHighlightedForegroundColor(), 2);
	}

	public void unselect() {
		GfxManager.getInstance().setStroke(line, ThemeManager.getForegroundColor(), 1);
	}
	
	public void setLocation(int x, int y) {
		throw new UMLDrawerException("invalid operation : setLocation on a line");
	}
	
	public UMLCanvas getCanvas() {
		return canvas;
	}
	
	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void adjusted() {	
	}

	public void adjust() {
		UMLCanvas cnv = canvas;
		if (cnv!=null)
			cnv.remove(this);
		gfxObject = buildGfxObject();
		if (cnv!=null)
			cnv.add(this);
		adjusted();
	}
	
	public void edit(GfxObject gfxObject, int x, int y) {
		// TODO Auto-generated method stub
		
	}
	UMLCanvas canvas;
	GfxObject gfxObject =  null;
	GfxObject line = null;

	int x1, y1, x2, y2;
	NoteArtifact note;
	UMLArtifact target;
}
