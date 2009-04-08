package com.objetdirect.gwt.umlapi.client.webinterface;

import com.objetdirect.gwt.umlapi.client.gfx.GfxColor;

/**
 * @author florian
 */
public class ThemeManager {
    /**
     * @author florian
     */
    public enum Theme {
	NORMAL("Normal", 
		new GfxColor(255,255,255,255), 	// White
		new GfxColor(0,0,0,255), 	// Black
		new GfxColor(0,0,255,255)), 	// Blue
	DARK("Dark", 
		new GfxColor(0,0,0,255), 	// Black
		new GfxColor(255,255,255,255), 	// White
		new GfxColor(0,255,0,255)), 	// Green
	AERO("Aero", 
		new GfxColor(26,106,155,255), 	// Light Blue
		new GfxColor(56,192,192,255), 	// Teal
		new GfxColor(255,255,255,255)); // White

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

	/**
		 * 
		 */
	private final String themeName;

	private Theme(final String themeName, final GfxColor backgroundColor,
		final GfxColor foregroundColor,
		final GfxColor highlightedForegroundColor) {
	    this.themeName = themeName;
	    this.backgroundColor = backgroundColor;
	    this.foregroundColor = foregroundColor;
	    this.highlightedForegroundColor = highlightedForegroundColor;
	}

	public GfxColor getThemeBackgroundColor() {
	    return this.backgroundColor;
	}

	public GfxColor getThemeForegroundColor() {
	    if (opacity != 255) {
		return new GfxColor(this.foregroundColor.getRed(), this.foregroundColor
			.getGreen(), this.foregroundColor.getBlue(), opacity);
	    }
	    return this.foregroundColor;
	}

	public GfxColor getThemeHighlightedForegroundColor() {
	    return this.highlightedForegroundColor;
	}

	@Override
	public String toString() {
	    return this.themeName;
	}
    }

    private static Theme current_theme = Theme.NORMAL;
    private static int opacity = 255;

    public static GfxColor getBackgroundColor() {
	return current_theme.getThemeBackgroundColor();
    }

    public static GfxColor getForegroundColor() {
	return current_theme.getThemeForegroundColor();
    }

    public static GfxColor getHighlightedForegroundColor() {
	return current_theme.getThemeHighlightedForegroundColor();
    }

    public static String getThemeName(final Theme theme) {
	return theme.toString();
    }

    public static void setCurrentTheme(final Theme theme) {
	current_theme = theme;
    }

    public static void setForegroundOpacityTo(final int i) {
	opacity = i;

    }
}
