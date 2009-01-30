package com.objetdirect.gwt.umlapi.client.gfx;

public interface GfxObjectListener {

	void mouseClicked();

	void mouseLeftClickPressed(GfxObject graphicObject, int x, int y);

	void mouseRightClickPressed(GfxObject graphicObject, int x, int y);

	void mouseMoved(int x, int y);

	void mouseReleased(GfxObject graphicObject, int x, int y);

	public void mouseDblClicked(GfxObject graphicObject, int x, int y);
}
