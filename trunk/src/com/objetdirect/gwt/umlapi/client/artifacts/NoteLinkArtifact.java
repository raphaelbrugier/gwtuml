package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.Geometry;
import com.objetdirect.gwt.umlapi.client.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.Line;

public class NoteLinkArtifact implements UMLElement {

	public NoteLinkArtifact(NoteArtifact note, UMLArtifact target) {
		this.note = note;
		this.note.addDependency(this);
		this.target = target;
		this.target.addNoteLink(this);
		
	}
	
	public GraphicObject getGraphicObject() {
		if (graphicObject == null) {
			graphicObject = buildGraphicObject();
		}
		return graphicObject;
	}

	public GraphicObject buildGraphicObject() {
		float[] lineBounds = Geometry.computeLineBounds(note, target);
		x1 = (int)lineBounds[0];
		y1 = (int)lineBounds[1];
		x2 = (int)lineBounds[2];
		y2 = (int)lineBounds[3];
		line = new Line(x1, y1, x2, y2);
		line.setStroke(Color.BLACK, 1);
		line.setStrokeStyle(GraphicObject.DASH);
		return line;
	}
	
	public int getX() {
		return x1<x2 ? x1 : x2;
	}
	
	public int getY() {
		return y1<y2 ? y1 : y2;
	}

	public List<GraphicObject> getComponents() {
		List<GraphicObject> comps = new ArrayList<GraphicObject>();
		comps.add(line);
		return comps;
	}

	public boolean isDraggable() {
		return false;
	}
	
	public GraphicObject getOutline() {
		return null;
	}
	
	public void select() {
		line.setStroke(Color.BLUE, 2);
	}

	public void unselect() {
		line.setStroke(Color.BLACK, 1);
	}
	
	public void setLocation(int x, int y) {
		throw new UMLDrawerException("invalid operation : setLocation on a line");
	}
	
	public UMLCanvas getCanvas() {
		return canvas;
	}
	
	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void adjusted() {	
	}

	public void adjust() {
		UMLCanvas cnv = canvas;
		if (cnv!=null)
			cnv.remove(this);
		graphicObject = buildGraphicObject();
		if (cnv!=null)
			cnv.add(this);
		adjusted();
	}
	
	UMLCanvas canvas;
	GraphicObject graphicObject =  null;
	Line line = null;

	int x1, y1, x2, y2;
	NoteArtifact note;
	UMLArtifact target;
}
