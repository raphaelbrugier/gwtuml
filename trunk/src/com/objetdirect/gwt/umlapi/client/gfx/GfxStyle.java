package com.objetdirect.gwt.umlapi.client.gfx;
/**
 * @author  florian
 */
public final class GfxStyle {
	/**
	 * Style for the stroke a suite of dash
	 * @uml.property  name="dASH"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle DASH = new GfxStyle("Dash");
	/**
	 * Style for the stroke a suite of dash follow by a dot
	 * @uml.property  name="dASHDOT"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle DASHDOT = new GfxStyle("DashDot");
	/**
	 * Style for the stroke, a suite of dots
	 * @uml.property  name="dOT"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle DOT = new GfxStyle("Dot");
	/**
	 * Style for the stroke, a suite of long dash
	 * @uml.property  name="lONGDASH"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle LONGDASH = new GfxStyle("LongDash");
	/**
	 * Style for the stroke a suite of a long dash follow by a dot
	 * @uml.property  name="lONGDASHDOT"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle LONGDASHDOT = new GfxStyle("LongDashDot");
	/**
	 * Style for the stroke a suite of a long dash follow by 2 dots
	 * @uml.property  name="lONGDASHDOTDOT"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle LONGDASHDOTDOT = new GfxStyle("LongDashDotDot");
	/**
	 * Style for the stroke
	 * @uml.property  name="nONE"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle NONE = new GfxStyle("none");
	/**
	 * Style for the stroke, a suite a shot dashes
	 * @uml.property  name="sHORTDASH"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle SHORTDASH = new GfxStyle("ShortDash");
	/**
	 * Style for the stroke a suite of a short dash follow by a dot
	 * @uml.property  name="sHORTDASHDOT"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle SHORTDASHDOT = new GfxStyle("ShortDashDot");
	/**
	 * Style for the stroke a suite of a short dash follow by 2 dots
	 * @uml.property  name="sHORTDASHDOTDOT"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle SHORTDASHDOTDOT = new GfxStyle(
			"ShortDashDotDot");
	/**
	 * Style for the stroke a suite of a short dots
	 * @uml.property  name="sHORTDOT"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle SHORTDOT = new GfxStyle("ShortDot");
	/**
	 * Style for the stroke, a solid stroke
	 * @uml.property  name="sOLID"
	 * @uml.associationEnd  
	 */
	public static final GfxStyle SOLID = new GfxStyle("Solid");
	private String style;
	public GfxStyle(String style) {
		this.style = (style);
	}
	public String getStyleString() {
		return style;
	}
}
