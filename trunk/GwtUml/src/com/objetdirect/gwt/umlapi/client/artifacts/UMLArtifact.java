package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public abstract class UMLArtifact implements UMLElement {

	public GfxObject getGfxObject() {
		if (gfxObject == null) {
			gfxObject = buildGfxObject();
		}
		return gfxObject;
	}

	public void adjust() {
		UMLCanvas cnv = canvas;
		if (cnv != null)
			cnv.remove(this);
		gfxObject = buildGfxObject();
		if (cnv != null)
			cnv.add(this);
		adjusted();
	}

	public void adjusted() {
		for (int i = 0; i < notes.size(); i++) {
			NoteLinkArtifact elem = notes.get(i);
			elem.adjust();
		}
	}

	public int getCenterX() {
		return getX() + getWidth() / 2;
	}

	public int getCenterY() {
		return getY() + getHeight() / 2;
	}

	public void addNoteLink(NoteLinkArtifact noteLink) {
		notes.add(noteLink);
	}

	public UMLCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}

	public abstract int getX();

	public abstract int getY();

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract float[] getOpaque();

	protected abstract GfxObject buildGfxObject();

	public String toString() {
		return UMLDrawerHelper.getShortName(this);
	}

	GfxObject gfxObject;
	UMLCanvas canvas;
	List<NoteLinkArtifact> notes = new ArrayList<NoteLinkArtifact>();
}
