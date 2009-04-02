package com.objetdirect.gwt.umlapi.client.artifacts;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  florian
 */
public abstract class BoxArtifact extends UMLArtifact {
	 
	int x = 0;
	 
	int y = 0;
	@Override
	public int[] getOpaque() {
		int[] opaque = new int[] { getX(), getY(), getX(),
				getY() + getHeight(), getX() + getWidth(),
				getY() + getHeight(), getX() + getWidth(), getY() };
		return opaque;
	}
	public GfxObject getOutline() {
		GfxObject vg = GfxManager.getPlatform().buildVirtualGroup();
		GfxObject rect = GfxManager.getPlatform().buildRect(getWidth(), getHeight());
		GfxManager.getPlatform().setStrokeStyle(rect, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(rect, ThemeManager.getHighlightedForegroundColor(), 1);
        GfxManager.getPlatform().setFillColor(rect, ThemeManager.getBackgroundColor());
		GfxManager.getPlatform().addToVirtualGroup(vg, rect);
		return vg;
	}
@Override
	public int getX() {
		return x;
	}
@Override
	public int getY() {
		return y;
	}
	public boolean isDraggable() {
		return true;
	}
	
	public void moveTo(int x, int y) {
		GfxManager.getPlatform().translate(getGfxObject(), x - this.x,
				y - this.y);
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
