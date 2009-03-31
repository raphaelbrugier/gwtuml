package com.objetdirect.gwt.umlapi.client.gfx.canvas.objects;
import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.gfx.canvas.CanvasBridge;
public class VirtualGroup extends IncubatorGfxObject {
	private Set<IncubatorGfxObject> incubatorGfxObjectSet = new HashSet<IncubatorGfxObject>();
	public void add(IncubatorGfxObject incubatorGfxObject) {
		incubatorGfxObjectSet.add(incubatorGfxObject);
		incubatorGfxObject.setParentGroup(this);
		if(isVisible) incubatorGfxObject.addOnCanvasAt(canvas, 0, 0);
	}
	@Override
	public void addOnCanvasAt(CanvasBridge canvas, int dx, int dy) {
		super.addOnCanvasAt(canvas, dx, dy);
		for (IncubatorGfxObject incubatorGfxObject : incubatorGfxObjectSet) {
			incubatorGfxObject.addOnCanvasAt(canvas, dx, dy);
		}
	}
	@Override
	public void draw() {
		if (!isVisible) {
			Log.trace(this + " is not visible");
			return;
		}
		if (canvas == null) Log.fatal("canvas is null for " + this);
		
		Log.trace("Starting drawing " + this);
		for (IncubatorGfxObject incubatorGfxObject : incubatorGfxObjectSet) {
			incubatorGfxObject.draw();
		}
		Log.trace("Ending drawing " + this);
	}
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isPointed(int x, int y) {
		return false;
	}
	public void remove(IncubatorGfxObject incubatorGfxObject) {
		incubatorGfxObjectSet.remove(incubatorGfxObject);
	}

	public void clear() {
		incubatorGfxObjectSet.clear();
		
	}
}
