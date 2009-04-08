package com.objetdirect.gwt.umlapi.client.gfx;

/**
 * @author florian
 */
public final class GfxStyle {
    /**
     * Style for the stroke a suite of dash
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle DASH = new GfxStyle("Dash");
    /**
     * Style for the stroke a suite of dash follow by a dot
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle DASHDOT = new GfxStyle("DashDot");
    /**
     * Style for the stroke, a suite of dots
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle DOT = new GfxStyle("Dot");
    /**
     * Style for the stroke, a suite of long dash
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle LONGDASH = new GfxStyle("LongDash");
    /**
     * Style for the stroke a suite of a long dash follow by a dot
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle LONGDASHDOT = new GfxStyle("LongDashDot");
    /**
     * Style for the stroke a suite of a long dash follow by 2 dots
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle LONGDASHDOTDOT = new GfxStyle("LongDashDotDot");
    /**
     * Style for the stroke
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle NONE = new GfxStyle("none");
    /**
     * Style for the stroke, a suite a shot dashes
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle SHORTDASH = new GfxStyle("ShortDash");
    /**
     * Style for the stroke a suite of a short dash follow by a dot
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle SHORTDASHDOT = new GfxStyle("ShortDashDot");
    /**
     * Style for the stroke a suite of a short dash follow by 2 dots
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle SHORTDASHDOTDOT = new GfxStyle(
	    "ShortDashDotDot");
    /**
     * Style for the stroke a suite of a short dots
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle SHORTDOT = new GfxStyle("ShortDot");
    /**
     * Style for the stroke, a solid stroke
     * 
     * @uml.associationEnd
     */
    public static final GfxStyle SOLID = new GfxStyle("Solid");
    private final String style;

    public GfxStyle(final String style) {
	this.style = style;
    }

    public String getStyleString() {
	return this.style;
    }
}
