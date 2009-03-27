package com.objetdirect.gwt.umlapi.client.artifacts.links;
import java.util.LinkedHashMap;
import com.google.gwt.user.client.Command;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Geometry;
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
	/**
	 * @uml.property  name="line"
	 * @uml.associationEnd  
	 */
	GfxObject line = null;
	/**
	 * @uml.property  name="note"
	 * @uml.associationEnd  
	 */
	NoteArtifact note;
	/**
	 * @uml.property  name="target"
	 * @uml.associationEnd  
	 */
	UMLArtifact target;
	public NoteLinkArtifact(NoteArtifact note, UMLArtifact target) {
		this.note = note;
		this.note.addDependency(this);
		this.target = target;
		this.target.addDependency(this);
	}
	public void buildGfxObject() {
		Point targetCenterPoint = new Point(target.getCenterX(), target.getCenterY());
		Point lineLeftPoint  = Geometry.getPointForLine(note, targetCenterPoint);
		Point lineRightPoint;
		if(isTargetALink()) {
			lineRightPoint = targetCenterPoint;
		} else {
			lineRightPoint = Geometry.getPointForLine(target, new Point(note.getCenterX(), note.getCenterY()));
		}
		x1 = lineLeftPoint.getX();
		y1 = lineLeftPoint.getY();
		x2 = lineRightPoint.getX();
		y2 = lineRightPoint.getY();
		
		line = GfxManager.getPlatform().buildLine(x1, y1, x2, y2);		
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
	public int getX() {
		return x1 < x2 ? x1 : x2;
	}
	public int getY() {
		return y1 < y2 ? y1 : y2;
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
