package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.UMLDrawerHelper;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;

public abstract class UMLArtifact implements UMLElement {

	UMLCanvas canvas;

	GfxObject gfxObject;

	List<NoteLinkArtifact> notes = new ArrayList<NoteLinkArtifact>();

	public void addNoteLink(NoteLinkArtifact noteLink) {
		notes.add(noteLink);
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

	public UMLCanvas getCanvas() {
		return canvas;
	}

	public int getCenterX() {
		return getX() + getWidth() / 2;
	}

	public int getCenterY() {
		return getY() + getHeight() / 2;
	}

	public GfxObject getGfxObject() {
		if (gfxObject == null) {
			gfxObject = buildGfxObject();
		}
		return gfxObject;
	}

	public abstract int getHeight();

	public abstract float[] getOpaque();

	public abstract int getWidth();

	public abstract int getX();

	public abstract int getY();

	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	@Override
	public String toString() {
		return UMLDrawerHelper.getShortName(this);
	}
	protected abstract GfxObject buildGfxObject();
}
