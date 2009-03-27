package com.objetdirect.gwt.umlapi.client.artifacts;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
/**
 * @author  florian
 */
public abstract class BoxArtifact extends UMLArtifact {
	/**
	 * @uml.property  name="x"
	 */
	int x = 0;
	/**
	 * @uml.property  name="y"
	 */
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
		GfxObject line1 = GfxManager.getPlatform().buildLine(0, 0, getWidth(),
				0);
		GfxObject line2 = GfxManager.getPlatform().buildLine(getWidth(), 0,
				getWidth(), getHeight());
		GfxObject line3 = GfxManager.getPlatform().buildLine(getWidth(),
				getHeight(), 0, getHeight());
		GfxObject line4 = GfxManager.getPlatform().buildLine(0, getHeight(), 0,
				0);
		GfxManager.getPlatform().setStrokeStyle(line1, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(line2, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(line3, GfxStyle.DASH);
		GfxManager.getPlatform().setStrokeStyle(line4, GfxStyle.DASH);
		GfxManager.getPlatform().setStroke(line1,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(line2,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(line3,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().setStroke(line4,
				ThemeManager.getHighlightedForegroundColor(), 1);
		GfxManager.getPlatform().addToVirtualGroup(vg, line1);
		GfxManager.getPlatform().addToVirtualGroup(vg, line2);
		GfxManager.getPlatform().addToVirtualGroup(vg, line3);
		GfxManager.getPlatform().addToVirtualGroup(vg, line4);
		return vg;
	}
	/**
	 * @return
	 * @uml.property  name="x"
	 */
	@Override
	public int getX() {
		return x;
	}
	/**
	 * @return
	 * @uml.property  name="y"
	 */
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
