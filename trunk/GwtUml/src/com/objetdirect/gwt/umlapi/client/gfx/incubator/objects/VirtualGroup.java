package com.objetdirect.gwt.umlapi.client.gfx.incubator.objects;

import java.util.HashSet;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class VirtualGroup extends IncubatorGfxObject {

	private Set<IncubatorGfxObject> incubatorGfxObjectSet = new HashSet<IncubatorGfxObject>();

	public void add(IncubatorGfxObject incubatorGfxObject) {
		incubatorGfxObjectSet.add(incubatorGfxObject);
	}

	@Override
	public void addOnCanvasAt(int dx, int dy) {
		super.addOnCanvasAt(dx, dy);
		for (IncubatorGfxObject incubatorGfxObject : incubatorGfxObjectSet) {
			incubatorGfxObject.addOnCanvasAt(dx, dy);
		}

	}

	@Override
	public void draw(GWTCanvas canvas) {
		if (!isVisible)
			return;
		Log.trace("{Incubator} Starting drawing " + this);
		for (IncubatorGfxObject incubatorGfxObject : incubatorGfxObjectSet) {
			incubatorGfxObject.draw(canvas);
		}
		Log.trace("{Incubator} Ending drawing " + this);

	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
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

	@Override
	public void translate(int dx, int dy) {
		super.translate(dx, dy);
		for (IncubatorGfxObject incubatorGfxObject : incubatorGfxObjectSet) {
			incubatorGfxObject.translate(dx, dy);
		}
	}

	public void clear() {
		incubatorGfxObjectSet.clear();
		
	}

}
