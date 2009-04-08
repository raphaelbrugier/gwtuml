package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author florian
 */
public abstract class BoxArtifact extends UMLArtifact {

    int x = 0;

    int y = 0;

    @Override
    public int[] getOpaque() {
	final int[] opaque = new int[] { getX(), getY(), getX(),
		getY() + getHeight(), getX() + getWidth(),
		getY() + getHeight(), getX() + getWidth(), getY() };
	return opaque;
    }

    @Override
    public GfxObject getOutline() {
	final GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
	final GfxObject rect = GfxManager.getPlatform().buildRect(getWidth(),
		getHeight());
	GfxManager.getPlatform().setStrokeStyle(rect, GfxStyle.DASH);
	GfxManager.getPlatform().setStroke(rect,
		ThemeManager.getHighlightedForegroundColor(), 1);
	GfxManager.getPlatform().setFillColor(rect,
		ThemeManager.getBackgroundColor());
	GfxManager.getPlatform().addToVirtualGroup(vg, rect);
	return vg;
    }

    @Override
    public int getX() {
	return this.x;
    }

    @Override
    public int getY() {
	return this.y;
    }

    @Override
    public boolean isALink() {
	return false;
    }

    @Override
    public boolean isDraggable() {
	return true;
    }

    @Override
    public void moveTo(final int xm, final int ym) {
	GfxManager.getPlatform().translate(getGfxObject(), xm - this.x, ym - this.y);
	this.x = xm;
	this.y = ym;
    }

    /**
     * @param xl
     * @param yl
     */
    public void setLocation(final int xl, final int yl) {
	this.x = xl;
	this.y = yl;
    }
}
