package com.objetdirect.gwt.umlapi.client.artifacts.links;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
public abstract class LinkArtifact extends UMLArtifact {
    Point point1 = new Point(0,0);
    Point point2 = new Point(0,0);
	@Override
	public int getHeight() {
		return point1.getY() < point2.getY() ? point2.getY() - point1.getY() : point1.getY() - point2.getY();
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
		return point1.getX() < point2.getX() ? point2.getX() - point1.getX() : point1.getX() - point2.getX();
	}
	@Override
	public int getX() {
		return point1.getX() < point2.getX() ? point1.getX() : point2.getX();
	}
	@Override
	public int getY() {
		return point1.getY() < point2.getY() ? point1.getY() : point2.getY();
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
