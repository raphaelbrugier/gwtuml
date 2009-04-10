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
	NORMAL("Normal", new GfxColor(255, 255, 255, 255), // White
		new GfxColor(0, 0, 0, 255), // Black
		new GfxColor(0, 0, 255, 255)), // Blue

		/**
		 * Very dark theme color 
		 */
		DARK("Dark", new GfxColor(0, 0, 0, 255), // Black
			new GfxColor(255, 255, 255, 255), // White
			new GfxColor(0, 255, 0, 255)), // Green
			/**
			 * Aero try theme color 
			 */
			AERO("Aero", new GfxColor(26, 106, 155, 255), // Light Blue
				new GfxColor(56, 192, 192, 255), // Teal
				new GfxColor(255, 255, 255, 255)); // White

	// NORMAL("Normal", new GfxColor(255, 255, 255, 255), // White
	// new GfxColor(0, 0, 0, 255), // Black
	// new GfxColor(0, 0, 255, 255)), // Blue
	// DARK("Dark", new GfxColor(0, 0, 0, 255), // Black
	// new GfxColor(255, 255, 255, 255), // White
	// new GfxColor(0, 255, 0, 255)), // Green
	// AERO("Aero", new GfxColor(26, 106, 155, 255), // Light Blue
	// new GfxColor(56, 192, 192, 255), // Teal
	// new GfxColor(255, 255, 255, 255)); // White


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

	private final GfxColor backgroundColor;
	private final GfxColor foregroundColor;
	private final GfxColor highlightedForegroundColor;
	private final String themeName;

	private Theme(final String themeName, final GfxColor backgroundColor,
		final GfxColor foregroundColor,
		final GfxColor highlightedForegroundColor) {
	    this.themeName = themeName;
	    this.backgroundColor = backgroundColor;
	    this.foregroundColor = foregroundColor;
	    this.highlightedForegroundColor = highlightedForegroundColor;
	}

	/**
	 * Getter for the theme background {@link GfxColor}
	 * 
	 * @return The theme background {@link GfxColor}
	 */
	public GfxColor getThemeBackgroundColor() {
	    return this.backgroundColor;
	}

	/**
	 * Getter for the theme foreground {@link GfxColor}
	 * 
	 * @return The theme foreground {@link GfxColor}
	 */
	public GfxColor getThemeForegroundColor() {
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
	public GfxColor getThemeHighlightedForegroundColor() {
	    return this.highlightedForegroundColor;
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
     * Getter for the current theme background {@link GfxColor}
     * 
     * @return The current theme background {@link GfxColor}
     */
    public static GfxColor getBackgroundColor() {
	return current_theme.getThemeBackgroundColor();
    }
    /**
     * Getter for the current theme foreground {@link GfxColor}
     * 
     * @return The current theme foreground {@link GfxColor}
     */
    public static GfxColor getForegroundColor() {
	return current_theme.getThemeForegroundColor();
    }
    /**
     * Getter for the current theme highlighted foreground {@link GfxColor}
     * 
     * @return The current theme highlighted foreground {@link GfxColor}
     */
    public static GfxColor getHighlightedForegroundColor() {
	return current_theme.getThemeHighlightedForegroundColor();
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
	current_theme = theme;
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
