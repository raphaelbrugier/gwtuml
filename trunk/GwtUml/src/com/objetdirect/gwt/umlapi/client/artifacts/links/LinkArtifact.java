package com.objetdirect.gwt.umlapi.client.artifacts.links;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;

public abstract class LinkArtifact extends UMLArtifact {

	int x1 = 0;
	int x2 = 0;
	int y1 = 0;
	int y2 = 0;

	@Override
	public int getHeight() {
		return y1 < y2 ? y2 - y1 : y1 - y2;
	}

	@Override
	public int[] getOpaque() {
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

	public void setLocation(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}
	public void moveTo(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}
	public void moved() {
		throw new UMLDrawerException(
		"can't move a line !");
	}

}
