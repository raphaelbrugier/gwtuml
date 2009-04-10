package com.objetdirect.gwt.umlapi.client.gfx;

class Text extends IncubatorGfxObject {
    private final String text;

    public Text(final String text) {
	this.text = text;
    }

    @Override
    public void draw() {
	// if (!isVisible)
	return;
	/*
	 * Log.trace("{Incubator} Drawing " + this); canvas.saveContext(); if
	 * (fillColor != null) canvas.setFillStyle(fillColor); if (strokeColor
	 * != null) canvas.setStrokeStyle(strokeColor); if (strokeWidth != 0)
	 * canvas.setLineWidth(strokeWidth); for (int i = 0; i < text.length();
	 * i++) { canvas.strokeRect(getX() + 10 * i, getY() - 8, 8, 10); }
	 * canvas.restoreContext();
	 */
    }

    @Override
    public int getHeight() {
	return 8;
    }

    @Override
    public int getWidth() {
	return this.text.length() * 10;
    }

    @Override
    public boolean isPointed(final int xp, final int yp) {
	return false;
    }
}
