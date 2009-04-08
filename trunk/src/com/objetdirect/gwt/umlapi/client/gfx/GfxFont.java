package com.objetdirect.gwt.umlapi.client.gfx;

/**
 * Represent a Font for text object TODO fix this class :
 */
public class GfxFont {
    /** use for the weight attribute */
    public static final String BOLD = "bold";
    /** use for the weight attribute */
    public static final String BOLDER = "bolder";
    /**
     * the default font used (serif 10)
     * 
     */
    public static final String NORMAL = "normal";
    public static final GfxFont DEFAULT_FONT = new GfxFont("monospace", 10,
	    NORMAL, NORMAL, NORMAL);
    /** use for style */
    public static final String ITALIC = "italic";

    /** use for the weight attribute */
    public static final String LIGHTER = "lighter";
    /**
     * use for variant, style, weight attributes
     */
 

    /** use for style */
    public static final String OBLIQUE = "oblique";
    /**
     * use for variant attribute : In a small-caps font the lower case letters
     * look similar to the uppercase ones, but in a smaller size and with
     * slightly different proportions.
     **/
    public static final String SMALL_CAPS = "small-caps";
    /**
     * the family of the font
     * 
     */
    private String family;
    /**
     * the size of the font
     * 
     */
    private int size;
    /**
     * the style of the font
     * 
     */
    private String style;
    /**
     * the variant of the font
     * 
     */
    private String variant;
    /**
     * the weight of the font
     * 
     */
    private String weight;

    /**
     * Creates a Font object for text object
     * 
     * @param family
     *            the family to use
     * @param size
     *            the size of the font
     * @param style
     *            the style to use : <code>NORMAL,OBLIQUE,ITALIC  </code>
     * @param variant
     *            the varient to use : <code>NORMAL,SMALL_CAPS</code>
     * @param weight
     *            the wieght to use :
     *            <code>	NORMAL,BOLD,BOLDER,LIGHTER ,100,200,300,400,500,600,700,800,900</code>
     */
    public GfxFont(final String family, final int size, final String style,
	    final String variant, final String weight) {
	this.family = family;
	this.size = size;
	this.style = style;
	this.variant = variant;
	this.weight = weight;
    }

    /**
     * Returns the family of the font
     * 
     * @return the family of the font
     * 
     */
    public String getFamily() {
	return this.family;
    }

    /**
     * Returns the size of the font
     * 
     * @return the size of the font
     * 
     */
    public int getSize() {
	return this.size;
    }

    /**
     * Returns the style used by the font
     * 
     * @return style used by the font
     * 
     */
    public String getStyle() {
	return this.style;
    }

    /**
     * Returns the variant of the font
     * 
     * @return the variant of the font
     * 
     */
    public String getVariant() {
	return this.variant;
    }

    /**
     * Returns the weight of the font
     * 
     * @return the weight of the font
     * 
     */
    public String getWeight() {
	return this.weight;
    }

    /**
     * Sets the family of the font
     * 
     * @param family
     *            a family
     * 
     */
    public void setFamily(final String family) {
	this.family = family;
    }

    /**
     * Sets the size of the font in pt
     * 
     * @param size
     *            size of the font
     * 
     */
    public void setSize(final int size) {
	this.size = size;
    }

    public void setStyle(final String style) {
	this.style = style;
    }

    public void setVariant(final String variant) {
	this.variant = variant;
    }

    public void setWeight(final String weight) {
	this.weight = weight;
    }
}