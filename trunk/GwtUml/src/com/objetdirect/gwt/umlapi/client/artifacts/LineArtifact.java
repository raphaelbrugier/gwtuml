package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

public abstract class LineArtifact extends UMLArtifact {

	int x1 = 0;
	int x2 = 0;
	int y1 = 0;
	int y2 = 0;

	@Override
	public int getHeight() {
		return y1 < y2 ? y2 - y1 : y1 - y2;
	}

	@Override
	public float[] getOpaque() {
		return null;
	}

	public GfxObject getOutline() {
		return null;
	}

	@Override
	public int getWidth() {
		return x1 < x2 ? x2 - x1 : x1 - x2;
	}

	@Override
	public int getX() {
		return x1 < x2 ? x1 : x2;
	}


	@Override
	public int getY() {
		return y1 < y2 ? y1 : y2;
	}


	public boolean isDraggable() {
		return false;
	}
	public void setBounds(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	public void setLocation(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}
	public void moveTo(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}

}
