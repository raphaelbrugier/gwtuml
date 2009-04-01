package com.objetdirect.gwt.umlapi.client.webinterface;
import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
/**
 * This class allows to set and access configuration values 
 * @author  fmounier
 */
public class OptionsManager {

	private static final int RIGHT_TEXT_PADDING = 1;
	private static final int LEFT_TEXT_PADDING = 1;
	private static final int TOP_TEXT_PADDING = 1;
	private static final int BOTTOM_TEXT_PADDING = 2;
	private static final int RIGHT_RECT_PADDING = 4;
	private static final int LEFT_RECT_PADDING = 4;
	private static final int TOP_RECT_PADDING = 4;
	private static final int BOTTOM_RECT_PADDING = 6;
	private static final int ARROW_LENGTH = 25;
	private static final int ARROW_WIDTH = 15;
	private static final int FILLED_ARROW_LENGTH = 30;
	private static final int FILLED_ARROW_WIDTH = 20;
	private static boolean isAnimated = false;

	/**
	 * @return the isAnimated
	 */
	public static boolean isAnimated() {
		return isAnimated;
	}
	/**
	 * @param isAnimated the isAnimated to set
	 */
	public static void setAnimated(boolean isAnimated) {
		OptionsManager.isAnimated = isAnimated;
	}
	/**
	 * @uml.property  name="font"
	 * @uml.associationEnd  
	 */
	private static GfxFont font = new GfxFont("monospace", 10, GfxFont.NORMAL, GfxFont.NORMAL, GfxFont.NORMAL);
	/**
	 * @uml.property  name="smallCapsFont"
	 * @uml.associationEnd  
	 */
	private static GfxFont smallCapsFont = new GfxFont("monospace", 10, GfxFont.NORMAL, GfxFont.SMALL_CAPS, GfxFont.NORMAL);
	
	/**
	 * @return the aRROW_LENGTH
	 */
	public static int getArrowLenght() {
		return ARROW_LENGTH;
	}
	/**
	 * @return the aRROW_WIDTH
	 */
	public static int getArrowWidth() {
		return ARROW_WIDTH;
	}
	/**
	 * @return the fILLED_ARROW_LENGTH
	 */
	public static int getFilledArrowLenght() {
		return FILLED_ARROW_LENGTH;
	}
	/**
	 * @return the fILLED_ARROW_WIDTH
	 */
	public static int getFilledArrowWidth() {
		return FILLED_ARROW_WIDTH;
	}
	
	public static int getTextRightPadding() {
		return RIGHT_TEXT_PADDING;
	}
	public static int getTextLeftPadding() {
		return LEFT_TEXT_PADDING;
	}
	public static int getTextTopPadding() {
		return TOP_TEXT_PADDING;
	}
	public static int getTextBottomPadding() {
		return BOTTOM_TEXT_PADDING;
	}
	public static int getTextXTotalPadding() {
		return RIGHT_TEXT_PADDING + LEFT_TEXT_PADDING;
	}
	public static int getTextYTotalPadding() {
		return TOP_TEXT_PADDING + BOTTOM_TEXT_PADDING;
	}
	public static int getRectangleRightPadding() {
		return RIGHT_RECT_PADDING;
	}
	public static int getRectangleLeftPadding() {
		return LEFT_RECT_PADDING;
	}
	public static int getRectangleTopPadding() {
		return TOP_RECT_PADDING;
	}
	public static int getRectangleBottomPadding() {
		return BOTTOM_RECT_PADDING;
	}	
	public static int getRectangleXTotalPadding() {
		return RIGHT_RECT_PADDING + LEFT_RECT_PADDING;
	}
	public static int getRectangleYTotalPadding() {
		return TOP_RECT_PADDING + BOTTOM_RECT_PADDING;
	}
	
	/**
	 * @return
	 * @uml.property  name="font"
	 */
	public static GfxFont getFont() {
		return font;
	}
	/**
	 * @return
	 * @uml.property  name="smallCapsFont"
	 */
	public static GfxFont getSmallCapsFont() {
		return smallCapsFont;
	}
}
