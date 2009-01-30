package com.objetdirect.gwt.umlapi.client.gfx;

public class GfxColor {

	/**
	 * The aqua color in RGB
	 */
	static public final GfxColor AQUA = new GfxColor(0, 255, 255, 255);

	/**
	 * The balck color in RGB
	 */
	static public final GfxColor BLACK = new GfxColor(0, 0, 0, 255);

	/**
	 * The blue color in RGB
	 */
	static public final GfxColor BLUE = new GfxColor(0, 0, 255, 255);

	/**
	 * The fuchsia color in RGB
	 */
	static public final GfxColor FUCHSIA = new GfxColor(255, 0, 255, 255);

	/**
	 * The gray color in RGB
	 */
	static public final GfxColor GRAY = new GfxColor(128, 128, 128, 255);

	/**
	 * The green color in RGB
	 */
	static public final GfxColor GREEN = new GfxColor(0, 128, 0, 255);

	/**
	 * The lime color in RGB
	 */
	static public final GfxColor LIME = new GfxColor(0, 255, 0, 255);

	/**
	 * The maroon color in RGB
	 */
	static public final GfxColor MAROON = new GfxColor(128, 0, 0, 255);

	/**
	 * The navy color in RGB
	 */
	static public final GfxColor NAVY = new GfxColor(0, 0, 128, 255);

	/**
	 * The olive color in RGB
	 */
	static public final GfxColor OLIVE = new GfxColor(128, 128, 0, 255);

	/**
	 * The purpble color in RGB
	 */
	static public final GfxColor PURPLE = new GfxColor(128, 0, 128, 255);

	/**
	 * The red color in RGB
	 */
	static public final GfxColor RED = new GfxColor(255, 0, 0, 255);
	/**
	 * The silver color inRGB
	 */
	static public final GfxColor SILVER = new GfxColor(192, 192, 192, 255);
	/**
	 * The teal color in RGB
	 */
	static public final GfxColor TEAL = new GfxColor(0, 128, 128, 255);
	/**
	 * The white color in RGB
	 */
	static public final GfxColor WHITE = new GfxColor(255, 255, 255, 255);
	/**
	 * The yellow color in RGB
	 */
	static public final GfxColor YELLOW = new GfxColor(255, 255, 0, 255);
	int r, g, b, a;
	public GfxColor(int r, int g, int b) {
		this(r, g, b, 255);
	}
	public GfxColor(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	public int getAlpha() {
		return a;
	}
	public int getBlue() {
		return b;
	}
	public int getGreen() {
		return g;
	}
	public int getRed() {
		return r;
	}
	public void setAlpha(int a) {
		this.a = a;
	}
	public void setBlue(int b) {
		this.b = b;
	}
	public void setGreen(int g) {
		this.g = g;
	}
	public void setRed(int r) {
		this.r = r;
	}
}
