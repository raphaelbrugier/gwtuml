package com.objetdirect.gwt.umlapi.client.gfx;
public interface GfxObjectListener {
	public void mouseDblClicked(GfxObject graphicObject, int x, int y);
	public void mouseClicked();
	public void mouseLeftClickPressed(GfxObject graphicObject, int x, int y);
	public void mouseMoved(int x, int y);
	public void mouseReleased(GfxObject graphicObject, int x, int y);
	public void mouseRightClickPressed(GfxObject graphicObject, int x, int y);
}
