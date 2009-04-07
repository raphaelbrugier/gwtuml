package com.objetdirect.gwt.umlapi.client.artifacts.links;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
import com.objetdirect.gwt.umlapi.client.webinterface.MenuBarAndTitle;
import com.objetdirect.gwt.umlapi.client.webinterface.ThemeManager;
import com.objetdirect.gwt.umlapi.client.webinterface.UMLCanvas;
/**
 * @author  florian
 */
public class NoteLinkArtifact extends LinkArtifact {
GfxObject line = null;
NoteArtifact note;
UMLArtifact target;
	public NoteLinkArtifact(NoteArtifact note, UMLArtifact target) {
		this.note = note;
		this.note.addDependency(this, target);
		this.target = target;
		this.target.addDependency(this, note);
	}
	public void buildGfxObject() {

        leftPoint = note.getCenter(); 
        rightPoint = target.getCenter();
		line = GfxManager.getPlatform().buildLine(leftPoint.getX(), leftPoint.getY(), rightPoint.getX(), rightPoint.getY());
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
		GfxManager.getPlatform().moveToBack(gfxObject);
		
	}
	public void edit(GfxObject gfxObject) {
		// TODO Auto-generated method stub
	}
    @Override
    public MenuBarAndTitle getRightMenu() {
        MenuBarAndTitle rightMenu = new MenuBarAndTitle();
        rightMenu.setName("Note link");
        return rightMenu;
    }

	public boolean isDraggable() {
		return false;
	}
	public void select() {
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getHighlightedForegroundColor(), 2);
	}
	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	public void setLocation(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}
	public void moveTo(int x, int y) {
		throw new UMLDrawerException(
		"invalid operation : setLocation on a line");
	}
	public void unselect() {
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getForegroundColor(), 1);
	}
}
