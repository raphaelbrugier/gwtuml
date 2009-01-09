package com.objetdirect.gwt.umlapi.client.artifacts;

import com.objetdirect.tatami.client.gfx.Color;
import com.objetdirect.tatami.client.gfx.GraphicObject;
import com.objetdirect.tatami.client.gfx.Line;
import com.objetdirect.tatami.client.gfx.Text;
import com.objetdirect.tatami.client.gfx.VirtualGroup;

public abstract class BoxArtifact extends UMLArtifact{

	public void setLocation(int x, int y) {
		getGraphicObject().translate(x-this.x, y-this.y);
		this.x = x;
		this.y = y;
	}
	
	public void adjust() {
		super.adjust();
		if (getCanvas()!=null)
			getGraphicObject().translate(getX(), getY());
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public float[] getOpaque() {
		float[] opaque = new float[] {
				getX(), getY(),
				getX(), getY() + getHeight(), 
				getX() + getWidth(), getY() + getHeight(), 
				getX() + getWidth(), getY()
		};
		return opaque;
	}
		
	public boolean isDraggable() {
		return true;
	}
	
	protected boolean set(Text[] slot, Text text) {
		int oldWidth = getWidth();
		int oldHeight = getHeight();
		if (slot[0]!=null) {
			((VirtualGroup)getGraphicObject()).remove(slot[0], false);
			if (getCanvas()!=null)
				getCanvas().unregister(slot[0]);
		}
		slot[0] = text;
		if (slot[0]!=null) {
			((VirtualGroup)getGraphicObject()).add(slot[0]);
			if (getCanvas()!=null)
				getCanvas().register(slot[0], this);
		}
		int newWidth = getWidth();
		int newHeight = getHeight();
		if (oldWidth!=newWidth || oldHeight!=newHeight) {
			adjust();
			return true;
		}
		else 
			return false;
	}
	
	public GraphicObject getOutline() {
		VirtualGroup vg = new VirtualGroup();
		Line line1 = new Line(0, 0, getWidth(), 0);
		Line line2 = new Line(getWidth(), 0, getWidth(), getHeight());
		Line line3 = new Line(getWidth(), getHeight(), 0, getHeight());
		Line line4 = new Line(0, getHeight(), 0, 0);
		line1.setStroke(Color.BLUE, 1);
		line1.setStrokeStyle(GraphicObject.DASH);
		line2.setStroke(Color.BLUE, 1);
		line2.setStrokeStyle(GraphicObject.DASH);
		line3.setStroke(Color.BLUE, 1);
		line3.setStrokeStyle(GraphicObject.DASH);
		line4.setStroke(Color.BLUE, 1);
		line4.setStrokeStyle(GraphicObject.DASH);
		vg.add(line1);
		vg.add(line2);
		vg.add(line3);
		vg.add(line4);
		return vg;
	}

	int x = 0;
	int y = 0;
	
}
