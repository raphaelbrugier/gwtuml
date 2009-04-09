package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public abstract class BoxArtifact extends UMLArtifact {

    @Override
    public int[] getOpaque() {
	final int[] opaque = new int[] { getLocation().getX(),
		getLocation().getY(), getLocation().getX(),
		getLocation().getY() + getHeight(),
		getLocation().getX() + getWidth(),
		getLocation().getY() + getHeight(),
		getLocation().getX() + getWidth(), getLocation().getY() };
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
    public boolean isALink() {
	return false;
    }

    @Override
    public boolean isDraggable() {
	return true;
    }
}
