package com.objetdirect.gwt.umlapi.client.gfx;

public final class GfxStyle {

	/** Style for the stroke a suite of dash */
	public static final GfxStyle DASH = new GfxStyle("Dash");

	/** Style for the stroke a suite of dash follow by a dot */
	public static final GfxStyle DASHDOT = new GfxStyle("DashDot");

	/** Style for the stroke, a suite of dots */
	public static final GfxStyle DOT = new GfxStyle("Dot");

	/** Style for the stroke, a suite of long dash */
	public static final GfxStyle LONGDASH = new GfxStyle("LongDash");
	/** Style for the stroke a suite of a long dash follow by a dot */
	public static final GfxStyle LONGDASHDOT = new GfxStyle("LongDashDot");
	/** Style for the stroke a suite of a long dash follow by 2 dots */
	public static final GfxStyle LONGDASHDOTDOT = new GfxStyle("LongDashDotDot");
	/** Style for the stroke */
	public static final GfxStyle NONE = new GfxStyle("none");
	/** Style for the stroke, a suite a shot dashes */
	public static final GfxStyle SHORTDASH = new GfxStyle("ShortDash");
	/** Style for the stroke a suite of a short dash follow by a dot */
	public static final GfxStyle SHORTDASHDOT = new GfxStyle("ShortDashDot");
	/** Style for the stroke a suite of a short dash follow by 2 dots */
	public static final GfxStyle SHORTDASHDOTDOT = new GfxStyle(
			"ShortDashDotDot");
	/** Style for the stroke a suite of a short dots */
	public static final GfxStyle SHORTDOT = new GfxStyle("ShortDot");
	/** Style for the stroke, a solid stroke */
	public static final GfxStyle SOLID = new GfxStyle("Solid");
	private String style;
	public GfxStyle(String style) {
		this.style = (style);
	}
	public String getStyleString() {
		return style;
	}

}
