package com.objetdirect.gwt.umlapi.client.artifacts.links;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.GeometryManager;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.gfx.GfxManager;
import com.objetdirect.gwt.umlapi.client.gfx.GfxObject;
import com.objetdirect.gwt.umlapi.client.gfx.GfxStyle;
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
		Point targetCenterPoint = new Point(target.getCenterX(), target.getCenterY());
		Point lineLeftPoint  = GeometryManager.getPlatform().getPointForLine(note, targetCenterPoint);
		Point lineRightPoint;
		if(isTargetALink()) {
			lineRightPoint = targetCenterPoint;
		} else {
			lineRightPoint = GeometryManager.getPlatform().getPointForLine(target, new Point(note.getCenterX(), note.getCenterY()));
		}

        point1 = lineLeftPoint; 
        point2 = lineRightPoint;
		line = GfxManager.getPlatform().buildLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
		GfxManager.getPlatform().addToVirtualGroup(gfxObject, line);
		GfxManager.getPlatform().setStroke(line,
				ThemeManager.getForegroundColor(), 1);
		GfxManager.getPlatform().setStrokeStyle(line, GfxStyle.DASH);
		
	}
	public void edit(GfxObject gfxObject, int x, int y) {
		// TODO Auto-generated method stub
	}
	public LinkedHashMap<String, Command> getRightMenu() {
		LinkedHashMap<String, Command> rightMenu = new LinkedHashMap<String, Command>();
		Command doNothing = new Command() {
			public void execute() {
			}
		};
		Command remove = new Command() {
			public void execute() {
				getCanvas().removeSelected();
			}
		};
		rightMenu.put("Note link", doNothing);
		rightMenu.put("-", null);
		rightMenu.put("> Delete", remove);
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
	private boolean isTargetALink() {
		//TODO : find a better way :
		return (target.getClass().getSuperclass().equals(LinkArtifact.class) || target.getClass().getSuperclass().getSuperclass().equals(LinkArtifact.class));
	}
}
