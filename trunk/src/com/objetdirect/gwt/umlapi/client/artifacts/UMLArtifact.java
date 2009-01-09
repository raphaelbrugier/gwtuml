package com.objetdirect.gwt.umlapi.client.artifacts;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.UMLCanvas;
import com.objetdirect.tatami.client.gfx.GraphicObject;

public abstract class UMLArtifact implements UMLElement {

	public GraphicObject getGraphicObject() {
		if (graphicObject == null) {
			graphicObject = buildGraphicObject();
		}
		return graphicObject;
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
	
	public void adjusted() {
		for (int i=0; i<notes.size(); i++) {
			NoteLinkArtifact elem = notes.get(i);
			elem.adjust();
		}
	}
	
	public int getCenterX() {
		return getX()+getWidth()/2;
	}
	
	public int getCenterY() {
		return getY()+getHeight()/2;
	}

	public void addNoteLink(NoteLinkArtifact noteLink) {
		notes.add(noteLink);
	}
	
	public UMLCanvas getCanvas() {
		return canvas;
	}
	
	public void setCanvas(UMLCanvas canvas) {
		this.canvas = canvas;
	}
	
	public abstract Object getSubPart(GraphicObject o);
	
	public abstract int getX();
	
	public abstract int getY();

	public abstract int getWidth();
	
	public abstract int getHeight();
	
	public abstract float[] getOpaque();
	
	protected abstract GraphicObject buildGraphicObject();
	
	GraphicObject graphicObject;
	UMLCanvas canvas;
	List<NoteLinkArtifact> notes = new ArrayList<NoteLinkArtifact>();
}
