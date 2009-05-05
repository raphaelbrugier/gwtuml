package com.objetdirect.gwt.umlapi.client.webinterface;

import com.objetdirect.gwt.umlapi.client.gfx.GfxFont;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNote;

/**
 * This class allows to set and access configuration values
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class OptionsManager {

    /**
     * This enumeration lists the available quality level for the drawer
     * 
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     *
     */
    public enum QualityLevel {
	
	 /**
	 * Highest quality level requires JIT JavaScript Compile
	 */
	VERY_HIGH("Very High", "Slow", 40),
	 /**
	 * It is the best compromise quality level available for medium to fast PCs/browsers
	 */
	HIGH("High", "For good pc and browser", 30), 
	 /**
	 * It is the normal quality, worths a try if High is slow
	 */
	NORMAL("Normal", "Recommended for real browser", 20),
	 /**
	 * Lowest quality but not so fast by the way
	 */
	LOW("Low", "For very old pc and IE users", 10);


	/**
	 * Compare two quality level and return true if the first is higher or equal than the second
	 * 
	 * @param q1 The first quality level
	 * @param q2 The first quality level
	 * @return True if the first quality level is higher or equal than the second
	 */
	public static boolean compare(final QualityLevel q1,
		final QualityLevel q2) {
	    return q1.index >= q2.index;
	}

	/**
	 * Return a the quality level that corresponds to the String name
	 * 
	 * @param qualityName the String for the quality to retrieve
	 * @return The {@link QualityLevel} corresponding to the name
	 */
	public static QualityLevel getQualityFromName(final String qualityName) {
	    for (final QualityLevel qlvl : QualityLevel.values()) {
		if (qlvl.toString().equalsIgnoreCase(qualityName)) {
		    return qlvl;
		}
	    }
	    return QualityLevel.HIGH;
	}

	private final String description;

	private final int index;

	private final String name;

	private QualityLevel(final String name, final String description,
		final int index) {
	    this.name = name;
	    this.description = description;
	    this.index = index;

	}

	/**
	 * Getter for the {@link QualityLevel} description 
	 * 
	 * @return the description
	 */
	public String getDescription() {
	    return this.description;
	}

	/** 
	 * Getter for the {@link QualityLevel} name
	 *  
	 * @return the name
	 */
	public String getName() {
	    return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	    return this.name + " (" + this.description + ")";
	}
    }

    private static final int ARROW_LENGTH = 25;
    private static final int ARROW_WIDTH = 15;
    private static final int BOTTOM_RECT_PADDING = 4;
    private static final int BOTTOM_TEXT_PADDING = 1;    
    private static final int CROSS_LENGTH = 5;
    private static final int CROSS_WIDTH = 10;
    private static final int DIAMOND_LENGTH = 20;
    private static final int DIAMOND_WIDTH = 15;
    private static GfxFont font = new GfxFont("monospace", 10, GfxFont._NORMAL,
	    GfxFont._NORMAL, GfxFont._NORMAL);
    private static GfxFont smallFont = new GfxFont("monospace", 9, GfxFont._NORMAL,
	    GfxFont._NORMAL, GfxFont.LIGHTER);
    private static final int LEFT_RECT_PADDING = 2;
    private static final int LEFT_TEXT_PADDING = 1;
    private static final int MOVING_STEP = 20;
    private static final int NOTE_CORNER_HEIGHT = 15;
    private static final int NOTE_CORNER_WIDTH = 15;
    private static QualityLevel qualityLevel;
    private static final int REFLEXIVE_PATH_X_GAP = 25;
    private static final int REFLEXIVE_PATH_Y_GAP = 50;
    private static final int RIGHT_RECT_PADDING = 2;
    private static final int RIGHT_TEXT_PADDING = 1; //Depends on browser/os -> taking max needed
    private static GfxFont smallCapsFont = new GfxFont("monospace", 10,
	    GfxFont._NORMAL, GfxFont.SMALL_CAPS, GfxFont._NORMAL);
    private static final int SOLID_ARROW_LENGTH = 30;
    private static final int SOLID_ARROW_WIDTH = 20;
    private static final int TOP_RECT_PADDING = 4;
    private static final int TOP_TEXT_PADDING = 1;
    private static final int UNDERLINE_SHIFT = 4;
    
    
    /**
     * Getter for the arrow length
     * 
     * @return the arrow length
     */
    public static int getArrowLength() {
	return ARROW_LENGTH;
    }
    /**
     * Getter for the arrow width
     * 
     * @return the arrow width 
     */
    public static int getArrowWidth() {
	return ARROW_WIDTH;
    }

    /**
     * Getter for the cross length
     *
     * @return the cross length
     */
    public static final int getCrossLength() {
        return CROSS_LENGTH;
    }
    /**
     * Getter for the cross width
     *
     * @return the cross width
     */
    public static final int getCrossWidth() {
        return CROSS_WIDTH;
    }
    /**
     * Getter for the diamond length
     * 
     * @return the diamond length
     */
    public static int getDiamondLength() {
	return DIAMOND_LENGTH;
    }

    /**
     * Getter for the diamond width
     * 
     * @return the diamond width
     */
    public static int getDiamondWidth() {
	return DIAMOND_WIDTH;
    }

    /**
     * Getter for the application font
     * 
     * @return the current application font
     */
    public static GfxFont getFont() {
	return font;
    }
    
    /**
     * Getter for the application small font
     * 
     * @return the current application small font
     */
    public static GfxFont getSmallFont() {
	return smallFont;
    }

    /**
     * Getter for the artifact key moving step
     *  
     * @return the moving step
     */
    public static int getMovingStep() {
	return MOVING_STEP;
    }

    /**
     * Getter for the {@link UMLNote} corner height
     * 
     * @return the {@link UMLNote} corner height
     */
    public static int getNoteCornerHeight() {
	return NOTE_CORNER_HEIGHT;
    }

    /**
     * Getter for the {@link UMLNote} corner width
     * 
     * @return the {@link UMLNote} corner width
     */
    public static int getNoteCornerWidth() {
	return NOTE_CORNER_WIDTH;
    }

    /**
     * Getter for the current {@link QualityLevel}
     * 
     * @return the current {@link QualityLevel}
     */
    public static QualityLevel getQualityLevel() {
	return qualityLevel;
    }

    /**
     * Getter for the rectangle bottom padding
     * 
     * @return The rectangle bottom padding
     */
    public static int getRectangleBottomPadding() {
	return BOTTOM_RECT_PADDING;
    }
    
    /**
     * Getter for the rectangle left padding
     * 
     * @return The rectangle left padding
     */
    public static int getRectangleLeftPadding() {
	return LEFT_RECT_PADDING;
    }
    
    /**
     * Getter for the rectangle right padding
     * 
     * @return The rectangle right padding
     */
    public static int getRectangleRightPadding() {
	return RIGHT_RECT_PADDING;
    }
    
    /**
     * Getter for the rectangle top padding
     * 
     * @return The rectangle top padding
     */
    public static int getRectangleTopPadding() {
	return TOP_RECT_PADDING;
    }
    
    /**
     * Getter for the total rectangle horizontal padding
     * 
     * @return The total rectangle horizontal padding
     */
    public static int getRectangleXTotalPadding() {
	return RIGHT_RECT_PADDING + LEFT_RECT_PADDING;
    }
    
    /**
     * Getter for the total rectangle vertical padding
     * 
     * @return The total rectangle vertical padding
     */
    public static int getRectangleYTotalPadding() {
	return TOP_RECT_PADDING + BOTTOM_RECT_PADDING;
    }

    /**
     * Getter for the horizontal gap for reflexive relations
     * 
     * @return the horizontal gap for reflexive relations
     */
    public static int getReflexivePathXGap() {
	return REFLEXIVE_PATH_X_GAP;
    }

    /**
     * Getter for the vertical gap for reflexive relations
     * 
     * @return the vertical gap for reflexive relations
     */
    public static int getReflexivePathYGap() {
	return REFLEXIVE_PATH_Y_GAP;
    }

    /**
     * Getter for the application font with small caps
     * 
     * @return the current application font with small caps
     */
    public static GfxFont getSmallCapsFont() {
	return smallCapsFont;
    }

     /**
     * Getter for the solid arrow length
     * 
     * @return the arrow length
     */
    public static int getSolidArrowLength() {
	return SOLID_ARROW_LENGTH;
    }

    /**
    * Getter for the solid arrow width
    * 
    * @return the arrow width
    */
    public static int getSolidArrowWidth() {
	return SOLID_ARROW_WIDTH;
    }
    
    /**
     * Getter for the text bottom padding
     * 
     * @return The text bottom padding
     */
    public static int getTextBottomPadding() {
	return BOTTOM_TEXT_PADDING;
    }
    
    /**
     * Getter for the text left padding
     * 
     * @return The text left padding
     */
    public static int getTextLeftPadding() {
	return LEFT_TEXT_PADDING;
    }
    
    /**
     * Getter for the text right padding
     * 
     * @return The text right padding
     */
    public static int getTextRightPadding() {
	return RIGHT_TEXT_PADDING;
    }
    
    /**
     * Getter for the text top padding
     * 
     * @return The text top padding
     */
    public static int getTextTopPadding() {
	return TOP_TEXT_PADDING;
    }
    
    /**
     * Getter for the total text horizontal padding
     * 
     * @return The total text horizontal padding
     */
    public static int getTextXTotalPadding() {
	return RIGHT_TEXT_PADDING + LEFT_TEXT_PADDING;
    }
    
    /**
     * Getter for the total text vertical padding
     * 
     * @return The total text vertical padding
     */
    public static int getTextYTotalPadding() {
	return TOP_TEXT_PADDING + BOTTOM_TEXT_PADDING;
    }

    /**
     * Getter for the shift needed between the text and the underline shift
     *
     * @return the shift between the text and the underline shift
     */
    public static int getUnderlineShift() {
        return UNDERLINE_SHIFT;
    }
    /**
     * Return true if the current quality level is almost the given level
     * 
     * @param level The level to compare with
     * 
     * @return True if the current quality level is almost the given level, False otherwise
     */
    public static boolean qualityLevelIsAlmost(final QualityLevel level) {
	return QualityLevel.compare(qualityLevel, level);
    }

    /**
     * Setter for the current {@link QualityLevel}
     * 
     * @param qualityLevel
     *            the qualityLevel to set
     */
    public static void setQualityLevel(final QualityLevel qualityLevel) {
	OptionsManager.qualityLevel = qualityLevel;
    }
   }
