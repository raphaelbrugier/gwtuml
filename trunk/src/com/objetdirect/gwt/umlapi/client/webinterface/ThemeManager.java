package com.objetdirect.gwt.umlapi.client.webinterface;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;

/**
 * This class sets the current theme and give colors corresponding to it
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ThemeManager {
    /**
     * This enumeration initialize all themes with their colors
     * 
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     */
    public enum Theme {
	/**
	 * The normal theme color
	 */
	NORMAL("Normal", 0, new GfxColor(255, 255, 255, 255), // White
		new GfxColor(255, 255, 255, 255), // White
		new GfxColor(0, 0, 0, 255), // Black
		new GfxColor(0, 0, 255, 255), //Blue
		new GfxColor(134, 171, 217, 255), // Soft Blue
		new GfxColor(134, 171, 217, 100)), // Soft Blue Translucent

		/**
		 * Very dark theme color 
		 */
		DARK("Dark", 1, new GfxColor(0, 0, 0, 255), // Black
			new GfxColor(25, 25, 25, 255), // Dark grey
			new GfxColor(255, 255, 255, 255), // White
			new GfxColor(0, 255, 0, 255), // Green
			new GfxColor(134, 0, 217, 255), // Soft pink
			new GfxColor(134, 0, 217, 125)), // Soft pink Translucent

			/**
			 * Clear theme color 
			 */
			CLEAR("Clear", 2, new GfxColor(255, 255, 255, 255), // White
				new GfxColor(225, 225, 255, 255), // Light blue
				new GfxColor(25, 25, 25, 255), // Teal
				new GfxColor(134, 171, 217, 255), // White
				new GfxColor(154, 191, 237, 255), // Soft Blue
				new GfxColor(114, 151, 197, 75)), // Soft Blue Translucent


				/**
				 * Pinky theme color 
				 */
				PINKY("Pinky", 3, new GfxColor("C992FE"),
					new GfxColor("FC00F1"), 
					new GfxColor("590055"), 
					new GfxColor("FE005E"), 
					new GfxColor("2D0059"), 
					new GfxColor("65428822"));

	/**
	 * Getter for the {@link Theme} from his index
	 * 
	 * @param themeIndex The index corresponding to a {@link ThemeManager}
	 * @return The {@link Theme} if it has been found the default theme otherwise
	 */
	public static Theme getThemeFromIndex(final int themeIndex) {
	    for (final Theme theme : Theme.values()) {
		if (theme.index == themeIndex) {
		    return theme;
		}
	    }
	    return Theme.NORMAL;
	}

	/**
	 * Getter for the {@link Theme} from his String name
	 * 
	 * @param themeName The string corresponding to a {@link ThemeManager}
	 * @return The {@link Theme} if it has been found the default theme otherwise
	 */
	public static Theme getThemeFromName(final String themeName) {
	    for (final Theme theme : Theme.values()) {
		if (theme.toString().equalsIgnoreCase(themeName)) {
		    return theme;
		}
	    }
	    return Theme.NORMAL;
	}

	private GfxColor canvasColor;
	private final GfxColor backgroundColor;
	private final GfxColor foregroundColor;
	private final GfxColor highlightedForegroundColor;
	private final String themeName;
	private GfxColor selectBoxForegroundColor;
	private GfxColor selectBoxBackgroundColor;
	private int index;

	private Theme(final String themeName, final int index,
		final GfxColor canvasColor,
		final GfxColor backgroundColor,
		final GfxColor foregroundColor,
		final GfxColor highlightedForegroundColor, 
		final GfxColor selectBoxForegroundColor, 
		final GfxColor selectBoxBackgroundColor) {
	    this.canvasColor = canvasColor;
	    this.themeName = themeName;
	    this.index = index;
	    this.backgroundColor = backgroundColor;
	    this.foregroundColor = foregroundColor;
	    this.highlightedForegroundColor = highlightedForegroundColor;
	    this.selectBoxForegroundColor = selectBoxForegroundColor;
	    this.selectBoxBackgroundColor = selectBoxBackgroundColor;
	}

	/**
	 * Getter for the canvas background {@link GfxColor}
	 *
	 * @return the canvas background {@link GfxColor}
	 */
	public GfxColor getCanvasColor() {
	    return this.canvasColor;
	}

	/**
	 * Getter for the theme background {@link GfxColor}
	 * 
	 * @return The theme background {@link GfxColor}
	 */
	public GfxColor getBackgroundColor() {
	    return this.backgroundColor;
	}

	/**
	 * Getter for the theme foreground {@link GfxColor}
	 * 
	 * @return The theme foreground {@link GfxColor}
	 */
	public GfxColor getForegroundColor() {
	    if (opacity != 255) {
		return new GfxColor(this.foregroundColor.getRed(), this.foregroundColor
			.getGreen(), this.foregroundColor.getBlue(), opacity);
	    }
	    return this.foregroundColor;
	}

	/**
	 * Getter for the theme highlighted foreground {@link GfxColor}
	 * 
	 * @return The theme highlighted foreground {@link GfxColor}
	 */
	public GfxColor getHighlightedForegroundColor() {
	    return this.highlightedForegroundColor;
	}
	/**
	 * Getter for the select box foreground {@link GfxColor}
	 * 
	 * @return the select box foreground {@link GfxColor}
	 */
	public GfxColor getSelectBoxForegroundColor() {
	    return this.selectBoxForegroundColor;
	}
	/**
	 * Getter for the select box background {@link GfxColor}
	 * 
	 * @return the select box background {@link GfxColor}
	 */
	public GfxColor getSelectBoxBackgroundColor() {
	    return this.selectBoxBackgroundColor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	    return this.themeName;
	}
    }

    private static Theme current_theme = Theme.NORMAL;
    private static int opacity = 255;


    /**
     * Getter for the current {@link Theme}
     *
     * @return the current {@link Theme}
     */
    public static Theme getTheme() {
	return current_theme;
    }

    /**
     * Getter for a theme name from a {@link Theme}
     * @param theme The theme to get the name
     * @return The {@link Theme} name of theme
     */
    public static String getThemeName(final Theme theme) {
	return theme.toString();
    }

    /**
     * Setter for the active {@link Theme}
     * 
     * @param theme The theme to sets current
     */
    public static void setCurrentTheme(final Theme theme) {
	if(theme == null) current_theme = Theme.NORMAL;
	else current_theme = theme;
    }

    /**
     * Setter for the foreground color opacity
     * 
     * @param opacity The new foreground opacity (0-255) 
     */
    public static void setForegroundOpacityTo(final int opacity) {
	ThemeManager.opacity = opacity;

    }
}
